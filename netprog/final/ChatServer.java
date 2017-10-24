/**
 * Robert Koeninger
 * m02477822
 * Network Systems Programming, Spring 2009
 */

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * The ChatServer class is an object that encapsulates all the behaviors
 * of the server side. Generally speaking, the server's job is to accept
 * messages from hosts running the ChatClient process and relay their
 * messages to all other clients that have signed up.
 *
 * By default, the server always accepts incoming messages on port 45014
 * and sends them on 45015. The protocol format is as follows:
 * The first 8 characters must be the string "robchat" followed by a space.
 * Following that is one of the strings "message", "signup" or "signoff"
 * If it is a "message", the next line should either be an '*', meaning
 * all clients on the server will receive the message or a comma-separated
 * list of IP addresses, who are the exclusive recipients of the message.
 * If it is a "signup", the next line should be a string which is the
 * nickname of the client.
 * If it is a "signoff", there need not be anything else to the message.
 */
public class ChatServer{

	/**
	 * The main method that gets called when the server starts. All it does
	 * is create an instance of the server object.
	 */
	public static void main(String[] args){
		ChatServer server = new ChatServer();
	}

	/**
	 * A private inner class to contain information about clients chating
	 * on the server.
	 */
	private static class ClientInfo{
		public int clientIP;
		public String clientName;
	}

	/**
	 * A list of clients connected to the server. When the server receives
	 * a message for general broadcast, it goes down this list and relays
	 * the message to every client in it.
	 */
	private List<ClientInfo> clients;

	/**
	 * A reference to the server socket the server listens on. (port 45014)
	 */
	private ServerSocket listenSocket;

	/**
	 * A reference to the thread that the server's response code runs in.
	 */
	private Thread process;

	/**
	 * ChatServer constructor. Initializes the client list, the server
	 * process and starts the server process.
	 */
	public ChatServer(){
		clients = new ArrayList<ClientInfo>();
		process = new Thread(new ServerProcess());
		process.start();
	}

	/**
	 * The process that responds to server requests.
	 */
	private class ServerProcess implements Runnable{
		public void run(){
			System.out.println("ChatServer v0.1");
			System.out.print("Listening on port: ");
			System.out.println(45014);
			try{
				listenSocket = new ServerSocket(45014);
			}catch (Exception e1){
				e1.printStackTrace();
				System.exit(1);
			}
			System.out.println("Socket Opened, Listening...");

			// In this loop, we wait for a request, and then handle it
			while (true){
				try{
					Socket connection = listenSocket.accept();
					InetAddress client =
					connection.getInetAddress();
					System.out.println("Request from: " +
					client.getHostName());
					BufferedReader input = new BufferedReader(
					new InputStreamReader(
					connection.getInputStream()));
					DataOutputStream output =
					new DataOutputStream(
					connection.getOutputStream());
					byte[] addrParts = client.getAddress();
					handleMessage(ipPartsToWord(addrParts),input,output);
					input.close();
					output.flush();
					output.close();
					connection.close();
				} catch (Exception e2){
					e2.printStackTrace();
					System.exit(1);
				}
			}
		}
		public void handleMessage(int clientIP,
		BufferedReader input, DataOutputStream output)throws IOException{
			String headerLine = input.readLine();

			// Must start with "robchat "
			if (! headerLine.startsWith("robchat ")){
				System.out.println("Invalid message");
				return;
			}
			headerLine = headerLine.substring(headerLine.indexOf(' ')+1);

			// If it's a signup request, add the client to the list
			if (headerLine.startsWith("signup")){
				String idLine = input.readLine();
				ClientInfo newMember = new ClientInfo();
				newMember.clientIP = clientIP;
				newMember.clientName = idLine;
				clients.add(newMember);
				System.out.print(newMember.clientName);
				System.out.print(" [");
				System.out.print(ipWordToString(newMember.clientIP));
				System.out.print("] ");
				System.out.println("signed up");
			}

			// If it's a signoff request, remove the client from the list
			else if (headerLine.startsWith("signoff")){
				for (int x = 0; x < clients.size(); ++x){
					if (clients.get(x).clientIP == clientIP){
						ClientInfo oldMember = clients.remove(x);
						System.out.print(oldMember.clientName);
						System.out.print(" [");
						System.out.print(ipWordToString(oldMember.clientIP));
						System.out.print("] ");
						System.out.println("signed off BYE!");
						return;
					}
				}
			}

			// If it's a message request, build the list of recipients
			// and copy the message to them
			else if (headerLine.startsWith("message")){
				String recipientLine = input.readLine();
				List<ClientInfo> recipients;
				if (recipientLine.charAt(0) == '*'){

					// Everyone gets the message
					recipients = clients;
				}
				else{

					// Parse recipient list
					StringTokenizer tokens =
					new StringTokenizer(recipientLine,",");
					recipients = new ArrayList<ClientInfo>();
					while (tokens.hasMoreTokens()){
						byte[] ip = stringToIPParts(tokens.nextToken());
						ClientInfo nextRecipient = new ClientInfo();
						nextRecipient.clientIP = ipPartsToWord(ip);
						nextRecipient.clientName =
						ipWordToString(nextRecipient.clientIP);
						recipients.add(nextRecipient);
					}
				}

				// Read the message
				StringBuffer message = new StringBuffer();
				String nextLine;
				while ((nextLine = input.readLine()) != null){
					message.append(nextLine);
				}
				System.out.println("Message: ");
				System.out.println(message.toString());
				String senderString = "unknown";

				// Get human-readable form of sender's name
				for (int z = 0; z < clients.size(); ++z){
					if (clients.get(z).clientIP == clientIP){
						senderString = clients.get(z).clientName +
						" [" + ipWordToString(clients.get(z).clientIP) +
						"]";
					}
				}
				System.out.println("Relaying message from " +
				senderString + "...");

				// Write the message to each recipient
				for (ClientInfo nextRecipient : recipients){
					Socket transmitSocket = null;
					try{
						transmitSocket = new Socket(
						InetAddress.getByAddress(
						ipWordToParts(nextRecipient.clientIP)), 45015);
					}catch(Exception e3){

						// If the recipient couldn't be reached,
						// send message to sending client
						System.out.print("Could not connect to ");
						System.out.println(
						ipWordToString(nextRecipient.clientIP));
						DataOutputStream outputToClient =
						new DataOutputStream(
						transmitSocket.getOutputStream());
						outputToClient.writeBytes("robchat message\n");
						outputToClient.writeBytes("THE SERVER");
						outputToClient.writeBytes("\n");
						outputToClient.writeBytes(message.toString());
						outputToClient.close();
						transmitSocket.close();
						continue;
					}

					// Write message to recipient
					System.out.println("Relaying to " +
					nextRecipient.clientName);
					DataOutputStream outputToClient = new DataOutputStream(
					transmitSocket.getOutputStream());
					outputToClient.writeBytes("robchat message\n");
					outputToClient.writeBytes(senderString);
					outputToClient.writeBytes("\n");
					outputToClient.writeBytes(message.toString());
					outputToClient.close();
					transmitSocket.close();
				}
			}

			// Request type unkown
			else{
				System.out.println("Bad request");
			}
		}
		public String ipWordToString(int ip){
			byte[] ipParts = ipWordToParts(ip);
			return Integer.toString(ipParts[0]) + "." +
			Integer.toString(ipParts[1]) + "." +
			Integer.toString(ipParts[2]) + "." +
			Integer.toString(ipParts[3]);
		}
		public int ipPartsToWord(byte[] ipParts){
			return (((int)ipParts[0]) << 24) |
			(((int)ipParts[1]) << 16) |
			(((int)ipParts[2]) << 8) |
			(int)ipParts[3];
		}
		public byte[] stringToIPParts(String string){
			StringTokenizer ipParts = new StringTokenizer(string,".");
			byte[] ip = new byte[4];
			ip[0] = (byte)Integer.parseInt(ipParts.nextToken());
			ip[1] = (byte)Integer.parseInt(ipParts.nextToken());
			ip[2] = (byte)Integer.parseInt(ipParts.nextToken());
			ip[3] = (byte)Integer.parseInt(ipParts.nextToken());
			return ip;
		}
		public byte[] ipWordToParts(int ip){
			byte[] ipParts = new byte[4];
			ipParts[3] = (byte)(ip & 0x000000ff);
			ipParts[2] = (byte)((ip & 0x0000ff00) >> 8);
			ipParts[1] = (byte)((ip & 0x00ff0000) >> 16);
			ipParts[0] = (byte)((ip & 0xff000000) >> 24);
			return ipParts;
		}
	}
}
