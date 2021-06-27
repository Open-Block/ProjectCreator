package org.openblock.creator.impl.java.clazz.generic.specified;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.clazz.generic.IGeneric;
import org.openblock.creator.code.clazz.generic.specified.SpecifiedGenerics;
import org.openblock.creator.impl.java.clazz.JavaClass;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaClassGenerics implements SpecifiedGenerics {

    private final JavaClass clazz;

    public JavaClassGenerics(JavaClass clazz) {
        this.clazz = clazz;
    }

    @Override
    public List<IGeneric> getGenerics() {
        return this.getSpecifiedGenericClass().stream().flatMap(s -> s.getGenerics().stream()).collect(Collectors.toList());
    }

    @Override
    public List<SpecifiedGenerics> getSpecifiedGenericClass() {
        return Stream
                .of(this.clazz.getTargetClass().getTypeParameters())
                .map(e -> new JavaVariableGenerics<>(this.clazz, e))
                .collect(Collectors.toList());
    }

    @Override
    public @NotNull JavaClass getTargetReference() {
        return this.clazz;
    }

    @Override
    public @NotNull String getDisplayName() {
        return this
                .clazz
                .getName()
                + "<"
                + Stream
                .of(this.clazz.getTargetClass().getTypeParameters())
                .map(t -> new JavaVariableGenerics<>(this.clazz, t))
                .map(JavaVariableGenerics::getDisplayName)
                .collect(Collectors.joining(", "))
                + ">";

    }
}
