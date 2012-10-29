
public class AbstractEntry {
	private int id;
	private int predLabel;
	private int actualLabel;
	private SparseTermList list;
	
	public AbstractEntry() {
		list = new SparseTermList();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPredLabel() {
		return predLabel;
	}
	public void setPredLabel(int predLabel) {
		this.predLabel = predLabel;
	}
	public int getActualLabel() {
		return actualLabel;
	}
	public void setActualLabel(int actualLabel) {
		this.actualLabel = actualLabel;
	}
	public SparseTermList getList() {
		return list;
	}
	public void setList(SparseTermList list) {
		this.list = list;
	}	
}
