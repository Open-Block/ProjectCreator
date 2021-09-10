package org.openblock.creator.code.line.primitive;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.call.Callable;
import org.openblock.creator.code.clazz.IClass;
import org.openblock.creator.impl.java.clazz.JavaClass;

import java.util.SortedSet;
import java.util.TreeSet;

public class StringConstructor implements PrimitiveConstructor<String> {

    private @NotNull String value;

    public StringConstructor(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public @NotNull String writeCode(int indent) {
        return "\"" + this.value + "\"";
    }

    @Override
    public @NotNull SortedSet<IClass> getImports() {
        return new TreeSet<>();
    }

    @Override
    public JavaClass getJavaClass() {
        return new JavaClass(String.class);
    }
}
