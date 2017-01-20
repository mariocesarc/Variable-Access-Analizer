import ch.usi.dag.disl.dynamicbypass.DynamicBypass;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.lang.reflect.*;
import java.util.*;

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

    public static boolean allVariablesAdded(String className){
        if(!classes.containsKey(className)){
            return false;
        }
        else{
            return classes.get(className).allVariablesAdded();
        }
    }

    public static void addAllVariables(String className, Field[] fields){
        if(!classes.containsKey(className)){
            classes.put(className, new ClassInfo());
        }
        ArrayList<String> fieldsNames = new ArrayList<String>();
        for(Field field : fields){
            if(!java.lang.reflect.Modifier.isStatic(field.getModifiers())){ // add only instance variables
                fieldsNames.add(field.getName());
            }
        }
        classes.get(className).addAllVariables(fieldsNames);
    }

    public static void addInstance(String className, int objHashCode){
        classes.get(className).addInstance(objHashCode);
    }

    public static void processRead(String className, String variableName, int objHashCode) {
        if(!classes.containsKey(className)){
            classes.put(className, new ClassInfo());
        }
        classes.get(className).processRead(variableName, objHashCode);
    }

    public static void processWrite(String className, String variableName, int objHashCode) {
        if(!classes.containsKey(className)){
            classes.put(className, new ClassInfo());
        }
  		classes.get(className).processWrite(variableName, objHashCode);
    }

    public static class ExitThread implements Runnable {
        @Override
        public void run() {
            System.out.println(
                        "CLASS, VARIABLE, READS, WRITES, INSTANCES-USE-VARIABLE, INSTANCES-TOTAL");
            for (String className : classes.keySet()) {
                HashMap<String, VariableInfo> variables = classes.get(className).getVariablesInfo();
                for (String variableName : variables.keySet()) {
                    VariableInfo var = variables.get(variableName);
                    ClassInfo classInfo = classes.get(className);
                    System.out.println(
                        className+", "+
                        variableName+", "+
                        var.getReads()+", "+
                        var.getWrites()+", "+
                        var.getNbOfInstances()+", "+
                        classInfo.getNbOfInstances());
                }
            }
        }
    }

}
