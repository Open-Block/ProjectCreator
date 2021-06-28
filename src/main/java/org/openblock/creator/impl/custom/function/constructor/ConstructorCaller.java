package org.openblock.creator.impl.custom.function.constructor;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.Codeable;
import org.openblock.creator.code.call.Caller;
import org.openblock.creator.code.clazz.IClass;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class ConstructorCaller implements Caller.Parameter {

    private final CustomConstructor constructor;
    private final List<Codeable> parameters = new ArrayList<>();

    public ConstructorCaller(CustomConstructor constructor) {
        this.constructor = constructor;
    }

    @Override
    public @NotNull String writeCode(int indent) {
        return "new " + this.constructor.getTargetClass().getName() + "(" + this.parameters.stream().map(p -> p.writeCode(0)).collect(Collectors.joining(", ")) + ")";
    }

    @Override
    public @NotNull SortedSet<IClass> getImports() {
        SortedSet<IClass> set = new TreeSet<>();
        set.add(this.constructor.getTargetClass());
        set.addAll(this
                .getParameters()
                .parallelStream()
                .flatMap(c -> c.getImports().parallelStream())
                .collect(Collectors.toSet()));
        return set;
    }

    @Override
    public CustomConstructor getCallable() {
        return this.constructor;
    }

    @Override
    public List<Codeable> getParameters() {
        return this.parameters;
    }
}
