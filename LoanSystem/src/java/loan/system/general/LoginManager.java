/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package loan.system.general;

import loan.system.entities.User;
import java.io.IOException;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import loan.system.entities.Departement;
import loan.system.entities.Role;
import org.joda.time.Chronology;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.chrono.IslamicChronology;

/**
 *
 * @author me
 */
@Named
@SessionScoped
public class LoginManager implements Serializable{
    private String userName;
    private String password;
    private String newUserName;
    private String newPassword;
    private String oldPassword;
    private String prePassword;
    private String WelcomeMessage;
    private User current;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private DataModel items=null;
    private int DeptId;
    private int RoleId;
    private List<Departement> userDept;
    private List<Role> userRole;
    private boolean logged;
    private boolean renewPassword;
    private boolean showNewPasswordForm;
    private boolean forgotPassword;
   
    @PersistenceUnit(unitName="LOAN_SYSTEMPU")
    EntityManagerFactory emf;
    
    @Resource
    UserTransaction utx;

    public boolean isForgotPassword() {
        return forgotPassword;
    }

    public void setForgotPassword(boolean forgotPassword) {
        this.forgotPassword = forgotPassword;
    }
    
    public boolean isShowNewPasswordForm() {
        return showNewPasswordForm;
    }

    public void setShowNewPasswordForm(boolean showNewPasswordForm) {
        this.showNewPasswordForm = showNewPasswordForm;
    }
    
    public boolean isRenewPassword() {
        return renewPassword;
    }

    public void setRenewPassword(boolean renewPassword) {
        this.renewPassword = renewPassword;
    }
    
    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getPrePassword() {
        return prePassword;
    }

    public void setPrePassword(String prePassword) {
        this.prePassword = prePassword;
    }
    
    public int getDeptId() {
        return DeptId;
    }

    public void setDeptId(int DeptId) {
        this.DeptId = DeptId;
    }

    public int getRoleId() {
        return RoleId;
    }

    public void setRoleId(int RoleId) {
        this.RoleId = RoleId;
    }
    
    public List<Departement> getUserDept() {
        return userDept;
    }

    public void setUserDept(List<Departement> userDept) {
        this.userDept = userDept;
    }

    public List<Role> getUserRole() {
        return userRole;
    }

    public void setUserRole(List<Role> userRole) {
        this.userRole = userRole;
    }
    
    public String getNewUserName() {
        return newUserName;
    }

    public void setNewUserName(String newUserName) {
        this.newUserName = newUserName;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    
    public LoginManager() {
    }

    public String getWelcomeMessage() {
        return WelcomeMessage;
    }

    public void setWelcomeMessage(String WelcomeMessage) {
        this.WelcomeMessage = WelcomeMessage;
    }
    
    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }
 
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public User getSelected(){
        if(current == null){
            current = new User();
            selectedItemIndex = -1;
        }
        return current;
    }
    public PaginationHelper getPagination() {
        
        if (pagination == null) {
            
            pagination = new PaginationHelper(6) {
                @Override
                public int getItemsCount() {
                    return emf.createEntityManager().createNamedQuery("User.findAll", User.class).getResultList().size();
                }
                
                @Override
                public DataModel createPageDataModel() {
                    List<User> range = new ArrayList<>();
                    int maxResult = (getPageFirstItem() + getPageSize()) - getPageFirstItem() ;
                    
                        range = emf.createEntityManager().createNamedQuery("User.findAll", User.class)
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
    public void initLoginManager() {
        LoginManager lm = new LoginManager();
        userDept = new ArrayList<>();
        userRole = new ArrayList<>();
        
        userDept = emf.createEntityManager().createNamedQuery("Departement.findAll", Departement.class)
                .getResultList();
        userRole = emf.createEntityManager().createNamedQuery("Role.findAll", Role.class)
                .getResultList();
        
        this.userName ="";
        this.password ="";
   
        
    }
    
    public String unlockUser(){
        current = (User) getItems().getRowData();
        try {
        User tmp = emf.createEntityManager().find(User.class, current.getUserId());
        utx.begin();
        tmp.setUserPassword(hashPssword("123"));
        tmp.setUserForgot(false);
        emf.createEntityManager().merge(tmp);
        utx.commit();
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "تم إعادة تفعيل المستخدم بنجاح", null));
        FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("usersMessages");
        
        //Refresh Users Table
        recreatePagination();
        recreateModel();
        }catch(Exception e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "لايوجد إتصال مع قاعدة البيانات", null));
            FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("usersMessages");
        }
        
        return "";
    }
    
    public int findUserIDofSession(){
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        HttpSession targetSession = (HttpSession) request.getSession(false);
        return (Integer) targetSession.getAttribute("userId");
    }
    
    private String unhashPassword(String hashedPassword){
        String salt = "W@l99d904";
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update((hashedPassword+salt).getBytes());
            byte byteData[] = md.digest();

                //Convert from byte to HEX
                StringBuffer hexString = new StringBuffer();
                for (int i=0;i<byteData.length;i++) {
                    String hex=Integer.toHexString(0xff & byteData[i]);
                    if(hex.length()==1) hexString.append('0');
                    hexString.append(hex);
                }
                return hexString.toString();
                
        }catch(NoSuchAlgorithmException e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "لايوجد نظام حمايه في هذا الخادم الرجاء التواصل مع الدعم الفني", null));
            FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("usersMessages");
            return "";
        }
    }
    
    /*
     It will check for the user based on his name, password and role
    */
    public boolean checkUserByNamePasswordAndRole(String userName, String password, int role){
        int userIsThere = emf.createEntityManager().createNamedQuery("User.getUserByNamePasswordAndRoleID", User.class)
                .setParameter("userName", this.userName)
                .setParameter("userPassword", unhashPassword(this.password))
                .setParameter("roleId", role)
                .getResultList().size();
        
        if(userIsThere > 0){
            return true;
        } else {
            return false;
        }
    }
    
    public void validatUserName(FacesContext context, UIComponent toValidate, Object value){
        String tmp = (String) value;
        if( tmp.equals("")){
            ((UIInput) toValidate).setValid(false);
            context.addMessage(toValidate.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, "الرجاء تعبئة حقل اسم المستخدم", null));
        }
    }
    
    public void validatPassword(FacesContext context, UIComponent toValidate, Object value){
        String tmp = (String) value;
        if( tmp.equals("")){
            ((UIInput) toValidate).setValid(false);
            context.addMessage(toValidate.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, "الرجاء تعبئة حقل كلمة المرور", null));
        }
    }
    
    public String login() throws IOException, ServletException {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        HttpSession session; 
        Chronology hijri = IslamicChronology.getInstance(DateTimeZone.forID("Asia/Riyadh"));
        LocalDateTime todayHijri = LocalDateTime.now(hijri).plusDays(1);
        
        if ((checkUserByNamePasswordAndRole(this.userName, this.password, 1))){
                    session = request.getSession(true);
                    session.setAttribute("user", userName);
                    session.setAttribute("role", 1);
                    int deptId = emf.createEntityManager().createNamedQuery("User.findByUserName", User.class).
                            setParameter("userName", userName).getSingleResult().getUserDeptId().getDeptId();
                    session.setAttribute("deptId", deptId);
                    int userId = emf.createEntityManager().createNamedQuery("User.findByUserName", User.class)
                            .setParameter("userName", userName).getSingleResult().getUserId();
                    session.setAttribute("userId", userId);
                    logged = true;
                    User tmpUser = emf.createEntityManager().createNamedQuery("User.findByUserId", User.class)
                            .setParameter("userId", userId)
                            .getSingleResult();
                    try{
                        utx.begin();
                        tmpUser.setCannotRemove(true);
                        tmpUser.setLastLogin(todayHijri.toDate());
                        emf.createEntityManager().merge(tmpUser);
                        utx.commit();
                    }catch(Exception e){
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "لايوجد إتصال مع قاعدة البيانات", null));
                        FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("usersMessages");
                    }
                    
                    context.getExternalContext().redirect("/LOAN_SYSTEM/faces/admin/main.xhtml");
                    WelcomeMessage = emf.createEntityManager().createNamedQuery("User.findByUserId", User.class).setParameter("userId", findUserIDofSession()).getSingleResult().getUserName();
                    return "";
                } else if ((checkUserByNamePasswordAndRole(this.userName, this.password, 2))){
                    session = request.getSession(true);
                    session.setAttribute("user", userName);
                    session.setAttribute("role", 2);
                    int deptId = emf.createEntityManager().createNamedQuery("User.findByUserName", User.class).
                            setParameter("userName", userName).getSingleResult().getUserDeptId().getDeptId();
                    session.setAttribute("deptId", deptId);
                    int userId = emf.createEntityManager().createNamedQuery("User.findByUserName", User.class)
                            .setParameter("userName", userName).getSingleResult().getUserId();
                    session.setAttribute("userId", userId);
                    logged = true;
                    User tmpUser = emf.createEntityManager().createNamedQuery("User.findByUserId", User.class)
                            .setParameter("userId", userId)
                            .getSingleResult();
                    try{
                        utx.begin();
                        tmpUser.setLastLogin(todayHijri.toDate());
                        emf.createEntityManager().merge(tmpUser);
                        utx.commit();
                    }catch(Exception e){
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "لايوجد إتصال مع قاعدة البيانات", null));
                        FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("usersMessages");
                    }
                    context.getExternalContext().redirect("/LOAN_SYSTEM/faces/manager/main.xhtml");
                    WelcomeMessage = emf.createEntityManager().createNamedQuery("User.findByUserId", User.class).setParameter("userId", findUserIDofSession()).getSingleResult().getUserName();
                    //response.sendRedirect("/DArch/faces/manager/main.xhtml");
                    return "";
                } else if ((checkUserByNamePasswordAndRole(this.userName, this.password, 3))){
                    session = request.getSession(true);
                    session.setAttribute("user", userName);
                    session.setAttribute("role", 3);
                    int deptId = emf.createEntityManager().createNamedQuery("User.findByUserName", User.class).
                            setParameter("userName", userName).getSingleResult().getUserDeptId().getDeptId();
                    session.setAttribute("deptId", deptId);
                    int userId = emf.createEntityManager().createNamedQuery("User.findByUserName", User.class)
                            .setParameter("userName", userName).getSingleResult().getUserId();
                    session.setAttribute("userId", userId);
                    logged = true;
                    User tmpUser = emf.createEntityManager().createNamedQuery("User.findByUserId", User.class)
                            .setParameter("userId", userId)
                            .getSingleResult();
                    try{
                        utx.begin();
                        tmpUser.setCannotRemove(false);
                        tmpUser.setLastLogin(todayHijri.toDate());
                        emf.createEntityManager().merge(tmpUser);
                        utx.commit();
                    }catch(Exception e){
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "لايوجد إتصال مع قاعدة البيانات", null));
                        FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("usersMessages");
                    }
                    context.getExternalContext().redirect("/LOAN_SYSTEM/faces/employee/main.xhtml");
                    WelcomeMessage = emf.createEntityManager().createNamedQuery("User.findByUserId", User.class).setParameter("userId", findUserIDofSession()).getSingleResult().getUserName();
                    return "";
                } else if((checkUserByNamePasswordAndRole(this.userName, this.password, 4))){
                    session = request.getSession(true);
                    session.setAttribute("user", userName);
                    session.setAttribute("role", 4);
                    int deptId = emf.createEntityManager().createNamedQuery("User.findByUserName", User.class).
                            setParameter("userName", userName).getSingleResult().getUserDeptId().getDeptId();
                    session.setAttribute("deptId", deptId);
                    int userId = emf.createEntityManager().createNamedQuery("User.findByUserName", User.class)
                            .setParameter("userName", userName).getSingleResult().getUserId();
                    session.setAttribute("userId", userId);
                    logged = true;
                    User tmpUser = emf.createEntityManager().createNamedQuery("User.findByUserId", User.class)
                            .setParameter("userId", userId)
                            .getSingleResult();
                    try{
                        utx.begin();
                        tmpUser.setCannotRemove(false);
                        tmpUser.setLastLogin(todayHijri.toDate());
                        emf.createEntityManager().merge(tmpUser);
                        utx.commit();
                    }catch(Exception e){
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "لايوجد إتصال مع قاعدة البيانات", null));
                        FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("usersMessages");
                    }
                    context.getExternalContext().redirect("/LOAN_SYSTEM/faces/user/main.xhtml");
                    WelcomeMessage = emf.createEntityManager().createNamedQuery("User.findByUserId", User.class).setParameter("userId", findUserIDofSession()).getSingleResult().getUserName();
                    return "";
                }else if((checkUserByNamePasswordAndRole(this.userName, this.password, 5))){
                    session = request.getSession(true);
                    session.setAttribute("user", userName);
                    session.setAttribute("role", 5);
                    int deptId = emf.createEntityManager().createNamedQuery("User.findByUserName", User.class).
                            setParameter("userName", userName).getSingleResult().getUserDeptId().getDeptId();
                    session.setAttribute("deptId", deptId);
                    int userId = emf.createEntityManager().createNamedQuery("User.findByUserName", User.class)
                            .setParameter("userName", userName).getSingleResult().getUserId();
                    session.setAttribute("userId", userId);
                    logged = true;
                    User tmpUser = emf.createEntityManager().createNamedQuery("User.findByUserId", User.class)
                            .setParameter("userId", userId)
                            .getSingleResult();
                    try{
                        utx.begin();
                        tmpUser.setCannotRemove(false);
                        tmpUser.setLastLogin(todayHijri.toDate());
                        emf.createEntityManager().merge(tmpUser);
                        utx.commit();
                    }catch(Exception e){
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "لايوجد إتصال مع قاعدة البيانات", null));
                        FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("usersMessages");
                    }
                    context.getExternalContext().redirect("/LOAN_SYSTEM/faces/ceo/main.xhtml");
                    WelcomeMessage = emf.createEntityManager().createNamedQuery("User.findByUserId", User.class).setParameter("userId", findUserIDofSession()).getSingleResult().getUserName();
                    return "";
                }else if((checkUserByNamePasswordAndRole(this.userName, this.password, 6))){
                    session = request.getSession(true);
                    session.setAttribute("user", userName);
                    session.setAttribute("role", 6);
                    int deptId = emf.createEntityManager().createNamedQuery("User.findByUserName", User.class).
                            setParameter("userName", userName).getSingleResult().getUserDeptId().getDeptId();
                    session.setAttribute("deptId", deptId);
                    int userId = emf.createEntityManager().createNamedQuery("User.findByUserName", User.class)
                            .setParameter("userName", userName).getSingleResult().getUserId();
                    session.setAttribute("userId", userId);
                    logged = true;
                    User tmpUser = emf.createEntityManager().createNamedQuery("User.findByUserId", User.class)
                            .setParameter("userId", userId)
                            .getSingleResult();
                    try{
                        utx.begin();
                        tmpUser.setCannotRemove(false);
                        tmpUser.setLastLogin(todayHijri.toDate());
                        emf.createEntityManager().merge(tmpUser);
                        utx.commit();
                    }catch(Exception e){
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "لايوجد إتصال مع قاعدة البيانات", null));
                        FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("usersMessages");
                    }
                    context.getExternalContext().redirect("/LOAN_SYSTEM/faces/coordinator/main.xhtml");
                    WelcomeMessage = emf.createEntityManager().createNamedQuery("User.findByUserId", User.class).setParameter("userId", findUserIDofSession()).getSingleResult().getUserName();
                    return "";
                } 
                else {
                    logged = false;
                    System.out.println("Logged in: " + logged);
                    context.getExternalContext().redirect("/LOAN_SYSTEM/faces/errorLogin.xhtml");
                    return "";
                }
        
        
    }
    
    public void logout() throws ServletException, IOException{
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        
        HttpSession session = (HttpSession) request.getSession(false);
        session.invalidate();
        this.logged = false;
        context.getExternalContext().redirect("/LOAN_SYSTEM/faces/login.xhtml?faces-redirect=true");
    }
    
    public String deleteUser(){
        EntityManager em = emf.createEntityManager();
        current = (User) getItems().getRowData();
        try{
            utx.begin();
            User u = em.find(User.class, current.getUserId());
            User tmp = em.merge(u);
            em.remove(tmp);
            em.joinTransaction();
            em.flush();
            utx.commit();
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "تم حذف المستخدم بنجاح", null));
            FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("usersMessages");
            
            recreatePagination();
            recreateModel();
            
           return "";
        }catch(Exception e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "لايوجد إتصال مع قاعدة البيانات", null));
            FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("usersMessages");
            
            return "";
        }
    }
    public String forgotMyPassword(){
        try{
            User tmp = emf.createEntityManager().createNamedQuery("User.findByUserName", User.class)
                .setParameter("userName", userName)
                .getSingleResult();
        
        try{
            utx.begin();
            tmp.setUserForgot(true);
            emf.createEntityManager().merge(tmp);
            utx.commit();
            
            this.userName ="";
            this.password ="";
   
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "الرجاء التواصل مع مدير النظام لكي يتم تفعيل حسابكم", null));
            FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("loginMessages");
            
            showNewPasswordForm = false;
            forgotPassword = false;
            renewPassword = false;
            
        }catch(Exception e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "لايوجد إتصال مع قاعدة البيانات", null));
            FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("loginMessages");
            
        }
        }catch(NoResultException r){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "المستخدم غير موجود بالنظام ", null));
            FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("loginMessages");
        }
        
        return "";
    }
    
    public String displayNewPasswordForm(){
        showNewPasswordForm = true;
        renewPassword = true;
        
        this.userName = "";
        this.oldPassword = "";
        this.newPassword = "";
        
        return "";
    }
    
    public String displayForgotPasswordForm(){
    
        forgotPassword = true;
        showNewPasswordForm =false;
        renewPassword = false;
        
        this.userName= "";
        
        return "";
    }
    
    public String updatePassword(){
        Chronology hijri = IslamicChronology.getInstance(DateTimeZone.forID("Asia/Riyadh"));
        LocalDate todayHijri = LocalDate.now(hijri).plusDays(1);
        User tmp = null;
        List tmpUser = emf.createEntityManager().createNamedQuery("User.findByUserName", User.class)
                .setParameter("userName", userName)
                .getResultList();
        
        if(tmpUser.isEmpty()){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "إسم المستخدم غير موجود بالنظام", null));
            FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("loginMessages");
        }else {
           tmp = (User) tmpUser.get(0);
           if(tmp.getUserPassword().equals(unhashPassword(oldPassword))){
            try{
                utx.begin();
                
                String tmpPassword = hashPssword(prePassword);
                tmp.setUserPassword(tmpPassword);
                tmp.setLastPasswordUpdate(todayHijri.toDate());
                emf.createEntityManager().merge(tmp);
                utx.commit();

                this.oldPassword = "";
                this.prePassword = "";

                showNewPasswordForm = false;
                renewPassword = false;

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "تم تحديث كلمة المررو بنجاح", null));
                FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("loginMessages");

            }catch(Exception e){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "لايوجد إتصال مع قاعدة البيانات", null));
                FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("loginMessages");

            }
            } else {
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "كلمة المرور القديمه غير مطابقه", null));
               FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("loginMessages");
                    }
        }
        
        return "";
    }
    private String hashPssword(String nPassword){
        String tmpPassword = new String();
        String salt = "W@l99d904";
        try{
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                md.update((nPassword+salt).getBytes());
            
                byte byteData[] = md.digest();

                //Convert from byte to HEX
                StringBuffer hexString = new StringBuffer();
                for (int i=0;i<byteData.length;i++) {
                    String hex=Integer.toHexString(0xff & byteData[i]);
                    if(hex.length()==1) hexString.append('0');
                    hexString.append(hex);
                }
                tmpPassword = hexString.toString();
                
            }catch(NoSuchAlgorithmException e){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "لايوجد نظام حمايه في هذا الخادم الرجاء التواصل مع الدعم الفني", null));
                FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("usersMessages");
            }
        return tmpPassword;
    }
    
    public String createUser() {
            
            Chronology hijri = IslamicChronology.getInstance(DateTimeZone.forID("Asia/Riyadh"));
            LocalDate todayHijri = LocalDate.now(hijri).plusDays(1);
            
            String tmpPassword =hashPssword(newPassword);
            
            int tmp = emf.createEntityManager().createNamedQuery("User.findByUserName", User.class)
                .setParameter("userName", newUserName)
                .getResultList().size();
            if(tmp > 0 ){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "المستخدم موجود مسبقا بالنظام", null));
                FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("usersMessages");
            }else {
                try{
                    utx.begin();
                    User u = new User();
                    u.setUserName(newUserName);
                    u.setUserPassword(tmpPassword);
                    u.setUserDeptId(new Departement(DeptId));
                    u.setUserRoleId(new Role(RoleId));
                    u.setCannotRemove(false);
                    u.setLastLogin(todayHijri.toDate());
                    u.setLastPasswordUpdate(todayHijri.toDate());
                    
                    emf.createEntityManager().persist(u);
                    utx.commit();

                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "تم إضافة المستخدم بنجاح", null));
                    FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("usersMessages"); 
                    
                    //Refresh Users Table
                    recreatePagination();
                    recreateModel();
                    
                    //Reset form fields
                    newUserName = "";
                    newPassword = "";
                    DeptId =0;
                    RoleId = 0;
                }catch(Exception ee){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "يوجد مشكله بالاتصال مع قاعدة البيانات", null));
                    FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("usersMessages");
                }
                
            }
            
            
        return "";
    }
    
    //User Name Validation
    public void validateUserName(FacesContext context, UIComponent toValidate, Object value){
        String userNameValue = (String) value;
        if(userNameValue.equals("")){
           ((UIInput) toValidate).setValid(false);
           FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إدخال إسم المستخدم", null);
                   //new FacesMessage( "حجم الملف أكبر من المسموح به وهو ٢م.ب.");
           context.addMessage(toValidate.getClientId(context), message); 
        }
    }
    
    //Password Validation
    public void validateNewPassword(FacesContext context, UIComponent toValidate, Object value){
        String passwordValue = (String) value;
        if(passwordValue.equals("")){
           ((UIInput) toValidate).setValid(false);
           FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إدخال كلمة المرور", null);
                   //new FacesMessage( "حجم الملف أكبر من المسموح به وهو ٢م.ب.");
           context.addMessage(toValidate.getClientId(context), message); 
        }
    }
    
    //Old Password Validation
    public void validateOldPassword(FacesContext context, UIComponent toValidate, Object value){
        String oldpasswordValue = (String) value;
        if(oldpasswordValue.equals("")){
           ((UIInput) toValidate).setValid(false);
           FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إدخال كلمة المرور القديمة", null);
                   //new FacesMessage( "حجم الملف أكبر من المسموح به وهو ٢م.ب.");
           context.addMessage(toValidate.getClientId(context), message); 
        }
    }
    
    //New Password Validation
    public void validatePrePassword(FacesContext context, UIComponent toValidate, Object value){
        String prepasswordValue = (String) value;
        if(prepasswordValue.equals("")){
           ((UIInput) toValidate).setValid(false);
           FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إدخال كلمة المرور الجديدة", null);
                   //new FacesMessage( "حجم الملف أكبر من المسموح به وهو ٢م.ب.");
           context.addMessage(toValidate.getClientId(context), message); 
        }
    }
    
    //ٍDepartement selection Validation
    public void validateDepartementSelected(FacesContext context, UIComponent toValidate, Object value){
        int id= (int) value;
        if(id == 0){
            ((UIInput) toValidate).setValid(false);
           FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إختيار الإدارة أو القسم", null);
                   //new FacesMessage( "حجم الملف أكبر من المسموح به وهو ٢م.ب.");
           context.addMessage(toValidate.getClientId(context), message); 
        }
    }
    
    //ٍRole selection Validation
    public void validateRoleSelected(FacesContext context, UIComponent toValidate, Object value){
        int id= (int) value;
        if(id == 0){
            ((UIInput) toValidate).setValid(false);
           FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "لابد من إختيار صلاحيه للمستخدم", null);
                   //new FacesMessage( "حجم الملف أكبر من المسموح به وهو ٢م.ب.");
           context.addMessage(toValidate.getClientId(context), message); 
        }
    }
}
