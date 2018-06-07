package com.wwh.swing.monitor;

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

public class MonitorPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private SimpleDateFormat dateF = new SimpleDateFormat("HH:mm");
	private SimpleDateFormat dateF2 = new SimpleDateFormat("ss:SSS");

	private List<Coordinate> clist;

	public MonitorPanel() {

	}

	/**
	 * Create the panel.
	 */
	public MonitorPanel(List<Coordinate> list) {
		this.clist = list;
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
	 * 技术Y最大最小值
	 * </pre>
	 */
	private void calcMaxAndMinYValue() {
		// 先设置一次
		maxYValue = Long.MIN_VALUE;
		minYValue = Long.MAX_VALUE;
		for (Coordinate coordinate : clist) {
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
	 * <pre>
	 * 计算最大 、最小 Y轴值
	 * </pre>
	 */
	private void calcYAxis() {
		calcMaxAndMinYValue();

		long dValue = maxYValue - minYValue;
		if (dValue == 0) {
			maxY = maxYValue;
			minY = minYValue;
			return;
		}

		long interval = dValue / 10;// 显示10条线
		if (interval == 0)
			interval = 1;
		long log = (long) Math.log10(interval);// 计算位数
		long _tmp = (long) Math.pow(10, log);
		yAxisInterval = Math.round((double) interval / _tmp) * _tmp;

		long maxc = maxYValue / yAxisInterval;
		maxY = (maxc + 1) * yAxisInterval;
		long minc = minYValue / yAxisInterval;
		minY = minc * yAxisInterval;
		if (minY > minYValue) {
			minY = minY - yAxisInterval;
		}

		yAxisLineCount = (int) ((maxY - minY) / yAxisInterval);

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

	/**
	 * 显示当前值的文本宽度
	 */
	private int currentTextWidth = 50;

	/**
	 * Y轴上一个与像素点的比例
	 */
	private double yRatio;

	/**
	 * 曲线图的高度
	 */
	private int graphHeight;
	/**
	 * 曲线图的宽度
	 */
	private int graphWidth;

	/**
	 * <pre>
	 * 技术Y轴比例 
	 * 既：值与显示区域的像素的比例
	 * </pre>
	 */
	private void calcYRatio() {
		calcYAxis();
		// 计算曲线图的高度
		graphHeight = imgHeight - titleHeight - XTextHeight;

		if (graphHeight < 0) {
			yRatio = 0;
			return;
		}
		long yd = maxY - minY;// Y轴
		if (yd < 0) {
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

	private long maxXValue;

	/**
	 * <pre>
	 * 计算X轴的比例
	 * 根据list中的元素个数
	 * </pre>
	 */
	private void calcXRatio() {

		// 计算曲线图的宽度
		graphWidth = imgWidth - YTextWidth - currentTextWidth;
		if (graphWidth < 0) {
			xRatio = 0;
			return;
		}
		minXValue = clist.get(0).getxValue();
		// 这里使用list，如果list会改变需要注意
		maxXValue = clist.get(clist.size() - 1).getxValue();
		long xv = maxXValue - minXValue;

		xRatio = (double) graphWidth / (double) xv;// 得到比例
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
		return graphHeight - y + titleHeight;
	}

	/**
	 * 图片
	 */
	private BufferedImage bimage;
	private Graphics2D big;

	private Color backgroundColor = new Color(0, 100, 0);
	private Color lineColor = new Color(0, 0, 0);
	private Color graphColor = new Color(250, 0, 0);
	private Color fontColor = Color.BLACK;

	private int imgWidth;
	private int imgHeight;

	private String title = "测试标题";

	public void setGraphTitle(String title) {
		this.title = title;
	}

	/**
	 * <pre>
	 * 将内容画在图片上
	 * </pre>
	 */
	public void drawImage() {
		if (clist == null || clist.isEmpty()) {
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
			bimage = new BufferedImage(imgWidth, imgHeight,
					BufferedImage.TYPE_INT_RGB);
			big = bimage.createGraphics();
		}
		if (bimage == null) {
			return;
		}
		// 内容有变化
		rePaint = true;

		if (!rePaint) {
			return;
		}

		// 先计算title的高度

		// 技术X 和 Y 的比例
		calcYRatio();
		calcXRatio();
		System.out.println("Y轴的比例是：" + yRatio);
		System.out.println("X轴的比例是：" + xRatio);

		// 清空图片
		big.setBackground(getBackground());
		big.clearRect(0, 0, imgWidth, imgHeight);

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
		big.setColor(graphColor);

		int size = clist.size();
		if (size < 2) {
			return;
		}
		int[] xPoints = new int[size];
		int[] yPoints = new int[size];

		for (int i = 0; i < size; i++) {
			xPoints[i] = getXPoint(clist.get(i).getxValue());
			yPoints[i] = getYPoint(clist.get(i).getyValue());
		}

		big.drawPolyline(xPoints, yPoints, size);

		// 画一个当前值
		Font font = big.getFont();
		FontMetrics fm = big.getFontMetrics(font);
		int descent = fm.getDescent();
		big.setColor(fontColor);
		int _yPoint = yPoints[size - 1];
		int _xPoint = YTextWidth + graphWidth + 1;
		long currentValue = clist.get(clist.size() - 1).getyValue();
		String valueOf = String.valueOf(currentValue);
		big.drawString(valueOf, _xPoint, _yPoint - descent);
		big.drawLine(_xPoint, _yPoint, _xPoint + fm.stringWidth(valueOf),
				_yPoint);

		// 面板 重绘
		repaint();
	}

	/**
	 * X轴上默认的线与线之间的像素
	 */
	private int xAxisLineInterval = 80;

	/**
	 * <pre>
	 * 第二种方式画
	 * </pre>
	 */
	private void drawXAxis2() {

		Font font = big.getFont();
		FontMetrics fm = big.getFontMetrics(font);
		int ascent = fm.getAscent();
		// int descent = fm.getDescent();
		int fHeight = fm.getHeight();

		// 画X坐标
		// 在X轴能显示几条线
		int xLineCount = graphWidth / xAxisLineInterval;
		if (clist.size() <= xLineCount) {
			for (int i = 0; i < clist.size() - 1; i++) {
				long _xValue = clist.get(i).getxValue();
				int _xPoint = getXPoint(_xValue);
				Date date = new Date(_xValue);
				String dateStr = dateF.format(date);

				big.setColor(lineColor);
				big.drawLine(_xPoint, titleHeight, _xPoint, titleHeight
						+ graphHeight);
				big.setColor(fontColor);
				big.drawString(dateStr, _xPoint, titleHeight + graphHeight
						+ ascent);

				String dateStr2 = dateF2.format(date);
				big.drawString(dateStr2, _xPoint, titleHeight + graphHeight
						+ ascent + fHeight);
			}
		} else {
			double per = (double) graphWidth / xAxisLineInterval;

			long xMinV = clist.get(0).getxValue();
			long xMaxV = clist.get(clist.size() - 1).getxValue();

			long xInterval = (long) ((xMaxV - xMinV) / per);

			for (int i = 0; i <= xLineCount; i++) {
				long _xValue = xMinV + (xInterval * i);
				int _xPoint = YTextWidth + (xAxisLineInterval * i);
				Date date = new Date(_xValue);
				String dateStr = dateF.format(date);

				big.setColor(lineColor);
				big.drawLine(_xPoint, titleHeight, _xPoint, titleHeight
						+ graphHeight);
				big.setColor(fontColor);
				big.drawString(dateStr, _xPoint, titleHeight + graphHeight
						+ ascent);

				String dateStr2 = dateF2.format(date);
				big.drawString(dateStr2, _xPoint, titleHeight + graphHeight
						+ ascent + fHeight);
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
		Font font = big.getFont();
		FontMetrics fm = big.getFontMetrics(font);
		int ascent = fm.getAscent();
		// int descent = fm.getDescent();
		int fHeight = fm.getHeight();

		// 画X坐标
		// 每隔间隔像素
		int xLineCount = graphWidth / xAxisLineInterval;
		if (clist.size() <= xLineCount) {
			for (int i = 0; i < clist.size() - 1; i++) {
				long _xValue = clist.get(i).getxValue();
				int _xPoint = getXPoint(_xValue);
				Date date = new Date(_xValue);
				String dateStr = dateF.format(date);
				String dateStr2 = dateF2.format(date);

				big.setColor(lineColor);
				big.drawLine(_xPoint, titleHeight, _xPoint, titleHeight
						+ graphHeight);
				big.setColor(fontColor);
				big.drawString(dateStr, _xPoint, titleHeight + graphHeight
						+ ascent);
				big.drawString(dateStr2, _xPoint, titleHeight + graphHeight
						+ ascent + fHeight);
			}
		} else {
			long xMinV = clist.get(0).getxValue();
			long xMaxV = clist.get(clist.size() - 1).getxValue();
			long xInterval = (xMaxV - xMinV) / xLineCount;

			for (int i = 0; i < xLineCount; i++) {
				long _xValue = xMinV + (xInterval * i);
				int _xPoint = getXPoint(_xValue);
				Date date = new Date(_xValue);
				String dateStr = dateF.format(date);
				String dateStr2 = dateF2.format(date);

				big.setColor(lineColor);
				big.drawLine(_xPoint, titleHeight, _xPoint, titleHeight
						+ graphHeight);
				big.setColor(fontColor);
				big.drawString(dateStr, _xPoint, titleHeight + graphHeight
						+ ascent);
				big.drawString(dateStr2, _xPoint, titleHeight + graphHeight
						+ ascent + fHeight);
			}
		}
	}

	/**
	 * <pre>
	 * 画Y轴
	 * </pre>
	 */
	private void drawYAxis() {
		Font font = big.getFont();
		FontMetrics fm = big.getFontMetrics(font);
		int descent = fm.getDescent();
		// 先设置字体
		big.setFont(font);
		// 画最大值
		// String maxYStr = String.valueOf(maxY);
		// big.drawString(maxYStr, YTextWidth - fm.stringWidth(maxYStr) - 1,
		// titleHeight + descent);
		// // 画最小值
		// String minYStr = String.valueOf(minY);
		// big.drawString(minYStr, YTextWidth - fm.stringWidth(minYStr) - 1,
		// titleHeight + graphHeight);

		// 每隔一定的像素画一条线

		for (int i = 0; i <= yAxisLineCount; i++) {
			long _l = minY + (yAxisInterval * i);
			String _yStr = String.valueOf(_l);
			int _yPosition = getYPoint(_l);
			// 数字
			big.setColor(fontColor);
			big.drawString(_yStr, YTextWidth - fm.stringWidth(_yStr) - 1,
					_yPosition + descent);

			// 画横线
			big.setColor(lineColor);
			big.drawLine(YTextWidth, _yPosition, YTextWidth + graphWidth,
					_yPosition);
		}
	}

	/**
	 * <pre>
	 * 画背景
	 * </pre>
	 */
	private void drawGraphBackgound() {
		big.setColor(backgroundColor);
		// big.fill3DRect(YTextWidth, titleHeight, imgWidth - YTextWidth
		// - cutlineWidth, imgHeight - titleHeight - XTextHeight, false);

		// 填充指定的矩形。该矩形左边缘和右边缘分别位于 x 和 x + width - 1。上边缘和下边缘分别位于 y 和 y + height -
		// 1。得到的矩形覆盖 width 像素宽乘以 height 像素高的区域。使用图形上下文的当前颜色填充该矩形。
		big.fillRect(YTextWidth, titleHeight, graphWidth, graphHeight);

		big.setColor(lineColor);
		// 绘制指定矩形的边框。矩形的左边缘和右边缘分别位于 x 和 x + width。上边缘和下边缘分别位于 y 和 y +
		// height。使用图形上下文的当前颜色绘制该矩形。
		big.drawRect(YTextWidth, titleHeight, graphWidth, graphHeight);

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
		big.setColor(fontColor);
		// Font font = big.getFont();
		Font font = new Font("宋体", Font.BOLD, 14);
		big.setFont(font);
		// ascent = (int) fm.getAscent();
		// descent = (int) fm.getDescent();

		FontMetrics fm = big.getFontMetrics(font);
		int titleWidth = fm.stringWidth(title);

		int titleX = (graphWidth - titleWidth) / 2 + YTextWidth;
		big.drawString(title, titleX, fm.getAscent());
	}

	@Override
	public void paint(Graphics g) {

		super.paint(g);
		// 用图片
		if (bimage != null)
			g.drawImage(bimage, 0, 0, this);

	}

}
