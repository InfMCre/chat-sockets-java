package basicChat.server;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;

public class ClientConnection extends Thread {

    Socket socket;
    
    BufferedReader bufferedReader;
    BufferedWriter bufferedWriter;

    public ClientConnection(Socket socket) throws IOException {
        this.socket = socket;
        bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
    }

    @Override
    public void run() {

        try  {
            String line;
            while ( (line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                // Server.broadcast(line);
                
                Server.broadcastUnlessTheSender(line, socket);
            }
        } catch (SocketException e) {
        	// lo tomaremos como que el cliente se ha desconectado
        	// podriamos enviar mensaje al resto de usuarios
        	System.out.println("Usuario desconectado");
        	
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(String message) {
        try {
            bufferedWriter.write(message);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public Socket getSocket() {
		return socket;
	}
    
    
}
