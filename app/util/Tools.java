package util;

import java.io.InputStream;
import java.util.regex.Pattern;

import play.Play;

public class Tools {

	private static final Pattern rfc2822 = Pattern
			.compile("^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$");

	public static boolean isBlankOrNull(String str) {
		return null == str || str.trim().length() == 0;
	}

	public static boolean isValidEmail(String email) {
		return rfc2822.matcher(email).matches();
	}

	public static String getString(String confFile) {
		InputStream is = Play.application().resourceAsStream(confFile);
		if (is != null) {
			try {
				StringBuilder c = new StringBuilder();
				byte[] b = new byte[1024];
				int read = -1;
				while ((read = is.read(b)) > 0) {
					c.append(new String(b, 0, read));
				}
				return c.toString();
			} catch (Throwable e) {
				//
			}
		}
		return "";
	}

}
