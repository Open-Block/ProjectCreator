package org.openblock.creator.code.variable.field;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.openblock.creator.code.Visibility;
import org.openblock.creator.code.call.Returnable;
import org.openblock.creator.code.call.returntype.ReturnType;
import org.openblock.creator.code.call.returntype.StatedReturnType;
import org.openblock.creator.code.variable.VariableBuilder;

public class FieldBuilder implements VariableBuilder<FieldBuilder> {

	private String name;
	private boolean isFinal;
	private boolean isStatic;
	private Visibility visibility;
	private Returnable.ReturnableLine assigned;
	private ReturnType statedReturnType;

	public boolean isStatic() {
		return this.isStatic;
	}

	public FieldBuilder setStatic(boolean isStatic) {
		this.isStatic = isStatic;
		return this;
	}

	public @Nullable Visibility getVisibility() {
		return this.visibility;
	}

	public FieldBuilder setVisibility(@NotNull Visibility visibility) {
		this.visibility = visibility;
		return this;
	}

	public @Nullable Returnable.ReturnableLine getAssigned() {
		return this.assigned;
	}

	public FieldBuilder setAssigned(@Nullable Returnable.ReturnableLine line) {
		this.assigned = line;
		return this;
	}

	@Override
	public Field build() {
		if (assigned != null) {
			return new InitiatedField(this);
		}
		if (this.statedReturnType != null && this.statedReturnType instanceof StatedReturnType) {
			return new UninitiatedField(this);
		}
		throw new IllegalStateException(
				"The 'assignment' value must be set or the 'return type' must be set to a 'StatedReturnType'");
	}

	@Override
	public @Nullable ReturnType getReturnType() {
		if (this.statedReturnType != null) {
			return this.statedReturnType;
		}
		if (this.assigned != null) {
			return this.assigned.getReturnType();
		}
		return null;
	}

	@Override
	public FieldBuilder setReturnType(@Nullable ReturnType type) {
		this.statedReturnType = type;
		return this;
	}

	@Override
	public @Nullable String getName() {
		return this.name;
	}

	@Override
	public FieldBuilder setName(@NotNull String name) {
		this.name = name;
		return this;
	}

	@Override
	public boolean isFinal() {
		return this.isFinal;
	}

	@Override
	public FieldBuilder setFinal(boolean fin) {
		this.isFinal = fin;
		return this;
	}
}
