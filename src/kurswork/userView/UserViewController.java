/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kurswork.userView;



import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import kurswork.MainTransferDate;
import kurswork.VODBC.VODBC;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
 
/**
 * FXML Controller class
 *
 * @author Vlasov
 */
public class UserViewController implements Initializable {

   
    @FXML
    WebView WebViewID;
    
    @FXML
    Label userNameLabel;
    @FXML
    Label userGrupLabel;
    @FXML
    Label newLabel;
    @FXML
    Label courseTitleLabel;
    @FXML
    Label labNumLabel;
    @FXML
    Label deadlineLabel;
    @FXML
    Label userResultLabel;
         
    @FXML
    TextField emailField;
    @FXML
    TextField phoneNumField;
    
    @FXML
    Button EditEmailButton;
    @FXML
    Button EditEmailCancelButton;
    @FXML
    Button EditPhoneButton;
    @FXML
    Button EditPhoneCancelButton;
    
    @FXML
    TextArea DescriptionTextArea;
    
         
    @FXML
    TabPane UserTabPane;
    
    @FXML
    TableColumn LabNumber;
    @FXML
    TableColumn LabSum;
    @FXML
    TableColumn LabTab[];
    
    @FXML
    TableView UserLabsTable;
    
    @FXML
    TreeView MyNewTree;

    
    public MainTransferDate date;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    
    boolean EmailEdit;
    boolean PhoneEdit;
    
    @FXML
    private void editEmailAction(ActionEvent event) throws ClassNotFoundException, SQLException{
        if(EmailEdit){
            EditEmailCancelButton.setVisible(false);
            date.user.tmp = emailField.getText();
            VODBC.updateEmail(date.user);
            EditEmailButton.setText("Edit");
            date.user.setEmail(emailField.getText());
            emailField.setEditable(false);
            
            EmailEdit = false;
        }else{
            EditEmailCancelButton.setVisible(true);
            EditEmailButton.setText("Save");
            emailField.setEditable(true);
            EmailEdit = true;
        }
    }
    
    @FXML
    private void editEmailCancelAction(ActionEvent event){
        EditEmailCancelButton.setVisible(false);
        EditEmailButton.setText("Edit");
        emailField.setText(date.user.getEmail());
        EmailEdit = false;
        emailField.setEditable(false);
    }
    
    @FXML
    private void editPhoneAction(ActionEvent event) throws ClassNotFoundException, SQLException{
        if(PhoneEdit){
            EditPhoneCancelButton.setVisible(false);
            EditPhoneButton.setText("Edit");
            
            date.user.tmp = phoneNumField.getText();
            VODBC.updatePhoneNumber(date.user);
            date.user.setPhone(phoneNumField.getText());
            phoneNumField.setEditable(false);
            
            PhoneEdit = false;
        }else{
            EditPhoneCancelButton.setVisible(true);
            EditPhoneButton.setText("Save");
            phoneNumField.setEditable(true);
            PhoneEdit = true;
        }
    }
    
    @FXML
    private void editPhoneCancelAction(ActionEvent event){
        EditPhoneCancelButton.setVisible(false);
        EditPhoneButton.setText("Edit");
        phoneNumField.setText(date.user.getPhone());
        PhoneEdit = false;
        phoneNumField.setEditable(false);
    }
    
    private TreeItem<String> childNode[];
    private TreeItem<String> childNodeNude[][];
    TreeItem<String> root = new TreeItem<>("root_root_root");
    
    public void myInit()throws ClassNotFoundException,
      SQLException{
        
       // WebViewID.getEngine().load("/kurswork/loginView/Login.fxml");
        
        VODBC vodbc = new VODBC();
        date = vodbc.LoadUserDate(date);
        
        userNameLabel.setText(date.user.getName());
        userGrupLabel.setText(date.user.getGrup());
        
        emailField.setText(date.user.getEmail());
        phoneNumField.setText(date.user.getPhone());
        
        if(!(date.user.course == null)){
        childNode = new TreeItem[date.user.course.length];
        childNodeNude = new TreeItem[date.user.course.length][];
        
        root.setExpanded(true); 
        
        for(int i=0; i<date.user.course.length; i++){
            
            childNode[i] = new TreeItem<>(date.user.course[i].getName());
 
            childNodeNude[i] = new TreeItem[date.user.course[i].getLabNumber()];
            for(int j=0; j<date.user.course[i].getLabNumber(); j++){
                childNodeNude[i][j] = new TreeItem<>(date.user.course[i].labs[j].getName());
                childNode[i].getChildren().add(childNodeNude[i][j]);
            }
            root.getChildren().add(childNode[i]);
        
        }

        MyNewTree.setRoot(root);
        MyNewTree.setShowRoot(false);
        
        MyNewTree.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent mouseEvent){            
                if(mouseEvent.getClickCount() == 1)
                    setInfoLab((TreeItem<String>)MyNewTree.getSelectionModel().getSelectedItem());
            }});
        }
    }
    
    public void setInfoLab(TreeItem<String> item)
    {
        for(int i=0; i<date.user.course.length; i++)
        {
            if(item != null) if(item.getParent() != null)
            if(date.user.course[i].getName().equals(item.getParent().getValue()))
            {
                courseTitleLabel.setText(date.user.course[i].getName());
                for(int j=0; j<date.user.course[i].getLabNumber(); j++)
                    if(date.user.course[i].labs[j].getName().equals(item.getValue()))
                    {
                        labNumLabel.setText(date.user.course[i].labs[j].getName());
                        deadlineLabel.setText(date.user.course[i].labs[j].getDeadline());
                        userResultLabel.setText(date.user.course[i].labs[j].getResult());
                        DescriptionTextArea.setText(date.user.course[i].labs[j].getDescription());
                    }
            }
        }
    }
    
    public TreeItem<String> makeBranch (String title, TreeItem<String> perent){
        TreeItem<String> item = new TreeItem<>(title);
        item.setExpanded(true);
        perent.getChildren().add(item);
        return item;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        EmailEdit = false;
        PhoneEdit = false;
        // TODO
    }    
    
}