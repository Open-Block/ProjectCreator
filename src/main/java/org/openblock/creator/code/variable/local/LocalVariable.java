package org.openblock.creator.code.variable.local;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.CodeBuilder;
import org.openblock.creator.code.clazz.IClass;
import org.openblock.creator.code.variable.IVariable;
import org.openblock.creator.code.variable.VariableCaller;

import java.util.SortedSet;
import java.util.TreeSet;

public abstract class LocalVariable implements IVariable {

	private final @NotNull String name;
	private final boolean isFinal;

	public LocalVariable(@NotNull String name, boolean isFinal) {
		this.isFinal = isFinal;
		this.name = name;
	}

	@Override
	public @NotNull SortedSet<IClass> getImports() {
		return new TreeSet<>(this.getReturnType().getType().getClasses());
	}

	@Override
	public @NotNull CodeBuilder<?> toBuilder() {
		return null;
	}

	@Override
	public @NotNull String getName() {
		return this.name;
	}

	@Override
	public @NotNull VariableCaller<LocalVariable> createCaller() {
		return new VariableCaller<>(this);
	}

	@Override
	public boolean isFinal() {
		return this.isFinal;
	}
}
