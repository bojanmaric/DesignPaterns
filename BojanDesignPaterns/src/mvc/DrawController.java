package mvc;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Stack;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


import command.CmdAdd;
import command.CmdBringToBack;
import command.CmdBringToFront;
import command.CmdDeleteShape;
import command.CmdEditCircle;
import command.CmdEditHexagon;
import command.CmdEditLine;
import command.CmdEditPoint;
import command.CmdEditRectangle;
import command.CmdEditSquare;
import command.CmdSelectShape;
import command.CmdToBack;
import command.CmdToFront;
import command.CmdUnselect;
import command.Command;
import dialogs.DialogAddCircle;
import dialogs.DialogAddHexagon;
import dialogs.DialogAddRectangle;
import dialogs.DialogAddSquare;
import dialogs.DialogEditCircle;
import dialogs.DialogEditHexagon;
import dialogs.DialogEditLine;
import dialogs.DialogEditPoint;
import dialogs.DialogEditRectangle;
import dialogs.DialogEditSquare;
import geometry.Circle;
import geometry.HexagonAdapter;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;
import geometry.Square;

import observer.SelectedCount;
import observer.SelectedUpdate;
import save.SaveDrawing;
import save.SaveLog;
import save.SaveManager;


public class DrawController {
	
	private CmdAdd cmdAddShape;
	private CmdSelectShape cmdSelectShape;
	private CmdDeleteShape cmdDeleteShape;
	private CmdEditPoint cmdEditPoint;
	private CmdEditLine cmdEditLine;
	private CmdEditRectangle cmdEditRectangle;
	private CmdEditSquare cmdEditSquare;
	private CmdEditCircle cmdEditCircle;
	private CmdEditHexagon cmdEditHexagon;
	private CmdToFront cmdToFront;
	private CmdToBack cmdToBack;
	private CmdBringToBack cmdBringToBack;
	private CmdBringToFront cmdBringToFront;
	private DrawModel model;
	private DrawFrame frame;
	private ArrayList<Shape> selectedShapes;
	private ArrayList<String> logCommands;
	private Stack<Command> commandStack;
	private Stack<Command> redoStack;
	private int counterLine=1;
	private SelectedCount selectedCount;
	private Point startPoint;

	private FileReader fr;
	private BufferedReader bf;

	
	public DrawController(DrawModel model, DrawFrame frame) {
		this.model=model;
		this.frame=frame;
		this.selectedCount=new SelectedCount();//Observer
		SelectedUpdate selectedCountUpdate= new SelectedUpdate(frame);
		this.selectedCount.setObserver(selectedCountUpdate);
		commandStack = new Stack<Command>();
		redoStack = new Stack<Command>();
		logCommands = new ArrayList<String>();
		selectedShapes = new ArrayList<Shape>();
	}
	
	public void select(MouseEvent click) {
		boolean foundShape=false;
		Collections.reverse(model.getAll());
		for(Shape s:model.getAll()) {
			if(s.contains(click.getX(), click.getY())) {
				if(!s.isSelected()) {
					s.setSelected(true);
					selectedShapes.add(s);	
					executeCommand(new CmdSelectShape(s));	
					this.selectedCount.setSelectedCount(model.getSelectedCount()); 
					//frame.getView().repaint();
				}
				if(model.getSelectedCount()==1) {
					if(model.getAll().indexOf(s)==0){	
					
						frame.getBtnBringToFront().setEnabled(false);
						frame.getBtnToFront().setEnabled(false);
				
					}else if(model.getAll().indexOf(s)==model.getAll().size()-1) {
						frame.getBtnToBack().setEnabled(false);
						frame.getBtnBringToBack().setEnabled(false);
						frame.getBtnBringToFront().setEnabled(true);
						frame.getBtnToFront().setEnabled(true);
					
					}else {
						frame.getBtnToBack().setEnabled(true);
						frame.getBtnBringToBack().setEnabled(true);
						frame.getBtnBringToFront().setEnabled(true);
						frame.getBtnToFront().setEnabled(true);
					}
				}else {
					frame.getBtnToBack().setEnabled(false);
					frame.getBtnBringToBack().setEnabled(false);
					frame.getBtnBringToFront().setEnabled(false);
					frame.getBtnToFront().setEnabled(false);
				}
				foundShape=true;
				break;
			}
			
		}
		
		
		if(!foundShape) {
						for(Shape s:model.getAll()) {
				if(s.isSelected()) {
					s.setSelected(false);
					selectedShapes.remove(s);
					executeCommand(new CmdUnselect(s));
					this.selectedCount.setSelectedCount(model.getSelectedCount()); 
				}
			}
			frame.getBtnToBack().setEnabled(false);
			frame.getBtnBringToBack().setEnabled(false);
			frame.getBtnBringToFront().setEnabled(false);
			frame.getBtnToFront().setEnabled(false);
			
		}
	
		frame.getView().repaint();
		Collections.reverse(model.getAll());
		
	}
	
	public void drawPoint(MouseEvent click, Color foreground, Color background) {
		
			CmdAdd cmdAdd=new CmdAdd(new Point(click.getX(),click.getY(),foreground),model);
			executeCommand(cmdAdd);
		}

	public void drawLine(MouseEvent click, Color foreground, Color background) {
	
		if(counterLine==1) {
			startPoint=	new Point(click.getX(),click.getY());
			counterLine++;
		}else {
			executeCommand(new CmdAdd( new Line(startPoint,new Point(click.getX(),click.getY()),foreground),model));
			counterLine=1;
		}
	}

	public void drawRectangle(MouseEvent click, Color foreground, Color background) {
	
			DialogAddRectangle dialogAddRectangle = new DialogAddRectangle();
			dialogAddRectangle.setVisible(true);
			
			if(dialogAddRectangle.getWidth()>0 && dialogAddRectangle.getWidth()<501 && dialogAddRectangle.getHeight()>0 && dialogAddRectangle.getHeight()<501) {
				CmdAdd cmdAdd=new CmdAdd(new Rectangle(new Point(click.getX(),click.getY()),dialogAddRectangle.getRectangleHeight(),dialogAddRectangle.getRectangleWidth(),foreground,background),model);
				executeCommand(cmdAdd); 
			}
	}

	public void drawSquare(MouseEvent click, Color foreground, Color background) {
	
			DialogAddSquare dialogAddSquare = new DialogAddSquare();
			dialogAddSquare.setVisible(true);
			if(dialogAddSquare.getSide()>0&& dialogAddSquare.getSide()<501) {
			
			CmdAdd cmdAdd=new CmdAdd(new Square(new Point(click.getX(),click.getY()),dialogAddSquare.getSide(),foreground,background),model);
			executeCommand(cmdAdd);
			}
			
		}

	public void drawCircle(MouseEvent click, Color foreground, Color background) {
	
		
			DialogAddCircle dialogAddCircle = new DialogAddCircle();
			dialogAddCircle.setVisible(true);
			if(dialogAddCircle.getRadius()>0 && dialogAddCircle.getRadius()<=500)
			{
				CmdAdd cmdAdd=new CmdAdd(new Circle(new Point(click.getX(),click.getY()),dialogAddCircle.getRadius(),foreground,background),model);
				executeCommand(cmdAdd);
				
			}
		}

	public void drawHexagon(MouseEvent click, Color foreground, Color background) {
	
		
			DialogAddHexagon dialogAddHexagon = new DialogAddHexagon();
			dialogAddHexagon.setVisible(true);
			if(dialogAddHexagon.getRadius()>0&& dialogAddHexagon.getRadius()<501) {
				HexagonAdapter hexagon=new HexagonAdapter(click.getX(),click.getY(), dialogAddHexagon.getRadius(),foreground,background);
				CmdAdd cmdAdd=new CmdAdd(hexagon, model);
				executeCommand(cmdAdd);
			}
				
			
		}
		
	
	public void edit() {
		
				if (selectedShapes.get(0) instanceof Point) {
					DialogEditPoint dialogEditPoint = new DialogEditPoint((Point)selectedShapes.get(0));
					dialogEditPoint.setVisible(true);

					if (dialogEditPoint.getX() > 0 && dialogEditPoint.getX() < 751 && dialogEditPoint.getY() > 0
							&& dialogEditPoint.getY() < 521)
															// {
						executeCommand(new CmdEditPoint((Point) selectedShapes.get(0), new Point(dialogEditPoint.getX(),
								dialogEditPoint.getY(), dialogEditPoint.getForeground())));
				} else if (selectedShapes.get(0) instanceof Line) {
					DialogEditLine dialogEditLine = new DialogEditLine((Line) selectedShapes.get(0));
					dialogEditLine.setVisible(true);
					if (dialogEditLine.getStartX() > 0 && dialogEditLine.getStartX() < 751
							&& dialogEditLine.getStartY() > 0 && dialogEditLine.getStartY() < 521
							&& dialogEditLine.getEndX() > 0 && dialogEditLine.getEndX() < 751
							&& dialogEditLine.getEndY() > 0 && dialogEditLine.getEndY() < 521) {
						executeCommand(new CmdEditLine((Line) selectedShapes.get(0),
								new Line(new Point(dialogEditLine.getStartX(), dialogEditLine.getStartY()),
										new Point(dialogEditLine.getEndX(), dialogEditLine.getEndY()),
										dialogEditLine.getForeground())));
					}

				} else if (selectedShapes.get(0) instanceof Rectangle) {
					DialogEditRectangle dialogEditRectangle = new DialogEditRectangle((Rectangle) selectedShapes.get(0));
					dialogEditRectangle.setVisible(true);
					if (dialogEditRectangle.getStartX() > 0 && dialogEditRectangle.getStartX() < 751
							&& dialogEditRectangle.getStartY() > 0 && dialogEditRectangle.getStartY() < 521
							&& dialogEditRectangle.getHeightR() > 0 && dialogEditRectangle.getHeightR() < 400
							&& dialogEditRectangle.getWidthR() > 0 && dialogEditRectangle.getWidthR() < 400) {
						executeCommand(new CmdEditRectangle((Rectangle) selectedShapes.get(0),
								new Rectangle(
										new Point(dialogEditRectangle.getStartX(), dialogEditRectangle.getStartY()),
										dialogEditRectangle.getHeightR(), dialogEditRectangle.getWidthR(),
										dialogEditRectangle.getForeground(), dialogEditRectangle.getBackground())));
					}
				} else if (selectedShapes.get(0) instanceof Square) {
					DialogEditSquare dialogEditSquare = new DialogEditSquare((Square) selectedShapes.get(0));
					dialogEditSquare.setVisible(true);
					if (dialogEditSquare.getStartX() > 0 && dialogEditSquare.getStartX() < 751
							&& dialogEditSquare.getStartY() > 0 && dialogEditSquare.getStartY() < 521
							&& dialogEditSquare.getSide() > 0 && dialogEditSquare.getSide() < 400) {
						executeCommand(new CmdEditSquare((Square)selectedShapes.get(0),
								new Square(new Point(dialogEditSquare.getStartX(), dialogEditSquare.getStartY()),
										dialogEditSquare.getSide(), dialogEditSquare.getForeground(),
										dialogEditSquare.getBackground())));
					}
				} else if (selectedShapes.get(0) instanceof Circle) {
					DialogEditCircle dialogEditCircle = new DialogEditCircle((Circle) selectedShapes.get(0));
					dialogEditCircle.setVisible(true);
					if (dialogEditCircle.getCenterX() > 0 && dialogEditCircle.getCenterX() < 751
							&& dialogEditCircle.getCenterY() > 0 && dialogEditCircle.getCenterY() < 521
							&& dialogEditCircle.getRadius() > 0 && dialogEditCircle.getRadius() < 400) {
						executeCommand(new CmdEditCircle((Circle) selectedShapes.get(0),
								new Circle(new Point(dialogEditCircle.getCenterX(), dialogEditCircle.getCenterY()),
										dialogEditCircle.getRadius(), dialogEditCircle.getForeground(),
										dialogEditCircle.getBackground())));
					}
				} else if (selectedShapes.get(0) instanceof HexagonAdapter) {
					HexagonAdapter hexagon=(HexagonAdapter) selectedShapes.get(0);
					DialogEditHexagon dialogEditHexagon = new DialogEditHexagon(hexagon);
					//dialogEditHexagon.setColors();
					dialogEditHexagon.setVisible(true);
					if (dialogEditHexagon.getCenterX() > 0 && dialogEditHexagon.getCenterX() < 751
							&& dialogEditHexagon.getCenterY() > 0 && dialogEditHexagon.getCenterY() < 521
							&& dialogEditHexagon.getRadius() > 0 && dialogEditHexagon.getRadius() < 400) {
						executeCommand(new CmdEditHexagon((HexagonAdapter) selectedShapes.get(0),
								new HexagonAdapter(dialogEditHexagon.getCenterX(), dialogEditHexagon.getCenterY(),
										dialogEditHexagon.getRadius(), dialogEditHexagon.getForeground(),
										dialogEditHexagon.getBackground())));
					}
				}
			

		
	}
	
	public void delete() {
		if(JOptionPane.showConfirmDialog(null, "Are you sure that you want to delete selected shape?", "Warning!", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			
			Iterator<Shape> it=model.getAll().iterator();
			while(it.hasNext()) {
				Shape s=it.next();
				if(s.isSelected()) {
					cmdDeleteShape =new CmdDeleteShape(s, model);
					
				}
			}
			executeCommand(cmdDeleteShape);
			
			
		}
	}
	public void undo() {
		if(commandStack.isEmpty()) {
			frame.getBtnUndo().setEnabled(false);
			return;
		}
		Command cmd=commandStack.pop();
		cmd.unexecute();
		redoStack.push(cmd);
		logCommands.add("undo:"+cmd.toString());
		frame.getTextAreaLog().append("undo:"+cmd+"\n");
		frame.getBtnRedo().setEnabled(true);
		selectedCount.setSelectedCount(model.getSelectedCount());
		frame.getView().repaint();
		if(commandStack.isEmpty()) {
			frame.getBtnUndo().setEnabled(false);
		}
		
	}
	public void redo() {
		
		if(redoStack.isEmpty()) {
			frame.getBtnRedo().setEnabled(false);
			return;
		}
		Command cmd=redoStack.pop();
		frame.getBtnUndo().setEnabled(true);
		
		logCommands.add("redo:"+cmd.toString());
		frame.getTextAreaLog().append("redo:"+cmd+"\n");
		cmd.execute();
		commandStack.push(cmd);
		selectedCount.setSelectedCount(model.getSelectedCount());
		frame.getView().repaint();
		if(redoStack.isEmpty()) {
			frame.getBtnRedo().setEnabled(false);
		}
		
	}
	public void executeCommand(Command command) {
		
		
		command.execute();
		
		commandStack.push(command);
		redoStack.clear();
		frame.getBtnUndo().setEnabled(true);
		frame.getBtnRedo().setEnabled(false);
		frame.getTextAreaLog().append(command.toString()+"\n");
		logCommands.add(command.toString());
		selectedCount.setSelectedCount(model.getSelectedCount());
		chekStateButtons();
		frame.getView().repaint();
	}
	public void chekStateButtons() {
		for(Shape s:model.getAll()) {
			
				
				if(model.getSelectedCount()==1) {
					if(model.getAll().indexOf(s)==0){	
					
						frame.getBtnBringToFront().setEnabled(false);
						frame.getBtnToFront().setEnabled(false);
				
					}else if(model.getAll().indexOf(s)==model.getAll().size()-1) {
						frame.getBtnToBack().setEnabled(false);
						frame.getBtnBringToBack().setEnabled(false);
						frame.getBtnBringToFront().setEnabled(true);
						frame.getBtnToFront().setEnabled(true);
					
					}else {
						frame.getBtnToBack().setEnabled(true);
						frame.getBtnBringToBack().setEnabled(true);
						frame.getBtnBringToFront().setEnabled(true);
						frame.getBtnToFront().setEnabled(true);
					}
				}else {
					frame.getBtnToBack().setEnabled(false);
					frame.getBtnBringToBack().setEnabled(false);
					frame.getBtnBringToFront().setEnabled(false);
					frame.getBtnToFront().setEnabled(false);
				}
				
			
			
		}
	}
	
	
	public void bringToBack() {
		if(frame.getBtnBringToBack().isEnabled()) {
		selectedCount.setSelectedCount(getNumberOfSelected());
		executeCommand(new CmdBringToBack(model, getSelected()));

			frame.getBtnToBack().setEnabled(false);
			frame.getBtnBringToBack().setEnabled(false);

			frame.getBtnToFront().setEnabled(true);
			frame.getBtnBringToFront().setEnabled(true);
		}
	}
	public void bringToFront() {
		
		if(frame.getBtnBringToFront().isEnabled()) {
			executeCommand(new CmdBringToFront(model, getSelected()));

			frame.getBtnToFront().setEnabled(false);
			frame.getBtnBringToFront().setEnabled(false);

			frame.getBtnToBack().setEnabled(true);
			frame.getBtnBringToBack().setEnabled(true);
		}
		
		
	}
	public void toFront() {
		if(frame.getBtnToFront().isEnabled()) {
		
		executeCommand(new CmdToFront(model, getSelected()));

		frame.getBtnToBack().setEnabled(true);
		frame.getBtnBringToBack().setEnabled(true);

		if (model.getAll().indexOf(getSelected()) == model.getAll().size() - 1) {
			frame.getBtnToFront().setEnabled(false);
			frame.getBtnBringToFront().setEnabled(false);
		}
		}
	}
	public void toBack() {
		
		if(frame.getBtnToBack().isEnabled()) {
		
			executeCommand(new CmdToBack(model, getSelected()));

			frame.getBtnToFront().setEnabled(true);
			frame.getBtnBringToFront().setEnabled(true);

			if (model.getAll().indexOf(getSelected()) == 0) {
				frame.getBtnToBack().setEnabled(false);
				frame.getBtnBringToBack().setEnabled(false);
			}
		}
	}
	public int getNumberOfSelected() {
		
		return selectedShapes.size();
		
	}
	
	public Shape getSelected() {
		
		for(Shape s:model.getAll()) {
			if(s.isSelected()) {
				return s;
			}
		}
		return null;
	}
	public DrawModel getModel() {
		return model;
	}
	public void save(String option) {
		
		if (option.equals("drawing")) {
			SaveManager sm = new SaveManager(new SaveDrawing(model));
			sm.save();
		} else if (option.equals("log")) {
			SaveManager sm = new SaveManager(new SaveLog(logCommands));
			sm.save();
		}

	}
	public void open() {
		try {

			JFileChooser fileChooser = new JFileChooser();

			if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				File f = (fileChooser.getSelectedFile());

				if (f.getAbsolutePath().endsWith(".txt")) {

					model.getAll().clear();
					commandStack.clear();
					frame.setDefaultFrame();

					fr = new FileReader(f);
					bf = new BufferedReader(fr);

					frame.getView().repaint();
					
					frame.getBtnRunNextLine().setVisible(true);

				} else {
					FileInputStream fis = new FileInputStream(f);
					ObjectInputStream ois = new ObjectInputStream(fis);

					ArrayList<Shape> loadedList = (ArrayList<Shape>) ois.readObject();

					model.setList(loadedList);

					commandStack.clear();

					frame.setDefaultFrame(); 
					chekStateButtons();
					selectedCount.setSelectedCount(model.getSelectedCount());
					
					frame.getView().repaint();
				
					ois.close();
					fis.close();
				}
			}

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Error while opening file", "Error", JOptionPane.ERROR_MESSAGE);
		}
	
	}
	public void runNextCommand() {
		String line;
	
	try {
		if ((line = bf.readLine()) != null) { 

			String[] lineElements = line.split(":"); 

			String valuesLine = lineElements[2].replaceAll("[^0-9,.]", "");
																		

			String[] values = valuesLine.split(",");

			if (lineElements[0].equals("add")) {
				if (lineElements[1].equals("Point")) {
					Point p = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]),
							parseColor(values[2]));
					executeCommand(new CmdAdd(p, model));
				} else if (lineElements[1].equals("Line")) {
					Point start = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
					Point end = new Point(Integer.parseInt(values[2]), Integer.parseInt(values[3]));

					Line l = new Line(start, end, parseColor(values[4]));

					executeCommand(new CmdAdd(l, model));
				} else if (lineElements[1].equals("Rectangle")) {
					Point upperLeft = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
					int height = Integer.parseInt(values[2]);
					int width = Integer.parseInt(values[3]);

					Rectangle r = new Rectangle(upperLeft, height, width, parseColor(values[4]),
							parseColor(values[5]));
					executeCommand(new CmdAdd(r, model));
				} else if (lineElements[1].equals("Square")) {

					Point start = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
					int side = Integer.parseInt(values[2]);

					executeCommand(new CmdAdd(new Square(start, side, parseColor(values[3]), parseColor(values[4])),
							model));
				} else if (lineElements[1].equals("Circle")) {

					Point center = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
					int radius = Integer.parseInt(values[2]);

					executeCommand(new CmdAdd(
							new Circle(center, radius, parseColor(values[3]), parseColor(values[4])), model));
				} else if (lineElements[1].equals("Hexagon")) {
					int x = Integer.parseInt(values[0]);
					int y = Integer.parseInt(values[1]);
					int r = Integer.parseInt(values[2]);
					executeCommand(new CmdAdd(
							new HexagonAdapter(x, y, r, parseColor(values[3]), parseColor(values[4])), model));
				}

			}else if(lineElements[0].equals("select")){
				//select:Point:[374,125],foreground[0.0.0]
				
				
				String shape= line.substring(7);
			
				for(Shape s:model.getAll()) {
					if(s.toString().equals(shape)) {
						
						if(!s.isSelected()) {
							s.setSelected(true);
							executeCommand(new CmdSelectShape(s));
							break;
						}
					}
				}
				
				

			} else if(lineElements[0].equals("unselect")){
				//unselected:Point:[374,125],foreground[0.0.0]
				String shape= line.substring(11);
				
				for(Shape s:model.getAll()) {
					if(s.toString().equals(shape)) {
						
						if(s.isSelected()) {
							s.setSelected(false);
							executeCommand(new CmdUnselect(s));
							
							break;
						}
					}
				}
			} 
			else if (lineElements[0].equals("edit")) {
				// edit:Point:[426,184],foreground[0.0.0],to:Point:[1,1],foreground[0.0.0]

			
				String newValuesLine = lineElements[4].replaceAll("[^0-9,.]", "");
				String[] newValues = newValuesLine.split(",");

				if (lineElements[1].equals("Point")) {
					
					Point oldState = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]),
							parseColor(values[2]));
					Point newState = new Point(Integer.parseInt(newValues[0]), Integer.parseInt(newValues[1]),
							parseColor(newValues[2]));
					for (Shape s : model.getAll()) {
						if (s.equals(oldState)) {
							executeCommand(new CmdEditPoint((Point) s, newState));
							break;
						}
					}
				} else if (lineElements[1].equals("Line")) {

					Line oldState = new Line(new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1])),
							new Point(Integer.parseInt(values[2]), Integer.parseInt(values[3])),
							parseColor(values[4]));
					Line newState = new Line(
							new Point(Integer.parseInt(newValues[0]), Integer.parseInt(newValues[1])),
							new Point(Integer.parseInt(newValues[2]), Integer.parseInt(newValues[3])),
							parseColor(newValues[4]));

					for (Shape s : model.getAll()) {
						if (s.equals(oldState)) {
							executeCommand(new CmdEditLine((Line) s, newState));
							break;
						}
					}
				} else if (lineElements[1].equals("Square")) {

					Square oldState = new Square(
							new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1])),
							Integer.parseInt(values[2]), parseColor(values[3]), parseColor(values[4]));
					Square newState = new Square(
							new Point(Integer.parseInt(newValues[0]), Integer.parseInt(newValues[1])),
							Integer.parseInt(newValues[2]), parseColor(newValues[3]), parseColor(newValues[4]));

					for (Shape s : model.getAll()) {
						if (s.equals(oldState)) {
							executeCommand(new CmdEditSquare((Square) s, newState));
							break;
						}
					}
				} else if (lineElements[1].equals("Rectangle")) {
					Rectangle oldState = new Rectangle(
							new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1])),
							Integer.parseInt(values[2]), Integer.parseInt(values[3]), parseColor(values[4]),
							parseColor(values[5]));
					Rectangle newState = new Rectangle(
							new Point(Integer.parseInt(newValues[0]), Integer.parseInt(newValues[1])),
							Integer.parseInt(newValues[2]), Integer.parseInt(newValues[3]),
							parseColor(newValues[4]), parseColor(newValues[5]));

					for (Shape s : model.getAll()) {
						if (s.equals(oldState)) {
							executeCommand(new CmdEditRectangle((Rectangle) s, newState));
							break;
						}
					}
				} else if (lineElements[1].equals("Circle")) {

					Circle oldState = new Circle(
							new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1])),
							Integer.parseInt(values[2]), parseColor(values[3]), parseColor(values[4]));
					Circle newState = new Circle(
							new Point(Integer.parseInt(newValues[0]), Integer.parseInt(newValues[1])),
							Integer.parseInt(newValues[2]), parseColor(newValues[3]), parseColor(newValues[4]));

					for (Shape s : model.getAll()) {
						if (s.equals(oldState)) {
							executeCommand(new CmdEditCircle((Circle) s, newState));
							break;
						}
					}
				} else if (lineElements[1].equals("Hexagon")) {

					HexagonAdapter oldState = new HexagonAdapter(Integer.parseInt(values[0]),
							Integer.parseInt(values[1]), Integer.parseInt(values[2]), parseColor(values[3]),
							parseColor(newValues[4]));
					HexagonAdapter newState = new HexagonAdapter(Integer.parseInt(newValues[0]),
							Integer.parseInt(newValues[1]), Integer.parseInt(newValues[2]),
							parseColor(newValues[3]), parseColor(newValues[4]));

					for (Shape s : model.getAll()) {

						if (s.equals(oldState)) {
							executeCommand(new CmdEditHexagon((HexagonAdapter) s, newState));
							break;
						}
					}
				}
			} else if (lineElements[0].equals("delete")) {
				
					if (lineElements[1].equals("Point")) {
						String shapeValues = lineElements[2].replaceAll("[^0-9,.]", "");
						String[] sValues = shapeValues.split(",");// je l dobro to sto pricam

						Point p = new Point(Integer.parseInt(sValues[0]), Integer.parseInt(sValues[1]), 
								parseColor(sValues[2]));
						executeCommand(new CmdDeleteShape(p, model));
					} else if (lineElements[1].equals("Line")) {
						String shapeValues = lineElements[2].replaceAll("[^0-9,.]", "");
						String[] sValues = shapeValues.split(",");

						Point start = new Point(Integer.parseInt(sValues[0]), Integer.parseInt(sValues[1]));
						Point end = new Point(Integer.parseInt(sValues[2]), Integer.parseInt(sValues[3]));
						Color foreground = parseColor(sValues[4]);

						Line l = new Line(start, end, foreground);
						executeCommand(new CmdDeleteShape(l, model));
						
					} else if (lineElements[1].equals("Square")) {
						String shapeValues = lineElements[2].replaceAll("[^0-9,.]", "");
						String[] sValues = shapeValues.split(",");

						Point start = new Point(Integer.parseInt(sValues[0]), Integer.parseInt(sValues[1]));
						int side = Integer.parseInt(sValues[2]);
						Color foreground = parseColor(sValues[3]);
						Color background = parseColor(sValues[4]);

						Square s = new Square(start, side, foreground, background);
						executeCommand(new CmdDeleteShape(s, model));
					} else if (lineElements[1].equals("Rectangle")) {
						String shapeValues = lineElements[2].replaceAll("[^0-9,.]", "");
						String[] sValues = shapeValues.split(",");

						Point start = new Point(Integer.parseInt(sValues[0]), Integer.parseInt(sValues[1]));
						int height = Integer.parseInt(sValues[2]);
						int width = Integer.parseInt(sValues[3]);
						Color foreground = parseColor(sValues[4]);
						Color background = parseColor(sValues[5]);

						Rectangle r = new Rectangle(start, height, width, foreground, background);
						executeCommand(new CmdDeleteShape(r, model));
					} else if (lineElements[1].equals("Circle")) {
						String shapeValues = lineElements[2].replaceAll("[^0-9,.]", "");
						String[] sValues = shapeValues.split(",");

						Point center = new Point(Integer.parseInt(sValues[0]), Integer.parseInt(sValues[1]));
						int radius = Integer.parseInt(sValues[2]);
						Color foreground = parseColor(sValues[3]);
						Color background = parseColor(sValues[4]);

						Circle c = new Circle(center, radius, foreground, background);
						executeCommand(new CmdDeleteShape(c, model));
					} else if (lineElements[1].equals("Hexagon")) {
						String shapeValues = lineElements[2].replaceAll("[^0-9,.]", "");
						String[] sValues = shapeValues.split(",");

						int radius = Integer.parseInt(sValues[2]);
						Color foreground = parseColor(sValues[3]);
						Color background = parseColor(sValues[4]);

						HexagonAdapter h = new HexagonAdapter(Integer.parseInt(sValues[0]),
								Integer.parseInt(sValues[1]), Integer.parseInt(sValues[2]), foreground, background);
						executeCommand(new CmdDeleteShape(h, model));
						
					}

				
					


			} else if ((lineElements[0].equals("toback")) || (lineElements[0].equals("tofront"))
					|| (lineElements[0].equals("bringtoback")) || (lineElements[0].equals("bringtofront"))) {

				if (lineElements[1].equals("Point")) {
					Color foreground = parseColor(values[2]);
					Point p = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]), foreground);
					

					for (Shape s : model.getAll()) {
						if (s.equals(p)) {
							if (lineElements[0].equals("toback"))
								executeCommand(new CmdToBack(model, (Point) s));
							else if (lineElements[0].equals("tofront"))
								executeCommand(new CmdToFront(model, (Point) s));
							else if (lineElements[0].equals("bringtoback"))
								executeCommand(new CmdBringToBack(model, (Point) s));
							else if (lineElements[0].equals("bringtofront"))
								executeCommand(new CmdBringToFront(model, (Point) s));
							break;
						}
					}
				} else if (lineElements[1].equals("Line")) {

					Point start = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
					Point end = new Point(Integer.parseInt(values[2]), Integer.parseInt(values[3]));
					Color outline = parseColor(values[4]);

					Line l = new Line(start, end, outline);

					for (Shape s : model.getAll()) {
						if (s.equals(l)) {
							if (lineElements[0].equals("toback"))
								executeCommand(new CmdToBack(model, (Line) s));
							else if (lineElements[0].equals("tofront"))
								executeCommand(new CmdToFront(model, (Line) s));
							else if (lineElements[0].equals("bringtoback"))
								executeCommand(new CmdBringToBack(model, (Line) s));
							else if (lineElements[0].equals("bringtofront"))
								executeCommand(new CmdBringToFront(model, (Line) s));
							break;
						}
					}
				} else if (lineElements[1].equals("Square")) {

					Point start = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
					int side = Integer.parseInt(values[2]);
					Color outline = parseColor(values[3]);
					Color inside = parseColor(values[4]);

					Square sq = new Square(start, side, outline, inside);

					for (Shape s : model.getAll()) {
						if (s.equals(sq)) {
							if (lineElements[0].equals("toback"))
								executeCommand(new CmdToBack(model, (Square) s));
							else if (lineElements[0].equals("tofront"))
								executeCommand(new CmdToFront(model, (Square) s));
							else if (lineElements[0].equals("bringtoback"))
								executeCommand(new CmdBringToBack(model, (Square) s));
							else if (lineElements[0].equals("bringtofront"))
								executeCommand(new CmdBringToFront(model, (Square) s));
							break;
						}
					}
				} else if (lineElements[1].equals("Rectangle")) {

					Point start = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
					int height = Integer.parseInt(values[2]);
					int width = Integer.parseInt(values[3]);
					Color outline = parseColor(values[4]);
					Color inside = parseColor(values[5]);

					Rectangle r = new Rectangle(start, height, width, outline, inside);

					for (Shape s : model.getAll()) {
						if (s.equals(r)) {
							if (lineElements[0].equals("toback"))
								executeCommand(new CmdToBack(model, (Rectangle) s));
							else if (lineElements[0].equals("tofront"))
								executeCommand(new CmdToFront(model, (Rectangle) s));
							else if (lineElements[0].equals("bringtoback"))
								executeCommand(new CmdBringToBack(model, (Rectangle) s));
							else if (lineElements[0].equals("bringtofront"))
								executeCommand(new CmdBringToFront(model, (Rectangle) s));
							break;
						}
					}
				} else if (lineElements[1].equals("Circle")) {

					Point center = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
					int radius = Integer.parseInt(values[2]);
					Color outline = parseColor(values[3]);
					Color inside = parseColor(values[4]);

					Circle c = new Circle(center, radius, outline, inside);

					for (Shape s : model.getAll()) {
						if (s.equals(c)) {
							if (lineElements[0].equals("toback"))
								executeCommand(new CmdToBack(model, (Circle) s));
							else if (lineElements[0].equals("tofront"))
								executeCommand(new CmdToFront(model, (Circle) s));
							else if (lineElements[0].equals("bringtoback"))
								executeCommand(new CmdBringToBack(model, (Circle) s));
							else if (lineElements[0].equals("bringtofront"))
								executeCommand(new CmdBringToFront(model, (Circle) s));
							break;
						}
					}
				} else if (lineElements[1].equals("Hexagon")) {

					int x = Integer.parseInt(values[0]);
					int y = Integer.parseInt(values[1]);
					int r = Integer.parseInt(values[2]);
					HexagonAdapter h = new HexagonAdapter(x, y, r, parseColor(values[3]), parseColor(values[4]));

					for (Shape s : model.getAll()) {
						if (s.equals(h)) {
							if (lineElements[0].equals("toback"))
								executeCommand(new CmdToBack(model, (HexagonAdapter) s));
							else if (lineElements[0].equals("tofront"))
								executeCommand(new CmdToFront(model, (HexagonAdapter) s));
							else if (lineElements[0].equals("bringtoback"))
								executeCommand(new CmdBringToBack(model, (HexagonAdapter) s));
							else if (lineElements[0].equals("bringtofront"))
								executeCommand(new CmdBringToFront(model, (HexagonAdapter) s));
							break;
						}
					}
				}
			} else if (lineElements[0].equals("undo") || lineElements[0].equals("redo")) {
				// undo:add:Point:[313,146],foreground[0.0.0]

						if (lineElements[0].equals("undo"))
							undo();
						else
							redo();
			
			}

		} else {
			frame.getBtnRunNextLine().setVisible(false);
		}
	} catch (IOException e) {
		e.printStackTrace();
	}
		
	}
	public Color parseColor(String text) {
		String[] rgb = text.split("\\.");
		int red = Integer.parseInt(rgb[0]);
		int green = Integer.parseInt(rgb[1]);
		int blue = Integer.parseInt(rgb[2]);

		Color c = new Color(red, green, blue);
		return c;
	}
	
	
}
