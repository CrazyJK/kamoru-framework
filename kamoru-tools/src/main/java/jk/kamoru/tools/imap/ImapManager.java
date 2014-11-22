package jk.kamoru.tools.imap;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;

import com.sun.mail.imap.IMAPStore;

/**
 * imap 서버에 연결하는 간략한 샘플 코드
 * @author kamoru
 *
 */
public class ImapManager {

	protected static Session session;  
    
    protected static Store store;  
      
    protected static Folder folder;  
    
    private static String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";  
    
	/**
	 * @param args
	 * @throws MessagingException 
	 */
	public static void main(String[] args) throws MessagingException {
		String host = "imap.handysoft.co.kr";
		String id = "namjk24@handysoft.co.kr";
		String passwd = "22222";
		
		Properties props = new Properties();  
        props.setProperty("mail.imap.socketFactory.class", SSL_FACTORY);  
        props.setProperty("mail.imap.socketFactory.fallback", "false");  
        props.setProperty("mail.imap.port", "143");  
        props.setProperty("mail.imap.socketFactory.port", "143");  
        session = Session.getInstance(props, null);  
        URLName urlName = new URLName("imap", host, 143, "", id, passwd);  
        store = new IMAPStore(session, urlName);  
        store.connect();  
        folder = store.getDefaultFolder().getFolder("INBOX");  
        folder.open(Folder.READ_WRITE);  

        System.out.println(folder.getMessageCount());  
	}

}
