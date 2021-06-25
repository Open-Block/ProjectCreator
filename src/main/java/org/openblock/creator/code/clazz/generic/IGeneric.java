package org.openblock.creator.code.clazz.generic;

import org.openblock.creator.code.Codeable;
import org.openblock.creator.code.Nameable;
import org.openblock.creator.code.clazz.type.IType;

import java.util.List;

public interface IGeneric extends Nameable, Codeable {

    List<IType> getClasses();

}
