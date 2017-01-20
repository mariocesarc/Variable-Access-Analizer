import java.util.HashMap;

public class VariableInfo {
	
	private int nbOfReads;
	private int nbOfWrites;
	private HashMap<Integer, Boolean> instancesUseVariable;

	public VariableInfo() {
		nbOfReads = 0;
		nbOfWrites = 0;
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

	public int getNbOfInstances() {
		return instancesUseVariable.size();
	}
}
