package models.oauth2;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author Martial SOMDA
 *
 */
public final class GrantedAuthorities {

	@SuppressWarnings("serial")
	public static final GrantedAuthority User = new GrantedAuthority() {		
		@Override
		public String getAuthority() {
			return "User";
		}
	};
	
	@SuppressWarnings("serial")
	public static final GrantedAuthority Backend = new GrantedAuthority() {		
		@Override
		public String getAuthority() {
			return "Backend";
		}
	};
	
	@SuppressWarnings("serial")
	public static final GrantedAuthority Backoffice = new GrantedAuthority() {		
		@Override
		public String getAuthority() {
			return "Backoffice";
		}
	};
	
	@SuppressWarnings("serial")
	public static final GrantedAuthority JCetifAndroid = new GrantedAuthority() {		
		@Override
		public String getAuthority() {
			return "JCetifAndroid";
		}
	};
	
	@SuppressWarnings("serial")
	public static final GrantedAuthority JcertifOfficial = new GrantedAuthority() {		
		@Override
		public String getAuthority() {
			return "JcertifOfficial";
		}
	};
	
	private static Map<String, GrantedAuthority> ENUM_MAP;
	
	static {
		ENUM_MAP = new HashMap<String, GrantedAuthority>();
		ENUM_MAP.put("User", GrantedAuthorities.User);
		ENUM_MAP.put("Backend", GrantedAuthorities.Backend);
		ENUM_MAP.put("Backoffice", GrantedAuthorities.Backoffice);
		ENUM_MAP.put("JCetifAndroid", GrantedAuthorities.JCetifAndroid);
		ENUM_MAP.put("JcertifOfficial", GrantedAuthorities.JcertifOfficial);
	}

	private GrantedAuthorities(){
	}
	
	public static GrantedAuthority fromValue(String grantCode){
		return ENUM_MAP.get(grantCode);
	}
	
	
}
