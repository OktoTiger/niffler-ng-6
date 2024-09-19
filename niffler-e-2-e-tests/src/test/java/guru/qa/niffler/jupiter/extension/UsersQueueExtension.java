package guru.qa.niffler.jupiter.extension;

import io.qameta.allure.Allure;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.platform.commons.support.AnnotationSupport;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

import static javax.swing.UIManager.put;

public class UsersQueueExtension implements
    BeforeTestExecutionCallback,
    AfterTestExecutionCallback,
    ParameterResolver {

  public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(UsersQueueExtension.class);

  public record StaticUser(String username, String password, boolean empty) {
  }

  private static final Queue<StaticUser> EMPTY_USERS = new ConcurrentLinkedQueue<>();
  private static final Queue<StaticUser> WITH_FRIENDS = new ConcurrentLinkedQueue<>();
  private static final Queue<StaticUser> WITH_INCOME_REQUEST = new ConcurrentLinkedQueue<>();
  private static final Queue<StaticUser> WITH_OUTCOME_REQUEST = new ConcurrentLinkedQueue<>();

  static {
    EMPTY_USERS.add(new StaticUser("bee", "12345", true));
    WITH_FRIENDS.add(new StaticUser("bee", "12345", true));
    WITH_INCOME_REQUEST.add(new StaticUser("bee", "12345", true));
    WITH_OUTCOME_REQUEST.add(new StaticUser("duck", "12345", false));
  }

  @Target(ElementType.PARAMETER)
  @Retention(RetentionPolicy.RUNTIME)
  public @interface UserType {
    Type value() default Type.empty;

      enum Type {
          empty, withFriends, withIncomeFriendRequest, withOutcomeFriendRequest
      }
  }

    private Queue<StaticUser> getQueueType(UserType.Type type) {
        return switch (type) {
            case empty -> EMPTY_USERS;
            case withFriends -> WITH_FRIENDS;
            case withIncomeFriendRequest -> WITH_INCOME_REQUEST;
            case withOutcomeFriendRequest -> WITH_OUTCOME_REQUEST;
            default -> throw new IllegalArgumentException("Unknown type: " + type);
        };
    }

  @Override
  public void beforeTestExecution(ExtensionContext context) {
    Arrays.stream(context.getRequiredTestMethod().getParameters())
        .filter(p -> AnnotationSupport.isAnnotated(p, UsersQueueExtension.UserType.class))
        .forEach(p -> {
            UserType ut = p.getAnnotation(UserType.class);
            Queue<StaticUser> queue = getQueueType(ut.value());
          Optional<StaticUser> user = Optional.empty();
          StopWatch sw = StopWatch.createStarted();
          while (user.isEmpty() && sw.getTime(TimeUnit.SECONDS) < 30) {
            user = Optional.ofNullable(queue.poll());
          }
          Allure.getLifecycle().updateTestCase(testCase ->
              testCase.setStart(new Date().getTime())
          );
          user.ifPresentOrElse(
              u -> {
                  ((Map<UserType, StaticUser>) context.getStore(NAMESPACE)
                          .getOrComputeIfAbsent(
                                  context.getUniqueId(),
                                  key -> new HashMap<>()
                          )).put(ut, u);
              },
              () -> {
                throw new IllegalStateException("Can`t obtain user after 30s.");
              }
          );
        });
  }


  @Override
  public void afterTestExecution(ExtensionContext context) {
    UsersQueueExtension.StaticUser user = context.getStore(NAMESPACE).get(
        context.getUniqueId(),
        StaticUser.class
    );
    if (user.empty()) {
      UsersQueueExtension.EMPTY_USERS.add(user);
    } else {
      NOT_EMPTY_USERS.add(user);
    }
  }

  @Override
  public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
    return parameterContext.getParameter().getType().isAssignableFrom(StaticUser.class)
        && AnnotationSupport.isAnnotated(parameterContext.getParameter(), UserType.class);
  }

  @Override
  public StaticUser resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
    return extensionContext.getStore(NAMESPACE).get(extensionContext.getUniqueId(), StaticUser.class);
  }
}
