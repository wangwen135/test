package com.wwh.test.swing.irregular;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ImageFrame extends JFrame {
    int Frame_Width = 500;
    int Frame_Height = 500;
    ImageIcon icon = new ImageIcon(ImageFrame.class.getResource("/image/running.png"));

    public ImageFrame() {
        JLabel backLabel = new JLabel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                icon.paintIcon(this, g, 0, 0);
            }
        };
        this.add(backLabel);
        setUndecorated(true); // 关键语句1 不启用窗体装饰

        setSize(Frame_Width, Frame_Height); // 设置窗口大小

        // 这个需要包的支持
        // AWTUtilities.setWindowOpaque(this, false);

        // JDK 1.7 版本以上，使用 this.setBackground(new Color(0, 0, 0, 0));
        this.setBackground(new Color(0, 0, 0, 0)); // 关键句2

        setLocationRelativeTo(null); // 设置窗口居中
        // setVisible(true);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public static void main(String[] args) {
        ImageFrame frame = new ImageFrame();
        System.out.println(frame.icon.getIconHeight());

        frame.setSize(500, 500);
        frame.setVisible(true);
    }

}
