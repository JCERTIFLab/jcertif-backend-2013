import java.io.UnsupportedEncodingException;

import models.exception.JCertifExceptionHandler;
import models.util.Tools;
import play.Logger;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Http.Response;
import play.mvc.Result;
import play.mvc.Results;

public class RequestWrapper extends Action.Simple {

    private static final String TRACE_REQUESTED_URL_TAG = "Requested URL : ";

    public RequestWrapper(Action<?> action) {
        this.delegate = action;
    }

    @Override
    public Result call(Http.Context context) throws Throwable {
        Logger.debug(new StringBuilder().append(TRACE_REQUESTED_URL_TAG)
                .append(context.request().path()).toString());
        
        Result result = null;
        
        try{	
        	
        	if("POST".equals(context.request().method())){
        		Tools.verifyJSonRequest(context.request().body());
        	}
        	
			result = delegate.call(context);
			
			// Cross Origin que si c'est un service
	        if (!context.request().path().contains("/home")) {
	        	String jsonpCallback = context.request().getQueryString("jsonp");
	        	if(null != jsonpCallback 
	        			&& "GET".equals(context.request().method())){
	        		result = jsonpify(result, jsonpCallback);
	        		allowCrossOriginJsonP(context.response());
	        	}else{
	        		allowCrossOriginJson(context.response());
	        	}
	        }
			
		}catch (Throwable throwable){
			Logger.error("Error during request processing",throwable);
			result = JCertifExceptionHandler.resolve(throwable);
		}

        return result;
    }

    private Result jsonpify(Result result, String callback) throws UnsupportedEncodingException {
    	int status = play.core.j.JavaResultExtractor.getStatus(result);
		String body = new String(play.core.j.JavaResultExtractor.getBody(result),"utf-8");
		return Results.status(status, callback + "(" + body + ")");
	}

	private void allowCrossOriginJsonP(Response response) {
    	allowCrossOrigin(response);
        response.setHeader("Content-Type", "text/javascript; charset=utf-8");
	}

	private void allowCrossOriginJson(Http.Response response) {
		allowCrossOrigin(response);
        response.setHeader("Content-Type", "application/json; charset=utf-8");
    }
	
	private void allowCrossOrigin(Http.Response response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    }
}
