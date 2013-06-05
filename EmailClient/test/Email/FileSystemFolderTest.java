/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Email;

import Persist.PersistentStorage;
import java.io.File;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Bargavi
 */
public class FileSystemFolderTest {
    private static String userHome;
    private static String testDirectory;
    
    private String id, name;
    private ArrayList<Message> messages;
    private ArrayList<Folder> folders;
    private PersistentStorage persistStore;
    private Mailbox parent;
    static private String mailboxID;

            
    public FileSystemFolderTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        mailboxID = "junit";
        userHome = System.getProperty("user.home") + File.separator;
        testDirectory = userHome + "_mailbox" + File.separator + mailboxID + File.separator;
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.id = "JUnit4";
        this.messages = new ArrayList<Message> ();
        this.folders = new ArrayList<Folder> ();
        this.persistStore = PersistentStorage.getFileSystemStorage(id);
        this.parent = (Mailbox) new FileSystemMailbox(id);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getName and setName method, of class FileSystemFolder.
     */
    @Test
    public void testSetGetName() {
        FileSystemFolder instance = new FileSystemFolder(id,(FileSystemMailbox)parent);
        
        String expResult = "id";
        instance.setName(expResult);
        String result = instance.getName();
        assertEquals(expResult, result);
       
    }

    /**
     * Test of addMessages and getMessages method, of class FileSystemFolder.
     */
    @Test
      public void testAddAndGetMessages() {
        PlainTextMessage msg;
        msg = PlainTextMessage.parse("Date: 01 Jan 01 1970 GMT\r\n"
                + "From: toor@example.com\r\n"
                + "To: alice@example.com\r\n"
                + "Subject: Hello\r\n"
                + "\r\n"
                + "Hello, World\r\n");
                
        String userHome = System.getProperty("user.home") + File.separator;

        String testId = userHome + "_mailbox" + File.separator + mailboxID + File.separator;
        msg.setId(testDirectory + "test_email");
        ArrayList<Message> expResult = new ArrayList<Message> ();
        //expResult.add((Message)msg);
                
        FileSystemFolder instance = new FileSystemFolder(id, (FileSystemFolder) parent);
        instance.addMessage(msg);
        
        ArrayList result = instance.getMessages();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSubfolders method, of class FileSystemFolder.
     */
    @Test
     public void testGetSubfolders() {
        PersistentStorage.getTestingStorage();
        FileSystemMailbox mailbox = new FileSystemMailbox("TEST_MAILBOX");
        mailbox.createFolder("level1");
        mailbox.createFolder("level2");

        ArrayList<Folder> result = mailbox.getSubfolders();
        
        assertEquals(result.size(), 2);
        assertEquals(result.get(0), "level1");
        assertEquals(result.get(2), "level2");
    }

    /**
     * Test of addMessageCopy method, of class FileSystemFolder.
     */
    @Test
    public void testAddMessageCopy() {
        
        PlainTextMessage msg;
        msg = PlainTextMessage.parse("Date: 01 Jan 01 1970 GMT\r\n"
                + "From: toor@example.com\r\n"
                + "To: alice@example.com\r\n"
                + "Subject: Hello\r\n"
                + "\r\n"
                + "Hello, World\r\n");
       
        ArrayList<Message> expResult = new ArrayList<Message> ();
        //expResult.add((Message)msg);
                
        FileSystemFolder instance = new FileSystemFolder(id, (FileSystemFolder) parent);
       
        instance.addMessageCopy(msg);
        
        ArrayList result = instance.getMessages();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of addMessage and deleteMessage method, of class FileSystemFolder.
     */
    @Test
    public void testAddAndDeleteMessage() {
        
        PlainTextMessage msg;
        msg = PlainTextMessage.parse("Date: 01 Jan 01 1970 GMT\r\n"
                + "From: toor@example.com\r\n"
                + "To: alice@example.com\r\n"
                + "Subject: Hello\r\n"
                + "\r\n"
                + "Hello, World\r\n");
                
        FileSystemFolder instance = new FileSystemFolder(id, (FileSystemFolder) parent);
        instance.addMessage(msg);
        
        instance.deleteMessage(msg);

    }

    /**
     * Test of addFolder and DeleteFolder method, of class FileSystemFolder.
     */
    @Test
    public void testAddAndDeleteFolder() {
        PersistentStorage.getTestingStorage();
        FileSystemMailbox mailbox = new FileSystemMailbox("TEST_MAILBOX");
        
        String testname = "zzz-test-zzz";
        
        Folder t = new FileSystemFolderProxy(testname, mailbox);
        
        mailbox.addFolder(t);
        mailbox.deleteFolder(t);
        
    }

     /**
     * Test of getParent and setParent method, of class FileSystemFolder.
     */
    @Test
    public void testSetGetParent() {
        
        FileSystemFolder instance = new FileSystemFolder(id,(FileSystemMailbox)parent);        
        instance.setParent((FileSystemFolder)this.parent);
        FileSystemFolder result = instance.getParent();
        assertEquals(parent.getName(), result.getName());
       
    }
}