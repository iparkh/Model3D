package view.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.faces.context.FacesContext;

import javax.servlet.http.HttpSession;

import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.component.rich.nav.RichCommandMenuItem;

import view.common.CommonADFUtil;

public class LoginBean {
    
    private RichInputText usernameInputText;
    private RichInputText passwordInputText;

    

    public LoginBean() {
    }

    public void setUsernameInputText(RichInputText usernameInputText) {
        this.usernameInputText = usernameInputText;
    }

    public RichInputText getUsernameInputText() {
        return usernameInputText;
    }

    public void setPasswordInputText(RichInputText passwordInputText) {
        this.passwordInputText = passwordInputText;
    }

    public RichInputText getPasswordInputText() {
        return passwordInputText;
    }

    public String loginAction() {
        String username=usernameInputText.getValue().toString();
        String password=passwordInputText.getValue().toString();
        System.out.println("username:"+username+" password:"+password);
        
        Connection conn=null;
        PreparedStatement stat=null;
        ResultSet rs=null;
        
        try {
            String sql="select NAME from user_information where USER_NAME='"+username+"' and USER_PASSWORD='"+password+"'";
            System.out.println("sql:::::::::"+sql);
            conn=CommonADFUtil.getConnection();
            stat =conn.prepareStatement(sql);
            rs=stat.executeQuery();
            while(rs.next()){
                CommonADFUtil.putInSessionScope("username", username);
                CommonADFUtil.putInSessionScope("principalName", rs.getString(1));
                return "main";
            }
            
        } catch (Exception e) {
            System.out.println("error in logout --"+e);
        }

        CommonADFUtil.showErrorMessage("Не вірне імя користувача чи пароль");
        return null;
    }
}
