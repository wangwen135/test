package com.wwh.test.utils;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 添加时间到文件的第一列
 * 
 * @author 313921
 *
 */
public class AddTime2File extends JFrame {

	private static final long serialVersionUID = 525761611551075082L;

	private static final Logger logger = LoggerFactory
			.getLogger(AddTime2File.class);

	private JPanel contentPane;
	private JTextField txtf_oldFile;
	private JTextField txtf_newFile;

	private JFileChooser jfchooser;

	private File oldFile;

	private JTextArea textArea;

	private JFormattedTextField formattedTextField;

	private JSpinner spinner;

	private JComboBox cmb_timeUnit;

	private JComboBox cmb_format;

	private JCheckBox ckb_titleLine;

	/**
	 * 
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddTime2File frame = new AddTime2File();
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
	public AddTime2File() {
		setTitle("文件处理");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 683, 583);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(10, 200));
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("给文件的添加上时间，按照一定的间隔");
		lblNewLabel.setBounds(10, 10, 513, 15);
		panel.add(lblNewLabel);

		JLabel label = new JLabel("开始时间：");
		label.setBounds(10, 49, 77, 15);
		panel.add(label);

		formattedTextField = new JFormattedTextField(new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss"));

		formattedTextField.setValue(new Date());

		formattedTextField.setBounds(110, 46, 127, 21);
		panel.add(formattedTextField);

		JLabel label_1 = new JLabel("间隔：");
		label_1.setBounds(258, 49, 54, 15);
		panel.add(label_1);

		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1),
				null, new Integer(1)));
		spinner.setBounds(313, 45, 83, 22);
		panel.add(spinner);

		JLabel label_2 = new JLabel("时间单位：");
		label_2.setBounds(418, 49, 77, 15);
		panel.add(label_2);

		cmb_timeUnit = new JComboBox();
		cmb_timeUnit.setModel(new DefaultComboBoxModel(new TimeUnit[] {
				TimeUnit.MILLISECONDS, TimeUnit.SECONDS, TimeUnit.MINUTES,
				TimeUnit.HOURS, TimeUnit.DAYS }));
		cmb_timeUnit.setSelectedIndex(1);
		//

		cmb_timeUnit.setBounds(498, 46, 120, 21);
		panel.add(cmb_timeUnit);

		JLabel lblNewLabel_1 = new JLabel("需要处理的文件：");
		lblNewLabel_1.setBounds(10, 127, 120, 15);
		panel.add(lblNewLabel_1);

		txtf_oldFile = new JTextField();
		txtf_oldFile.setEditable(false);
		txtf_oldFile.setBounds(140, 124, 355, 21);
		panel.add(txtf_oldFile);
		txtf_oldFile.setColumns(10);

		JButton btn_chooseFile = new JButton("浏览");
		btn_chooseFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (jfchooser == null) {
					jfchooser = new JFileChooser();
				}
				int returnVal = jfchooser.showOpenDialog(AddTime2File.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					oldFile = jfchooser.getSelectedFile();

					txtf_oldFile.setText(oldFile.getAbsolutePath());

					// 设置新的文件名
					txtf_newFile.setText(oldFile.getAbsolutePath() + ".new");
				}
			}
		});
		btn_chooseFile.setBounds(525, 123, 93, 23);
		panel.add(btn_chooseFile);

		JLabel label_3 = new JLabel("输出的新文件：");
		label_3.setBounds(10, 171, 120, 15);
		panel.add(label_3);

		txtf_newFile = new JTextField();
		txtf_newFile.setEditable(false);
		txtf_newFile.setColumns(10);
		txtf_newFile.setBounds(140, 168, 355, 21);
		panel.add(txtf_newFile);

		final JButton btn_worked = new JButton("处理");
		btn_worked.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (oldFile == null) {
					JOptionPane.showMessageDialog(AddTime2File.this, "请先选择文件");
					return;
				}

				if (!oldFile.exists()) {
					JOptionPane
							.showMessageDialog(AddTime2File.this, "选择的文件不存在");
					return;
				}

				final File newFile = new File(oldFile.getParentFile(), oldFile
						.getName() + ".new");

				// 开始时间
				Date startDate = (Date) formattedTextField.getValue();
				final long startMillis = startDate.getTime();// 开始的毫秒数
				// 间隔
				int interval = (Integer) spinner.getValue();
				// 时间单位
				TimeUnit tu = (TimeUnit) cmb_timeUnit.getSelectedItem();

				// 日期输出格式
				String format = (String) cmb_format.getSelectedItem();
				SimpleDateFormat sdf = null;

				try {
					sdf = new SimpleDateFormat(format);
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(AddTime2File.this,
							"给定的日期格式是错的", "错误", JOptionPane.ERROR_MESSAGE);
					return;
				}
				final SimpleDateFormat fsdf = sdf;

				// 是否标题
				final boolean isTitle = ckb_titleLine.isSelected();

				final long intervalMillis = tu.toMillis(interval);

				textArea.setText("");
				btn_worked.setEnabled(false);// 禁用按钮

				new SwingWorker<String, String>() {

					@Override
					protected String doInBackground() throws Exception {

						BufferedReader br = null;
						BufferedWriter bw = null;
						try {
							newFile.createNewFile();
							br = new BufferedReader(new FileReader(oldFile));
							bw = new BufferedWriter(new FileWriter(newFile));

							StringBuffer sbuf;
							long start = startMillis;
							if (isTitle) {
								String titleLine = br.readLine();

								sbuf = new StringBuffer();
								sbuf.append("时间 ");
								sbuf.append(titleLine);
								sbuf.append("\r\n");
								publish(sbuf.toString());
								bw.write(sbuf.toString());
							}
							String line;
							int i = 0;
							while ((line = br.readLine()) != null) {
								i++;
								if (i % 1000 == 0) {
									// 休眠一下让界面有反应
									Thread.sleep(10);
								}

								start += intervalMillis;
								sbuf = new StringBuffer();
								sbuf.append(fsdf.format(new Date(start)));

								sbuf.append(" ");// 空格
								sbuf.append(line);
								sbuf.append("\r\n");// 换行符号

								publish(sbuf.toString());

								bw.write(sbuf.toString());
							}
							bw.flush();

						} catch (IOException e1) {
							logger.error("IO操作异常", e1);
							publish("IO操作异常：\n");
							StackTraceElement[] ste = e1.getStackTrace();
							for (StackTraceElement stackTraceElement : ste) {
								publish(stackTraceElement.toString());
								publish("\n");
							}

						} finally {
							if (br != null) {
								try {
									br.close();
								} catch (IOException e1) {
									logger.error("流关闭异常", e1);
									publish("流关闭异常");
								}
							}

							if (bw != null) {
								try {
									bw.close();
								} catch (IOException e1) {
									logger.error("流关闭异常", e1);
									publish("流关闭异常");
								}
							}
						}
						return null;
					}

					protected void process(java.util.List<String> chunks) {
						for (String string : chunks) {
							textArea.append(string);
						}
					};

					protected void done() {
						JOptionPane
								.showMessageDialog(AddTime2File.this, "处理完成");

						btn_worked.setEnabled(true);
					};

				}.execute();

			}
		});
		btn_worked.setBounds(525, 167, 93, 23);
		panel.add(btn_worked);

		JLabel label_4 = new JLabel("插入时间格式：");
		label_4.setBounds(10, 80, 93, 15);
		panel.add(label_4);

		cmb_format = new JComboBox();
		cmb_format.setModel(new DefaultComboBoxModel(new String[] {
				"yyyy-MM-dd_HH:mm:ss", "yyyy-MM-dd HH:mm:ss",
				"yyyyMMdd HHmmss", "yyyy-MM-dd", "HH:mm:ss" }));
		cmb_format.setSelectedIndex(0);
		cmb_format.setEditable(true);
		cmb_format.setBounds(110, 77, 146, 21);
		panel.add(cmb_format);

		ckb_titleLine = new JCheckBox("是否包含标题行");
		ckb_titleLine.setSelected(true);
		ckb_titleLine.setBounds(277, 76, 139, 23);
		panel.add(ckb_titleLine);

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
	}
}
