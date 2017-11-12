/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emailserver;

/**
 *
 * @author Eirini Mitsopoulou
 */
import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmailServer extends Thread
{
   static private SaveAccounts s;
   static private EmailStorage u;
   private static String[] emailss;
   private final ServerSocket serverSocket;
   public EmailServer(int port) throws IOException
   {
      u = new EmailStorage();
      serverSocket = new ServerSocket(port);
      serverSocket.setSoTimeout(10000);
   }

   @Override
   public void run()
   {
      while(true)
      {
         try
         {
             
            System.out.println("Waiting for client on port " +
            serverSocket.getLocalPort() + "...");
            Socket server = serverSocket.accept();
            System.out.println("Just connected to " + server.getRemoteSocketAddress());
            DataOutputStream out =   new DataOutputStream(server.getOutputStream());
            DataInputStream in= new DataInputStream(server.getInputStream());
            
            
            String ap="";
            String line = in.readUTF();
            while(!line.equals("Exit")){
                if(line.equals("LogIn")){
                    String username = in.readUTF();
                    int result = u.existname(username); //check the name existence
                    if(result==0){
                        out.writeUTF("Yes");
                        String password = in.readUTF(); //check the password existence
                        result = u.existcode(username,password);
                        if(result==0){
                            out.writeUTF("Yes");
                            ap = in.readUTF();
                            while(!ap.equals("LogOut")){
                                if(ap.equals("NewEmail")){
                                    String receiver= in.readUTF();
                                    result = u.existname(receiver);//check the existence username of the receiver  
                                    if(result==0){
                                        out.writeUTF("Yes");
                                        String subject= in.readUTF();
                                         String mainbody= in.readUTF();
                                        String code = u.getPassword(receiver);
                                        newEmail(receiver,code,username,subject,mainbody); // create a new email in the receiver's list
                                    }
                                    else{
                                        out.writeUTF("No");
                                    }
                                }
                                else if(ap.equals("ShowEmails")){
                                     String[] emmails = new String[100];
                                      emmails = showEmails(username);
                                      int k=0;
                                      for(int j=1;j<100;j++){
                                          if(!emmails[j].equals("-")) k++; 
                                      }
                                      for(int j=1;j<=k;j++){
                                          out.writeUTF(emmails[j]);//sent one by one id,if it's new,sender and subject of each email 
                                      }
                                      out.writeUTF("End");
                                }
                                else if(ap.equals("ReadEmail")){
                                    int num=u.numEmails(username);//if this email id exists
                                    String id= in.readUTF();
                                    int id1 = Integer.parseInt(id);
                                    if(id1<=num){
                                        out.writeUTF("Yes");
                                        String m_b = u.readEmail(username, id); 
                                        out.writeUTF(m_b); //sent the main body of this email 
                                    }
                                    else{
                                        out.writeUTF("No");
                                    }                           
                                }else if(ap.equals("DeleteEmail")){
                                    int num=u.numEmails(username);//if this email id exists
                                    String id= in.readUTF();
                                    int id1 = Integer.parseInt(id);
                                    if(id1<=num && id1!=0){
                                        out.writeUTF("Yes");
                                        u.deleteEmail(username,id);//delete the email
                                        u.changeidEmails(username);//change the row of the ids that left if it's needen
                                    }
                                    else{
                                        out.writeUTF("No");
                                    }
                                }
                                else if(ap.equals("Exit")){
                                        break;
                                }
                                else{
                                    out.writeUTF(ap+": not an option in the menu");
                                }
                            ap = in.readUTF();
                           }
                        }
                        else{
                            out.writeUTF("No");
                        }
                    }
                    else{
                        out.writeUTF("No");
                    }
                }
                else if(line.equals("SignIn")){
                    String name = in.readUTF();
                    int r=u.existname(name);//check if the username already exists
                    if(r==1){
                        out.writeUTF("Yes");
                        String codee = in.readUTF();
                        emailss[0]=""; // Crete an new acount with empty email box
                        emailss[1]="";
                        emailss[2]="";
                        emailss[3]="";
                        emailss[4]="";
                        SaveAccounts l = new SaveAccounts(name,codee,emailss);
                        u.addEmail(l);
                        
                    }
                    else{
                        out.writeUTF("No");
                    }
                }
                else{
                       out.writeUTF(line+": not an option in the menu"); //wrong spell
                }
                if(ap.equals("Exit")){
                    break;
                }
             line = in.readUTF();
            }
            System.out.println("Connection with " + server.getRemoteSocketAddress()+" terminated");
         }catch(SocketTimeoutException s)
         {
            System.out.println("Socket timed out!");
             try {
                 serverSocket.close();
             } catch (IOException ex) {
                 Logger.getLogger(EmailServer.class.getName()).log(Level.SEVERE, null, ex);
             }
            break;
         }catch(IOException e)
         {
            e.printStackTrace();
            break;
         }
      }
   }
   // sent an email to an other available account
   public void newEmail(String receiver,String code,String username,String subject,String mainbody){
      
       int num= u.numEmails(receiver);
        emailss[0]=++num+"";
        emailss[1]=". [New]";
        emailss[2]=username;
        emailss[3]=subject;
        emailss[4]=mainbody;
        SaveAccounts l = new SaveAccounts(receiver,code,emailss);
        u.addEmail(l);
      
   }
   // shows the emails of an account
   public String[] showEmails(String username){
  
        String[] emmails = new String[100];
        emmails = u.getEmails(username);
        return emmails;
   }
 
   public static void main(String [] args)
   {
       int port = 9000;    
      try
      {
         Thread t = new EmailServer(port);
         emailss = new String[5];
        emailss[0]="1";
        emailss[1]=". [New]";
        emailss[2]="eirini@csd.gr";
        emailss[3]="Communication Networks";
        emailss[4]="Sed ut perspiciatis unde omnis iste natus error \n"
                + "sit voluptatem accusantium doloremque laudantium,\n"
                + "totam rem aperiam, eaque ipsa quae ab illo inventore\n"
                + "veritatis et quasi architecto beatae vitae dicta sunt \n"
                + "explicabo. Nemo enim ipsam voluptatem quia voluptas \n"
                + "sit aspernatur aut odit aut fugit, sed quia consequuntur\n"
                + "magni dolores eos qui ratione ￼voluptatem sequi nesciunt.";
        s = new SaveAccounts("emitsopou@csd.gr","emitsopou",emailss);
        u.addEmail(s);
        emailss[0] ="2";
        emailss[1]=". [New]";
        emailss[2]= "eirini@csd.gr";
        emailss[3]="Account expiration";
        emailss[4]="In Active Directory Users and Computers you can specify the date\n"
                + "when a user account expires on the \"Account\" tab of the user\n"
                + "properties dialog. This date is stored in the accountExpires attribute\n"
                + "of the user object.";
        s = new SaveAccounts("emitsopou@csd.gr","emitsopou",emailss);
        u.addEmail(s);
        emailss[0]="3";
        emailss[1]=".      ";
        emailss[2]="eirini@csd.gr";
        emailss[3]="Registration confirmation";
        emailss[4]="We have discovered an issue that is preventing users from logging in on Android.\n"
                + "The email address field is case sensitive; as a result you must type your email address\n"
                + "exactly as when you registered. We will be fixing this issue soon. In the meantime please\n"
                + "be cognizant of your Caps Lock key";
        s = new SaveAccounts("emitsopou@csd.gr","emitsopou",emailss);
        u.addEmail(s);
        emailss[0]="1";
        emailss[1]=". [New]";
        emailss[2]="emitsopou@csd.gr";
        emailss[3]="Networks";
        emailss[4]="A computer network or data network is a telecommunications network which allows computers to\n"
                + "exchange data. In computer networks, networked computing devices exchange data with each\n"
                + "other along network links (data connections). The connections between nodes are established\n"
                + "using either cable media or wireless media. The best-known computer network is the Internet.";
        s = new SaveAccounts("eirini@csd.gr","eirini",emailss);
        u.addEmail(s);
        emailss[0]="2";
        emailss[1]=".      ";
        emailss[2]="emitsopou@csd.gr";
        emailss[3]="Communication";
        emailss[4]="Communication in general takes place inside and between three main subject categories:\n"
                + "human beings, living organisms in general and communication-enabled devices ";
        s = new SaveAccounts("eirini@csd.gr","eirini",emailss);
        u.addEmail(s);
        emailss[0]="3";
        emailss[1]=".      ";
        emailss[2]="emitsopou@csd.gr";
        emailss[3]="Computer Science";
        emailss[4]="Computer science is the scientific and practical approach to computation and its\n"
                + "applications.";
        s = new SaveAccounts("eirini@csd.gr","eirini",emailss);
        u.addEmail(s);

         t.start();
      }catch(IOException e)
      {
         e.printStackTrace();
      }
   }
}
