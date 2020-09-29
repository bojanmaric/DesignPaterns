package command;

import java.util.Collections;

import mvc.DrawModel;
import geometry.Shape;

public class CmdToBack implements Command{

	private DrawModel model;
	private Shape s;
	private int position;
	
	public CmdToBack(DrawModel model,Shape s)
	{
		this.model=model;
		this.s=s;
	}
	
	@Override
	public void execute() {
		
		this.position=model.getAll().indexOf(s);
		Collections.swap(model.getAll(), position,position-1);
	}

	@Override
	public void unexecute() {
		this.position=model.getAll().indexOf(s);
		Collections.swap(model.getAll(),position,position+1);
	}
	
	public String toString()
	{
		return "toback:"+s.toString();
	}



}
