package command;

import geometry.Shape;

public class CmdDeselectShape implements  Command {
	//private DrawingController controller;
	private Shape s;
	
	public CmdDeselectShape( Shape s) {
		//this.controller = controller;
		this.s = s;
	}
	
	@Override
	public void execute() {
		s.setSelected(false);
		
	}

	@Override
	public void unexecute() {
		s.setSelected(true);
		
	}

	public String toString() {
		return "deselect:" + s.toString();
	}


}
