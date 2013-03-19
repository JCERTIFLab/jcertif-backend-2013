package controllers;


import models.exception.JCertifDuplicateObjectException;
import models.exception.JCertifException;
import models.exception.JCertifInvalidRequestException;
import models.exception.JCertifObjectNotFoundException;
import models.exception.JCertifResourceAccessException;
import models.util.Tools;
import play.Logger;
import play.mvc.Http;
import play.mvc.Result;
import scala.collection.mutable.StringBuilder;

/**
 * <p>
 * Cette Classe founit un Contexte d'exécution aux actions des controllers. Ce
 * contexte d'exécution décharge les differents controleurs de la gestion des
 * logs et des erreurs.
 * </p>
 * 
 * <pre>
 * <code>
 * ActionCallback callBack = new ActionCallback("ReferentielController.addSponsorLevel") {
 * 	
 * 	@Override
 * 	public Result execute() throws JCertifException {
 * 		String myBody = request().body();		
 * 		Model myModel = new Model();	
 * 		return ok();
 * 	}
 * };
 * JSONActionTemplate template = new JSONActionTemplate();
 * return template.doAction(callBack, true);
 * </code>
 * </pre>
 * 
 * @author Martial SOMDA
 */
public abstract class ActionTemplate {

	protected static final String TRACE_START_TAG = "[DEBUT] :";
	protected static final String TRACE_END_TAG = "[FIN] :";
	public abstract Result doAction(ActionCallback actionCallback,
			boolean isAdminDelegated);

	/**
	 * Classe Callback s'executant dans le contexte d'un {@link ActionTemplate}
	 *
	 */
	static abstract class ActionCallback {
		private final String actionName;

		String getActionName() {
			return actionName;
		}

		public ActionCallback(String actionName) {
			this.actionName = actionName;
		}

		public abstract Result execute() throws Exception;
	}
}
/**
 * <p>
 * Contexte d'exécution pour les actions répondant à la méthode GET 
 * sur des controleurs manipulant des données au format JSON.
 * </p>
 */
class JSONGetActionTemplate extends ActionTemplate {

	@Override
	public Result doAction(ActionCallback actionCallback, boolean isAdminDelegated) {
		Logger.debug(new StringBuilder(TRACE_START_TAG).append(actionCallback.getActionName()).toString());
		AbstractController.allowCrossOriginJson();
		Result result = null;
		try {
			
			if (isAdminDelegated) {
				AbstractController.checkAdmin();
			}
			
			result = actionCallback.execute();
		} catch (JCertifResourceAccessException accessException) {
			Logger.error(accessException.getMessage(), accessException);
			return AbstractController.forbidden(accessException.getMessage());
		} catch (JCertifException jcertifException) {
			Logger.error(jcertifException.getMessage());
			return AbstractController.internalServerError(jcertifException.getMessage());
		} catch (Exception exception) {
			Logger.error("JCertif-Backend Unhandled Exception :" + exception.getMessage());
			return AbstractController.internalServerError(new JCertifException(
					"Unhandled Exception", exception).getMessage());
		}

		Logger.debug(new StringBuilder(TRACE_END_TAG).append(actionCallback.getActionName()).toString());
		return result;
	}
	
}
/**
 * <p>
 * Contexte d'exécution pour les actions répondant à la méthode POST 
 * sur des controleurs manipulant des données au format JSON.
 * </p>
 */
class JSONPostActionTemplate extends ActionTemplate {

	@Override
	public Result doAction(ActionCallback actionCallback,
			boolean isAdminDelegated) {
		Logger.debug(new StringBuilder(TRACE_START_TAG).append(actionCallback.getActionName()).toString());
		AbstractController.allowCrossOriginJson();

		Result result = null;
		try {
			if (isAdminDelegated) {
				AbstractController.checkAdmin();
			}

			Http.RequestBody requestBody = AbstractController.request().body();
			Tools.verifyJSonRequest(requestBody);

			result = actionCallback.execute();

		} catch (JCertifResourceAccessException accessException) {
			Logger.error(accessException.getMessage(), accessException);
			return AbstractController.forbidden(accessException.getMessage());
		} catch (JCertifInvalidRequestException badReqException) {
			Logger.error(badReqException.getMessage(), badReqException);
			return AbstractController.badRequest(badReqException.getMessage());
		} catch (JCertifDuplicateObjectException duplicateObjException) {
			Logger.error(duplicateObjException.getMessage(), duplicateObjException);
			return AbstractController.status(AbstractController.CONFLICT,duplicateObjException.getMessage());
		} catch (JCertifObjectNotFoundException notFoundObjException) {
			Logger.error(notFoundObjException.getMessage(), notFoundObjException);
			return AbstractController.notFound(notFoundObjException.getMessage());
		} catch (JCertifException jcertifException) {
			Logger.error(jcertifException.getMessage());
			return AbstractController.internalServerError(jcertifException.getMessage());
		} catch (Exception exception) {
			Logger.error("JCertif-Backend Unhandled Exception :" + exception.getMessage());
			return AbstractController.internalServerError(new JCertifException(
					"Unhandled Exception", exception).getMessage());
		}

		Logger.debug(new StringBuilder(TRACE_END_TAG).append(actionCallback.getActionName()).toString());
		return result;
	}

}
