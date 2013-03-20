package controllers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import play.mvc.With;

/**
 * <p>Marqueur permetant de définir une méthode devant s'exécuter dans
 * dans le contexte offert par la classe {@link JCertifContextAction} </p>
 * 
 * @author Martial SOMDA
 *
 */
@With(JCertifContextAction.class)
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JCertifContext {
	String action();
	boolean admin() default false;
}
