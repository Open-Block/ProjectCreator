package org.openblock.creator.project;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.clazz.IClass;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Project<C extends IClass> {

    private final Set<C> classes = new HashSet<>();
    private final String name;

    public Project(@NotNull String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Collection<C> getClasses() {
        return Collections.unmodifiableCollection(this.classes);
    }

    public void register(C clazz) {
        this.classes.add(clazz);
    }
}
