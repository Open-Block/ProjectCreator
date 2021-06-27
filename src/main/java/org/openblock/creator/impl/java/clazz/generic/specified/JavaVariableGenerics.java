package org.openblock.creator.impl.java.clazz.generic.specified;

import org.openblock.creator.code.Nameable;
import org.openblock.creator.code.clazz.generic.IGeneric;
import org.openblock.creator.code.clazz.generic.specified.SpecifiedGenerics;
import org.openblock.creator.impl.java.clazz.generic.JavaGenerics;

import java.lang.reflect.TypeVariable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class JavaVariableGenerics<T extends TypeVariable<?>> implements SpecifiedGenerics {

    private final T type;
    private final Nameable reference;

    public JavaVariableGenerics(Nameable reference, T type) {
        this.type = type;
        this.reference = reference;
    }

    @Override
    public List<IGeneric> getGenerics() {
        return Collections.singletonList(JavaGenerics.typeVariable(this.type));
    }

    @Override
    public List<SpecifiedGenerics> getSpecifiedGenericClass() {
        return Collections.emptyList();
    }

    @Override
    public Nameable getTargetReference() {
        return this.reference;
    }

    @Override
    public String getDisplayName() {
        return this.getGenerics().stream().map(g -> g.writeCode(false)).collect(Collectors.joining(", "));
    }
}
