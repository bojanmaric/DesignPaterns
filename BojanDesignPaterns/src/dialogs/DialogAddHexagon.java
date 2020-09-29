package dialogs;

import javax.swing.JDialog;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DialogAddHexagon extends JDialog{
	private JTextField tFRadius;
	private int radius;
	public DialogAddHexagon() {
		
		setSize(280, 200);
		setLocationRelativeTo(null);
		setModal(true);
		setResizable(false);
		setTitle("Radius for Hexagon");
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JButton btnOk = new JButton("OK");
		btnOk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					radius=Integer.parseInt(tFRadius.getText());
					if(radius>500 || radius<1) {
						throw new NumberFormatException();
					}
					dispose();
				}catch(NumberFormatException nfe)
					{
						JOptionPane.showMessageDialog(null, "Only numbers 1-500 allowed","Error",JOptionPane.ERROR_MESSAGE);
					}
			}
		});
		
		JLabel lblSetRadiusFor = new JLabel("Set radius for Hexagon");
		GridBagConstraints gbc_lblSetRadiusFor = new GridBagConstraints();
		gbc_lblSetRadiusFor.insets = new Insets(0, 0, 5, 0);
		gbc_lblSetRadiusFor.gridx = 1;
		gbc_lblSetRadiusFor.gridy = 0;
		getContentPane().add(lblSetRadiusFor, gbc_lblSetRadiusFor);
		
		JLabel lblRadius = new JLabel("Radius:");
		GridBagConstraints gbc_lblRadius = new GridBagConstraints();
		gbc_lblRadius.insets = new Insets(0, 0, 5, 5);
		gbc_lblRadius.anchor = GridBagConstraints.EAST;
		gbc_lblRadius.gridx = 0;
		gbc_lblRadius.gridy = 1;
		getContentPane().add(lblRadius, gbc_lblRadius);
		
		tFRadius = new JTextField();
		GridBagConstraints gbc_tFRadius = new GridBagConstraints();
		gbc_tFRadius.insets = new Insets(0, 0, 5, 0);
		gbc_tFRadius.fill = GridBagConstraints.HORIZONTAL;
		gbc_tFRadius.gridx = 1;
		gbc_tFRadius.gridy = 1;
		getContentPane().add(tFRadius, gbc_tFRadius);
		tFRadius.setColumns(10);
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.gridx = 1;
		gbc_btnOk.gridy = 2;
		getContentPane().add(btnOk, gbc_btnOk);
	}

	public int getRadius() {
		return radius;
	}
	
}
