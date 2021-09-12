package org.openblock.creator.code.line.returning;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.openblock.creator.code.call.Returnable;
import org.openblock.creator.code.clazz.IClass;
import org.openblock.creator.code.line.Line;

import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;

public class ReturnLine implements Line {

    private final Returnable.ReturnableLine line;

    public ReturnLine() {
        this(null);
    }

    public ReturnLine(@Nullable Returnable.ReturnableLine line) {
        this.line = line;
    }

    public Optional<Returnable.ReturnableLine> getLine() {
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
}
