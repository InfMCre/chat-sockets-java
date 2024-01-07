package basicChat.client;

import java.io.BufferedReader;
import java.net.SocketException;

public class ClientServerListener extends Thread {
	BufferedReader bufferedReader;
	public ClientServerListener(BufferedReader bufferedReader) {
		this.bufferedReader = bufferedReader;
	}
	
	@Override
	public void run() {
		String message;
        try {
            while ((message = bufferedReader.readLine()) != null) {
                System.out.println(message);
            }
        } catch(SocketException e) {
        	// si el socket da una excepcion entenderemos que el server ha sido cerrado, de forma manual o por algun error. 
			System.out.println("Se ha cerrado el servidor");
			// si se ha cerrado no tiene sentido seguir con el chat en funcionamiento asi que se cierra la app cliente
			System.exit(0);
		} catch (Exception e) {
            e.printStackTrace();
        }
	}
}