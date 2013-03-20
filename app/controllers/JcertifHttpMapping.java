package controllers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import play.mvc.Http;

/**
 * <p>Annotation permettant d'indiquer un mapping entre une exception 
 * et le status HTTP à renvoyer au client</p>
 * 
 * @author Martial SOMDA
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface JcertifHttpMapping {
	int status() default Http.Status.INTERNAL_SERVER_ERROR;
}
