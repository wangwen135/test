package com.wwh.test.swing.monitor;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;

public class MonitorPanelTest extends JFrame {

	private JPanel contentPane;

	private List<Coordinate> list;
	private MonitorPanel panel;
	private JButton btnNewButton_1;

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

	/**
	 * Create the frame.
	 */
	public MonitorPanelTest() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 644, 410);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		list = new ArrayList<Coordinate>();
		panel = new MonitorPanel(list);
		contentPane.add(panel, BorderLayout.CENTER);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);

		JButton btnNewButton = new JButton("启  动");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start();
			}
		});
		panel_1.add(btnNewButton);

		btnNewButton_1 = new JButton("停  止");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				run = false;
			}
		});
		panel_1.add(btnNewButton_1);
	}

	private boolean run = true;

	private void start() {
		run = true;
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				int rint = 100;
				while (run) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Random r = new Random();
					rint = r.nextInt(100);
					System.out.println(rint);
					if(list.size()>50){
						list.remove(0);
					}
					list.add(new Coordinate(System.currentTimeMillis(), rint +1000));
					panel.drawImage();
				}
			}
		});
		t.start();
	}
}
