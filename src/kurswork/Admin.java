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
public class Admin {
    public Grup grups[];

    public void addGrup(String text) {
        if(grups == null){
            grups = new Grup[1];
            grups[0] = new Grup();
            grups[0].setName(text);;
        }else{
            Grup tmpGrups[] = new Grup[grups.length];
            System.arraycopy(grups, 0, tmpGrups, 0, grups.length);
            grups = new Grup[tmpGrups.length+1];
            System.arraycopy(tmpGrups, 0, grups, 0, tmpGrups.length);
            grups[tmpGrups.length] = new Grup();
            grups[tmpGrups.length].setName(text);
        }
    }
    

    public void delateGrup(String text) {
        if(grups.length == 1){
            grups = null;
        }else{
            Grup tmpGrups[] = new Grup[grups.length];
            System.arraycopy(grups, 0, tmpGrups, 0, grups.length);
            grups = new Grup[tmpGrups.length-1];
            for(int i=0, j=0; i<grups.length; i++, j++){
                if(!tmpGrups[i].getName().equals(text))
                {
                    grups[j] = tmpGrups[i];
                    
                }else{
                    i++;
                    grups[j] = tmpGrups[i];
                }
            }
        }
    }
}
