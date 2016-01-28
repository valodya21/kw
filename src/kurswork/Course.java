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
public class Course {
    private String name;
    private String grup;
    public String grups[] = null;
    public Laba labs[] = null;
    
    public Course(String name){
        this.name = name;
    }
    
    public void addLab(Laba lab){
        if(labs == null){
            labs = new Laba[1];
            labs[0] = lab;
        }else{
            Laba tmpLab[] = new Laba[labs.length];
            System.arraycopy(labs, 0, tmpLab, 0, labs.length);
            labs = new Laba[tmpLab.length+1];
            System.arraycopy(tmpLab, 0, labs, 0, tmpLab.length);
            labs[tmpLab.length] = lab;
        }
    }
    
    public int getLabNumber(){
        if(labs == null) return 0;
        return labs.length;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getName(){
        return this.name;
    }
    
    public void setGrup(String name){
        this.name = name;
    }
    
    public String getGrup(){
        return this.name;
    }
    
    //topin212
    public String[] getGrups(){
        return grups;
    }
    
    //topin212
    public String getGrupsAt(int at){
        return grups[at];
    }
    
}
