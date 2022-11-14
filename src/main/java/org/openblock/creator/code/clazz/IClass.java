package org.openblock.creator.code.clazz;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.Codeable;
import org.openblock.creator.code.Nameable;
import org.openblock.creator.code.OpenCompares;
import org.openblock.creator.code.Visibility;
import org.openblock.creator.code.call.Callable;
import org.openblock.creator.code.call.Caller;
import org.openblock.creator.code.call.CallerProvider;
import org.openblock.creator.code.clazz.caller.StaticClassCaller;
import org.openblock.creator.code.clazz.generic.IGeneric;
import org.openblock.creator.code.clazz.generic.specified.SpecifiedGenerics;
import org.openblock.creator.code.function.IFunction;
import org.openblock.creator.code.function.IMethod;
import org.openblock.creator.code.variable.field.Field;
import org.openblock.creator.impl.custom.clazz.CustomClassBuilder;
import org.openblock.creator.impl.custom.function.method.CustomMethod;

import java.util.*;
import java.util.stream.Collectors;

public interface IClass extends Nameable, Codeable, CallerProvider, Abstractable, Comparable<IClass> {

	@Override
	@NotNull CustomClassBuilder toBuilder();

	@NotNull String[] getPackage();

	boolean isFinal();

	@NotNull ClassType getClassType();

	@NotNull Visibility getVisibility();

	List<IGeneric> getGenerics();

	List<Field> getFields();

	Optional<SpecifiedGenerics> getExtendingClass();

	Collection<SpecifiedGenerics> getImplements();

	Collection<IClass> getNestedClasses();

	Collection<IFunction> getFunctions();

	@Override
	default TreeSet<Caller> getCallers() {
		Set<Callable> callables = new HashSet<>();
		callables.addAll(this.getFields());
		callables.addAll(this.getFunctions(IMethod.class));
		TreeSet<Caller> set = callables.parallelStream()
				.map(Callable::createCaller)
				.collect(Collectors.toCollection(() -> new TreeSet<>(OpenCompares.CALLER_COMPARE)));
		return set;
	}

	default TreeSet<Caller> getStaticCallers() {
		TreeSet<Caller> callers = new TreeSet<>(OpenCompares.CALLER_COMPARE);
		callers.addAll(this.getFields()
				.stream()
				.filter(Field::isStatic)
				.map(Callable::createCaller)
				.collect(Collectors.toSet()));
		callers.addAll(this.getFunctions(CustomMethod.class)
				.stream()
				.filter(CustomMethod::isStatic)
				.map(Callable::createCaller)
				.collect(Collectors.toSet()));
		return callers;
	}

	default @NotNull String getFullName() {
		return String.join(".", this.getPackage()) + "." + this.getName();
	}

	default <F extends IFunction> List<F> getFunctions(Class<F> clazz) {
		return this.getFunctions()
				.parallelStream()
				.filter(clazz::isInstance)
				.map(f -> (F) f)
				.toList();
	}

	default boolean hasInheritance(IClass clazz) {
		if (clazz.equals(this)) {
			return true;
		}
		boolean iImpl = this.getImplements().parallelStream().anyMatch(iface -> iface.hasInheritance(clazz));
		if (iImpl) {
			return true;
		}
		Optional<SpecifiedGenerics> opExtending = this.getExtendingClass();
		return opExtending
				.map(specifiedGenerics -> specifiedGenerics.hasInheritance(clazz))
				.orElse(false);

	}

	@Override
	default int compareTo(@NotNull IClass o) {
		return this.getFullName().compareTo(o.getFullName());
	}

	default @NotNull StaticClassCaller createStaticCaller() {
		return new StaticClassCaller(this);
	}

	@Override
	default @NotNull SortedSet<IClass> getImports() {
		SortedSet<IClass> classes = new TreeSet<>();
		classes.addAll(this.getFields()
				.parallelStream()
				.flatMap(f -> f.getReturnType().getType().getClasses().parallelStream())
				.toList());
		classes.addAll(this.getFunctions()
				.parallelStream()
				.flatMap(f -> f.getImports().parallelStream())
				.collect(Collectors.toSet()));
		classes.addAll(this.getImplements()
				.parallelStream()
				.filter(g -> g.getTargetReference() instanceof IClass)
				.map(g -> (IClass) g.getTargetReference())
				.collect(Collectors.toSet()));
		this.getExtendingClass().ifPresent(extending -> {
			if (extending.getTargetReference() instanceof IClass) {
				classes.add((IClass) extending.getTargetReference());
			}
		});
		return classes;
	}
}
