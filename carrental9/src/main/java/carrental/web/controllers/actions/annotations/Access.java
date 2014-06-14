package carrental.web.controllers.actions.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import carrental.domain.UserStatus;

@Retention(value= RetentionPolicy.RUNTIME)
@Target(value=ElementType.TYPE)
public @interface Access {
	UserStatus[] value() default UserStatus.USER;
}
