import ch.usi.dag.disl.annotation.After;
import ch.usi.dag.disl.annotation.Before;
import ch.usi.dag.disl.dynamiccontext.DynamicContext;
import ch.usi.dag.disl.staticcontext.MethodStaticContext;
import ch.usi.dag.disl.marker.BodyMarker;
import java.lang.reflect.*;
import java.util.*;

public class DiSLClass {

	@Before(marker = BodyMarker.class)
	public static void beforeMethod(DynamicContext di, MethodStaticContext msc) {
		try {
			String methodName = msc.thisMethodName();
			if (methodName.equals("<init>")) { // method is constructor
				String className = di.getThis().getClass().getName();
				if (!Process.allVariablesAdded(className)) {
					Field[] fields = di.getThis().getClass().getDeclaredFields();
					Process.addAllVariables(className, fields);
				}
				int objHashCode = di.getThis().hashCode();
				Process.addInstance(className, objHashCode);
			}
		}
		catch(Exception e) {

		}
		
	}

	@After(marker = FieldReadMarker.class)
	public static void afterReadField(DynamicContext di, MethodStaticContext msc, FieldInsnStaticContext fsc) {
		try {
			String fieldOwnerClassName = fsc.getFieldOwnerClassName();
			String methodClassName = msc.thisClassCanonicalName();
			String variableName = fsc.getFieldName();
			int objHashCode = di.getThis().hashCode();
			Process.processRead(fieldOwnerClassName, methodClassName, variableName, objHashCode);
		}
		catch(Exception e) {

		}
	}

	@After(marker = FieldWriteMarker.class)
	public static void afterWriteField(DynamicContext di, MethodStaticContext msc, FieldInsnStaticContext fsc) {
		try {
			String fieldOwnerClassName = fsc.getFieldOwnerClassName();
			String methodClassName = msc.thisClassCanonicalName();
			String variableName = fsc.getFieldName();
			int objHashCode = di.getThis().hashCode();
			Process.processWrite(fieldOwnerClassName, methodClassName, variableName, objHashCode);
		}
		catch(Exception e) {

		}
	}

}
