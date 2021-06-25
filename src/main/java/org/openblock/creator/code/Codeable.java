package org.openblock.creator.code;

import org.openblock.creator.code.clazz.IClass;

import java.util.SortedSet;

public interface Codeable {

    String writeCode();

    SortedSet<IClass> getImports();
}
