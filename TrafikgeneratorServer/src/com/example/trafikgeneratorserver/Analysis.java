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
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Analysis {

	public static void main(String[] args) throws IOException {
		String everything;
		
		String filepath = (String)JOptionPane.showInputDialog(
                "Sökväg till loggfil:\n",
                JOptionPane.PLAIN_MESSAGE);
		
		if (filepath.isEmpty()){
			filepath = "C:\\Users\\Frans\\Desktop\\text.txt";
		}
		/*
		 *
		Scanner filenamescanner = new Scanner(System.in);
		String filepath = filenamescanner.nextLine();
		*/
		BufferedReader br = new BufferedReader(new FileReader(
				filepath));
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
		String[][] str_array2 = new String[str_array.length][3];

		String[] sent = new String[str_array.length];
		String[] ack = new String[str_array.length];

		for (int i = 1; i < str_array.length; i++) {
			String str = str_array[i];
			String[] str_array3 = str.split(" ");//changed from tab to space
			str_array2[i - 1][0] = str_array3[0];
			str_array2[i - 1][1] = str_array3[2];
			str_array2[i - 1][2] = str_array3[6];

		}
		int n = 0;
		int k = 0;
		String[] temp = new String[str_array2.length];
		boolean in_temp = false;
		for (int i = 0; i < str_array.length; i++) {
			
			try {
				for (int m = 0; m < str_array.length; m++) {
					
					if (str_array2[i][2].equals(temp[m])) {
						in_temp = true;
					}else{
						in_temp = false;
					}
				}
			} catch(NullPointerException e) {
			    break;
			}
			if (in_temp == false) {
				sent[n] = str_array2[i][0];
				n++;
				temp[i] = str_array2[i][2];
				for (int j = 0; j < str_array.length; j++) {
					if (str_array2[i][2].equals(str_array2[j][2]) && !str_array2[i][0].equals(str_array2[j][0])) {
						ack[k] = str_array2[j][0];
						k++;
					}
				}
			}
			in_temp = false;
		}

		/*
		 * for(int i = 0; i < str_array2.length - 1; i++){
		 * if(str_array2[i][1].equals("ACK") && !str_array2[i].equals(null)){
		 * //System.out.println("ACK bajs"); }else{
		 * //System.out.println("inte ACK"); sent[n] = str_array2[i][0];
		 * 
		 * for(int j = 0; j < str_array2.length - 1; j++){
		 * 
		 * if ((str_array2[j][1].equals("ACK"))){
		 * 
		 * if(str_array2[i][2].equals(str_array2[j][2])){ ack[k] =
		 * str_array2[j][0];
		 * 
		 * //System.out.println(ack[k]); k++; }
		 * 
		 * } } n++; }
		 * 
		 * }
		 */

		double[] d_array = new double[str_array2.length / 2];

		for (int i = 0; i < str_array2.length / 2; i++) {
			double a, b;
			a = Double.parseDouble(sent[i]);
			b = Double.parseDouble(ack[i]);
			d_array[i] = Math.abs(b - a);
		}

		/*
		 * int[][] d_array = new int[str_array2.length][2]; /*for(int i = 0; i <
		 * str_array2.length; i++){ if(str_array2[i][0] != null){ d_array[i][0]
		 * = Integer.parseInt(str_array2[i][0]); d_array[i][1] =
		 * Integer.parseInt(str_array2[i][0]); } }
		 * 
		 * String[][] str_array3 = new String[str_array.length][2]; double[]
		 * d_array2 = new double[str_array2.length]; for (int i = 0; i <
		 * str_array2.length - 1; i++){
		 * 
		 * d_array2[i] = Double.parseDouble(str_array2[i][0]);
		 * System.out.println(d_array2[i]); }
		 */

		DrawGraph.Draw(d_array);
	}

}
