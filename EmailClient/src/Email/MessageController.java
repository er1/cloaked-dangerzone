package Email;

/**
 *
 * @author chanman
 */
public class MessageController {

    private Message getMessageFromID(MessageID message) {
        return new PlainTextMessage() {
        }; // FIXME 
    }

    private Folder getFolderFromID(FolderID folder) {
        return new TemporaryFolder(""); // FIXME
    }

    private MessageID getIDfromMessage(Message message) {
        return new MessageID(); // FIXME
    }

    private FolderID getIDfromFolder(Folder folder) {
        return new FolderID(); // FIXME
    }

    /**
     *
     * @param folder
     * @return
     */
    public FolderID[] getFolderList(FolderID folder) {
        Folder fldr = getFolderFromID(folder);
        FolderSet set = fldr.getSubfolders();
        FolderID[] ids = new FolderID[set.size()];

        int index = 0;
        for (Folder subfolder : set) {
            ids[index++] = getIDfromFolder(subfolder);
        }

        return ids;
    }

    /**
     *
     * @param folder
     * @return
     */
    public MessageID[] getEmailList(FolderID folder) {
        Folder fldr = getFolderFromID(folder);
        MessageSet set = fldr.getMessages();
        MessageID[] ids = new MessageID[set.size()];

        int index = 0;
        for (Message message : set) {
            ids[index++] = getIDfromMessage(message);
        }

        return ids;
    }

    /**
     *
     * @param id
     * @return
     */
    public String getEmailContent(MessageID id) {
        Message message = getMessageFromID(id);
        return message.getContent();
    }

    /**
     *
     * @param id
     * @param content
     */
    public void setEmailContent(MessageID id, String content) {
        Message message = getMessageFromID(id);
        message.setContent(content);
    }

    /**
     *
     * @param id
     * @return
     */
    public Summary getEmailSummary(MessageID id) {
        Message message = getMessageFromID(id);
        Summary summary = new Summary();

        summary.Date(message.getHeader("Date")).
                From(message.getHeader("From")).
                To(message.getHeader("To")).
                CC(message.getHeader("Cc")).
                BCC(message.getHeader("Bcc")).
                Subject(message.getHeader("Subject")).
                Read(message.getHeader("X-Read").length() > 0);

        return summary;
    }

    /**
     *
     * @param message
     * @param key
     * @param value
     */
    public void setEmailHeader(MessageID message, String key, String value) {
        Message msg = getMessageFromID(message);
        msg.setHeader(key, value);
    }

    /**
     *
     * @param message
     * @param key
     * @return
     */
    public String getEmailHeader(MessageID message, String key) {
        Message msg = getMessageFromID(message);
        return msg.getHeader(key);
    }

    /**
     *
     * @param message
     */
    public void markRead(MessageID message) {
        setEmailHeader(message, "X-Read", "FIXME: Set to NOW()");
    }

    /**
     *
     * @param message
     */
    public void markUnread(MessageID message) {
        setEmailHeader(message, "X-Read", null);
    }

    /**
     *
     * @param message
     */
    public void delete(MessageID message) {
        // FIXME;
    }

    /**
     *
     * @param message
     * @param folder
     */
    public void moveTo(MessageID message, FolderID folder) {
        Message msg = getMessageFromID(message);
        Folder fldr = getFolderFromID(folder);
        fldr.addMessage(msg);
    }

    /**
     *
     * @return
     */
    public MessageID compose() {
        return new MessageID();
    }

    MessageID reply(MessageID originalMessage) {
        Message original = getMessageFromID(originalMessage);

        MessageID replyid = compose();
        Message replymsg = getMessageFromID(replyid);

        String replyContent = original.getContent();
        replyContent = "\r\n\r\n" + replyContent;
        replyContent = replyContent.replaceAll("\n", "\n> ");

        String to = original.getHeader("From");
        String subject = original.getHeader("subject");

        if (!"RE:".equals(subject.substring(0, 3).toUpperCase())) {
            subject = "RE: " + subject;
        }

        replymsg.setContent(replyContent);
        replymsg.setHeader("To", to);
        replymsg.setHeader("Subject", subject);

        return replyid;
    }
}
