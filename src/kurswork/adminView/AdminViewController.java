/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kurswork.adminView;


import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import kurswork.MainTransferDate;
import kurswork.VODBC.VODBC;
import kurswork.VODBC.LODBC;

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
    //topin212
    @FXML
    TreeView aUserLabsTreeView;
    //}
    @FXML
    TableView<String[]>  aUserLabsStudentTable;
    
    
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
    //USER LAB TAB
    private TreeItem<String> aul_childNode[];
    private TreeItem<String> aul_childNodeNude[][];
    TreeItem<String> aul_root = new TreeItem<>("root_root_root");
    
    private TreeItem<String> ag_childNode[];
    TreeItem<String> ag_root = new TreeItem<>("root_root_root");
    
    public TreeItem<String> makeBranch (String title, TreeItem<String> perent){
        TreeItem<String> item = new TreeItem<>(title);
        item.setExpanded(true);
        perent.getChildren().add(item);
        return item;
    }
    
    //topin212
    public void drawTableInfo(TreeItem<String> item) throws ClassNotFoundException, SQLException{
        aUserLabsStudentTable.getColumns().clear();

        int columnNumber=0;
        TableColumn tableHeader = new TableColumn("Students");
        aUserLabsStudentTable.getColumns().add(tableHeader);
        TableColumn[] labs=null;
        String[] labNames=null;
        
        String item_name = item.getValue();
        if(item_name.equals("root_root_root"))
            return;
        else if(item_name == null)
            return;
    
        String parent_name = item.getParent().getValue();
            if(parent_name.equals("root_root_root"))
                return;
        
        try{
            columnNumber = LODBC.aGetLabaNumber(parent_name);
            labNames = LODBC.aGetLabaNames(parent_name, columnNumber);
            labs = new TableColumn[columnNumber];
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
        //I'm going to do this via two-dimenshional array. So, i need an array 1 row bigger, for columns
        
        /*
        columns  : {lab,lab,lab}
        makarenko: {"4","5","2"}
        vaslienko: {"3","6","1"}
        */
        
        int studentsNumber = LODBC.aGetNumberofStudentsInGroup(item_name);
        int labsNumer = LODBC.aGetLabaNumber(parent_name);
        String[][] kek;
        kek = new String[studentsNumber+1][labsNumer];
        String[] headers = new String[labsNumer];
        
        System.out.println(studentsNumber);
        System.out.println(parent_name);
        
        for(int i = 0; i< studentsNumber; i++){
            kek[i] = LODBC.aGetAllStudentsPoints(parent_name, item_name, labsNumer);
        }
        
        
        //now we need to fill the table up. We have selected a group, and a course. so, we need to display all the students of current group.
        //let's try to do some stuff
        
        //we need to get the list of all students in a group. Then, put them in the first field.
        
        //after that's done, we need to grab a certain student's name and feed it to a procedure, that will return an array? of his marks
        
        ObservableList<String[]> data = FXCollections.observableArrayList();
        data.addAll(Arrays.asList(kek));
        
        for (int i = 0; i < kek[0].length; i++) {
            TableColumn tc  = new TableColumn(labNames[i]);
            final int colNo = i;
            tc.setCellValueFactory(new Callback<CellDataFeatures<String[], String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(CellDataFeatures<String[], String> p) {
                    return new SimpleStringProperty((p.getValue()[colNo]));
                }
            });
            tc.setPrefWidth(90);
            aUserLabsStudentTable.getColumns().add(tc);
        }
        aUserLabsStudentTable.setItems(data);
    }
    
    public void myInit() throws ClassNotFoundException,
      SQLException{
        grupEdit = false;
        grupAdd = false;
        date.admin= VODBC.aLoadAdmin(); 
        date.courses = LODBC.aLoadCourses();
        
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
                if(mouseEvent.getClickCount() == 1)
                    setUserTabView((TreeItem<String>)aUsersTreeView.getSelectionModel().getSelectedItem());
            }});
        
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
                    setGrupTabView((TreeItem<String>)aGrupsTreeView.getSelectionModel().getSelectedItem());
                }}});
        
//        //COURSES TAB - TreeView
//        ac_childNode = new TreeItem[date.admin.grups.length];
//        ac_root.setExpanded(true); 
//        for(int i=0; i<date.admin.grups.length; i++){
//            ac_childNode[i] = new TreeItem<>(date.admin.grups[i].getName());
//            ac_root.getChildren().add(ac_childNode[i]);
//            }
//            aGrupsTreeView.setRoot(ag_root);
//            aGrupsTreeView.setShowRoot(false);
//        
//            aGrupsTreeView.setOnMouseClicked(new EventHandler<MouseEvent>(){
//            @Override
//            public void handle(MouseEvent mouseEvent){            
//                if(mouseEvent.getClickCount() == 1){}
//                    //setInfoLab((TreeItem<String>)MyNewTree.getSelectionModel().getSelectedItem());
//            }});
        
        //USER TAB - Content
        
        
//        aUserGrupChoiceBox;
            String obs[] = new String[date.admin.grups.length];
            for(int i=0; i<date.admin.grups.length;i++)
                obs[i] = date.admin.grups[i].getName();
            
            aUserGrupChoiceBox.setItems(FXCollections.observableArrayList(obs));
            
            
            
//Topin212
    //USER LAB TAB
        //fill treeView
            aul_root = new TreeItem<>("root_root_root");
            aul_childNode = new TreeItem[date.courses.length];          //courses
            aul_childNodeNude = new TreeItem[date.courses.length][];    //groups
            aul_root.setExpanded(true); 
            
            //Is this supposed to get all the users?
            
            for(int i=0; i<date.courses.length; i++){
                aul_childNode[i] = makeBranch(date.courses[i].getName(), aul_root);
                aul_childNodeNude[i] = new TreeItem[date.courses[i].grups.length];
                for (int j = 0; j < date.courses[i].grups.length; j++) {
                    aul_childNodeNude[i][j] = makeBranch(date.courses[i].grups[j], aul_childNode[i]);
                }
                
            }
            
            aUserLabsTreeView.setRoot(aul_root);
            aUserLabsTreeView.setShowRoot(false);
        
            aUserLabsTreeView.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent mouseEvent){            
                if(mouseEvent.getClickCount() == 1){
                    try {
                        setUserTabView((TreeItem<String>)aUserLabsTreeView.getSelectionModel().getSelectedItem());
                        drawTableInfo((TreeItem<String>)aUserLabsTreeView.getSelectionModel().getSelectedItem());
                    } catch (ClassNotFoundException | SQLException ex) {
                        Logger.getLogger(AdminViewController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }});
            
        //end fill treeview
        //fill table
                //курс -> groups -> rating
        //end fill table
                
                
                
    //END USER LAB TAB
//end Topin212            
            
    }

    public void setUserTabView(TreeItem<String> item){
    if(item == null) return;    
        
    String root_string = item.getValue();
        if(root_string.equals("root_root_root"))
            return;
    
    root_string = item.getParent().getValue();
        if(root_string.equals("root_root_root"))
            return;
            
    for(int i=0; i<date.admin.grups.length; i++){
        if(item.getParent().getValue().equals(date.admin.grups[i].getName())){
           for(int j=0; j<date.admin.grups[i].users.length; j++){
               if(item.getValue().equals(date.admin.grups[i].users[j].getName())){
                   
                    aUserLoginTextField.setText(date.admin.grups[i].users[j].getLogin());
                    aUserEmailTextField.setText(date.admin.grups[i].users[j].getEmail());
                    aUserPhoneTextField.setText(date.admin.grups[i].users[j].getPhone());
                    aUserNameTextField.setText(date.admin.grups[i].users[j].getName());
                    
                    aUserGrupChoiceBox.setValue(date.admin.grups[i].getName());
                }
                }
            }
        }
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
    
    
    
    String tmpGrupName;
    
    @FXML//group
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
