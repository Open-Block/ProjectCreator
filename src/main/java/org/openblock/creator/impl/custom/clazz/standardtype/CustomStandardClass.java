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
        if (this.isFinal && this.isAbstract) {
            throw new IllegalArgumentException("Cannot have a class as abstract and final");
        }
    }

    @Override
    public boolean isAbstract() {
        return this.isAbstract;
    }

    @Override
    public @NotNull CustomClassBuilder toBuilder() {
        return new CustomClassBuilder()
                .setVisibility(this.getVisibility())
                .setAbstract(this.isAbstract())
                .setFinal(this.isFinal())
                .setType(this.getClassType())
                .setPackageLocation(this.getPackage())
                .setExtending(this.getExtendingClass().orElse(null))
                .addImplementing(this.getImplements())
                .addFunctions(this.getFunctions().parallelStream().map(function -> function.toBuilder()).toList())
                .addGenerics(this.getGenerics());
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
