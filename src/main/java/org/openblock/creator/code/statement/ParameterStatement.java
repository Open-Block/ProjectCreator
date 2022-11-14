package org.openblock.creator.code.statement;

import org.openblock.creator.code.Codeable;

import java.util.List;

public interface ParameterStatement extends Statement {

	List<Codeable> getParameterCode();
	int getMaxParameters();
}
