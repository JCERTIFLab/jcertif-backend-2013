import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import models.Token;
import models.database.DBInitializer;
import models.exception.JCertifExceptionHandler;
import models.notifiers.MailerPlugin;
import models.util.Constantes;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.Play;
import play.libs.Akka;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Http.RequestHeader;
import play.mvc.Result;
import play.mvc.Results;
import scala.concurrent.duration.Duration;
import akka.actor.Cancellable;

/**
 * <p>Cette classe est un handler global à l'application</p>
 * 
 * @author Martial SOMDA
 *
 */
public class Global extends GlobalSettings {

	private Cancellable cancellable;
	
	@Override
	public void onStart(Application application) {
		Logger.info("JCertif Backend Application running");
		super.onStart(application);
		Logger.info("Initialisation des données de référence");
		try {
			DBInitializer.init(Constantes.INIT_REF_DATA_FILE);
			if(!Play.isTest() &&
					Play.application().configuration().getBoolean("data.tests.init")){
				Play.application().plugin(MailerPlugin.class).mock();
				DBInitializer.initCounters();
				DBInitializer.init(Constantes.INIT_TESTS_DATA_FILE);
				Play.application().plugin(MailerPlugin.class).release();
			}			
		} catch (IOException e) {
			Logger.error("Impossible d'initialiser les données de réference : " + e.getMessage());			
		}
		
		cancellable = Akka.system().scheduler().schedule(
		  Duration.create(0, TimeUnit.MILLISECONDS), //Initial delay 0 milliseconds
		  Duration.create(3600, TimeUnit.SECONDS),   //Frequency 1 hour
		  new Runnable() {				
				@Override
				public void run() {
					Logger.info("[token remove service] : A purge is running..");
					for(Token token : Token.findAll()){
						if(token.isExpired()){
							token.delete();
						}
					}
				}
			},
		  Akka.system().dispatcher()
		);
	}
	
	@Override
	public void onStop(Application application) {
		Logger.info("JCertif Backend Application shutdown...");
		if(cancellable != null){
			cancellable.cancel();			
		}
		super.onStop(application);
	}

    @Override
    public Action<?> onRequest(Http.Request request, Method method) {
        return new RequestWrapper(super.onRequest(request, method));
    }

    @Override
	public Result onError(RequestHeader requestHeader, Throwable throwable) {
		Logger.info("JCertif Backend onError Global Handler");
		Logger.info("Error URI " + requestHeader.uri());
		Logger.error("Erreur inattendue", throwable);
		
		return JCertifExceptionHandler.resolve(throwable);
	}
	
	@Override
	public Result onHandlerNotFound(RequestHeader requestHeader) {
		Logger.info("JCertif Backend onHandlerNotFound Global Handler");
		Logger.info("Not Found URI " + requestHeader.uri());
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
