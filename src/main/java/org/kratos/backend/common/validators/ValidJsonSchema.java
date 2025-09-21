package org.kratos.backend.common.validators;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint (validatedBy = JsonSchemaValidator.class)
@Target ({ElementType.FIELD, ElementType.PARAMETER})
@Retention (RetentionPolicy.RUNTIME)
public @interface ValidJsonSchema {
	
	String schema();
	
	String message() default "JSON does not match schema";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
	
}
