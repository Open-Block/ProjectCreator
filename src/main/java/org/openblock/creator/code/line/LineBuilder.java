package org.openblock.creator.code.line;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.CodeBuilder;

public interface LineBuilder<Self extends LineBuilder<Self>> extends CodeBuilder<Self> {

	@Override
	@NotNull Line build();

}
