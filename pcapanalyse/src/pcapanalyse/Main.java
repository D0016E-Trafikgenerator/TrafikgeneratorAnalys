package pcapanalyse;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;

//"C:\\Users\\Olle\\Desktop\\mergedlogs.pcap"

public class Main {
	
	public static int FILES = 0;
	static Vector<String> filenames = new Vector<String>();
	static String meanspeeddata = "";
	static Vector<float[]> avgspeeds = new Vector<float[]>();
	public static ArrayList<Analyzer> data = new ArrayList<Analyzer>();


	public static void main(String[] args) {
		
		Gui GUI = new Gui();
		GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GUI.setVisible(true);
	}
	
	public static void addfile(String filename){
		filenames.add(filename);
	}
	
	public static void analize(String filepath){
		
		for (int i = 0; i < filenames.size(); i++){
			data.add(new Analyzer(filenames.get(i)));
			data.get(i).printStats();
			System.out.println(" ");
		}
		//makeLogsTime(filepath);
		//makeLogsSpeed(filepath);
		makeLogsPacketTime(filepath);
	}
	
	public static void makeLogsTime(String filepath){
		long[] times = new long[filenames.size()];
		for(int i = 0; i < data.size(); i++){
			times[i]=data.get(i).getTotalT();
		}
		new forGraph(filepath, times);
	}
	
	public static void makeLogsSpeed(String filepath){
		float[] speeds = new float[filenames.size()];
		for(int i = 0; i < data.size(); i++){
			speeds[i]=data.get(i).getSpeed();
		}
		new forGraph(filepath, speeds);
	}
	
	public static void makeLogsPacketTime(String filepath){
		data.get(0).setTimes();

		new forGraph(filepath, data.get(0).time);
	}
	
}