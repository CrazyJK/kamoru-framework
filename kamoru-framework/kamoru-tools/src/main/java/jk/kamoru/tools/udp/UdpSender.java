package jk.kamoru.tools.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * {@link DatagramSocket}을 이용해 UDP 통신
 * <p>{@link UdpReceiver}로 packet을 보낸다
 * @author kamoru
 *
 */
public class UdpSender {

	private String ip;
	private int port;
	private String message;
	
	
	public UdpSender(String ip, int port, String message) {
		this.ip = ip;
		this.port = port;
		this.message = message;
	}

	public void send() {
		DatagramSocket datagramSocket = null;
		try {
			InetAddress receiverAddress = InetAddress.getByName(ip);
			message = "UDP send to " + receiverAddress.getHostAddress() + ":" + port + " " + message;
			byte packet[] = message.getBytes();
			DatagramPacket datagramPacket = new DatagramPacket(packet, packet.length, receiverAddress, port);
			datagramSocket = new DatagramSocket();
			datagramSocket.send(datagramPacket);
			datagramSocket.close();
			System.out.println("sent to " + ip + ":" + port + " - " + message);
		} 
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} 
		finally {
			if (datagramSocket != null)
				datagramSocket.close();
		}
	}
	
	public static void main(String args[]) throws Exception {
		String _ip   = "127.0.0.1";
		int _port = 7213;
		String _msg  = "asdadsad";
		
		UdpSender udpSender = new UdpSender(_ip, _port, _msg);
		udpSender.send();
	}
}
