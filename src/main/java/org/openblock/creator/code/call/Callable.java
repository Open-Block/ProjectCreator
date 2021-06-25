package org.openblock.creator.code.call;

public interface Callable extends Returnable {

    Caller createCaller();
}
