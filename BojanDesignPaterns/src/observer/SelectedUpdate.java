package observer;

import mvc.DrawFrame;

public class SelectedUpdate implements Observer {

	private int selectedCount;
	private DrawFrame frame;
	
	public SelectedUpdate (DrawFrame frame) {
		this.frame=frame;
	}
	@Override
	public void update(int selectedCount) {
		this.selectedCount=selectedCount;
		updateButtons();
	}
	private void updateButtons() {
	
		if(selectedCount>0)
		{
			frame.getBtnDelete().setEnabled(true);
			frame.getBtnEdit().setEnabled(true);
			frame.getBtnToBack().setEnabled(true);
			frame.getBtnToFront().setEnabled(true);
			frame.getBtnBringToBack().setEnabled(true);
			frame.getBtnToFront().setEnabled(true);
			
			
			 if(selectedCount>1)
			{
				frame.getBtnEdit().setEnabled(false);
				frame.getBtnToBack().setEnabled(false);
				frame.getBtnToFront().setEnabled(false);
				frame.getBtnBringToBack().setEnabled(false);
				frame.getBtnToFront().setEnabled(false);
			}
		}
		else
		{
			frame.getBtnDelete().setEnabled(false);
			frame.getBtnEdit().setEnabled(false);
			frame.getBtnToBack().setEnabled(false);
			frame.getBtnToFront().setEnabled(false);
			frame.getBtnBringToBack().setEnabled(false);
			frame.getBtnToFront().setEnabled(false);
		}
		
	}
	
	
}
