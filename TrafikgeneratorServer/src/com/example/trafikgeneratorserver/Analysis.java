package com.example.trafikgeneratorserver;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;


	
public class Analysis {

		  public static void main(String[] args) throws IOException {
			  String everything;
			  
			  BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Olle\\Desktop\\test.txt"));
			    try {
			        StringBuilder sb = new StringBuilder();
			        String line = br.readLine();

			        while (line != null) {
			            sb.append(line);
			            sb.append(System.lineSeparator());
			            line = br.readLine();
			        }
			        everything = sb.toString();
			    } finally {
			        br.close();
			    }
			 
			String[] str_array = everything.split("\n");
			String[][] str_array2 = new String[str_array.length][2];
			
			for(int i = 1; i < str_array.length; i++){
				String str = str_array[i];
				String[] str_array3 = str.split("\t");
				str_array2[i - 1][0] = str_array3[0] + " ";
				str_array2[i - 1][1] = str_array3[6] + " ";
	    	}
			
			int[][] d_array = new int[str_array2.length][2];
	    	for(int i = 0; i < str_array2.length; i++){
	    		if(str_array2[i][0] != null){
	    			d_array[i][0] =  Integer.parseInt(str_array2[i][0]);
	    			d_array[i][1] =  Integer.parseInt(str_array2[i][0]);
	    		}
	    	}
	    	
	    	String[][] str_array3 = new String[str_array.length][2];
	    	double[] d_array2 = new double[str_array2.length];
	    	for (int i = 0; i < str_array2.length; i++){
	    		d_array2[i] =  Double.parseDouble(str_array2[i][0]);
	    	}
	    	
	    	DrawGraph.Draw(d_array2);
		  }
		  
}


