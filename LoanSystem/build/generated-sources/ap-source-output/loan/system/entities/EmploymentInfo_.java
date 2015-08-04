package loan.system.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import loan.system.entities.Client;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-11T12:35:35")
@StaticMetamodel(EmploymentInfo.class)
public class EmploymentInfo_ { 

    public static volatile SingularAttribute<EmploymentInfo, String> toRetire;
    public static volatile SingularAttribute<EmploymentInfo, Integer> employerTel;
    public static volatile SingularAttribute<EmploymentInfo, Integer> employmentId;
    public static volatile SingularAttribute<EmploymentInfo, Date> employmentDate;
    public static volatile SingularAttribute<EmploymentInfo, Integer> employerTelExt;
    public static volatile SingularAttribute<EmploymentInfo, String> employer;
    public static volatile SingularAttribute<EmploymentInfo, Integer> contactTel;
    public static volatile SingularAttribute<EmploymentInfo, Client> clientId;
    public static volatile SingularAttribute<EmploymentInfo, String> departement;
    public static volatile SingularAttribute<EmploymentInfo, String> payDay;
    public static volatile SingularAttribute<EmploymentInfo, String> rank;
    public static volatile SingularAttribute<EmploymentInfo, String> employerReportTo;
    public static volatile SingularAttribute<EmploymentInfo, Integer> extraIncom;
    public static volatile SingularAttribute<EmploymentInfo, String> contactName;
    public static volatile SingularAttribute<EmploymentInfo, Integer> salary;
    public static volatile SingularAttribute<EmploymentInfo, Integer> contactMobile;
    public static volatile SingularAttribute<EmploymentInfo, String> employerJobTitle;
    public static volatile SingularAttribute<EmploymentInfo, Integer> militaryNumber;
    public static volatile SingularAttribute<EmploymentInfo, Integer> employmentNumber;
    public static volatile SingularAttribute<EmploymentInfo, String> jobTitle;

}