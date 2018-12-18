package GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import data.Data;
import utility.Utility;

public class Window {

	private final int WIDTH = 900, HEIGHT = 600;
	private final String TITLE = "Routenplaner";

	private JFrame frame;

	private int sourceInput, targetInput, distanceOutput, lastStartNodeID;

	private JTextField sourceTitle, source, targetTitle, target, resultWindow, sourceFile, questionFile, solutionFile;

	private JButton calculateButton, graphSourceFileButton, questionSourceFileButton, solutionSourceFileButton;

	public Window() {

		lastStartNodeID = -1;

		frame = new JFrame();
		frame.setSize(WIDTH, HEIGHT);
		frame.setResizable(false);
		frame.setTitle(TITLE);
		frame.setLocationRelativeTo(null);
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		addStuff();
		frame.setVisible(true);
	}

	private void addStuff() {

		sourceTitle = new JTextField("Source");
		sourceTitle.setBounds(75, 75, WIDTH - 150, 30);
		sourceTitle.setEditable(false);
		frame.getContentPane().add(sourceTitle);

		source = new JTextField();
		source.setBounds(75, 125, WIDTH - 150, 30);
		frame.getContentPane().add(source);

		targetTitle = new JTextField("Target");
		targetTitle.setBounds(75, 200, WIDTH - 150, 30);
		targetTitle.setEditable(false);
		frame.getContentPane().add(targetTitle);

		target = new JTextField();
		target.setBounds(75, 250, WIDTH - 150, 30);
		frame.getContentPane().add(target);

		calculateButton = new JButton("Calculate");
		calculateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				sourceInput = Integer.parseInt(source.getText());
				targetInput = Integer.parseInt(target.getText());
				distanceOutput = algorithm.Dijkstra.setSourceAndTarget(sourceInput, targetInput);

				resultWindow.setText("The Distance between " + sourceInput + " and " + targetInput + " is: "
						+ distanceOutput + ". Result calculated in : " + Utility.endTimer() + " seconds");

				if (sourceInput != lastStartNodeID) {
					Utility.addEmptyLineToFile(Utility.getLogFileWriter());
					Utility.addLineToFile("Dijkstra calculated from " + sourceInput + " in " + Utility.endTimer()
							+ " seconds." + System.lineSeparator(), Utility.getLogFileWriter());
					lastStartNodeID = sourceInput;
				}
				Utility.addLineToFile("Distance from " + sourceInput + " to " + targetInput + ": " + distanceOutput
						+ System.lineSeparator(), Utility.getLogFileWriter());
			}
		});
		calculateButton.setBounds(75, 300, 140, 30);
		frame.getContentPane().add(calculateButton);

		sourceFile = new JTextField(" ");
		sourceFile.setBounds(225, 300, 140, 30);
		sourceFile.setEditable(false);
		frame.getContentPane().add(sourceFile);

		graphSourceFileButton = new JButton(".fmi Sourcefile");
		graphSourceFileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("Choose a .fmi sourcefile: ");
				File workingDirectory = new File(System.getProperty("user.dir"));
				jfc.setCurrentDirectory(workingDirectory);
				FileNameExtensionFilter filter = new FileNameExtensionFilter(".fmi files", new String[] { "fmi" });
				jfc.setFileFilter(filter);
				jfc.addChoosableFileFilter(filter);
				int returnVal = jfc.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					Utility.setSourceFile(new File(jfc.getSelectedFile().getAbsolutePath()));
					sourceFile.setText(Utility.getSourceFile().getName());
				}
			}
		});
		graphSourceFileButton.setBounds(225, 350, 140, 30);
		frame.getContentPane().add(graphSourceFileButton);

		questionFile = new JTextField(" ");
		questionFile.setBounds(375, 300, 140, 30);
		questionFile.setEditable(false);
		frame.getContentPane().add(questionFile);

		questionSourceFileButton = new JButton(".que Sourcefile");
		questionSourceFileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("Choose a .que sourcefile: ");
				File workingDirectory = new File(System.getProperty("user.dir"));
				jfc.setCurrentDirectory(workingDirectory);
				FileNameExtensionFilter filter = new FileNameExtensionFilter(".que files", new String[] { "que" });
				jfc.setFileFilter(filter);
				jfc.addChoosableFileFilter(filter);
				int returnVal = jfc.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					Utility.setSourceFile(new File(jfc.getSelectedFile().getAbsolutePath()));
					questionFile.setText(Utility.getQuestionFile().getName());
					Data.readAndWrite();
				}
			}
		});
		questionSourceFileButton.setBounds(375, 350, 140, 30);
		frame.getContentPane().add(questionSourceFileButton);

		solutionFile = new JTextField(" ");
		solutionFile.setBounds(525, 300, 140, 30);
		solutionFile.setEditable(false);
		frame.getContentPane().add(solutionFile);

		solutionSourceFileButton = new JButton(".sol Sourcefile");
		solutionSourceFileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("Choose a .sol sourcefile: ");
				File workingDirectory = new File(System.getProperty("user.dir"));
				jfc.setCurrentDirectory(workingDirectory);
				FileNameExtensionFilter filter = new FileNameExtensionFilter(".sol files", new String[] { "sol" });
				jfc.setFileFilter(filter);
				jfc.addChoosableFileFilter(filter);
				int returnVal = jfc.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					Utility.setSolutionFile(new File(jfc.getSelectedFile().getAbsolutePath()));
					solutionFile.setText(Utility.getSolutionFile().getName());
				}
			}
		});
		solutionSourceFileButton.setBounds(525, 350, 140, 30);
		frame.getContentPane().add(solutionSourceFileButton);

		resultWindow = new JTextField();
		resultWindow.setBounds(75, 400, WIDTH - 150, 30);
		resultWindow.setEditable(false);
		resultWindow.setBackground(Color.WHITE);
		frame.getContentPane().add(resultWindow);
	}

}
