package com.wwh.test.swing.monitor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.event.EventListenerList;

// 应该抽象出接口
public class MonitorPanelModel implements Serializable {

    public enum XAxisMode {
        AUTO, FIXED, KEEP_MOVE
    }

    public enum YAxisMode {
        AUTO, FIXED
    }

    /**
     * 默认的最大坐标点的数量1000
     */
    public static final int DEFAULT_MAX_COORDINATE_NUMBER = 1000;
    private static final long serialVersionUID = 1L;
    /**
     * 坐标集合
     */
    private List<Coordinate> coordinatelist;
    /**
     * 使X轴呈现一个向左边移动的效果<br>
     * 需要指定一个时间段
     */
    private Long keepMoveTimeInterval;
    private EventListenerList listenerList = new EventListenerList();
    /**
     * 最大的坐标点个数
     */
    private int maxCoordinateCount = DEFAULT_MAX_COORDINATE_NUMBER;
    /**
     * 最大X坐标
     */
    private Long maxXCoords;
    /**
     * X轴上的最大值
     */
    private Long maxXValue;
    /**
     * 最大Y坐标
     */
    private Long maxYCoords;
    /**
     * Y轴上的最大值
     */
    private Long maxYValue;
    /**
     * 最小X坐标
     */
    private Long minXCoords;
    /**
     * X轴上的最小值
     */
    private Long minXValue;
    /**
     * 最小Y坐标
     */
    private Long minYCoords;

    /**
     * Y轴上的最小值
     */
    private Long minYValue;

    /**
     * 记录一个开始的时间
     */
    private Long startTime;

    /**
     * X 轴间距
     */
    private Long xAxisInterval;

    private XAxisMode xAxisMode = XAxisMode.AUTO;

    /**
     * Y轴间距
     */
    private Long yAxisInterval;

    private YAxisMode yAxisMode = YAxisMode.AUTO;

    public MonitorPanelModel() {
        coordinatelist = new ArrayList<Coordinate>();
    }

    public void addCoordinate(Coordinate coordinate) {
        coordinatelist.add(coordinate);
        if (coordinatelist.size() > maxCoordinateCount) {
            coordinatelist.remove(0);
            fireMonitorPanelDataChanged();
        } else {
            fireMonitorPanelDataAdd(coordinate);
        }
    }

    public void addMonitorPanelModelListener(MonitorPanelModelListener l) {
        listenerList.add(MonitorPanelModelListener.class, l);
    }

    /**
     * 根据间隔计算Y轴上的线条数量
     * 
     * @param interval
     * @return
     */
    private int calcLineCountByInterval(long interval) {
        // 计算Y轴的最大值
        long tmp = maxYValue / interval;
        maxYCoords = tmp * interval;
        if (maxYCoords < maxYValue) {
            maxYCoords += interval;
        }

        tmp = minYValue / interval;
        minYCoords = tmp * interval;
        if (minYCoords > minYValue) {
            minYCoords -= interval;
        }

        long line = (maxYCoords - minYCoords) / interval;

        yAxisInterval = interval;

        return (int) line;
    }

    public void clearCoordinate() {
        coordinatelist.clear();
        fireMonitorPanelDataChanged();
    }

    private void fireMonitorPanelChanged(MonitorPanelModelEvent e) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == MonitorPanelModelListener.class) {
                ((MonitorPanelModelListener) listeners[i + 1]).monitorPanelChanged(e);
            }
        }
    }

    private void fireMonitorPanelDataAdd(Coordinate coordinate) {
        refreshValue(coordinate);
        fireMonitorPanelChanged(new MonitorPanelModelEvent(this));
    }

    private void fireMonitorPanelDataChanged() {
        // 刷新数据
        refreshAllValue();
        fireMonitorPanelChanged(new MonitorPanelModelEvent(this));
    }

    private void fireMonitorPanelPropertyChanged(String property, Object oldValue, Object newValue) {
        // 属性先不管
        fireMonitorPanelChanged(new MonitorPanelModelEvent(this));
    }

    private long[] getAutoXAxisLines(int bestXLineCount) {
        if (maxXValue == null || minXValue == null) {
            return null;
        }
        long differ = maxXValue - minXValue;
        if (differ == 0) {
            return null;
        }
        minXCoords = minXValue;
        maxXCoords = maxXValue;
        xAxisInterval = differ / bestXLineCount;
        if (xAxisInterval < 1) {
            return null;
        }
        List<Long> l = new ArrayList<>();
        long tmp = maxXCoords;
        while (tmp > minXCoords) {
            l.add(tmp);
            tmp -= xAxisInterval;
        }
        long[] ret = new long[l.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = l.get(i);
        }
        return ret;
    }

    private long[] getAutoYAxisLines(int bestYLineCount) {
        if (maxYValue == null || minYValue == null) {
            return null;
        }
        long differValue = maxYValue - minYValue;
        if (differValue == 0) {
            // 此时可能是一条横线
            yAxisInterval = 1L;
            long absMaxYValue = Math.abs(maxYValue);
            if (absMaxYValue > 10) {
                double log = Math.log10(absMaxYValue / 10);
                // 四舍五入取整
                log = Math.round(log);
                // 间距
                yAxisInterval = (long) Math.pow(10, log);
            }
            maxYCoords = ((maxYValue / yAxisInterval) + 1) * yAxisInterval;
            minYCoords = ((maxYValue / yAxisInterval) - 1) * yAxisInterval;
            long middleValue = (maxYValue / yAxisInterval) * yAxisInterval;
            return new long[] { minYCoords, middleValue, maxYCoords };
        }

        long interval = 1;
        if (differValue > 10) {
            double log = Math.log10(differValue / 10);
            // 四舍五入取整
            log = Math.round(log);
            // 间距
            interval = (long) Math.pow(10, log);
        }
        readjustYAxis(interval, bestYLineCount);

        // 返回Y轴上的坐标线
        return getFixedYAxisLines();
    }

    public List<Coordinate> getCoordinatelist() {
        return Collections.unmodifiableList(coordinatelist);
    }

    public int getCoordinateSize() {
        return coordinatelist.size();
    }

    private long[] getFixedXAxisLines() {
        if (maxXCoords == null || minXCoords == null || xAxisInterval == null) {
            throw new IllegalArgumentException("固定模式下，最大值、最小值、间距都不能为空");
        }
        List<Long> l = new ArrayList<>();
        long tmp = minXCoords;
        while (tmp <= maxXCoords) {
            l.add(tmp);
            tmp += xAxisInterval;
        }
        long[] ret = new long[l.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = l.get(i);
        }
        return ret;
    }

    private long[] getFixedYAxisLines() {
        if (maxYCoords == null || minYCoords == null || yAxisInterval == null) {
            throw new IllegalArgumentException("固定模式下，最大值、最小值、间距都不能为空");
        }
        List<Long> l = new ArrayList<>();
        long tmp = minYCoords;
        while (tmp <= maxYCoords) {
            l.add(tmp);
            tmp += yAxisInterval;
        }
        long[] ret = new long[l.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = l.get(i);
        }
        return ret;
    }

    public Long getKeepMoveTimeInterval() {
        return keepMoveTimeInterval;
    }

    private long[] getKeepMoveXAxisLines(int bestXLineCount) {
        if (keepMoveTimeInterval == null || keepMoveTimeInterval < 1) {
            throw new IllegalArgumentException("移动模式下，固定的时间段不能为空或小于1");
        }
        if (startTime == null) {
            startTime = System.currentTimeMillis();
        }
        maxXCoords = System.currentTimeMillis();
        minXCoords = maxXCoords - keepMoveTimeInterval;

        xAxisInterval = keepMoveTimeInterval / bestXLineCount;
        if (xAxisInterval < 1) {
            return null;
        }
        List<Long> l = new ArrayList<>();
        long tmp = startTime;
        while (tmp <= maxXCoords) {
            if (tmp >= minXCoords) {
                l.add(tmp);
            }
            tmp += xAxisInterval;
        }
        long[] ret = new long[l.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = l.get(i);
        }
        return ret;
    }

    public int getMaxCoordinateCount() {
        return maxCoordinateCount;
    }

    public Long getMaxXCoords() {
        return maxXCoords;
    }

    public Long getMaxXValue() {
        return maxXValue;
    }

    public Long getMaxYCoords() {
        return maxYCoords;
    }

    public Long getMaxYValue() {
        return maxYValue;
    }

    public Long getMinXCoords() {
        return minXCoords;
    }

    public Long getMinXValue() {
        return minXValue;
    }

    public Long getMinYCoords() {
        return minYCoords;
    }

    public Long getMinYValue() {
        return minYValue;
    }

    public MonitorPanelModelListener[] getMonitorPanelModelListeners() {
        return listenerList.getListeners(MonitorPanelModelListener.class);
    }

    public long getXAxisFirstValue() {
        if (coordinatelist.isEmpty()) {
            return 0;
        }
        return coordinatelist.get(0).getxValue();
    }

    public Long getxAxisInterval() {
        return xAxisInterval;
    }

    public long getXAxisLastValue() {
        if (coordinatelist.isEmpty()) {
            return 0;
        }
        return coordinatelist.get(coordinatelist.size() - 1).getxValue();
    }

    /**
     * X 轴上要画的竖线
     * 
     * @param bestXLineCount 最佳的竖线条数
     * @return
     */
    public long[] getXAxisLines(int bestXLineCount) {

        switch (xAxisMode) {
        case AUTO:
            return getAutoXAxisLines(bestXLineCount);
        case FIXED:
            return getFixedXAxisLines();
        case KEEP_MOVE:
            return getKeepMoveXAxisLines(bestXLineCount);
        default:
            return null;
        }
    }

    public XAxisMode getxAxisMode() {
        return xAxisMode;
    }

    public Long getyAxisInterval() {
        return yAxisInterval;
    }

    /**
     * Y 轴上要画的横线
     * 
     * @param bestYLineCount 最佳的横线条数
     * @return
     */
    public long[] getYAxisLines(int bestYLineCount) {
        switch (yAxisMode) {
        case AUTO:
            return getAutoYAxisLines(bestYLineCount);
        case FIXED:
            return getFixedYAxisLines();
        default:
            return null;
        }
    }

    public YAxisMode getyAxisMode() {
        return yAxisMode;
    }

    /**
     * 重新计算Y轴
     * 
     * @param interval
     */
    private void readjustYAxis(long interval, int bestYLineCount) {
        // 先根据目前的间距计算一次Y轴的行数
        int lineCount = calcLineCountByInterval(interval);

        // 再尝试调整为最佳的
        long newInterval = 0;
        if (bestYLineCount > lineCount) {
            // 减小间距
            float f = (float) bestYLineCount / lineCount;
            int p = Math.round(f);
            if (p == 1) {
                return;
            } else {
                for (int i = p; i > 1; i--) {
                    if (interval % i == 0) {
                        newInterval = interval / i;
                        break;
                    }
                }
            }
        } else {
            // 增加间距
            float f = (float) lineCount / bestYLineCount;
            int p = Math.round(f);
            if (p == 1) {
                return;
            } else {
                newInterval = interval * p;
            }
        }

        if (newInterval != 0) {
            // 根据调整后的间距再算一次
            lineCount = calcLineCountByInterval(newInterval);
        }
    }

    private void refreshAllValue() {
        minXValue = null;
        maxXValue = null;
        minYValue = null;
        maxYValue = null;
        for (Coordinate coordinate : coordinatelist) {
            refreshValue(coordinate);
        }
    }

    private void refreshValue(Coordinate coordinate) {
        long y = coordinate.getyValue();
        long x = coordinate.getxValue();
        if (maxYValue == null || y > maxYValue) {
            maxYValue = y;
        }
        if (minYValue == null || y < minYValue) {
            minYValue = y;
        }
        if (maxXValue == null || x > maxXValue) {
            maxXValue = x;
        }
        if (minXValue == null || x < minXValue) {
            minXValue = x;
        }
    }

    public void removeMonitorPanelModelListener(MonitorPanelModelListener l) {
        listenerList.remove(MonitorPanelModelListener.class, l);
    }

    public void setCoordinatelist(List<Coordinate> coordinatelist) {
        if (coordinatelist == null) {
            throw new IllegalArgumentException("不能为空");
        }
        this.coordinatelist = coordinatelist;
        fireMonitorPanelDataChanged();
    }

    public void setKeepMoveTimeInterval(Long keepMoveTimeInterval) {
        fireMonitorPanelPropertyChanged(null, null, null);
        this.keepMoveTimeInterval = keepMoveTimeInterval;
    }

    public void setMaxCoordinateCount(int maxCoordinateCount) {
        fireMonitorPanelPropertyChanged(null, null, null);
        this.maxCoordinateCount = maxCoordinateCount;
    }

    public void setMaxXCoords(Long maxXCoords) {
        fireMonitorPanelPropertyChanged(null, null, null);
        this.maxXCoords = maxXCoords;
    }

    public void setMaxYCoords(Long maxYCoords) {
        fireMonitorPanelPropertyChanged(null, null, null);
        this.maxYCoords = maxYCoords;
    }

    public void setMinXCoords(Long minXCoords) {
        fireMonitorPanelPropertyChanged(null, null, null);
        this.minXCoords = minXCoords;
    }

    public void setMinYCoords(Long minYCoords) {
        fireMonitorPanelPropertyChanged(null, null, null);
        this.minYCoords = minYCoords;
    }

    public void setxAxisInterval(Long xAxisInterval) {
        fireMonitorPanelPropertyChanged(null, null, null);
        this.xAxisInterval = xAxisInterval;
    }

    public void setxAxisMode(XAxisMode xAxisMode) {
        fireMonitorPanelPropertyChanged(null, null, null);
        this.xAxisMode = xAxisMode;
    }

    public void setyAxisInterval(Long yAxisInterval) {
        fireMonitorPanelPropertyChanged(null, null, null);
        this.yAxisInterval = yAxisInterval;
    }

    public void setyAxisMode(YAxisMode yAxisMode) {
        fireMonitorPanelPropertyChanged(null, null, null);
        this.yAxisMode = yAxisMode;
    }

}
