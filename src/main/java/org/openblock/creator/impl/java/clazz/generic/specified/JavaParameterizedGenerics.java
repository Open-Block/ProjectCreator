package org.openblock.creator.impl.java.clazz.generic.specified;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.Nameable;
import org.openblock.creator.code.clazz.generic.IGeneric;
import org.openblock.creator.code.clazz.generic.specified.SpecifiedGenerics;
import org.openblock.creator.impl.java.clazz.generic.JavaGenerics;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaParameterizedGenerics implements SpecifiedGenerics {

    private final @NotNull ParameterizedType type;
    private final @NotNull Nameable reference;

    public JavaParameterizedGenerics(@NotNull Nameable reference, @NotNull ParameterizedType type) {
        this.type = type;
        this.reference = reference;
    }

    @Override
    public List<IGeneric> getGenerics() {
        return Stream.of(this.type.getActualTypeArguments()).flatMap(type -> JavaGenerics.type(type).parallelStream()).collect(Collectors.toList());
    }

    @Override
    public List<SpecifiedGenerics> getSpecifiedGenericClass() {
        return Stream.of(this.type.getActualTypeArguments()).map(type -> JavaGenerics.specified(this.reference, type)).collect(Collectors.toList());
    }

    @Override
    public Nameable getTargetReference() {
        return this.reference;
    }
}
