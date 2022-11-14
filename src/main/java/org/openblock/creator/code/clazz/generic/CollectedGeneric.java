package org.openblock.creator.code.clazz.generic;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.CodeBuilder;
import org.openblock.creator.code.Nameable;
import org.openblock.creator.code.clazz.IClass;
import org.openblock.creator.code.clazz.type.BasicType;
import org.openblock.creator.code.clazz.type.IType;
import org.openblock.creator.impl.java.clazz.JavaClass;

import java.util.*;
import java.util.stream.Collectors;

public class CollectedGeneric implements IGeneric {

	private final String name;
	private final boolean extended;
	private final List<IType> classes = new ArrayList<>();

	public CollectedGeneric(SimpleGenericBuilder builder) {
		this.extended = builder.isExtending();
		this.classes.addAll(builder.getTypes());
		this.name = builder.getName();
	}

	public CollectedGeneric(String name) {
		this(name, true, Collections.singleton(new BasicType(new JavaClass(Object.class))));
	}

	public CollectedGeneric(String name, boolean extending, Collection<IType> type) {
		this.name = name;
		this.extended = extending;
		this.classes.addAll(type);
	}

	public @NotNull String writeCode(boolean code) {
		if (!this.name.equals("?") && !code) {
			return this.name;
		}
		return this.name + " " + (this.extended ? "extends" : "super") + " " + this.classes.stream()
				.map(Nameable::getName)
				.collect(Collectors.joining(" & "));
	}

	@Override
	public @NotNull SortedSet<IClass> getImports() {
		return this.classes.parallelStream()
				.flatMap(i -> i.getClasses().parallelStream())
				.collect(Collectors.toCollection(TreeSet::new));
	}

	@Override
	public @NotNull CodeBuilder<?> toBuilder() {
		return new SimpleGenericBuilder().setExtending(this.extended).setName(this.name).addTypes(this.classes);
	}

	@Override
	public @NotNull String getName() {
		return this.name;
	}

	@Override
	public List<IType> getClasses() {
		return this.classes;
	}

	@Override
	public boolean isExtending() {
		return this.extended;
	}
}
