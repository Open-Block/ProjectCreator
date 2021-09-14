package org.openblock.creator.code.call.returntype;

import org.openblock.creator.code.call.Caller;
import org.openblock.creator.code.call.CallerProvider;
import org.openblock.creator.code.clazz.IClass;
import org.openblock.creator.code.clazz.type.IType;

import java.util.TreeSet;

public interface ReturnType extends CallerProvider {

    IType getType();

    boolean isArray();

    default String getDisplayText() {
        return this.getType().getName() + (this.isArray() ? "[]" : "");
    }

    default boolean hasInheritance(IClass clazz) {
        if (this.isArray()) {
            return false;
        }
        return this.getType().hasInheritance(clazz);
    }

    default boolean hasInheritance(ReturnType type) {
        if (this.isArray() || type.isArray()) {
            return false;
        }
        return this.getType().hasInheritance(type.getType());
    }

    @Override
    default TreeSet<Caller> getCallers() {
        if (isArray()) {
            //handle array callings
            throw new RuntimeException("Not implemented");
        }
        return this.getType().getCallers();
    }
}
