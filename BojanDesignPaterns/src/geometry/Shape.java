package geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

import observer.Observer;
import observer.Subject;

@SuppressWarnings("serial")
public abstract class Shape implements Comparable, Serializable {
	
	
	protected boolean selected;
	private Color foreground;
	
	private Observer observer;

	public Shape() {

	}
	
	public Color getForeground() {
		return foreground;
	}

	public void setForeground(Color color) {
		this.foreground= color;
	}
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
		//notifyObservers();
	}
	
	public String getForegroundText()
	{
		return "foreground["+foreground.getRed()+"."+foreground.getGreen()+"."+foreground.getBlue()+"]";
	}
	
	public void setObserver(Observer observer)
	{
		this.observer=observer;
	}

	public void removeObserver(Observer observer)
	{
		this.observer=null;
	}

	/*public void notifyObservers()
	{
		observer.update(int selectedCount);
	}*/

	public abstract void draw(Graphics g);
	public abstract void selected(Graphics g);
	public abstract boolean contains(int x, int y);
	public abstract Shape clone();
}
