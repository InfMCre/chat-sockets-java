package basicChat.client;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {
	
	// la conexion con el servidor
	private Socket socket;
	// el server nos mandara mensajes que deberemos leer
	private BufferedReader bufferedReaderFromServer;
	// necesitamos un buffer de escritura para comunicarnos con el server
	private BufferedWriter bufferedWritterToServer;
	

	public static void main(String [] args) {
		new Client();

	}
	
	// en este caso podriamos hacer un constructor privado
	private Client () {
		connectToServer();
		enableClientChat();
	}

	private void connectToServer() {
		try {
			socket = new Socket("localhost",1234);
			// el server nos mandara mensajes que deberemos leer
			bufferedReaderFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			// necesitamos un buffer de escritura para comunicarnos con el server
			bufferedWritterToServer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
	        
	        // la lectura de lo que viene por el server la haremos en un hilo
			ClientServerListener clientServerListener = new ClientServerListener(bufferedReaderFromServer);
			clientServerListener.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// Si no se conecta cerramos
			System.exit(0);
		}
	}
	
	private void enableClientChat() {
      // este bufferedReader va a leer lo que escribamos en el teclado, para luego enviarlo al server
		BufferedReader bufferedReaderFromThisClient = new BufferedReader(new InputStreamReader(System.in));
		String userName = "User" + ((int) (Math.random() * 200));
		System.out.println("Conectado como: " + userName);
		String msg;
		try {
			while ((msg = bufferedReaderFromThisClient.readLine()) != null) {
				if (msg != null && msg.equalsIgnoreCase("exit")) {
					System.exit(0);
				} else {
					bufferedWritterToServer.write(userName + ": " + msg + "\n");
					bufferedWritterToServer.flush();                		
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
