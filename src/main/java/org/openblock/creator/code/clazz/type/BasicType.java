package org.openblock.creator.code.clazz.type;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.clazz.IClass;

import java.util.Collections;
import java.util.List;

public class BasicType implements IType {

    private final @NotNull
    IClass clazz;

    public BasicType(@NotNull IClass clazz) {
        this.clazz = clazz;
    }

    public IClass getTargetClass() {
        return this.clazz;
    }

    @Override
    public @NotNull String getName() {
        return this.clazz.getName();
    }

    @Override
    @Deprecated
    public List<IClass> getClasses() {
        return Collections.singletonList(this.clazz);
    }
}
