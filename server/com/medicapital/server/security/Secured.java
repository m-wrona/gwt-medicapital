package com.medicapital.server.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.medicapital.common.entities.UserRole;

/**
 * Annotation marks that specified access rights are required to invoke given
 * method.
 * 
 * @author mwronski
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Secured {

	/**
	 * Role required to perform an action
	 * 
	 * @return
	 */
	UserRole role() default UserRole.User;

}
