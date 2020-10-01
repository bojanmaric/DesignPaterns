package command;

import geometry.Shape;
import mvc.DrawModel;

public class CmdDeleteShape implements Command{

	private Shape s;
	private DrawModel model;
	private int oldPosition;
	
	public CmdDeleteShape(Shape s, DrawModel model) {
		
		this.s = s;
		this.model = model;
	}

	@Override
	public void execute() {
		oldPosition=model.getAll().indexOf(s);
		model.getAll().remove(s);
		
	}

	@Override
	public void unexecute() {
		if(!s.isSelected()) {
			s.setSelected(true);
		}
	
		model.getAll().add(oldPosition, s);
		
	}
	public String toString()
	{
		return "delete:"+s.toString();
	}
}
