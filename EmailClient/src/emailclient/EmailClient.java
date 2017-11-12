/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emailclient;

import java.net.*;
import java.io.*;
import java.util.Scanner;
/**
 *
 * @author Eirini Mitsopoulou
 */

public class EmailClient
{
   public static void main(String [] args)
   {
      String serverName = new String ("127.0.0.1");
      int port = 9000;
      String ap="";
      try
      {
          Socket client = new Socket(serverName, port);
          OutputStream outToServer = client.getOutputStream();
         DataOutputStream out = new DataOutputStream(outToServer);
         InputStream inFromServer = client.getInputStream();
         DataInputStream in = new DataInputStream(inFromServer);
         System.out.println("­­­­­­­­­­­");
            System.out.println("MailServer:");
            System.out.println("­­­­­­­­­­­");
            System.out.println("Hello, you connected as a guest. ");
            System.out.println("­­­­­­­­­­­");
            System.out.println("­­­­­­­­­­­");
            System.out.println(">LogIn");
            System.out.println(">SignIn");
            System.out.println(">Exit");
            System.out.println("­­­­­­­­­­­");
            System.out.println("­­­­­­­­­­­");
         Scanner s = new Scanner(System.in);
         String line = s.nextLine();
         out.writeUTF(line);
         out.flush();
    while(!line.equals("Exit")){
         if(line.equals("LogIn")){
            System.out.println("­­­­­­­­­­­");
            System.out.println("MailServer:");
            System.out.println("­­­­­­­­­­­");
            System.out.println("Type your username");
            String username = s.nextLine();
            out.writeUTF(username);
            out.flush();
            if (in.readUTF().equals("Yes")){
                System.out.println("­­­­­­­­­­­");
                System.out.println("MailServer:");
                System.out.println("­­­­­­­­­­­");
                System.out.println("Type your password");
                String password = s.nextLine();
                out.writeUTF(password);
                out.flush();
                if (in.readUTF().equals("Yes")){
                    System.out.println("­­­­­­­­­­­");
                    System.out.println("MailServer:");
                    System.out.println("­­­­­­­­­­­");
                    System.out.println("Welcome back " + username +"!");
                    System.out.println("­­­­­­­­­­­");
                    System.out.println("­­­­­­­­­­­");
                    System.out.println(">NewEmail");
                    System.out.println(">ShowEmails");
                    System.out.println(">ReadEmail");
                    System.out.println(">DeleteEmail");
                    System.out.println(">LogOut");
                    System.out.println(">Exit");
                    System.out.println("­­­­­­­­­­­");
                    System.out.println("­­­­­­­­­­­");
                    ap = s.nextLine();
                    out.writeUTF(ap);
                    out.flush();
                    while(!ap.equals("LogOut")){
                    if(ap.equals("NewEmail")){
                        System.out.println("­­­­­­­­­­­");
                        System.out.println("MailServer:");
                        System.out.println("­­­­­­­­­­­");
                        System.out.println("Receiver:");
                        String r = s.nextLine();
                        out.writeUTF(r);
                        out.flush();
                        System.out.println("­­­­­­­­­­­");
                        System.out.println("MailServer:");
                        System.out.println("­­­­­­­­­­­");
                        if (in.readUTF().equals("Yes")){
                            System.out.println("Subject:");
                            String sub = s.nextLine();
                            out.writeUTF(sub);
                            out.flush();
                            System.out.println("­­­­­­­­­­­");
                            System.out.println("MailServer:");
                            System.out.println("­­­­­­­­­­­");
                            System.out.println("Main body:");
                            String m = s.nextLine();
                            out.writeUTF(m);
                            out.flush();
                            System.out.println("­­­­­­­­­­­");
                            System.out.println("MailServer:");
                            System.out.println("­­­­­­­­­­­");
                            System.out.println("Mail sent successfully!");
                        }
                        else{
                            System.out.println("Unavailable Username");
                        }
                    }
                    else if(ap.equals("ShowEmails")){
                        System.out.println("­­­­­­­­­­­");
                        System.out.println("MailServer:");
                        System.out.println("­­­­­­­­­­­");
                        System.out.println("Id           From                 Subject");
                        String output;
                        int k=0;
                        for(int j=1; j<100;j++){
                            output = in.readUTF();
                            if(output.equals("End")) break;
                            if (j % 5 != 0 && (j != (1+k))  ) {
                                 System.out.print(output+"     ");
                            }
                            if((j == (1 + k)) ){
                                System.out.print(output);
                                k = k+5;
                            }
                            if(j % 5 == 0){
                                System.out.println();
                            }
                            
                        }
                    }
                    else if(ap.equals("ReadEmail")){
                        System.out.println("­­­­­­­­­­­");
                        System.out.println("MailServer:");
                        System.out.println("­­­­­­­­­­­");
                        System.out.println("Id Email:");
                        String id = s.nextLine();
                        out.writeUTF(id);
                        out.flush();
                        if (in.readUTF().equals("Yes")){
                            System.out.println("­­­­­­­­­­­");
                            System.out.println("MailServer:");
                            System.out.println("­­­­­­­­­­­");
                            System.out.println("Main Body:");
                            System.out.println(in.readUTF());                        
                        }
                        else{
                            System.out.println("­­­­­­­­­­­");
                            System.out.println("MailServer:");
                            System.out.println("­­­­­­­­­­­");
                            System.out.println("Wrong Id");
                        }
                    }
                    else if(ap.equals("DeleteEmail")){
                        System.out.println("­­­­­­­­­­­");
                        System.out.println("MailServer:");
                        System.out.println("­­­­­­­­­­­");
                        System.out.println("Id Email:");
                        String id = s.nextLine();
                        out.writeUTF(id);
                        out.flush();
                        if (in.readUTF().equals("Yes")){
                            System.out.println("­­­­­­­­­­­");
                            System.out.println("MailServer:");
                            System.out.println("­­­­­­­­­­­");
                            System.out.println("Email Deleted Successfully!");                                
                        }
                        else{
                            System.out.println("Wrong Id");
                        }
                    }
                    else if(ap.equals("Exit")){
                                break;
                    }
                    else{
                          System.out.println(in.readUTF());
                    }
                    System.out.println("­­­­­­­­­­­");
                    System.out.println("­­­­­­­­­­­");
                    System.out.println(">NewEmail");
                    System.out.println(">ShowEmails");
                    System.out.println(">ReadEmail");
                    System.out.println(">DeleteEmail");
                    System.out.println(">LogOut");
                    System.out.println(">Exit");
                    System.out.println("­­­­­­­­­­­");
                    System.out.println("­­­­­­­­­­­");
                    ap = s.nextLine();
                    out.writeUTF(ap);
                    out.flush();
                }
      
                }
                else{
                     System.out.println("Invalid user or password");}
            }else{
                System.out.println("Invalid user or password");
            }
     
        }
        else if(line.equals("SignIn")){
            System.out.println("­­­­­­­­­­­");
            System.out.println("MailServer:");
            System.out.println("­­­­­­­­­­­");
            System.out.println("Type a username");
            String name = s.nextLine();
            out.writeUTF(name);
            out.flush();
            if (in.readUTF().equals("Yes")){
                System.out.println("­­­­­­­­­­­");
                System.out.println("MailServer:");
                System.out.println("­­­­­­­­­­­");
                System.out.println("Type a password");
                String p_w = s.nextLine();
                out.writeUTF(p_w);
                out.flush();
                System.out.println("Acount " +name+ " created successfully!");
            } 
            else{
                System.out.println("Username " +name+ " is already taken!");
            }
        }
        else{
            System.out.println(in.readUTF());
         }
         if(ap.equals("Exit")){
             break;
         }
         System.out.println("­­­­­­­­­­­");
            System.out.println("­­­­­­­­­­­");
            System.out.println(">LogIn");
            System.out.println(">SignIn");
            System.out.println(">Exit");
            System.out.println("­­­­­­­­­­­");
            System.out.println("­­­­­­­­­­­");

            line = s.nextLine();
            out.writeUTF(line);
            out.flush();
    }
         client.close();
         in.close();
           out.flush();
         out.close();
      }catch(IOException e)
      {
         e.printStackTrace();
      }
   }
}
