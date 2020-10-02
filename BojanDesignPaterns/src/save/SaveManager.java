package save;

public class SaveManager implements Save {
	private Save saveObject;
	
	public SaveManager(Save saveObject) {
		this.saveObject=saveObject;
	}

	@Override
	public void save() {
		saveObject.save();
	}
	
	
}
