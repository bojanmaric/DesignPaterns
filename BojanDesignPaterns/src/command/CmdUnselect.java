package command;

import geometry.Shape;

public class CmdUnselect implements Command {
	Shape s;
	
	public CmdUnselect(Shape shape) {
		this.s = shape;
	}

	@Override
	public void execute() {
		s.setSelected(false);
		
	}

	@Override
	public void unexecute() {
		s.setSelected(true);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "unselected:" + s.toString();
	}
}
