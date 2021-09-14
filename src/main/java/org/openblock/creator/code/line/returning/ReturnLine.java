package org.openblock.creator.code.line.returning;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.openblock.creator.code.OpenCompares;
import org.openblock.creator.code.call.Caller;
import org.openblock.creator.code.call.Returnable;
import org.openblock.creator.code.clazz.IClass;
import org.openblock.creator.code.line.CallingLine;
import org.openblock.creator.code.line.MultiLine;

import java.util.Comparator;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;

public class ReturnLine implements CallingLine {

    private final CallingLine line;

    public ReturnLine() {
        this((Returnable.ReturnableLine) null);
    }

    public ReturnLine(@Nullable Returnable.ReturnableLine line) {
        this.line = line;
    }

    public ReturnLine(@NotNull MultiLine line) {
        if (!(line.getLines().get(line.getLines().size() - 1) instanceof Returnable.ReturnableLine)) {
            throw new RuntimeException("Multiline must have a ReturnableLine at the end to be used in a returnLine");
        }
        this.line = line;
    }

    public Optional<CallingLine> getLine() {
        return Optional.ofNullable(this.line);
    }

    @Override
    public @NotNull String writeCode(int indent) {
        if (this.line == null) {
            return "return";
        }
        return "return " + this.line.writeCode(0);
    }

    @Override
    public @NotNull SortedSet<IClass> getImports() {
        if (this.line == null) {
            return new TreeSet<>();
        }
        return this.line.getImports();
    }

    @Override
    public TreeSet<Caller> getCallers() {
        if (this.line == null) {
            return new TreeSet<>(OpenCompares.CALLER_COMPARE);
        }
        Returnable.ReturnableLine rLine;
        if (this.line instanceof MultiLine multiLine) {
            rLine = (Returnable.ReturnableLine) multiLine.getLines().get(multiLine.getLines().size() - 1);
        } else {
            rLine = (Returnable.ReturnableLine) this.line;
        }
        
        return rLine.getReturnType().getCallers();
    }
}
