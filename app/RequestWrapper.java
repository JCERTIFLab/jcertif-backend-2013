import models.exception.JCertifExceptionHandler;
import models.exception.JCertifResourceAccessException;
import models.util.Tools;
import play.Logger;
import play.Play;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

public class RequestWrapper extends Action.Simple {

    private static final String TRACE_REQUESTED_URL_TAG = "Requested URL : ";

    public RequestWrapper(Action action) {
        this.delegate = action;
    }

    @Override
    public Result call(Http.Context context) throws Throwable {
        Logger.debug(new StringBuilder().append(TRACE_REQUESTED_URL_TAG)
                .append(context.request().path()).toString());
        
        Result result = null;
        
        try{	
			
        	if (Play.isProd() && !context.request().path().equals("/") && !context.request().path().equals("/admin")) {
                checkAdmin(context.session());
            }
        	
        	if("POST".equals(context.request().method())){
        		Tools.verifyJSonRequest(context.request().body());
        	}
        	
			result = delegate.call(context);
			
			// Cross Origin que si c'est un service
	        if (!context.request().path().equals("/")) {
	            allowCrossOriginJson(context.response());
	        }
			
		}catch (Throwable throwable){
			Logger.error("Error during request processing",throwable);
			result = JCertifExceptionHandler.resolve(throwable);
		}

        return result;
    }

    private void checkAdmin(Http.Session session) {
        if (Play.application().configuration().getBoolean("admin.active") && session.get("admin") == null) {
            throw new JCertifResourceAccessException("Operation not allowed for non-administrators");
        }
    }

    private void allowCrossOriginJson(Http.Response response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.setHeader("Content-Type", "application/json; charset=utf-8");
    }
}
