/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

/**
 *
 * @author anasalkhatib
 */
public class Main {

    public static void main(String args[]) {
        UserInterface gui = UserInterface.getSwingUserInterface();
        gui.display();
        PersistentStorage persistentStorage = PersistentStorage.getFileSystemStorage();
        persistentStorage.load();
    }
}