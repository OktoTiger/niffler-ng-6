package guru.qa.niffler.helper;

import io.netty.util.internal.ThreadLocalRandom;

public class UserData {
    public static String usernameCorrect = "User" + ThreadLocalRandom.current().nextInt(1000000);
    public static String usernameInCorrect = "Us";
    public static String existedUser = "severus";
    public static String existedPassword = "12345";
    public static String passwordCorrect = String.valueOf(ThreadLocalRandom.current().nextInt(100, 1000000));
    public static String passwordInCorrectShort = String.valueOf(ThreadLocalRandom.current().nextInt(99));
    public static String passwordInCorrectLong = "1234567891011";
}
