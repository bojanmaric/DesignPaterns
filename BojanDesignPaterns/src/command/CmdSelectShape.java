package command;


import geometry.Shape;

public class CmdSelectShape implements Command{

	private Shape  s;

	
	public CmdSelectShape(Shape s) {
		
		this.s = s;
		
	}

	@Override
	public void execute() {

		this.s.setSelected(true);
		
		
	}

	@Override
	public void unexecute() {

			s.setSelected(false);
		
		
	}
	
	public String toString() {
		return "select:" + s.toString();
	}

}
