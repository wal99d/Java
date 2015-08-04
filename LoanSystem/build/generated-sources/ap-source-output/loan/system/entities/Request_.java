package loan.system.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import loan.system.entities.Client;
import loan.system.entities.Status;
import loan.system.entities.User;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-11T12:35:35")
@StaticMetamodel(Request.class)
public class Request_ { 

    public static volatile SingularAttribute<Request, Status> reqStatusId;
    public static volatile SingularAttribute<Request, Integer> reqId;
    public static volatile SingularAttribute<Request, byte[]> reqApprovedDoc;
    public static volatile SingularAttribute<Request, Date> rEQDate;
    public static volatile SingularAttribute<Request, Client> reqClientId;
    public static volatile SingularAttribute<Request, User> reqUserId;
    public static volatile SingularAttribute<Request, Integer> reqNumber;
    public static volatile SingularAttribute<Request, Integer> reqClientGovId;
    public static volatile SingularAttribute<Request, Boolean> reqToBeCollected;
    public static volatile SingularAttribute<Request, byte[]> reqColPapers;
    public static volatile SingularAttribute<Request, byte[]> reqPaidByClient;
    public static volatile SingularAttribute<Request, String> historyNotes;
    public static volatile SingularAttribute<Request, byte[]> reqDoc;
    public static volatile SingularAttribute<Request, Boolean> reqToBeScanned;

}