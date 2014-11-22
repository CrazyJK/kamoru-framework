package jk.kamoru.util;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class NetUtils {

	public static Future<Object[]> portIsOpen(final ExecutorService es, final String ip, final int port, final int timeout) {
		return es.submit(new Callable<Object[]>() {
			@Override public Object[] call() {
				try {
					Socket socket = new Socket();
		          	socket.connect(new InetSocketAddress(ip, port), timeout);
		          	socket.close();
		          	return new Object[] {ip, port, true};
		        } 
				catch (Exception ex) {
		          	return new Object[] {ip, port, false};
		        }
			}
		});
	}
}


