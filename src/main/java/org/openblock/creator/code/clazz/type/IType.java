package org.openblock.creator.code.clazz.type;

import org.openblock.creator.code.Nameable;
import org.openblock.creator.code.clazz.IClass;

import java.util.List;

public interface IType extends Nameable {

    List<IClass> getClasses();

}
