package com.ww.test.monitor;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JPanel;

public class MonitorPanel extends JPanel {

	private static final long serialVersionUID = 1L;

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
	 * <pre>
	 * 计算最大 、最小 Y轴值
	 * </pre>
	 */
	private void calcYAxis() {
		calcMaxAndMinYValue();
		maxY = maxYValue;
		minY = minYValue;

		// long dValue = maxYValue - minYValue;
		// // 上面差1/10
		// long l = dValue / 10;
		// maxY = maxYValue + l;
		// // 下面差1/20
		// l = l / 2;
		// minY = minYValue - l;
	}

	/**
	 * Y轴的文本宽度
	 */
	private int YTextWidth = 80;
	/**
	 * X轴的文本高度
	 */
	private int XTextHeight = 80;

	/**
	 * 标题的高度
	 */
	private int titleHeight = 25;

	/**
	 * 图例的宽度
	 */
	private int cutlineWidth = 0;

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
		// 获取曲线图的高度
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

		// 获取曲线的宽度
		graphWidth = imgWidth - YTextWidth - cutlineWidth;
		if (graphWidth < 0) {
			xRatio = 0;
			return;
		}
		minXValue = clist.get(0).getxValue();
		// 这里使用list，如果list会改变需要注意
		maxXValue = clist.get(clist.size() - 1).getxValue();
		long xv = maxXValue - minXValue;

		xRatio = (double) graphWidth / xv;// 得到比例
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
	private Color mfColor = new Color(0, 100, 0);
	private int imgWidth;
	private int imgHeight;

	private String title = "测试标题";

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

		// 技术X 和 Y 的比例
		calcXRatio();
		calcYRatio();

		// 清空图片
		big.setBackground(getBackground());
		big.clearRect(0, 0, imgWidth, imgHeight);
		// 画标题
		drawTitle();
		// 画X坐标

		// 画竖线

		// 画Y坐标

		// 画横线

		// 画图例

		// 画内容区域
		big.setColor(mfColor);
		big.fill3DRect(YTextWidth, titleHeight, imgWidth - YTextWidth
				- cutlineWidth, imgHeight - titleHeight - XTextHeight, false);
		big.setColor(Color.RED);

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

		// 重绘
		repaint();
	}

	private void drawTitle() {
		if (title == null || title.isEmpty() || titleHeight < 1) {
			return;
		}
		big.setColor(Color.BLACK);
		// Font font = big.getFont();
		Font font = new Font("宋体", Font.BOLD, 14);
		big.setFont(font);
		// ascent = (int) fm.getAscent();
		// descent = (int) fm.getDescent();

		FontMetrics fm = big.getFontMetrics(font);
		int titleWidth = fm.stringWidth(title);

		int titleX = (graphWidth - titleWidth) / 2 + YTextWidth;
		big.drawString(title, titleX, fm.getHeight());
	}

	@Override
	public void paint(Graphics g) {

		super.paint(g);
		// 用图片
		if (bimage != null)
			g.drawImage(bimage, 0, 0, this);

	}

}
