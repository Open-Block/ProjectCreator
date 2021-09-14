package org.openblock.creator.code.clazz.type;

import org.openblock.creator.code.Nameable;
import org.openblock.creator.code.OpenCompares;
import org.openblock.creator.code.call.Caller;
import org.openblock.creator.code.call.CallerProvider;
import org.openblock.creator.code.clazz.IClass;

import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

public interface IType extends Nameable, CallerProvider {

    List<IClass> getClasses();

    @Override
    default TreeSet<Caller> getCallers() {
        //THIS IS WHERE IT FAILS
        List<IClass> classes = this.getClasses();
        return classes
                .parallelStream()
                .flatMap(iClass -> iClass.getCallers().parallelStream())
                .collect(Collectors.toCollection(() -> new TreeSet<>(OpenCompares.CALLER_COMPARE)));
    }

    default boolean hasInheritance(IClass clazz) {
        return this.getClasses().parallelStream().anyMatch(c -> c.hasInheritance(clazz));
    }

    default boolean hasInheritance(IType type) {
        return this.getClasses().parallelStream().anyMatch(c -> type.getClasses().parallelStream().anyMatch(c::hasInheritance));
    }
}