/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package loan.system.ejbs;

//import com.google.common.base.Charsets;
//import com.sun.tools.xjc.api.S2JJAXBModel;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUnit;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import loan.system.entities.Action;
import loan.system.entities.Departement;
import loan.system.entities.EmploymentInfo;
import loan.system.entities.FinancailInfo;
import loan.system.entities.Loaner;
import loan.system.entities.PersonalInfo;
import loan.system.entities.Request;
import loan.system.entities.Status;
import loan.system.entities.User;
import loan.system.general.GenerateDocx;
import loan.system.general.Mobily;
import loan.system.general.PaginationHelper;
import org.apache.commons.io.IOUtils;
import org.joda.time.Chronology;
import org.joda.time.LocalDate;
import org.joda.time.chrono.IslamicChronology;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author me
 */

@Named("Workflow")
@SessionScoped

public class WorkflowBean implements Serializable{
    
    @PersistenceUnit(unitName = "LOAN_SYSTEMPU")
    EntityManagerFactory emf;
    EntityManager em;
    
    @Resource
    UserTransaction utx;
    
    @Inject
    LoanerInfoBean lib;
    
    @Inject
    ToBePaidBean tbp;
    
    private Request current;
    private Request selectedFile;
    private DataModel items=null;
    private PaginationHelper pagination;
    private List<Action> managerActionsList;
    private List<Action> usersActionsList;
    private List<Action> employeeActionList;
    private List<Action> dirctorActionList;
    private List<Action> coordinatorActionList;
    private List<Action> toAction;
    private PersonalInfo clientPI;
    private EmploymentInfo clientEI;
    private FinancailInfo clientFI;
    private int selectedItemIndex;
    private int actionID;
    private int clientMobile;
    private float profitMargin = 1F;
    private boolean isManager;
    private boolean isUser;
    private boolean isClientFI;
    private String historyNotes;
    private String notes;
    private String genderString;
    private String statusString;
    private String jobString;
    private String smsMessage;
    private String resedentTypeString;
    private boolean isEmployee;
    private boolean isDirector;
    private boolean isCoordinator;
    private boolean isPayment;

    public String getSmsMessage() {
        return smsMessage;
    }

    public void setSmsMessage(String smsMessage) {
        this.smsMessage = smsMessage;
    }
    
    public float getProfitMargin() {
        return profitMargin;
    }

    public void setProfitMargin(float profitMargin) {
        this.profitMargin = profitMargin;
    }
    
    public boolean isIsPayment() {
        return isPayment;
    }

    public void setIsPayment(boolean isPayment) {
        this.isPayment = isPayment;
    }
    
    public boolean isIsCoordinator() {
        return isCoordinator;
    }

    public void setIsCoordinator(boolean isCoordinator) {
        this.isCoordinator = isCoordinator;
    }
    
    public List<Action> getCoordinatorActionList() {
        return coordinatorActionList;
    }

    public void setCoordinatorActionList(List<Action> coordinatorActionList) {
        this.coordinatorActionList = coordinatorActionList;
    }
    
    public boolean isIsEmployee() {
        return isEmployee;
    }

    public void setIsEmployee(boolean isEmployee) {
        this.isEmployee = isEmployee;
    }

    public boolean isIsDirector() {
        return isDirector;
    }

    public void setIsDirector(boolean isDirector) {
        this.isDirector = isDirector;
    }
    
    public String getResedentTypeString() {
        return resedentTypeString;
    }

    public void setResedentTypeString(String resedentTypeString) {
        this.resedentTypeString = resedentTypeString;
    }
    
    public String getJobString() {
        return jobString;
    }

    public void setJobString(String jobString) {
        this.jobString = jobString;
    }
    
    public String getStatusString() {
        return statusString;
    }

    public void setStatusString(String statusString) {
        this.statusString = statusString;
    }
    
    public boolean isIsClientFI() {
        return isClientFI;
    }

    public void setIsClientFI(boolean isClientFI) {
        this.isClientFI = isClientFI;
    }
    
    public String getGenderString() {
        return genderString;
    }

    public void setGenderString(String genderString) {
        this.genderString = genderString;
    }
    
    public PersonalInfo getClientPI() {
        return clientPI;
    }

    public void setClientPI(PersonalInfo clientPI) {
        this.clientPI = clientPI;
    }

    public EmploymentInfo getClientEI() {
        return clientEI;
    }

    public void setClientEI(EmploymentInfo clientEI) {
        this.clientEI = clientEI;
    }

    public FinancailInfo getClientFI() {
        return clientFI;
    }

    public void setClientFI(FinancailInfo clientFI) {
        this.clientFI = clientFI;
    }
    
    public List<Action> getEmployeeActionList() {
        return employeeActionList;
    }

    public void setEmployeeActionList(List<Action> employeeActionList) {
        this.employeeActionList = employeeActionList;
    }

    public List<Action> getDirctorActionList() {
        return dirctorActionList;
    }

    public void setDirctorActionList(List<Action> dirctorActionList) {
        this.dirctorActionList = dirctorActionList;
    }
    
    public int getClientMobile() {
        return clientMobile;
    }

    public void setClientMobile(int clientMobile) {
        this.clientMobile = clientMobile;
    }
    
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public int getActionID() {
        return actionID;
    }

    public void setActionID(int actionID) {
        this.actionID = actionID;
    }
    
    public Request getSelectedFile() {
        return selectedFile;
    }

    public void setSelectedFile(Request selectedFile) {
        this.selectedFile = selectedFile;
    }
    
    public List<Action> getToAction() {
        return toAction;
    }

    public void setToAction(List<Action> toAction) {
        this.toAction = toAction;
    }
    
    public boolean isIsUser() {
        return isUser;
    }

    public void setIsUser(boolean isUser) {
        this.isUser = isUser;
    }
    
    public String getHistoryNotes() {
        return historyNotes;
    }

    public void setHistoryNotes(String historyNotes) {
        this.historyNotes = historyNotes;
    }
    
    public boolean isIsManager() {
        return isManager;
    }

    public void setIsManager(boolean isManager) {
        this.isManager = isManager;
    }
    
    public List<Action> getManagerActionsList() {
        return managerActionsList;
    }

    public void setManagerActionsList(List<Action> managerActionsList) {
        this.managerActionsList = managerActionsList;
    }

    public List<Action> getUsersActionsList() {
        return usersActionsList;
    }

    public void setUsersActionsList(List<Action> usersActionsList) {
        this.usersActionsList = usersActionsList;
    }
    
    public WorkflowBean(){
    }
    
    public Request getSelected(){
        if(current == null){
            current = new Request();
            selectedItemIndex = -1;
        }
        return current;
    }
    public PaginationHelper getPagination() {
        
        if (pagination == null) {
            
            pagination = new PaginationHelper(6) {
                @Override
                public int getItemsCount() {
                    if(findRoleofSession() == 2){
                        //He/she is the manager
                       
                            return emf.createEntityManager().createNamedQuery("Request.ViewForManager", Request.class)
                                .getResultList().size();
                        
                        
                    }else if(findRoleofSession() == 4){
                        //He/she is the user
                         return emf.createEntityManager().createNamedQuery("Request.ViewForUser", Request.class)
                                .getResultList().size();
                        
                    }else if(findRoleofSession() == 3){
                        //He/she is the employee
                        
                            return emf.createEntityManager().createNamedQuery("Request.ViewForEmployee", Request.class)
                                .setParameter("userId", findUserIDofSession())
                                .getResultList().size();
                            
                    }else if(findRoleofSession() == 6){
                        //He/She is a coordinator
                            
                            return emf.createEntityManager().createNamedQuery("Request.ViewForCoorindator", Request.class)
                               .getResultList().size();
                            
                        
                            
                    }else if(findRoleofSession() == 5){
                        //He/she is the director
                        
                            return emf.createEntityManager().createNamedQuery("Request.ViewForCEO", Request.class)
                                .getResultList().size();
                        
                    }
                    else {
                        return 0;
                    }
                }

                @Override
                public DataModel createPageDataModel() {
                    List<Request> range = new ArrayList<>();
                    int maxResult = (getPageFirstItem() + getPageSize()) - getPageFirstItem() ;
                    int actions;
                    
                    if(findRoleofSession()== 2){
                        //He/She is a manager
                            
                            List<Request> tmp = emf.createEntityManager().createNamedQuery("Request.ViewForManager", Request.class)
                                .setMaxResults(maxResult)
                                .setFirstResult(getPageFirstItem())
                                .getResultList();
                            range = tmp;
                            
                            
                        
                    } else if(findRoleofSession() == 4){
                        //He/She is a user
                        
                        List<Request> tmp = emf.createEntityManager().createNamedQuery("Request.ViewForUser", Request.class)
                                    .setMaxResults(maxResult)
                                    .setFirstResult(getPageFirstItem())
                                    .getResultList();
                            range = tmp;
                        
                            
                    } else if(findRoleofSession() == 3){
                        //He/She is a employee
                        
                        List<Request> tmp = emf.createEntityManager().createNamedQuery("Request.ViewForEmployee", Request.class)
                                .setParameter("userId", findUserIDofSession())
                                .setMaxResults(maxResult)
                                .setFirstResult(getPageFirstItem())
                                .getResultList();
                            range = tmp;
                        
                            
                    }else if(findRoleofSession() == 6){
                        //He/She is a coordinator
                        
                        List<Request> tmp = emf.createEntityManager().createNamedQuery("Request.ViewForCoorindator", Request.class)
                                .setMaxResults(maxResult)
                                .setFirstResult(getPageFirstItem())
                                .getResultList();
                        
                            range = tmp;
                           
                    } else if(findRoleofSession() == 5){
                        //He/She is a director
                        
                        List<Request> tmp = emf.createEntityManager().createNamedQuery("Request.ViewForCEO", Request.class)
                                .setMaxResults(maxResult)
                                .setFirstResult(getPageFirstItem())
                                .getResultList();
                            range = tmp;
                        
                            
                    } 
                    
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
    public int findRoleofSession(){
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        HttpSession targetSession = (HttpSession) request.getSession(false);
        if(targetSession.getAttribute("role") == null){
            String userName = (String) targetSession.getAttribute("userName");
            try{
            int tmpRole = emf.createEntityManager().createNamedQuery("User.findByUserName", User.class)
                    .setParameter("userName", userName).getSingleResult().getUserRoleId().getRoleId();
            targetSession.setAttribute("role", tmpRole);
            return (Integer) targetSession.getAttribute("role");
            }catch(NoResultException e){
                return 0;
            }
        } else {
            return (Integer) targetSession.getAttribute("role");
        }
    }
    public int findDeptIdofSession(){
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        HttpSession targetSession = (HttpSession) request.getSession(false);
        return (Integer) targetSession.getAttribute("deptId");
    }
    public int findUserIDofSession(){
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        HttpSession targetSession = (HttpSession) request.getSession(false);
        return (Integer) targetSession.getAttribute("userId");
    }
    //Populate Actions for everyboby
    public void populateActionsListForAll(){
        managerActionsList = new ArrayList<>();
        usersActionsList = new ArrayList<>();
        employeeActionList = new ArrayList<>();
        dirctorActionList = new ArrayList<>();
        coordinatorActionList = new ArrayList<>();
        
        List<Action> tmp = emf.createEntityManager().createNamedQuery("Action.findAll", Action.class).getResultList();
        for(Action tmp2: tmp){
            if(tmp2.getActionId()== 1){
                // This is approved
                managerActionsList.add(tmp2);
                usersActionsList.add(tmp2);
                dirctorActionList.add(tmp2);
            }else if(tmp2.getActionId()== 2){
                // This is rejected
                managerActionsList.add(tmp2);
                usersActionsList.add(tmp2);
                dirctorActionList.add(tmp2);
            }else if(tmp2.getActionId()== 3){
                // This is done
                employeeActionList.add(tmp2);
                //coordinatorActionList.add(tmp2);
            }else if(tmp2.getActionId()==4){
                //This is edit
                managerActionsList.add(tmp2);
                usersActionsList.add(tmp2);
            }else if(tmp2.getActionId()==5){
                //This is partial paid
                coordinatorActionList.add(tmp2);
            }else if(tmp2.getActionId()==6){
                //This is collected
                coordinatorActionList.add(tmp2);
            }else if(tmp2.getActionId()==7){
                //This is Eidted Done
                employeeActionList.add(tmp2);
            }else if(tmp2.getActionId()==8){
                //This is Full Payment done
                coordinatorActionList.add(tmp2);
            }else if(tmp2.getActionId()==9){
                //This is accepted for partial payment
                dirctorActionList.add(tmp2);
            }else if(tmp2.getActionId()==10){
                //This is done collection
                usersActionsList.add(tmp2);
            }else if(tmp2.getActionId()==11){
                //This is approved for collection
                dirctorActionList.add(tmp2);
            }
        }
    }
    
    public void prepareHistoryViewofText(){
        Request tmp = (Request) getItems().getRowData();
        String oldMessage = tmp.getHistoryNotes();
        historyNotes =  oldMessage;
    }
    
    public String processItem(){
      current = (Request) getItems().getRowData();
      int currentStatus = current.getReqStatusId().getStatusId();
      if(currentStatus == 5 && (isCoordinator)){
          isPayment = true;
      } else if (currentStatus == 10 && (isCoordinator )){
          isPayment = true;
      } else if (currentStatus == 11 && (isUser)) {
          isPayment = true;
      }else if (currentStatus == 13 && (isDirector)) {
          isPayment = true;
      }else {
          isPayment = false;
      } 
      
        prepareHistoryViewofText();
        
//       HttpServletRequest servlet = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//       HttpSession session = servlet.getSession(true);
//       session.setAttribute("loanerRequest", current);
       
       
      if(findRoleofSession() == 4) {
          isUser = true;
          isManager = false;
          isEmployee = false;
          isDirector = false;
          isCoordinator = false;
          if(current.getReqStatusId().getStatusId() == 11){
              populateActionsListForAll();
              //System.out.println("Manager's Actions: "+ managerActionsList);
              usersActionsList.remove(0);
              usersActionsList.remove(0);
              usersActionsList.remove(0);
              toAction = usersActionsList;
          } else if(current.getReqStatusId().getStatusId() == 2) {
              populateActionsListForAll();
              usersActionsList.remove(3);
              //System.out.println(employeeActionList.get(0));
              toAction = usersActionsList;
          }
      } else if(findRoleofSession() == 2){
          isManager = true;
          isUser = false;
          isEmployee = false;
          isDirector = false;
          isCoordinator = false;
          if(current.getReqStatusId().getStatusId() == 14){
              populateActionsListForAll();
              //System.out.println("Manager's Actions: "+ managerActionsList);
              managerActionsList.remove(0);
              managerActionsList.remove(0);
              System.out.println(managerActionsList.get(0));
              toAction = managerActionsList;
          } else if(current.getReqStatusId().getStatusId() == 1) {
              populateActionsListForAll();
              managerActionsList.remove(2);
              //System.out.println(employeeActionList.get(0));
              toAction = managerActionsList;
          }
      } else if(findRoleofSession() == 3){
          isEmployee = true;
          isManager = false;
          isUser = false;
          isDirector = false;
          isCoordinator = false;
          if(current.getReqStatusId().getStatusId() == 9){
              populateActionsListForAll();
              employeeActionList.remove(0);
              //System.out.println(employeeActionList.get(1));
              toAction = employeeActionList;
          } else if(current.getReqStatusId().getStatusId() == 3) {
              populateActionsListForAll();
              employeeActionList.remove(1);
              //System.out.println(employeeActionList.get(0));
              toAction = employeeActionList;
          }
      } else if(findRoleofSession() == 5){
          isEmployee = true;
          isManager = false;
          isUser = false;
          isDirector = true;
          isCoordinator = false;
          toAction = dirctorActionList;
      } else if(findRoleofSession() == 6){
          isEmployee = true;
          isManager = false;
          isUser = false;
          isDirector = false;
          isCoordinator = true;
          if((current.getReqStatusId().getStatusId() == 5) || (current.getReqStatusId().getStatusId() == 10)){
              populateActionsListForAll();
              coordinatorActionList.remove(1);
              //System.out.println(employeeActionList.get(1));
              toAction = coordinatorActionList;
          } else if(current.getReqStatusId().getStatusId() == 6) {
              populateActionsListForAll();
              coordinatorActionList.remove(0);
              coordinatorActionList.remove(1);
              //System.out.println(employeeActionList.get(0));
              toAction = coordinatorActionList;
          }
      }
      
      //Getting Client Personal Info
      clientPI = emf.createEntityManager().createNamedQuery("PersonalInfo.findByClientNumber", PersonalInfo.class)
              .setParameter("clientNumber", current.getReqClientId().getClientNumber())
              .getSingleResult();
      clientMobile = clientPI.getMobile();
      String g = clientPI.getGender();
      if(g.equals("1")){
        this.genderString = "ذكر";
      } else if(g.equals("2")){
        this.genderString = "أنثى";
      }
      
      g = clientPI.getJob();
      if(g.equals("1")){
          this.jobString = "عسكري";
      }else if(g.equals("2")){
          this.jobString = "مدني";
      }else if(g.equals("3")){
          this.jobString = "أخرى";
      }
      
      g = clientPI.getStatus();
      if(g.equals("1")){
          this.statusString = "متزوج";
      }else if(g.equals("2")){
          this.statusString = "أعزب";
      }else if(g.equals("3")){
          this.statusString = "أخرى";
      }
      
      g = clientPI.getResedentType();
      if(g.equals("1")){
          this.resedentTypeString = "أملك منزلا أو شقه";
      }else if(g.equals("2")){
          this.resedentTypeString = "مستأجر شقه أو فيلا";
      }else if(g.equals("3")){
          this.resedentTypeString = "متوفر من جهه العمل";
      }else if(g.equals("4")){
          this.resedentTypeString = "أسكن مع أخرين";
      }
      
      //Getting Client Employment Info
      clientEI = emf.createEntityManager().createNamedQuery("EmploymentInfo.findByClientNumber", EmploymentInfo.class)
              .setParameter("clientNumber", current.getReqClientId().getClientNumber())
              .getSingleResult();
      
      //Getting Client Financail Info
      try{
      clientFI = emf.createEntityManager().createNamedQuery("FinancailInfo.findByClientNumber", FinancailInfo.class)
              .setParameter("clientNumber", current.getReqClientId().getClientNumber())
              .getSingleResult();
              isClientFI =true;
      }catch(NoResultException e){
          isClientFI = false;
      }
      
      //Trying to inject EJB
      lib.setClientNumber(current.getReqClientId().getClientNumber());
      lib.recreatePagination();
      lib.recreateModel();
      
      //Refreshing to be paid datatable
      tbp.setClientNumber(current.getReqClientId().getClientNumber());
      tbp.recreatePagination();
      tbp.recreateModel();
      
      
      
      
      return "";
   }
    
    public String showOrignalFile() throws IOException {
       selectedFile = (Request) getItems().getRowData();
       byte[] downloadFile = selectedFile.getReqDoc();
       HttpServletRequest servlet = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
       HttpSession session = servlet.getSession(true);
       session.setAttribute("doc", downloadFile);
        
       return "";
       
   }
    
    public String downloadFile() throws IOException {
       
       
       byte[] downloadFile = selectedFile.getReqDoc();
       FacesContext fc = FacesContext.getCurrentInstance();
       ExternalContext ec = fc.getExternalContext();
       HttpServletResponse response = (HttpServletResponse) ec.getResponse();
       
       response.reset();
       response.setContentType("application/pdf");
       response.setHeader("Content-disposition", "attachment; filename=\"downlaodedFile.pdf\"");
       BufferedInputStream input = null;
       BufferedOutputStream output = null;

        try {
                InputStream content = new ByteArrayInputStream(downloadFile);
                input = new BufferedInputStream(content);
                output = new BufferedOutputStream(response.getOutputStream());

                byte[] buffer = new byte[10240];
                for (int length; (length = input.read(buffer)) > 0;) {
                    output.write(buffer, 0, length);
                }
            
        } finally {
            output.close();
            input.close();
        }

        fc.responseComplete();
        return "";
   }
    
    public String showApprovedDocFile() throws IOException {
       selectedFile = (Request) getItems().getRowData();
       byte[] downloadFile = selectedFile.getReqApprovedDoc();
       HttpServletRequest servlet = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
       HttpSession session = servlet.getSession(true);
       session.setAttribute("approvedDoc", downloadFile);
        
       return "";
       
   }
    
    public String downloadApprovedDocFile() throws IOException {
       
       
       byte[] downloadFile = selectedFile.getReqApprovedDoc();
       FacesContext fc = FacesContext.getCurrentInstance();
       ExternalContext ec = fc.getExternalContext();
       HttpServletResponse response = (HttpServletResponse) ec.getResponse();
       
       response.reset();
       response.setContentType("application/pdf");
       response.setHeader("Content-disposition", "attachment; filename=\"downlaodedFile.pdf\"");
       BufferedInputStream input = null;
       BufferedOutputStream output = null;

        try {
                InputStream content = new ByteArrayInputStream(downloadFile);
                input = new BufferedInputStream(content);
                output = new BufferedOutputStream(response.getOutputStream());

                byte[] buffer = new byte[10240];
                for (int length; (length = input.read(buffer)) > 0;) {
                    output.write(buffer, 0, length);
                }
            
        } finally {
            output.close();
            input.close();
        }

        fc.responseComplete();
        return "";
   }
    
    
    public String saveScannedPDF2() throws IOException, NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException{
        
        Request tmp = emf.createEntityManager().find(Request.class, current.getReqId());
        //Acceess the location of the scanned PDF file
        Departement dep = emf.createEntityManager().createNamedQuery("Departement.findByDeptId", Departement.class)
                .setParameter("deptId", findDeptIdofSession())
                .getSingleResult();
        String scannerPath = dep.getDeptScanner().trim();
        Path path = Paths.get(scannerPath,dep.getDeptScannerFileName().trim());
        File sourceFile = path.toFile();
        System.out.println(sourceFile);
        
        System.out.println(sourceFile.exists());
        byte[] tmpDoc2;
        //Upload it to server location @ /tmp
        if (!sourceFile.exists()) {
            System.out.println("File doesn't exsists!!");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إعادة المسح الضوئي مرة أخرى", null));
            FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("contractMessages");
        } else {
            if(sourceFile.length()>10000000){
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "حجم الملف أكبر من المسموح به وهو١٠ ميغا بايت", null));
               FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("contractMessages"); 
            }else {
            
                InputStream content = new FileInputStream(sourceFile);
                //final byte[] bytes = new byte[3072];
                //doc = new byte[4096];

                //Copy it to "tmpDoc" in order to persist it to DB
                tmpDoc2 = IOUtils.toByteArray(content);
                //incoming.setInDoc(tmpDoc);
                content.close();



            
            //COPY IT into docRes 
            
            if(tmp == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "حدث خطأ في الاتصال مع قاعدة البيانات", null));
                FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("contractMessages");
            } else {
            utx.begin();
            tmp.setReqApprovedDoc(tmpDoc2);
            emf.createEntityManager().merge(tmp);
            utx.commit();
            //Remove it from both Server and FileSystem locations
            sourceFile.delete();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "تم إضافة الملف بنجاح", null));
            FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("contractMessages");
            }
            }
        }
        return "";
    }
    
    public String saveScannedReceipte() throws IOException, NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException{
        
        Request tmp = emf.createEntityManager().find(Request.class, current.getReqId());
        //Acceess the location of the scanned PDF file
        Departement dep = emf.createEntityManager().createNamedQuery("Departement.findByDeptId", Departement.class)
                .setParameter("deptId", findDeptIdofSession())
                .getSingleResult();
        String scannerPath = dep.getDeptScanner().trim();
        Path path = Paths.get(scannerPath,dep.getDeptScannerFileName().trim());
        File sourceFile = path.toFile();
        System.out.println(sourceFile);
        
        System.out.println(sourceFile.exists());
        byte[] tmpDoc2;
        //Upload it to server location @ /tmp
        if (!sourceFile.exists()) {
            System.out.println("File doesn't exsists!!");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إعادة المسح الضوئي مرة أخرى", null));
            FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("receiptePaperMessages");
        } else {
            if(sourceFile.length()>10000000){
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "حجم الملف أكبر من المسموح به وهو٢ ميغا بايت", null));
               FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("receiptePaperMessages"); 
            }else {
            
                InputStream content = new FileInputStream(sourceFile);
                //final byte[] bytes = new byte[3072];
                //doc = new byte[4096];

                //Copy it to "tmpDoc" in order to persist it to DB
                tmpDoc2 = IOUtils.toByteArray(content);
                //incoming.setInDoc(tmpDoc);
                content.close();



            
            //COPY IT into docRes 
            
            if(tmp == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "حدث خطأ في الاتصال مع قاعدة البيانات", null));
                FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("receiptePaperMessages");
            } else {
            utx.begin();
            tmp.setReqPaidByClient(tmpDoc2);
            tmp.setReqToBeCollected(false);
            tmp.setReqToBeScanned(true);
            emf.createEntityManager().merge(tmp);
            utx.commit();
            //Remove it from both Server and FileSystem locations
            sourceFile.delete();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "تم إضافة الملف بنجاح", null));
            FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("receiptePaperMessages");
            }
            }
        }
        return "";
    }
    public String refreshTable(){
        this.items = null;
        recreatePagination();
        recreateModel();
        
        return "";
    }
    
    public String saveScannedLetter() throws IOException, NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException{
        
        Request tmp = emf.createEntityManager().find(Request.class, current.getReqId());
        //Acceess the location of the scanned PDF file
        Departement dep = emf.createEntityManager().createNamedQuery("Departement.findByDeptId", Departement.class)
                .setParameter("deptId", findDeptIdofSession())
                .getSingleResult();
        String scannerPath = dep.getDeptScanner().trim();
        Path path = Paths.get(scannerPath,dep.getDeptScannerFileName().trim());
        File sourceFile = path.toFile();
        System.out.println(sourceFile);
        
        System.out.println(sourceFile.exists());
        byte[] tmpDoc2;
        //Upload it to server location @ /tmp
        if (!sourceFile.exists()) {
            System.out.println("File doesn't exsists!!");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إعادة المسح الضوئي مرة أخرى", null));
            FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("letterMessages");
        } else {
            if(sourceFile.length()>10000000){
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "حجم الملف أكبر من المسموح به وهو٢ ميغا بايت", null));
               FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("letterMessages"); 
            }else {
            
                InputStream content = new FileInputStream(sourceFile);
                //final byte[] bytes = new byte[3072];
                //doc = new byte[4096];

                //Copy it to "tmpDoc" in order to persist it to DB
                tmpDoc2 = IOUtils.toByteArray(content);
                //incoming.setInDoc(tmpDoc);
                content.close();



            
            //COPY IT into docRes 
            
            if(tmp == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "حدث خطأ في الاتصال مع قاعدة البيانات", null));
                FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("letterMessages");
            } else {
            utx.begin();
            tmp.setReqColPapers(tmpDoc2);
            //tmp.setReqToBeCollected(false);
            tmp.setReqToBeScanned(false);
            emf.createEntityManager().merge(tmp);
            utx.commit();
            //Remove it from both Server and FileSystem locations
            sourceFile.delete();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "تم إضافة الملف بنجاح", null));
            FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("letterMessages");
            }
            }
        }
        return "";
    }
    
    public String updatedBy(){
       String tmp = emf.createEntityManager().createNamedQuery("User.findByUserId", User.class)
               .setParameter("userId", findUserIDofSession())
               .getSingleResult().getUserName();
       return tmp;
   }
    
    public String saveChangesToProcess(){
       Chronology hijri = IslamicChronology.getInstanceUTC();
       LocalDate todayHijri = LocalDate.now(hijri).plusDays(1);
       String tmpNote;
       boolean unDo = false;
       try {
            Request i = emf.createEntityManager().find(Request.class, current.getReqId());
            utx.begin();
            
            //IF THERE IS NO NOTES WRITTEN BUT THERE IS ACTION TAKEN
            if((notes.equals("")) && (actionID != 0)){
                tmpNote = historyNotes +
                                 "\r\n" +
                                emf.createEntityManager().find(Action.class, actionID).getActionDescription() +
                                " " +
                                " بواسطه: " +
                                " " +
                                updatedBy() +
                                " " +
                                " بتاريخ: " + 
                                " " +
                                todayHijri.toString("yyyy-MM-dd") ;
                i.setHistoryNotes(tmpNote);
            }
            //IF SOMEPNE WROTE A NOTE WITH ACTION
            else if ((!notes.equals("")) && (actionID !=0)){
                tmpNote = historyNotes +
                                "\r\n" + 
                                "بتاريخ: " + 
                                todayHijri.toString("yyyy-MM-dd") + 
                                "  " + 
                                "كتب " + 
                                " " +
                                updatedBy() + 
                                " : " + 
                                notes +
                                "\r\n" + 
                                emf.createEntityManager().find(Action.class, actionID).getActionDescription()
                                +
                                " " +
                                " بواسطه: " +
                                " " +
                                updatedBy() +
                                " " +
                                " بتاريخ: " + 
                                " " +
                                todayHijri.toString("yyyy-MM-dd") ;
                i.setHistoryNotes(tmpNote);
            }//IF SOMEPNE WROTE A NOTE WITH NO ACTION
            else if ((!notes.equals("")) && (actionID ==0)){
                tmpNote = historyNotes +
                                "\r\n" + 
                                "بتاريخ: " + 
                                todayHijri.toString("yyyy-MM-dd") + 
                                "  " + 
                                "كتب " + 
                                " " +
                                updatedBy() + 
                                " : " + 
                                notes ;
                i.setHistoryNotes(tmpNote);
            }
            
            if((findRoleofSession() == 4) && (actionID == 1)){
                i.setReqStatusId(new Status(3));
            }else if((findRoleofSession() == 4) && (actionID == 10)){
                
                if(i.getReqColPapers() == null){
                    unDo = true;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "الرجاء إرفاق المخالصه النهائيه", null));
                    FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("allMessages");
                }else {
                    i.setReqStatusId(new Status(7));
                }
            }else if((findRoleofSession() == 4) && (actionID == 4)){
                i.setReqStatusId(new Status(14));
            }
            
            else if((findRoleofSession() == 2) && (actionID == 1)) {
                i.setReqStatusId(new Status(2));
            }else if((findRoleofSession() == 2) && (actionID == 4)) {
                i.setReqStatusId(new Status(9));
            }
            
            else if((findRoleofSession() == 3) && (actionID == 3)){
                if(i.getReqApprovedDoc() == null){
                    unDo = true;
                    System.out.println("Error!!");
                    
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "الرجاء إرفاق المستندات مع العقد أولاً", null));
                    FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("allMessages");
                }else {
                    i.setReqStatusId(new Status(4));
                }
            }else if((findRoleofSession() == 3) && (actionID == 7)){
                i.setReqStatusId(new Status(1));
            }
            
            else if((findRoleofSession()== 5) && (actionID == 1)){
                // Approved for Full paymenet
                i.setReqStatusId(new Status(5));
            } else if((findRoleofSession()== 5) && (actionID == 9)){
                // Approved for partial paymenet
                i.setReqStatusId(new Status(10));
            }else if((findRoleofSession()== 5) && (actionID == 11)){
                i.setReqStatusId(new Status(6));
                i.setReqToBeCollected(true);
            }
            
            else if((findRoleofSession()== 6) && (actionID == 5)){
                //Full payment is done
                i.setReqStatusId(new Status(13));
            } else if((findRoleofSession()== 6) && (actionID == 6)){
                if(i.getReqPaidByClient() == null){
                    unDo = true;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "الرجاء إرفاق إيصال الإيداع أولاًً", null));
                    FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("allMessages");
                }else {
                    i.setReqStatusId(new Status(11));
                }
            }else if((findRoleofSession()== 6) && (actionID == 8)){
                //Parital payment is done
                i.setReqStatusId(new Status(13));
            }
            
            else if(actionID == 2){
                i.setReqStatusId(new Status(8));
            }
            
            //Check if it's partial payment or not and update status
            int n = emf.createEntityManager().createNamedQuery("Loaner.paidForClient", Loaner.class)
                    .setParameter("clientNumber", current.getReqClientId().getClientNumber())
                    .getResultList().size();
            if(n > 0) {
             //This is partial payment process
               if((findRoleofSession() == 4) && (actionID == 10)){
                   i.setReqStatusId(new Status(12));
               } 
            }else {
             //This is a full payment process
                if((findRoleofSession() == 4) && (actionID == 10)){
                   i.setReqStatusId(new Status(7));
               }
            }
            if (!unDo){
                emf.createEntityManager().merge(i);
                utx.commit();
            }
       } catch(Exception e){
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "يوجد مشكله بالتواصل مع قاعدة البيانات", null));
           FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("scanMessages");
            
       }
       
       
           notes = "";
           actionID =0;
           refreshTable();
       return "";
    }
    
    public boolean showMobile(){
       try{ 
            current = (Request) getItems().getRowData();
            PersonalInfo tmp = emf.createEntityManager().createNamedQuery("PersonalInfo.findByClientNumber", PersonalInfo.class)
                    .setParameter("clientNumber", current.getReqClientId().getClientNumber())
                    .getSingleResult();
            if (tmp.getMobile() != 0){
                return true;
            } else {
                return false;
            }
       }catch(Exception e){
           return false;
       }
        
    }
    
    
    //Method to call ContractServlet and execute it to download Word.doc file
//    public String downloadContractFile(){
//        current = (Request) getItems().getRowData();
//        HttpServletRequest servlet = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//       HttpSession session = servlet.getSession(true);
//       session.setAttribute("currentRequest", current);
//        return "";
//    }
    
    //Hijri Date to String
    public String toHijriString(Date date){
        //Chronology hijri = IslamicChronology.getInstanceUTC();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy/MM/dd");
        LocalDate ld= LocalDate.fromDateFields(date);
        
        String tmp = fmt.print(ld);
        
        System.out.println("tmp: "+ tmp);
        return tmp;
    }
    
    public String downloadContractFile() throws Exception{
        
        Map<String,String> docxValue = new HashMap<>();
        Chronology hijri = IslamicChronology.getInstanceUTC();
            LocalDate todayHijri = LocalDate.now(hijri).plusDays(1);
            //current = (Request) getItems().getRowData();
            System.out.println("Curretn is: "+ current);
            PersonalInfo pi = emf.createEntityManager().createNamedQuery("PersonalInfo.findByClientNumber", PersonalInfo.class)
                    .setParameter("clientNumber", current.getReqClientId().getClientNumber())
                    .getSingleResult();
            EmploymentInfo ei = emf.createEntityManager().createNamedQuery("EmploymentInfo.findByClientNumber", EmploymentInfo.class)
                    .setParameter("clientNumber", current.getReqClientId().getClientNumber())
                    .getSingleResult();
            Request re = emf.createEntityManager().createNamedQuery("Request.findByClientNumber", Request.class)
                    .setParameter("clientNumber", current.getReqClientId().getClientNumber()).getSingleResult();
            
            String g = pi.getGender();
          if(g.equals("1")){
            this.genderString = "ذكر";
          } else if(g.equals("2")){
            this.genderString = "أنثى";
          }

          g = pi.getJob();
          if(g.equals("1")){
              this.jobString = "عسكري";
          }else if(g.equals("2")){
              this.jobString = "مدني";
          }else if(g.equals("3")){
              this.jobString = "أخرى";
          }

          g = pi.getStatus();
          if(g.equals("1")){
              this.statusString = "متزوج";
          }else if(g.equals("2")){
              this.statusString = "أعزب";
          }else if(g.equals("3")){
              this.statusString = "أخرى";
          }

          g = pi.getResedentType();
          if(g.equals("1")){
              this.resedentTypeString = "أملك منزلا أو شقه";
              docxValue.put("${owned}", "*");
              docxValue.put("${rented}","");
          }else if(g.equals("2")){
              this.resedentTypeString = "مستأجر شقه أو فيلا";
              docxValue.put("${owned}", "");
              docxValue.put("${rented}","*");
          }else if(g.equals("3")){
              this.resedentTypeString = "متوفر من جهه العمل";
              docxValue.put("${owned}", "");
              docxValue.put("${rented}","");
          }else if(g.equals("4")){
              this.resedentTypeString = "أسكن مع أخرين";
              docxValue.put("${owned}", "");
              docxValue.put("${rented}","");
          }
        Date d = new Date();
        SimpleDateFormat y = new SimpleDateFormat("YY");
        SimpleDateFormat m = new SimpleDateFormat("MM");
          
        docxValue.put("${name}", pi.getName());
        docxValue.put("${govId}", ""+ pi.getGovId());
        docxValue.put("${issueDate}",toHijriString(pi.getIdIssueDate()));
        docxValue.put("${birthday}", toHijriString(pi.getBirthday()));
        docxValue.put("${city}", pi.getResedentCity());
        docxValue.put("${status}", statusString);
        docxValue.put("${district}", pi.getResedentDistrict());
        docxValue.put("${streert}", pi.getResedentStreet());
        docxValue.put("${tel}", ""+ pi.getTel());
        docxValue.put("${employer}", ei.getEmployer());
        docxValue.put("${Addemployer}", ei.getEmployerTel() + "-" + ei.getEmployerTelExt());
        docxValue.put("${jobTitle}", ei.getJobTitle());
        docxValue.put("${salary}", ""+ ei.getSalary());
        docxValue.put("${unitPrice}", ""+String.format("%.2f", calculateUnitPrice(current.getReqClientId().getClientNumber())));
        docxValue.put("${qnty}", ""+calculateQnty(current.getReqClientId().getClientNumber()));
        docxValue.put("${totalPrice}", ""+ String.format("%.2f", newTotalPrice(calculateUnitPrice(current.getReqClientId().getClientNumber()) ,calculateQnty(current.getReqClientId().getClientNumber()) )));
        docxValue.put("${date}", toHijriString(todayHijri.toDate()));
        docxValue.put("${endDate}", ""+ toHijriString(todayHijri.plusDays(46).toDate()));
        docxValue.put("${reqNumber}", ""+re.getReqNumber());
        docxValue.put("${reqMonth}", ""+ m.format(d) );
        docxValue.put("${reqYear}", ""+ y.format(d));
        
        GenerateDocx.generateAndSendDocx("newContract.docx", docxValue);
        
        return "";
    }
    
    public int calculateQnty(int clientN){
        return ((int)Math.round(calculateTotalPrice(clientN))/100);
    }
    
    public float calculateUnitPrice(int clientN){
        
        return ((((profitMargin/100)+1) * calculateTotalPrice(clientN))/calculateQnty(clientN));
    }
    public float newTotalPrice(float value1, float value2){
        return value1 * value2;
    }
    public float calculateTotalPrice(int clientN){
        List<Loaner> l = emf.createEntityManager().createNamedQuery("Loaner.findByClientNumber", Loaner.class)
                .setParameter("clientNumber", clientN).getResultList();
        float total =0;
        for(Loaner tmp:l){
            total += tmp.getRemaining();
        }
        return total;
        
    }
    
    //Method to call FinalLetterServlet and execute it to download Word.doc file
//    public String downloadLetterFile(){
//        current = (Request) getItems().getRowData();
//        HttpServletRequest servlet = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//       HttpSession session = servlet.getSession(true);
//       session.setAttribute("currentLetterRequest", current);
//        return "";
//    }
    
    public String downloadLetterFile()throws Exception {
        System.out.println("Triggering Download!!");
        Map<String,String> docxValue = new HashMap<>();
        Chronology hijri = IslamicChronology.getInstanceUTC();
            LocalDate todayHijri = LocalDate.now(hijri).plusDays(1);
            Date date = new Date();
            SimpleDateFormat s = new SimpleDateFormat("yyyy/MM/dd");
                
            current = (Request) getItems().getRowData();
            PersonalInfo pi = emf.createEntityManager().createNamedQuery("PersonalInfo.findByClientNumber", PersonalInfo.class)
                    .setParameter("clientNumber", current.getReqClientId().getClientNumber())
                    .getSingleResult();
            EmploymentInfo ei = emf.createEntityManager().createNamedQuery("EmploymentInfo.findByClientNumber", EmploymentInfo.class)
                    .setParameter("clientNumber", current.getReqClientId().getClientNumber())
                    .getSingleResult();
            
            docxValue.put("${name}", pi.getName());
            docxValue.put("${govId}", ""+ pi.getGovId());
            docxValue.put("${employer}", ei.getEmployer());
            docxValue.put("${tel}", ""+ pi.getTel());
            docxValue.put("${date}", toHijriString(todayHijri.toDate()));
            docxValue.put("${gregorian}", s.format(date));
            
            GenerateDocx.generateAndSendDocx("letter.docx", docxValue);
            
            return "";
    }
    
    @PostConstruct
    public void initAll(){
        toAction = new ArrayList<>();
        populateActionsListForAll();
        refreshTable();
    }
    
    public String convert(String str)
	{
		StringBuffer ostr = new StringBuffer();
		for(int i=0; i<str.length(); i++) 
		{
			char ch = str.charAt(i);
			if ((ch >= 0x0020) && (ch <= 0x007e))	// Does the char need to be converted to unicode? 
			{
				ostr.append(ch);					// No.
			} else 									// Yes.
			{
	        	ostr.append("\\u") ;				// standard unicode format.
				String hex = Integer.toHexString(str.charAt(i) & 0xFFFF);	// Get hex value of the char. 
				for(int j=0; j<4-hex.length(); j++)	// Prepend zeros because unicode requires 4 digits
					ostr.append("0");
				ostr.append(hex.toLowerCase());		// standard unicode format.
				//ostr.append(hex.toLowerCase(Locale.ENGLISH));
			}
		}
	return (new String(ostr));		//Return the stringbuffer cast as a string.
	}
    
    public String sendSms(){
        Mobily m = new Mobily();
        PersonalInfo p = emf.createEntityManager().createNamedQuery("PersonalInfo.findByClientNumber", PersonalInfo.class)
                .setParameter("clientNumber", current.getReqClientId().getClientNumber())
                .getSingleResult();
        
        String senderName = "Loan system";
        String message = convert(smsMessage);
        //System.out.println(message);
        String number = "966"+ p.getMobile();
       
        m.sendMessage("966548388377", "baggio904", senderName, message, number);
        return "";
    }
    
    public void validateProfit(FacesContext context, UIComponent toValidate, Object value){
        try{
            int pValue = (int) value;
            //int length = String.valueOf(clientNumberValue).length();
            if((pValue == 0)){
               ((UIInput) toValidate).setValid(false);
               FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إدخال نسبة الربح ", null);
                       //new FacesMessage( "حجم الملف أكبر من المسموح به وهو ٢م.ب.");
               context.addMessage("marginMessages", message); 
            } 
        } catch(Exception e){
            ((UIInput) toValidate).setValid(false);
               FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إدخال أرقام في خانة نسبه الربح", null);
                       //new FacesMessage( "حجم الملف أكبر من المسموح به وهو ٢م.ب.");
               context.addMessage("marginMessages" , message);
        }
    }
    
    
}
