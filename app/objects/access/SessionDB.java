package objects.access;

import com.mongodb.BasicDBObject;
import exception.JCertifException;
import objects.Session;
import objects.checker.SessionChecker;
import util.Constantes;

import java.util.*;

public class SessionDB extends JCertifObjectDB {

    public static SessionDB sessionDB = new SessionDB();

    public SessionDB() {
        super(Constantes.JCERTIFBACKEND_COLLECTIONNAME_SESSION, new SessionChecker());
    }

    public boolean add(Session session) throws JCertifException {
        return add(session.toBasicDBObject());
    }

    public boolean remove(Session session) throws JCertifException {
        return remove(session.toBasicDBObject(), "id");
    }

    public boolean update(Session session) throws JCertifException {
        return update(session.toBasicDBObject(), "id");
    }

    public Session get(String id) throws JCertifException {
        BasicDBObject dbObject = get("id", id);
        Session session = null;
        if (null != dbObject) session = new Session(dbObject);
        return session;
    }

    /**
     * Liste de status sans doublons
     * @return
     */
    public List<BasicDBObject> listStatus(){
        BasicDBObject columnToReturn = new BasicDBObject("status",1).append("_id",0);
        List<BasicDBObject>  tmpList = list(null, columnToReturn);
        List<BasicDBObject>  retList = new ArrayList<BasicDBObject>();
        Map<String, BasicDBObject> tmpMap = new LinkedHashMap<String, BasicDBObject>();
        Iterator iterator = tmpList.iterator();
        BasicDBObject dbObject;
        for(;iterator.hasNext();){
           dbObject = (BasicDBObject)iterator.next();
           if(tmpMap.get(dbObject.getString("status").trim().toLowerCase())==null){
                tmpMap.put(dbObject.getString("status").trim().toLowerCase(), dbObject);
               retList.add(dbObject);
           }
        }
        return retList;
    }
}
