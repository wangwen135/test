package com.wwh.test.swing.irregular;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.Timer;

public class PopupDialog extends JDialog {

    private static final long serialVersionUID = 1L;

    private ImageIcon icon;

    private Frame owner;

    private Timer timer;

    private void initTimer(int delayTime) {
        // 定时消失
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                System.out.println("延时隐藏弹框执行了");
                setVisible(false);
            }
        };
        timer = new Timer(delayTime, taskPerformer);
        timer.setRepeats(false);
    }

    /**
     * Create the dialog.
     */
    public PopupDialog(Frame owner, ImageIcon icon2, int delayTime) {
        super(owner);
        this.icon = icon2;
        this.owner = owner;

        initTimer(delayTime);

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
        this.setBackground(new Color(0, 0, 0, 0)); // 关键句2

        this.setLocationRelativeTo(owner);

        this.setDefaultCloseOperation(HIDE_ON_CLOSE);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("隐藏弹框");

                PopupDialog.this.setVisible(false);

            }
        });
    }

    /**
     * 先显示再延期隐藏
     */
    public void showDelayHide() {
        // 重新定位
        this.setLocationRelativeTo(owner);
        setVisible(true);
        timer.start();
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        if (!b) {
            timer.stop();
        }
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            ImageIcon icon = new ImageIcon(ImageFrame.class.getResource("/image/success.png"));
            PopupDialog dialog = new PopupDialog(null, icon, 10000);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            // dialog.setVisible(true);
            dialog.showDelayHide();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
