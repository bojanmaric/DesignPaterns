package command;

import mvc.DrawModel;
import geometry.Shape;

public class CmdBringToBack implements Command {

	private DrawModel model;
	private Shape s;
	private int position;
	
	public CmdBringToBack(DrawModel model,Shape s) {
		this.model=model;
		this.s=s;
		this.position=model.getAll().indexOf(s);
	}
	
	@Override
	public void execute() {
		
		model.remove(s);
		model.getAll().add(0, s);
		
	}

	@Override
	public void unexecute() {
		
		model.remove(s);
		model.getAll().add(position, s);
	}
	
	public String toString()
	{
		return "bringtoback:"+s.toString();
	}

}
