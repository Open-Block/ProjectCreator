package org.openblock.creator.code.variable.parameter;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.call.returntype.StatedReturnType;
import org.openblock.creator.code.clazz.IClass;
import org.openblock.creator.code.clazz.type.BasicType;
import org.openblock.creator.code.clazz.type.IType;
import org.openblock.creator.code.variable.IVariable;
import org.openblock.creator.code.variable.VariableCaller;

import java.util.SortedSet;
import java.util.TreeSet;

public class Parameter implements IVariable {

    protected boolean isFinal;
    protected @NotNull String name;
    protected @NotNull StatedReturnType returnType;

    public Parameter(@NotNull StatedReturnType type, @NotNull String name, boolean isFinal) {
        isValidName(name);
        this.isFinal = isFinal;
        this.name = name;
        this.returnType = type;
    }

    protected void isValidName(@NotNull String name) {
        if (name.contains(" ") || name.contains("\t") || name.contains("\n")) {
            throw new IllegalArgumentException("'" + name + "' cannot contain whitespace");
        }
    }

    @Override
    public boolean isFinal() {
        return this.isFinal;
    }

    @Override
    public @NotNull String getName() {
        return this.name;
    }

    @Override
    public @NotNull StatedReturnType getReturnType() {
        return this.returnType;
    }

    @Override
    public @NotNull String writeCode(int indent) {
        String ret = this.getReturnType().getDisplayText() + " " + this.getName();
        if (this.isFinal()) {
            ret = "final " + ret;
        }
        return ret;
    }

    @Override
    public @NotNull SortedSet<IClass> getImports() {
        SortedSet<IClass> set = new TreeSet<>();
        IType type = this.getReturnType().getType();
        if (type instanceof BasicType b) {
            set.add(b.getTargetClass());
        }
        return set;
    }

    @Override
    public @NotNull VariableCaller<Parameter> createCaller() {
        return new VariableCaller<>(this);
    }
}
