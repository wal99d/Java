/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package loan.system.ejbs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUnit;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import loan.system.entities.Action;
import loan.system.entities.Client;
import loan.system.entities.Departement;
import loan.system.entities.EmploymentInfo;
import loan.system.entities.FinancailInfo;
import loan.system.entities.Loaner;
import loan.system.entities.PersonalInfo;
import loan.system.entities.Ranks;
import loan.system.entities.Request;
import loan.system.entities.Status;
import loan.system.entities.User;
import loan.system.general.PaginationHelper;
import org.apache.commons.io.IOUtils;
import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.Years;
import org.joda.time.chrono.IslamicChronology;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author me
 */
@SessionScoped
@Named("orderForm")
public class OrderFormBean implements Serializable {
    @PersistenceUnit(unitName = "LOAN_SYSTEMPU")
    EntityManagerFactory emf;
    EntityManager em;
    
    @Resource
    UserTransaction utx;
   
    //CLient info
    private int clientNumber;
    private int clinetId;
    
    //Request Info
    private int requestNumber;
    private byte[] doc;
    private byte[] approvedDoc;
    
    //Personal Info //
    private String clientName;
    private String gender;
    private String job;
    private int govId;
    private String idIssuer;
    private Date issueDate;
    private Date idExpireDate;
    private Date birthDay;
    private String status;
    private int dependents;
    private int crId;
    private String crIssuer;
    private Date crIssueDate;
    private int pobox;
    private String city;
    private int postalCode;
    private String wassel;
    private int tel;
    private int mobile;
    private String resedentType;
    private int resedentRentAmount;
    private String resedentFromCompany;
    private String resedentWithOther;
    private String resedentCity;
    private String resedentDistrict;
    private String resedentStreet;
    private int resedentNumber;
    private String clientNotes;
    
    //Employment Info //
    private String employer;
    private String jobTitle;
    private int employmentNumber;
    private String departement;
    private Date employmentDate;
    private String rank;
    private int militaryNumber;
    private int salary;
    private int extraIncom;
    private String payDay;
    private String toRetire;
    private String employerReportTo;
    private String employerJobTitle;
    private int employerTel;
    private int employerTelExt;
    private String contactName;
    private int contactTel;
    private int contactMobile;
    
    //Financail Info //
    private boolean liable;
    private String loanerName;
    private float amount;
    private int duration;
    private float remaining;
    private float installment;
    
    //Used by this been
    private String otherJob;
    private boolean isOtherJob;
    private boolean isOtherStatus;
    private boolean isRented;
    private String issueDateString;
    private String issueEndDateString;
    private String birthdayDateString;
    private String otherStatus;
    private String crIssueDateString;
    private String yearType;
    private String employmentDateString;
    private String historyNotes;
    private boolean isHijri;
    private boolean isClientValidated;
    private boolean isNewClient;
    private boolean isPersonalInfoValidated;
    private boolean isClient;
    private boolean isEmploymentInfoValidated;
    private boolean isLoanarTableEditable;
    private boolean deleteAll;
    private boolean isFinancailInfoValidated;
    private boolean hideLoanerInputs;
    private boolean isRequest;
    private Loaner current;
    private DataModel items=null;
    private int selectedItemIndex;
    private PaginationHelper pagination;
    private List<Ranks> ranks;
    private int rankID;
    private int valueOfPilotAge;
    private boolean isPilot;

    public boolean isIsPilot() {
        return isPilot;
    }

    public void setIsPilot(boolean isPilot) {
        this.isPilot = isPilot;
    }
    
    public int getValueOfPilotAge() {
        return valueOfPilotAge;
    }

    public void setValueOfPilotAge(int valueOfPilotAge) {
        this.valueOfPilotAge = valueOfPilotAge;
    }
    
    public int getRankID() {
        return rankID;
    }

    public void setRankID(int rankID) {
        this.rankID = rankID;
    }
    
    public List<Ranks> getRanks() {
        return ranks;
    }

    public void setRanks(List<Ranks> ranks) {
        this.ranks = ranks;
    }
    
    public String getClientNotes() {
        return clientNotes;
    }

    public void setClientNotes(String clientNotes) {
        this.clientNotes = clientNotes;
    }
    
    public boolean isIsRequest() {
        return isRequest;
    }

    public void setIsRequest(boolean isRequest) {
        this.isRequest = isRequest;
    }
    
    public String getHistoryNotes() {
        return historyNotes;
    }

    public void setHistoryNotes(String historyNotes) {
        this.historyNotes = historyNotes;
    }
    
    public boolean isHideLoanerInputs() {
        return hideLoanerInputs;
    }

    public void setHideLoanerInputs(boolean hideLoanerInputs) {
        this.hideLoanerInputs = hideLoanerInputs;
    }
    
    public boolean isIsFinancailInfoValidated() {
        return isFinancailInfoValidated;
    }

    public void setIsFinancailInfoValidated(boolean isFinancailInfoValidated) {
        this.isFinancailInfoValidated = isFinancailInfoValidated;
    }
    
    public boolean isDeleteAll() {
        return deleteAll;
    }

    public void setDeleteAll(boolean deleteAll) {
        this.deleteAll = deleteAll;
    }
    
    public boolean isIsLoanarTableEditable() {
        return isLoanarTableEditable;
    }

    public void setIsLoanarTableEditable(boolean isLoanarTableEditable) {
        this.isLoanarTableEditable = isLoanarTableEditable;
    }
    
    public boolean isIsEmploymentInfoValidated() {
        return isEmploymentInfoValidated;
    }

    public void setIsEmploymentInfoValidated(boolean isEmploymentInfoValidated) {
        this.isEmploymentInfoValidated = isEmploymentInfoValidated;
    }
    
    public boolean isIsClient() {
        return isClient;
    }

    public void setIsClient(boolean isClient) {
        this.isClient = isClient;
    }
    
    public boolean isIsPersonalInfoValidated() {
        return isPersonalInfoValidated;
    }

    public void setIsPersonalInfoValidated(boolean isPersonalInfoValidated) {
        this.isPersonalInfoValidated = isPersonalInfoValidated;
    }
    
    public int getClinetId() {
        return clinetId;
    }

    public void setClinetId(int clinetId) {
        this.clinetId = clinetId;
    }
    
    public boolean isIsNewClient() {
        return isNewClient;
    }

    public void setIsNewClient(boolean isNewClient) {
        this.isNewClient = isNewClient;
    }
    
    public boolean isIsClientValidated() {
        return isClientValidated;
    }

    public void setIsClientValidated(boolean isClientValidated) {
        this.isClientValidated = isClientValidated;
    }
    
    public int getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(int clientNumber) {
        this.clientNumber = clientNumber;
    }
    
    public String getEmploymentDateString() {
        return employmentDateString;
    }
    
    public int getRequestNumber() {
        return requestNumber;
    }

    public void setRequestNumber(int requestNumber) {
        this.requestNumber = requestNumber;
    }

    public byte[] getDoc() {
        return doc;
    }

    public void setDoc(byte[] doc) {
        this.doc = doc;
    }

    public byte[] getApprovedDoc() {
        return approvedDoc;
    }

    public void setApprovedDoc(byte[] approvedDoc) {
        this.approvedDoc = approvedDoc;
    }

    public void setEmploymentDateString(String employmentDateString) {
        this.employmentDateString = employmentDateString;
    }
    
    public boolean isIsHijri() {
        return isHijri;
    }

    public void setIsHijri(boolean isHijri) {
        this.isHijri = isHijri;
    }
    
    public String getYearType() {
        return yearType;
    }

    public void setYearType(String yearType) {
        this.yearType = yearType;
    }
    
    public boolean isIsRented() {
        return isRented;
    }

    public void setIsRented(boolean isRented) {
        this.isRented = isRented;
    }
    
    public String getCrIssueDateString() {
        return crIssueDateString;
    }

    public void setCrIssueDateString(String crIssueDateString) {
        this.crIssueDateString = crIssueDateString;
    }
    
    public String getOtherStatus() {
        return otherStatus;
    }

    public void setOtherStatus(String otherStatus) {
        this.otherStatus = otherStatus;
    }
    
    public boolean isIsOtherStatus() {
        return isOtherStatus;
    }

    public void setIsOtherStatus(boolean isOtherStatus) {
        this.isOtherStatus = isOtherStatus;
    }
    
    public String getBirthdayDateString() {
        return birthdayDateString;
    }

    public void setBirthdayDateString(String birthdayDateString) {
        this.birthdayDateString = birthdayDateString;
    }
    
    public String getIssueEndDateString() {
        return issueEndDateString;
    }

    public void setIssueEndDateString(String issueEndDateString) {
        this.issueEndDateString = issueEndDateString;
    }
    
    public String getIssueDateString() {
        return issueDateString;
    }

    public void setIssueDateString(String issueDateString) {
        this.issueDateString = issueDateString;
    }
    
    public boolean isIsOtherJob() {
        return isOtherJob;
    }

    public void setIsOtherJob(boolean isOtherJob) {
        this.isOtherJob = isOtherJob;
    }
    
    public String getOtherJob() {
        return otherJob;
    }

    public void setOtherJob(String otherJob) {
        this.otherJob = otherJob;
    }
    
    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getGovId() {
        return govId;
    }

    public void setGovId(int govId) {
        this.govId = govId;
    }

    public String getIdIssuer() {
        return idIssuer;
    }

    public void setIdIssuer(String idIssuer) {
        this.idIssuer = idIssuer;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getIdExpireDate() {
        return idExpireDate;
    }

    public void setIdExpireDate(Date idExpireDate) {
        this.idExpireDate = idExpireDate;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getDependents() {
        return dependents;
    }

    public void setDependents(int dependents) {
        this.dependents = dependents;
    }

    public int getCrId() {
        return crId;
    }

    public void setCrId(int crId) {
        this.crId = crId;
    }

    public String getCrIssuer() {
        return crIssuer;
    }

    public void setCrIssuer(String crIssuer) {
        this.crIssuer = crIssuer;
    }

    public Date getCrIssueDate() {
        return crIssueDate;
    }

    public void setCrIssueDate(Date crIssueDate) {
        this.crIssueDate = crIssueDate;
    }

    public int getPobox() {
        return pobox;
    }

    public void setPobox(int pobox) {
        this.pobox = pobox;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getWassel() {
        return wassel;
    }

    public void setWassel(String wassel) {
        this.wassel = wassel;
    }

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    public int getMobile() {
        return mobile;
    }

    public void setMobile(int mobile) {
        this.mobile = mobile;
    }

    public String getResedentType() {
        return resedentType;
    }

    public void setResedentType(String resedentType) {
        this.resedentType = resedentType;
    }

    public int getResedentRentAmount() {
        return resedentRentAmount;
    }

    public void setResedentRentAmount(int resedentRentAmount) {
        this.resedentRentAmount = resedentRentAmount;
    }

    public String getResedentFromCompany() {
        return resedentFromCompany;
    }

    public void setResedentFromCompany(String resedentFromCompany) {
        this.resedentFromCompany = resedentFromCompany;
    }

    public String getResedentWithOther() {
        return resedentWithOther;
    }

    public void setResedentWithOther(String resedentWithOther) {
        this.resedentWithOther = resedentWithOther;
    }

    public String getResedentCity() {
        return resedentCity;
    }

    public void setResedentCity(String resedentCity) {
        this.resedentCity = resedentCity;
    }

    public String getResedentDistrict() {
        return resedentDistrict;
    }

    public void setResedentDistrict(String resedentDistrict) {
        this.resedentDistrict = resedentDistrict;
    }

    public String getResedentStreet() {
        return resedentStreet;
    }

    public void setResedentStreet(String resedentStreet) {
        this.resedentStreet = resedentStreet;
    }

    public int getResedentNumber() {
        return resedentNumber;
    }

    public void setResedentNumber(int resedentNumber) {
        this.resedentNumber = resedentNumber;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public int getEmploymentNumber() {
        return employmentNumber;
    }

    public void setEmploymentNumber(int employmentNumber) {
        this.employmentNumber = employmentNumber;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public Date getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(Date employmentDate) {
        this.employmentDate = employmentDate;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public int getMilitaryNumber() {
        return militaryNumber;
    }

    public void setMilitaryNumber(int militaryNumber) {
        this.militaryNumber = militaryNumber;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getExtraIncom() {
        return extraIncom;
    }

    public void setExtraIncom(int extraIncom) {
        this.extraIncom = extraIncom;
    }

    public String getPayDay() {
        return payDay;
    }

    public void setPayDay(String payDay) {
        this.payDay = payDay;
    }
    
    public String getToRetire() {
        return toRetire;
    }

    public void setToRetire(String toRetire) {
        this.toRetire = toRetire;
    }

    public String getEmployerReportTo() {
        return employerReportTo;
    }

    public void setEmployerReportTo(String employerReportTo) {
        this.employerReportTo = employerReportTo;
    }

    public String getEmployerJobTitle() {
        return employerJobTitle;
    }

    public void setEmployerJobTitle(String employerJobTitle) {
        this.employerJobTitle = employerJobTitle;
    }

    public int getEmployerTel() {
        return employerTel;
    }

    public void setEmployerTel(int employerTel) {
        this.employerTel = employerTel;
    }

    public int getEmployerTelExt() {
        return employerTelExt;
    }

    public void setEmployerTelExt(int employerTelExt) {
        this.employerTelExt = employerTelExt;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public int getContactTel() {
        return contactTel;
    }

    public void setContactTel(int contactTel) {
        this.contactTel = contactTel;
    }

    public int getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(int contactMobile) {
        this.contactMobile = contactMobile;
    }

    public boolean isLiable() {
        return liable;
    }

    public void setLiable(boolean liable) {
        this.liable = liable;
    }

    public String getLoanerName() {
        return loanerName;
    }

    public void setLoanerName(String loanerName) {
        this.loanerName = loanerName;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public float getRemaining() {
        return remaining;
    }

    public void setRemaining(float remaining) {
        this.remaining = remaining;
    }

    public float getInstallment() {
        return installment;
    }

    public void setInstallment(float installment) {
        this.installment = installment;
    }
    
    //Client Number Validator
    public void validateClientNumber(FacesContext context, UIComponent toValidate, Object value){
        try{
            int clientNumberValue = (int) value;
            //int length = String.valueOf(clientNumberValue).length();
            if((clientNumberValue == 0)){
               ((UIInput) toValidate).setValid(false);
               FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إدخال رقم العميل ", null);
                       //new FacesMessage( "حجم الملف أكبر من المسموح به وهو ٢م.ب.");
               context.addMessage(toValidate.getClientId(context), message); 
            } 
        } catch(Exception e){
            ((UIInput) toValidate).setValid(false);
               FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إدخال أرقام في خانة رقم العميل", null);
                       //new FacesMessage( "حجم الملف أكبر من المسموح به وهو ٢م.ب.");
               context.addMessage(toValidate.getClientId(context), message); 
        }
    }
    
    //Client Name validator
    public void validateClientName(FacesContext context, UIComponent toValidate, Object value){
       if(value.equals("")){
           ((UIInput) toValidate).setValid(false);
           FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إدخال إسم للعميل", null);
                   //new FacesMessage( "حجم الملف أكبر من المسموح به وهو ٢م.ب.");
           context.addMessage(toValidate.getClientId(context), message); 
       }
   }
    
    //Gender Validator
    public void validateGenderSelected(FacesContext context, UIComponent toValidate, Object value){
        String genderValue = (String) value;
        if(genderValue.equals("0")){
           ((UIInput) toValidate).setValid(false);
           FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إختيار جنس العميل", null);
                   //new FacesMessage( "حجم الملف أكبر من المسموح به وهو ٢م.ب.");
           context.addMessage(toValidate.getClientId(context), message); 
        }
    }
    public void genderSelected(ValueChangeEvent e){
       if(e.getNewValue().equals("1")){
           this.gender="ذكر";
       } else if(e.getNewValue().equals("2")){
           this.gender="أنثى";
       }
    }
    
    //Job Validator
    public void validateJobSelected(FacesContext context, UIComponent toValidate, Object value){
        String jobValue = (String) value;
        if(jobValue.equals("0")){
           ((UIInput) toValidate).setValid(false);
           FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إختيار طبيعه عمل العميل", null);
                   //new FacesMessage( "حجم الملف أكبر من المسموح به وهو ٢م.ب.");
           context.addMessage(toValidate.getClientId(context), message); 
        }
    }
    public void validateOtherJobSelected(FacesContext context, UIComponent toValidate, Object value){
        String otherJobValue = (String) value;
        if(otherJobValue.equals("")){
           ((UIInput) toValidate).setValid(false);
           FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إدخال طبيعه عمل العميل", null);
                   //new FacesMessage( "حجم الملف أكبر من المسموح به وهو ٢م.ب.");
           context.addMessage(toValidate.getClientId(context), message); 
        }
    }
    public void jobSelected(AjaxBehaviorEvent e){
       if(job.equals("1")){
           this.job="عسكري";
           isOtherJob = false;
       } else if(job.equals("2")){
           this.job="مدني";
           isOtherJob = false;
       } else if(job.equals("3")){
           this.job= "أخرى";
           isOtherJob = true;
           System.out.println(job +" "+ isOtherJob);
       }
    }
    
    //Governement ID Validator
    public void validateGovID(FacesContext context, UIComponent toValidate, Object value){
        try{
            int govIdValue = (int) value;
            int length = String.valueOf(value).length();
            if(govIdValue == 0){
               ((UIInput) toValidate).setValid(false);
               FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إدخال رقم هويه العميل ", null);
                       //new FacesMessage( "حجم الملف أكبر من المسموح به وهو ٢م.ب.");
               context.addMessage(toValidate.getClientId(context), message); 
            }else if(length < 10) {
               ((UIInput) toValidate).setValid(false);
               FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إدخال رقم هويه العميل بشكل صحيح ", null);
                       //new FacesMessage( "حجم الملف أكبر من المسموح به وهو ٢م.ب.");
               context.addMessage(toValidate.getClientId(context), message); 
            }else if(length > 10) {
               ((UIInput) toValidate).setValid(false);
               FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "رقم الهويه المدخل أكثر من عشرة أرقام الرجاء التأكد من ذلك ", null);
                       //new FacesMessage( "حجم الملف أكبر من المسموح به وهو ٢م.ب.");
               context.addMessage(toValidate.getClientId(context), message); 
            }
        } catch(Exception e){
            ((UIInput) toValidate).setValid(false);
               FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إدخال أرقام في خانة رقم الهويه", null);
                       //new FacesMessage( "حجم الملف أكبر من المسموح به وهو ٢م.ب.");
               context.addMessage(toValidate.getClientId(context), message); 
        }
    }
    
    //Governement ID Issuer Validator
    public void validateGovIDIssuer(FacesContext context, UIComponent toValidate, Object value){
       
            String govIdIssuerValue = (String) value;
            if(govIdIssuerValue.equals("")){
               ((UIInput) toValidate).setValid(false);
               FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إدخال جهه مصدر الهويه ", null);
                       //new FacesMessage( "حجم الملف أكبر من المسموح به وهو ٢م.ب.");
               context.addMessage(toValidate.getClientId(context), message); 
            }
    }
    
    //Governement ID Issuer Date Validator
    public void validateGovIDIssueDate(FacesContext context, UIComponent toValidate, Object value){
            Pattern pattern = Pattern.compile("\\d{4}(/)\\d{2}(/)\\d{2}");
            String govIdIssuerDateValue = (String) value;
            Matcher matcher = pattern.matcher(govIdIssuerDateValue.toString());
            
            if(!(value.equals(""))){
                if(!matcher.matches()){
                   ((UIInput) toValidate).setValid(false);
                   FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إدخال تاريخ الهويه بشكل صحيح ", null);
                           //new FacesMessage( "حجم الملف أكبر من المسموح به وهو ٢م.ب.");
                   context.addMessage(toValidate.getClientId(context), message); 
                }
            }
            else if(value.equals("")){
               ((UIInput) toValidate).setValid(false);
               FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إدخال تاريخ الهويه ", null);
                       //new FacesMessage( "حجم الملف أكبر من المسموح به وهو ٢م.ب.");
               context.addMessage(toValidate.getClientId(context), message);  
            }
    }
    
    //Governement ID Issuer End Date Validator
    public void validateGovIDIssueEndDate(FacesContext context, UIComponent toValidate, Object value){
            Pattern pattern = Pattern.compile("\\d{4}(/)\\d{2}(/)\\d{2}");
            String govIdIssueEndDateValue = (String) value;
            Matcher matcher = pattern.matcher(govIdIssueEndDateValue.toString());
            
            if(!(value.equals(""))){
                if(!matcher.matches()){
                   ((UIInput) toValidate).setValid(false);
                   FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إدخال تاريخ إصدار الهويه بشكل صحيح ", null);
                           //new FacesMessage( "حجم الملف أكبر من المسموح به وهو ٢م.ب.");
                   context.addMessage(toValidate.getClientId(context), message); 
                }
            }
            else if(value.equals("")){
               ((UIInput) toValidate).setValid(false);
               FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إدخال تاريخ إصدار الهويه ", null);
                       //new FacesMessage( "حجم الملف أكبر من المسموح به وهو ٢م.ب.");
               context.addMessage(toValidate.getClientId(context), message);  
            }
    }
    
    //Birthday Date Validator
    public void validateBirthdayDate(FacesContext context, UIComponent toValidate, Object value){
            Pattern pattern = Pattern.compile("\\d{4}(/)\\d{2}(/)\\d{2}");
            String birthdayDateValue = (String) value;
            Matcher matcher = pattern.matcher(birthdayDateValue.toString());
            
            if(!(value.equals(""))){
                if(!matcher.matches()){
                   ((UIInput) toValidate).setValid(false);
                   FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إدخال تاريخ الميلاد بشكل صحيح ", null);
                           //new FacesMessage( "حجم الملف أكبر من المسموح به وهو ٢م.ب.");
                   context.addMessage(toValidate.getClientId(context), message); 
                }
            }
            else if(value.equals("")){
               ((UIInput) toValidate).setValid(false);
               FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إدخال تاريخ الميلاد ", null);
                       //new FacesMessage( "حجم الملف أكبر من المسموح به وهو ٢م.ب.");
               context.addMessage(toValidate.getClientId(context), message);  
            }
    }
    
    //Status Validator
    public void validateStatusSelected(FacesContext context, UIComponent toValidate, Object value){
        String jobValue = (String) value;
        if(jobValue.equals("0")){
           ((UIInput) toValidate).setValid(false);
           FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إختيار الحاله الإجتماعيه للعميل", null);
                   //new FacesMessage( "حجم الملف أكبر من المسموح به وهو ٢م.ب.");
           context.addMessage(toValidate.getClientId(context), message); 
        }
    }
    public void validateOtherStatusSelected(FacesContext context, UIComponent toValidate, Object value){
        String otherJobValue = (String) value;
        if(otherJobValue.equals("")){
           ((UIInput) toValidate).setValid(false);
           FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إدخال الحاله الإجتماعيه للعميل", null);
                   //new FacesMessage( "حجم الملف أكبر من المسموح به وهو ٢م.ب.");
           context.addMessage(toValidate.getClientId(context), message); 
        }
    }
    public void statusSelected(ValueChangeEvent e){
       if(e.getNewValue().equals("1")){
           this.status="متزوج";
           isOtherStatus = false;
       } else if(e.getNewValue().equals("2")){
           this.status="أعزب";
           isOtherStatus = false;
       } else if(e.getNewValue().equals("3")){
           this.status= "أخرى";
           isOtherStatus = true;
       }
    }
    
    //Dependtns Validator
    public void validateDependents(FacesContext context, UIComponent toValidate, Object value){
        try{
            int dependentValue = (int) value;
            if(dependentValue == 0){
               ((UIInput) toValidate).setValid(false);
               FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إدخال عدد أفراد الأسرة ", null);
                       //new FacesMessage( "حجم الملف أكبر من المسموح به وهو ٢م.ب.");
               context.addMessage(toValidate.getClientId(context), message); 
            }
        } catch(Exception e){
            ((UIInput) toValidate).setValid(false);
               FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إدخال أرقام في خانة عدد أفراد الأسره", null);
                       //new FacesMessage( "حجم الملف أكبر من المسموح به وهو ٢م.ب.");
               context.addMessage(toValidate.getClientId(context), message); 
        }
    }
    
    //Mobile validatition
    public void validateMobile(FacesContext context, UIComponent toValidate, Object value){
        int mobileValue = (int) value;
        int mobileDigits = String.valueOf(value).length();
        if(mobileValue == 0){
               ((UIInput) toValidate).setValid(false);
               FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إدخال رقم الجوال للعميل ", null);
                       //new FacesMessage( "حجم الملف أكبر من المسموح به وهو ٢م.ب.");
               context.addMessage(toValidate.getClientId(context), message); 
        } else if(mobileDigits < 9 || mobileDigits > 9 ){
            ((UIInput) toValidate).setValid(false);
               FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إدخال رقم جوال العميل بشكل صحيح ", null);
                       //new FacesMessage( "حجم الملف أكبر من المسموح به وهو ٢م.ب.");
               context.addMessage(toValidate.getClientId(context), message); 
        }
    }
    
    //Resident Type Validator
    public void validateResidentSelected(FacesContext context, UIComponent toValidate, Object value){
        String residentValue = (String) value;
        if(residentValue.equals("0")){
           ((UIInput) toValidate).setValid(false);
           FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إختيار نوع السكن للعميل", null);
                   //new FacesMessage( "حجم الملف أكبر من المسموح به وهو ٢م.ب.");
           context.addMessage(toValidate.getClientId(context), message); 
        }
    }
    public void residentSelected(AjaxBehaviorEvent e){
       if(resedentType.equals("1")){
           resedentType="أملك منزلا أو شقه";
           isRented = false;
       } else if(resedentType.equals("2")){
           resedentType="مستأجر شقه أو فيلا";
           isRented = true;
       } else if(resedentType.equals("3")){
           resedentType= "متوفر من جهه العمل";
           isRented = false;
       } else if(resedentType.equals("4")){
           resedentType= "أسكن مع أخرين";
           isRented = false;
       }
       
    }
    
    //Employer Name Validation
    public void validateEmployerName(FacesContext context, UIComponent toValidate, Object value){
        String employerNameValue = (String) value;
        if(employerNameValue.equals("")){
           ((UIInput) toValidate).setValid(false);
           FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إدخال جهه العمل للعميل", null);
                   //new FacesMessage( "حجم الملف أكبر من المسموح به وهو ٢م.ب.");
           context.addMessage(toValidate.getClientId(context), message); 
        }
    }
    
    //Employer Name Validation
    public void validateJobTitle(FacesContext context, UIComponent toValidate, Object value){
        String jobTitleValue = (String) value;
        if(jobTitleValue.equals("")){
           ((UIInput) toValidate).setValid(false);
           FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إدخال المسمى الوظيفي للعميل", null);
                   //new FacesMessage( "حجم الملف أكبر من المسموح به وهو ٢م.ب.");
           context.addMessage(toValidate.getClientId(context), message); 
        }
    }
    
    //Employment date Valitator
    public void validateEmploymentDate(FacesContext context, UIComponent toValidate, Object value){
            Pattern pattern = Pattern.compile("\\d{4}(/)\\d{2}(/)\\d{2}");
            String employmentDateValue = (String) value;
            Matcher matcher = pattern.matcher(employmentDateValue.toString());
            
            if(!(value.equals(""))){
                if(!matcher.matches()){
                   ((UIInput) toValidate).setValid(false);
                   FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إدخال تاريخ التوظيف بشكل صحيح ", null);
                           //new FacesMessage( "حجم الملف أكبر من المسموح به وهو ٢م.ب.");
                   context.addMessage(toValidate.getClientId(context), message); 
                }
            }
            else if(value.equals("")){
               ((UIInput) toValidate).setValid(false);
               FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إدخال تاريخ التوظيف ", null);
                       //new FacesMessage( "حجم الملف أكبر من المسموح به وهو ٢م.ب.");
               context.addMessage(toValidate.getClientId(context), message);  
            }
    }
    
    //Rank Selected
    public void rankSelected(AjaxBehaviorEvent e){
       
        Chronology hijri = IslamicChronology.getInstance(DateTimeZone.forID("Asia/Riyadh"));
        LocalDate todayHijri = LocalDate.now(hijri);
        int currentAgeInYears, ageAtEmploymentDateInYears, serviceTimeInYear , fullServiceTimeInYear , retirementInYear = 0;
        Ranks tmp = emf.createEntityManager().find(Ranks.class, rankID);

        
        valueOfPilotAge = tmp.getRankRetirmentAgePilot();
        
            DateTime dt1 = new DateTime(birthDay);
            DateTime dt2 = new DateTime(todayHijri.toDate());
            //DateTime dt3 = new DateTime(fortmatToHijri(employmentDateString));
            currentAgeInYears = Years.yearsBetween(dt1, dt2).getYears();
            System.out.println("Current Ages in Years: "+ currentAgeInYears);
            //ageAtEmploymentDateInYears = Years.yearsBetween(dt1, dt3).getYears();
            //serviceTimeInYear = currentAgeInYears - ageAtEmploymentDateInYears ;
            UIComponent toValidate = e.getComponent();
           
            
            if(isPilot){
                //fullServiceTimeInYear  = tmp.getRankRetirmentAgePilot() - ageAtEmploymentDateInYears;
                fullServiceTimeInYear  = tmp.getRankRetirmentAgePilot() - currentAgeInYears;
                this.isPilot = false;
                
                retirementInYear = fullServiceTimeInYear ;
                if(retirementInYear < 5){
                    retirementInYear =0;
                    System.out.println("المدة المتبقيه لتقاعد العميل أقل من المدة المسموحه");
                    ((UIInput) toValidate).setValid(false);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "المدة المتبقيه لتقاعد العميل أقل من المدة المسموحه", null));
                    FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("scanMessages");
                }
            } else {
                //fullServiceTimeInYear = tmp.getRankRetirmentAge() - ageAtEmploymentDateInYears;
                fullServiceTimeInYear = tmp.getRankRetirmentAge() - currentAgeInYears;
                retirementInYear = fullServiceTimeInYear ;
                if(retirementInYear < 5){
                    retirementInYear = 0;
                    System.out.println("المدة المتبقيه لتقاعد العميل أقل من المدة المسموحه");
                    ((UIInput) toValidate).setValid(false);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "المدة المتبقيه لتقاعد العميل أقل من المدة المسموحه", null));
                    FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("scanMessages");
                }
            }
            
            
            
        
        this.rank = tmp.getRankTitle();
        this.toRetire = "" + retirementInYear;
        System.out.println("Rank Selected: " + tmp.getRankTitle());
        
    }
    
    
    // Salary Validator
    public void validateSalary(FacesContext context, UIComponent toValidate, Object value){
        int salaryValue = (int) value;
        int salaryDigits = String.valueOf(value).length();
        if(salaryDigits == 0){
               ((UIInput) toValidate).setValid(false);
               FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إدخال الراتب الشهري ", null);
                       //new FacesMessage( "حجم الملف أكبر من المسموح به وهو ٢م.ب.");
               context.addMessage(toValidate.getClientId(context), message); 
        }
    }
    
    //Salary year selection
    public void yearSelected(ValueChangeEvent e){
       if(e.getNewValue().equals("1")){
            isHijri = true;
       } else if(e.getNewValue().equals("2")){
            isHijri = false;
       }
    }
    
    //Contact Name Validation
    public void validateContactName(FacesContext context, UIComponent toValidate, Object value){
        String contactValue = (String) value;
        if(contactValue.equals("")){
           ((UIInput) toValidate).setValid(false);
           FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إدخال إسم الشخص الذي يمكن الإتصال به", null);
                   //new FacesMessage( "حجم الملف أكبر من المسموح به وهو ٢م.ب.");
           context.addMessage(toValidate.getClientId(context), message); 
        }
    }
    //Contact Mobile validatition
    public void validateContactMobile(FacesContext context, UIComponent toValidate, Object value){
        int mobileValue = (int) value;
        int mobileDigits = String.valueOf(value).length();
        if(mobileValue == 0){
               ((UIInput) toValidate).setValid(false);
               FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إدخال رقم الجوال للشخص الذي يمكن الإتصال به ", null);
                       //new FacesMessage( "حجم الملف أكبر من المسموح به وهو ٢م.ب.");
               context.addMessage(toValidate.getClientId(context), message); 
        } else if(mobileDigits < 9 || mobileDigits > 9 ){
            ((UIInput) toValidate).setValid(false);
               FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إدخال رقم جوال الشخص بشكل صحيح ", null);
                       //new FacesMessage( "حجم الملف أكبر من المسموح به وهو ٢م.ب.");
               context.addMessage(toValidate.getClientId(context), message); 
        }
    }
    
    //Loaner Name Validator 
    public void validateLoanerName(FacesContext context , UIComponent toValidate , Object value) {
        String lonarNameValue = (String) value;
        if(lonarNameValue.equals("")){
           ((UIInput) toValidate).setValid(false);
           FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إدخال إسم البنك أو شركة التقسيط", null);
                   //new FacesMessage( "حجم الملف أكبر من المسموح به وهو ٢م.ب.");
           context.addMessage(toValidate.getClientId(context), message); 
        }
    }
    
    //Loaned Amount Validator
    public void validateLoanedAmount(FacesContext context , UIComponent toValidate , Object value) {
      try{
            //int salaryValue = (int) value;
            int amountDigits = String.valueOf(value).length();
            if(amountDigits == 0){
                   ((UIInput) toValidate).setValid(false);
                   FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إدخال مبلغ المديونيه ", null);
                           //new FacesMessage( "حجم الملف أكبر من المسموح به وهو ٢م.ب.");
                   context.addMessage(toValidate.getClientId(context), message); 
            }
        } catch(Exception e){
            ((UIInput) toValidate).setValid(false);
               FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إدخال أرقام في خانة مبلغ المديونيه", null);
                       //new FacesMessage( "حجم الملف أكبر من المسموح به وهو ٢م.ب.");
               context.addMessage(toValidate.getClientId(context), message); 
        }
    }
    
    //Duration Validator
    public void validateDuration(FacesContext context , UIComponent toValidate , Object value) {
     try{
            int amountDigits = String.valueOf(value).length();
            if(amountDigits == 0){
                   ((UIInput) toValidate).setValid(false);
                   FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إدخال مدة المديونية ", null);
                           //new FacesMessage( "حجم الملف أكبر من المسموح به وهو ٢م.ب.");
                   context.addMessage(toValidate.getClientId(context), message); 
            }
        } catch(Exception e){
            ((UIInput) toValidate).setValid(false);
               FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إدخال أرقام في خانة مده المديونيه", null);
                       //new FacesMessage( "حجم الملف أكبر من المسموح به وهو ٢م.ب.");
               context.addMessage(toValidate.getClientId(context), message); 
        }
    }
    
    //Remaining Validator
    public void validateRemaining (FacesContext context , UIComponent toValidate , Object value) {
       try{ 
            int remainingDigits = String.valueOf(value).length();
            if(remainingDigits == 0){
                   ((UIInput) toValidate).setValid(false);
                   FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إدخال المبلغ المتبقي ", null);
                           //new FacesMessage( "حجم الملف أكبر من المسموح به وهو ٢م.ب.");
                   context.addMessage(toValidate.getClientId(context), message); 
            }
       } catch(Exception e){
            ((UIInput) toValidate).setValid(false);
               FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إدخال أرقام في خانة المبلغ المتبقي", null);
                       //new FacesMessage( "حجم الملف أكبر من المسموح به وهو ٢م.ب.");
               context.addMessage(toValidate.getClientId(context), message); 
        }
    }
    
    //Installement Validator
    public void validateInstallement (FacesContext context , UIComponent toValidate , Object value) {
        try{
            int installementDigits = String.valueOf(value).length();
            if(installementDigits == 0){
                   ((UIInput) toValidate).setValid(false);
                   FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إدخال مبلغ القسط ", null);
                           //new FacesMessage( "حجم الملف أكبر من المسموح به وهو ٢م.ب.");
                   context.addMessage(toValidate.getClientId(context), message); 
            }
        } catch(Exception e){
            ((UIInput) toValidate).setValid(false);
               FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إدخال أرقام في خانة القسط", null);
                       //new FacesMessage( "حجم الملف أكبر من المسموح به وهو ٢م.ب.");
               context.addMessage(toValidate.getClientId(context), message); 
        }
    }
    
    //Request Number Validator
    public void validateRequestNumber(FacesContext context, UIComponent toValidate, Object value){
        try{
            int requestValue = (int) value;
            if(requestValue == 0){
               ((UIInput) toValidate).setValid(false);
               FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إدخال رقم الطلب ", null);
                       //new FacesMessage( "حجم الملف أكبر من المسموح به وهو ٢م.ب.");
               context.addMessage(toValidate.getClientId(context), message); 
            }
        } catch(Exception e){
            ((UIInput) toValidate).setValid(false);
               FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إدخال أرقام في خانة رقم الطلب", null);
                       //new FacesMessage( "حجم الملف أكبر من المسموح به وهو ٢م.ب.");
               context.addMessage(toValidate.getClientId(context), message); 
        }
    }
    
    public OrderFormBean() {
    }
    
    public void getNewClientNumber(){
        try{
                Client tmp = emf.createEntityManager().createNamedQuery("client.getLast", Client.class)
                        .getResultList().get(0);
                this.clientNumber = tmp.getClientNumber()+1;
            }catch(NoResultException e){
                this.clientNumber = 1;
            }
    }
    
    @PostConstruct
    public void initAll() {
        isClientValidated = false;
        isClient = true;
        hideLoanerInputs = true;
        
        ranks = emf.createEntityManager().createNamedQuery("Ranks.findAll", Ranks.class).getResultList();
        getNewClientNumber();
    }
    
    //Date conversion to Hijri
    public Date fortmatToHijri(String newDateAsString){
        
            DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy/MM/dd");
            LocalDate ld = fmt.parseLocalDate(newDateAsString);
            
            System.out.println(fmt.print(ld));
            
            return ld.toDate();
        
        
    }
    
    //Date conversion to Hijri from AD
    public Date formatToHijriAD(Date newDate){
        Chronology hijri = IslamicChronology.getInstance(DateTimeZone.forID("Asia/Riyadh"));
        LocalDate ld = new LocalDate(newDate, hijri).plusDays(1);
        
        return ld.toDate();
    }
    
    //Hijri Date to String
    public String toHijriString(Date date){
        //Chronology hijri = IslamicChronology.getInstanceUTC();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy/MM/dd");
        LocalDate ld= LocalDate.fromDateFields(date);
        
        String tmp = fmt.print(ld);
        
        System.out.println("tmp: "+ tmp);
        return tmp;
    }
    public void resetFields(){
        //Personal Fields
        this.clientName = "";
        this.gender = "";
        this.job = "";
        this.otherJob = null;
        this.govId = 0;
        this.idIssuer = "";
        this.status = "";
        this.otherStatus = "";
        this.dependents = 0;
        this.crId = 0;
        this.crIssuer = "";
        this.pobox = 0;
        this.city = "";
        this.postalCode = 0;
        this.wassel = "";
        this.tel = 0;
        this.mobile = 0;
        this.resedentType = "";
        this.resedentRentAmount = 0;
        this.resedentCity = "";
        this.resedentDistrict = "";
        this.resedentStreet = "";
        this.resedentNumber = 0;
        this.issueDateString = "";
        this.issueEndDateString = "";
        this.birthdayDateString = "";
        this.clientNotes = "";
        this.crIssueDateString = "";
        
        //Employment Fields
        this.employer = "";
        this.jobTitle = "";
        this.employmentNumber = 0;
        this.departement = "";
        this.employmentDateString = null;
        this.rankID = 0;
        this.militaryNumber = 0;
        this.salary = 0;
        this.extraIncom = 0;
        this.payDay = "";
        this.toRetire = "";
        this.employerReportTo ="";
        this.employerJobTitle = "";
        this.employerTel = 0;
        this.employerTelExt =0;
        this.contactName = "";
        this.contactTel = 0;
        this.contactMobile = 0; 
        
        this.liable = false;
        
        this.requestNumber =0;
       
        this.loanerName = "";
        this.amount = 0;
        this.duration = 0;
        this.installment = 0;
        this.remaining = 0;
        recreatePagination();
        recreateModel();
    }
    //Get Client ID if he/she exsists
    public void findClientId() throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SecurityException, IllegalStateException, SystemException, NotSupportedException{
        
        try{
        int cId = emf.createEntityManager().createNamedQuery("Client.findByClientNumber", Client.class)
                .setParameter("clientNumber", clientNumber)
                .getSingleResult().getClientId();
            
            if (deleteAll){
                //Show him/her new reocrds form
                isClient=false;
                isClientValidated = true;
                
                //Populate old Personal Info from DB
                PersonalInfo currentOne = emf.createEntityManager().createNamedQuery("PersonalInfo.findByClientNumber", PersonalInfo.class)
                        .setParameter("clientNumber", clientNumber)
                        .getSingleResult();
                this.clientName = currentOne.getName();
                this.gender = currentOne.getGender();
                this.job = currentOne.getJob();
                this.otherJob = currentOne.getOtherJob();
                this.govId = currentOne.getGovId();
                this.idIssuer = currentOne.getIdIssuer();
                this.status = currentOne.getStatus();
                this.otherStatus = currentOne.getOtherStatus();
                this.dependents = currentOne.getDependent();
                this.crId = currentOne.getCrId();
                this.crIssuer = currentOne.getCrIssuer();
                this.pobox = currentOne.getPobox();
                this.city = currentOne.getCity();
                this.postalCode = currentOne.getPostalcode();
                this.wassel = currentOne.getWassel();
                this.tel = currentOne.getTel();
                this.mobile = currentOne.getMobile();
                this.resedentType = currentOne.getResedentType();
                this.resedentRentAmount = currentOne.getResedentRentAmount();
                this.resedentCity = currentOne.getResedentCity();
                this.resedentDistrict = currentOne.getResedentDistrict();
                this.resedentStreet = currentOne.getResedentStreet();
                this.resedentNumber = currentOne.getResedentNumber();
                this.issueDateString = toHijriString(currentOne.getIdIssueDate());
                this.issueEndDateString = toHijriString(currentOne.getIdIssueExpireDate());
                this.birthdayDateString = toHijriString(currentOne.getBirthday());
                this.clientNotes = currentOne.getClientNotes();
                if(currentOne.getCrIssueDate()!= null){
                    this.crIssueDateString = toHijriString(currentOne.getCrIssueDate());
                } else {
                    this.crIssueDateString = "";
                }
                
                //Populate old Employment Info from DB
            try{
                EmploymentInfo currentEmInfo = emf.createEntityManager().createNamedQuery("EmploymentInfo.findByClientNumber", EmploymentInfo.class)
                        .setParameter("clientNumber", clientNumber)
                        .getSingleResult();
                this.employer = currentEmInfo.getEmployer();
                this.jobTitle = currentEmInfo.getJobTitle();
                this.employmentNumber = currentEmInfo.getEmploymentNumber();
                this.departement = currentEmInfo.getDepartement();
                this.employmentDateString = toHijriString(currentEmInfo.getEmploymentDate());
                this.rank = currentEmInfo.getRank();
                this.rankID = emf.createEntityManager().createNamedQuery("Ranks.findByRankTitle",Ranks.class).setParameter("rankTitle", rank).getSingleResult().getRankId();
                this.militaryNumber = currentEmInfo.getMilitaryNumber();
                this.salary = currentEmInfo.getSalary();
                this.extraIncom = currentEmInfo.getExtraIncom();
                this.payDay =currentEmInfo.getPayDay();
                this.toRetire = currentEmInfo.getToRetire();
                this.employerReportTo =currentEmInfo.getEmployerReportTo();
                this.employerJobTitle = currentEmInfo.getEmployerJobTitle();
                this.employerTel = currentEmInfo.getEmployerTel();
                this.employerTelExt =currentEmInfo.getEmployerTelExt();
                this.contactName = currentEmInfo.getContactName();
                this.contactTel = currentEmInfo.getContactTel();
                this.contactMobile = currentEmInfo.getContactMobile();
            }catch(NoResultException r){
                System.out.println("No Employment Result for this Clinet!!");
                this.employer = "";
                this.jobTitle = "";
                this.employmentNumber = 0;
                this.departement = "";
                this.employmentDateString = "";
                this.rankID = 0;
                this.militaryNumber = 0;
                this.salary = 0;
                this.extraIncom = 0;
                this.payDay = "";
                this.toRetire = "";
                this.employerReportTo ="";
                this.employerJobTitle = "";
                this.employerTel = 0;
                this.employerTelExt =0;
                this.contactName = "";
                this.contactTel = 0;
                this.contactMobile = 0;
                /*
                utx.begin();
                EmploymentInfo ei = new EmploymentInfo();
                ei.setEmployer(employer);
                ei.setJobTitle(jobTitle);
                ei.setEmploymentNumber(employmentNumber);
                ei.setDepartement(departement);
                    //Formatting Employment Date to hijri
                    employmentDate = fortmatToHijri(employmentDateString);
                ei.setEmploymentDate(employmentDate);
                ei.setRank(rank);
                ei.setMilitaryNumber(militaryNumber);
                ei.setSalary(salary);
                ei.setExtraIncom(extraIncom);
                /*    if (isHijri){
                        //Formatting Salary Date to hijri
                        payDay = "بالتاريخ الهجري" ;
                    } else {
                        //Formatting Salary Date to georgian
                         payDay = "بالتاريخ الميلادي";
                    }
                
                ei.setPayDay(payDay);
                ei.setToRetire(toRetire);
                ei.setEmployerReportTo(employerReportTo);
                ei.setEmployerJobTitle(employerJobTitle);
                ei.setEmployerTel(employerTel);
                ei.setEmployerTelExt(employerTelExt);
                ei.setContactName(contactName);
                ei.setContactTel(contactTel);
                ei.setContactMobile(contactMobile);
                    Client c = emf.createEntityManager().createNamedQuery("Client.findByClientNumber", Client.class)
                        .setParameter("clientNumber", clientNumber)
                        .getSingleResult();
                ei.setClientId(c);
                emf.createEntityManager().persist(ei);
                utx.commit();
                */
            }
                
               try{ 
                //Populate old Financial Info from DB
                FinancailInfo currentFiInfo = emf.createEntityManager().createNamedQuery("FinancailInfo.findByClientNumber", FinancailInfo.class)
                        .setParameter("clientNumber", clientNumber)
                        .getSingleResult();
                this.liable = currentFiInfo.getLiable();
                recreatePagination();
                recreateModel();
                
               } catch(NoResultException r){
                   //Create Financial Info for him/her with false Liable
                   /*
                   utx.begin();
                   FinancailInfo tmpFi = new FinancailInfo();
                   tmpFi.setLiable(false);
                       Client c = emf.createEntityManager().createNamedQuery("Client.findByClientNumber", Client.class)
                            .setParameter("clientNumber", clientNumber)
                            .getSingleResult();
                   tmpFi.setClientId(c);
                   emf.createEntityManager().persist(tmpFi);
                   utx.commit();
                   */
                    this.liable =false;
                    this.loanerName = "";
                    this.amount = 0;
                    this.duration = 0;
                    this.installment = 0;
                    this.remaining = 0;
                    recreatePagination();
                    recreateModel();
                   //this.liable = tmpFi.getLiable();
                   
               }
               
               //Client Request
               try{
               Request currentCR = emf.createEntityManager().createNamedQuery("Request.findLast", Request.class)
                       .setParameter("clientNumber", clientNumber)
                       .getSingleResult();
               this.requestNumber = currentCR.getReqNumber();
               isRequest = true;
               }catch(NoResultException nr){ 
                   isRequest = false;
                   this.requestNumber = 0;
               }catch(ArrayIndexOutOfBoundsException e){
                   isRequest = false;
                   this.requestNumber = 0;
               }
            }else {
                //jump to request creation!!
                isClient = false;
                isFinancailInfoValidated = true;
                //Client Request
               try{
               Request currentCR = emf.createEntityManager().createNamedQuery("Request.findLast", Request.class)
                       .setParameter("clientNumber", clientNumber)
                       .getSingleResult();
               this.requestNumber = currentCR.getReqNumber();
               isRequest = true;
               }catch(NoResultException nr){
                   isRequest = false;
                   this.clientNumber = 0;
               }catch(ArrayIndexOutOfBoundsException ai){
                   isRequest = false;
                   this.clientNumber =0;
               }
            }
            
            clinetId = cId;
        }catch(NoResultException e){
           isClientValidated = false;
           isClient = true;
           System.out.println("Entering Client is not there!!");
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,  "رقم العميل غير موجود بالنظام", null));
           FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("errorMessages"); 
        }        
    }
    
    //Adding Client
    public String addClient() throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SecurityException, IllegalStateException, SystemException, NotSupportedException{
           
           
               Client client = new Client();
                utx.begin();
                client.setClientNumber(clientNumber);
                emf.createEntityManager().persist(client);
                utx.commit();
                
                resetFields();
                isClient=false;
                isClientValidated =true;
            
       return "";
    }
    
    //This method will save new Order Request
    public String addClientPersonalInfo() throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SecurityException, IllegalStateException, SystemException, NotSupportedException{
        //em = emf.createEntityManager();
        //Check if client's Persoanl Info exists 
        try{
            PersonalInfo oldPi = emf.createEntityManager().createNamedQuery("PersonalInfo.findByClientNumber", PersonalInfo.class)
                    .setParameter("clientNumber", clientNumber)
                    .getSingleResult();
            utx.begin();
            oldPi.setName(clientName);
            oldPi.setGender(gender);
            oldPi.setJob(job);
            oldPi.setOtherJob(otherJob);
            oldPi.setGovId(govId);
            oldPi.setIdIssuer(idIssuer);
                //Formatting Issue Date to hijri
                issueDate = fortmatToHijri(issueDateString);
            oldPi.setIdIssueDate(issueDate);
                //Formatting Issue End Date to hijri
                idExpireDate = fortmatToHijri(issueEndDateString);
            oldPi.setIdIssueExpireDate(idExpireDate);
                //Formatting Birthday Date to hijri
                birthDay = fortmatToHijri(birthdayDateString);
            oldPi.setBirthday(birthDay);
            oldPi.setStatus(status);
            oldPi.setOtherStatus(otherStatus);
            oldPi.setDependent(dependents);
            oldPi.setCrId(crId);
            oldPi.setCrIssuer(crIssuer);
                //Formatting CR Issue Date to Hijri
                if(!crIssueDateString.equals("")){
                    crIssueDate = fortmatToHijri(crIssueDateString);
                    oldPi.setCrIssueDate(crIssueDate);
                }
            oldPi.setPobox(pobox);
            oldPi.setCity(city);
            oldPi.setPostalcode(postalCode);
            oldPi.setWassel(wassel);
            oldPi.setTel(tel);
            oldPi.setMobile(mobile);
            oldPi.setResedentType(resedentType);
            oldPi.setResedentRentAmount(resedentRentAmount);
            oldPi.setResedentCity(resedentCity);
            oldPi.setResedentDistrict(resedentDistrict);
            oldPi.setResedentStreet(resedentStreet);
            oldPi.setResedentNumber(resedentNumber);
                Client c = emf.createEntityManager().createNamedQuery("Client.findByClientNumber", Client.class)
                    .setParameter("clientNumber", clientNumber)
                    .getSingleResult();
            oldPi.setClientId(c);
            oldPi.setClientNotes(clientNotes);
            emf.createEntityManager().merge(oldPi);
            utx.commit();
            //To hide current form
            isClientValidated =false;
            //To show next form
            isPersonalInfoValidated = true;
        }catch(NoResultException e){
            //No results found for Client then create new Personal Info for him/her
            utx.begin();
            PersonalInfo pi = new PersonalInfo();
            pi.setName(clientName);
            pi.setGender(gender);
            pi.setJob(job);
            pi.setOtherJob(otherJob);
            pi.setGovId(govId);
            pi.setIdIssuer(idIssuer);
                //Formatting Issue Date to hijri
                issueDate = fortmatToHijri(issueDateString);
            pi.setIdIssueDate(issueDate);
                //Formatting Issue End Date to hijri
                idExpireDate = fortmatToHijri(issueDateString);
            pi.setIdIssueExpireDate(idExpireDate);
                //Formatting Birthday Date to hijri
                birthDay = fortmatToHijri(birthdayDateString);
            pi.setBirthday(birthDay);
            pi.setStatus(status);
            pi.setOtherStatus(otherStatus);
            pi.setDependent(dependents);
            pi.setCrId(crId);
            pi.setCrIssuer(crIssuer);
                //Formatting CR Issue Date to Hijri
                if(!crIssueDateString.equals("")){
                    crIssueDate = fortmatToHijri(crIssueDateString);
                    pi.setCrIssueDate(crIssueDate);
                }
            pi.setPobox(pobox);
            pi.setCity(city);
            pi.setPostalcode(postalCode);
            pi.setWassel(wassel);
            pi.setTel(tel);
            pi.setMobile(mobile);
            pi.setResedentType(resedentType);
            pi.setResedentRentAmount(resedentRentAmount);
            pi.setResedentCity(resedentCity);
            pi.setResedentDistrict(resedentDistrict);
            pi.setResedentStreet(resedentStreet);
            pi.setResedentNumber(resedentNumber);
                Client c = emf.createEntityManager().createNamedQuery("Client.findByClientNumber", Client.class)
                    .setParameter("clientNumber", clientNumber)
                    .getSingleResult();
            pi.setClientId(c);
            pi.setClientNotes(clientNotes);
            emf.createEntityManager().persist(pi);
            utx.commit();
            //To hide current form
            isClientValidated =false;
            //To show next form
            isPersonalInfoValidated = true;
        }
        
        return "";
    }
    
    public String addClientEmploymentInfo() throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SecurityException, IllegalStateException, SystemException, NotSupportedException{
        try{
            EmploymentInfo oldEi = emf.createEntityManager().createNamedQuery("EmploymentInfo.findByClientNumber", EmploymentInfo.class)
                    .setParameter("clientNumber", clientNumber)
                    .getSingleResult();
            utx.begin();
            oldEi.setEmployer(employer);
            oldEi.setJobTitle(jobTitle);
            oldEi.setEmploymentNumber(employmentNumber);
            oldEi.setDepartement(departement);
                //Formatting Employment Date to hijri
                employmentDate = fortmatToHijri(employmentDateString);
            oldEi.setEmploymentDate(employmentDate);
            oldEi.setRank(rank);
            oldEi.setMilitaryNumber(militaryNumber);
            oldEi.setSalary(salary);
            oldEi.setExtraIncom(extraIncom);
            /*
                if (isHijri){
                    //Formatting Salary Date to hijri
                    payDay = payDay + "بالتاريخ الهجري" ;
                } else {
                    //Formatting Salary Date to georgian
                     payDay = payDay + "بالتاريخ الميلادي";
                }
            */
            oldEi.setPayDay(payDay);
            oldEi.setToRetire(toRetire);
            oldEi.setEmployerReportTo(employerReportTo);
            oldEi.setEmployerJobTitle(employerJobTitle);
            oldEi.setEmployerTel(employerTel);
            oldEi.setEmployerTelExt(employerTelExt);
            oldEi.setContactName(contactName);
            oldEi.setContactTel(contactTel);
            oldEi.setContactMobile(contactMobile);
                Client c = emf.createEntityManager().createNamedQuery("Client.findByClientNumber", Client.class)
                    .setParameter("clientNumber", clientNumber)
                    .getSingleResult();
            oldEi.setClientId(c);
            emf.createEntityManager().merge(oldEi);
            utx.commit();
            
            //Hide the current form
            isPersonalInfoValidated = false;
            //Show next form
            isEmploymentInfoValidated = true;
        }catch(NoResultException a){
            //No result for Client Employment Info found
            System.out.println("Entering NoResult for Employment!!");
            utx.begin();
            EmploymentInfo ei = new EmploymentInfo();
            ei.setEmployer(employer);
            ei.setJobTitle(jobTitle);
            ei.setEmploymentNumber(employmentNumber);
            ei.setDepartement(departement);
                //Formatting Employment Date to hijri
                employmentDate = fortmatToHijri(employmentDateString);
            ei.setEmploymentDate(employmentDate);
            ei.setRank(rank);
            ei.setMilitaryNumber(militaryNumber);
            ei.setSalary(salary);
            ei.setExtraIncom(extraIncom);
            /*    if (isHijri){
                    //Formatting Salary Date to hijri
                    payDay = "بالتاريخ الهجري" ;
                } else {
                    //Formatting Salary Date to georgian
                     payDay = "بالتاريخ الميلادي";
                }
            */
            ei.setPayDay(payDay);
            ei.setToRetire(toRetire);
            ei.setEmployerReportTo(employerReportTo);
            ei.setEmployerJobTitle(employerJobTitle);
            ei.setEmployerTel(employerTel);
            ei.setEmployerTelExt(employerTelExt);
            ei.setContactName(contactName);
            ei.setContactTel(contactTel);
            ei.setContactMobile(contactMobile);
                Client c = emf.createEntityManager().createNamedQuery("Client.findByClientNumber", Client.class)
                    .setParameter("clientNumber", clientNumber)
                    .getSingleResult();
            ei.setClientId(c);
            emf.createEntityManager().persist(ei);
            utx.commit();

            //Hide the current form
            isPersonalInfoValidated = false;
            //Show next form
            isEmploymentInfoValidated = true;
        }
       
        return "";
    }
    
    //Used for performing the save after editing the row for Loaner info
    public String saveLoanerRowInfo() throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SecurityException, IllegalStateException, SystemException, NotSupportedException{
        current = (Loaner) getItems().getRowData();
        utx.begin();
        Loaner s = emf.createEntityManager().find(Loaner.class, current.getLoanerId());
        s.setIsEditable(false);
        s.setAmount(amount);
        s.setDuration(duration);
        s.setInstallment(installment);
        s.setLoanerName(loanerName);
        s.setRemaining(remaining);
        FinancailInfo oldFi = emf.createEntityManager().createNamedQuery("FinancailInfo.findByClientNumber", FinancailInfo.class)
                    .setParameter("clientNumber", clientNumber)
                    .getSingleResult();
        s.setFinancailId(oldFi);
        emf.createEntityManager().merge(s);
        utx.commit();
        hideLoanerInputs = true;
        
        //ٍReset all textFileds
        loanerName ="";
        amount  = remaining = installment = 0;
        duration = 0;    
        
        //Refresh DataTable
        recreatePagination();
        recreateModel();
        return "";
    }
    //Used to set isEditable for Loaner Table row
    public String editLoanerInfo()throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SecurityException, IllegalStateException, SystemException, NotSupportedException{
        hideLoanerInputs = false;
        current = (Loaner) getItems().getRowData();
        utx.begin();
        Loaner s = emf.createEntityManager().find(Loaner.class, current.getLoanerId());
        s.setIsEditable(true);
        emf.createEntityManager().merge(s);
        utx.commit();
        
        //Refresh Datatable
        recreatePagination();
        recreateModel();
        
        this.loanerName = s.getLoanerName();
        this.amount = s.getAmount();
        this.duration = s.getDuration();
        this.installment = s.getInstallment();
        this.remaining = s.getRemaining();
        
        return "";
    }
    
    //Remove Selected row Loaner info
    public String deleteLoanerInfo() throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SecurityException, IllegalStateException, SystemException, NotSupportedException{
        em = emf.createEntityManager();
        //EntityTransaction et = em.getTransaction();
        current = (Loaner) getItems().getRowData();
        System.out.println("Current: " + current );
        try{
            utx.begin();
            Loaner s = em.find(Loaner.class, current.getLoanerId());
            Loaner tmp = em.merge(s);
            em.remove(tmp);
            em.joinTransaction();
            em.flush();
            utx.commit();
            
            
            
            //Refresh Datatable
            recreatePagination();
            recreateModel();
            
            FacesContext.getCurrentInstance().addMessage("savedStatus", new FacesMessage(FacesMessage.SEVERITY_INFO, "تم الحذف بنجاح", null));
            //FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("successMessages");
            
            
            return "";
        }catch(Exception e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "يوجد مشكله بالتواصل مع قاعدة البيانات", null));
            FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("scanMessages");
            
            return "";
        }
    }
    
    //Find financail info for Client based on his/her number
    public List<FinancailInfo> findFinancailInfoForClient(int cNumber){
        List<FinancailInfo> tmp = emf.createEntityManager().createNamedQuery("FinancailInfo.findByClientNumber", FinancailInfo.class)
                .setParameter("clientNumber", cNumber)
                .getResultList();
        return tmp;
    }
    
    //Adding new loanerInfo
    public String addLoanerInfo() throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SecurityException, IllegalStateException, SystemException, NotSupportedException{
        try{
            FinancailInfo oldFi = emf.createEntityManager().createNamedQuery("FinancailInfo.findByClientNumber", FinancailInfo.class)
                    .setParameter("clientNumber", clientNumber)
                    .getSingleResult();
            utx.begin();
            oldFi.setLiable(liable);
                Client c = emf.createEntityManager().createNamedQuery("Client.findByClientNumber", Client.class)
                        .setParameter("clientNumber", clientNumber)
                        .getSingleResult();
            oldFi.setClientId(c);
            emf.createEntityManager().merge(oldFi);
            utx.commit();

            //Adding new Loaner
            utx.begin();
            Loaner l = new Loaner();
            l.setLoanerName(loanerName);
            l.setAmount(amount);
            l.setDuration(duration);
            l.setRemaining(remaining);
            l.setInstallment(installment);
            l.setIsEditable(false);
              /*  FinancailInfo f =emf.createEntityManager().createNamedQuery("FinancailInfo.findByClientNumber", FinancailInfo.class)
                        .setParameter("clientNumber", clientNumber)
                        .getSingleResult();*/
            l.setFinancailId(oldFi);
            emf.createEntityManager().merge(l);
            utx.commit();
            
            //Refresh DataTable
            recreatePagination();
            recreateModel();
            
            //ٍReset all textFileds
            loanerName ="";
            amount  = remaining = installment = 0;
            duration = 0;
            
        }catch(NoResultException e){
            //Adding new Financail Info
            utx.begin();
            FinancailInfo fi= new FinancailInfo();
            fi.setLiable(liable);
                Client c = emf.createEntityManager().createNamedQuery("Client.findByClientNumber", Client.class)
                        .setParameter("clientNumber", clientNumber)
                        .getSingleResult();
            fi.setClientId(c);
            emf.createEntityManager().persist(fi);
            utx.commit();

            //Adding new Loaner
            utx.begin();
            Loaner l = new Loaner();
            l.setLoanerName(loanerName);
            l.setAmount(amount);
            l.setDuration(duration);
            l.setRemaining(remaining);
            l.setInstallment(installment);
            l.setIsEditable(false);
              /*  FinancailInfo f =emf.createEntityManager().createNamedQuery("FinancailInfo.findByClientNumber", FinancailInfo.class)
                        .setParameter("clientNumber", clientNumber)
                        .getSingleResult();*/
            l.setFinancailId(fi);
            emf.createEntityManager().persist(l);
            utx.commit();
            
            //Refresh DataTable
            recreatePagination();
            recreateModel();
            
            //ٍReset all textFileds
            loanerName ="";
            amount  = remaining = installment = 0;
            duration =0;
        }
        return "";
    }
    
    //Navigate Next to request Creation and submission
    public String moveNext(){
        //Hide the current form
            isEmploymentInfoValidated = false;
            //Show next form
            isFinancailInfoValidated = true;
            
            //ٍReset all textFileds
            loanerName ="";
            amount  = remaining = installment = 0;
            duration = 0;
            
            return "";
    }
    
    //Navigate back to main
    public String backToMain(){
        isClient = true;
        isClientValidated = false;
        isPersonalInfoValidated = false;
        isEmploymentInfoValidated = false;
        isFinancailInfoValidated = false;
        deleteAll = false;
        getNewClientNumber();
        
        
        
        return "";
    }
    
    //Navigate back to personal info
    public String backToPersonalInfo(){
        isClientValidated = true;
        isPersonalInfoValidated = false;
        isEmploymentInfoValidated = false;
        isFinancailInfoValidated = false;
        
        return "";
    }
    
    //Navigate back to employment info
    public String backToEmploymentInfo(){
        isPersonalInfoValidated = true;
        isEmploymentInfoValidated = false;
        isClientValidated =false;
        isFinancailInfoValidated = false;
        
        return "";
    }
    
    //This method will copy the file form local fileSystem
    public String saveScannedPDF() throws IOException{
        //Acceess the location of the scanned PDF file
        Departement tmp = emf.createEntityManager().createNamedQuery("Departement.findByDeptId", Departement.class)
                .setParameter("deptId", findDeptIdofSession())
                .getSingleResult();
        String scannerPath = tmp.getDeptScanner().trim();
        Path path = Paths.get(scannerPath,tmp.getDeptScannerFileName().trim());
        File sourceFile = path.toFile();
        System.out.println(sourceFile);
        
        System.out.println(sourceFile.exists());
        
        //Upload it to server location @ /tmp
        if (!sourceFile.exists()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إعادة المسح الضوئي مرة أخرى", null));
            FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("scanMessages");
        } else {
            /*if(sourceFile.length()>2000000){
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "حجم الملف أكبر من المسموح به وهو٢ ميغا بايت", null));
               FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("scanMessages"); 
            }else {*/
            try {

                InputStream content = new FileInputStream(sourceFile);
                //final byte[] bytes = new byte[3072];
                //doc = new byte[4096];

                //Copy it to "tmpDoc" in order to persist it to DB
                
                doc = IOUtils.toByteArray(content);
                content.close();
            } catch (IOException ex) {
                //Logger.getLogger(WaredBean.class.getName()).log(Level.SEVERE, null, ex);
            }

            //Remove it from both Server and FileSystem locations
            sourceFile.delete();
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "تم إضافة الملف بنجاح", null));
            FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("scanMessages");
            //}
        }
        return "";
    }
    
    public int findUserIDofSession(){
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        HttpSession targetSession = (HttpSession) request.getSession(false);
        return (Integer) targetSession.getAttribute("userId");
    }
    public String updatedBy(){
       String tmp = emf.createEntityManager().createNamedQuery("User.findByUserId", User.class)
               .setParameter("userId", findUserIDofSession())
               .getSingleResult().getUserName();
       return tmp;
   }
    
    //Method to create new request
    public String saveRequest() throws NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException{
        if(doc == null){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إرفاق الملف عن طريق الماسح الضوئي أولاً", null));
            FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("scanMessages");
        } else {
          //  try{
            utx.begin();
                try{
                    
                    Request oldRequest = emf.createEntityManager().createNamedQuery("Request.findByReqNumber", Request.class)
                        .setParameter("reqNumber", requestNumber)
                        .getSingleResult();
                    oldRequest.setReqDoc(doc);
                    oldRequest.setReqNumber(requestNumber);
                    oldRequest.setReqStatusId(new Status(1));
                        Client c = emf.createEntityManager().createNamedQuery("Client.findByClientNumber", Client.class)
                                .setParameter("clientNumber", clientNumber)
                                .getSingleResult();
                    oldRequest.setReqClientId(c);
                        User u = emf.createEntityManager().createNamedQuery("User.findByUserId", User.class)
                                .setParameter("userId", findUserIDofSession())
                                .getSingleResult();
                    oldRequest.setReqUserId(u);
                        Chronology hijri = IslamicChronology.getInstance(DateTimeZone.forID("Asia/Riyadh"));
                        LocalDate todayHijri = LocalDate.now(hijri).plusDays(1);
                    oldRequest.setREQDate(todayHijri.toDate());
                    String tmpNote = oldRequest.getHistoryNotes() +
                                 "\r\n" +
                                " تم تحديثها " +
                                " بواسطه: " +
                                " " +
                                updatedBy() +
                                " " +
                                " بتاريخ: " + 
                                " " +
                                todayHijri.toString("yyyy-MM-dd") ;
                    oldRequest.setHistoryNotes(tmpNote);
                    emf.createEntityManager().merge(oldRequest);
                    utx.commit();
                    
                    //Reset all values
                    requestNumber = 0;

                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "تم إضافة الطلب بنجاح", null));
                    FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("successMessages");
                    
                    //Go back to main
                    backToMain();
 
                }catch(NoResultException e){
                   
                    Request r = new Request();
                    r.setReqDoc(doc);
                    r.setReqStatusId(new Status(1));
                    r.setReqNumber(requestNumber);
                        Client c = emf.createEntityManager().createNamedQuery("Client.findByClientNumber", Client.class)
                                .setParameter("clientNumber", clientNumber)
                                .getSingleResult();
                    r.setReqClientId(c);
                        User u = emf.createEntityManager().createNamedQuery("User.findByUserId", User.class)
                                .setParameter("userId", findUserIDofSession())
                                .getSingleResult();
                    r.setReqUserId(u);
                        Chronology hijri = IslamicChronology.getInstance(DateTimeZone.forID("Asia/Riyadh"));
                        LocalDate todayHijri = LocalDate.now(hijri).plusDays(1);
                    r.setREQDate(todayHijri.toDate());
                    historyNotes = "تم إنشاء طلب شراء رقم " +
                                requestNumber +
                                "بتاريخ : " + 
                                todayHijri.toString("yyyy-MM-dd") + 
                                " بواسطه: " + 
                                updatedBy() ;
                    r.setHistoryNotes(historyNotes);
                    r.setReqClientGovId(govId);
                    emf.createEntityManager().persist(r);
                    utx.commit();
                    backToMain();

                    //Reset all values
                    requestNumber = 0;

                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "تم إضافة الطلب بنجاح", null));
                    FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("successMessages");
                    
                    backToMain();
 
                }
                
               getNewClientNumber();
            /*}catch(Exception e){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "يوجد مشكله بالتواصل مع قاعدة البيانات", null));
                FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("scanMessages");

            }*/
        }
      return "";
    }
    
    public int findDeptIdofSession(){
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        HttpSession targetSession = (HttpSession) request.getSession(false);
        return (Integer) targetSession.getAttribute("deptId");
    }
    
    public Loaner getSelected(){
        if(current == null){
            current = new Loaner();
            selectedItemIndex = -1;
        }
        return current;
    }
    
    public PaginationHelper getPagination() {
        
        if (pagination == null) {
            
            pagination = new PaginationHelper(6) {
                @Override
                public int getItemsCount() {
                    int i=0;
                    try {
                        System.out.println("ClientNumber: "+ clientNumber);
                        i= emf.createEntityManager().createNamedQuery("Loaner.findByClientNumber", Loaner.class)
                            .setParameter("clientNumber", clientNumber)
                            .getResultList().size();
                        
                        }catch(IllegalArgumentException e) {
                          return 0;
                        }
                    return i;
                }

                @Override
                public DataModel createPageDataModel() {
                    List<Loaner> range = new ArrayList<>();
                    int maxResult = (getPageFirstItem() + getPageSize()) - getPageFirstItem() ;
                    
                        range = emf.createEntityManager().createNamedQuery("Loaner.findByClientNumber", Loaner.class)
                                .setMaxResults(maxResult)
                                .setFirstResult(getPageFirstItem())
                                .setParameter("clientNumber", clientNumber)
                                .getResultList();
                        System.out.println("Range: "+ range);
                        return new ListDataModel(range);
                    
                    
                }
            };
        }
        return pagination;
    }
    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }
    
    //This method is very useful to refresh items.
    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "";
    }
    
}