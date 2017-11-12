/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emailserver;

import java.util.ArrayList;
import java.util.Iterator;
/**
 *
 * @author Eirini Mitsopoulou
 */
public class EmailStorage {
    ArrayList<SaveAccounts> accounts;
    public EmailStorage(){
        accounts= new ArrayList<>();
    
    }
    //add an email
    public void addEmail(SaveAccounts c){

       accounts.add(c);
      
    }
    // check if a username exists
    public int existname(String username){
        for(SaveAccounts s: accounts){
           
            if(s.getUsername().equals(username) )
            {
                return 0;
            }
        
        }
        return 1;
    }
    // check if the code belongs to username
    public int existcode(String name,String code){
        for(SaveAccounts s: accounts){
            
            if(s.getUsername().equals(name) )
            {
                if(s.getPassword().equals(code)){
                return 0;}
            }
        
        }
        return 1;
    }
    //return the password according to a username
    public String getPassword(String name){
        for(SaveAccounts s: accounts){
           
            if(s.getUsername().equals(name) )
            {
                return s.getPassword();
            }
        
        }
        return null;
    }
    // return the arraylist of emails
    public String[] getEmails(String name){
        String[] emails = new String[100];
        int i=1,j=0;
        for(j=0;j<100;j++){
            emails[j] = "-";
        }
        for(SaveAccounts s: accounts){
           
            if(s.getUsername().equals(name) )
            {
                String[] emailss = new String[5];
                emailss = s.getEmails();
                emails[i] = emailss[0];
                emails[++i] = emailss[1];
                emails[++i] = emailss[2];
                emails[++i] = emailss[3];
                emails[++i] = emailss[4];
                i++;
            }
        
        }
        return emails;
    }
    // return the number of email that a user has in his account
    public int numEmails(String name){
        int count=0;
        for(SaveAccounts s: accounts){
            
            if(s.getUsername().equals(name) )
            {
                String[] emailss = new String[5];
                emailss = s.getEmails();
                if(!emailss[0].equals("")){
                count++;}
            }
        
        }
        return count;
    }
    // return the main bosy of the email with this id
    public String readEmail(String name,String id){
        for(SaveAccounts s: accounts){
            
            if(s.getUsername().equals(name) )
            {
                String[] emailss = new String[5];
                emailss = s.getEmails();
                if(emailss[0].equals(id)){
                    if(emailss[1].equals(". [New]")){//if it's new delete the new
                        s.changeEmails();
                    }
                    return emailss[4];
                }
            }
        
        }
        return "error";
    }
    // delete an email
    public void deleteEmail(String name,String id){
        
        Iterator<SaveAccounts> it = accounts.iterator();        
        while (it.hasNext()) {
            SaveAccounts l = it.next();
            if (l.getUsername().equals(name)) {
                String[] emailss = new String[5];
                emailss = l.getEmails();
                if(emailss[0].equals(id)){
                    it.remove(); 
                    return;
                }
            }
        }
    }
    // When you delete an email you have to put the ids in a row
    public void changeidEmails(String name){ 
       int count=0;
        for(SaveAccounts s: accounts){
            
            if(s.getUsername().equals(name) )
            {
                String[] emailss = new String[5];
                emailss = s.getEmails();
                if(!emailss[0].equals("")){
                count++;
                s.changeidEmails(count);
                }
            }
        
        }  
    }
    
}
