package org.openblock.creator.impl.custom.clazz.interfacetype;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.clazz.ClassType;
import org.openblock.creator.code.clazz.generic.specified.SpecifiedGenerics;
import org.openblock.creator.impl.custom.clazz.AbstractCustomClass;
import org.openblock.creator.impl.custom.clazz.CustomClassBuilder;

import java.util.Optional;

public class CustomInterface extends AbstractCustomClass {

    public CustomInterface(CustomClassBuilder builder) {
        super(builder);
    }

    @Override
    public @NotNull String writeCode(int indent) {
        return new CustomInterfaceWriter().write(this);
    }

    @Override
    public boolean isAbstract() {
        return true;
    }

    @Override
    public @NotNull ClassType getClassType() {
        return ClassType.INTERFACE;
    }

    @Override
    public Optional<SpecifiedGenerics> getExtendingClass() {
        return Optional.empty();
    }
}
