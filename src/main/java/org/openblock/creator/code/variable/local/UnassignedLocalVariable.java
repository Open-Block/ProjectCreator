package org.openblock.creator.code.variable.local;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.call.returntype.ReturnType;

public class UnassignedLocalVariable extends LocalVariable {

	private final @NotNull ReturnType type;

	public UnassignedLocalVariable(@NotNull String name, boolean isFinal, @NotNull ReturnType type) {
		super(name, isFinal);
		this.type = type;
	}

	@Override
	public @NotNull String writeCode(int indent) {
		return type.getDisplayText() + " " + this.getName();
	}

	@Override
	public @NotNull ReturnType getReturnType() {
		return this.type;
	}
}
