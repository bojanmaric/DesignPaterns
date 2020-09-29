package save;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import mvc.DrawModel;

public class SaveDrawing implements Save {

	private DrawModel model;
	
	public SaveDrawing(DrawModel model) {
		this.model=model;
	}
	
	@Override
	public void save() {
		JFileChooser saveDialog = new JFileChooser(); 
		saveDialog.setDialogTitle("Choose save destination");
		if (saveDialog.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) { 
			
			File choosenFile = new File(saveDialog.getSelectedFile().getAbsolutePath());

			if (choosenFile.exists()) { 
				JOptionPane.showMessageDialog(null, "Filename already exists!", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				FileOutputStream fos;// upis u fajl
				
				ObjectOutputStream oos;//objekata u fajl
				try { 
					fos = new FileOutputStream(choosenFile);
					oos = new ObjectOutputStream(fos);

					oos.writeObject(model.getAll());
					oos.close();
					fos.close();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
