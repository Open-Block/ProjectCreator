package org.openblock.creator.code.variable.field;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.openblock.creator.code.Visibility;
import org.openblock.creator.code.call.returntype.ReturnType;
import org.openblock.creator.code.call.returntype.VoidedReturnType;
import org.openblock.creator.code.clazz.IClass;
import org.openblock.creator.code.variable.IAssignedVariable;
import org.openblock.creator.code.variable.VariableCaller;

import java.util.SortedSet;

public class InitiatedField extends Field implements IAssignedVariable {

	private final @NotNull ReturnableLine caller;
	private final @Nullable ReturnType type;

	public InitiatedField(FieldBuilder builder) {
		super(builder);
		this.type = builder.getReturnType();
		this.caller = builder.getAssigned();
	}

	public InitiatedField(@NotNull Visibility visibility, @NotNull ReturnableLine line, @NotNull String name,
			boolean isFinal, boolean isStatic) {
		this(visibility, line, null, name, isFinal, isStatic);
	}

	public InitiatedField(@NotNull Visibility visibility, @NotNull ReturnableLine line, @Nullable ReturnType type,
			@NotNull String name, boolean isFinal, boolean isStatic) {
		super(visibility, name, isFinal, isStatic);
		this.caller = line;
		this.type = type;
	}

	@Override
	public @NotNull String writeCode(int indent) {
		return super.writeCode(indent) + " = " + caller.writeCode(0);
	}

	@Override
	public @NotNull FieldBuilder toBuilder() {
		return new FieldBuilder()
				.setStatic(this.isStatic)
				.setFinal(this.isFinal)
				.setReturnType(this.type)
				.setVisibility(this.visibility)
				.setAssigned(this.caller)
				.setName(this.name);
	}

	@Override
	public @NotNull SortedSet<IClass> getImports() {
		SortedSet<IClass> classes = super.getImports();
		classes.addAll(this.getAssigned().getImports());
		return classes;
	}

	@Deprecated(forRemoval = true)
	public @NotNull ReturnableLine getCode() {
		return this.getAssigned();
	}

	@Override
	public @NotNull ReturnableLine getAssigned() {
		return this.caller;
	}

	@Override
	public @NotNull ReturnType getReturnType() {
		if (this.type != null) {
			return this.type;
		}
		@NotNull ReturnType returnType = this.caller.getReturnType();
		if (returnType instanceof VoidedReturnType) {
			throw new RuntimeException("Initiated Field cannot have a caller returning Void");
		}
		return returnType;
	}

	@Override
	public @NotNull VariableCaller<InitiatedField> createCaller() {
		return new VariableCaller<>(this);
	}
}
