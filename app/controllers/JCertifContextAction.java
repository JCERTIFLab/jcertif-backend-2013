package controllers;


import models.exception.JCertifResourceAccessException;
import models.util.Tools;
import play.Logger;
import play.mvc.Action;
import play.mvc.Http.Context;
import play.mvc.Http.Response;
import play.mvc.Http.Session;
import play.mvc.Result;
import scala.collection.mutable.StringBuilder;

/**
 * <p>Action englobante faisant office d'intercepteur pour
 * les actions des controleurs JCertif Backend.</p>
 * <p>Cette action est appelée par le Framework Play lors de lutilisation de 
 * l'annotation {@link JCertifContext} et réalise les traitement suivants :</p>
 * <dl>
 * <dt>pré-traitements</dt>
 * <dd>log d'entrée de méthode<dd>
 * <dd>vérification des habilitations<dd><br/>
 * <dt>délégation du traitement à l'action cible</dt><br/><br/>
 * <dt>post-traitements</dt>
 * <dd>Appliquer l'autorisation cross origin<dd>
 * <dd>log de sortie de méthode<dd>
 * </dl>
 * 
 * <pre>
 * <code>
 * @JCertifContext(admin=true)
 *	public static Result addSponsorLevel() {
 *   //do some stuff
 *   return ok();
 *  }
 * </code>
 * </pre>
 * 
 * @author Martial SOMDA
 * @see JCertifContext
 */
public class JCertifContextAction extends Action<JCertifContext> {

	private static final String TRACE_REQUESTED_URL_TAG = "Requested URL : ";
	
	@Override
	public Result call(Context context) throws Throwable {

		Logger.debug(new StringBuilder().append(TRACE_REQUESTED_URL_TAG)
				.append(context.request().path()).toString());
		
		if (configuration.admin()) {
			checkAdmin(context.session());
		}
		
		Tools.verifyJSonRequest(context.request().body());
		
		Result result = delegate.call(context);
		
		allowCrossOriginJson(context.response());
		
		return result;
	}
	
	private void checkAdmin(Session session) {
		if(session.get("admin") == null){
			throw new JCertifResourceAccessException("Operation not allowed for non-administrators");
		}
	}

	private void allowCrossOriginJson(Response response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setHeader("Content-Type", "application/json; charset=utf-8");
    }

}
