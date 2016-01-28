/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kurswork.adminView;


import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import kurswork.MainTransferDate;
import kurswork.User;
import kurswork.VODBC.VODBC;

/**
 * FXML Controller class
 *
 * @author Vlasov
 */
public class AdminViewController implements Initializable {
    public MainTransferDate date;

    boolean grupEdit;
    boolean grupAdd;
    
    
    @FXML
    TreeView aUsersTreeView;
    @FXML
    TreeView aGrupsTreeView;
    
    @FXML
    TextField aUserLoginTextField;
    @FXML
    TextField aUserEmailTextField;
    @FXML
    TextField aUserPhoneTextField;
    @FXML
    TextField aUserNameTextField;
    @FXML
    TextField aGrupNameTextField;
    @FXML
    TextField aUserPasswordTextField;
    
    @FXML
    Button aGrupEditSaveButton;
    @FXML
    Button aGrupEditCancelButton;
    @FXML
    Button aGrupNewGrupButton;
    @FXML
    Button aGrupNewGrupSaveButton;
    @FXML
    Button aGrupDeletButton;
    @FXML
    Button aUserNewUserButton;
    @FXML
    Button aUserCancelButton;
    @FXML
    Button aUsersEditSaveButton;
    @FXML
    Button aUserSaveButton;
    @FXML
    Button aUserDeleteButton;
    
    @FXML
    ChoiceBox aUserGrupChoiceBox;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
        
    private TreeItem<String> au_childNode[];
    private TreeItem<String> au_childNodeNude[][];
    TreeItem<String> au_root = new TreeItem<>("root_root_root");
        
    private TreeItem<String> ag_childNode[];
    TreeItem<String> ag_root = new TreeItem<>("root_root_root");
    
    public void myInit() throws ClassNotFoundException,
      SQLException{
        grupEdit = false;
        grupAdd = false;
        date.admin= VODBC.aLoadAdmin(); 
        
        
        //USER TAB - TreeView
        au_root = new TreeItem<>("root_root_root");
        au_childNode = new TreeItem[date.admin.grups.length];
        au_childNodeNude = new TreeItem[date.admin.grups.length][];
        au_root.setExpanded(true); 
        for(int i=0; i<date.admin.grups.length; i++){
            au_childNode[i] = new TreeItem<>(date.admin.grups[i].getName());
            if(date.admin.grups[i].users != null){
                au_childNodeNude[i] = new TreeItem[date.admin.grups[i].users.length];
            for(int j=0; j<date.admin.grups[i].users.length; j++){
                au_childNodeNude[i][j] = new TreeItem<>(date.admin.grups[i].users[j].getName());
                au_childNode[i].getChildren().add(au_childNodeNude[i][j]);
            }
            }
            else{
                au_childNodeNude[i] = new TreeItem[1];
            }
            au_root.getChildren().add(au_childNode[i]);
        }
            aUsersTreeView.setRoot(au_root);
            aUsersTreeView.setShowRoot(false);
        
            aUsersTreeView.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent mouseEvent){            
                if(mouseEvent.getClickCount() == 1){
                    userEdit = false;
                    newUser = false;
                    
                    
                    
                    aUserCancelButton.setVisible(false);
                    aUserSaveButton.setVisible(false);
                    
                    aUsersEditSaveButton.setVisible(true);
                    aUsersEditSaveButton.setText("Edit");
                    aUserNewUserButton.setText("New User");
                    aUserNewUserButton.setVisible(true);
                    aUserDeleteButton.setVisible(true);
                    
                    aUserCancelAction();
                    aUserLoginTextField.setText("");
                    aUserNameTextField .setText("");
                    aUserEmailTextField.setText("");
                    aUserPhoneTextField.setText("");
                    
                    setUserTabView((TreeItem<String>)aUsersTreeView.getSelectionModel().getSelectedItem());
                }}});
        
        //Grup TAB -TreeView
        ag_root = new TreeItem<>("root_root_root");
        ag_childNode = new TreeItem[date.admin.grups.length];
        ag_root.setExpanded(true); 
        for(int i=0; i<date.admin.grups.length; i++){
            ag_childNode[i] = new TreeItem<>(date.admin.grups[i].getName());
            ag_root.getChildren().add(ag_childNode[i]);
            }
            aGrupsTreeView.setRoot(ag_root);
            aGrupsTreeView.setShowRoot(false);
        
            
            aGrupsTreeView.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent mouseEvent){            
                if(mouseEvent.getClickCount() == 1){
                    
                    grupEdit = false;
                    grupAdd = false;
                    
                    aGrupEditCancelButton.setVisible(false);
                    aGrupNewGrupSaveButton.setVisible(false);
                    
                    aGrupEditSaveButton.setText("Edit");
                    aGrupEditSaveButton.setVisible(true);
                  
                    aGrupNewGrupButton.setText("New Grup");
                    aGrupNewGrupButton.setVisible(true);
            
                    aGrupDeletButton.setVisible(true);
                    
                    aGrupNameTextField.setEditable(false);
                    grupEdit = false;
                    aGrupDeletButton.setVisible(true);
                    
                    setGrupTabView((TreeItem<String>)aGrupsTreeView.getSelectionModel().getSelectedItem());
                }}});
        

        //USER TAB - Content
        
        
//        aUserGrupChoiceBox;
            String obs[] = new String[date.admin.grups.length];
            for(int i=0; i<date.admin.grups.length;i++)
                obs[i] = date.admin.grups[i].getName();
            
            aUserGrupChoiceBox.setItems(FXCollections.observableArrayList(obs));
            
            aUserGrupChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new
                ChangeListener<Number>(){
                    public void changed(ObservableValue ov, Number value, Number new_value){
                        if(new_value.intValue()!= -1) 
                            newUserGrup = obs[new_value.intValue()];
                    }
                });
            
            newUser = false;
    }
    
    
    public void setUserTabView(TreeItem<String> item){
    if(item == null) return;    
        
    String root_string = item.getValue();
        if(root_string.equals("root_root_root")){
            aUsersEditSaveButton.setVisible(false);
            aUserDeleteButton.setVisible(false);
            userGrupSelected = true;
            return;
        }
    
    root_string = item.getParent().getValue();
        if(root_string.equals("root_root_root")){
            aUsersEditSaveButton.setVisible(false);
            aUserDeleteButton.setVisible(false);
            userGrupSelected = true;
            return;
        }
            
    for(int i=0; i<date.admin.grups.length; i++){
        if(item.getParent().getValue().equals(date.admin.grups[i].getName())){
           for(int j=0; j<date.admin.grups[i].users.length; j++){
               if(item.getValue().equals(date.admin.grups[i].users[j].getName())){
                   
                    aUserLoginTextField.setText(date.admin.grups[i].users[j].getLogin());
                    aUserEmailTextField.setText(date.admin.grups[i].users[j].getEmail());
                    aUserPhoneTextField.setText(date.admin.grups[i].users[j].getPhone());
                    aUserNameTextField.setText(date.admin.grups[i].users[j].getName());
                    
                    tmpUserGrup = date.admin.grups[i].getName();
                    aUserGrupChoiceBox.setValue(date.admin.grups[i].getName());
                }
                }
            }
        }
    }
    
    boolean userEdit;
    
    String tmpUserLogin;
    String tmpUserGrup;
    String newUserGrup;
    String tmpUserName;
    String tmpUserEmail;
    String tmpUserPhone;
    
    @FXML
    public void aUsersEditSaveAction() throws ClassNotFoundException, SQLException{
        if(userEdit){ //save Button
            
            userEdit = false;
            aUserCancelButton.setVisible(false);
            aUserNewUserButton.setVisible(true);
            aUsersEditSaveButton.setText("Edit");
            
            aUserLoginTextField.setEditable(false);
            aUserEmailTextField.setEditable(false);
            aUserPhoneTextField.setEditable(false);
            aUserNameTextField.setEditable(false);
          
            aUserGrupChoiceBox.setDisable(true);
            
            System.out.println(newUserGrup);
            
            User newUser = new User();
            
            newUser.setLogin(aUserLoginTextField.getText());
            newUser.setEmail(aUserEmailTextField.getText());
            newUser.setPhone(aUserPhoneTextField.getText());
            newUser.setName(aUserNameTextField.getText());
            
            if(newUserGrup.equals("admin"))
                newUser.setPermissionLevel(newUserGrup);
            else newUser.setPermissionLevel("user");
            
            newUser.setGrup(newUserGrup);
            
            newUser.tmp = tmpUserLogin;
            aUserDeleteButton.setVisible(true);
            VODBC.updateUserInfo(newUser);
            myInit();
            
        }else{  //edit Button
            
            userEdit = true;
            aUserNewUserButton.setVisible(false);
            aUserCancelButton.setVisible(true);
            aUsersEditSaveButton.setText("Save");
            
            aUserLoginTextField.setEditable(true);
            aUserEmailTextField.setEditable(true);
            aUserPhoneTextField.setEditable(true);
            aUserNameTextField.setEditable(true);
            
            aUserGrupChoiceBox.setDisable(false);
            aUserGrupChoiceBox.setValue(tmpUserGrup);
            
            tmpUserLogin = aUserLoginTextField.getText();
            tmpUserName  = aUserNameTextField.getText();
            tmpUserEmail = aUserEmailTextField.getText();
            tmpUserPhone = aUserPhoneTextField.getText();
            
            aUserDeleteButton.setVisible(false);
            System.out.println(newUserGrup);
            // = aUserGrupChoiceBox.
        }
    }
    
    @FXML
    public void aUserCancelAction(){
        //if(userEdit){
        userEdit = false;
        aUserCancelButton.setVisible(false);
        aUserNewUserButton.setVisible(true);
        aUsersEditSaveButton.setText("Edit");

        aUserLoginTextField.setEditable(false);
        aUserEmailTextField.setEditable(false);
        aUserPhoneTextField.setEditable(false);
        aUserNameTextField.setEditable(false);

        aUserGrupChoiceBox.setDisable(true);
       // aUserGrupChoiceBox.setText(tmpUserGrup);

        aUserLoginTextField.setText(tmpUserLogin);
        aUserNameTextField .setText(tmpUserName);
        aUserEmailTextField.setText(tmpUserEmail);
        aUserPhoneTextField.setText(tmpUserPhone);
        aUserDeleteButton.setVisible(true);
        System.out.println(newUserGrup);
        //}else{}
    }
    
    public void setGrupTabView(TreeItem<String> item){
        if(item == null) 
            return;    

        String root_string = item.getValue();
        if(root_string.equals("root_root_root"))
            return;

        for(int i=0; i<date.admin.grups.length; i++){
            if(item.getValue().equals(date.admin.grups[i].getName())){
               aGrupNameTextField.setText(date.admin.grups[i].getName());
            }
        }
    }
    
    boolean newUser;
    boolean userGrupSelected = true;
    
    @FXML
    public void aUserNewUserAction(){
        if(newUser){//cancel button
            newUser = false;
            
            aUserSaveButton.setVisible(false);
            if(!userGrupSelected){
                aUsersEditSaveButton.setVisible(true);
                aUserDeleteButton.setVisible(true);
            }
            aUserNewUserButton.setText("New User");
            
            aUserLoginTextField.setEditable(false);
            aUserEmailTextField.setEditable(false);
            aUserPhoneTextField.setEditable(false);
            aUserNameTextField.setEditable(false);
            
            aUserGrupChoiceBox.setDisable(true);
            
            aUserPasswordTextField.setText("");
            aUserLoginTextField.setText("");
            aUserNameTextField .setText("");
            aUserEmailTextField.setText("");
            aUserPhoneTextField.setText("");
            
        }else{//new user button
            newUser = true;
            
            aUsersEditSaveButton.setVisible(false);
            aUserSaveButton.setVisible(true);
            aUserDeleteButton.setVisible(false);
            aUserNewUserButton.setText("Cancel");
            
            aUserPasswordTextField.setText("user");
            
            aUserLoginTextField.setEditable(true);
            aUserEmailTextField.setEditable(true);
            aUserPhoneTextField.setEditable(true);
            aUserNameTextField.setEditable(true);
            
            aUserGrupChoiceBox.setDisable(false);
        }
    }
    
    @FXML
    public void aUserSaveAction() throws ClassNotFoundException, SQLException{ //save button
        newUser = false;
            
        aUserSaveButton.setVisible(false);
        aUsersEditSaveButton.setVisible(true);
        aUserDeleteButton.setVisible(true);
        aUserNewUserButton.setText("New User");
        
        aUserLoginTextField.setEditable(false);
        aUserEmailTextField.setEditable(false);
        aUserPhoneTextField.setEditable(false);
        aUserNameTextField.setEditable(false);
        
        aUserGrupChoiceBox.setDisable(true);
        
        User newUser = new User();
            
        newUser.setLogin(aUserLoginTextField.getText());
        newUser.setPassword(aUserPasswordTextField.getText());
        newUser.setName(aUserNameTextField.getText());
        newUser.setEmail(aUserEmailTextField.getText());
        newUser.setPhone(aUserPhoneTextField.getText());

        if(newUserGrup.equals("admin"))
            newUser.setPermissionLevel(newUserGrup);
        else newUser.setPermissionLevel("user");
        newUser.setGrup(newUserGrup);

        VODBC.addUser(newUser);
        myInit();
    }
    
    @FXML
    private void aUserDeletAction() throws ClassNotFoundException, SQLException{
        if(aUserLoginTextField.getText().equals("admin")) return;
        VODBC.deleteUser(aUserLoginTextField.getText());
        
        myInit();
    }
    
    public TreeItem<String> makeBranch (String title, TreeItem<String> perent){
        TreeItem<String> item = new TreeItem<>(title);
        item.setExpanded(true);
        perent.getChildren().add(item);
        return item;
    }
    
    String tmpGrupName;
    
    @FXML
    private void aGrupEditSaveAction() throws ClassNotFoundException, SQLException{
        if(aGrupNameTextField.getText().equals("admin")) return;
        if(grupEdit){
            if(!aGrupNameTextField.getText().equals(""))
            {
                aGrupEditSaveButton.setText("Edit");
                aGrupEditCancelButton.setText("_C_");
                aGrupEditCancelButton.setVisible(false);
                aGrupNewGrupButton.setVisible(true);
                
                aGrupNameTextField.setEditable(false);
                grupEdit = false;

                for(int i=0; i<date.admin.grups.length;i++){
                    if(date.admin.grups[i].getName().equals(tmpGrupName)){
                        date.admin.grups[i].setName(aGrupNameTextField.getText());
                        System.out.println(date.admin.grups[i].getName());
                        
                        VODBC.updateGrupName(tmpGrupName,aGrupNameTextField.getText());
                    }
                }
                aGrupDeletButton.setVisible(true);
                myInit();
            }
            else
            {
                
            }
        }else{
            if(!aGrupNameTextField.getText().equals("admin")){
                aGrupEditSaveButton.setText("Save");
                
                aGrupNewGrupButton.setVisible(false);
                aGrupEditCancelButton.setVisible(true);
                aGrupEditCancelButton.setText("Cancel");
                tmpGrupName = aGrupNameTextField.getText();
                aGrupNameTextField.setEditable(true);
                grupEdit = true;
                aGrupDeletButton.setVisible(false);
            }else{}
        }
    }
    
    @FXML
    private void aGrupEditCancelAction(){
        if(grupEdit){
            aGrupEditSaveButton.setText("Edit");
            aGrupEditCancelButton.setText("_C_");
            aGrupEditCancelButton.setVisible(false);
            aGrupNewGrupButton.setVisible(true);
            aGrupNameTextField.setText(tmpGrupName);
            aGrupNameTextField.setEditable(false);
            aGrupDeletButton.setVisible(false);
            grupEdit = false;
            aGrupDeletButton.setVisible(true);
        }else{
            
        }
    }
    
    @FXML
    private void aGrupNewGrupAction() throws ClassNotFoundException, SQLException{
        if(grupAdd){
            grupAdd = false;
            aGrupNewGrupSaveButton.setVisible(false);
            aGrupEditSaveButton.setVisible(true);
            aGrupNameTextField.setText(tmpGrupName);
            aGrupNameTextField.setEditable(false);
            aGrupNewGrupButton.setText("New Grup");
            aGrupDeletButton.setVisible(true);
        }else{
            grupAdd = true;
            aGrupEditSaveButton.setVisible(false);
            aGrupNewGrupSaveButton.setVisible(true);
            aGrupNewGrupButton.setText("Cancel");
            tmpGrupName = aGrupNameTextField.getText();
            aGrupNameTextField.setText("");
            aGrupNameTextField.setEditable(true);
            aGrupDeletButton.setVisible(false);
        }
    }
    
    @FXML
    private void aGrupNewGrupSaveAction() throws ClassNotFoundException,
      SQLException{
        if(grupAdd){
            grupAdd = false;
            aGrupNewGrupButton.setText("New Grup");
            aGrupNewGrupSaveButton.setVisible(false);
            aGrupEditSaveButton.setVisible(true);
            VODBC.addGrup(aGrupNameTextField.getText());
            date.admin.addGrup(aGrupNameTextField.getText());
            myInit();
            aGrupDeletButton.setVisible(true);
        }
    }
    
    @FXML
    private void aGrupDeletAction() throws ClassNotFoundException, SQLException{
        if(aGrupNameTextField.getText().equals("admin")) return;
        VODBC.deleteGrup(aGrupNameTextField.getText());
        date.admin.delateGrup(aGrupNameTextField.getText());
        myInit();
        
    }
    
}
