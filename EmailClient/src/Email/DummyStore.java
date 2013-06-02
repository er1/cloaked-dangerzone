/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Email;

/**
 * DummyStore is for testing purposes
 * @author chanman
 */
public class DummyStore extends TemporaryFolder implements Mailbox {

    // Some temporary boxes
    private Folder drafts = new TemporaryFolder("Drafts");
    private Folder inbox = new TemporaryFolder("Inbox");
    private Folder outbox = new TemporaryFolder("Outbox");
    private Folder sent = new TemporaryFolder("Sent Messages");
    private Folder trash = new TemporaryFolder("Trash");

    /**
     * Create a testing MesaageStore
     *
     */
    public DummyStore() {
        super("Mailbox");

        inbox.setId("in");
        outbox.setId("out");
        sent.setId("sent");
        drafts.setId("draft");
        trash.setId("trash");
        
        // add them to the store on creation
        this.addFolder(inbox);
        this.addFolder(outbox);
        this.addFolder(sent);
        this.addFolder(drafts);
        this.addFolder(trash);

        // add two test messages
        PlainTextMessage msg = new PlainTextMessage();
        msg.setId("m1");
        msg.parse("Date: 01 Jan 70 00:00 GMT\r\n"
                + "From: toor@example.com\r\n"
                + "To: alice@example.com\r\n"
                + "Subject: Hello\r\n"
                + "\r\n"
                + "Hello, World\r\n");
        inbox.addMessage(msg);

        msg = new PlainTextMessage();
        msg.setId("m2");
        msg.parse("Date: 05 Nov 09 1900 EDT\r\n"
                + "From: Alice Anderson <alice@example.com>\r\n"
                + "To: Bob Brown <bob@example.com>, Charlie Chapman <charlie@example.com>\r\n"
                + "BCC: eve@example.org\r\n"
                + "Subject: Payroll forms\r\n"
                + "\r\n"
                + "Hi everyone,\r\n"
                + "\r\n"
                + "I just want to make sure everyone has submitted their forms for payroll\r\n"
                + "\r\n"
                + "Alice\r\n"
                + "\r\n");
        inbox.addMessage(msg);
    }

    @Override
    public Folder getDrafts() {
        return drafts;
    }

    @Override
    public Folder getInbox() {
        return inbox;
    }

    @Override
    public Folder getOutbox() {
        return outbox;
    }

    @Override
    public Folder getSentMessages() {
        return sent;
    }

    @Override
    public Folder getTrash() {
        return trash;
    }

    @Override
    public void moveFolder(Folder destination) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Folder getMeetings() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Folder getTemplates() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getUserId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
