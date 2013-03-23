import models.exception.JCertifResourceAccessException;
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
        if (Play.isProd() && !context.request().path().equals("/") && !context.request().path().equals("/admin")) {
            checkAdmin(context.session());
        }
        Result result = delegate.call(context);
        // Cross Origin que si c'est un service
        if (!context.request().path().equals("/")) {
            allowCrossOriginJson(context.response());
        }

        return result;
    }

    private void checkAdmin(Http.Session session) {
        if (session.get("admin") == null) {
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
