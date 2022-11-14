package org.openblock.creator.code.statement;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.CodeBuilder;
import org.openblock.creator.code.Codeable;
import org.openblock.creator.code.call.Returnable;
import org.openblock.creator.code.clazz.IClass;

import java.util.*;
import java.util.stream.Collectors;

public class IfStatement implements ParameterStatement {

	private List<Codeable> parameters = new LinkedList<>();
	private List<Codeable> codeBlock = new LinkedList<>();

	public IfStatement(Returnable.ReturnableLine line) {
		this.parameters.add(line);
	}

	@Override
	public @NotNull String writeCode(int indent) {
		return "if("
				+ this.parameters.stream().map(par -> par.writeCode(0)).collect(Collectors.joining(", "))
				+ ") {\n"
				+ this.codeBlock.stream().map(code -> code.writeCode(indent + 1)).collect(
				Collectors.joining("\n"))
				+ "}";
	}

	@Override
	public @NotNull SortedSet<IClass> getImports() {
		Set<IClass> set = new HashSet<>();
		set.addAll(this.parameters.parallelStream().flatMap(p -> p.getImports().parallelStream()).toList());
		set.addAll(this.codeBlock.parallelStream().flatMap(p -> p.getImports().parallelStream()).toList());
		return new TreeSet<>(set);
	}

	@Override
	public @NotNull CodeBuilder<?> toBuilder() {
		throw new RuntimeException("Not implement");
	}

	@Override
	public List<Codeable> getParameterCode() {
		return this.parameters;
	}

	@Override
	public int getMaxParameters() {
		return 1;
	}

	@Override
	public List<Codeable> getCodeBlock() {
		return this.codeBlock;
	}
}
