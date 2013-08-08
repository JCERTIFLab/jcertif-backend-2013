package models.util;

import com.mongodb.BasicDBList;
import models.exception.JCertifInvalidRequestException;
import play.Logger;
import play.Play;
import play.mvc.Http;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public final class Tools {

    private Tools(){

    }

    private static Pattern rfc2822 = Pattern
            .compile("^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$");

    private static final Pattern NUMBER_PATTERN = Pattern.compile(".*[^0-9].*");

    public static boolean isBlankOrNull(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isBlankOrNull(Collection<?> list) {
        return (null == list) || (list.isEmpty());
    }

    public static boolean isValidEmail(String email) {
        return rfc2822.matcher(email).matches();
    }

    public static String getFileContent(String confFile) throws IOException {
        InputStream is = Play.application().resourceAsStream(confFile);
        if (is != null) {
            StringBuilder c = new StringBuilder();
            byte[] b = new byte[Integer.valueOf("1024").intValue()];
            int read = -1;
            while ((read = is.read(b)) > 0) {
                c.append(new String(b, 0, read));
            }
            return c.toString();
        }
        return "";
    }

    public static void println(String comment, Map<?,?> mapToPrint) {
        if (((comment) != (null)) && ((0)<(comment.length()))) {
                Logger.info(comment);
        }
        if ((mapToPrint) != (null)) {
            Set<?> entrySet = mapToPrint.entrySet();
            for (Iterator<?> itEntrySet = entrySet.iterator(); itEntrySet.hasNext(); ) {
                Map.Entry<?,?> entry = (Map.Entry<?,?>)itEntrySet.next() ;
                Logger.info("     [" + entry.getKey() + " = " + entry.getValue().toString() + "]");
            }
        }
    }

    public static boolean isValidDate(String dateToValidate){
        if(Tools.isBlankOrNull(dateToValidate)){
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(Constantes.DATEFORMAT, Locale.FRANCE);
        sdf.setLenient(false);

        try {
            sdf.parse(dateToValidate);
        } catch (ParseException e) {
            return false;
        }

        return true;
    }

    public static List<String> basicDBListToJavaList(Object object){
        List<String> retList = new ArrayList<String>();
        if(object==null){
            return retList;
        }
             
        if(object instanceof BasicDBList){
        	BasicDBList basicDBList = BasicDBList.class.cast(object);
        	for(Iterator<Object> iterator = basicDBList.iterator();iterator.hasNext();){
                retList.add((String)iterator.next());
            }
        }else if(object instanceof String){
        	retList.add((String)object);
        }
        
        return retList;
    }

    public static BasicDBList javaListToBasicDBList(List<?> javaList){
        BasicDBList retList = new BasicDBList();
        if(javaList==null){
            return retList;
        }
        for(Iterator<?> iterator = javaList.iterator();iterator.hasNext();){
            retList.add(iterator.next());
        }
        return retList;
    }

    public static BasicDBList javaStringToBasicDBList(String str){
        List<String> list;
        
        // TODO: test a revoir, trouver un meilleur moyen pour détecter les délimiteur du contenu json
        if(null == str || "".equals(str)){
        	list = new ArrayList <String>();
        } else if(-1 != str.indexOf('[') || -1 != str.indexOf('{')){
        	list = Arrays.asList(str.substring(1, str.length() - 1).split(","));
		} else{
			list = Arrays.asList(str.split(","));
		}
        return javaListToBasicDBList(list);
    }
    
    public static void verifyJSonRequest(Http.RequestBody requestBody) {
    	
        if(requestBody == null || requestBody.asJson() == null){
            throw new JCertifInvalidRequestException(Tools.class, "verifyJSonRequest(), Request has not JSon Content-Type");
        }
    }
    
    public static boolean isNotValidNumber(String innerID) {
    	return NUMBER_PATTERN.matcher(innerID).matches();
    }

}
