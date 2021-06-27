package org.openblock.creator.impl.java.clazz.generic.specified;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.Nameable;
import org.openblock.creator.code.clazz.generic.IGeneric;
import org.openblock.creator.code.clazz.generic.specified.SpecifiedGenerics;
import org.openblock.creator.impl.java.clazz.generic.JavaGenerics;

import java.lang.reflect.WildcardType;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class JavaWildcardGenerics implements SpecifiedGenerics {

    private final WildcardType type;
    private final Nameable reference;

    public JavaWildcardGenerics(Nameable reference, WildcardType type) {
        this.type = type;
        this.reference = reference;
    }

    @Override
    public List<IGeneric> getGenerics() {
        return Collections.singletonList(JavaGenerics.typeWildcard(this.type));
    }

    @Override
    public List<SpecifiedGenerics> getSpecifiedGenericClass() {
        return Collections.emptyList();
    }

    @Override
    public @NotNull Nameable getTargetReference() {
        return this.reference;
    }

    @Override
    public @NotNull String getDisplayName() {
        return this.getGenerics().stream().map(g -> g.writeCode(false)).collect(Collectors.joining(", "));
        //return this.type.getName() + " extends " + this.getGenerics().get(0).getClasses().stream().flatMap(type -> type.getClasses().stream()).map(t -> t.getName()).collect(Collectors.joining("&"));
    }
}
