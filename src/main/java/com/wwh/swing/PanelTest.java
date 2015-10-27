package com.wwh.swing;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PanelTest {
    public static void main(String arg[]) {
        JFrame f = new JFrame();
        JPanel jp1 = new JPanel();
        JButton jb1 = new JButton("玻璃面板默认FlowLayout布局");
        JButton jb2 = new JButton("内容面板默认BorderLayout布局");
        JButton jb3 = new JButton("在内容面板之下的层面板");
        JButton jb4 = new JButton("根面板无默认布局");
        JButton jb5 = new JButton("在内容面板之上的层面板");
        JButton jb6 = new JButton("JFrame窗体");
        // 将JFrame所反回的面板都强制转换为JComponent类型，以便 调用setOpaque方法。
        JComponent p1 = (JComponent) f.getGlassPane();
        JComponent p2 = (JComponent) f.getContentPane();
        JComponent p3 = (JComponent) f.getLayeredPane();
        JComponent p4 = (JComponent) f.getRootPane();

        p1.setBackground(Color.red);
        p2.setBackground(Color.yellow);
        p3.setBackground(Color.blue);
        p4.setBackground(Color.green);

        p1.add(jb1);
        p2.add(jb2, BorderLayout.SOUTH);
        // 为层面板p3添加两个按钮，且分别放置在内容面板之上和内容面板之下
        p3.add(jb3, new Integer(-30001)); // 将按钮jb3，放置在内容面板之下
        p3.add(jb5, new Integer(22)); // 将按钮jb5，放置在内容面板之上
        jb5.setSize(200, 22);
        jb5.setLocation(7, 35);
        jb3.setSize(200, 22);
        jb3.setLocation(7, 90);
        // 向根面板p4中添加一个按钮jb4
        p4.add(jb4);
        jb4.setSize(200, 22);
        jb4.setLocation(7, 60);
        // f.add(jb6,BorderLayout.NORTH);//该方法实际上是把按钮jb5添加到了内容面板上，并未把jb5添加到窗体f上。
        f.setLocation(222, 222);
        f.setSize(222, 222);
        f.setVisible(true);

        // 循环显示各层面板
        while (true) {
            // 玻璃面板默认是不可见且透明的。显示玻璃面板红色背景。
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            p1.setVisible(true);
            p1.setOpaque(true);
            f.repaint(); // 应使用repaint方法重缓一次，要不然会出现组件上次显示的残影

            // 使玻璃面板透明，但是是可见的，这样的话就会显示内容面板和层次数目在内容面板之上的层面板的内容。
            //因为层面板自身层内之间是相互透明的，因此不会显示层面板的蓝色背景，相反内容面板是不透明的，因此会显示内容面板的背景颜色
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            p1.setOpaque(false);
            // p1.setVisible(false); //你也可以把该行的注释删除掉，以便观察可见性与透明性的区别。
            f.repaint();
            // 使内容面板透明，因为前面已把玻璃面板设为透明，因此会显示全部的层面板的内容，包括层面板的蓝色背景
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            p2.setOpaque(false);
            // p2.setVisible(false);
            p3.setOpaque(true);
            f.repaint();
            // 使层面板透明，因为内容面板是层面板中的某一层，因此该设置同样会使内容面板透明，再加上之前已把玻璃面板设为透明，因此最后将会显示即不透明也是可见的根面板的内容及其绿色的背景色。
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            p3.setOpaque(false);
            // p3.setVisible(false);
            f.repaint();
            // 使所有面板的可见性及透明性还原。
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            p1.setVisible(false);
            p2.setOpaque(true);
            p3.setOpaque(true);
            f.repaint();
        }
    }
}
