package models.util;

import com.mongodb.BasicDBList;
import models.exception.JCertifException;
import play.Play;
import play.mvc.Http;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class Tools {

    private static Pattern rfc2822 = Pattern
            .compile("^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$");

    public static boolean isBlankOrNull(String str) {
        return null == str || str.trim().length() == 0;
    }

    public static boolean isBlankOrNull(List list) {
        return null == list || list.isEmpty();
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

    public static void println(String comment, Map mapToPrint) {
        if (comment != null && 0<comment.length())
                System.out.println(comment);
        if (mapToPrint != null) {
            Set entrySet = mapToPrint.entrySet();
            for (Iterator itEntrySet = entrySet.iterator(); itEntrySet.hasNext(); ) {
                Map.Entry entry = (Map.Entry)itEntrySet.next() ;
                System.out.println("     [" + entry.getKey() + " = " + entry.getValue().toString() + "]");
            }
        }
    }

    public static boolean isValidDate(String dateToValidate){
        if(dateToValidate == null){
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(Constantes.DATEFORMAT);
        sdf.setLenient(false);

        try {
            sdf.parse(dateToValidate);
        } catch (ParseException e) {
            return false;
        }

        return true;
    }

    public static List basicDBListToJavaList(BasicDBList basicDBList){
        List retList = new ArrayList();
        if(basicDBList==null) return retList;
        for(Iterator<Object> iterator = basicDBList.iterator();iterator.hasNext();){
            retList.add(iterator.next());
        }
        return retList;
    }

    public static BasicDBList javaListToBasicDBList(List javaList){
        BasicDBList retList = new BasicDBList();
        if(javaList==null) return retList;
        for(Iterator iterator = javaList.iterator();iterator.hasNext();){
            retList.add(iterator.next());
        }
        return retList;
    }

    public static void verifyJSonRequest(Http.RequestBody requestBody) throws JCertifException {
        if(requestBody==null || requestBody.asJson()==null){
            throw new JCertifException(Tools.class, "verifyJSonRequest(), Request has not JSon Content-Type");
        }
    }

}
