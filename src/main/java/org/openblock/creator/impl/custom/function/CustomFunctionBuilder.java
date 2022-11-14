package org.openblock.creator.impl.custom.function;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.openblock.creator.code.CodeBuilder;
import org.openblock.creator.code.Codeable;
import org.openblock.creator.code.Visibility;
import org.openblock.creator.code.call.returntype.ReturnType;
import org.openblock.creator.code.clazz.IClass;
import org.openblock.creator.code.clazz.generic.IGeneric;
import org.openblock.creator.code.variable.parameter.Parameter;
import org.openblock.creator.impl.custom.function.constructor.CustomConstructor;
import org.openblock.creator.impl.custom.function.method.CustomMethod;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomFunctionBuilder implements CodeBuilder<CustomFunctionBuilder> {

    protected String name;
    protected Visibility visibility;
    protected ReturnType returnType;
    protected boolean isStatic;
    protected List<Parameter> parameters = new ArrayList<>();
    protected List<IGeneric> generics = new ArrayList<>();
    protected List<Codeable> codeBlock = new ArrayList<>();
    protected IClass classFor;

    public IClass getClassFor() {
        return classFor;
    }

    public CustomFunctionBuilder setClassFor(IClass classFor) {
        this.classFor = classFor;
        return this;
    }

    public ReturnType getReturnType() {
        return returnType;
    }

    public CustomFunctionBuilder setReturnType(@Nullable ReturnType returnType) {
        this.returnType = returnType;
        return this;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public CustomFunctionBuilder setStatic(boolean aStatic) {
        isStatic = aStatic;
        return this;
    }

    public CustomFunctionBuilder addParameters(Collection<Parameter> parameters) {
        this.parameters.addAll(parameters);
        return this;
    }

    public CustomFunctionBuilder addGenerics(Collection<IGeneric> generics) {
        this.generics.addAll(generics);
        return this;
    }

    public CustomFunctionBuilder addCodeBlock(Collection<Codeable> codeBlock) {
        this.codeBlock.addAll(codeBlock);
        return this;
    }

    public String getName() {
        return name;
    }

    public CustomFunctionBuilder setName(@Nullable String name) {
        this.name = name;
        return this;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public CustomFunctionBuilder setVisibility(@NotNull Visibility visibility) {
        this.visibility = visibility;
        return this;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public List<IGeneric> getGenerics() {
        return generics;
    }

    public List<Codeable> getCodeBlock() {
        return codeBlock;
    }

    public AbstractCustomFunction build(IClass clazz) {
        if (this.getName() == null && this.getReturnType() == null) {
            return new CustomConstructor(clazz, this);
        }
        if (this.getReturnType() == null) {
            throw new RuntimeException("A ReturnType was specified without a Name. Please either don't specify either or both");
        }
        if (this.getName() == null) {
            throw new RuntimeException("A Name was specified without a ReturnType. Please either don't specify either or both");
        }
        return new CustomMethod(clazz, this);
    }

    @Override
    public Codeable build() {
        return build(this.classFor);
    }
}
