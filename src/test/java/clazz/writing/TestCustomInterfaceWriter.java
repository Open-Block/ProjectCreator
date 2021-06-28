package clazz.writing;

import org.openblock.creator.code.Visibility;
import org.openblock.creator.code.call.returntype.VoidedReturnType;
import org.openblock.creator.code.clazz.ClassType;
import org.openblock.creator.impl.custom.clazz.AbstractCustomClass;
import org.openblock.creator.impl.custom.clazz.CustomClassBuilder;
import org.openblock.creator.impl.custom.function.CustomFunctionBuilder;

public class TestCustomInterfaceWriter {

    public static void main(String[] args) {
        AbstractCustomClass customClass = new CustomClassBuilder()
                .setName("Test")
                .setPackageLocation("org", "tester")
                .setVisibility(Visibility.PUBLIC)
                .setType(ClassType.INTERFACE)
                .addFunctions(new CustomFunctionBuilder()
                        .setVisibility(Visibility.PUBLIC)
                        .setReturnType(new VoidedReturnType())
                        .setName("test")
                )
                .build();
        System.out.println(customClass.writeCode(0));
    }
}
