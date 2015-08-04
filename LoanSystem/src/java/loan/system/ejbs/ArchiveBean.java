/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package loan.system.ejbs;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
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
import javax.transaction.UserTransaction;
import loan.system.entities.EmploymentInfo;
import loan.system.entities.FinancailInfo;
import loan.system.entities.Loaner;
import loan.system.entities.PersonalInfo;
import loan.system.entities.Request;
import loan.system.entities.User;
import loan.system.general.PaginationHelper;

/**
 *
 * @author me
 */

@Named
@SessionScoped
public class ArchiveBean implements Serializable{
    @PersistenceUnit(unitName = "LOAN_SYSTEMPU")
    EntityManagerFactory emf;
    EntityManager em;
    
    @Resource
    UserTransaction utx;
    
    @Inject
    LoanerInfoArchivedBean liab;
    
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private DataModel items=null;
    private Request current;
    private Request selectedFile;
    private String historyNotes;
    private boolean isUser;
    private boolean isManager;
    private int clientMobile;
    private String searchTerm;
    private PersonalInfo clientPI;
    private EmploymentInfo clientEI;
    private FinancailInfo clientFI;
    private String genderString;
    private String jobString;
    private String statusString;
    private String resedentTypeString;
    private boolean isClientFI;
    private boolean isEmployee;
    private boolean isDirector;
    private boolean isCoordinator;

    public Request getSelectedFile() {
        return selectedFile;
    }

    public void setSelectedFile(Request selectedFile) {
        this.selectedFile = selectedFile;
    }
    
    public ArchiveBean() {
    }
    
    @PostConstruct
    public void initAll() {
        searchTerm = "";
    }
    
    public boolean isIsCoordinator() {
        return isCoordinator;
    }

    public void setIsCoordinator(boolean isCoordinator) {
        this.isCoordinator = isCoordinator;
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

    public String getGenderString() {
        return genderString;
    }

    public void setGenderString(String genderString) {
        this.genderString = genderString;
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

    public String getResedentTypeString() {
        return resedentTypeString;
    }

    public void setResedentTypeString(String resedentTypeString) {
        this.resedentTypeString = resedentTypeString;
    }

    public boolean isIsClientFI() {
        return isClientFI;
    }

    public void setIsClientFI(boolean isClientFI) {
        this.isClientFI = isClientFI;
    }
    
    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }
    
    public int getClientMobile() {
        return clientMobile;
    }

    public void setClientMobile(int clientMobile) {
        this.clientMobile = clientMobile;
    }
    
    public String getHistoryNotes() {
        return historyNotes;
    }

    public void setHistoryNotes(String historyNotes) {
        this.historyNotes = historyNotes;
    }

    public boolean isIsUser() {
        return isUser;
    }

    public void setIsUser(boolean isUser) {
        this.isUser = isUser;
    }

    public boolean isIsManager() {
        return isManager;
    }

    public void setIsManager(boolean isManager) {
        this.isManager = isManager;
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
                    
                        return countAllArchives();
                    
                }

                @Override
                public DataModel createPageDataModel() {
                    List<Request> range = new ArrayList<>();
                    int maxResult = (getPageFirstItem() + getPageSize()) - getPageFirstItem() ;
                    HashMap<String,Object> filters = new HashMap<>();
                    
                    //Check if filter is not enabled
                    
                    if(searchTerm.equals("")){
                        List<Request> tmp = emf.createEntityManager().createNamedQuery("Request.ArchiveForAll", Request.class)
                                    .setMaxResults(maxResult)
                                    .setFirstResult(getPageFirstItem())
                                    .getResultList();
                            range = tmp;
                        
                    } else if (searchTerm.getClass().equals(Integer.class)) {
                          System.out.println("Searching for Integer values!!");
                          filters.put("subject", searchTerm);
                          Iterator it = filters.entrySet().iterator();
                          Map.Entry pairs;

                          while(it.hasNext()){
                              pairs = (Map.Entry) it.next();
                              
                                List<Request> tmp = emf.createEntityManager().createNamedQuery("Request.ArchiveViewForAll", Request.class)
                                        .setParameter("subject", Integer.parseInt( "%"+pairs.getValue()+"%"))
                                        .setMaxResults(maxResult)
                                        .setFirstResult(getPageFirstItem())
                                        .getResultList();
                                range = tmp;
                                //count= range.size();
                              
                              }
                    }
                    else {
                          filters.put("subject", searchTerm);
                          Iterator it = filters.entrySet().iterator();
                          Map.Entry pairs;

                          while(it.hasNext()){
                              pairs = (Map.Entry) it.next();
                              
                                List<Request> tmp = emf.createEntityManager().createNamedQuery("Request.ArchiveViewForAll", Request.class)
                                        .setParameter("subject", "%"+pairs.getValue()+"%")
                                        .setMaxResults(maxResult)
                                        .setFirstResult(getPageFirstItem())
                                        .getResultList();
                                range = tmp;
                                //count= range.size();
                             

                          }
                      
                     
                      }
                    
                    
                    
                     
                    
                        return new ListDataModel(range);
                    
                }
            };
        }
        return pagination;
    }
    
    public int countAllArchives(){
        List<Request> range = new ArrayList<>();
                    int count = 0 ;
                    HashMap<String,Object> filters = new HashMap<>();
                    
                    //Check if filter is not enabled
                    if(searchTerm.equals("")){
                        List<Request> tmp = emf.createEntityManager().createNamedQuery("Request.ArchiveForAll", Request.class)
                                    .getResultList();
                            range = tmp;
                            count = range.size();
                        
                    } else if (searchTerm.getClass().equals(Integer.class)) {
                          System.out.println("Searching for Integer values!!");
                          filters.put("subject", searchTerm);
                          Iterator it = filters.entrySet().iterator();
                          Map.Entry pairs;

                          while(it.hasNext()){
                              pairs = (Map.Entry) it.next();
                              
                                List<Request> tmp = emf.createEntityManager().createNamedQuery("Request.ArchiveViewForAll", Request.class)
                                        .setParameter("subject", Integer.parseInt( "%"+pairs.getValue()+"%"))
                                        .getResultList();
                                range = tmp;
                                count = range.size();
                              
                              }
                    }
                    else {
                          filters.put("subject", searchTerm);
                          Iterator it = filters.entrySet().iterator();
                          Map.Entry pairs;

                          while(it.hasNext()){
                              pairs = (Map.Entry) it.next();
                              
                                List<Request> tmp = emf.createEntityManager().createNamedQuery("Request.ArchiveViewForAll", Request.class)
                                        .setParameter("subject", "%"+pairs.getValue()+"%")
                                        .getResultList();
                                range = tmp;
                                count= range.size();
                             

                          }
                      
                     
                      }
                    
                    
                    return count;
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
        System.out.println("Triggering Next!!");
        getPagination().nextPage();
        recreateModel();
        return "";
    }
    
    public void doSearch() {
        recreatePagination();
        recreateModel();
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
            int tmpRole = emf.createEntityManager().createNamedQuery("User.findByUserName", User.class)
                    .setParameter("userName", userName).getSingleResult().getUserRoleId().getRoleId();
            targetSession.setAttribute("role", tmpRole);
            return (Integer) targetSession.getAttribute("role");
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
    public void prepareHistoryViewofText(){
        Request tmp = (Request) getItems().getRowData();
        String oldMessage = tmp.getHistoryNotes();
        historyNotes =  oldMessage;
    }
    
    public String refreshTable(){
        this.items = null;
        recreatePagination();
        recreateModel();
        
        return "";
    }
    
    
    
    public String processItem(){
      current = (Request) getItems().getRowData();
        prepareHistoryViewofText();
        
//       HttpServletRequest servlet = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//       HttpSession session = servlet.getSession(true);
//       session.setAttribute("loanerArchivedRequest", current);
//     
        liab.setClientNumber(current.getReqClientId().getClientNumber());
        
      if(findRoleofSession() == 4) {
          isUser = true;
          isManager = false;
          isEmployee = false;
          isDirector = false;
          isCoordinator = false;
       } else if(findRoleofSession() == 2){
          isManager = true;
          isUser = false;
          isEmployee = false;
          isDirector = false;
          isCoordinator = false;
       } else if(findRoleofSession() == 3){
          isEmployee = true;
          isManager = false;
          isUser = false;
          isDirector = false;
          isCoordinator = false;
       } else if(findRoleofSession() == 5){
          isEmployee = true;
          isManager = false;
          isUser = false;
          isDirector = true;
          isCoordinator = false;
       } else if(findRoleofSession() == 6){
          isEmployee = true;
          isManager = false;
          isUser = false;
          isDirector = false;
          isCoordinator = true;
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
      
      //Refreshing archvied financail datatable
      
      liab.recreatePagination();
      liab.recreateModel();
      
      return "";
   }
    
    public String showOrignalFile() throws IOException {
       current = (Request) getItems().getRowData();
       byte[] downloadFile = current.getReqDoc();
       HttpServletRequest servlet = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
       HttpSession session = servlet.getSession(true);
       session.setAttribute("doc", downloadFile);
        
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
    public String downloadContractFile(){
        current = (Request) getItems().getRowData();
        HttpServletRequest servlet = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
       HttpSession session = servlet.getSession(true);
       session.setAttribute("currentRequest", current);
        return "";
    }
    
    public String downloadFile() throws IOException {
       
       
       byte[] downloadFile = current.getReqDoc();
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
    public String showFinalLetterFile() throws IOException {
       selectedFile = (Request) getItems().getRowData();
       byte[] downloadFile = selectedFile.getReqColPapers();
       HttpServletRequest servlet = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
       HttpSession session = servlet.getSession(true);
       session.setAttribute("colPaper", downloadFile);
        
       return "";
       
   }
    
    public String downloadFinalLetterFile() throws IOException {
       
       
       byte[] downloadFile = selectedFile.getReqColPapers();
       FacesContext fc = FacesContext.getCurrentInstance();
       ExternalContext ec = fc.getExternalContext();
       HttpServletResponse response = (HttpServletResponse) ec.getResponse();
       
       response.reset();
       response.setContentType("application/pdf");
       response.setHeader("Content-disposition", "attachment; filename=\"downlaodedFinalLetterFile.pdf\"");
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
}
