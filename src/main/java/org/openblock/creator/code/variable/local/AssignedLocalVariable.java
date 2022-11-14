package org.openblock.creator.code.variable.local;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.openblock.creator.code.call.Returnable;
import org.openblock.creator.code.call.returntype.ReturnType;
import org.openblock.creator.code.variable.IAssignedVariable;

public class AssignedLocalVariable extends LocalVariable implements IAssignedVariable {

	private final @NotNull Returnable.ReturnableLine line;
	private final @Nullable ReturnType type;

	public AssignedLocalVariable(@NotNull String name, boolean isFinal, @NotNull Returnable.ReturnableLine line,
			@Nullable ReturnType returnType) {
		super(name, isFinal);
		this.line = line;
		this.type = returnType;
	}

	@Override
	public @NotNull String writeCode(int indent) {
		return this.getReturnType().getDisplayText() + " " + this.getName() + " = " + this.getAssigned().writeCode(0);
	}

	@Override
	public @NotNull ReturnType getReturnType() {
		if (this.type != null) {
			return type;
		}
		return this.line.getReturnType();
	}

	@Override
	public @NotNull ReturnableLine getAssigned() {
		return this.line;
	}
}
