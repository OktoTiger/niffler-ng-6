package guru.qa.niffler.jupiter;

import com.github.javafaker.Faker;
import guru.qa.niffler.api.CategoryApiClient;
import guru.qa.niffler.api.SpendApiClient;
import guru.qa.niffler.model.CategoryJson;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;

public class CategoryExtension implements BeforeEachCallback, ParameterResolver,AfterTestExecutionCallback {
    Faker faker = new Faker();
    String randomCategory = faker.cat().breed();

    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(CategoryExtension.class);
    private final SpendApiClient spendApiClient = new SpendApiClient();


    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        AnnotationSupport.findAnnotation(context.getRequiredTestMethod(), Category.class)
                .ifPresent(anno-> {
                    CategoryJson category = new CategoryJson(
                            null,
                            randomCategory,
                            anno.username(),
                            false);

                    CategoryJson created = spendApiClient.createCategory(category);
                    if (anno.acrhived()){
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
                });

    }


    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        CategoryJson category = context.getStore(NAMESPACE).get(context.getUniqueId(), CategoryJson.class);
        if(!category.archived()){
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
