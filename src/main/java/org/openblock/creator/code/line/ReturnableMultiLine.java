package org.openblock.creator.code.line;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.call.Caller;
import org.openblock.creator.code.call.Returnable;
import org.openblock.creator.code.call.returntype.ReturnType;
import org.openblock.creator.code.clazz.IClass;
import org.openblock.creator.utils.OpenStringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class ReturnableMultiLine extends MultiLine implements Returnable.ReturnableLine {

	@Deprecated
	public ReturnableMultiLine() {
		throw new RuntimeException("MultiLine requires 1 returnable or more");
	}

	public ReturnableMultiLine(CallingLine... lines) {
		this(Arrays.asList(lines));
	}

	public ReturnableMultiLine(Collection<CallingLine> lines) {
		super(lines);
	}

	@Override
	public @NotNull ReturnType getReturnType() {
		List<CallingLine> lines = this.getLines();
		CallingLine line = lines.get(lines.size() - 1);
		if(line instanceof Returnable returnable){
			return returnable.getReturnType();
		}
		throw new RuntimeException("Last call is not returnable");
	}
}
