package fr.plb.springsecuritydemo.service.dto.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordPolicyValidator.class)
public @interface PasswordPolicy {
    String message() default "Le mot de passe n'est pas valide";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
