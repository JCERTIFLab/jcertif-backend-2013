import controllers.JcertifHttpMapping;
import models.exception.JCertifException;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.mvc.Http;
import play.mvc.Http.RequestHeader;
import play.mvc.Result;
import play.mvc.Results;

/**
 * <p>Cette classe est un handler global à l'application</p>
 * 
 * @author Martial SOMDA
 *
 */
public class Global extends GlobalSettings {

	@Override
	public void onStart(Application application) {
		Logger.info("JCertif Backend Application running");
		super.onStart(application);
	}
	
	@Override
	public void onStop(Application application) {
		Logger.info("JCertif Backend Application shutdown...");
		super.onStop(application);
	}

	
	@Override
	public Result onError(RequestHeader requestHeader, Throwable throwable) {
		
		Logger.info("JCertif Backend onError Global Handler");

		Result result = Results.status(Http.Status.INTERNAL_SERVER_ERROR, throwable.getMessage());
		
		if(throwable.getCause() instanceof JCertifException
				&& throwable.getCause().getClass().isAnnotationPresent(JcertifHttpMapping.class)){

			JcertifHttpMapping mapped = throwable.getCause().getClass().getAnnotation(JcertifHttpMapping.class);
			
			result = Results.status(mapped.status(), throwable.getCause().getMessage());
		}
		
		return result;
	}
}
