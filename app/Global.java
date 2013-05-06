import java.io.IOException;
import java.lang.reflect.Method;

import models.database.MongoDB;
import models.exception.JCertifExceptionHandler;
import models.util.Constantes;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.Play;
import play.mvc.Action;
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
		Logger.info("Initialisation des données de référence");
		try {
			MongoDB.getInstance().loadDbWithData(Constantes.INIT_DATA_FILE);
		} catch (IOException e) {
			Logger.info("Impossible d'initialiser les données de réference : " + e.getMessage());			
		}
	}
	
	@Override
	public void onStop(Application application) {
		Logger.info("JCertif Backend Application shutdown...");
		super.onStop(application);
	}

    @Override
    public Action<?> onRequest(Http.Request request, Method method) {
        return new RequestWrapper(super.onRequest(request, method));
    }

    @Override
	public Result onError(RequestHeader requestHeader, Throwable throwable) {
		Logger.info("JCertif Backend onError Global Handler");
		Logger.error("Erreur inattendue", throwable);
		
		return JCertifExceptionHandler.resolve(throwable);
	}
	
	@Override
	public Result onHandlerNotFound(RequestHeader requestHeader) {
		Logger.info("JCertif Backend onHandlerNotFound Global Handler");
		
		putResponseStatusInCoockieIfNecessary(Http.Status.NOT_FOUND);
		
		return Results.notFound();
	}
	
	/**
	 * @param result
	 */
	private void putResponseStatusInCoockieIfNecessary(int httpStatus){
		if(!Play.isProd()){
			Http.Context.current().session().put("status", Integer.toString(httpStatus));
		}
	}
}
