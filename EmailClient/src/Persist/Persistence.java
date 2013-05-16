package Persist;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.ArrayList;

 
// the following are our persistence methods

    
class Persistence{
    
    public  Mail  openMail( Path mailPath) {
        
        String text;
        BufferedReader bufferReader = null;
        ArrayList fileContent = new ArrayList();
        
        Mail mail  = new Mail();
        
        try{
            bufferReader = new BufferedReader(new FileReader(mailPath.toString()));	
            
            while ((text = bufferReader.readLine()) != null) {
                    fileContent.add(text);
            } 
            
            mail.setPath(mailPath); //I have to check if we need to use the parameter, or keep the one in Mail class
            
            // fill Sender
            text ="";
            for(int i= fileContent.indexOf("<From>")+1;i < fileContent.indexOf("</From>");i++) {
                text = text + fileContent.get(i).toString();
            }
            mail.setSender(text);
                        
            // fill Receivers
            text ="";
            for(int i= fileContent.indexOf("<TO>")+1;i < fileContent.indexOf("</TO>");i++) {
                text = text + fileContent.get(i).toString();
            }
            mail.setReceivers(text);
            
            // fill CcReceivers
            text ="";
            for(int i= fileContent.indexOf("<CC>")+1;i < fileContent.indexOf("</CC>");i++) {
                text = text + fileContent.get(i).toString();
            }
            mail.setCcReceivers(text);
            
            // fill CciReceivers
            text ="";
            for(int i= fileContent.indexOf("<CCi>")+1;i < fileContent.indexOf("</CCi>");i++) {
                text = text + fileContent.get(i).toString();
            }
            mail.setCciReceivers(text);
            
            // fill to Subject 
            text ="";
            for(int i= fileContent.indexOf("<Subject>")+1;i < fileContent.indexOf("</Subject>");i++) {
                text = text + fileContent.get(i).toString();
            }
            mail.setSubject(text);
            
            // fill to message 
            text ="";
            for(int i= fileContent.indexOf("<Message>")+1;i < fileContent.indexOf("</Message>");i++) {

                mail.appendMessage(fileContent.get(i).toString()+"\n"); 
            }                        
            text ="";
            return mail;

        } catch (IOException e) {
                e.printStackTrace();
                return mail;
        } finally {
                try {
                        if (bufferReader != null)bufferReader.close();
                } catch (IOException ex) {
                        ex.printStackTrace();
                        return mail;
                }
        }

    }

    public  boolean saveMail(Mail mail, Path mailPath) { 
        File draft ;
        FileOutputStream outputStream = null;
        String mailContent;

        try {
                draft = new File(mailPath.toString());

                if (!draft.exists()) {
                        draft.createNewFile();
                }
                outputStream = new FileOutputStream(draft);
                
                // format file content
                 mailContent    =   "<Path>\n"      + mail.getPath().toString() +   "\n</Path>\n"
                                +   "<From>\n"      + mail.getSender()          +   "\n</From>\n"
                                +   "<TO>\n"        + mail.getReceivers()         +   "\n</TO>\n"
                                +   "<Cc>\n"        + mail.getCcReceivers()       +   "\n</Cc>\n"
                                +   "<Cci>\n"       + mail.getCciReceivers()      +   "\n</Cci>\n"
                                +   "<Subject>\n"   + mail.getSubject()         +   "\n</Subject>\n"
                                +   "<Message>\n"   + mail.getMessage()         +   "\n</Message>\n";
                
                byte[] contentInBytes = mailContent.getBytes();
                outputStream.write(contentInBytes);
                outputStream.flush();
                outputStream.close();
                
                return true;
                
        } catch (IOException except) {
                except.printStackTrace();
                System.out.println("saveButton:IOException");
              return false;
        } finally {
                try {
                        if (outputStream != null){
                                outputStream.close();
                                System.out.println("saveButton:colse");
                                return true;
                        }
                } catch (IOException ex) {
                        ex.printStackTrace();
                        System.out.println("saveButton:ex");
                        return false;
                }
        }
    }
    
    public   boolean  deleteFile( Path filePath) {
        
        File file = new File(filePath.toString());
        
        //System.err.format("start:" +filePath.toString()+"\n");
        
        if (! file.exists()){
        
            System.err.format("no such file or Dirctory" +filePath.toString()+"\n");
            return false; 

        } else {
                    if(!file.isDirectory()) {

                        file.delete();
                        return true;

                    }   else {
                            if(file.list().length==0){

                                file.delete();
                                return true;
                            }   else {
                                    for(String pathTemp : file.list()){

                                        deleteFile( Paths.get(filePath.toString(),pathTemp));

                                    }
                                    file.delete();
                                        return true;
                                   
                                }
                            }
            }
    }
    
    public  boolean moveFile(Path oldPath, Path newPath) { 
        
        try {
                Files.copy(oldPath, newPath, REPLACE_EXISTING);
                deleteFile(oldPath);
                return true;
        } catch (IOException except) {
                except.printStackTrace();
                return false;
        } 
    }
    
    public  boolean deleteFolder(Path path) {
      return deleteFile (path) ;  
    }
    public  boolean moveFolder(Path oldPath, Path newPath) { 
        
        try {
                Files.copy(oldPath, newPath, REPLACE_EXISTING);
                return true;
        } catch (IOException except) {
                except.printStackTrace();
                return false;
        } 
    }   
}

//this class is only for  my tests purpose, should use the class defined with the group
   
class Mail {
    String sender;
    String receivers;
    String ccReceivers;
     String cciReceivers;
    String subject;
    String message;
    Path  path;  
    
    public boolean setSender (String sender){
        this.sender = sender;
        return true;
    }
    
    public boolean setReceivers (String to){
        this.receivers = to;
        return true;
    }
    
    public boolean setCcReceivers (String ccReceivers){
        this.ccReceivers = ccReceivers;
        return true;
    }
    
    public boolean setCciReceivers (String cciReceivers){
        this.cciReceivers = cciReceivers;
        return true;
    }
    
    public boolean setSubject (String subject){
        this.subject = subject;
        return true;
    }
    public boolean setMessage (String message){
        this.message = message;
        return true;
    }
        public boolean setPath (Path path){
        this.path = path;
        return true;
    }
    public boolean appendMessage (String message){
        this.message = this.message + message;
        return true;
    }
    
    public String getSender(){ return this.sender; }
    
    public String getReceivers(){ return this.receivers; } 
    
    public String getCcReceivers(){ return this.ccReceivers; } 
    
    public String getCciReceivers(){ return this.cciReceivers; } 

    public String getSubject(){ return this.subject;}

    public String getMessage(){ return this.message;}

    public Path getPath(){ return this.path;}    
}