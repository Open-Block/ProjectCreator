package clazz.writing;


import org.openblock.creator.code.Visibility;
import org.openblock.creator.code.clazz.ClassType;
import org.openblock.creator.impl.custom.clazz.CustomClassBuilder;

/*
 * This creates a copy of the Vector class from Ships
 * (found here: https://github.com/Minecraft-Ships/ShipsCore/blob/master/src/main/java/org/core/vector/Vector.java )
 */
public class TestVectorWriter {

    public static void main(String[] args) {
        new CustomClassBuilder()
                .setPackageLocation("org", "core", "Vector")
                .setName("Vector")
                .setVisibility(Visibility.PUBLIC)
                .setAbstract(true)
                .setType(ClassType.STANDARD)
                .build()
                .writeCode(0);

    }
}
