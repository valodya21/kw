/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kurswork;

/**
 *
 * @author Vlasov
 */
public class User {
    private String Login;
    private String Name;
    private String Password;
    private String PermissionLevel;
    private String grup;
    
    public String tmp;
    
    public Course course[];
    private String phone;
    private String email;
    
    public String getPermissionLevel(){
        return this.PermissionLevel;
    }
    
    public String getLogin(){
        return this.Login;
    }
    
    public String getPassword(){
        return this.Password;
    }
    
    public void setPermissionLevel(String level){
        this.PermissionLevel = level;
    }
    
    public void setLogin(String login){
        this.Login = login;
    }
    
    public void setPassword(String pass){
        this.Password = pass;
    }

    public String getGrup(){
        return this.grup;
    }
    
    public void setGrup(String grup) {
        this.grup = grup;
    }
    
    public void setName(String name){
        this.Name = name;
    }

    public String getName() {
        return this.Name;
    }
    
    public void setPhone(String phone){
        this.phone = phone;
    }

    public String getPhone() {
        return this.phone;
    }
    
    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }
}
