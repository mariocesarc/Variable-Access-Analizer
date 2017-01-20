import java.util.LinkedList;
import java.util.List;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.MethodNode;

import ch.usi.dag.disl.marker.AbstractMarker;
import ch.usi.dag.disl.marker.AbstractDWRMarker;

public class FieldReadMarker extends AbstractDWRMarker {

	public List<MarkedRegion> markWithDefaultWeavingReg(MethodNode method) {
		List<MarkedRegion> regions = new LinkedList<MarkedRegion>();

		InsnList instructions = method.instructions;

		for (AbstractInsnNode instruction : instructions.toArray()) {
			if (instruction instanceof FieldInsnNode && instruction.getOpcode() == 180) {
				regions.add(new MarkedRegion(instruction, instruction));
			}
		}
		return regions;
	}
}
