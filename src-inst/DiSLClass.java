import ch.usi.dag.disl.annotation.After;
import ch.usi.dag.disl.annotation.Before;
import ch.usi.dag.disl.dynamiccontext.DynamicContext;
import ch.usi.dag.disl.staticcontext.MethodStaticContext;
import ch.usi.dag.disl.marker.BodyMarker;
import java.lang.reflect.*;
import java.util.*;

public class DiSLClass {

	@After(marker = BodyMarker.class)
	public static void afterMethod(DynamicContext di, MethodStaticContext msc) {
		try {
			String methodName = msc.thisMethodName();
			if(methodName.equals("<init>")){ // method is constructor
				String className = msc.thisClassCanonicalName();
				if(!Process.allVariablesAdded(className)){
					Field[] fields = di.getThis().getClass().getDeclaredFields();
					Process.addAllVariables(className, fields);
				}
				int objHashCode = di.getThis().hashCode();
				Process.addInstance(className, objHashCode);
			}
		}
		catch (Throwable t){

		}
	}

	@After(marker = FieldReadMarker.class)
	public static void afterReadField(DynamicContext di, MethodStaticContext msc, FieldInsnStaticContext fsc) {
		try {
			String className = msc.thisClassCanonicalName();
			String variableName = fsc.getFieldName();
			int objHashCode = di.getThis().hashCode();
			Process.processRead(className, variableName, objHashCode);
		}
		catch (Throwable t){

		}
	}
	
	@After(marker = FieldWriteMarker.class)
	public static void afterWriteField(DynamicContext di, MethodStaticContext msc, FieldInsnStaticContext fsc) {
		try {
			String className = msc.thisClassCanonicalName();
			String variableName = fsc.getFieldName();
			int objHashCode = di.getThis().hashCode();
			Process.processWrite(className, variableName, objHashCode);
		}
		catch (Throwable t){

		}
	}
	
}
