package com.wwh.test.swing.monitor1;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JPanel;

/**
 * <pre>
 * 有XY轴的曲线图监控面板
 * 
 * <pre>
 * 
 * @author 313921
 * @date 2015-01-28 22:11:04
 */
public class MonitorPanel2 extends JPanel {

    // 固定Y轴
    // 固定X轴

    // X轴显示方式的枚举
    public enum PrintMode {
        NORMAL, FIT_WIDTH
    }

    // TODO 更多的配置参数
    // 窗口大小变化时图片刷新
    // 最大值，最小值
    // 副标题
    // 各种颜色，字体等的设置

    // 缺省的没有任何数据时需要显示一个空的坐标系

    private static final long serialVersionUID = 1L;

    private SimpleDateFormat dateF = new SimpleDateFormat("HH:mm");
    private SimpleDateFormat dateF2 = new SimpleDateFormat("ss:SSS");

    /**
     * 坐标集合
     */
    private List<Coordinate> coordinatelist;

    public MonitorPanel2() {
        coordinatelist = new ArrayList<>();
    }

    // 添加坐标
    // 最大的坐标数量
    // 清空坐标

    /**
     * Create the panel.
     */
    public MonitorPanel2(List<Coordinate> list) {
        if (list == null) {
            throw new IllegalArgumentException("参数不能为null");
        }
        this.coordinatelist = list;
    }

    /**
     * Y轴上的最大值
     */
    private long maxYValue;

    /**
     * Y轴上的最小值
     */
    private long minYValue;

    /**
     * <pre>
     * 计算Y轴的最大最小值
     * </pre>
     */
    private void calcMaxAndMinYValue() {
        // 先设置一次
        maxYValue = Long.MIN_VALUE;
        minYValue = Long.MAX_VALUE;
        for (Coordinate coordinate : coordinatelist) {
            if (coordinate.getyValue() > maxYValue) {
                maxYValue = coordinate.getyValue();
            }
            if (coordinate.getyValue() < minYValue) {
                minYValue = coordinate.getyValue();
            }
        }
    }

    /**
     * 最大Y坐标
     */
    private long maxY;
    /**
     * 最小Y坐标
     */
    private long minY;

    /**
     * Y轴间距
     */
    private long yAxisInterval;

    /**
     * Y轴线条数量
     */
    private int yAxisLineCount;

    /**
     * 最少的Y轴线条数量
     */
    private static final int minYAxisLineCount = 3;

    /**
     * 最合适的Y轴线条高度间距
     */
    private static final int bastYAxisLineHeightInterval = 26;

    /**
     * 根据坐标系高度计算最佳的横线条数
     * 
     * @return
     */
    private int getBestYAxisLineCount() {
        int lineCount = getGraphHeight() / bastYAxisLineHeightInterval;
        lineCount = lineCount > minYAxisLineCount ? lineCount : minYAxisLineCount;
        System.out.println("计算后的最佳的Y轴线条数量" + lineCount);

        return lineCount;
    }

    // 动态Y轴，固定Y轴

    /**
     * <pre>
     * 计算最大 、最小 Y轴值
     * </pre>
     */
    private void calcYAxis() {
        calcMaxAndMinYValue();

        long differValue = maxYValue - minYValue;
        if (differValue == 0) {
            maxY = maxYValue;
            minY = minYValue;
            return;
        }

        System.out.println("差值是：" + differValue);

        long interval = 1;
        if (differValue > 10) {
            double log = Math.log10(differValue / 10);
            // 四舍五入取整
            log = Math.round(log);
            // 间距
            interval = (long) Math.pow(10, log);
            System.out.println("计算后的Y坐标间隔：" + interval);
        }
        readjustYAxis(interval);
    }

    /**
     * 重新计算Y轴
     * 
     * @param interval
     */
    private void readjustYAxis(long interval) {
        // 根据高度计算的最佳行数
        int bestLineCount = getBestYAxisLineCount();
        // 先根据间距计算一次Y轴的行数
        int lineCount = calcLineCountByInterval(interval);

        // 再尝试调整为最佳的
        long newInterval = 0;
        if (bestLineCount > lineCount) {
            // 减小间距
            float f = (float) bestLineCount / lineCount;
            int p = Math.round(f);
            if (p == 1) {
                return;
            } else {
                for (int i = p; i > 1; i--) {
                    if (interval % i == 0) {
                        newInterval = interval / i;
                        System.out.println("间距减小为" + newInterval);
                        break;
                    }
                }
            }
        } else {
            // 增加间距
            float f = (float) lineCount / bestLineCount;
            int p = Math.round(f);
            if (p == 1) {
                return;
            } else {
                newInterval = interval * p;
                System.out.println("间距增加为" + newInterval);
            }
        }

        if (newInterval != 0) {
            // 再算一次
            lineCount = calcLineCountByInterval(newInterval);
            System.out.println("调整后的行数为:" + lineCount);
        }
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
        maxY = tmp * interval;
        if (maxY < maxYValue) {
            maxY += interval;
        }
        System.out.println("Y轴最大值：" + maxY);

        tmp = minYValue / interval;
        minY = tmp * interval;
        if (minY > minYValue) {
            minY -= interval;
        }

        System.out.println("Y轴最小值：" + minY);

        long line = (maxY - minY) / interval;
        System.out.println("一共要画线：" + line);

        yAxisInterval = interval;
        yAxisLineCount = (int) line;

        return yAxisLineCount;
    }

    /**
     * Y轴的文本宽度
     */
    private int YTextWidth = 40;
    /**
     * X轴的文本高度
     */
    private int XTextHeight = 40;

    /**
     * 标题的高度
     */
    private int titleHeight = 20;

    private static final int defaultCurrentTextWidth = 50;
    /**
     * 显示当前值的文本宽度
     */
    private int currentTextWidth = 50;

    /**
     * Y轴上一个与像素点的比例
     */
    private double yRatio;

    /**
     * 获取曲线图区域的高度
     * 
     * @return
     */
    public int getGraphHeight() {
        if (bufferImage == null) {
            return 0;
        }
        return bufferImage.getHeight() - titleHeight - XTextHeight;
    }

    /**
     * 获取曲线图区域的宽度
     * 
     * @return
     */
    public int getGraphWidth() {
        if (bufferImage == null) {
            return 0;
        }
        return bufferImage.getWidth() - YTextWidth - currentTextWidth;
    }

    /**
     * <pre>
     * 计算Y轴比例 
     * 既：值与显示区域的像素的比例
     * </pre>
     */
    private void calcYRatio() {
        calcYAxis();
        // 计算曲线图的高度
        int graphHeight = getGraphHeight();

        if (graphHeight <= 0) {
            yRatio = 0;
            return;
        }
        long yd = maxY - minY;// Y轴
        if (yd <= 0) {
            yRatio = 0;
            return;
        }
        yRatio = (double) graphHeight / (double) yd;// 得到比例
    }

    /**
     * X轴上的一个与像素点的比例
     */
    private double xRatio;

    /**
     * 最小的X轴的数值
     */
    private long minXValue;

    /**
     * 最大的X轴的数值
     */
    private long maxXValue;

    /**
     * <pre>
     * 计算X轴的比例
     * 根据list中的元素个数
     * </pre>
     */
    private void calcXRatio() {

        // 曲线图的宽度
        int graphWidth = getGraphWidth();
        if (graphWidth <= 0) {
            xRatio = 0;
            return;
        }
        minXValue = coordinatelist.get(0).getxValue();
        // 这里使用list，如果list会改变需要注意
        maxXValue = coordinatelist.get(coordinatelist.size() - 1).getxValue();
        long xv = maxXValue - minXValue;
        // 得到比例
        xRatio = (double) graphWidth / (double) xv;
    }

    /**
     * <pre>
     * 获取值对应X点坐标
     * </pre>
     * 
     * @param value
     * @return
     */
    private int getXPoint(long value) {
        long v = value - minXValue;
        int x = (int) (v * xRatio);
        return x + YTextWidth;
    }

    /**
     * <pre>
     * 获取值对应Y点坐标
     * </pre>
     * 
     * @param value
     * @return
     */
    private int getYPoint(long value) {
        long v = value - minY;
        int y = (int) (v * yRatio);
        int graphHeight = getGraphHeight();
        return graphHeight - y + titleHeight;
    }

    /**
     * 图片
     */
    private BufferedImage bufferImage;
    private Graphics2D graphics;

    /**
     * 曲线区域的背景颜色
     */
    private Color graphBackgroundColor = new Color(0, 100, 0);
    private Color lineColor = new Color(0, 0, 0);
    private Color graphColor = new Color(250, 0, 0);
    private Color fontColor = Color.BLACK;
    /**
     * 标题颜色
     */
    private Color titleFontColor = Color.BLACK;
    /**
     * 标题字体
     */
    private Font titleFont = new Font("宋体", Font.BOLD, 14);

    /**
     * 图片的宽度，整个组件就是一个画上去的图片
     */
    private int imgWidth;
    /**
     * 图片的高度
     */
    private int imgHeight;

    private String title = "测试标题";

    public void setGraphTitle(String title) {
        this.title = title;
    }

    private void drawEmptyImage() {
        int tw = getWidth();
        int th = getHeight();
        if (tw != imgWidth || th != imgHeight) {
            // 大小有变化，重建一张图片
            imgWidth = tw;
            imgHeight = th;
            bufferImage = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
            graphics = bufferImage.createGraphics();
        }

        // 清空图片
        clearBufferImage();

        // 画标题
        drawTitle();

        // 画内容区域 背景
        drawGraphBackgound();

        // 画缺省的Y坐标
        drawDefaultYAxis();

        // 画缺省的X坐标
        drawDefaultXAxis();

    }

    private void clearBufferImage() {
        graphics.setBackground(getBackground());
        graphics.clearRect(0, 0, imgWidth, imgHeight);
    }

    /**
     * <pre>
     * 将内容画在图片上
     * </pre>
     */
    public void drawImage() {
        if (coordinatelist == null || coordinatelist.isEmpty() || coordinatelist.size() < 2) {
            drawEmptyImage();
            return;
        }
        int tw = getWidth();
        int th = getHeight();
        boolean rePaint = false;
        if (tw != imgWidth || th != imgHeight) {
            rePaint = true;
            // 大小有变化，重建一张图片
            imgWidth = tw;
            imgHeight = th;
            bufferImage = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
            graphics = bufferImage.createGraphics();
        }
        if (bufferImage == null) {
            return;
        }
        // 内容有变化
        rePaint = true;

        if (!rePaint) {
            return;
        }

        // 先计算title的高度

        // 计算X 和 Y 的比例
        calcYRatio();
        calcXRatio();
        System.out.println("Y轴的比例是：" + yRatio);
        System.out.println("X轴的比例是：" + xRatio);

        // 先清空图片
        clearBufferImage();

        // 获取字体

        // int titleWidth = fm.stringWidth(title);
        // 画标题
        drawTitle();

        // 画内容区域 背景
        drawGraphBackgound();

        // 画Y坐标
        drawYAxis();

        // Font font = big.getFont();
        // FontMetrics fm = big.getFontMetrics(font);
        // int ascent = fm.getAscent();
        // int descent = fm.getDescent();
        // int fHeight = fm.getHeight();

        // 画X坐标
        // drawXAxis();
        drawXAxis2();

        // 画曲线
        drawGraph();

        // 面板 重绘
        // repaint();
    }

    /**
     * 是否限制最后一个值
     */
    private boolean displayLastValue = true;

    public void setDisplayLastValue(boolean display) {
        displayLastValue = display;
        if (display) {
            currentTextWidth = defaultCurrentTextWidth;
        } else {
            currentTextWidth = 0;
        }
    }

    public boolean getDisplayLastValue() {
        return displayLastValue;
    }

    /**
     * 绘制曲线
     */
    private void drawGraph() {
        graphics.setColor(graphColor);

        int size = coordinatelist.size();
        if (size < 2) {
            return;
        }
        int[] xPoints = new int[size];
        int[] yPoints = new int[size];

        for (int i = 0; i < size; i++) {
            xPoints[i] = getXPoint(coordinatelist.get(i).getxValue());
            yPoints[i] = getYPoint(coordinatelist.get(i).getyValue());
        }

        graphics.drawPolyline(xPoints, yPoints, size);

        // 画一个当前值
        Font font = graphics.getFont();
        FontMetrics fm = graphics.getFontMetrics(font);
        int descent = fm.getDescent();
        graphics.setColor(fontColor);
        int _yPoint = yPoints[size - 1];
        int graphWidth = getGraphWidth();
        int _xPoint = YTextWidth + graphWidth + 1;
        long currentValue = coordinatelist.get(coordinatelist.size() - 1).getyValue();
        String valueOf = String.valueOf(currentValue);
        graphics.drawString(valueOf, _xPoint, _yPoint - descent);
        graphics.drawLine(_xPoint, _yPoint, _xPoint + fm.stringWidth(valueOf), _yPoint);
    }

    /**
     * X轴上默认的线与线之间的像素
     */
    private int xAxisLineInterval = 80;

    private void drawDefaultXAxis() {
        int graphWidth = getGraphWidth();
        int graphHeight = getGraphHeight();
        // 每隔一定的像素画一条竖线
        graphics.setColor(lineColor);

        for (int i = YTextWidth + xAxisLineInterval; i < YTextWidth + graphWidth; i += xAxisLineInterval) {
            graphics.drawLine(i, titleHeight, i, titleHeight + graphHeight);
        }
    }

    /**
     * <pre>
     * 第二种方式画
     * </pre>
     */
    private void drawXAxis2() {

        Font font = graphics.getFont();
        FontMetrics fm = graphics.getFontMetrics(font);
        int ascent = fm.getAscent();
        // int descent = fm.getDescent();
        int fHeight = fm.getHeight();

        int graphWidth = getGraphWidth();
        int graphHeight = getGraphHeight();
        // 画X坐标
        // 在X轴能显示几条线
        int xLineCount = graphWidth / xAxisLineInterval;
        if (coordinatelist.size() <= xLineCount) {
            for (int i = 0; i < coordinatelist.size() - 1; i++) {
                long _xValue = coordinatelist.get(i).getxValue();
                int _xPoint = getXPoint(_xValue);
                Date date = new Date(_xValue);
                String dateStr = dateF.format(date);

                graphics.setColor(lineColor);
                graphics.drawLine(_xPoint, titleHeight, _xPoint, titleHeight + graphHeight);
                graphics.setColor(fontColor);
                graphics.drawString(dateStr, _xPoint, titleHeight + graphHeight + ascent);

                String dateStr2 = dateF2.format(date);
                graphics.drawString(dateStr2, _xPoint, titleHeight + graphHeight + ascent + fHeight);
            }
        } else {
            double per = (double) graphWidth / xAxisLineInterval;

            long xMinV = coordinatelist.get(0).getxValue();
            long xMaxV = coordinatelist.get(coordinatelist.size() - 1).getxValue();

            long xInterval = (long) ((xMaxV - xMinV) / per);

            for (int i = 0; i <= xLineCount; i++) {
                long _xValue = xMinV + (xInterval * i);
                int _xPoint = YTextWidth + (xAxisLineInterval * i);
                Date date = new Date(_xValue);
                String dateStr = dateF.format(date);

                graphics.setColor(lineColor);
                graphics.drawLine(_xPoint, titleHeight, _xPoint, titleHeight + graphHeight);
                graphics.setColor(fontColor);
                graphics.drawString(dateStr, _xPoint, titleHeight + graphHeight + ascent);

                String dateStr2 = dateF2.format(date);
                graphics.drawString(dateStr2, _xPoint, titleHeight + graphHeight + ascent + fHeight);
            }
        }

    }

    /**
     * <pre>
     * 画X坐标轴
     * </pre>
     */
    @SuppressWarnings("unused")
    private void drawXAxis() {
        Font font = graphics.getFont();
        FontMetrics fm = graphics.getFontMetrics(font);
        int ascent = fm.getAscent();
        // int descent = fm.getDescent();
        int fHeight = fm.getHeight();

        int graphWidth = getGraphWidth();
        int graphHeight = getGraphHeight();
        // 画X坐标
        // 每隔间隔像素
        int xLineCount = graphWidth / xAxisLineInterval;
        if (coordinatelist.size() <= xLineCount) {
            for (int i = 0; i < coordinatelist.size() - 1; i++) {
                long _xValue = coordinatelist.get(i).getxValue();
                int _xPoint = getXPoint(_xValue);
                Date date = new Date(_xValue);
                String dateStr = dateF.format(date);
                String dateStr2 = dateF2.format(date);

                graphics.setColor(lineColor);
                graphics.drawLine(_xPoint, titleHeight, _xPoint, titleHeight + graphHeight);
                graphics.setColor(fontColor);
                graphics.drawString(dateStr, _xPoint, titleHeight + graphHeight + ascent);
                graphics.drawString(dateStr2, _xPoint, titleHeight + graphHeight + ascent + fHeight);
            }
        } else {
            long xMinV = coordinatelist.get(0).getxValue();
            long xMaxV = coordinatelist.get(coordinatelist.size() - 1).getxValue();
            long xInterval = (xMaxV - xMinV) / xLineCount;

            for (int i = 0; i < xLineCount; i++) {
                long _xValue = xMinV + (xInterval * i);
                int _xPoint = getXPoint(_xValue);
                Date date = new Date(_xValue);
                String dateStr = dateF.format(date);
                String dateStr2 = dateF2.format(date);

                graphics.setColor(lineColor);
                graphics.drawLine(_xPoint, titleHeight, _xPoint, titleHeight + graphHeight);
                graphics.setColor(fontColor);
                graphics.drawString(dateStr, _xPoint, titleHeight + graphHeight + ascent);
                graphics.drawString(dateStr2, _xPoint, titleHeight + graphHeight + ascent + fHeight);
            }
        }
    }

    private void drawDefaultYAxis() {
        // 每隔一定的像素画一条线
        int graphWidth = getGraphWidth();
        int graphHeight = getGraphHeight();

        // 画横线
        graphics.setColor(lineColor);
        for (int i = graphHeight; i - bastYAxisLineHeightInterval > 0; i -= bastYAxisLineHeightInterval) {
            int yPosition = i - bastYAxisLineHeightInterval + titleHeight;
            graphics.drawLine(YTextWidth, yPosition, YTextWidth + graphWidth, yPosition);
        }
    }

    /**
     * <pre>
     * 画Y轴
     * </pre>
     */
    private void drawYAxis() {
        Font font = graphics.getFont();
        FontMetrics fm = graphics.getFontMetrics(font);
        int descent = fm.getDescent();
        // 先设置字体
        graphics.setFont(font);
        // 画最大值
        // String maxYStr = String.valueOf(maxY);
        // big.drawString(maxYStr, YTextWidth - fm.stringWidth(maxYStr) - 1,
        // titleHeight + descent);
        // // 画最小值
        // String minYStr = String.valueOf(minY);
        // big.drawString(minYStr, YTextWidth - fm.stringWidth(minYStr) - 1,
        // titleHeight + graphHeight);

        // 每隔一定的像素画一条线
        int graphWidth = getGraphWidth();

        for (int i = 0; i <= yAxisLineCount; i++) {
            long yNmuber = minY + (yAxisInterval * i);
            String yNumberStr = String.valueOf(yNmuber);
            int yPosition = getYPoint(yNmuber);
            if (YTextWidth > 0) {
                // 数字
                graphics.setColor(fontColor);
                graphics.drawString(yNumberStr, YTextWidth - fm.stringWidth(yNumberStr) - 1, yPosition + descent);
            }

            // 画横线
            graphics.setColor(lineColor);
            graphics.drawLine(YTextWidth, yPosition, YTextWidth + graphWidth, yPosition);
        }
    }

    /**
     * <pre>
     * 画背景
     * </pre>
     */
    private void drawGraphBackgound() {
        int graphWidth = getGraphWidth();
        int graphHeight = getGraphHeight();

        graphics.setColor(graphBackgroundColor);
        // big.fill3DRect(YTextWidth, titleHeight, imgWidth - YTextWidth
        // - cutlineWidth, imgHeight - titleHeight - XTextHeight, false);

        // 填充指定的矩形。该矩形左边缘和右边缘分别位于 x 和 x + width - 1。上边缘和下边缘分别位于 y 和 y + height -
        // 1。得到的矩形覆盖 width 像素宽乘以 height 像素高的区域。使用图形上下文的当前颜色填充该矩形。
        graphics.fillRect(YTextWidth, titleHeight, graphWidth, graphHeight);

        graphics.setColor(lineColor);
        // 绘制指定矩形的边框。矩形的左边缘和右边缘分别位于 x 和 x + width。上边缘和下边缘分别位于 y 和 y +
        // height。使用图形上下文的当前颜色绘制该矩形。
        graphics.drawRect(YTextWidth, titleHeight, graphWidth, graphHeight);

    }

    /**
     * <pre>
     * 画标题
     * </pre>
     */
    private void drawTitle() {
        if (title == null || title.isEmpty() || titleHeight < 1) {
            return;
        }
        graphics.setColor(titleFontColor);
        graphics.setFont(titleFont);
        FontMetrics fm = graphics.getFontMetrics(titleFont);
        int titleWidth = fm.stringWidth(title);

        int graphWidth = getGraphWidth();
        int titleX = (graphWidth - titleWidth) / 2 + YTextWidth;
        graphics.drawString(title, titleX, fm.getAscent());
    }

    @Override
    public void repaint() {

        super.repaint();
        System.out.println("重绘方法触发");
    }

    @Override
    public void paint(Graphics g) {

        super.paint(g);
        System.out.println("绘制方法触发");

        drawImage();
        // 用图片
        if (bufferImage != null)
            g.drawImage(bufferImage, 0, 0, this);

    }

}
