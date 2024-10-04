package guru.qa.niffler.jupiter.extension;

import com.github.jknack.handlebars.internal.lang3.ArrayUtils;
import guru.qa.niffler.jupiter.annotation.Spending;
import guru.qa.niffler.jupiter.annotation.meta.User;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.service.SpendDbClient;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;

import java.util.Date;

public class SpendingExtensionDB implements BeforeEachCallback, ParameterResolver {

    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(SpendingExtensionDB.class);
    private final SpendDbClient spendDbClient = new SpendDbClient();


    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        AnnotationSupport.findAnnotation(context.getRequiredTestMethod(), User.class)
                .ifPresent(userAnno -> {
                    if (ArrayUtils.isNotEmpty(userAnno.spendings())) {
                        Spending spending = userAnno.spendings()[0];
                        SpendJson spend = new SpendJson(
                                null,
                                new Date(),
                                new CategoryJson(
                                        null,
                                        spending.category(),
                                        userAnno.username(),
                                        false
                                ),
                                CurrencyValues.RUB,
                                spending.amount(),
                                spending.description(),
                                userAnno.username()
                        );
                        context.getStore(NAMESPACE).put(
                                context.getUniqueId(),
                                spendDbClient.createSpend(spend));
                    }
                });
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(SpendJson.class);
    }

    @Override
    public SpendJson resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext.getStore(NAMESPACE).get(extensionContext.getUniqueId(), SpendJson.class);
    }
}