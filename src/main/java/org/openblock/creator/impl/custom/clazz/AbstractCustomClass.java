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

    protected String name;
    protected String[] pack;
    protected boolean isFinal;
    protected Visibility visibility;
    protected List<IGeneric> generics = new ArrayList<>();
    protected List<Field> fields = new ArrayList<>();
    protected SortedSet<SpecifiedGenerics> implementations = new TreeSet<>(Comparator.comparing((g) -> ((IClass) g.getTargetReference())));
    protected Set<IClass> nestedClasses = new HashSet<>();
    protected Set<IFunction> functions = new HashSet<>();

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
    public List<IGeneric> getGenerics() {
        return this.generics;
    }

    @Override
    public List<Field> getFields() {
        return this.fields;
    }

    @Override
    public SortedSet<SpecifiedGenerics> getImplements() {
        return this.implementations;
    }

    @Override
    public Set<IClass> getNestedClasses() {
        return this.nestedClasses;
    }

    @Override
    public Set<IFunction> getFunctions() {
        return this.functions;
    }
}
