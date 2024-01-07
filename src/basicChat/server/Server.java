package basicChat.server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
	
	
	static List<ClientConnection> connections = new ArrayList<>();
	
	
	public static void main(String[] args) {
		
		ServerSocket serverSocket;
		Boolean running = true;
		try {
			serverSocket = new ServerSocket(1234);
			System.out.println("Servidor en linea");
			
			while(running) {
				// Listens for a connection to be made to this socket and accepts it. The method blocks until a connection is made. 
				Socket socket = serverSocket.accept();
				ClientConnection connection = new ClientConnection(socket);
				connections.add(connection);
				connection.start();
				connection.write("te has conectado al servidor");
				// connection.write(connection.getSocket().getRemoteSocketAddress().toString());
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void broadcast(String message) {
		for (ClientConnection connection: connections) {
			connection.write(message);
		}
	}
	public static void broadcastUnlessTheSender(String message, Socket sender) {
		for (ClientConnection connection: connections) {
			if (connection.getSocket() != sender) {
				connection.write(message);
			}
		}
	}
		
}
