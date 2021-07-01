package org.openblock.creator.impl.custom.clazz.standardtype;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.clazz.ClassType;
import org.openblock.creator.code.clazz.generic.specified.SpecifiedGenerics;
import org.openblock.creator.impl.custom.clazz.AbstractCustomClass;
import org.openblock.creator.impl.custom.clazz.CustomClassBuilder;

import java.util.Optional;

public class CustomStandardClass extends AbstractCustomClass {

    private final boolean isAbstract;
    private final SpecifiedGenerics extending;

    public CustomStandardClass(CustomClassBuilder builder) {
        super(builder);
        this.isAbstract = builder.isAbstract();
        this.extending = builder.getExtending();
    }

    @Override
    public boolean isAbstract() {
        return this.isAbstract;
    }

    @Override
    public @NotNull ClassType getClassType() {
        return ClassType.STANDARD;
    }

    @Override
    public Optional<SpecifiedGenerics> getExtendingClass() {
        return Optional.ofNullable(this.extending);
    }
}
