package org.openblock.creator.code;

import org.openblock.creator.code.call.Caller;
import org.openblock.creator.code.function.IFunction;

import java.util.Comparator;
import java.util.stream.Collectors;

public interface OpenCompares {

    Comparator<IFunction> FUNCTION_COMPARE = (o1, o2) -> {
        String l1 = o1.getName() + "(";
        if (o1 instanceof Caller.ParameterCaller pCaller) {
            l1 = l1 + pCaller.getCallable().getParameters().stream().map(p -> p.writeCode(0)).collect(Collectors.joining(","));
        }
        l1 = l1 + ");";

        String l2 = o2.getName() + "(";
        if (o2 instanceof Caller.ParameterCaller pCaller) {
            l2 = l2 + pCaller.getCallable().getParameters().stream().map(p -> p.writeCode(0)).collect(Collectors.joining(","));
        }
        l2 = l2 + ");";

        return l1.compareTo(l2);
    };

    Comparator<Caller> CALLER_COMPARE = (o1, o2) -> {
        String l1 = o1.getCallable().getName() + "(";
        if (o1 instanceof Caller.ParameterCaller pCaller) {
            l1 = l1 + pCaller.getCallable().getParameters().stream().map(p -> p.writeCode(0)).collect(Collectors.joining(","));
        }
        l1 = l1 + ");";

        String l2 = o2.getCallable().getName() + "(";
        if (o2 instanceof Caller.ParameterCaller pCaller) {
            l2 = l2 + pCaller.getCallable().getParameters().stream().map(p -> p.writeCode(0)).collect(Collectors.joining(","));
        }
        l2 = l2 + ");";

        return l1.compareTo(l2);
    };
}
