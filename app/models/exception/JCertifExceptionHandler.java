package models.exception;

import play.Logger;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

/**
 * <p>Classe Helper pour la gestion des exception.</p>
 * 
 * @author Martial SOMDA
 *
 */
public final class JCertifExceptionHandler {

	private JCertifExceptionHandler(){		
	}

	public static Result resolve(Throwable throwable){
		
		Logger.info("JCertif ExceptionHandler");
		Logger.error("Error : " + throwable.getMessage(), throwable);

		Result result = Results.status(Http.Status.INTERNAL_SERVER_ERROR, null == throwable.getMessage()? "" : throwable.getMessage());
		
		Throwable unwrappedThrowable = unwrap(throwable);
		
		if(null != unwrappedThrowable 
				&& unwrappedThrowable instanceof JCertifException
				&& unwrappedThrowable.getClass().isAnnotationPresent(JCertifExceptionMapping.class)){

			JCertifExceptionMapping mapping = unwrappedThrowable.getClass().getAnnotation(JCertifExceptionMapping.class);
			
			result = Results.status(mapping.status(), unwrappedThrowable.getMessage());
		}
		
		return result;
	}

	private static Throwable unwrap(Throwable throwable) {

		Throwable unwrappedThrowable = throwable;

		while(null != unwrappedThrowable 
				&& unwrappedThrowable.getClass().getCanonicalName().contains("play.")){
			unwrappedThrowable = unwrappedThrowable.getCause();
		}
		return unwrappedThrowable;
	}
}
