package controllers;

import org.codehaus.jackson.JsonNode;

import models.TokenChecksFactoy;
import models.TokenChecksFactoy.TokenCheck;
import models.util.Tools;
import play.Logger;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security.Authenticator;


/**
 * @author Martial SOMDA
 */
public class DefaultAuthenticator extends Authenticator {

    @Override
    public String getUsername(Context context) {
        // Par défaut recherche des infos d'authentification dans l'URL
        String accessToken = context.request().getQueryString("access_token");
        String provider = context.request().getQueryString("provider");
        String email = context.request().getQueryString("user");

        // Si les infos d'authentification ne sont pas trouvées,
        // on s'autorise à les chercher dans le corps de la requête
        // Le cas du GET est écarté car par principe la requête ne doit pas contenir de corps.
        if ((Tools.isBlankOrNull(accessToken) || Tools.isBlankOrNull(provider)) && !"GET".equals(context.request().method())) {
            JsonNode node = context.request().body().asJson().findPath("access_token");
            if (null != node) {
                accessToken = node.getTextValue();
            }
            node = context.request().body().asJson().findPath("provider");
            if (null != node) {
                provider = node.getTextValue();
            }
            node = context.request().body().asJson().findPath("user");
            if (null != node) {
                email = node.getTextValue();
            }
        }

        if (Tools.isBlankOrNull(accessToken) || Tools.isBlankOrNull(provider)) {
            return null;
        }

        TokenCheck check = TokenChecksFactoy.getInstance().getCheck(provider);
        if (check.isValid(accessToken, email)) {
            Logger.info("token is valid");
            return accessToken;
        } else {
            return null;
        }
    }

    @Override
    public Result onUnauthorized(Context context) {
        return unauthorized("Operation not allowed for non-authenticated people");
    }
}
