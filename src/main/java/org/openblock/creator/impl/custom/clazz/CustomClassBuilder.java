package org.openblock.creator.impl.custom.clazz;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.openblock.creator.code.CodeBuilder;
import org.openblock.creator.code.Visibility;
import org.openblock.creator.code.clazz.ClassType;
import org.openblock.creator.code.clazz.IClass;
import org.openblock.creator.code.clazz.generic.IGeneric;
import org.openblock.creator.code.clazz.generic.specified.NoGenerics;
import org.openblock.creator.code.clazz.generic.specified.SpecifiedGenerics;
import org.openblock.creator.impl.custom.clazz.interfacetype.CustomInterface;
import org.openblock.creator.impl.custom.clazz.standardtype.CustomStandardClass;
import org.openblock.creator.impl.custom.function.CustomFunctionBuilder;

import java.util.*;

public class CustomClassBuilder implements CodeBuilder<CustomClassBuilder> {

	private String name;
	private String[] packageLocation;
	private boolean isFinal;
	private boolean isAbstract;
	private ClassType type;
	private Visibility visibility;
	private final @NotNull List<IGeneric> generics = new ArrayList<>();
	private SpecifiedGenerics extending;
	private final @NotNull Set<SpecifiedGenerics> implementing = new HashSet<>();

	private final List<CustomFunctionBuilder> functions = new ArrayList<>();

	public @NotNull List<IGeneric> getGenerics() {
		return this.generics;
	}

	public CustomClassBuilder addGeneric(IGeneric generic) {
		this.generics.add(generic);
		return this;
	}

	public CustomClassBuilder addGenerics(Collection<IGeneric> generics) {
		this.generics.addAll(generics);
		return this;
	}

	public SpecifiedGenerics getExtending() {
		return extending;
	}

	public @NotNull CustomClassBuilder setExtending(@Nullable IClass clazz) {
		if (clazz == null) {
			this.extending = null;
			return this;
		}
		if (clazz.getClassType() != ClassType.STANDARD) {
			throw new RuntimeException("Cannot extend classtype of "
					+ clazz.getClassType().name()
					+ ". If you are attempting to create a none standard class, then use addImplements()");
		}
		this.extending = new NoGenerics(clazz);
		return this;
	}

	public @NotNull CustomClassBuilder setExtending(@Nullable SpecifiedGenerics extending) {
		this.extending = extending;
		return this;
	}

	public @NotNull CustomClassBuilder addImplementing(@NotNull IClass clazz) {
		if (clazz.getClassType() != ClassType.INTERFACE) {
			throw new RuntimeException("Cannot implement classtype of "
					+ clazz.getClassType().name()
					+ ". If you are attempting to create a none interface class, then use setExtends()");
		}
		return addImplementing(new NoGenerics(clazz));
	}

	public @NotNull CustomClassBuilder addImplementing(Collection<SpecifiedGenerics> implementing) {
		this.implementing.addAll(implementing);
		return this;
	}

	public @NotNull CustomClassBuilder addImplementing(@NotNull SpecifiedGenerics implementing) {
		this.implementing.add(implementing);
		return this;
	}

	public boolean isAbstract() {
		return isAbstract;
	}

	public CustomClassBuilder setAbstract(boolean anAbstract) {
		isAbstract = anAbstract;
		return this;
	}

	public List<CustomFunctionBuilder> getFunctions() {
		return functions;
	}

	public CustomClassBuilder addFunctions(Collection<CustomFunctionBuilder> functions) {
		this.functions.addAll(functions);
		return this;
	}

	public CustomClassBuilder addFunctions(CustomFunctionBuilder... functions) {
		return this.addFunctions(Arrays.asList(functions));
	}

	public Visibility getVisibility() {
		return visibility;
	}

	public CustomClassBuilder setVisibility(Visibility visibility) {
		this.visibility = visibility;
		return this;
	}

	public String getName() {
		return name;
	}

	public CustomClassBuilder setName(String name) {
		this.name = name;
		return this;
	}

	public String[] getPackageLocation() {
		return packageLocation;
	}

	public CustomClassBuilder setPackageLocation(String... packageLocation) {
		this.packageLocation = packageLocation;
		return this;
	}

	public boolean isFinal() {
		return isFinal;
	}

	public CustomClassBuilder setFinal(boolean aFinal) {
		isFinal = aFinal;
		return this;
	}

	public ClassType getType() {
		return type;
	}

	public CustomClassBuilder setType(ClassType type) {
		this.type = type;
		return this;
	}

	@Override
	public AbstractCustomClass build() {
		AbstractCustomClass clazz = switch (this.type) {
			case STANDARD -> new CustomStandardClass(this);
			case INTERFACE -> new CustomInterface(this);
			default -> throw new RuntimeException("Type of " + type + " is not implemented yet");
		};
		clazz.implementations.addAll(this.implementing);
		return clazz;
	}
}
