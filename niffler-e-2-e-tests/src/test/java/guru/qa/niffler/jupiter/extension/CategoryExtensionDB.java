package guru.qa.niffler.jupiter.extension;

import com.github.jknack.handlebars.internal.lang3.ArrayUtils;
import guru.qa.niffler.helper.DataGenerator;
import guru.qa.niffler.jupiter.annotation.Category;
import guru.qa.niffler.jupiter.annotation.meta.User;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.service.SpendDbClient;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;


public class CategoryExtensionDB implements BeforeEachCallback, ParameterResolver, AfterTestExecutionCallback {
    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(CategoryExtensionDB.class);
    private final SpendDbClient spendDbClient = new SpendDbClient();
    DataGenerator dg = new DataGenerator();

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        AnnotationSupport.findAnnotation(context.getRequiredTestMethod(), User.class)
                .ifPresent(
                        userAnno -> {
                            if (ArrayUtils.isNotEmpty(userAnno.categories())) {
                                Category categoryAnno = userAnno.categories()[0];
                                CategoryJson category = new CategoryJson(
                                        null,
                                        dg.randomCategory(),
                                        userAnno.username(),
                                        categoryAnno.acrhived());

                                CategoryJson created = spendDbClient.createCategory(category);

                                context.getStore(NAMESPACE).put(
                                        context.getUniqueId(), created);
                            }

                        });
    }


    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        CategoryJson category = context.getStore(NAMESPACE).get(context.getUniqueId(), CategoryJson.class);
        if (category != null && !category.archived()) {
            spendDbClient.deleteCategory(category);
        }
    }


    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(CategoryJson.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext.getStore(CategoryExtensionDB.NAMESPACE).get(extensionContext.getUniqueId(), CategoryJson.class);
    }
}
