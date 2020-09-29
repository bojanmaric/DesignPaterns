package command;

import java.io.Serializable;

import geometry.Shape;

public interface Command extends Serializable {
	void execute();
	void unexecute();
	
}
