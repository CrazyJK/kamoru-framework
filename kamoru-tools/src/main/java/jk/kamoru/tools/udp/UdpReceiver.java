package jk.kamoru.tools.udp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * {@link DatagramSocket}을 이용해 UDP 통신
 * <p>{@link UdpSender}에서 보낸 packet을 받는다
 * @author kamoru
 *
 */
public class UdpReceiver extends Thread
{
	private int port;
 
	public UdpReceiver(int port) {
    	this.port = port;
    }

    public void run() {
    	System.out.println("Attempt to start UDP receiver... by port : " + port);
    	try {
			receive(port);
		} 
    	catch (IOException e) {
			System.err.println("[" + this.getName() + "] port:" + port + " Error:" + e.getMessage());
			e.printStackTrace();
		}
    }

    public void receive(int port) throws IOException {
    	boolean RUN = true;
        byte buffer[] = new byte[1024];
        DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
		DatagramSocket datagramSocket = new DatagramSocket(port);
        do {
            System.out.println("[" + this.getName() + "] Waiting to receive by port " + port);
            datagramSocket.receive(datagramPacket);
            
            InetAddress from = datagramPacket.getAddress();
            String reveivingData = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
            String currDate  = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss").format(new Date());

            String stdout = "\n[" + currDate + "] [" + this.getName() + "] "
            		+ "From[" + from.getHostAddress() + ":" + datagramPacket.getPort() + "]" 
            		+ "\n[" + reveivingData + "]";
            System.out.println("reveived" + stdout);
            
            MessageBox messageBox = new MessageBox("Notify Box (" + port + ")", stdout);
            messageBox.show();
        } while(RUN);
        datagramSocket.close();
    }
    
    class MessageBox extends JFrame {

    	private static final long serialVersionUID = 1L;
    	
    	private String title;
    	private String message;
    	
    	public MessageBox(String title, String msg) {
    		this.title = title;
    		this.message = msg;
    	}
    	
    	public void show() {
    		JTextArea textArea = new JTextArea(message);
    		textArea.setEditable(false);
    		textArea.setLineWrap(true);
    		
    		JScrollPane scrollPane = new JScrollPane(textArea);
    		
    		Container con = getContentPane();
    		con.setLayout(new BorderLayout());
    		con.add(scrollPane, "Center");
    		
       		setTitle(title);
       		setBounds(600, 800, 550, 200);
    		setVisible(true);
    	}
    }
    
    public static void main(String args[]) throws Exception {
    	if (args == null || args.length < 1) {
    		System.err.println("need to port arguments");
    	}
    	
    	for(int i=0; i<args.length; i++) {
    		UdpReceiver reveiver = new UdpReceiver(Integer.parseInt(args[i]));
    		reveiver.start();
    	}
    }
}
