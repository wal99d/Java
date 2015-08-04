/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package loan.system.general;

import java.io.IOException;
import java.io.Serializable;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author me
 */

@Named("lang")
@SessionScoped
public class General implements Serializable{
    
    private String locale;
    private boolean arabic;

    public boolean isArabic() {
        return arabic;
    }

    public void setArabic(boolean arabic) {
        this.arabic = arabic;
    }
    
    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public General() {
    }
    

    @PostConstruct
    public void initGeneral() {
        General g = new General();
    }
    
    public void changeLocale(){
        String tmpLocale ;
        locale = FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage();
        
        System.out.println("---The Locale is: " + locale);
        
        if(locale.equalsIgnoreCase("ar")){
            tmpLocale = "en";
            arabic = false;
        } else {
            tmpLocale = "ar";
            arabic = true;
            
        }
        locale = tmpLocale;
        System.out.println("---The Locale is: " + locale);
        
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale(tmpLocale));
      
        
    }
    
}
