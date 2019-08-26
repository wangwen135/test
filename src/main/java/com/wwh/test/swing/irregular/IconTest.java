package com.wwh.test.swing.irregular;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class IconTest extends JFrame {

    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    IconTest frame = new IconTest();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public IconTest() {

//        ImageIcon image = new ImageIcon(IconTest.class.getResource("/image/et.ico"));

//        
//        Image img = image.getImage();
        // img = img.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        // image.setImage(img);

//        setIconImage(img);

        Image img2 = Toolkit.getDefaultToolkit().getImage(IconTest.class.getResource("/image/success.png"));

        setIconImage(img2);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

    }

}
