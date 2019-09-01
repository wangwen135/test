package com.wwh.test.swing.monitor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.wwh.test.swing.monitor.MonitorPanelModel.XAxisMode;
import com.wwh.test.swing.monitor.MonitorPanelModel.YAxisMode;

public class MonitorPanelTest extends JFrame {

    private static final long serialVersionUID = 1L;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MonitorPanelTest frame = new MonitorPanelTest();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private JButton btn_clear1;
    private JButton btn_clear2;
    private JButton btn_clear3;
    private JButton btn_clear4;

    private JButton btn_start2;
    private JButton btn_start3;
    private JButton btn_start4;

    private JButton btn_stop1;
    private JButton btn_stop2;
    private JButton btn_stop3;
    private JButton btn_stop4;

    private JPanel contentPane;
    private MonitorPanel mPanel1;
    private MonitorPanel mPanel2;
    private MonitorPanel mPanel3;
    private MonitorPanel mPanel4;

    private MonitorPanelModel mpModel1;
    private MonitorPanelModel mpModel2;
    private MonitorPanelModel mpModel3;
    private MonitorPanelModel mpModel4;
    private JPanel panel_1;
    private JPanel panel_2;
    private JPanel panel_2_btn;
    private JPanel panel_3;
    private JPanel panel_3_btn;
    private JPanel panel_4;
    private JPanel panel_4_btn;
    private boolean runFlag1 = false;
    private boolean runFlag2 = false;
    private boolean runFlag3 = false;
    private boolean runFlag4 = false;

    /**
     * Create the frame.
     */
    public MonitorPanelTest() {
        setTitle("监控面板测试");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1183, 681);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new GridLayout(2, 2, 0, 0));

        createMonitorPanel1();

        createMonitorPanel2();

        createMonitorPanel3();

        createMonitorPanel4();

    }

    private void createMonitorPanel1() {
        panel_1 = new JPanel();
        contentPane.add(panel_1);
        panel_1.setLayout(new BorderLayout(0, 0));
        mpModel1 = new MonitorPanelModel();
        mPanel1 = new MonitorPanel(mpModel1);
        panel_1.add(mPanel1);

        mpModel1.setMaxCoordinateCount(200);

        mPanel1.setGraphTitle("0到10000之间的随机数");

        JPanel panel_1_btn = new JPanel();
        panel_1.add(panel_1_btn, BorderLayout.SOUTH);

        JButton btn_start1 = new JButton("启  动");
        btn_start1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                start1();
            }
        });
        panel_1_btn.add(btn_start1);

        btn_stop1 = new JButton("停  止");
        btn_stop1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                runFlag1 = false;
            }
        });
        panel_1_btn.add(btn_stop1);

        btn_clear1 = new JButton("清  空");
        btn_clear1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mpModel1.clearCoordinate();
            }
        });
        panel_1_btn.add(btn_clear1);
    }

    private void createMonitorPanel2() {
        panel_2 = new JPanel();
        contentPane.add(panel_2);
        panel_2.setLayout(new BorderLayout(0, 0));

        mpModel2 = new MonitorPanelModel();
        mpModel2.setxAxisMode(XAxisMode.KEEP_MOVE);
        mpModel2.setKeepMoveTimeInterval(10 * 60 * 1000L);

        mpModel2.setMaxCoordinateCount(600);

        mPanel2 = new MonitorPanel(mpModel2);

        mPanel2.setGraphTitle("内存图测试");
        mPanel2.setGraphBackgroundColor(new Color(38, 38, 38));
        mPanel2.setGridLineColor(new Color(0, 225, 34));
        mPanel2.setGraphLineColor(Color.YELLOW);

        panel_2.add(mPanel2);

        panel_2_btn = new JPanel();
        panel_2.add(panel_2_btn, BorderLayout.SOUTH);

        btn_start2 = new JButton("启  动");
        btn_start2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                start2();
            }
        });
        panel_2_btn.add(btn_start2);

        btn_stop2 = new JButton("停  止");
        btn_stop2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                runFlag2 = false;
            }
        });
        panel_2_btn.add(btn_stop2);

        btn_clear2 = new JButton("清  空");
        btn_clear2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mpModel2.clearCoordinate();
            }
        });
        panel_2_btn.add(btn_clear2);
    }

    private void createMonitorPanel3() {
        panel_3 = new JPanel();
        contentPane.add(panel_3);
        panel_3.setLayout(new BorderLayout(0, 0));

        mpModel3 = new MonitorPanelModel();
        mpModel3.setxAxisMode(XAxisMode.KEEP_MOVE);
        mpModel3.setKeepMoveTimeInterval(10 * 60 * 1000L);
        mpModel3.setMaxCoordinateCount(1200);

        mPanel3 = new MonitorPanel(mpModel3);
        mPanel3.setGraphTitle("递增递减");

        panel_3.add(mPanel3, BorderLayout.CENTER);

        panel_3_btn = new JPanel();
        panel_3.add(panel_3_btn, BorderLayout.SOUTH);

        btn_start3 = new JButton("启  动");
        btn_start3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                start3();
            }
        });
        panel_3_btn.add(btn_start3);

        btn_stop3 = new JButton("停  止");
        btn_stop3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                runFlag3 = false;
            }
        });
        panel_3_btn.add(btn_stop3);

        btn_clear3 = new JButton("清  空");
        btn_clear3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mpModel3.clearCoordinate();
            }
        });
        panel_3_btn.add(btn_clear3);

    }

    private void createMonitorPanel4() {

        panel_4 = new JPanel();
        contentPane.add(panel_4);
        panel_4.setLayout(new BorderLayout(0, 0));

        mpModel4 = new MonitorPanelModel();
        mpModel4.setxAxisMode(XAxisMode.KEEP_MOVE);
        mpModel4.setKeepMoveTimeInterval(360 * 1000L);

        mpModel4.setMaxCoordinateCount(720);

        mpModel4.setyAxisMode(YAxisMode.FIXED);
        mpModel4.setMaxYCoords(100L);
        mpModel4.setMinYCoords(-100L);
        mpModel4.setyAxisInterval(20L);

        mPanel4 = new MonitorPanel(mpModel4);
        panel_4.add(mPanel4, BorderLayout.CENTER);

        mPanel4.setGraphTitle("正弦函数");
        mPanel4.setGraphBackgroundColor(new Color(38, 38, 38));
        mPanel4.setGridLineColor(new Color(0, 225, 34));
        mPanel4.setGraphLineColor(Color.YELLOW);

        panel_4_btn = new JPanel();
        panel_4.add(panel_4_btn, BorderLayout.SOUTH);

        btn_start4 = new JButton("启  动");
        btn_start4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                start4();
            }
        });
        panel_4_btn.add(btn_start4);

        btn_stop4 = new JButton("停  止");
        btn_stop4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                runFlag4 = false;
            }
        });
        panel_4_btn.add(btn_stop4);

        btn_clear4 = new JButton("清  空");
        btn_clear4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mpModel4.clearCoordinate();
            }
        });
        panel_4_btn.add(btn_clear4);

    }

    private void start1() {
        runFlag1 = true;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                int rint;
                Random r = new Random();
                while (runFlag1) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    rint = r.nextInt(10000);
                    mpModel1.addCoordinate(new Coordinate(System.currentTimeMillis(), rint + 1));
                }
            }
        });
        t.start();
    }

    private void start2() {
        if (runFlag2) {
            return;
        }
        runFlag2 = true;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Runtime rt = Runtime.getRuntime();

                while (runFlag2) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mpModel2.addCoordinate(new Coordinate(System.currentTimeMillis(), rt.freeMemory() / 1024 / 1024));
                }
            }
        });
        t.start();
    }

    private void start3() {

        if (runFlag3) {
            return;
        }
        runFlag3 = true;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                boolean b = true;
                int i = 0;
                while (runFlag3) {
                    if (b) {
                        if (i < 100) {
                            i++;
                        } else {
                            b = !b;
                        }
                    } else {
                        if (i > 0) {
                            i--;
                        } else {
                            b = !b;
                        }
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mpModel3.addCoordinate(new Coordinate(System.currentTimeMillis(), i));
                }
            }
        });
        t.start();

    }

    private void start4() {
        if (runFlag4) {
            return;
        }
        runFlag4 = true;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                double d;
                long l;
                while (runFlag4) {
                    if (i == 360) {
                        i = 0;
                    }
                    d = Math.sin(Math.toRadians(i));
                    i++;
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    l = (long) (d * 100);
                    mpModel4.addCoordinate(new Coordinate(System.currentTimeMillis(), l));
                }
            }
        });
        t.start();
    }
}
