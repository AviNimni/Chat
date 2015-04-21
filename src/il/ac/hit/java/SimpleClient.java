package il.ac.hit.java;

import java.io.*;
import java.net.Socket;

public class SimpleClient
{
	public static String serverName = "127.0.0.1";
	public static int serverPort = 1301;
	public static void main(String[] args)
	{
		Socket socket = null;
		ConnectionProxy connection = null;
		DataInputStream dis;
		try
		{
			socket = new Socket(serverName,serverPort);
			connection = new ConnectionProxy(socket);
			connection.consume("Bar");
			//dis = connection.getDis();
		//	String str = dis.readUTF();
			//System.out.println(str);
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
