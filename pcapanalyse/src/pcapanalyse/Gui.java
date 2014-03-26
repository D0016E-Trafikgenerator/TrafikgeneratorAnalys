package pcapanalyse;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Gui extends JFrame implements ActionListener{
	static JPanel panel = new JPanel();
	Container contentPane = this.getContentPane();
	Vector<String> files = new Vector<String>();
	
	public Gui() {
			setTitle("My Frame");
			setSize(300,200); // default size is 0,0
			setLocation(10,200); // default is 0,0 (top left corner)
			
			
			
			JButton b1 = new JButton("Add log file");
			JButton b2 = new JButton("Analyze!");
			
						
			contentPane.add(b1, BorderLayout.NORTH);
			contentPane.add(b2, BorderLayout.SOUTH);
			
		
			
			b1.setMnemonic(KeyEvent.VK_D);
			b1.setActionCommand("ADD");
			b2.setMnemonic(KeyEvent.VK_M);
			b2.setActionCommand("ANALYZE");
			
			b1.addActionListener(this);
			b2.addActionListener(this);
			
			
		 }
	 
	 
	 public void actionPerformed(ActionEvent e){
		 String com = e.getActionCommand();
			if (com.equals("ADD")){
				//show some representation of file
				//add the file to a filenamevector
				//make the filenamevector available from Main (some API function)
				final JFileChooser fc = new JFileChooser("C:\\Users\\Olle\\Desktop\\projekt\\logs");
				int returnVal = fc.showOpenDialog(Gui.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					Main.FILES += 1;				
					Main.addfile(file.getAbsolutePath());
					files.add(file.getName());
					
					JOptionPane.showMessageDialog(this, file.getName()+ " added. Files to analyze: " + files.size());
					
					
					JLabel myLabel = new JLabel(file.getName() + " added. Add more files or analyze them!", JLabel.LEFT);
					contentPane.add(myLabel);
					contentPane.repaint();
			       //TODO this label needs the frame to be minimized to show label.
			       //Also, it prints the labels on top of each other
				}
				
				
				
			}
			else if (com.equals("ANALYZE")){
				if(files.size() == 0){
					JOptionPane.showMessageDialog(this, "Choose at least one pcap file to analyze.");
				}else{
					final JFileChooser sc = new JFileChooser("C:\\Users\\Olle\\Desktop\\projekt\\logs\\forgnu");
					int returnVal = sc.showSaveDialog(Gui.this);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						File sfile = sc.getSelectedFile();
						Main.analize(sfile.getAbsolutePath());
					}
				}
			}
		
			
	} // actionPerformed
	
	 //TODO(but not really important) Fix this method to print the stats of the analyzing software to the jframe
	public static void writelabel(String stats){
		JLabel newLabel = new JLabel(stats, JLabel.LEFT);
	}
	 
	 
	 
	 
	/*static void initbuttons(){
		
		JFrame frame = new JFrame("Title");
		Container contentPane = frame.getContentPane();
		JTextField tf = new JTextField(20);
		JPanel panel = new JPanel();
		panel.add(tf);		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(350,350);
		JButton b1 = new JButton("Add more files");
		JButton b2 = new JButton("Analyze!");
		
		contentPane.add(b1, BorderLayout.NORTH);
		contentPane.add(b2, BorderLayout.SOUTH);
		
		b1.setMnemonic(KeyEvent.VK_D);
		b1.setActionCommand("ADD");
		b2.setMnemonic(KeyEvent.VK_M);
		b2.setActionCommand("ANALYZE");
		
		//b1.addActionListener(frame);
		//b2.addActionListener(this);
		
		frame.setVisible(true);
		
		
		
		
		
	}
	*/
}
