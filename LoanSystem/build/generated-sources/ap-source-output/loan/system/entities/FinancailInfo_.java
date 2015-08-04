package loan.system.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import loan.system.entities.Client;
import loan.system.entities.Loaner;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-11T12:35:35")
@StaticMetamodel(FinancailInfo.class)
public class FinancailInfo_ { 

    public static volatile SingularAttribute<FinancailInfo, Integer> financailId;
    public static volatile CollectionAttribute<FinancailInfo, Loaner> loanerCollection;
    public static volatile SingularAttribute<FinancailInfo, Boolean> liable;
    public static volatile SingularAttribute<FinancailInfo, Client> clientId;

}