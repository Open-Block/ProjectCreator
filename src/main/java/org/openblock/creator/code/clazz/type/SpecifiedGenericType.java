package org.openblock.creator.code.clazz.type;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.clazz.IClass;
import org.openblock.creator.code.clazz.generic.specified.SpecifiedGenerics;

import java.util.List;
import java.util.stream.Collectors;

public class SpecifiedGenericType implements IType {

    private final SpecifiedGenerics generics;

    public SpecifiedGenericType(SpecifiedGenerics generics) {
        this.generics = generics;
    }

    @Override
    public @NotNull String getName() {
        return this.generics.getDisplayName();
    }

    @Override
    public List<IClass> getClasses() {
        return this
                .generics
                .getGenerics()
                .parallelStream()
                .flatMap(s -> s.getClasses().parallelStream())
                .flatMap(t -> t.getClasses().parallelStream())
                .collect(Collectors.toList());
    }
}
