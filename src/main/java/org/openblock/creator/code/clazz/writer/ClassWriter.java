package org.openblock.creator.code.clazz.writer;

import org.openblock.creator.code.clazz.IClass;

public interface ClassWriter<C extends IClass> {

    String write(C clazz);
}
