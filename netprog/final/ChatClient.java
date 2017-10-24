/**
 * Robert Koeninger
 * m02477822
 * Network Systems Programming, Spring 2009
 */

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * The ChatClient process. Start by calling
 *            java ChatClient 123 4 56 789 yourName
 * for a server IP address of 123.4.56.789.
 * Automatically signs up when started.
 * Commands are "signoff" which removes name from server.
 * "say [the message]" sends a message to everyone on your recipient list
 * "sayto [recipient list]" allows you to specify a comma-separated
 * list of recipients. Use "sayto *" to return to speaking to the whole room.
 */
public class ChatClient{

	// Starts the process
	public static void main(String[] args) throws Exception{
		byte[] serverIP = new byte[4];
		serverIP[0] = (byte)Integer.parseInt(args[0]);
		serverIP[1] = (byte)Integer.parseInt(args[1]);
		serverIP[2] = (byte)Integer.parseInt(args[2]);
		serverIP[3] = (byte)Integer.parseInt(args[3]);
		String myName = args[4];
		ChatClient client = new ChatClient(serverIP, myName);
	}
	byte[] serverIP; //IP of the server
	String myName;   // name to use
	String saytoString; // the string of recipients, '*' by default
	ServerSocket receptionSocket;
	Thread receptionThread, transmissionThread;//threads of processes

	// constructor. initializes fields
	public ChatClient(byte[] serverIP, String name){
		this.serverIP = serverIP;
		myName = name;
		saytoString = "*";
		receptionThread = new Thread(new ReceptionProcess());
		transmissionThread = new Thread(new TransmissionProcess());
		receptionThread.start();
		transmissionThread.start();
	}

	// Process that receives messages. Each time one is received, it just
	// spits it out on the screen
	private class ReceptionProcess implements Runnable{
		public void run(){
			try{
				receptionSocket = new ServerSocket(45015);
			}catch (Exception e1){
				e1.printStackTrace();
				System.exit(1);
			}
			System.out.println("Listening for Incoming Chat...");
			while (true){
				try{
					Socket connection = receptionSocket.accept();
					if (! connection.getInetAddress().equals(
					InetAddress.getByAddress(serverIP))){
						System.out.println("you're not my server!");
						continue;
					}
					BufferedReader input = new BufferedReader(
					new InputStreamReader(connection.getInputStream()));
					String nextLine;
					nextLine = input.readLine();
					if (! nextLine.startsWith("robchat message")){
						System.out.println("not a valid message");
						continue;
					}
					System.out.print(input.readLine() + " :\t");
					while ((nextLine = input.readLine()) != null){
						System.out.println(nextLine);
					}
					input.close();
					connection.close();
				}catch (Exception e2){
					e2.printStackTrace();
					System.exit(1);
				}
			}
		}
	}

	//process that transmits messages. Waits for user to give commands
	//and then sends messages
	private class TransmissionProcess implements Runnable{
		public void run(){
			try{
				Socket connectionSignup = new Socket(
				InetAddress.getByAddress(serverIP), 45014);
				DataOutputStream output = new DataOutputStream(
				connectionSignup.getOutputStream());
				output.writeBytes("robchat signup\n");
				output.writeBytes(myName);
				output.writeBytes("\n");
				output.close();
				connectionSignup.close();
				BufferedReader console = new BufferedReader(
				new InputStreamReader(System.in));
				while (true){
					String cmd = console.readLine();
					if (cmd.startsWith("signoff")){
						Socket connectionSignoff = new Socket(
						InetAddress.getByAddress(serverIP), 45014);
						output = new DataOutputStream(
						connectionSignoff.getOutputStream());
						output.writeBytes("robchat signoff");
						output.close();
						connectionSignoff.close();
						System.exit(0);
					} else if (cmd.startsWith("say ")){
						cmd = cmd.substring(4);
						Socket connectionMessage = new Socket(
						InetAddress.getByAddress(serverIP), 45014);
						output = new DataOutputStream(
						connectionMessage.getOutputStream());
						output.writeBytes("robchat message\n");
						output.writeBytes(saytoString);
						output.writeBytes("\n");
						output.writeBytes(cmd);
						output.close();
						connectionMessage.close();
					} else if (cmd.startsWith("sayto ")){
						cmd = cmd.substring(6);
						saytoString = cmd.trim();
					}
				}
			}catch (Exception e4){
				e4.printStackTrace();
				System.exit(1);
			}
		}
	}
}
