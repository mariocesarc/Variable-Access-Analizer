import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.*;
import java.util.*;

import ch.usi.dag.disl.dynamicbypass.DynamicBypass;

public class Process {

    static {
        DynamicBypass.activate();
        try {
            Runtime.getRuntime().addShutdownHook(new Thread(new ExitThread()));
        } finally {
            DynamicBypass.deactivate();
        }
    }

    private static HashMap<String, ClassInfo> classes = new HashMap<String, ClassInfo>();

    public static boolean allVariablesAdded(String className) {
        if (!classes.containsKey(className)) {
            return false;
        } else {
            return classes.get(className).allVariablesAdded();
        }
    }

    public static void addAllVariables(String className, Field[] fields) {

        ArrayList<String> fieldsNames = new ArrayList<String>();
        ArrayList<String> fieldsTypes = new ArrayList<String>();

        for (Field field : fields) {
            if (!java.lang.reflect.Modifier.isStatic(field.getModifiers())) { // add
                                                                                // only
                                                                                // instance
                                                                                // variables
                fieldsNames.add(field.getName());
                fieldsTypes.add(field.getType().getName());
            }
        }

        classes.put(className, new ClassInfo());
        classes.get(className).addAllVariables(fieldsNames, fieldsTypes);
    }

    public static void addInstance(String className, int objHashCode) {
        classes.get(className).addInstance(objHashCode);
    }

    public static void processRead(String fieldOwnerClassName, String methodClassName, String variableName, int objHashCode) {
    	if(classes.containsKey(fieldOwnerClassName) && fieldOwnerClassName.equals(methodClassName)){
    		classes.get(fieldOwnerClassName).processRead(variableName, objHashCode);
    	}
    }

    public static void processWrite(String fieldOwnerClassName, String methodClassName, String variableName, int objHashCode) {
        if(classes.containsKey(fieldOwnerClassName) && fieldOwnerClassName.equals(methodClassName)){
    		classes.get(fieldOwnerClassName).processWrite(variableName, objHashCode);
    	}
    }

    public static class ExitThread implements Runnable {
        @Override
        public void run() {
            System.out.println("CLASS, VARIABLE, TYPE, READS, WRITES, INSTANCES-USE-VARIABLE, INSTANCES-TOTAL");
            for (String className : classes.keySet()) {
                HashMap<String, VariableInfo> variables = classes.get(className).getVariablesInfo();
                for (String variableName : variables.keySet()) {
                    VariableInfo var = variables.get(variableName);
                    ClassInfo classInfo = classes.get(className);
                    System.out.println(
                        className + ", " + 
                        variableName + ", " + 
                        var.getType() + ", " + 
                        var.getReads() + ", " + 
                        var.getWrites() + ", " + 
                        var.getNbOfInstances() + ", " + 
                        classInfo.getNbOfInstances());
                }
            }
        }
    }

}
