package loan.system.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import loan.system.entities.FinancailInfo;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-11T12:35:35")
@StaticMetamodel(Loaner.class)
public class Loaner_ { 

    public static volatile SingularAttribute<Loaner, Float> amount;
    public static volatile SingularAttribute<Loaner, Float> installment;
    public static volatile SingularAttribute<Loaner, FinancailInfo> financailId;
    public static volatile SingularAttribute<Loaner, Boolean> paid;
    public static volatile SingularAttribute<Loaner, Integer> duration;
    public static volatile SingularAttribute<Loaner, Float> remaining;
    public static volatile SingularAttribute<Loaner, byte[]> paidPaper;
    public static volatile SingularAttribute<Loaner, String> loanerName;
    public static volatile SingularAttribute<Loaner, Boolean> isEditable;
    public static volatile SingularAttribute<Loaner, Integer> loanerId;

}