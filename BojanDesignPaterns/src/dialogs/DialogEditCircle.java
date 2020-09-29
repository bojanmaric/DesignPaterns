package dialogs;

import java.awt.Color;

import javax.swing.JDialog;

import geometry.Circle;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JColorChooser;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DialogEditCircle extends JDialog{
	

	private int centerX;
	private int centerY;
	private int radius;
	private Color foreground;
	private Color background;
	private JTextField xCenter;
	private JTextField yCenter;
	private JTextField fRadius;
	private JButton foreButton;
	private JButton backButton;
	public DialogEditCircle(Circle c) {

		setSize(270, 250);
		setLocationRelativeTo(null);
		setModal(true);
		setResizable(false);
		setTitle("Edit - Circle");

		foreground=c.getForeground();
		background=c.getBackground();
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lblEditCircle = new JLabel("Edit Circle");
		GridBagConstraints gbc_lblEditCircle = new GridBagConstraints();
		gbc_lblEditCircle.insets = new Insets(0, 0, 5, 0);
		gbc_lblEditCircle.gridx = 1;
		gbc_lblEditCircle.gridy = 0;
		getContentPane().add(lblEditCircle, gbc_lblEditCircle);
		
		JLabel lblXcenter = new JLabel("X-Center:");
		GridBagConstraints gbc_lblXcenter = new GridBagConstraints();
		gbc_lblXcenter.anchor = GridBagConstraints.EAST;
		gbc_lblXcenter.insets = new Insets(0, 0, 5, 5);
		gbc_lblXcenter.gridx = 0;
		gbc_lblXcenter.gridy = 1;
		getContentPane().add(lblXcenter, gbc_lblXcenter);
		
		xCenter = new JTextField();
		xCenter.setText(String.valueOf(c.getCenter().getX()));
		GridBagConstraints gbc_xCenter = new GridBagConstraints();
		gbc_xCenter.insets = new Insets(0, 0, 5, 0);
		gbc_xCenter.fill = GridBagConstraints.HORIZONTAL;
		gbc_xCenter.gridx = 1;
		gbc_xCenter.gridy = 1;
		getContentPane().add(xCenter, gbc_xCenter);
		xCenter.setColumns(10);
		
		JLabel lblYcenter = new JLabel("Y-Center:");
		GridBagConstraints gbc_lblYcenter = new GridBagConstraints();
		gbc_lblYcenter.anchor = GridBagConstraints.EAST;
		gbc_lblYcenter.insets = new Insets(0, 0, 5, 5);
		gbc_lblYcenter.gridx = 0;
		gbc_lblYcenter.gridy = 2;
		getContentPane().add(lblYcenter, gbc_lblYcenter);
		
		yCenter = new JTextField();
		yCenter.setText(String.valueOf(c.getCenter().getY()));
		GridBagConstraints gbc_yCenter = new GridBagConstraints();
		gbc_yCenter.insets = new Insets(0, 0, 5, 0);
		gbc_yCenter.fill = GridBagConstraints.HORIZONTAL;
		gbc_yCenter.gridx = 1;
		gbc_yCenter.gridy = 2;
		getContentPane().add(yCenter, gbc_yCenter);
		yCenter.setColumns(10);
		
		JLabel lblRadius = new JLabel("Radius:");
		GridBagConstraints gbc_lblRadius = new GridBagConstraints();
		gbc_lblRadius.anchor = GridBagConstraints.EAST;
		gbc_lblRadius.insets = new Insets(0, 0, 5, 5);
		gbc_lblRadius.gridx = 0;
		gbc_lblRadius.gridy = 3;
		getContentPane().add(lblRadius, gbc_lblRadius);
		
		fRadius = new JTextField();
		fRadius.setText(String.valueOf(c.getRadius()));
		GridBagConstraints gbc_fRadius = new GridBagConstraints();
		gbc_fRadius.insets = new Insets(0, 0, 5, 0);
		gbc_fRadius.fill = GridBagConstraints.HORIZONTAL;
		gbc_fRadius.gridx = 1;
		gbc_fRadius.gridy = 3;
		getContentPane().add(fRadius, gbc_fRadius);
		fRadius.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Foreground");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 4;
		getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		
		foreButton = new JButton("");
		foreButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Color previousForeground = foreground;
				JColorChooser jcc = new JColorChooser();
				foreground = jcc.showDialog(null, "Choose foreground color", foreground);

				if (foreground == null) {
					foreground = previousForeground;
				}

				foreButton.setBackground(foreground);
			}
		});
		foreButton.setBackground(foreground);
		foreButton.setPreferredSize(new Dimension(40, 40));
		GridBagConstraints gbc_foreButton = new GridBagConstraints();
		gbc_foreButton.insets = new Insets(0, 0, 5, 0);
		gbc_foreButton.gridx = 1;
		gbc_foreButton.gridy = 4;
		getContentPane().add(foreButton, gbc_foreButton);
		
		JLabel lblNewLabel_1 = new JLabel("Background");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 5;
		getContentPane().add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		backButton = new JButton("");
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Color previousBackground = background;
				JColorChooser jcc = new JColorChooser();
				background = jcc.showDialog(null, "Choose background color", background);

				if (background == null) {
					background = previousBackground;
				}

				backButton.setBackground(background);
			}
		});
		backButton.setBackground(background);
		backButton.setPreferredSize(new Dimension(40, 40));
		GridBagConstraints gbc_backButton = new GridBagConstraints();
		gbc_backButton.insets = new Insets(0, 0, 5, 0);
		gbc_backButton.gridx = 1;
		gbc_backButton.gridy = 5;
		getContentPane().add(backButton, gbc_backButton);
		
		JButton btnOk = new JButton("OK");
		btnOk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					centerX=Integer.parseInt(xCenter.getText());
					centerY=Integer.parseInt(yCenter.getText());
					radius=Integer.parseInt(fRadius.getText());
					if (centerX < 1 || centerX > 750 || centerY < 1 || centerY > 520 || radius < 1 || radius > 400) {
						throw new NumberFormatException();
					}
					dispose();
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(DialogEditCircle.this,
							"Only numbers 1-750 for X, 1-520 for Y and 1-400 for radius allowed", "Warrning",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.gridx = 1;
		gbc_btnOk.gridy = 6;
		getContentPane().add(btnOk, gbc_btnOk);
		

		foreground = c.getForeground();
		background = c.getBackground();

		
		
	}

	
	
	public int getCenterX() {
		return centerX;
	}
	public int getCenterY() {
		return centerY;
	}
	public int getRadius() {
		return radius;
	}
	public Color getForeground() {
		return foreground;
	}
	public Color getBackground() {
		return background;
	}
}
