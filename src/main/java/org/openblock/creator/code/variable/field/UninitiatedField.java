package org.openblock.creator.code.variable.field;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.Visibility;
import org.openblock.creator.code.call.returntype.StatedReturnType;
import org.openblock.creator.code.variable.VariableCaller;

import java.util.Objects;

public class UninitiatedField extends Field {

	private final @NotNull StatedReturnType state;

	public UninitiatedField(FieldBuilder builder) {
		super(builder);
		this.state = (StatedReturnType) Objects.requireNonNull(builder.getReturnType());
	}

	public UninitiatedField(@NotNull Visibility visibility, @NotNull StatedReturnType statedType, @NotNull String name,
			boolean isFinal, boolean isStatic) {
		super(visibility, name, isFinal, isStatic);
		this.state = statedType;
	}

	@Override
	public @NotNull StatedReturnType getReturnType() {
		return this.state;
	}

	@Override
	public @NotNull VariableCaller<UninitiatedField> createCaller() {
		return new VariableCaller<>(this);
	}

	@Override
	public @NotNull FieldBuilder toBuilder() {
		return new FieldBuilder().setReturnType(this.state)
				.setFinal(this.isFinal)
				.setVisibility(this.visibility)
				.setStatic(this.isStatic);
	}

}
