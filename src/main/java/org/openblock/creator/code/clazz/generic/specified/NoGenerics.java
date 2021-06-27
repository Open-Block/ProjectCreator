package org.openblock.creator.code.clazz.generic.specified;

import org.openblock.creator.code.Nameable;
import org.openblock.creator.code.clazz.generic.IGeneric;

import java.util.Collections;
import java.util.List;

public class NoGenerics implements SpecifiedGenerics {

    private Nameable reference;

    public NoGenerics(Nameable nameable) {
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
    public Nameable getTargetReference() {
        return this.reference;
    }
}
