package models.webapp;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.codehaus.jackson.JsonNode;

import play.Application;
import play.Logger;
import play.Play;
import play.libs.Json;
import play.libs.WS;
import play.libs.WS.Response;
import play.mvc.Http;
import scala.Option;
import securesocial.core.AuthenticationMethod;
import securesocial.core.Identity;
import securesocial.core.UserId;
import securesocial.core.UserService;
import securesocial.core.java.BaseUserService;
import securesocial.core.java.Token;
import securesocial.core.providers.UsernamePasswordProvider;

/**
 * <p>An implementation of {@link UserService} that stores registered users as JCertif Participants.<br/>
 * This implementation is also responsible of communication with backend to retrieve access tokens
 * on user log in</p>
 * 
 * @author Martial SOMDA
 *
 */
public class WebAppUserService extends BaseUserService{

	private static final String BackendHost = Play.application().configuration().getString("backend.host");
	private Map<String, Identity> users  = new HashMap<String, Identity>();
	private Map<String, Token> tokens = new HashMap<String, Token>();
	
	public WebAppUserService(Application application) {
		super(application);
	}

	@Override
	public void doDeleteExpiredTokens() {
		Logger.info("[user service] : doDeleteExpiredTokens");
		Iterator<Map.Entry<String,Token>> iterator = tokens.entrySet().iterator();
        while ( iterator.hasNext() ) {
            Map.Entry<String, Token> entry = iterator.next();
            if ( entry.getValue().isExpired() ) {
            	Logger.info("[user service] : remove token " + entry.getValue().uuid);
                iterator.remove();
            }
        }
	}

	@Override
	public void doDeleteToken(String uuid) {
		Logger.info("[user service] : doDeleteToken " + uuid);
		tokens.remove(uuid);
	}

	@Override
	public Identity doFind(UserId userId) {
		Logger.info("[user service] : doFind " + userId);
		Identity userIdentity = users.get(userId.id() + userId.providerId());
		if(userIdentity == null){
			Logger.info("[user service] : user not in web app cache, looking up toward backend");
			userIdentity = findUserInBackend(userId.id());
		}
		return userIdentity;
	}

	@Override
	public Identity doFindByEmailAndProvider(String email, String providerId) {
		Logger.info("[user service] : doFindByEmailAndProvider " + email + " " + providerId);
		Identity result = null;
        for( Identity user : users.values() ) {
            Option<String> optionalEmail = user.email();
            if ( user.id().providerId().equals(providerId) 
            		&& optionalEmail.isDefined() 
            		&& optionalEmail.get().equalsIgnoreCase(email)){
                result = user;
                break;
            }
        }
        if(result == null){
        	Logger.info("[user service] : no username registered under " + email + " in web app cache, looking up toward backend");
        	result = findUserInBackend(email);
        }
        return result;
	}

	@Override
	public Token doFindToken(String tokenId) {
		Logger.info("[user service] : doFindToken " + tokenId);
		return tokens.get(tokenId);
	}

	@Override
	public Identity doSave(Identity user) {
		Logger.info("[user service] : doSave " + user);
		Identity identity = findUserInBackend(user.email().get());
		if(identity == null
				&& user.authMethod() == AuthenticationMethod.UserPassword()){
			identity = createUserInBackend(user);
		}else{
			identity = user;
		}
		users.put(identity.id().id() + identity.id().providerId(), identity);
        return identity;
	}

	@Override
	public void doSave(Token token) {
		Logger.info("[user service] : doSave " + token);
		tokens.put(token.uuid, token);
	}
	
	private Identity findUserInBackend(String email){
		Identity result = null;
		Response response = WS.url(BackendHost + "/participant/find/" + email).get().get();
		
		if(Http.Status.OK == response.getStatus()){
			JsonNode jsonNode = response.asJson();
			String lastname = jsonNode.findPath("lastname").getTextValue();
			String firstname = jsonNode.findPath("firstname").getTextValue();
			String photo = jsonNode.findPath("photo").getTextValue();
			
			result = enhanceUserWithAccessToBackend(
					new WebAppIdentity.Builder().withEmail(email).withLastname(lastname)
					.withFirstname(firstname).withPhoto(photo).build());
			
			
		}else{
			Logger.info("[user service] : user " + email + " not found in backend");
		}
		return result;
	}
	
	private Identity createUserInBackend(Identity user){
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("email", user.email().get());		
		map.put("password", user.passwordInfo().get().password());
		map.put("title", "M.");
		map.put("lastname", user.lastName());
		map.put("firstname", user.firstName());
		map.put("city", "Paris");
		map.put("country", "France");
		map.put("photo", user.avatarUrl().get());
		WS.url(BackendHost + "/participant/register").post(Json.toJson(map)).get();

		return enhanceUserWithAccessToBackend(user);
	}
	
	private Identity enhanceUserWithAccessToBackend(Identity user){
		
		Response response = WS.url(BackendHost + "/token/new")
		.setQueryParameter("user", user.email().get())
		.setQueryParameter("provider", UsernamePasswordProvider.UsernamePassword())
		.setAuth("webapp", "password").get().get();
		
		if(Http.Status.OK == response.getStatus()){
			JsonNode jsonNode = response.asJson();
			String accessToken = jsonNode.findPath("access_token").getTextValue();
			int expiresIn = jsonNode.findPath("expires_in").getIntValue();
			
			return new WebAppIdentity.Builder()
						.withIdentity(user).withAccessToken(accessToken)
							.withTokenExpiration(expiresIn).build();
		}else{
			Logger.info("[user service] : impossible de g�n�rer un token d'access pour le user " + user.email().get());
			return user;
		}
	}

}
