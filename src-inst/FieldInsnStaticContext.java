import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;

import ch.usi.dag.disl.staticcontext.AbstractStaticContext;

public class FieldInsnStaticContext extends AbstractStaticContext {

    public String getFieldName() {

        AbstractInsnNode startInsn = staticContextData.getRegionStart();

    	return ((FieldInsnNode)startInsn).name;
    }

}
