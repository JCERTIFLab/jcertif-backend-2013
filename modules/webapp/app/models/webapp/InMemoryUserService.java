package models.webapp;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import play.Application;
import play.Logger;
import scala.Option;
import securesocial.core.Identity;
import securesocial.core.UserId;
import securesocial.core.java.BaseUserService;
import securesocial.core.java.Token;

public class InMemoryUserService extends BaseUserService {

	private Map<String, Identity> users  = new HashMap<String, Identity>();
    private Map<String, Token> tokens = new HashMap<String, Token>();
    
	public InMemoryUserService(Application application) {
		super(application);
	}

	/* (non-Javadoc)
	 * @see securesocial.core.java.BaseUserService#doDeleteExpiredTokens()
	 */
	@Override
	public void doDeleteExpiredTokens() {
		Logger.info("[user service] : doDeleteExpiredTokens");
		Iterator<Map.Entry<String,Token>> iterator = tokens.entrySet().iterator();
        while ( iterator.hasNext() ) {
            Map.Entry<String, Token> entry = iterator.next();
            if ( entry.getValue().isExpired() ) {
                iterator.remove();
            }
        }
	}

	/* (non-Javadoc)
	 * @see securesocial.core.java.BaseUserService#doDeleteToken(java.lang.String)
	 */
	@Override
	public void doDeleteToken(String uuid) {
		Logger.info("[user service] : doDeleteToken " + uuid);
		tokens.remove(uuid);
	}

	/* (non-Javadoc)
	 * @see securesocial.core.java.BaseUserService#doFind(securesocial.core.UserId)
	 */
	@Override
	public Identity doFind(UserId userId) {
		Logger.info("[user service] : doFind " + userId);
		return users.get(userId.id() + userId.providerId());
	}

	/* (non-Javadoc)
	 * @see securesocial.core.java.BaseUserService#doFindByEmailAndProvider(java.lang.String, java.lang.String)
	 */
	@Override
	public Identity doFindByEmailAndProvider(String email, String providerId) {
		Logger.info("[user service] : doFindByEmailAndProvider " + email + " " + providerId);
		Identity result = null;
        for( Identity user : users.values() ) {
            Option<String> optionalEmail = user.email();
            if ( user.id().providerId().equals(providerId) &&
                 optionalEmail.isDefined() &&
                 optionalEmail.get().equalsIgnoreCase(email))
            {
                result = user;
                break;
            }
        }
        return result;
	}

	/* (non-Javadoc)
	 * @see securesocial.core.java.BaseUserService#doFindToken(java.lang.String)
	 */
	@Override
	public Token doFindToken(String tokenId) {
		Logger.info("[user service] : doFindToken " + tokenId);
		return tokens.get(tokenId);
	}

	/* (non-Javadoc)
	 * @see securesocial.core.java.BaseUserService#doSave(securesocial.core.Identity)
	 */
	@Override
	public Identity doSave(Identity user) {
		Logger.info("[user service] : doSave " + user);
		users.put(user.id().id() + user.id().providerId(), user);
        // this sample returns the same user object, but you could return an instance of your own class
        // here as long as it implements the Identity interface. This will allow you to use your own class in the
        // protected actions and event callbacks. The same goes for the doFind(UserId userId) method.
        return user;
	}

	/* (non-Javadoc)
	 * @see securesocial.core.java.BaseUserService#doSave(securesocial.core.java.Token)
	 */
	@Override
	public void doSave(Token token) {
		Logger.info("[user service] : doSave " + token);
		tokens.put(token.uuid, token);
	}

}
