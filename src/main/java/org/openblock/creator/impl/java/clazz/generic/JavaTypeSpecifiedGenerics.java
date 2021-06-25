package org.openblock.creator.impl.java.clazz.generic;

import org.openblock.creator.code.Nameable;
import org.openblock.creator.code.clazz.generic.IGeneric;
import org.openblock.creator.code.clazz.generic.SpecifiedGenerics;

import java.lang.reflect.Type;
import java.util.List;

public class JavaTypeSpecifiedGenerics implements SpecifiedGenerics {

    private final Type type;

    public JavaTypeSpecifiedGenerics(Type type) {
        this.type = type;
    }

    @Override
    public List<IGeneric> getGenerics() {
        return null;
    }

    @Override
    public List<SpecifiedGenerics> getSpecifiedGenericClass() {
        return null;
    }

    @Override
    public Nameable getTargetReference() {
        return null;
    }
}
