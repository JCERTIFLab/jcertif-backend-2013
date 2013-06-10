package controllers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import models.exception.JCertifExceptionHandler;
import play.mvc.Action;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security.Authenticator;
import play.mvc.With;

/**
 * <p>Cette classe contient les interfaces et les classes necessaires à la mise en place d'une
 * politique de sécurity dans l'application JCertif Backend.</p>
 * 
 * @author Martial SOMDA
 *
 */
public class Security {

	/*
	 * Annotation pour les services nécessitant une authentification basic
	 */
	@With(BasicAction.class)
	@Target({ElementType.TYPE, ElementType.METHOD})
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Basic {
	    Class<? extends Authenticator> value() default BasicAuthenticator.class;
	}
	
	/*
	 * Annotation pour les services nécessitant des droit admin
	 */
	@With(AdminAction.class)
	@Target({ElementType.TYPE, ElementType.METHOD})
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Admin {
	    Class<? extends Authenticator> value() default AdminAuthenticator.class;
	}
	
	/*
	 * Annotation pour les services nécessitant d'être connecté à l'application
	 */
	@With(AuthenticatedAction.class)
	@Target({ElementType.TYPE, ElementType.METHOD})
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Authenticated {
	    Class<? extends Authenticator> value() default DefaultAuthenticator.class;
	}
	
	/**
	 * Action exécutée pour les services nécessitant une authentification basic
	 * @see Basic
	 */
	public static class BasicAction extends Action<Basic> {
        
		public Result call(Context ctx) throws Throwable{
            return executeAuthAction(this,configuration.value().newInstance(), ctx);     
        }

    }
	
	/**
	 * Action exécutée pour les services nécessitant les droits admin
	 * @see Admin
	 */
	public static class AdminAction extends Action<Admin> {
        
		public Result call(Context ctx) throws Throwable{
            return executeAuthAction(this,configuration.value().newInstance(), ctx);     
        }

    }
	
	/**
	 * Action exécutée pour les services nécessitant une authentification uilisateur
	 * @see Authenticated
	 */
	public static class AuthenticatedAction extends Action<Authenticated> {
        
        public Result call(Context ctx) throws Throwable{
            return executeAuthAction(this,configuration.value().newInstance(), ctx);     
        }
    }
	
	/**
	 * Exécute une action que si l'utilisateur possède les droits idoines
	 * 
	 * @param action Action à exécuter
	 * @param authenticator Classe permettant de vérifier les droits de l'utilisateur 
	 * @param ctx Contexte HTTP
	 * 
	 * @return Le résltat de l'action exécutée
	 */
	private static Result executeAuthAction(Action<?> action, Authenticator authenticator, Context ctx){
		Result result = null;
		try {
            String username = authenticator.getUsername(ctx);
            if(username == null) {
            	result = authenticator.onUnauthorized(ctx);
            } else {
                try {
                    ctx.request().setUsername(username);
                    result = action.delegate.call(ctx);
                } finally {
                    ctx.request().setUsername(null);
                }
            }
        } catch(Throwable t) {
        	result = JCertifExceptionHandler.resolve(t);
        }
        return result;
	}
}
