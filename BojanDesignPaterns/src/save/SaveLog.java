package save;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class SaveLog implements Save{
	
	private ArrayList<String>dlm;
	
	public SaveLog(ArrayList<String>dlm) {
		this.dlm=dlm;
	}

	@Override
	public void save() {
		JFileChooser saveDialog = new JFileChooser();
		saveDialog.setDialogTitle("Choose save destination");
		if (saveDialog.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			File chosenFile = new File(saveDialog.getSelectedFile().getAbsolutePath() + ".txt");

			if (chosenFile.exists()) {
				JOptionPane.showMessageDialog(null, "Filename already exists!", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				try {
					PrintWriter pw = new PrintWriter(chosenFile);
					
					for (int i = 0; i < dlm.size(); i++) {
						pw.println(dlm.get(i));
					}
					pw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
