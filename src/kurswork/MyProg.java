/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kurswork;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 

final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
messageDigest.reset();
messageDigest.update(string.getBytes(Charset.forName("UTF8")));
final byte[] resultByte = messageDigest.digest();
final String result = new String(Hex.encodeHex(resultByte));
 * 
 * 
 * 
 *
 * @author Vlasov
 */
public class MyProg extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/kurswork/loginView/Login.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("/kurswork/adminView/AdminView.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("/kurswork/userView/UserView.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
        
    }
    
}
