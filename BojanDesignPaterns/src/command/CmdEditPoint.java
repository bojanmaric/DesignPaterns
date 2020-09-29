package command;

import geometry.Point;
import geometry.Shape;

public class CmdEditPoint implements Command {

	private Point oldState;
	private Point newState;
	private Point originalState = new Point();

	public CmdEditPoint(Point oldState, Point newState) {
		this.oldState = oldState;
		this.newState = newState;
	}

	@Override
	public void execute() {
		originalState = oldState.clone();

		oldState.setX(newState.getX());
		oldState.setY(newState.getY());
		oldState.setForeground(newState.getForeground());
	}

	@Override
	public void unexecute() {
		oldState.setX(originalState.getX());
		oldState.setY(originalState.getY());
		oldState.setForeground(originalState.getForeground());
	}

	public String toString() {
		return "edit:" + originalState.toString() + ",to:" + newState;
	}
}
