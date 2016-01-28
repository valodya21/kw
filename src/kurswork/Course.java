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
    public String stringGrups[] = null;
    public Grup grups[];
    public Laba labs[] = null;
    
    public String tmp;
    
    public Course(String name){
        this.name = name;
    }

    public Course() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void addGrup(Grup grup) {
        if(grups == null){
            grups = new Grup[1];
            grups[0] = grup;
        }else{
            Grup tmpGrups[] = new Grup[grups.length];
            System.arraycopy(grups, 0, tmpGrups, 0, grups.length);
            grups = new Grup[tmpGrups.length+1];
            System.arraycopy(tmpGrups, 0, grups, 0, tmpGrups.length);
            grups[tmpGrups.length] = grup;
        }
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
        return stringGrups;
    }
    
    //topin212
    public String getGrupsAt(int at){
        return stringGrups[at];
    }
    
}

