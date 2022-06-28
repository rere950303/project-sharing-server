package YHWLTH.sharing.annotation;

import YHWLTH.sharing.security.WithMockCustomUserSecurityContextFactory;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.*;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {

    String username() default "yhw";

    String studentId() default "2014170089";

    String password() default "1234";

    String passwordConfirm() default "1234";

    String role() default "ROLE_USER";
}