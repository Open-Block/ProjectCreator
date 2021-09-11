package org.openblock.creator.impl.custom.clazz;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.Visibility;
import org.openblock.creator.code.clazz.IClass;
import org.openblock.creator.code.clazz.generic.IGeneric;
import org.openblock.creator.code.clazz.generic.specified.SpecifiedGenerics;
import org.openblock.creator.code.function.IFunction;
import org.openblock.creator.code.variable.field.Field;

import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractCustomClass implements IClass {

    protected final String name;
    protected final String[] pack;
    protected final boolean isFinal;
    protected final Visibility visibility;
    protected @NotNull List<IGeneric> generics = new ArrayList<>();
    protected @NotNull List<Field> fields = new ArrayList<>();
    protected @NotNull SortedSet<SpecifiedGenerics> implementations = new TreeSet<>(Comparator.comparing((g) -> ((IClass) g.getTargetReference())));
    protected @NotNull Set<IClass> nestedClasses = new HashSet<>();
    protected @NotNull Set<IFunction> functions = new HashSet<>();

    public AbstractCustomClass(CustomClassBuilder builder) {
        this.name = builder.getName();
        this.pack = builder.getPackageLocation();
        this.visibility = builder.getVisibility();
        this.isFinal = builder.isFinal();
        this.functions.addAll(builder
                .getFunctions()
                .stream()
                .map(f -> f.build(this))
                .collect(Collectors.toList()));

        if (this.name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
    }

    @Override
    public @NotNull String writeCode(int indent) {
        return new CustomClassWriter<>().write(this);
    }

    @Override
    public @NotNull String getName() {
        return this.name;
    }

    @Override
    public @NotNull String[] getPackage() {
        return this.pack;
    }

    @Override
    public boolean isFinal() {
        return this.isFinal;
    }

    @Override
    public @NotNull Visibility getVisibility() {
        return this.visibility;
    }

    @Override
    public @NotNull List<IGeneric> getGenerics() {
        return this.generics;
    }

    @Override
    public @NotNull List<Field> getFields() {
        return this.fields;
    }

    @Override
    public @NotNull SortedSet<SpecifiedGenerics> getImplements() {
        return this.implementations;
    }

    @Override
    public @NotNull Set<IClass> getNestedClasses() {
        return this.nestedClasses;
    }

    @Override
    public @NotNull Set<IFunction> getFunctions() {
        return this.functions;
    }
}
