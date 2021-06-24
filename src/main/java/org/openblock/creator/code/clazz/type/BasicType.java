package org.openblock.creator.code.clazz.type;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.Nameable;
import org.openblock.creator.code.clazz.IClass;

import java.util.List;

public class BasicType implements IType {

    private IClass clazz;


    @Override
    public String getName() {
        return null;
    }

    @Override
    public Nameable setName(@NotNull String name) {
        return null;
    }

    @Override
    public List<IClass> getClasses() {
        return null;
    }

    @Override
    public boolean isArray() {
        return false;
    }

    @Override
    public IType setArray(boolean array) {
        return null;
    }
}
