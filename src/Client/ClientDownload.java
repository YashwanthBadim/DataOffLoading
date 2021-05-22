package Client;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;

import server.WebsiteDL1;

public class ClientDownload {

	
	public static void main(String[] args) throws IOException
	
	{
		
		long time = System.currentTimeMillis();
		String url= "http://www.nku.edu/~raghavan/";
		WebsiteDL1 db = new WebsiteDL1(url, "C://workspace/Client Download");
		URL website = new URL(url);
		website.getContent();
		db.triggerDownload(website);
		System.out.println("Time takes : " + (System.currentTimeMillis()-time));
		
	}
}
