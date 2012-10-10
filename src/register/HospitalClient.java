/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package register;

import entity.EntityTable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author fish
 */
public class HospitalClient {
    private static final int SERVER_PORT = 10000; 
    Socket requestSocket;
    ObjectOutputStream out;
    ObjectInputStream in;
    String message;

    HospitalClient() {
    }

    void run() {
	try {
	    //1. creating a socket to connect to the server
	    requestSocket = new Socket("localhost", SERVER_PORT);
	    System.out.println("Connected to localhost in port 2004");
	    //2. get Input and Output streams
	    out = new ObjectOutputStream(requestSocket.getOutputStream());
	    out.flush();
	    in = new ObjectInputStream(requestSocket.getInputStream());
	    //3: Communicating with the server
	    do {
		try {
		    EntityTable e = (EntityTable) in.readObject();
		    message = e.getType();
		    System.out.println("server>" + message);
		    sendMessage("Hi my server");
		    message = "bye";
		    sendMessage(message);
		} catch (ClassNotFoundException classNot) {
		    System.err.println("data received in unknown format");
		}
	    } while (!message.equals("bye"));
	} catch (UnknownHostException unknownHost) {
	    System.err.println("You are trying to connect to an unknown host!");
	} catch (IOException ioException) {
	    ioException.printStackTrace();
	} finally {
	    //4: Closing connection
	    try {
		in.close();
		out.close();
		requestSocket.close();
	    } catch (IOException ioException) {
		ioException.printStackTrace();
	    }
	}
    }

    void sendMessage(String msg) {
	try {
	    EntityTable r = new EntityTable();
	    r.setType(msg);
	    out.writeObject(r);
	    out.flush();
	    System.out.println("client>" + msg);
	} catch (IOException ioException) {
	    ioException.printStackTrace();
	}
    }

    public static void main(String args[]) {
	HospitalClient client = new HospitalClient();
	client.run();
    }
}
