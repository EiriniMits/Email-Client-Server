/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emailserver;

import java.util.ArrayList;
/**
 *
 * @author Eirini Mitsopoulou
 */
public class SaveAccounts {

    private final String name;
    private final String code;
    private final ArrayList<String> emails = new ArrayList<>();

    public SaveAccounts(String username,String password,String []mail){
        name = username;
        code = password;
        for(int i=0;i<5;i++){                     
                emails.add(mail[i]);    
        }

    }
    
    public String getUsername(){
        return name;
    }
    
    public String getPassword(){
        return code;
    }
    
    public String[] getEmails(){ 
        int i;
        String[] emailss = new String[5];
        for(i=0;i<5;i++){
            emailss[i] = emails.get(i);
        }   
        return emailss;
    }
    //delete the NEW because you read that email
    public void changeEmails(){ 
        emails.set(1, ".      ");  
    }
    //When you delete an email you have to put the ids in a row, so change the emails ids
    public void changeidEmails(int num){ 
        emails.set(0,num+"");  
    }
    
    
}
