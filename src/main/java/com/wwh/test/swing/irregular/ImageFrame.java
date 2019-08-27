package com.wwh.test.swing.irregular;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class ImageFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private ImageIcon icon;
    private Point origin = new Point();; // 用于移动窗体

    // 小弹出框
    private PopupDialog popupDialog;

    public ImageFrame(ImageIcon icon2) {

        this.icon = icon2;
        JLabel imageLabel = new JLabel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paint(Graphics g) {
                super.paint(g);
                icon.paintIcon(this, g, 0, 0);
            }
        };
        this.add(imageLabel);

        this.setUndecorated(true); // 关键语句1 不启用窗体装饰

        this.setSize(icon.getIconWidth(), icon.getIconHeight()); // 设置窗口大小

        // 这个需要包的支持
        // AWTUtilities.setWindowOpaque(this, false);

        // JDK 1.7 版本以上，使用 this.setBackground(new Color(0, 0, 0, 0));
        this.setBackground(new Color(0, 0, 0, 0)); // 关键句2 设置alpha值为0，完全透明

        // 设置透明度
        this.setOpacity(0.8f);

        this.setLocationRelativeTo(null); // 设置窗口居中
        // setVisible(true);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 鼠标事件监听
        // 由于取消了默认的窗体结构，所以我们要手动设置一下移动窗体的方法
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                // 按下的位置，拖拽是相对于这个位置来的
                origin.x = e.getX();
                origin.y = e.getY();
            }

            // 窗体上单击鼠标右键关闭程序
            public void mouseClicked(MouseEvent e) {
                if (popupDialog != null) {
                    popupDialog.showDelayHide();
                } else {
                    JOptionPane.showMessageDialog(ImageFrame.this, "点击了");
                }

                if (e.getButton() == MouseEvent.BUTTON3)
                    System.exit(0);
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point p = getLocation();
                setLocation(p.x + e.getX() - origin.x, p.y + e.getY() - origin.y);
            }
        });

        // 启用拖拽支持
        drag(imageLabel);

        // 设置 任务栏/窗口 的图标
        Image img2 = Toolkit.getDefaultToolkit().getImage(IconTest.class.getResource("/image/success.png"));
        setIconImage(img2);
    }

    /**
     * 定义的拖拽方法
     * 
     * @param c
     */
    public void drag(Component c) {
        // c 表示要接受拖拽的控件
        new DropTarget(c, DnDConstants.ACTION_COPY_OR_MOVE, new DropTargetAdapter() {
            @Override
            public void drop(DropTargetDropEvent dtde)// 重写适配器的drop方法
            {
                try {
                    if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor))// 如果拖入的文件格式受支持
                    {
                        dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);// 接收拖拽来的数据
                        @SuppressWarnings("unchecked")
                        List<File> list = (List<File>) (dtde.getTransferable()
                                .getTransferData(DataFlavor.javaFileListFlavor));
                        String temp = "处理这些文件:\n";
                        for (File file : list)
                            temp += file.getAbsolutePath() + ";\n";
                        JOptionPane.showMessageDialog(null, temp);
                        dtde.dropComplete(true);// 指示拖拽操作已完成
                    } else {
                        dtde.rejectDrop();// 否则拒绝拖拽来的数据
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public PopupDialog getPopupDialog() {
        return popupDialog;
    }

    public void setPopupDialog(PopupDialog popupDialog) {
        this.popupDialog = popupDialog;
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ImageIcon icon = new ImageIcon(ImageFrame.class.getResource("/image/柳岩.png"));
                    ImageFrame frame = new ImageFrame(icon);

                    ImageIcon icon2 = new ImageIcon(ImageFrame.class.getResource("/image/success.png"));
                    PopupDialog pd = new PopupDialog(frame, icon2, 2000);
                    frame.setPopupDialog(pd);

                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
