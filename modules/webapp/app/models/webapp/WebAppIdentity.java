package models.webapp;

import scala.Option;
import securesocial.core.AuthenticationMethod;
import securesocial.core.Identity;
import securesocial.core.OAuth1Info;
import securesocial.core.OAuth2Info;
import securesocial.core.PasswordInfo;
import securesocial.core.Registry;
import securesocial.core.UserId;
import securesocial.core.providers.UsernamePasswordProvider;

/**
 * <p>Cette classe permet de stocker l'identité d'un utilisateur connecté à la Web App.</p>
 * 
 * @author Martial SOMDA
 *
 */
public class WebAppIdentity implements Identity {

	private String email;
    private String lastname;
    private String firstname;
    private String photo;
    private String accessToken;
    private int expiresIn;
	
    public WebAppIdentity(){
	}
    
	public WebAppIdentity(
			String email,
			String lastname,
			String firstname,
			String photo,
			String accessToken,
			int expiresIn){
		this.email = email;
		this.lastname = lastname;
		this.firstname = firstname;
		this.photo = photo;
		this.accessToken = accessToken;
		this.expiresIn = expiresIn;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

	@Override
	public Option<PasswordInfo> passwordInfo() {
		return scala.Option.apply(Registry.hashers().get(NOpPasswordHasher.ID).get().hash("-"));
	}
	
	@Override
	public Option<OAuth2Info> oAuth2Info() {
		return scala.Option.<OAuth2Info>apply(
				new OAuth2Info(
						accessToken, scala.Option.<String>apply("bearer"),
						scala.Option.<Object>apply(expiresIn), 
						scala.Option.<String>apply(null)));
	}
	
	@Override
	public Option<OAuth1Info> oAuth1Info() {
		return scala.Option.<OAuth1Info>apply(null);
	}
	
	@Override
	public String lastName() {
		return lastname;
	}
	
	@Override
	public UserId id() {
		return new UserId(email, UsernamePasswordProvider.UsernamePassword());
	}
	
	@Override
	public String fullName() {
		return firstName() + " " + lastName();
	}
	
	@Override
	public String firstName() {
		return firstname;
	}
	
	@Override
	public Option<String> email() {
		return scala.Option.apply(email);
	}
	
	@Override
	public Option<String> avatarUrl() {
		return scala.Option.apply(photo);
	}
	
	@Override
	public AuthenticationMethod authMethod() {
		return AuthenticationMethod.UserPassword();
	}
	
	static class Builder {
		private WebAppIdentity identity;
		
		public Builder(){
			identity = new WebAppIdentity();
		}
		
		public Builder withIdentity(Identity identity){
			this.identity = new WebAppIdentity(
					identity.email().get(), 
					identity.lastName(), identity.firstName(), 
					identity.avatarUrl().get(), 
					null, 0);
			return this;
		}
		
		public Builder withEmail(String email){
			this.identity.setEmail(email);
			return this;
		}
		
		public Builder withLastname(String lastname){
			this.identity.setLastname(lastname);
			return this;
		}
		
		public Builder withFirstname(String firstname){
			this.identity.setFirstname(firstname);
			return this;
		}
		
		public Builder withPhoto(String photo){
			this.identity.setPhoto(photo);
			return this;
		}
		
		public Builder withAccessToken(String accessToken){
			this.identity.setAccessToken(accessToken);
			return this;
		}
		
		public Builder withTokenExpiration(int expiresIn){
			this.identity.setExpiresIn(expiresIn);
			return this;
		}
		
		public Identity build(){
			return identity;
		}
	}

}
