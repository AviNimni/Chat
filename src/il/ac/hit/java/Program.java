package il.ac.hit.java;

import javax.swing.SwingUtilities;

public class Program {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ClientGUI ui = new ClientGUI();
				ui.start();
			}
		});
	}

}