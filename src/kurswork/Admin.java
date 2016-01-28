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

    public Course courses[];
//    public Course Lcourses[];
    
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
    
    public void addCourse(String text) {
        if(courses == null){
            courses = new Course[1];
            courses[0] = new Course();
            courses[0].setName(text);;
        }else{
            Course tmpCourses[] = new Course[courses.length];
            System.arraycopy(courses, 0, tmpCourses, 0, courses.length);
            courses = new Course[tmpCourses.length+1];
            System.arraycopy(tmpCourses, 0, courses, 0, tmpCourses.length);
            courses[tmpCourses.length] = new Course();
            courses[tmpCourses.length].setName(text);
        }
    }
    
    public void delateCourse(String text) {
        if(courses.length == 1){
            courses = null;
        }else{
            Course tmpGrups[] = new Course[courses.length];
            System.arraycopy(courses, 0, tmpGrups, 0, courses.length);
            courses = new Course[tmpGrups.length-1];
            for(int i=0, j=0; i<courses.length; i++, j++){
                if(!tmpGrups[i].getName().equals(text))
                {
                    courses[j] = tmpGrups[i];
                    
                }else{
                    i++;
                    courses[j] = tmpGrups[i];
                }
            }
        }
    }
    
    
}
