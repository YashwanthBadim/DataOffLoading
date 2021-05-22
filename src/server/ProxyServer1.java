package server;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;

public class ProxyServer1{

	public static void main(String[] args) throws IOException {

		try {
			int port = 9977;
			
			System.out.println("Starting Server" + ":" + " on port "
					+ port);
			
			startServer(port); 
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	
	public static void startServer(int port) throws IOException {
		
		ServerSocket s1 = new ServerSocket(port);

		final byte[] req = new byte[1024];
		byte[] reply = new byte[4096];
		Socket Myclient = null;
		try {
		
			Myclient = s1.accept();

			while (true) {
				final InputStream streamingfromClient = Myclient.getInputStream();
				final OutputStream streamingtoClient = Myclient.getOutputStream();
				String SrcFolder = System.getProperty("user.dir")
						+ File.separator + "Output";
				String DestFold = System.getProperty("user.dir")
						+ File.separator + "Output.zip";

				BufferedReader inClient = new BufferedReader(
						new InputStreamReader(streamingfromClient));
				
				System.out.println(" Client connection has established successfully ");
			String url = null;
				String s = inClient.readLine();
				url = s;
				System.out.println(" Input received from client is: " +s);
				
				System.out.println(" url : " + url);
				if (url == null) {
					System.out.println(" Invalid URL Input ");
					continue;
				}

				WebsiteDL1 data = new WebsiteDL1(url, SrcFolder);
				URL website = new URL(url);
				website.getContent();
				data.triggerDownload(website);
				System.out.println("Download Completed successfully \n Starting zipping");
				Zip1 zipping = new Zip1(SrcFolder, DestFold);
				zipping.createList(new File(SrcFolder));
				zipping.zipfile(DestFold);
				FileInputStream finst = null;
				BufferedInputStream binst = null;
				try {
					File f1 = new File(DestFold);
					byte[] bytearray = new byte[(int) f1.length()];
					finst = new FileInputStream(f1);
					binst = new BufferedInputStream(finst);
					binst.read(bytearray, 0, bytearray.length);
					System.out.println("Transmitting: " + DestFold + "("
							+ bytearray.length + " bytes)");
					streamingtoClient.write(bytearray, 0, bytearray.length);
					streamingtoClient.flush();
					streamingtoClient.close();
					System.out.println("Mission Accomplished");
				} finally {
					if (binst != null)
						binst.close();
					new File(DestFold).delete();
				}
				break;
			}
		} catch (Exception e) {
			System.err.println(e);
		} finally {
			try {
				if (Myclient != null)
					Myclient.close();
			} catch (IOException e) {
			}
		}
	}
}
