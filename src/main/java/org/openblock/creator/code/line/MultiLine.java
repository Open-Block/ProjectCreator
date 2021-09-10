package org.openblock.creator.code.line;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.call.Returnable;
import org.openblock.creator.code.call.returntype.ReturnType;
import org.openblock.creator.code.clazz.IClass;
import org.openblock.creator.utils.OpenStringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class MultiLine implements Returnable.ReturnableLine {

    private final List<Returnable.ReturnableLine> lines = new ArrayList<>();

    @Deprecated
    public MultiLine() {
        throw new RuntimeException("MultiLine requires 1 returnable or more");
    }

    public MultiLine(Returnable.ReturnableLine... lines) {
        this(Arrays.asList(lines));
    }

    public MultiLine(Collection<Returnable.ReturnableLine> lines) {
        this.lines.addAll(lines);
    }

    public List<Returnable.ReturnableLine> getLines() {
        return this.lines;
    }

    @Override
    public @NotNull String writeCode(int indent) {
        return OpenStringUtils.repeat(indent, '\t') + this.lines.stream().map(l -> l.writeCode(0)).collect(Collectors.joining("."));
    }

    @Override
    public @NotNull SortedSet<IClass> getImports() {
        return this
                .lines
                .parallelStream()
                .flatMap(l -> l.getImports().parallelStream())
                .collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(IClass::getFullName))));
    }

    @Override
    public @NotNull ReturnType getReturnType() {
        return this.lines.get(this.lines.size() - 1).getReturnType();
    }
}
