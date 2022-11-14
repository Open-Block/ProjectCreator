package org.openblock.creator.code.line.primitive;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.CodeBuilder;
import org.openblock.creator.code.clazz.IClass;
import org.openblock.creator.impl.java.clazz.JavaClass;

import java.util.SortedSet;
import java.util.TreeSet;

public class StringConstructor implements PrimitiveConstructor<String> {

	private final @NotNull String value;

	public StringConstructor(@NotNull String value) {
		this.value = value;
	}

	@Override
	public @NotNull String getValue() {
		return this.value;
	}

	@Override
	public @NotNull String writeCode(int indent) {
		return "\"" + this.value + "\"";
	}

	@Override
	public @NotNull SortedSet<IClass> getImports() {
		return new TreeSet<>();
	}

	@Override
	public @NotNull PrimitiveValueBuilder toBuilder() {
		return new PrimitiveValueBuilder().setValue(this.value);
	}

	@Override
	public JavaClass getJavaClass() {
		return new JavaClass(String.class);
	}
}
