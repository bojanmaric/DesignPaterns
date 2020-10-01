package mvc;

import javax.swing.JFrame;

public class Application {

	public static void main(String[] args) {
		DrawModel model= new DrawModel();
		DrawFrame frame= new DrawFrame();
		frame.getView().setModel(model);
		
		DrawController controller= new DrawController(model,frame);
		frame.setController(controller);
		frame.setSize(1000,800);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
	}

}
