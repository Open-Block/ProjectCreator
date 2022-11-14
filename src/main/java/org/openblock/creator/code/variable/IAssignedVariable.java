package org.openblock.creator.code.variable;

import org.jetbrains.annotations.NotNull;

public interface IAssignedVariable extends IVariable {

	@NotNull ReturnableLine getAssigned();
}
