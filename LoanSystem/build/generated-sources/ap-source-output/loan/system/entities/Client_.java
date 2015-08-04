package loan.system.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import loan.system.entities.EmploymentInfo;
import loan.system.entities.FinancailInfo;
import loan.system.entities.PersonalInfo;
import loan.system.entities.Request;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-11T12:35:35")
@StaticMetamodel(Client.class)
public class Client_ { 

    public static volatile CollectionAttribute<Client, EmploymentInfo> employmentInfoCollection;
    public static volatile CollectionAttribute<Client, PersonalInfo> personalInfoCollection;
    public static volatile CollectionAttribute<Client, FinancailInfo> financailInfoCollection;
    public static volatile SingularAttribute<Client, Integer> clientNumber;
    public static volatile CollectionAttribute<Client, Request> requestCollection;
    public static volatile SingularAttribute<Client, Integer> clientId;

}