package org.openblock.creator.code.call.returntype;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.clazz.type.IType;

public class StatedReturnType implements ReturnType {

    private final @NotNull IType type;
    private final boolean isArray;

    public StatedReturnType(@NotNull IType type, boolean isArray) {
        this.type = type;
        this.isArray = isArray;
    }

    @Override
    public @NotNull IType getType() {
        return type;
    }

    @Override
    public boolean isArray() {
        return isArray;
    }
}
