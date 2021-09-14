package org.openblock.creator.code.clazz.type;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.clazz.IClass;
import org.openblock.creator.code.clazz.generic.IGeneric;
import org.openblock.creator.code.clazz.generic.specified.SpecifiedGenerics;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SpecifiedGenericType implements IType {

    private final SpecifiedGenerics generics;

    public SpecifiedGenericType(SpecifiedGenerics generics) {
        this.generics = generics;
    }

    public SpecifiedGenerics getGenerics() {
        return this.generics;
    }

    @Override
    public @NotNull String getName() {
        return this.generics.getDisplayName();
    }

    @Override
    public List<IClass> getClasses() {
        List<IGeneric> generics = this.generics.getGenerics();
        if (generics.isEmpty() && this.generics.getTargetReference() instanceof IClass clazz) {
            return Collections.singletonList(clazz);
        }

        return generics
                .parallelStream()
                .flatMap(s -> s.getClasses().parallelStream())
                .flatMap(t -> t.getClasses().parallelStream())
                .collect(Collectors.toList());
    }
}
