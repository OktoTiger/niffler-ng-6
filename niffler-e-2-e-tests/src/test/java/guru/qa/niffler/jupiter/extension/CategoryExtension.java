package guru.qa.niffler.jupiter.extension;

import com.github.javafaker.Faker;
import com.github.jknack.handlebars.internal.lang3.ArrayUtils;
import guru.qa.niffler.api.SpendApiClient;
import guru.qa.niffler.helper.DataGenerator;
import guru.qa.niffler.jupiter.annotation.Category;
import guru.qa.niffler.jupiter.annotation.meta.User;
import guru.qa.niffler.model.CategoryJson;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;


public class CategoryExtension implements BeforeEachCallback, ParameterResolver, AfterTestExecutionCallback {
    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(CategoryExtension.class);
    private final SpendApiClient spendApiClient = new SpendApiClient();
    DataGenerator dg = new DataGenerator();
    String randomCategory = dg.randomCategory();


    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        AnnotationSupport.findAnnotation(context.getRequiredTestMethod(), User.class)
                .ifPresent(
                        userAnno -> {
                            if (ArrayUtils.isNotEmpty(userAnno.categories())) {
                                Category categoryAnno = userAnno.categories()[0];
                                CategoryJson category = new CategoryJson(
                                        null,
                                        randomCategory,
                                        userAnno.username(),
                                        false);

                                CategoryJson created = spendApiClient.createCategory(category);

                                if (categoryAnno.acrhived()) {
                                    CategoryJson archivedCategory = new CategoryJson(
                                            created.id(),
                                            randomCategory,
                                            created.username(),
                                            true
                                    );
                                    created = spendApiClient.updateCategory(archivedCategory);
                                }
                                context.getStore(NAMESPACE).put(
                                        context.getUniqueId(), created);
                            }

                        });


    }


    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        CategoryJson category = context.getStore(NAMESPACE).get(context.getUniqueId(), CategoryJson.class);
        if (category != null && !category.archived()) {
            CategoryJson archivedCategory = new CategoryJson(
                    category.id(),
                    category.name(),
                    category.username(),
                    true);
            spendApiClient.updateCategory(archivedCategory);
        }
    }


    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(CategoryJson.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext.getStore(CategoryExtension.NAMESPACE).get(extensionContext.getUniqueId(), CategoryJson.class);
    }
}
