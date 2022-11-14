package org.openblock.creator.code.variable;

import org.openblock.creator.code.CodeBuilder;
import org.openblock.creator.code.call.returntype.ReturnType;

public interface VariableBuilder<Self extends VariableBuilder<Self>> extends CodeBuilder<Self> {

	ReturnType getReturnType();

	Self setReturnType(ReturnType type);

	String getName();

	Self setName(String name);

	boolean isFinal();

	Self setFinal(boolean fin);
}
