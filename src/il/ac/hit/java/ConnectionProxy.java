package il.ac.hit.java;

import java.io.*;
import java.net.Socket;

public class ConnectionProxy extends Thread implements StringConsumer
{
	private Socket socket = null;
	private InputStream is;
	private OutputStream os;
	private DataInputStream dis;
	private DataOutputStream dos;
	private StringConsumer sc;
	public ConnectionProxy(Socket socket)
	{
		try{
			this.socket = socket;
			is = socket.getInputStream();
			dis = new DataInputStream(is);
			os = socket.getOutputStream();
			dos = new DataOutputStream(os);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	public void addConsumer(StringConsumer stringConsumer)
	{
		sc = stringConsumer;
	}
	@Override
	public void consume(String str)
	{
		try
		{
			dos.writeUTF(str);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	@Override
	public void run()
	{
		try {
            while (true) {

                String message = dis.readUTF();
                sc.consume(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
