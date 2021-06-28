package org.openblock.creator.code.call;

import org.openblock.creator.code.Codeable;

import java.util.List;

public interface Caller extends Codeable {

    interface Parameter extends Caller {

        List<Codeable> getParameters();

    }

    Callable getCallable();
}
