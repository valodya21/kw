/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kurswork.adminView;

import java.net.URL;
import java.sql.SQLException;
//import java.util.Arrays;
import java.util.ResourceBundle;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableColumn.CellDataFeatures;
//import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
//import javafx.util.Callback;
import kurswork.Course;
import kurswork.MainTransferDate;
import kurswork.User;
import kurswork.VODBC.VODBC;
//import kurswork.VODBC.LODBC;

/**
 * FXML Controller class
 *
 * @author Vlasov
 */
public class AdminViewController implements Initializable {
    public MainTransferDate date;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    private TreeItem<String> au_childNode[];
    private TreeItem<String> au_childNodeNude[][];
    TreeItem<String> au_root = new TreeItem<>("root_root_root");
    //USER LAB TAB
    private TreeItem<String> aul_childNode[];
    private TreeItem<String> aul_childNodeNude[][];
    TreeItem<String> aul_root = new TreeItem<>("root_root_root");
    
    private TreeItem<String> ag_childNode[];
    private TreeItem<String> ag_root = new TreeItem<>("root_root_root");
    
    public TreeItem<String> makeBranch (String title, TreeItem<String> perent){
        TreeItem<String> item = new TreeItem<>(title);
        item.setExpanded(true);
        perent.getChildren().add(item);
        return item;
    }
    
    public void myInit() throws ClassNotFoundException,
      SQLException{
    
        date.admin= VODBC.aLoadAdmin(); 
        //date.courses = LODBC.aLoadCourses();
        
        date.admin.courses = VODBC.getCourses();
        //USER TAB - TreeView
        initUserTabView();
        
        //Grup TAB
        initGrupTabView();
        
        //Course Tab
        initCoursesTabView();
        
        //Labs Tab
        initLabTabView();
        
        //Reating Tab
        //initRatingTabView();
    }

    
    
    
    boolean userGrupSelected = true;
    
////////////////////////////////////////////////////////////////////////////////    
    /**
     * User Tab Actions, Varibles and Methods
     */
    
    @FXML
    TreeView aUsersTreeView;
    
    @FXML
    TextField aUserLoginTextField;
    @FXML
    TextField aUserPasswordTextField;
    @FXML
    TextField aUserNameTextField;
    @FXML
    TextField aUserEmailTextField;
    @FXML
    TextField aUserPhoneTextField;
    
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
    
    String tmpUserLogin;
    String tmpUserGrup;
    String newUserGrup;
    String tmpUserName;
    String tmpUserEmail;
    String tmpUserPhone;
    
    boolean userModNew;
    boolean userModEdit;
    
    public void initUserTabView(){
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
                userModEdit = false;
                userModNew = false;

                aUserCancelButton.setVisible(false);
                aUserSaveButton.setVisible(false);

                aUsersEditSaveButton.setVisible(true);
                aUsersEditSaveButton.setText("Edit");
                aUserNewUserButton.setText("New User");
                aUserNewUserButton.setVisible(true);
                aUserDeleteButton.setVisible(true);

                aUserCancelAction();
                setUserTabTextFieldTextDef();

                setUserTabView((TreeItem<String>)aUsersTreeView.getSelectionModel().getSelectedItem());
            }}});
        
        String obs[] = new String[date.admin.grups.length];
        for(int i=0; i<date.admin.grups.length;i++)
            obs[i] = date.admin.grups[i].getName();

        aUserGrupChoiceBox.setItems(FXCollections.observableArrayList(obs));

        //aUserGrupChoiceBox.getSelectionModel().selectedIndexProperty().removeListener(ChangeListener<Number>());
        
        aUserGrupChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new
            ChangeListener<Number>(){
                public void changed(ObservableValue ov, Number value, Number new_value){
                    if(new_value.intValue()!= -1) 
                        newUserGrup = obs[new_value.intValue()];
                }
            });
    }
    
    public void setUserTabView(TreeItem<String> item){
        if(item == null) {
            setUserTabDef();
            return;
        }    

        String root_string = item.getValue();
        if(root_string.equals("root_root_root")){
            setUserTabDef();
            userGrupSelected = true;
            return;
        }

        root_string = item.getParent().getValue();
        if(root_string.equals("root_root_root")){
            setUserTabDef();
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

    public void setUserTabDef(){
        aUserGrupChoiceBox.setDisable(true);
        aUserGrupChoiceBox.setValue("");
        
        setUserTabButtonDef();
        setUserTabTextFieldDef();
        
        userModEdit = false;
    }
    
    public void setUserTabButtonDef(){
        aUsersEditSaveButton.setVisible(false);
        aUsersEditSaveButton.setText("Edit");
        
        aUserCancelButton.setVisible(false);
        aUserCancelButton.setText("Cencel");
        
        aUserDeleteButton.setVisible(false);
        aUserDeleteButton.setText("Delete");
        
        aUserSaveButton.setVisible(false);
        aUserSaveButton.setText("Save");
        
        aUserNewUserButton.setVisible(true);
        aUserNewUserButton.setText("New User");
    }
    
    public void setUserTabTextFieldDef(){
        setUserTabTextFieldEditable(false);
        aUserPasswordTextField.setEditable(false);
        
        setUserTabTextFieldTextDef();
    }
    
    public void setUserTabTextFieldTextDef(){
        aUserLoginTextField.setText("");
        aUserPasswordTextField.setText("");
        aUserNameTextField.setText("");
        aUserEmailTextField.setText("");
        aUserPhoneTextField.setText("");
    }
    
    public void setUserTabTextFieldEditable(boolean set){
        aUserLoginTextField.setEditable(set);
        aUserNameTextField.setEditable(set);
        aUserEmailTextField.setEditable(set);
        aUserPhoneTextField.setEditable(set);
        
        //aUserPasswordTextField.setEditable(set);
    }
    
    @FXML
    Button aPasswordButton;
    
    boolean aPasswordEditMod = false;
    
    @FXML
    public void aPasswordAction() throws ClassNotFoundException, SQLException{
        if(aPasswordEditMod){
            String pass = aUserPasswordTextField.getText();
            if(pass.equals("")){
                aUserPasswordTextField.setEditable(false);
                aUserPasswordTextField.setText("");
            }else{
                VODBC.setPass(aUserLoginTextField.getText(), pass);
                aUserPasswordTextField.setEditable(false);
                aUserPasswordTextField.setText("");
                aPasswordEditMod = false;
            }
        }else{
            aUserPasswordTextField.setEditable(true);
            aUserPasswordTextField.setText("");
            aPasswordEditMod = true;
        }
    }
    
    @FXML
    public void aUsersEditSaveAction() throws ClassNotFoundException, SQLException{
        if(userModEdit){ //save Button
        
                    userModEdit = false;
                    aUserCancelButton.setVisible(false);
                    aUserNewUserButton.setVisible(true);
                    aUsersEditSaveButton.setText("Edit");

                    setUserTabTextFieldEditable(false);

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
            
            userModEdit = true;
            aUserNewUserButton.setVisible(false);
            aUserCancelButton.setVisible(true);
            aUsersEditSaveButton.setText("Save");
            
            setUserTabTextFieldEditable(true);
            
            aUserGrupChoiceBox.setDisable(false);
            aUserGrupChoiceBox.setValue(tmpUserGrup);
            
            tmpUserLogin = aUserLoginTextField.getText();
            tmpUserName  = aUserNameTextField.getText();
            tmpUserEmail = aUserEmailTextField.getText();
            tmpUserPhone = aUserPhoneTextField.getText();
            
            aUserDeleteButton.setVisible(false);
            // = aUserGrupChoiceBox.
        }
    }
    
    @FXML
    public void aUserCancelAction(){
        //if(userEdit){
        userModEdit = false;
        aUserCancelButton.setVisible(false);
        aUserNewUserButton.setVisible(true);
        aUsersEditSaveButton.setText("Edit");

        setUserTabTextFieldEditable(false);

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
    
    @FXML
    public void aUserNewUserAction(){
        if(userModNew){//cancel button
            userModNew = false;
    
            aUserSaveButton.setVisible(false);
            if(!userGrupSelected){
                aUsersEditSaveButton.setVisible(true);
                aUserDeleteButton.setVisible(true);
            }
            aUserNewUserButton.setText("New User");
            
            aUserGrupChoiceBox.setDisable(true);
            
            setUserTabTextFieldDef();
            
        }else{//new user button
            userModNew = true;
            
            aUsersEditSaveButton.setVisible(false);
            aUserSaveButton.setVisible(true);
            aUserDeleteButton.setVisible(false);
            aUserNewUserButton.setText("Cancel");
            
            aUserPasswordTextField.setText("user");
            
            //setUserTabTextFieldTextDef();
            aUserLoginTextField.setText("");
        aUserPasswordTextField.setText("");
        aUserNameTextField.setText("");
        aUserEmailTextField.setText("");
        aUserPhoneTextField.setText("");
            setUserTabTextFieldEditable(true);
            
            aUserGrupChoiceBox.setDisable(false);
        }
    }
    
    @FXML
    public void aUserSaveAction() throws ClassNotFoundException, SQLException{ //save button
        userModNew = false;
            
        aUserSaveButton.setVisible(false);
        aUsersEditSaveButton.setVisible(true);
        aUserDeleteButton.setVisible(true);
        aUserNewUserButton.setText("New User");
        
        setUserTabTextFieldEditable(false);
        
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
        setUserTabButtonDef();
        myInit();
    }
    
    /**
     * End of User Tab
     */
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
    /**
     *  Grup Tab Actions Button, Variables and Methods
     */
    
    ///Grups FXML Varibles
    @FXML
    TreeView aGrupsTreeView;
    @FXML
    TextField aGrupNameTextField;
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
    
    boolean grupEdit;
    boolean grupAdd;
    
    String tmpGrupName;
    
    public void initGrupTabView(){
        
        grupEdit = false;
        grupAdd = false;
        
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
}
    
    public void setGrupTabView(TreeItem<String> item){
        if(item == null) return;    

        String root_string = item.getValue();
        if(root_string.equals("root_root_root"))
            return;
        
        /*
            aGrupNameTextField.setEditable(false);
            
            aGrupEditSaveButton.setVisible(false);
            aGrupDeletButton.setVisible(false);
            userGrupSelected = true;
            return;
        }
//*/
        for(int i=0; i<date.admin.grups.length; i++){
            if(item.getValue().equals(date.admin.grups[i].getName())){
               aGrupNameTextField.setText(date.admin.grups[i].getName());
            }
        }
    }
    
    @FXML
    private void aGrupEditSaveAction() throws ClassNotFoundException, SQLException{
        if(aGrupNameTextField.getText().equals("admin")) return;
        if(grupEdit){
            if(!aGrupNameTextField.getText().equals(""))
            {
                aGrupEditSaveButton.setText("Edit");
                aGrupEditCancelButton.setVisible(false);
                aGrupNewGrupButton.setVisible(true);
                
                aGrupNameTextField.setEditable(false);
                grupEdit = false;

                for(int i=0; i<date.admin.grups.length;i++){
                    if(date.admin.grups[i].getName().equals(tmpGrupName)){
                        date.admin.grups[i].setName(aGrupNameTextField.getText());
                        System.out.println(date.admin.grups[i].getName());
                        
                        VODBC.updateGroupName(tmpGrupName,aGrupNameTextField.getText());
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
            
//            if(!aTabGrupSelected){
//                aUsersEditSaveButton.setVisible(true);
//                aUserDeleteButton.setVisible(true);
//            }
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
    
    //Save Action for New Grup
    @FXML
    private void aGrupNewGrupSaveAction() throws ClassNotFoundException,
      SQLException{
        if(grupAdd){
            grupAdd = false;
            aGrupNewGrupButton.setText("New Grup");
            aGrupNewGrupSaveButton.setVisible(false);
            aGrupEditSaveButton.setVisible(true);
            VODBC.addGroup(aGrupNameTextField.getText());
            date.admin.addGrup(aGrupNameTextField.getText());
            aGrupDeletButton.setVisible(true);
            aGrupNameTextField.setEditable(false);
            myInit();
        }
    }
    
    //Delete Grup Action
    @FXML
    private void aGrupDeletAction() throws ClassNotFoundException, SQLException{
        if(aGrupNameTextField.getText().equals("admin")) return;
        VODBC.deleteGroup(aGrupNameTextField.getText());
        date.admin.delateGrup(aGrupNameTextField.getText());
        aGrupDeletButton.setVisible(false);
        aGrupEditSaveButton.setVisible(false);
        aGrupNameTextField.setText("");
        aGrupNameTextField.setEditable(false);
        myInit();
    }
    
    /**
     * End of Grup Tab Action Button, Variables and Methods
     */
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
    /**
     *  Courses Tab Action, Variables and Methods
     */
    
    @FXML
    TreeView aCoursesTreeView;
    
    @FXML
    TextField aCouTextField;
    
    @FXML
    Button aCouEditSaveButton;
    @FXML
    Button aCouSaveButton;
    @FXML
    Button aCouNewCencelButton;
    @FXML
    Button aCouCencelButton;
    @FXML
    Button aCouDeleteButton;
    
    private TreeItem<String> coursesTreeItem_childNode[];
    private TreeItem<String> coursesTreeItem_childNodeNode[][];
    private TreeItem<String> coursesTreeItem_root = new TreeItem<>("root_root_root");
    
    String courseTarget;
    
    public void initCoursesTabView() throws ClassNotFoundException, SQLException{
        coursesTreeItem_root = new TreeItem<>("root_root_root");
        coursesTreeItem_childNode = new TreeItem[date.admin.courses.length];
        coursesTreeItem_childNodeNode = new TreeItem[date.admin.courses.length][];
        coursesTreeItem_root.setExpanded(true); 
        for(int i=0; i<date.admin.courses.length; i++){
            coursesTreeItem_childNode[i] = new TreeItem<>(date.admin.courses[i].getName());
            if(date.admin.courses[i].grups != null){
                coursesTreeItem_childNodeNode[i] = new TreeItem[date.admin.courses[i].grups.length];
                for(int j=0; j<date.admin.courses[i].grups.length; j++){
                    coursesTreeItem_childNodeNode[i][j] = new TreeItem<>(date.admin.courses[i].grups[j].getName());
                    coursesTreeItem_childNode[i].getChildren().add(coursesTreeItem_childNodeNode[i][j]);
                }
            }
            else{
                coursesTreeItem_childNodeNode[i] = new TreeItem[1];
            }
            coursesTreeItem_root.getChildren().add(coursesTreeItem_childNode[i]);
        }
        aCoursesTreeView.setRoot(coursesTreeItem_root);
        aCoursesTreeView.setShowRoot(false);

        aCoursesTreeView.setOnMouseClicked(new EventHandler<MouseEvent>(){
        @Override
        public void handle(MouseEvent mouseEvent){            
            if(mouseEvent.getClickCount() == 1){

//
                setCouTabView((TreeItem<String>)aCoursesTreeView.getSelectionModel().getSelectedItem());
            }}});
    }
    
    public void setCouTabDef(){
        aCouTextField.setEditable(false);
        aCouTextField.setText("");
        aCouEditSaveButton.setVisible(false);
        aCouEditSaveButton.setText("Edit");
        
        aCouSaveButton.setVisible(false);
        aCouNewCencelButton.setVisible(true);
        aCouNewCencelButton.setText("New");
        aCouCencelButton.setVisible(false);
        aCouDeleteButton.setVisible(false);
            couEditMode = false;
    }

    public void setCouTabView(TreeItem<String> item){
        if(item == null) {
            setCouTabDef();
            return;
        }    

        if(item.getValue().equals("root_root_root")){
            setCouTabDef();
            return;
        }

        if(item.getParent().getValue().equals("root_root_root")){
            courseTarget = item.getValue();
        }else courseTarget = item.getParent().getValue();

        for(int i=0; i<date.admin.courses.length; i++){
            if(courseTarget.equals(date.admin.courses[i].getName())){
                aCouTextField.setText(date.admin.courses[i].getName());
            }
        }
        
        aCouEditSaveButton.setVisible(true);
        aCouDeleteButton.setVisible(true);
    }

    boolean couEditMode;
    boolean couNewMode;
    
    String tmpCourse = null;
    
    @FXML  //1
    public void aCouEditSaveAction() throws ClassNotFoundException, SQLException{
        if(couEditMode){ //Save Button
            couEditMode = false;
            aCouEditSaveButton.setText("Edit");
            aCouCencelButton.setVisible(false);
            aCouNewCencelButton.setVisible(true);
            
            aCouTextField.setEditable(false);
            
            Course cou = new Course();
            
            cou.setName(aCouTextField.getText());
            
            cou.tmp = tmpCourse;
            
            aCouDeleteButton.setVisible(true);
            VODBC.updateCource(cou);
            myInit();
        }else{ //edit Button
            couEditMode = true;
            aCouEditSaveButton.setText("Save");
            aCouNewCencelButton.setVisible(false);
            aCouCencelButton.setVisible(true);
            aCouTextField.setEditable(true);
            
            tmpCourse = aCouTextField.getText();
            aCouDeleteButton.setVisible(false);
        }
    }

    
    @FXML 
    public void aCouSaveAction() throws ClassNotFoundException, SQLException{
        couNewMode = false;
            
        aCouSaveButton.setVisible(false);
        aCouEditSaveButton.setVisible(true);
        aCouDeleteButton.setVisible(true);
        aCouNewCencelButton.setText("New User");
        
        aCouTextField.setEditable(false);
        
        Course cou = new Course();
            
        cou.setName(aCouTextField.getText());
        
        VODBC.addCourse(cou);
        myInit();
        date.admin.courses = VODBC.getCourses();
        initCoursesTabView();
        
    }
    @FXML //1
    public void aCouNewCencelAction(){
        if(couNewMode){//cancel button
            couNewMode = false;
    
            aCouSaveButton.setVisible(false);
            //f(!userGrupSelected){
                aCouEditSaveButton.setVisible(true);
                aCouDeleteButton.setVisible(true);
            //}
            aCouNewCencelButton.setText("New User");
            
            aCouTextField.setEditable(false);
            aCouTextField.setText("");
            
        }else{//new user button
            couNewMode = true;
            
            aCouEditSaveButton.setVisible(false);
            aCouSaveButton.setVisible(true);
            aCouDeleteButton.setVisible(false);
            aCouNewCencelButton.setText("Cancel");
            
            aCouTextField.setText("");
            aCouTextField.setEditable(true);
        }
    }
    
    @FXML 
    public void aCouCencelAction(){
        //if(userEdit){
        couEditMode = false;
        aCouCencelButton.setVisible(false);
        aCouNewCencelButton.setVisible(true);
        aCouEditSaveButton.setText("Edit");

        aCouTextField.setEditable(false);

        aCouTextField.setText(tmpCourse);
        aCouDeleteButton.setVisible(true);
    }
    
    @FXML 
    public void aCouDeleteAction() throws ClassNotFoundException, SQLException{

        VODBC.deleteCourse(aCouTextField.getText());
        setCouTabDef();
        myInit();
    }
   
    /**
     *  End of Courses Tab Action, Variables and Methods
     */
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
    /**
     *  Lab Tab Action, Variables and Methods
     */
    
    @FXML
    TreeView aLabTreeView;
    
    @FXML 
    TextField aLabNameTextField;
    
    @FXML
    DatePicker aDeadlineDatePicker;
    
    @FXML
    ChoiceBox aLabCourseChoiceBox;
    
    @FXML
    TextArea aLabDescription;
    
    @FXML
    Button aLabSaveEditButton;
    @FXML
    Button aLabNewButton;
    @FXML
    Button aLabDeleteButton;
    @FXML
    Button aLabSaveButton;
    @FXML
    Button aLabCancelButton;
    
    private TreeItem<String> LabTreeItem_childNode[];
    private TreeItem<String> LabTreeItem_childNodeNode[][];
    private TreeItem<String> LabTreeItem_root = new TreeItem<>("root_root_root");
    
    public void initLabTabView() throws ClassNotFoundException, SQLException{
        LabTreeItem_root = new TreeItem<>("root_root_root");
        LabTreeItem_childNode = new TreeItem[date.admin.courses.length];
        LabTreeItem_childNodeNode = new TreeItem[date.admin.courses.length][];
        LabTreeItem_root.setExpanded(true); 
        for(int i=0; i<date.admin.courses.length; i++){
            LabTreeItem_childNode[i] = new TreeItem<>(date.admin.courses[i].getName());
            if(date.admin.courses[i].labs != null){
                LabTreeItem_childNodeNode[i] = new TreeItem[date.admin.courses[i].labs.length];
                for(int j=0; j<date.admin.courses[i].labs.length; j++){
                    LabTreeItem_childNodeNode[i][j] = new TreeItem<>(date.admin.courses[i].labs[j].getName());
                    LabTreeItem_childNode[i].getChildren().add(LabTreeItem_childNodeNode[i][j]);
                }
            }
            else{
                LabTreeItem_childNodeNode[i] = new TreeItem[1];
            }
            LabTreeItem_root.getChildren().add(LabTreeItem_childNode[i]);
        }
        aLabTreeView.setRoot(LabTreeItem_root);
        aLabTreeView.setShowRoot(false);
    }
    
   
    
    @FXML
    public void aLabNewAction(){}
    
    @FXML
    public void aLabSaveEditAction(){}
    
    @FXML
    public void aLabDeleteAction(){}
    
    @FXML
    public void aLabSaveAction(){}
    
    @FXML
    public void aLabCancelAction(){}
    
    
    /**
     *  End of Courses Tab Action, Variables and Methods
     */
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
//    /**
//     *  Reating Tab Action Button, Varibles and Methods
//     */
//    @FXML
//    TreeView aRatingTreeView;
//    
//    @FXML
//    TableView<String[]>  aUserRatingTable;
//    
//    public void initRatingTabView(){
//    //USER LAB TAB
//        //fill treeView
//            aul_root = new TreeItem<>("root_root_root");
//            aul_childNode = new TreeItem[date.courses.length];          //courses
//            aul_childNodeNude = new TreeItem[date.courses.length][];    //groups
//            aul_root.setExpanded(true); 
//            
//            //Is this supposed to get all the users?
//            
//            for(int i=0; i<date.courses.length; i++){
//                aul_childNode[i] = makeBranch(date.courses[i].getName(), aul_root);
//                aul_childNodeNude[i] = new TreeItem[date.courses[i].stringGrups.length];
//                for (int j = 0; j < date.courses[i].stringGrups.length; j++) {
//                    aul_childNodeNude[i][j] = makeBranch(date.courses[i].stringGrups[j], aul_childNode[i]);
//                }
//                
//            }
//            
//            aRatingTreeView.setRoot(aul_root);
//            aRatingTreeView.setShowRoot(false);
//        
//            aRatingTreeView.setOnMouseClicked(new EventHandler<MouseEvent>(){
//            @Override
//            public void handle(MouseEvent mouseEvent){            
//                if(mouseEvent.getClickCount() == 1){
//                    try {
//                        setUserTabView((TreeItem<String>)aRatingTreeView.getSelectionModel().getSelectedItem());
//                        drawTableInfo((TreeItem<String>)aRatingTreeView.getSelectionModel().getSelectedItem());
//                    } catch (ClassNotFoundException | SQLException ex) {
//                        Logger.getLogger(AdminViewController.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//            }});
//            
//        //end fill treeview
//        //fill table
//                //курс -> groups -> rating
//        //end fill table
//               
//    //END USER LAB TAB
////end 
//    }
//    
//    public void drawTableInfo(TreeItem<String> item) throws ClassNotFoundException, SQLException{
//        aUserRatingTable.getColumns().clear();
//
//        int columnNumber=0;
//        TableColumn tableHeader = new TableColumn("Students");
//        aUserRatingTable.getColumns().add(tableHeader);
//        TableColumn[] labs=null;
//        String[] labNames=null;
//        
//        String item_name = item.getValue();
//        if(item_name == null) return;
//        if(item_name.equals("root_root_root"))
//            return;
//            
//        String parent_name = item.getParent().getValue();
//            if(parent_name.equals("root_root_root"))
//                return;
//        
//        try{
//            columnNumber = LODBC.aGetLabaNumber(parent_name);
//            labNames = LODBC.aGetLabaNames(parent_name, columnNumber);
//            labs = new TableColumn[columnNumber];
//        }catch(SQLException e){
//            System.out.println(e.getMessage());
//        }
//        
//        //I'm going to do this via two-dimenshional array. So, i need an array 1 row bigger, for columns
//        
//        /*
//        columns  : {lab,lab,lab}
//        makarenko: {"4","5","2"}
//        vaslienko: {"3","6","1"}
//        */
//        
//        int studentsNumber = LODBC.aGetNumberofStudentsInGroup(item_name);
//        int labsNumer = LODBC.aGetLabaNumber(parent_name);
//        String[][] kek;
//        kek = new String[studentsNumber+1][labsNumer];
//        String[] headers = new String[labsNumer];
//        
//        System.out.println(studentsNumber);
//        System.out.println(parent_name);
//        
//        for(int i = 0; i< studentsNumber; i++){
//            kek[i] = LODBC.aGetAllStudentsPoints(parent_name, item_name, labsNumer);
//        }
//        
//        
//        //now we need to fill the table up. We have selected a group, and a course. so, we need to display all the students of current group.
//        //let's try to do some stuff
//        
//        //we need to get the list of all students in a group. Then, put them in the first field.
//        
//        //after that's done, we need to grab a certain student's name and feed it to a procedure, that will return an array? of his marks
//        
//        ObservableList<String[]> data = FXCollections.observableArrayList();
//        data.addAll(Arrays.asList(kek));
//        
//        for (int i = 0; i < kek[0].length; i++) {
//            TableColumn tc  = new TableColumn(labNames[i]);
//            final int colNo = i;
//            tc.setCellValueFactory(new Callback<CellDataFeatures<String[], String>, ObservableValue<String>>() {
//                @Override
//                public ObservableValue<String> call(CellDataFeatures<String[], String> p) {
//                    return new SimpleStringProperty((p.getValue()[colNo]));
//                }
//            });
//            tc.setPrefWidth(90);
//            aUserRatingTable.getColumns().add(tc);
//        }
//        aUserRatingTable.setItems(data);
//    }
//    
//    /**
//     *  End of Reating Tab Action Button, Varibles and Methods
//     */
}
