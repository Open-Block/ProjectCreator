package org.openblock.creator.code.call.returntype;

import org.openblock.creator.code.clazz.type.IType;
import org.openblock.creator.code.clazz.type.VoidType;

public class VoidedReturnType implements ReturnType {
    @Override
    public IType getType() {
        return new VoidType();
    }

    @Override
    public boolean isArray() {
        return false;
    }
}
