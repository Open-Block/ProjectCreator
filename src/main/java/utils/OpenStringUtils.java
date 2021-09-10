package utils;

public interface OpenStringUtils {

    static String repeat(int amount, char toRepeat) {
        return repeat(amount, toRepeat + "");
    }

    static String repeat(int amount, String toRepeat) {
        StringBuilder builder = new StringBuilder();
        for (int a = 0; a < amount; a++) {
            builder.append(toRepeat);
        }
        return builder.toString();
    }
}
