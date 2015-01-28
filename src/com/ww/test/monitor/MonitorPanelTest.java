package com.ww.test.monitor;

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

		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start();
			}
		});
		panel_1.add(btnNewButton);
	}

	private void start() {
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				int rint = 100;
				while (true) {
					try {
						Thread.sleep(rint + 900);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Random r = new Random();
					rint = r.nextInt(100);
					System.out.println(rint);
					list.add(new Coordinate(System.currentTimeMillis(), rint));
					panel.drawImage();
				}
			}
		});
		t.start();
	}
}
