package mvc;

import java.awt.Graphics;
import java.util.ListIterator;

import javax.swing.JPanel;

import geometry.Shape;
import mvc.DrawModel;


public class DrawView extends JPanel {

	private DrawModel model;
	
	public void setModel(DrawModel model) {
		this.model=model;
	}
	public void paint(Graphics g)
	{
		super.paint(g);
		ListIterator<Shape> it = model.getAll().listIterator();
		while(it.hasNext()) {
			it.next().draw(g);
		}
		
	}
	public DrawModel getModel() {
		return model;
	}
	
}
