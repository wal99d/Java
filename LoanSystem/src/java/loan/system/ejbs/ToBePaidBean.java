/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package loan.system.ejbs;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
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
import loan.system.entities.Departement;
import loan.system.entities.FinancailInfo;
import loan.system.entities.Loaner;
import loan.system.entities.Request;
import loan.system.entities.User;
import loan.system.general.PaginationHelper;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author me
 */
@Named
@SessionScoped
public class ToBePaidBean implements Serializable{
    
    @PersistenceUnit(unitName = "LOAN_SYSTEMPU")
    EntityManagerFactory emf;
    EntityManager em;
    
    @Resource
    UserTransaction utx;
    
    private Loaner current;
    private Loaner selectedFile;
    private DataModel items=null;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private int clientNumber;
    private String loanerName;
    private float amount;
    private int duration;
    private float installment;
    private float remaining;

    public Loaner getSelectedFile() {
        return selectedFile;
    }

    public void setSelectedFile(Loaner selectedFile) {
        this.selectedFile = selectedFile;
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

    public float getInstallment() {
        return installment;
    }

    public void setInstallment(float installment) {
        this.installment = installment;
    }

    public float getRemaining() {
        return remaining;
    }

    public void setRemaining(float remaining) {
        this.remaining = remaining;
    }
    
    public int getClientNumber() {
        return clientNumber;
    }
    
    public ToBePaidBean(){
    }
    
    @PostConstruct
    public void initAll(){
        clientNumber = 0;
    }
    
    public void setClientNumber(int clientNumber) {
        this.clientNumber = clientNumber;
    }
    
    public Loaner getSelected(){
        if(current == null){
            current = new Loaner();
            selectedItemIndex = -1;
        }
        return current;
    }
    
    public PaginationHelper getPagination() {
       
//       HttpServletRequest servlet = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//       HttpSession session = servlet.getSession(false);
//       Request tmpRequest = (Request) session.getAttribute("loanerRequest");
//       clientNumber = tmpRequest.getReqClientId().getClientNumber();

        System.out.println("Loaner Client ID: "+ clientNumber); 
        
        
        if (pagination == null) {
            
            pagination = new PaginationHelper(6) {
                @Override
                public int getItemsCount() {
                    int i=0;
                   
                        i= emf.createEntityManager().createNamedQuery("Loaner.findByClientNumber", Loaner.class)
                            .setParameter("clientNumber", clientNumber)
                            .getResultList().size();
                        
                        
                        
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
    public void recreateModel() {
        items = null;
    }

    public void recreatePagination() {
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
          
        
        //Refresh DataTable
        recreatePagination();
        recreateModel();
        return "";
    }
    //Used to set isEditable for Loaner Table row
    public String editLoanerInfo()throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SecurityException, IllegalStateException, SystemException, NotSupportedException{
        //hideLoanerInputs = false;
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
            FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("paidMessages");
            
            return "";
        }
    }
    public String processItem(){
        current = (Loaner) getItems().getRowData();
      return "";
    }
    
    public String savePaidPaper() throws IOException, NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException{
        
        Loaner tmp = emf.createEntityManager().find(Loaner.class, current.getLoanerId());
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
            FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("paidMessages");
        } else {
            if(sourceFile.length()>2000000){
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "حجم الملف أكبر من المسموح به وهو٢ ميغا بايت", null));
               FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("paidMessages"); 
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
                FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("paidMessages");
            } else {
            utx.begin();
            tmp.setPaidPaper(tmpDoc2);
            tmp.setPaid(true);
            emf.createEntityManager().merge(tmp);
            utx.commit();
            //Remove it from both Server and FileSystem locations
            sourceFile.delete();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "تم إضافة الملف بنجاح", null));
            FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("paidMessages");
            }
            }
        }
        //Refresh Datatable
        this.items = null;
        recreatePagination();
        recreateModel();
        
        return "";
    }
    
    public String showOrignalPaidFile() throws IOException {
       selectedFile = (Loaner) getItems().getRowData();
       byte[] downloadFile = selectedFile.getPaidPaper();
       HttpServletRequest servlet = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
       HttpSession session = servlet.getSession(true);
       session.setAttribute("paidPaper", downloadFile);
        
       return "";
       
   }
    
    public String downloadFile() throws IOException {
       
       
       byte[] downloadFile = selectedFile.getPaidPaper();
       FacesContext fc = FacesContext.getCurrentInstance();
       ExternalContext ec = fc.getExternalContext();
       HttpServletResponse response = (HttpServletResponse) ec.getResponse();
       
       response.reset();
       response.setContentType("application/pdf");
       response.setHeader("Content-disposition", "attachment; filename=\"downlaodedPaidFile.pdf\"");
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
    
}
