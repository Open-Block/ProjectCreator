package org.openblock.creator.code.line;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.call.Caller;
import org.openblock.creator.code.clazz.IClass;
import org.openblock.creator.utils.OpenStringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class MultiLine implements CallingLine {

	private final List<CallingLine> lines = new ArrayList<>();

	@Deprecated
	public MultiLine() {
		throw new RuntimeException("MultiLine requires 1 returnable or more");
	}

	public MultiLine(CallingLine... lines) {
		this(Arrays.asList(lines));
	}

	public MultiLine(Collection<CallingLine> lines) {
		this.lines.addAll(lines);
	}

	public List<CallingLine> getLines() {
		return this.lines;
	}

	@Override
	public @NotNull String writeCode(int indent) {
		return OpenStringUtils.repeat(indent, '\t') + this.lines.stream()
				.map(l -> ((Line) l).writeCode(0))
				.collect(Collectors.joining("."));
	}

	@Override
	public @NotNull SortedSet<IClass> getImports() {
		return this
				.lines
				.parallelStream()
				.flatMap(l -> ((Line) l).getImports().parallelStream())
				.collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(IClass::getFullName))));
	}

	@Override
	public @NotNull MultiLineBuilder toBuilder() {
		return new MultiLineBuilder().addLines(this.lines);
	}

	@Override
	public TreeSet<Caller> getCallers() {
		return this.lines.get(this.lines.size() - 1).getCallers();
	}
}
