package org.openblock.creator.code.function.caller;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.Codeable;
import org.openblock.creator.code.call.Caller;
import org.openblock.creator.code.clazz.IClass;
import org.openblock.creator.code.function.IConstructor;
import org.openblock.creator.code.function.IFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public abstract class FunctionCaller<F extends IFunction> implements Caller.Parameter {

    protected final F function;
    protected final List<Codeable> parameters = new ArrayList<>();

    public FunctionCaller(F function) {
        this.function = function;
    }

    @Override
    public F getCallable() {
        return this.function;
    }

    @Override
    public @NotNull SortedSet<IClass> getImports() {
        SortedSet<IClass> set = new TreeSet<>();
        set.add(this.getCallable().getTargetClass());
        set.addAll(this
                .getParameters()
                .parallelStream()
                .flatMap(c -> c.getImports().parallelStream())
                .collect(Collectors.toSet()));
        return set;
    }

    @Override
    public List<Codeable> getParameters() {
        return this.parameters;
    }
}
