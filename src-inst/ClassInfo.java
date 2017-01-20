import java.util.*;

public class ClassInfo {

	private HashMap<Integer, Boolean> allInstances;
	private HashMap<String, VariableInfo> variables;
	private boolean allVariablesAdded;

	public ClassInfo(){
		allInstances = new HashMap<Integer, Boolean>();
		variables = new HashMap<String, VariableInfo>();
		allVariablesAdded = false; 
	}

	public void addVariable(String variableName) {
		if(!variables.containsKey(variableName)){
			variables.put(variableName, new VariableInfo());
		}
	}

	public void addAllVariables(ArrayList<String> variableNames) {
		for(String variableName : variableNames){
			addVariable(variableName);
		}
		allVariablesAdded = true;
	}

	public boolean allVariablesAdded(){
		return allVariablesAdded;
	}

	public void addInstance(int objHashCode) {
		allInstances.put(new Integer(objHashCode), new Boolean(true)); 
	}

	public int getNbOfInstances() {
		return allInstances.size();
	}

	public void processRead(String variableName, int objHashCode){
		addVariable(variableName);
		allInstances.put(new Integer(objHashCode), new Boolean(true));
		VariableInfo varInfo = variables.get(variableName);
		varInfo.addInstance(objHashCode);
		varInfo.increaseReads();
	}

	public void processWrite(String variableName, int objHashCode){
		addVariable(variableName);
		allInstances.put(new Integer(objHashCode), new Boolean(true));
		VariableInfo varInfo = variables.get(variableName);
		varInfo.addInstance(objHashCode);
		varInfo.increaseWrites();
	}

	public HashMap<String, VariableInfo> getVariablesInfo(){
		return variables;
	}
}
