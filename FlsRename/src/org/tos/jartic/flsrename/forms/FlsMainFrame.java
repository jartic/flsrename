package org.tos.jartic.flsrename.forms;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.tos.jartic.flsrename.proccessor.FlsProcessor;

public class FlsMainFrame extends JFrame {
	private Logger logger = Logger.getLogger(FlsMainFrame.class.getName());
	//
	static private int BOR = 10;
	static private int DELAY = 1000;
	//
	FlsProcessor processor = new FlsProcessor();
	//
	private JPanel mainPanel = new JPanel();
	private JTextField editFile = new JTextField("c:\\temp\\rename.txt");
	private JTextField editPath = new JTextField("c:\\temp\\rename");
	private JButton btnRun = new JButton();
	private JButton btnSetFile = new JButton();
	private JButton btnSetPath = new JButton();
	private JProgressBar pb = new JProgressBar();
	private Task task;

	private class Task extends Thread {
		public Task() {
		}

		public void run() {

			for (int i = 0; i < processor.cntTotal; i++) {
				processor.process(i);
				int percent = i;
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						pb.setValue(percent + 1);
					}
				});
				try {
					logger.log(Level.INFO, "wating ...");
					Thread.sleep(DELAY);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			JOptionPane.showMessageDialog(null, "Complete!");
		}
	}

	public boolean initFrame() {
		//
		mainPanel.setBorder(BorderFactory.createEmptyBorder(BOR, BOR, BOR, BOR));
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(Box.createVerticalGlue());
		//
		btnRun.setText("Run");
		btnSetFile.setText("File");
		btnSetPath.setText("Path");
		editFile.setMaximumSize(new Dimension(Integer.MAX_VALUE, editFile.getPreferredSize().height));
		mainPanel.add(editFile);
		mainPanel.add(Box.createVerticalGlue());
		mainPanel.add(btnSetFile);
		mainPanel.add(Box.createVerticalGlue());
		editPath.setMaximumSize(new Dimension(Integer.MAX_VALUE, editPath.getPreferredSize().height));
		mainPanel.add(editPath);
		mainPanel.add(Box.createVerticalGlue());
		mainPanel.add(btnSetPath);
		mainPanel.add(Box.createVerticalGlue());
		mainPanel.add(btnRun);
		mainPanel.add(Box.createVerticalGlue());
		mainPanel.add(pb);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(mainPanel, BorderLayout.CENTER);
		this.setPreferredSize(new Dimension(260, 225));
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		//
		btnSetFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(FlsMainFrame.this);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					editFile.setText(file.getAbsolutePath());
					logger.log(Level.INFO, "Opening: " + file.getName());
				} else {
					logger.log(Level.INFO, "Open command cancelled by user");
				}
			}
		});
		//
		btnSetPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fc.showOpenDialog(FlsMainFrame.this);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					editPath.setText(file.getAbsolutePath());
					logger.log(Level.INFO, "Opening: " + file.getName());
				} else {
					logger.log(Level.INFO, "Open command cancelled by user");
				}
			}
		});
		//
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pb.setIndeterminate(false);
				pb.setStringPainted(true);
				pb.setMinimum(0);
				pb.setMaximum(0);
				pb.setValue(0);
				//
				String fileName = "";
				fileName = editFile.getText();
				if (fileName == null) {
					return;
				}
				String pathName = "";
				pathName = editPath.getText();
				if (pathName == null) {
					return;
				}
				//
				if (fileName.equals("") || pathName.equals("")) {
					return;
				}
				//
				processor.rebuild(fileName, pathName);
				pb.setMaximum(processor.cntTotal);
				pb.setValue(0);
				logger.log(Level.INFO, "Total " + processor.cntTotal + " file was founded");
				//
				task = new Task();
				task.start();
			}
		});
		//
		return true;
	}

	public boolean runMainLogic() {
		//
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame.setDefaultLookAndFeelDecorated(true);
				initFrame();
			}
		});
		//
		return true;
	}

}
