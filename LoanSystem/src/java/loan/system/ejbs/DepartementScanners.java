/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package loan.system.ejbs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;
import loan.system.entities.Departement;
import loan.system.entities.Request;
import loan.system.general.PaginationHelper;

/**
 *
 * @author me
 */

@Named("deptScanner")
@SessionScoped

public class DepartementScanners implements Serializable{
    @PersistenceUnit(unitName = "LOAN_SYSTEMPU")
    EntityManagerFactory emf;
    EntityManager em;
    
    @Resource
    UserTransaction utx;
    
    private PaginationHelper pagination;
    private DataModel items=null;
    private Departement current;
    private int selectedItemIndex;
    private String deptName;
    private String deptScannerDevice;
    private String deptScannerFileName;
    private boolean showInputs;

    public boolean isShowInputs() {
        return showInputs;
    }

    public void setShowInputs(boolean showInputs) {
        this.showInputs = showInputs;
    }
    
    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptScannerDevice() {
        return deptScannerDevice;
    }

    public void setDeptScannerDevice(String deptScannerDevice) {
        this.deptScannerDevice = deptScannerDevice;
    }

    public String getDeptScannerFileName() {
        return deptScannerFileName;
    }

    public void setDeptScannerFileName(String deptScannerFileName) {
        this.deptScannerFileName = deptScannerFileName;
    }
    
    public DepartementScanners(){
    }
    
    public Departement getSelected(){
        if(current == null){
            current = new Departement();
            selectedItemIndex = -1;
        }
        return current;
    }
    public PaginationHelper getPagination() {
        
        if (pagination == null) {
            
            pagination = new PaginationHelper(6) {
                @Override
                public int getItemsCount() {
                    return emf.createEntityManager().createNamedQuery("Departement.findAll", Departement.class)
                            .getResultList().size();
                    
                }

                @Override
                public DataModel createPageDataModel() {
                    List<Departement> range = new ArrayList<>();
                    int maxResult = (getPageFirstItem() + getPageSize()) - getPageFirstItem() ;
                     
                    range = emf.createEntityManager().createNamedQuery("Departement.findAll", Departement.class)
                            .setFirstResult(getPageFirstItem())
                            .setMaxResults(maxResult)
                            .getResultList();
                    
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
    
    @PostConstruct 
    public void initAll(){
        showInputs =true;
    }
    
    
    
    //Scanner file name Validation
    public void validateFileName(FacesContext context, UIComponent toValidate, Object value){
        String fileNameValue = (String) value;
        if(fileNameValue.equals("")){
           ((UIInput) toValidate).setValid(false);
           FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إدخال إسم الملف", null);
                   //new FacesMessage( "حجم الملف أكبر من المسموح به وهو ٢م.ب.");
           context.addMessage(toValidate.getClientId(context), message); 
        }
    }
    
    //Used to remove the scanner and its file from dept
    public String removeScannerfromDept(){
        current = (Departement) getItems().getRowData();
        try{
            utx.begin();
            Departement tmp = emf.createEntityManager().find(Departement.class, current.getDeptId());
            tmp.setDeptScanner("");
            tmp.setDeptScannerFileName("");
            emf.createEntityManager().merge(tmp);
            utx.commit();
            
            //Refresh Deivces Table
            recreatePagination();
            recreateModel();
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "تم التحديث بنجاح", null));
            FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("devicesMessages");
            
        } catch(Exception e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "يوجد مشكله بالاتصال مع قاعدة البيانات", null));
            FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("devicesMessages");
        }
        return "";
    }
    
    //Used to set isEditable for Loaner Table row
    public String editScannerInfo(){
        showInputs = false;
        current = (Departement) getItems().getRowData();
        try{
            utx.begin();
            Departement s = emf.createEntityManager().find(Departement.class, current.getDeptId());
            s.setIsEditable(true);
            emf.createEntityManager().merge(s);
            utx.commit();

            //Refresh Datatable
            recreatePagination();
            recreateModel();

            this.deptName = s.getDeptName();
            this.deptScannerDevice = s.getDeptScanner();
            this.deptScannerFileName = s.getDeptScannerFileName();
            
        
        } catch(Exception e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "يوجد مشكله بالاتصال مع قاعدة البيانات", null));
            FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("devicesMessages");
        }
        
        return "";
    }
    
    //To save new scanner and its file to dept
    public String saveNewScannerInfo(){
        current = (Departement) getItems().getRowData();
        try{
            utx.begin();
            Departement s = emf.createEntityManager().find(Departement.class, current.getDeptId());
            s.setIsEditable(false);
            s.setDeptScanner(deptScannerDevice);
            s.setDeptScannerFileName(deptScannerFileName);
            emf.createEntityManager().merge(s);
            utx.commit();

            //Refresh Datatable
            recreatePagination();
            recreateModel();
            
            showInputs = true;
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "تم التحديث بنجاح", null));
            FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("devicesMessages");
        
        } catch(Exception e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "يوجد مشكله بالاتصال مع قاعدة البيانات", null));
            FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("devicesMessages");
        }
        
        return "";
    }
}
