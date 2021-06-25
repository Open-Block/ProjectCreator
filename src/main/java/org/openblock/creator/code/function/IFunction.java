package org.openblock.creator.code.function;

import org.openblock.creator.code.Nameable;
import org.openblock.creator.code.Visibility;
import org.openblock.creator.code.call.Callable;
import org.openblock.creator.code.clazz.ClassPart;
import org.openblock.creator.code.clazz.generic.IGeneric;
import org.openblock.creator.code.statement.Statement;
import org.openblock.creator.code.variable.parameter.Parameter;

import java.util.List;

public interface IFunction extends Nameable, Statement, Callable, ClassPart {

    List<Parameter> getParameters();

    Visibility getVisibility();

    List<IGeneric> getGenerics();
}
