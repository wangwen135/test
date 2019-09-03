package com.wwh.test.swing.monitor;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
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
public class MonitorPanel extends JPanel implements MonitorPanelModelListener {

    /**
     * 最合适的Y轴线条高度间距
     */
    private static final int bastYAxisLineHeightInterval = 26;

    /**
     * X轴上默认的线与线之间的像素
     */
    public static final int DEFAULT_X_AXIS_LINE_INTERVAL = 70;
    /**
     * 默认的当前值文本宽度
     */
    private static final int defaultCurrentTextWidth = 50;

    /**
     * 最少的Y轴线条数量
     */
    private static final int minYAxisLineCount = 3;

    private static final long serialVersionUID = 1L;

    /**
     * 图片
     */
    private BufferedImage bufferImage;

    /**
     * 显示当前值的文本宽度
     */
    private int currentTextWidth = 50;

    private MonitorPanelModel dataModel;

    private SimpleDateFormat dateF = new SimpleDateFormat("HH:mm");

    /**
     * 是否显示最后一个值
     */
    private boolean displayLastValue = true;

    private boolean drawEmptyGraph = true;

    private Color fontColor = Color.BLACK;

    /**
     * 曲线区域的背景颜色
     */
    private Color graphBackgroundColor = new Color(0, 100, 0);

    private Graphics2D graphics;

    /**
     * 曲线的颜色
     */
    private Color graphLineColor = new Color(250, 0, 0);

    private String graphTitle = "监控面板";

    private Color gridLineColor = new Color(0, 0, 0);
    /**
     * 图片的高度
     */
    private int imgHeight;

    /**
     * 图片的宽度，整个组件就是一个画上去的图片
     */
    private int imgWidth;

    /**
     * 标题字体
     */
    private Font titleFont = new Font("宋体", Font.BOLD, 14);

    /**
     * 标题颜色
     */
    private Color titleFontColor = Color.BLACK;

    /**
     * 标题的高度
     */
    private int titleHeight = 20;

    /**
     * X轴上的线与线之间的像素
     */
    private int xAxisLineInterval = DEFAULT_X_AXIS_LINE_INTERVAL;

    /**
     * X轴上的一个与像素点的比例
     */
    private double xRatio;

    /**
     * X轴的文本高度
     */
    private int XTextHeight = 20;

    /**
     * Y轴上一个与像素点的比例
     */
    private double yRatio;

    /**
     * Y轴的文本宽度
     */
    private int YTextWidth = 40;

    public MonitorPanel() {
        this(null);
    }

    public MonitorPanel(MonitorPanelModel model) {
        if (model == null) {
            model = new MonitorPanelModel();
        }
        setModel(model);
        updateUI();
    }

    /**
     * <pre>
     * 计算X轴的比例
     * </pre>
     */
    private void calcXRatio() {
        Long maxXCoords = dataModel.getMaxXCoords();
        if (maxXCoords == null) {
            return;
        }
        Long minXCoords = dataModel.getMinXCoords();
        if (minXCoords == null) {
            return;
        }
        // 曲线图的宽度
        int graphWidth = getGraphWidth();
        if (graphWidth <= 0) {
            xRatio = 0;
            return;
        }
        long xv = maxXCoords - minXCoords;
        // 得到比例
        xRatio = (double) graphWidth / (double) xv;
    }

    /**
     * <pre>
     * 计算Y轴比例 
     * 既：值与显示区域的像素的比例
     * </pre>
     */
    private void calcYRatio() {
        Long maxYCoords = dataModel.getMaxYCoords();
        if (maxYCoords == null) {
            return;
        }
        Long minYCoords = dataModel.getMinYCoords();
        if (minYCoords == null) {
            return;
        }
        // 计算曲线图的高度
        int graphHeight = getGraphHeight();

        if (graphHeight <= 0) {
            yRatio = 0;
            return;
        }
        long yd = maxYCoords - minYCoords;// Y轴
        if (yd <= 0) {
            yRatio = 0;
            return;
        }
        yRatio = (double) graphHeight / (double) yd;// 得到比例
    }

    private void clearBufferImage() {
        graphics.setBackground(getBackground());
        graphics.clearRect(0, 0, imgWidth, imgHeight);
    }

    private void drawDefaultXAxis() {
        int graphWidth = getGraphWidth();
        int graphHeight = getGraphHeight();
        // 每隔一定的像素画一条竖线
        graphics.setColor(gridLineColor);

        for (int i = YTextWidth + xAxisLineInterval; i < YTextWidth + graphWidth; i += xAxisLineInterval) {
            graphics.drawLine(i, titleHeight, i, titleHeight + graphHeight);
        }
    }

    private void drawDefaultYAxis() {
        // 每隔一定的像素画一条线
        int graphWidth = getGraphWidth();
        int graphHeight = getGraphHeight();

        // 画横线
        graphics.setColor(gridLineColor);
        for (int i = graphHeight; i - bastYAxisLineHeightInterval > 0; i -= bastYAxisLineHeightInterval) {
            int yPosition = i - bastYAxisLineHeightInterval + titleHeight;
            graphics.drawLine(YTextWidth, yPosition, YTextWidth + graphWidth, yPosition);
        }
    }

    /**
     * 绘制曲线
     */
    private void drawGraph() {
        graphics.setColor(graphLineColor);
        List<Coordinate> coordinatelist = dataModel.getCoordinatelist();
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

        graphics.setColor(gridLineColor);
        // 绘制指定矩形的边框。矩形的左边缘和右边缘分别位于 x 和 x + width。上边缘和下边缘分别位于 y 和 y +
        // height。使用图形上下文的当前颜色绘制该矩形。
        graphics.drawRect(YTextWidth, titleHeight, graphWidth, graphHeight);

    }

    /**
     * <pre>
     * 将内容画在图片上
     * </pre>
     */
    private void drawImage() {
        int tw = getWidth();
        int th = getHeight();
        if (tw != imgWidth || th != imgHeight) {
            // 大小有变化，重建一张图片
            imgWidth = tw;
            imgHeight = th;
            bufferImage = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
            graphics = bufferImage.createGraphics();
        }
        if (bufferImage == null) {
            return;
        }

        // 先清空图片
        clearBufferImage();

        // 画标题
        drawTitle();

        // 画内容区域 背景
        drawGraphBackgound();

        // 获取XY轴上的线
        long[] xLine = dataModel.getXAxisLines(getBestXAxisLineCount());
        long[] yLine = dataModel.getYAxisLines(getBestYAxisLineCount());

        // 计算X 和 Y 的比例
        calcYRatio();
        calcXRatio();

        // 画X坐标
        if (xLine != null) {
            drawXAxis(xLine);
        } else {
            if (drawEmptyGraph) {
                // 画缺省的X坐标
                drawDefaultXAxis();
            }
        }

        // 画Y坐标
        if (yLine != null) {
            drawYAxis(yLine);
        } else if (drawEmptyGraph) {
            // 画缺省的Y坐标
            drawDefaultYAxis();
        }

        // 画曲线
        drawGraph();
    }

    /**
     * <pre>
     * 画标题
     * </pre>
     */
    private void drawTitle() {
        if (graphTitle == null || graphTitle.isEmpty() || titleHeight < 1) {
            return;
        }
        graphics.setColor(titleFontColor);
        graphics.setFont(titleFont);
        FontMetrics fm = graphics.getFontMetrics(titleFont);
        int titleWidth = fm.stringWidth(graphTitle);

        int graphWidth = getGraphWidth();
        int titleX = (graphWidth - titleWidth) / 2 + YTextWidth;
        graphics.drawString(graphTitle, titleX, fm.getAscent());
    }

    /**
     * <pre>
     * 画X轴
     * </pre>
     */
    private void drawXAxis(long[] xLine) {
        if (xLine == null) {
            return;
        }

        Font font = graphics.getFont();
        FontMetrics fm = graphics.getFontMetrics(font);
        int ascent = fm.getAscent();
        // int descent = fm.getDescent();
        // int fHeight = fm.getHeight();

        int graphHeight = getGraphHeight();

        // 画X坐标
        for (long l : xLine) {
            int xPoint = getXPoint(l);

            graphics.setColor(gridLineColor);
            graphics.drawLine(xPoint, titleHeight, xPoint, titleHeight + graphHeight);

            Date date = new Date(l);
            String dateStr = dateF.format(date);

            graphics.setColor(fontColor);
            graphics.drawString(dateStr, xPoint, titleHeight + graphHeight + ascent);

        }
    }

    /**
     * <pre>
     * 画Y轴
     * </pre>
     */
    private void drawYAxis(long[] yLine) {
        if (yLine == null) {
            return;
        }

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

        int graphWidth = getGraphWidth();

        for (long l : yLine) {
            String yNumberStr = String.valueOf(l);
            int yPosition = getYPoint(l);
            if (YTextWidth > 0) {
                // 数字
                graphics.setColor(fontColor);
                graphics.drawString(yNumberStr, YTextWidth - fm.stringWidth(yNumberStr) - 1, yPosition + descent);
            }

            // 画横线
            graphics.setColor(gridLineColor);
            graphics.drawLine(YTextWidth, yPosition, YTextWidth + graphWidth, yPosition);

        }
    }

    private int getBestXAxisLineCount() {
        // 在X轴能显示几条线
        int lineCount = getGraphWidth() / xAxisLineInterval;
        lineCount = lineCount > 1 ? lineCount : 1;
        return lineCount;
    }

    /**
     * 根据坐标系高度计算最佳的横线条数
     * 
     * @return
     */
    private int getBestYAxisLineCount() {
        int lineCount = getGraphHeight() / bastYAxisLineHeightInterval;
        lineCount = lineCount > minYAxisLineCount ? lineCount : minYAxisLineCount;
        return lineCount;
    }

    public int getCurrentTextWidth() {
        return currentTextWidth;
    }

    public boolean getDisplayLastValue() {
        return displayLastValue;
    }

    public Color getFontColor() {
        return fontColor;
    }

    public Color getGraphBackgroundColor() {
        return graphBackgroundColor;
    }

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

    public Color getGraphLineColor() {
        return graphLineColor;
    }

    public String getGraphTitle() {
        return graphTitle;
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

    public Color getGridLineColor() {
        return gridLineColor;
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
        Long minXCoords = dataModel.getMinXCoords();
        long v = value - minXCoords;
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
        Long minYCoords = dataModel.getMinYCoords();
        long v = value - minYCoords;
        int y = (int) (v * yRatio);
        int graphHeight = getGraphHeight();
        return graphHeight - y + titleHeight;
    }

    public boolean isDrawEmptyGraph() {
        return drawEmptyGraph;
    }

    @Override
    public void monitorPanelChanged(MonitorPanelModelEvent e) {
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        drawImage();
        // 用图片
        if (bufferImage != null) {
            g.drawImage(bufferImage, 0, 0, this);
        }
    }

    public void setCurrentTextWidth(int currentTextWidth) {
        this.currentTextWidth = currentTextWidth;
    }

    public void setDisplayLastValue(boolean display) {
        displayLastValue = display;
        if (display) {
            currentTextWidth = defaultCurrentTextWidth;
        } else {
            currentTextWidth = 0;
        }
    }

    public void setDrawEmptyGraph(boolean drawEmptyGraph) {
        this.drawEmptyGraph = drawEmptyGraph;
    }

    public void setFontColor(Color fontColor) {
        this.fontColor = fontColor;
    }

    public void setGraphBackgroundColor(Color graphBackgroundColor) {
        this.graphBackgroundColor = graphBackgroundColor;
    }

    public void setGraphLineColor(Color graphLineColor) {
        this.graphLineColor = graphLineColor;
    }

    public void setGraphTitle(String title) {
        this.graphTitle = title;
    }

    public void setGridLineColor(Color gridLineColor) {
        this.gridLineColor = gridLineColor;
    }

    public void setModel(MonitorPanelModel model) {
        if (model == null) {
            throw new IllegalArgumentException("不能为空");
        }
        if (this.dataModel != model) {
            MonitorPanelModel old = this.dataModel;
            if (old != null) {
                old.removeMonitorPanelModelListener(this);
            }
            this.dataModel = model;
            model.addMonitorPanelModelListener(this);
            repaint();
        }
    }

}
