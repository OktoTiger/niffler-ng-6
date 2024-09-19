package guru.qa.niffler.helper;

import io.netty.util.internal.ThreadLocalRandom;

public class DataGenerator {

    public String validUsername() {
        return "User" + ThreadLocalRandom.current().nextInt(1000000);
    }

    public String validPassword() {
        return String.valueOf(ThreadLocalRandom.current().nextInt(100, 1000000));
    }

    public String invalidPassword() {
        return String.valueOf(ThreadLocalRandom.current().nextInt(99));
    }


}
