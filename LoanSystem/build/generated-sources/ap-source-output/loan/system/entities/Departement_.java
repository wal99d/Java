package loan.system.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import loan.system.entities.User;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-11T12:35:35")
@StaticMetamodel(Departement.class)
public class Departement_ { 

    public static volatile SingularAttribute<Departement, String> deptName;
    public static volatile SingularAttribute<Departement, String> deptScanner;
    public static volatile SingularAttribute<Departement, Integer> deptId;
    public static volatile SingularAttribute<Departement, Boolean> isEditable;
    public static volatile SingularAttribute<Departement, String> deptScannerFileName;
    public static volatile CollectionAttribute<Departement, User> userCollection;

}