import java.util.HashMap;

public class VariableInfo {

	private int nbOfReads;
	private int nbOfWrites;
	private String type;
	private HashMap<Integer, Boolean> instancesUseVariable;

	public VariableInfo(String type) {
		nbOfReads = 0;
		nbOfWrites = 0;
		this.type = type;
		instancesUseVariable = new HashMap<Integer, Boolean>();
	}

	public void increaseReads() {
		nbOfReads++;
	}

	public void increaseWrites() {
		nbOfWrites++;
	}

	public void addInstance(int objHashCode) {
		instancesUseVariable.put(new Integer(objHashCode), new Boolean(true));
	}

	public int getReads() {
		return nbOfReads;
	}

	public int getWrites() {
		return nbOfWrites;
	}

	public String getType() {
		return type;
	}

	public int getNbOfInstances() {
		return instancesUseVariable.size();
	}
}
