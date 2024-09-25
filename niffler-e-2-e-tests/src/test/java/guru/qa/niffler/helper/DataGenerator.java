package guru.qa.niffler.helper;

import com.github.javafaker.Faker;
import io.netty.util.internal.ThreadLocalRandom;

public class DataGenerator {
    Faker faker = new Faker();

    public String validUsername() {
        return "User" + ThreadLocalRandom.current().nextInt(1000000);
    }

    public String validPassword() {
        return String.valueOf(ThreadLocalRandom.current().nextInt(100, 1000000));
    }

    public String invalidPassword() {
        return String.valueOf(ThreadLocalRandom.current().nextInt(99));
    }

    public String randomCategory(){
        return faker.cat().breed();
    }


}
