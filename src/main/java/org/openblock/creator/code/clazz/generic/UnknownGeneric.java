package org.openblock.creator.code.clazz.generic;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.Nameable;
import org.openblock.creator.code.clazz.IClass;
import org.openblock.creator.code.clazz.type.IType;

import java.util.*;
import java.util.stream.Collectors;

public class UnknownGeneric implements IGeneric {

    private final List<IType> types = new ArrayList<>();
    private final boolean extended;

    public UnknownGeneric(boolean extended, Collection<IType> type) {
        this.types.addAll(type);
        this.extended = extended;
    }

    @Override
    public @NotNull SortedSet<IClass> getImports() {
        return this.types.stream().flatMap(c -> c.getClasses().stream()).collect(Collectors.toCollection(TreeSet::new));
    }

    @Override
    public @NotNull String getName() {
        return "?";
    }

    @Override
    public List<IType> getClasses() {
        return this.types;
    }

    @Override
    public boolean isExtending() {
        return this.extended;
    }

    @Override
    public @NotNull String writeCode(boolean genericExtends) {
        if (this.types.isEmpty() || !genericExtends) {
            return "?";
        }
        String classes = this.types.stream().flatMap(t -> t.getClasses().stream()).map(Nameable::getName).collect(Collectors.joining("&"));
        if (this.extended) {
            return "? extends " + classes;
        }
        return "? super " + classes;
    }
}
