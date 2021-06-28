package org.openblock.creator.code.clazz.type;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.clazz.IClass;

import java.util.Collections;
import java.util.List;

public class VoidType implements IType{
    @Override
    public @NotNull String getName() {
        return "Void";
    }

    @Override
    public List<IClass> getClasses() {
        return Collections.emptyList();
    }
}
