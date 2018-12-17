package GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import utility.Utility;

public class Window {

	private final int WIDTH = 900, HEIGHT = 600;
	private final String TITLE = "Routenplaner";

	private JFrame frame;

	private int sourceInput, targetInput, distanceOutput, lastStartNodeID;

	private JTextField sourceTitle, source, targetTitle, target, resultWindow;

	private JButton calculateButton, benchmarkButton;

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
					Utility.addEmptyLineToOutputFile();
					Utility.addLineToOutputFile("Dijkstra calculated from " + sourceInput + " in " + Utility.endTimer()
							+ " seconds." + System.lineSeparator());
					lastStartNodeID = sourceInput;
				}
				Utility.addLineToOutputFile("Distance from " + sourceInput + " to " + targetInput + ": "
						+ distanceOutput + System.lineSeparator());
			}
		});
		calculateButton.setBounds(WIDTH / 2 - 70, 350, 140, 30);
		frame.getContentPane().add(calculateButton);

		/*
		 * benchmarkButton = new JButton("Benchmark");
		 * benchmarkButton.addActionListener(new ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent arg0) {
		 * resultWindow.setText("Differences: " + Utility.benchmark()); } });
		 * benchmarkButton.setBounds(WIDTH / 2 - 70, 350, 140, 30);
		 * frame.getContentPane().add(benchmarkButton);
		 */

		resultWindow = new JTextField();
		resultWindow.setBounds(75, 400, WIDTH - 150, 30);
		resultWindow.setEditable(false);
		resultWindow.setBackground(Color.WHITE);
		frame.getContentPane().add(resultWindow);
	}

}
