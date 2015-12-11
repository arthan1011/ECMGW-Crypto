package ru.atc.sb.ecmgw.crypto.server.service.utils;


import com.filenet.api.collection.ObjectStoreSet;
import com.filenet.api.core.Connection;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.util.UserContext;

import javax.security.auth.Subject;
import java.util.Iterator;


public class CeHelper {

  public static Connection createConnection(String uri, String login, String password) {
    Connection conn = Factory.Connection.getConnection(uri);
    Subject subject = UserContext.createSubject(conn, login, password, null);
    UserContext uc = UserContext.get();
    uc.pushSubject(subject);
    return conn;
  }

  public static Domain getDomain(Connection conn, String domainName) {
    return Factory.Domain.fetchInstance(conn, domainName, null);
  }

  public static ObjectStore getObjectStore(Domain domain, String osName) {
    ObjectStoreSet osset = domain.get_ObjectStores();
    Iterator<ObjectStore> osIterator = osset.iterator();
    while (osIterator.hasNext()) {
      ObjectStore curOs = osIterator.next();
      if (curOs.get_Name().equalsIgnoreCase(osName)) {
        return curOs;
      }
    }
    throw new RuntimeException("Object store " + osName + " not found!");
  }


}

