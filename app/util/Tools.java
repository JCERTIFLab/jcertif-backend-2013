package util;

import java.util.regex.Pattern;

public class Tools {

    private static final Pattern rfc2822 = Pattern.compile(
            "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$"
    );

    public static boolean isBlankOrNull(String str){
        return null==str || str.trim().length()==0;
    }

    public static boolean isValidEmail(String email){
        return rfc2822.matcher(email).matches() ;
    }

}
