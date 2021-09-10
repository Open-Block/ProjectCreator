package org.openblock.creator.code.variable;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.call.Caller;
import org.openblock.creator.code.call.returntype.ReturnType;
import org.openblock.creator.code.clazz.IClass;
import utils.OpenStringUtils;

import java.util.SortedSet;
import java.util.TreeSet;

public class VariableCaller<V extends IVariable> implements Caller {

    private final V parameter;

    public VariableCaller(V parameter) {
        this.parameter = parameter;
    }

    @Override
    public @NotNull String writeCode(int indent) {
        return OpenStringUtils.repeat(indent, '\t') + this.parameter.getName();
    }

    @Override
    public @NotNull SortedSet<IClass> getImports() {
        return new TreeSet<>();
    }

    @Override
    public V getCallable() {
        return this.parameter;
    }
}
