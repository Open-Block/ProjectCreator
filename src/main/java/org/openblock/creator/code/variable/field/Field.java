package org.openblock.creator.code.variable.field;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.Visibility;
import org.openblock.creator.code.clazz.IClass;
import org.openblock.creator.code.clazz.type.BasicType;
import org.openblock.creator.code.clazz.type.IType;
import org.openblock.creator.code.variable.IVariable;

import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

public abstract class Field implements IVariable {

	protected @NotNull String name;
	protected boolean isFinal;
	protected boolean isStatic;
	protected @NotNull Visibility visibility;

	public Field(@NotNull Visibility visibility, @NotNull String name, boolean isFinal, boolean isStatic) {
		this.name = name;
		this.isFinal = isFinal;
		this.isStatic = isStatic;
		this.visibility = visibility;
	}

	public Field(@NotNull FieldBuilder builder) {
		this.name = Objects.requireNonNull(builder.getName());
		this.isFinal = builder.isFinal();
		this.isStatic = builder.isStatic();
		this.visibility = Objects.requireNonNull(builder.getVisibility());
	}

	@Override
	public abstract @NotNull FieldBuilder toBuilder();

	public @NotNull Visibility getVisibility() {
		return this.visibility;
	}

	public boolean isStatic() {
		return this.isStatic;
	}

	@Override
	public @NotNull String writeCode(int indent) {
		String ret = " ".repeat(indent * 4) + this.getVisibility().getDisplayName() + " ";
		if (this.isStatic()) {
			ret = ret + "static ";
		}
		if (this.isFinal()) {
			ret = ret + "final ";
		}
		ret = ret + this.getReturnType().getDisplayText() + " ";
		ret = ret + this.getName();
		return ret;
	}

	@Override
	public @NotNull SortedSet<IClass> getImports() {
		SortedSet<IClass> classes = new TreeSet<>();
		IType type = this.getReturnType().getType();
		if (type instanceof BasicType b) {
			classes.add(b.getTargetClass());
		}
		return classes;
	}

	@Override
	public @NotNull String getName() {
		return this.name;
	}

	@Override
	public boolean isFinal() {
		return this.isFinal;
	}
}
