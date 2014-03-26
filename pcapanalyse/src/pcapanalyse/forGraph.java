package pcapanalyse;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class forGraph {
	
	public forGraph(String file, long[] times, long[] sizes){
		try {
            File newTextFile = new File("C:\\Users\\Olle\\Desktop\\projekt\\logs\\forgnu\\"+file+".data");
            
            String str = "";
            
            for (int i = 0; i < times.length; i++){
            	str += sizes[i] + "\t" + times[i] + "\n";
            }

            FileWriter fw = new FileWriter(newTextFile);
            fw.write(str);
            fw.close();

        } catch (IOException iox) {
            //do stuff with exception
            iox.printStackTrace();
        }
	}
	public forGraph(String file, long[] times){
		try {
            File newTextFile = new File(file+"T.data");
            String str = "";
            for (int i = 0; i < 326; i++){
            	str += i + "\t" + times[i] + "\n";
            }

            FileWriter fw = new FileWriter(newTextFile);
            fw.write(str);
            fw.close();

        } catch (IOException iox) {
            //do stuff with exception
            iox.printStackTrace();
        }
	}
	
	public forGraph(String file, float[] speed){
		try {
            File newTextFile = new File(file+"S.data");
            String str = "";
            for (int i = 0; i < speed.length; i++){
            	str += i + "\t" + speed[i] + "\n";
            }

            FileWriter fw = new FileWriter(newTextFile);
            fw.write(str);
            fw.close();

        } catch (IOException iox) {
            //do stuff with exception
            iox.printStackTrace();
        }
	}

	public forGraph(String file, ArrayList<Analyzer> data){
		try {
            File newTextFile = new File(file+"P.data");
            String str = "";
            for (int i = 0; i < data.size(); i+=2){
            	for(int j = 0; j < data.get(i).load; j++){
            	str += (i + 1) * j + "\t" + (data.get(i).time[j] - data.get(i + 1).time[j]) + "\n";
            	}
            }

            FileWriter fw = new FileWriter(newTextFile);
            fw.write(str);
            fw.close();

        } catch (IOException iox) {
            //do stuff with exception
            iox.printStackTrace();
        }
	}

}
