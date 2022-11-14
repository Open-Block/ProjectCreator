package org.openblock.creator.code.variable.local;

import org.openblock.creator.code.call.Returnable;
import org.openblock.creator.code.call.returntype.ReturnType;
import org.openblock.creator.code.variable.VariableBuilder;

public class LocalVariableBuilder implements VariableBuilder<LocalVariableBuilder> {

	private boolean isFinal;
	private ReturnType type;
	private Returnable.ReturnableLine assigned;
	private String name;

	public Returnable.ReturnableLine getAssigned() {
		return assigned;
	}

	public LocalVariableBuilder setAssigned(Returnable.ReturnableLine assigned) {
		this.assigned = assigned;
		return this;
	}

	@Override
	public LocalVariable build() {
		if (assigned != null) {
			return new AssignedLocalVariable(this.name, this.isFinal, this.assigned, this.type);
		}
		if (type != null) {
			return new UnassignedLocalVariable(this.name, this.isFinal, this.type);
		}
		throw new RuntimeException("ReturnType or Assigned must be specified");
	}

	@Override
	public ReturnType getReturnType() {
		return this.type;
	}

	@Override
	public LocalVariableBuilder setReturnType(ReturnType type) {
		this.type = type;
		return this;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public LocalVariableBuilder setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public boolean isFinal() {
		return this.isFinal;
	}

	@Override
	public LocalVariableBuilder setFinal(boolean fin) {
		this.isFinal = fin;
		return this;
	}
}
