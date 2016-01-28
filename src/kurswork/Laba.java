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
public class Laba {
    private String name;
    private String deadline;
    private String result;
    private String description;
    
    public Laba(String name, String deadline){
        this.name = name;
        this.deadline = deadline;
    }
    
    public String getDescription(){
        return this.description;
    }
    
    public void setDescription(String newDescription){
        this.description = newDescription;
    }
    
    public String getResult(){
        return this.result;
    }
    
    public void setResult(String result){
        this.result = result;
    }
    
    public String getDeadline(){
        return this.deadline;
    }
    
    public void setDeadline(String deadline){
        this.deadline = deadline;
    }
    
    public String getName(){
        return this.name;
    }
    
    public void setName(String name){
        this.name = name;
    }
}