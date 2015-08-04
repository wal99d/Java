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
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import loan.system.entities.Loaner;
import loan.system.entities.Request;
import loan.system.general.PaginationHelper;

/**
 *
 * @author me
 */
@Named
@SessionScoped
public class LoanerInfoBean implements Serializable{
    
    @PersistenceUnit(unitName = "LOAN_SYSTEMPU")
    EntityManagerFactory emf;
    EntityManager em;
    
    private Loaner current;
    private Loaner selectedFile;
    private DataModel items=null;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private int clientNumber;
    private String loanerName;
    private int amount;
    private int duration;
    private int installment;
    private int remaining;

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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getInstallment() {
        return installment;
    }

    public void setInstallment(int installment) {
        this.installment = installment;
    }

    public int getRemaining() {
        return remaining;
    }

    public void setRemaining(int remaining) {
        this.remaining = remaining;
    }
    
    public int getClientNumber() {
        return clientNumber;
    }
    
    public LoanerInfoBean(){
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
                    try {
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
       response.setHeader("Content-disposition", "attachment; filename=\"downlaodedPaidArchivedFile.pdf\"");
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
