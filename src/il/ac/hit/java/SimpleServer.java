package il.ac.hit.java;

import java.io.IOException;
import java.io.*;
import java.net.*;

public class SimpleServer
{
	public static void main(String[] args) {
		ServerSocket server = null;
		InputStream is = null;
		OutputStream os = null;
		DataInputStream dis = null;
		DataOutputStream dos = null;
		Socket socket = null;
		try
			{
				server = new ServerSocket(1301);
				socket = server.accept();
				is = socket.getInputStream();
				os = socket.getOutputStream();
				dis = new DataInputStream(is);
				dos = new DataOutputStream(os);
				while(true)
				{
					String str = dis.readUTF();
					dos.writeUTF(str);}
				}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			finally {
				if (socket != null)
					try {
						socket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				if(server!=null)
					try {
						server.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
}

