package org.openblock.creator.code.clazz;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.Codeable;
import org.openblock.creator.code.Nameable;
import org.openblock.creator.code.Visibility;
import org.openblock.creator.code.clazz.generic.IGeneric;
import org.openblock.creator.code.clazz.generic.specified.SpecifiedGenerics;
import org.openblock.creator.code.function.IFunction;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;

public interface IClass extends Nameable, Codeable, Abstractable, Comparable<IClass> {

    @NotNull String[] getPackage();

    boolean isFinal();

    @NotNull ClassType getClassType();

    @NotNull Visibility getVisibility();

    List<IGeneric> getGenerics();

    Optional<SpecifiedGenerics> getExtendingClass();

    SortedSet<SpecifiedGenerics> getImplements();

    Set<IClass> getNestedClasses();

    Set<IFunction> getFunctions();

    default @NotNull String getFullName() {
        return String.join(".", this.getPackage()) + "." + this.getName();
    }

    default <F extends IFunction> Set<F> getFunctions(Class<F> clazz) {
        return this.getFunctions().parallelStream().filter(clazz::isInstance).map(f -> (F) f).collect(Collectors.toSet());
    }

    @Override
    default int compareTo(@NotNull IClass o) {
        return this.getFullName().compareTo(o.getFullName());
    }
}
