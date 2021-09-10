package org.openblock.creator.code.line.primitive;

import org.openblock.creator.code.call.Callable;
import org.openblock.creator.code.call.Caller;
import org.openblock.creator.code.call.returntype.ReturnType;
import org.openblock.creator.code.call.returntype.StatedReturnType;
import org.openblock.creator.code.clazz.type.BasicType;
import org.openblock.creator.impl.java.clazz.JavaClass;

public interface PrimitiveConstructor<T> extends Caller {

    T getValue();

    JavaClass getJavaClass();

    @Override
    default ReturnType getReturnType() {
        return new StatedReturnType(new BasicType(getJavaClass()), false);
    }

    @Deprecated
    @Override
    default Callable getCallable() {
        throw new RuntimeException("Primitive Constructors do not have a callable object");
    }
}
