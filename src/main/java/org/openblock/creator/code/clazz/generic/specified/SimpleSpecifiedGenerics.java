package org.openblock.creator.code.clazz.generic.specified;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.Nameable;
import org.openblock.creator.code.clazz.generic.IGeneric;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleSpecifiedGenerics implements SpecifiedGenerics {

	private final Nameable type;
	private final List<IGeneric> generics = new LinkedList<>();

	public SimpleSpecifiedGenerics(@NotNull Nameable nameable, List<IGeneric> generics) {
		this.type = nameable;
		this.generics.addAll(generics);
	}

	@Override
	public List<IGeneric> getGenerics() {
		return Collections.unmodifiableList(this.generics);
	}

	@Override
	public List<SpecifiedGenerics> getSpecifiedGenericClass() {
		return this.generics.stream().map(NoGenerics::new).collect(Collectors.toList());
	}

	@Override
	public @NotNull Nameable getTargetReference() {
		return this.type;
	}
}
