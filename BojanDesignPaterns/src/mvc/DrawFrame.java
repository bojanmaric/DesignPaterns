package mvc;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collections;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.JToggleButton;


public class DrawFrame extends JFrame {

	private DrawView view=new DrawView();
	private DrawController controller;


	
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenuItem mntmSaveAsDrawing;
	private JMenuItem mntmSave;
	private JMenuItem mntmOpen;
	private JPanel buttonsPanel;
	private JPanel undoRedoPanel;
	private JButton btnUndo;
	private JButton btnRedo;
	private JPanel bringToPanel;
	private JButton btnToBack;
	private JButton btnBringToBack;
	private JButton btnToFront;
	private JButton btnBringToFront;
	private JPanel editPanel;
	private JToggleButton btnSelect;
	private JButton btnEdit;
	private JButton btnDelete;
	private JToggleButton btnPoint;
	private JToggleButton btnLine;
	private JToggleButton btnSquare;
	private JToggleButton btnCircle;
	private JToggleButton btnRectangle;
	private JPanel shapesPanel;
	private JToggleButton btnHexagon;
	private JPanel fgrBgrPanel;
	private JButton btnForeground;
	private JButton btnBackground;
	private JPanel logPanel;
	private JScrollPane spLog;
	private JTextArea logTextArea;
	private JButton btnRunNextLine;
	
	private Color background = Color.WHITE;
	private Color foreground=Color.BLACK;
	private JTextArea textAreaLog;

	public DrawFrame() {
		setTitle("Drawing App");
		view.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent click) {
				if(btnSelect.isSelected()) {
					controller.select(click);
				}else if(btnLine.isSelected()) {
					
					controller.drawLine(click, foreground, background);
				}else if(btnPoint.isSelected()){
					controller.drawPoint(click, foreground, background);
				}else if(btnCircle.isSelected()) {
					controller.drawCircle(click, foreground, background);
				}else if(btnRectangle.isSelected()) {
					controller.drawRectangle(click, foreground, background);
				}else if(btnSquare.isSelected()) {
					controller.drawSquare(click, foreground, background);
				}else if(btnHexagon.isSelected()) {
					controller.drawHexagon(click, foreground, background);
				}
			}
		});

		menuBar = new JMenuBar();
		
		setJMenuBar(menuBar);
		
		mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		mntmSaveAsDrawing = new JMenuItem("Save as draw");
		mntmSaveAsDrawing.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.save("drawing");
			}
		});
		mnFile.add(mntmSaveAsDrawing);
		
		mntmSave = new JMenuItem("Save as log");
		mntmSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.save("log");
			}
		});
		mnFile.add(mntmSave);
		
		mntmOpen = new JMenuItem("Open");
		mntmOpen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				controller.open();
			}
		});
			
		mnFile.add(mntmOpen);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		buttonsPanel = new JPanel();
		getContentPane().add(buttonsPanel, BorderLayout.NORTH);
		
		undoRedoPanel = new JPanel();
		buttonsPanel.add(undoRedoPanel);
		
		btnUndo = new JButton("", new ImageIcon("images/undo.png"));
		btnUndo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(btnUndo.isEnabled()) {
					controller.undo();
				}
			}
		});
		btnUndo.setBackground(Color.WHITE);
		btnUndo.setToolTipText("Undo command");
		btnUndo.setPreferredSize(new Dimension(50, 50));
		undoRedoPanel.add(btnUndo);
		
		btnUndo.setToolTipText("Undo command");
		btnRedo = new JButton("", new ImageIcon("images/redo.png"));
		btnRedo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(btnRedo.isEnabled()) {
					controller.redo();
				}
			}
		});
		btnRedo.setBackground(Color.WHITE);
		btnRedo.setToolTipText("Redo command");
		btnRedo.setPreferredSize(new Dimension(50, 50));
		undoRedoPanel.add(btnRedo);
		btnRedo.setToolTipText("Redo command");
		
		
		bringToPanel = new JPanel();
		buttonsPanel.add(bringToPanel);
		GridBagLayout gbl_bringToPanel = new GridBagLayout();
		gbl_bringToPanel.columnWidths = new int[]{0, 0};
		gbl_bringToPanel.rowHeights = new int[]{0, 0, 0};
		gbl_bringToPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_bringToPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		bringToPanel.setLayout(gbl_bringToPanel);
		
		btnToBack = new JButton("", new ImageIcon("images/toback.png"));
		
		btnToBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.toBack();
			}
		});
		btnToBack.setBackground(Color.WHITE);
		btnToBack.setToolTipText("Move to down");
		btnToBack.setPreferredSize(new Dimension(50, 50));
		GridBagConstraints gbc_btnToBack = new GridBagConstraints();
		gbc_btnToBack.insets = new Insets(0, 0, 5, 5);
		gbc_btnToBack.gridx = 0;
		gbc_btnToBack.gridy = 0;
		bringToPanel.add(btnToBack, gbc_btnToBack);
		
		btnToFront = new JButton("", new ImageIcon("images/tofront.png"));
		btnToFront.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.toFront();
			}
		});
		btnToFront.setBackground(Color.WHITE);
		btnToFront.setToolTipText("Move to up");
		btnToFront.setPreferredSize(new Dimension(50, 50));
		GridBagConstraints gbc_btnToFront = new GridBagConstraints();
		gbc_btnToFront.insets = new Insets(0, 0, 5, 0);
		gbc_btnToFront.gridx = 1;
		gbc_btnToFront.gridy = 0;
		bringToPanel.add(btnToFront, gbc_btnToFront);
		
		btnBringToBack = new JButton("", new ImageIcon("images/bringtoback.png"));
		btnBringToBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.bringToBack();
			}
		});
		btnBringToBack.setBackground(Color.WHITE);
		btnBringToBack.setToolTipText("Bring to front");
		btnBringToBack.setPreferredSize(new Dimension(50, 50));
		GridBagConstraints gbc_btnBringToBack = new GridBagConstraints();
		gbc_btnBringToBack.insets = new Insets(0, 0, 0, 5);
		gbc_btnBringToBack.gridx = 0;
		gbc_btnBringToBack.gridy = 1;
		bringToPanel.add(btnBringToBack, gbc_btnBringToBack);
		
		btnBringToFront = new JButton("", new ImageIcon("images/bringtofront.png"));
		btnBringToFront.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.bringToFront();
			}
		});
		btnBringToFront.setBackground(Color.WHITE);
		btnBringToFront.setToolTipText("Bring to front");
		btnBringToFront.setPreferredSize(new Dimension(50, 50));
		GridBagConstraints gbc_btnBringToFront = new GridBagConstraints();
		gbc_btnBringToFront.gridx = 1;
		gbc_btnBringToFront.gridy = 1;
		bringToPanel.add(btnBringToFront, gbc_btnBringToFront);
		
		editPanel = new JPanel();
		buttonsPanel.add(editPanel);
		editPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnSelect = new JToggleButton("", new ImageIcon("images/select.png"));
		btnSelect.setBackground(Color.WHITE);
		btnSelect.setPreferredSize(new Dimension(50, 50));
		editPanel.add(btnSelect);
		btnSelect.setToolTipText("Select Shape");
		
		btnEdit = new JButton("", new ImageIcon("images/edit.png"));
		btnEdit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.edit();
			}
		});
		btnEdit.setBackground(Color.WHITE);
		btnEdit.setToolTipText("Edit Shape");
		btnEdit.setPreferredSize(new Dimension(50, 50));
		editPanel.add(btnEdit);
		btnEdit.setToolTipText("Edit shape");


		
		btnDelete = new JButton("", new ImageIcon("images/delete.png"));
		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(btnDelete.isEnabled()) {
					controller.delete();
				}
			}
		});
		btnDelete.setBackground(Color.WHITE);
		btnDelete.setToolTipText("Delete Shape");
		btnDelete.setPreferredSize(new Dimension(50, 50));
	
		editPanel.add(btnDelete);
		
		shapesPanel = new JPanel();
		buttonsPanel.add(shapesPanel);
		GridBagLayout gbl_shapesPanel = new GridBagLayout();
		gbl_shapesPanel.columnWidths = new int[]{0, 0};
		gbl_shapesPanel.rowHeights = new int[]{0, 0, 0};
		gbl_shapesPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_shapesPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		shapesPanel.setLayout(gbl_shapesPanel);
		
		btnPoint = new JToggleButton("", new ImageIcon("images/point.png"));
		btnPoint.setToolTipText("Draw Point");
		btnPoint.setBackground(Color.WHITE);
		btnPoint.setPreferredSize(new Dimension(50, 50));
		GridBagConstraints gbc_btnPoint = new GridBagConstraints();
		gbc_btnPoint.insets = new Insets(0, 0, 5, 5);
		gbc_btnPoint.gridx = 0;
		gbc_btnPoint.gridy = 0;
		shapesPanel.add(btnPoint, gbc_btnPoint);
		
		btnLine = new JToggleButton("", new ImageIcon("images/line.png"));
		btnLine.setToolTipText("Draw Line");
		btnLine.setBackground(Color.WHITE);
		btnLine.setPreferredSize(new Dimension(50, 50));
		GridBagConstraints gbc_btnLine = new GridBagConstraints();
		gbc_btnLine.insets = new Insets(0, 0, 5, 5);
		gbc_btnLine.gridx = 1;
		gbc_btnLine.gridy = 0;
		shapesPanel.add(btnLine, gbc_btnLine);
		
		btnSquare = new JToggleButton("", new ImageIcon("images/square.png"));
		btnSquare.setToolTipText("Draw Square");
		btnSquare.setBackground(Color.WHITE);
		btnSquare.setPreferredSize(new Dimension(50, 50));
		GridBagConstraints gbc_btnSquare = new GridBagConstraints();
		gbc_btnSquare.insets = new Insets(0, 0, 5, 0);
		gbc_btnSquare.gridx = 2;
		gbc_btnSquare.gridy = 0;
		shapesPanel.add(btnSquare, gbc_btnSquare);
		
		btnCircle = new JToggleButton("", new ImageIcon("images/circle.png"));
		btnCircle.setToolTipText("Draw Circle");
		btnCircle.setBackground(Color.WHITE);
		btnCircle.setPreferredSize(new Dimension(50, 50));
		GridBagConstraints gbc_btnCircle = new GridBagConstraints();
		gbc_btnCircle.insets = new Insets(0, 0, 0, 5);
		gbc_btnCircle.gridx = 0;
		gbc_btnCircle.gridy = 1;
		shapesPanel.add(btnCircle, gbc_btnCircle);
		
		btnRectangle = new JToggleButton("", new ImageIcon("images/rectangle.png"));
		btnRectangle.setToolTipText("Draw Rectangle");
		btnRectangle.setBackground(Color.WHITE);
		btnRectangle.setPreferredSize(new Dimension(50, 50));
		GridBagConstraints gbc_btnRectangle = new GridBagConstraints();
		gbc_btnRectangle.insets = new Insets(0, 0, 0, 5);
		gbc_btnRectangle.gridx = 1;
		gbc_btnRectangle.gridy = 1;
		shapesPanel.add(btnRectangle, gbc_btnRectangle);
		
		btnHexagon = new JToggleButton("", new ImageIcon("images/hexagon.png"));
		btnHexagon.setToolTipText("Draw Hexagon");
		btnHexagon.setBackground(Color.WHITE);
		btnHexagon.setPreferredSize(new Dimension(50, 50));
		
		GridBagConstraints gbc_btnHexagon = new GridBagConstraints();
		gbc_btnHexagon.gridx = 2;
		gbc_btnHexagon.gridy = 1;
		shapesPanel.add(btnHexagon, gbc_btnHexagon);
		
		fgrBgrPanel = new JPanel();
		buttonsPanel.add(fgrBgrPanel);
		
		btnForeground = new JButton("");
		fgrBgrPanel.add(btnForeground);
		btnForeground.setBackground(Color.BLACK);
		btnForeground.setPreferredSize(new Dimension(50, 50));
		btnForeground.setToolTipText("Choose shape foreground");
		btnForeground.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Color color=JColorChooser.showDialog(null, "Chose foreground color", Color.BLACK);
				if(color!=null) {
					btnForeground.setBackground(color);
					foreground=color;
				}
				
			}
		});
		
		btnBackground = new JButton("");
		btnBackground.setBackground(Color.WHITE);
		btnBackground.setToolTipText("Choose shape background");
		btnBackground.setPreferredSize(new Dimension(50, 50));
		
		btnBackground.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Color color=JColorChooser.showDialog(null, "Chose background color", Color.WHITE);
				
				if(color!=null) {
					btnBackground.setBackground(color);
					background=color;
				}
				
			}
		});
		
		
		fgrBgrPanel.add(btnBackground);
		
		logPanel = new JPanel();
		getContentPane().add(logPanel, BorderLayout.SOUTH);
		
		
		btnRunNextLine = new JButton("Run next line");
		btnRunNextLine.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.runNextCommand();
			}
		});
		logPanel.add(btnRunNextLine);
		btnRunNextLine.setVisible(false);
		
		JScrollPane spLog = new JScrollPane();
		spLog.setPreferredSize(new Dimension(500, 100));
		logPanel.add(spLog);
		
		textAreaLog = new JTextArea();
		textAreaLog.setColumns(20);
		textAreaLog.setEditable(false);
		spLog.setViewportView(textAreaLog);


		view.setBackground(Color.WHITE);
		
		ButtonGroup bgButtons = new ButtonGroup();
		bgButtons.add(btnPoint);
		bgButtons.add(btnLine);
		bgButtons.add(btnRectangle);
		bgButtons.add(btnSquare);
		bgButtons.add(btnCircle);
		bgButtons.add(btnHexagon);
		bgButtons.add(btnSelect);

		btnDelete.setEnabled(false);
		btnEdit.setEnabled(false);
		btnToBack.setEnabled(false);
		btnToFront.setEnabled(false);
		btnBringToBack.setEnabled(false);
		btnBringToFront.setEnabled(false);
		btnUndo.setEnabled(false);
		btnRedo.setEnabled(false);

		getContentPane().add(view, BorderLayout.CENTER);
		
		
	}
	
	public JTextArea getTextAreaLog() {
		return textAreaLog;
	}
	public JButton getBtnUndo() {
		return btnUndo;
	}
	public JButton getBtnRedo() {
		return btnRedo;
	}
	public JButton getBtnToBack() {
		return btnToBack;
	}
	public JButton getBtnBringToBack() {
		return btnBringToBack;
	}
	public JButton getBtnToFront() {
		return btnToFront;
	}
	public JButton getBtnBringToFront() {
		return btnBringToFront;
	}
	public JToggleButton getBtnSelect() {
		return btnSelect;
	}
	public JButton getBtnEdit() {
		return btnEdit;
	}
	public JButton getBtnDelete() {
		return btnDelete;
	}
	
	public JButton getBtnRunNextLine() {
		return btnRunNextLine;
	}
	public void setController(DrawController controller) {
		this.controller = controller;
	}
	public DrawView getView() {
		return view;
	}

	public void setDefaultFrame() {
		btnEdit.setEnabled(false);
		btnDelete.setEnabled(false);
		btnToBack.setEnabled(false);
		btnToFront.setEnabled(false);
		btnBringToBack.setEnabled(false);
		btnBringToFront.setEnabled(false);
		btnUndo.setEnabled(false);
		btnRedo.setEnabled(false);
		foreground = Color.BLACK;
		textAreaLog.setText("");
		btnForeground.setBackground(foreground);
		background = Color.WHITE;
		btnBackground.setBackground(background);
		btnSelect.setSelected(true);
		
		
	}
}
