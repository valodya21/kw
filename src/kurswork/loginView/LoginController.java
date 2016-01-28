/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kurswork.loginView;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import kurswork.Course;
import kurswork.Laba;
import kurswork.MainTransferDate;
import kurswork.VODBC.VODBC;
import kurswork.adminView.AdminViewController;
import kurswork.userView.UserViewController;

/**
 * FXML Controller class
 *
 * @author Vlasov
 */
public class LoginController implements Initializable {

    @FXML
    TextField txtUsername;
    @FXML
    PasswordField txtPassword;
    @FXML
    Label msgLabel;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @FXML
    AnchorPane ap = new AnchorPane();
    
    @FXML
    private void loginButtonAction(ActionEvent event) throws Exception{
        msgLabel.setText("");
        MainTransferDate date = new MainTransferDate();
        date.user.setLogin(txtUsername.getText());
        date.user.setPassword(txtPassword.getText());
        VODBC logc = new VODBC();
        if(!date.user.getLogin().equals("") && !date.user.getPassword().equals("")) {
            if(logc.userExistValid(date.user)){
                date.user.setPermissionLevel(logc.getUserPermision(date.user));
                
                if(date.user.getPermissionLevel().equals("user")){
                    Stage s = (Stage) msgLabel.getScene().getWindow();
                    
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/kurswork/userView/UserView.fxml"));
                    loader.load();
                    
                    UserViewController uvc = loader.getController();
                    uvc.date = date;
                    uvc.myInit();
                    
                    Parent parent = loader.getRoot();
                    
                    Stage stage = new Stage();
                    
                    stage.setScene(new Scene(parent));
                    stage.setTitle("Labs");
                    stage.setMinWidth(600);
                    stage.setMinHeight(400);
                    stage.show();
                    
                    s.close();
                }
                
                if(date.user.getPermissionLevel().equals("admin"))
                {
                    Stage s = (Stage) msgLabel.getScene().getWindow();
                    
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/kurswork/adminView/AdminView.fxml"));
                    loader.load();
                    
                    AdminViewController avc =  loader.getController();
                    avc.date = date;
                    avc.myInit();
                    
                    Parent parent = loader.getRoot();
                    
                    Stage stage = new Stage();
                    stage.setScene(new Scene(parent));
                    stage.setTitle("Admin");
                    stage.setMinWidth(600);
                    stage.setMinHeight(400);
                    stage.show();
                    
                    s.close();
                }
                
                msgLabel.setText("...!"+date.user.getPermissionLevel()+"_"); 
            }else
                msgLabel.setText("Username or password invalid!"); 
                
        }else 
            msgLabel.setText("All fields must be filled");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}