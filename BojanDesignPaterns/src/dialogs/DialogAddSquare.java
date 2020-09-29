package dialogs;

import javax.swing.JDialog;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DialogAddSquare extends JDialog{
	
	public DialogAddSquare() {
		setTitle("Size Square");
		setSize(280, 200);
		setLocationRelativeTo(null);
		setResizable(false);
		setModal(true);
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
					side=Integer.parseInt(fSide.getText());
					if(side>500 || side<1)
						throw new NumberFormatException();
					dispose();
				} catch ( NumberFormatException nfe) {
					JOptionPane.showMessageDialog(null,"Only numbers 1-500 allowed","Error",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		JLabel lblSetSizeOf = new JLabel("Set size of side for Square");
		GridBagConstraints gbc_lblSetSizeOf = new GridBagConstraints();
		gbc_lblSetSizeOf.insets = new Insets(0, 0, 5, 0);
		gbc_lblSetSizeOf.gridx = 1;
		gbc_lblSetSizeOf.gridy = 0;
		getContentPane().add(lblSetSizeOf, gbc_lblSetSizeOf);
		
		JLabel lblSide = new JLabel("Side:");
		GridBagConstraints gbc_lblSide = new GridBagConstraints();
		gbc_lblSide.insets = new Insets(0, 0, 5, 5);
		gbc_lblSide.anchor = GridBagConstraints.EAST;
		gbc_lblSide.gridx = 0;
		gbc_lblSide.gridy = 1;
		getContentPane().add(lblSide, gbc_lblSide);
		
		fSide = new JTextField();
		GridBagConstraints gbc_fSide = new GridBagConstraints();
		gbc_fSide.insets = new Insets(0, 0, 5, 0);
		gbc_fSide.fill = GridBagConstraints.HORIZONTAL;
		gbc_fSide.gridx = 1;
		gbc_fSide.gridy = 1;
		getContentPane().add(fSide, gbc_fSide);
		fSide.setColumns(10);
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.gridx = 1;
		gbc_btnOk.gridy = 2;
		getContentPane().add(btnOk, gbc_btnOk);
	}

	private int side;
	private JTextField fSide;
	

	public int getSide() {
		return side;
	}
}
