package org.openblock.creator.code.clazz.generic.specified;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.Nameable;
import org.openblock.creator.code.clazz.generic.IGeneric;

import java.util.Collections;
import java.util.List;

public class NoGenerics implements SpecifiedGenerics {

    private final Nameable reference;

    public NoGenerics(@NotNull Nameable nameable) {
        this.reference = nameable;
    }

    @Override
    public List<IGeneric> getGenerics() {
        return Collections.emptyList();
    }

    @Override
    public List<SpecifiedGenerics> getSpecifiedGenericClass() {
        return Collections.emptyList();
    }

    @Override
    public @NotNull Nameable getTargetReference() {
        return this.reference;
    }
}
