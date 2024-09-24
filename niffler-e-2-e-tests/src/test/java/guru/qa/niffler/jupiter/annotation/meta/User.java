package guru.qa.niffler.jupiter.annotation.meta;

import guru.qa.niffler.jupiter.annotation.Category;
import guru.qa.niffler.jupiter.annotation.Spending;
import guru.qa.niffler.jupiter.extension.CategoryExtension;
import guru.qa.niffler.jupiter.extension.SpendingExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({CategoryExtension.class, SpendingExtension.class})
public @interface User {
    String username();
    Category [] categories() default {};
    Spending [] spendings() default {};
}
