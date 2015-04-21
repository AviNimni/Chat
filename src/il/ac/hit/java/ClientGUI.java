package il.ac.hit.java;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;


public class ClientGUI implements StringConsumer
{
	private JFrame frame;
	private JButton btConnect;
	private JButton btDisconnect;
	private JButton btSend;
	private JTextField tfServer,tfPort,tfNick;
	private TransparentTextArea tfChat,tfUsers,tfSend;
	private JLabel labelServer,labelPort,labelNick;
	private ActionListener listener;
	private JPanel panelLogin,panelChat,panelSend,panelConnect,panelUsers;
	private JScrollPane scroller,scrollerUsers;
	private ConnectionProxy connection;
	private StringConsumer sc;
	public ClientGUI() {
		frame = new JFrame("Chat");
		panelLogin = new JPanel();
		panelSend = new JPanel();
		panelChat = new JPanel();
		panelUsers = new JPanel();
		panelConnect = new JPanel();
		btConnect = new JButton("Connect");
		btDisconnect = new JButton("Disconnect");
		btSend = new JButton("");
		btSend.setPreferredSize(new Dimension(100,100));
		tfServer = new JTextField(10);
		tfPort = new JTextField(5);
		tfChat = new TransparentTextArea();
		tfNick = new JTextField(7);
		tfUsers = new TransparentTextArea();
		tfSend = new TransparentTextArea();
		tfSend.setPreferredSize(new Dimension(0,130));
		labelServer = new JLabel("Server :");
		labelNick = new JLabel("Nickname :");
		labelPort = new JLabel("Port :");
		listener = new ButtonsListener();
		scrollerUsers = new JScrollPane(tfUsers);
		scroller = new JScrollPane(tfChat);
	}
	public boolean connect()
	{
		String server,port;
		server = tfServer.getText();
		port = tfPort.getText();
		if(!(port.equals("") && server.equals("")));
		try
		{
			Socket socket = new Socket(server,Integer.parseInt(port));
			connection = new ConnectionProxy(socket);
			addConsumer(connection);
			connection.addConsumer(this);
			connection.start();
			return true;
		} catch (NumberFormatException | IOException e1)
		{
			JOptionPane.showMessageDialog(null, "Connection failed!");
			return false;
		}
	}
	public class ButtonsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource()==btConnect)
			{
				if(connect())
				{
					btDisconnect.setEnabled(true);
					btConnect.setEnabled(false);
					tfServer.setEnabled(false);
					tfPort.setEnabled(false);
					btSend.setEnabled(true);
				}
				else
					btDisconnect.doClick();
			}
			if(e.getSource()==btDisconnect)
			{
				btDisconnect.setEnabled(false);
				btConnect.setEnabled(true);
				tfServer.setEnabled(true);
				tfPort.setEnabled(true);
				btSend.setEnabled(false);
			}
			if(e.getSource()==btSend)
			{
				String str = tfSend.getText();
				tfSend.setText(null);
				if(!str.equals(""))
					connection.consume(str);
			}
		}
	}
	
	public void start() {
		Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
		btConnect.addActionListener(listener);
		btDisconnect.addActionListener(listener);
		btSend.addActionListener(listener);
		btSend.setEnabled(false);
		frame.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent event) {
				System.exit(0);
			}
		});
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollerUsers.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollerUsers.setPreferredSize(new Dimension(150,0));
		scroller.getViewport().setOpaque(false);
	    scroller.setBorder(null);
	    scrollerUsers.getViewport().setOpaque(false);
	    scrollerUsers.setBorder(null);
	    scroller.setOpaque(false);
	    scrollerUsers.setOpaque(false);
		tfChat.setEditable(false);
		tfUsers.setEditable(false);
		tfSend.setFont(new Font("Tahoma", Font.BOLD, 13));
		tfSend.setForeground(Color.white);
		tfUsers.setFont(new Font("Tahoma", Font.BOLD, 13));
		tfUsers.setForeground(Color.white);
		tfChat.setFont(new Font("Tahoma", Font.BOLD, 13));
		tfChat.setForeground(Color.white);
		tfNick.setFont(new Font("Tahoma",Font.PLAIN,14));
		tfServer.setFont(new Font("Tahoma",Font.PLAIN,14));
		tfPort.setFont(new Font("Tahoma",Font.PLAIN,14));
		frame.setContentPane(new JLabel(new ImageIcon("src//resources//background1.jpg")));
		frame.setLayout(new BorderLayout());
		btSend.setIcon(new ImageIcon("src//resources//send2.png"));
		btSend.setPressedIcon(new ImageIcon("src//resources//sendPressed.png"));
		btSend.setToolTipText("Send a message!");
		btSend.setBorder(null);
		btSend.setContentAreaFilled(false);
		btSend.setCursor(new Cursor(12));
		panelLogin.setOpaque(false);
		panelConnect.setOpaque(false);
		panelChat.setOpaque(false);
		panelSend.setOpaque(false);
		panelUsers.setOpaque(false);
		panelLogin.setLayout(new FlowLayout());
		panelConnect.setLayout(new FlowLayout());
		panelChat.setLayout(new BorderLayout ());
		panelSend.setLayout(new BorderLayout ());
		panelLogin.add(labelNick);
		panelLogin.add(tfNick);
		panelLogin.add(labelServer);
		panelLogin.add(tfServer);
		panelLogin.add(labelPort);
		panelLogin.add(tfPort);
		panelConnect.add(btConnect);
		panelConnect.add(btDisconnect);
		panelLogin.add(panelConnect,BorderLayout.CENTER);
		btDisconnect.setEnabled(false);
		panelChat.add(scroller,BorderLayout.CENTER);
		panelChat.add(scrollerUsers, BorderLayout.EAST);
		panelSend.add(tfSend,BorderLayout.CENTER);
		panelSend.add(btSend,BorderLayout.EAST);
		frame.add(panelLogin, BorderLayout.NORTH);
		frame.add(panelChat, BorderLayout.CENTER);
		frame.add(panelSend, BorderLayout.SOUTH);
		frame.pack();
		frame.setSize(1000,800);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	public class TransparentTextArea extends JTextArea {

        public TransparentTextArea() {
            setOpaque(false);
            setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 10), new LineBorder(Color.LIGHT_GRAY)));
        }

        @Override
        protected void paintComponent(Graphics g) {
        	super.paintComponent(g);
            g.setColor(new Color(255, 255, 255, 80));
            Insets insets = getInsets();
            int x = insets.left;
            int y = insets.top;
            int width = getWidth() - (insets.left + insets.right);
            int height = getHeight() - (insets.top + insets.bottom);
            g.fillRect(x, y, width, height);
        }

    }
	public void addConsumer(StringConsumer stringConsumer)
	{
		sc = stringConsumer;
	}
	@Override
	public void consume(String str)
	{
		tfChat.append(str+"\n");
	}
		
}
