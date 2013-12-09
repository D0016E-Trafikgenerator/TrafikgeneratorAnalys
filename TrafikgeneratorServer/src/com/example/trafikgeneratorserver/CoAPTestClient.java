package com.example.trafikgeneratorserver;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Random;

import ch.ethz.inf.vs.californium.coap.Option;
import ch.ethz.inf.vs.californium.coap.OptionSet;
import ch.ethz.inf.vs.californium.coap.Request;
import ch.ethz.inf.vs.californium.coap.Response;
import ch.ethz.inf.vs.californium.coap.CoAP.ResponseCode;


public class CoAPTestClient {
	public static void main(String[] args) throws URISyntaxException, InterruptedException, IOException {
		int typ = 1;
		if (typ == 1) {
			while (new BufferedReader(new InputStreamReader(System.in)) != null) {
				System.out.println("DETTA PROGRAM PR�VAR NUMMERGENERATORN.");
				System.out.print("ANGE URI (ex. coap://localhost/random?12345): ");
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
				String adressen = bufferedReader.readLine();
				URI uri = new URI(adressen);
				Request meddelande = Request.newGet();
				
				
				
				Option blah = new Option();
				blah.setStringValue("poop=1234");
				blah.setNumber(123);
				OptionSet optioner = meddelande.getOptions();
				optioner.addOption(blah);
				Option portz = new Option();
				portz.setNumber(6666);
				optioner.addOption(portz);
				
				meddelande.setURI(uri);
				
				
				meddelande.setOptions(optioner);
				meddelande.send();
				Response svar = meddelande.waitForResponse(2000);
				long seed = Long.parseLong(meddelande.getOptions().getURIQueryString());
				Random rnd = new Random(seed);
				byte[] sentDummyData = new byte[150];
				rnd.nextBytes(sentDummyData);
				byte[] rcvDummyData = svar.getPayload();
				if (Arrays.equals(sentDummyData, rcvDummyData)) {
					System.out.print("FRAMG�NG! VI FICK TILLBAKA R�TT SEKVENS! UTDRAG: [");
					System.out.println((new String(sentDummyData, Charset.forName("ISO-8859-1"))).substring(0, 9) + "]");
				}
			}
		}
		else if (typ == 0) {
			System.out.println("DETTA PROGRAM PR�VAR HUR L�NG TID DET TAR ATT S�TTA OCH H�MTA ETT MEDDELANDE.");
			System.out.print("ANGE URI: ");
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			String adressen = bufferedReader.readLine();
			URI uri = new URI(adressen);
			
			System.out.print("ANGE TEXT ATT SKICKA: ");
			bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			String meddelandet = bufferedReader.readLine();
			
			System.out.print("ANGE ANTAL G�NGER ATT SKICKA: ");
			bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			int g�nger = Integer.parseInt(bufferedReader.readLine());
			
			System.out.println("DETTA SKICKAS " + g�nger + " G�NGER: " + meddelandet);
			
			for (int i = 1; i <= g�nger; i++) {
				Request meddelande = Request.newPost();
				meddelande.setPayload(meddelandet);
				meddelande.setURI(uri);
				meddelande.send();
				long tidF�rF�rstaAnropet = meddelande.getTimestamp();
				if (tidF�rF�rstaAnropet == 0)
					tidF�rF�rstaAnropet = System.currentTimeMillis();
				Response svar = meddelande.waitForResponse(2000);
				
				if (svar == null)
					System.out.println("INGEN SVARADE...");
				else if (svar != null && svar.getCode() == ResponseCode.CHANGED) {
					meddelande = Request.newGet();
					meddelande.setURI(uri);
					meddelande.send();
					svar = meddelande.waitForResponse(2000);
					if (svar == null) {
						System.out.println("INGEN SVARADE...");
						continue;
					}
					else if (svar != null && svar.getCode() == ResponseCode.CONTENT) {
						long tidF�rSistaSvar = svar.getTimestamp();
						if (tidF�rSistaSvar == 0)
							tidF�rSistaSvar = System.currentTimeMillis();
						long timestampskillnad = tidF�rSistaSvar-tidF�rF�rstaAnropet;
						System.out.println("[" + i + "] EFTER TIMESTAMPSKILLNADEN " + timestampskillnad + " KOM DETTA TILLBAKA: " + svar.getPayloadString());
					}
					else {
						System.out.println("INGET VETTIGT SVAR...");
						continue;
					}
				}
				else
					System.out.println("INGET VETTIGT SVAR...");
			}
		}
	}
}
