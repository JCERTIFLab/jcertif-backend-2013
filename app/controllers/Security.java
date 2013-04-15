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
	
	public static class AdminAction extends Action<Admin> {
        
		public Result call(Context ctx) throws Throwable{
            return executeAuthAction(this,configuration.value().newInstance(), ctx);     
        }

    }
	
	public static class AuthenticatedAction extends Action<Authenticated> {
        
        public Result call(Context ctx) throws Throwable{
            return executeAuthAction(this,configuration.value().newInstance(), ctx);     
        }
    }
	
	private static Result executeAuthAction(Action action, Authenticator authenticator, Context ctx){
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
