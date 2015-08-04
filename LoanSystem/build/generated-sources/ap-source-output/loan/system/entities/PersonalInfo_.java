package loan.system.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import loan.system.entities.Client;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-11T12:35:35")
@StaticMetamodel(PersonalInfo.class)
public class PersonalInfo_ { 

    public static volatile SingularAttribute<PersonalInfo, Integer> postalcode;
    public static volatile SingularAttribute<PersonalInfo, Date> birthday;
    public static volatile SingularAttribute<PersonalInfo, Integer> resedentRentAmount;
    public static volatile SingularAttribute<PersonalInfo, Integer> tel;
    public static volatile SingularAttribute<PersonalInfo, Integer> dependent;
    public static volatile SingularAttribute<PersonalInfo, String> idIssuer;
    public static volatile SingularAttribute<PersonalInfo, String> clientNotes;
    public static volatile SingularAttribute<PersonalInfo, String> resedentType;
    public static volatile SingularAttribute<PersonalInfo, String> city;
    public static volatile SingularAttribute<PersonalInfo, String> otherJob;
    public static volatile SingularAttribute<PersonalInfo, String> name;
    public static volatile SingularAttribute<PersonalInfo, Integer> resedentNumber;
    public static volatile SingularAttribute<PersonalInfo, String> gender;
    public static volatile SingularAttribute<PersonalInfo, Integer> crId;
    public static volatile SingularAttribute<PersonalInfo, String> crIssuer;
    public static volatile SingularAttribute<PersonalInfo, Integer> personalInfoId;
    public static volatile SingularAttribute<PersonalInfo, String> resedentStreet;
    public static volatile SingularAttribute<PersonalInfo, String> resedentDistrict;
    public static volatile SingularAttribute<PersonalInfo, String> otherStatus;
    public static volatile SingularAttribute<PersonalInfo, String> status;
    public static volatile SingularAttribute<PersonalInfo, String> resedentCity;
    public static volatile SingularAttribute<PersonalInfo, String> job;
    public static volatile SingularAttribute<PersonalInfo, Date> crIssueDate;
    public static volatile SingularAttribute<PersonalInfo, Date> idIssueDate;
    public static volatile SingularAttribute<PersonalInfo, Client> clientId;
    public static volatile SingularAttribute<PersonalInfo, String> wassel;
    public static volatile SingularAttribute<PersonalInfo, Integer> govId;
    public static volatile SingularAttribute<PersonalInfo, Integer> pobox;
    public static volatile SingularAttribute<PersonalInfo, Integer> mobile;
    public static volatile SingularAttribute<PersonalInfo, Date> idIssueExpireDate;

}