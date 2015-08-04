package loan.system.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import loan.system.entities.Departement;
import loan.system.entities.Request;
import loan.system.entities.Role;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-11T12:35:35")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, Boolean> userForgot;
    public static volatile SingularAttribute<User, Role> userRoleId;
    public static volatile SingularAttribute<User, Date> lastLogin;
    public static volatile SingularAttribute<User, Departement> userDeptId;
    public static volatile SingularAttribute<User, Date> lastPasswordUpdate;
    public static volatile SingularAttribute<User, Integer> userId;
    public static volatile SingularAttribute<User, String> userPassword;
    public static volatile SingularAttribute<User, String> userName;
    public static volatile SingularAttribute<User, Boolean> cannotRemove;
    public static volatile CollectionAttribute<User, Request> requestCollection;

}