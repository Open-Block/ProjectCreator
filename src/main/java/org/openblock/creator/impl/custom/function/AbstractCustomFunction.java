package org.openblock.creator.impl.custom.function;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.Codeable;
import org.openblock.creator.code.Visibility;
import org.openblock.creator.code.clazz.IClass;
import org.openblock.creator.code.clazz.generic.IGeneric;
import org.openblock.creator.code.function.IFunction;
import org.openblock.creator.code.variable.parameter.Parameter;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCustomFunction implements IFunction {

    protected @NotNull String name;
    protected @NotNull Visibility visibility;
    protected @NotNull IClass clazz;
    protected boolean isAbstract;
    protected List<Parameter> parameters = new ArrayList<>();
    protected List<IGeneric> generics = new ArrayList<>();
    protected List<Codeable> codeBlock = new ArrayList<>();

    public AbstractCustomFunction(@NotNull IClass clazz, CustomFunctionBuilder builder) {
        this.name = builder.getName();
        this.clazz = clazz;
        this.visibility = builder.getVisibility();
        this.parameters.addAll(builder.getParameters());
        this.generics.addAll(builder.getGenerics());
        this.codeBlock.addAll(builder.getCodeBlock());
    }

    @Override
    public @NotNull IClass getTargetClass() {
        return this.clazz;
    }

    @Override
    public @NotNull String getName() {
        return this.name;
    }

    @Override
    public List<Parameter> getParameters() {
        return this.parameters;
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
    public List<Codeable> getCodeBlock() {
        return this.codeBlock;
    }
}
