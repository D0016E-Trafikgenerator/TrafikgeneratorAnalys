package pcapanalyse;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.jnetpcap.Pcap;
import org.jnetpcap.packet.JMemoryPacket;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.protocol.JProtocol;
import org.jnetstream.capture.Captures;
import org.jnetstream.capture.FileMode;
import org.jnetstream.capture.file.RecordIndexer;
import org.jnetstream.capture.file.pcap.PcapFile;
import org.jnetstream.capture.file.pcap.PcapRecord;

public class Analyzer {
	
	long[] time, size, fnr;
	int load;
	private int psuedoload;
	private int TotalSize;
	static long first;
	private static long last;
	private float speed;
	private String file;
	private long TotalT;

	public Analyzer(String filename) {

		StringBuilder errbuf = new StringBuilder(); // For any error msgs
		this.file = filename;
		
		

		System.out.printf("Opening file for reading: %s%n", filename);

		Pcap pcap = Pcap.openOffline(file, errbuf);
		//Pcap pcap2 = Pcap.openLive(file, 0, 0, 0, errbuf);

		if (pcap == null) {
			System.err.printf("Error while opening device for capture: "
					+ errbuf.toString()+ "\n");
			return;
		}
		this.TotalT = 0;
		this.psuedoload = 7000;
		this.time = new long[psuedoload];
		this.size = new long[psuedoload];
		this.fnr = new long[psuedoload];
		this.TotalSize = 0;
		this.load = 0;
		first = 0;
		last = 0;
		
		
		PcapPacketHandler<String> jpacketHandler = new PcapPacketHandler<String>() {

			public void nextPacket(PcapPacket packet, String user) {

				fnr[load] = packet.getFrameNumber();
				time[load] = packet.getCaptureHeader().timestampInMillis();
				size[load] = packet.getTotalSize();

				if (load == 0) {
					first = packet.getCaptureHeader().timestampInMillis();
				}
				last = packet.getCaptureHeader().timestampInMillis();
				load++;
				
			}
		};
		try {
			pcap.loop(psuedoload, jpacketHandler, "jNetPcap rocks!");

		} finally {
			for(int j = 0; j < load; j++){
				TotalSize += size[j];
			}
			TotalSize = TotalSize / 1000;
			TotalT = last - first;
			speed = (TotalSize * 8) / (getTotalT() / 1000);
			pcap.close();
		}
	}

	public void printStats() {
		/*for (int j = 0; j < load; j++) {

			System.out.printf("Framnumber: %s \n", fnr[j]);
			System.out.printf("Timestamp: %s \n", time[j]);
			System.out.printf("Packetsize: %s \n", size[j]);
		}*/
			
		System.out.printf("Total Time: %s ms \n", getTotalT());
		System.out.printf("Total Size: %s kB \n", TotalSize);
		System.out.printf("Speed: %s kbps \n", speed);
		System.out.printf("Packets: %s \n", load);
		
	}
	
	public void setTimes(){
		long n = time[0];
		for(int i = 0; i < load; i++){
			time[i] = time[i] - n;
		}
	}

	public long getTotalT() {
		return TotalT;
	}
	
	public long[] getPaketSize(){
		return size;
	}
	
	public long[] getTime(){
		return time;
	}
	
	public int getTotalSize(){
		return TotalSize;
	}
	
	public int getLoad(){
		return load;
	}

	public float getSpeed(){
		return speed;
	}

}