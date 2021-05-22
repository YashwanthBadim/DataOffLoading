package server;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.stylesheets.MediaList;

import java.util.StringTokenizer;

public class WebsiteDL1 {

	static List<String> inputurls = new ArrayList<String>();
	String URL;
	String Host;
	String SrcFolder = "";

	public WebsiteDL1(String url1, String loc){
		inputurls.add(url1);
		URL = url1;
		Host = url1;
		SrcFolder = loc;
	}

	public void triggerDownload(URL p_url) {
		List<URL> presentUrls = new ArrayList<URL>();
		presentUrls = downloadreturn(p_url);
		for (URL url : presentUrls) {
			triggerDownload(url);
		}
	}

	private boolean urlcheck(String link) {
		for (int i = 0; i < inputurls.size(); i++) {
			String url = inputurls.get(i);
			if (url.trim().equalsIgnoreCase(link.trim())) {
				return true;
			}
		}
		return false;
	}

	private List<URL> downloadreturn(URL url) {
		List<URL> k1 = new ArrayList<URL>();
		try {
			url.getContent();
			Document document = null;
			String s = url.toString();
			try {
				if (s.endsWith(".html") || s.endsWith(".htm"))
					;
				document = Jsoup.connect(s).get();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(" Could not connect to: " + s + " : "
						+ e.getClass().getName());
				return k1;
			}
			System.out.println(" Downloading data from link: " + s);
			File f2;
			if (s.endsWith("/")) {
				f2 = downloadOtherFiles(url, false, true);
			} else {
				f2 = downloadOtherFiles(url, true, false);
			}
			if (document == null)
				return k1;
			Elements l1 = document.select("a[href]");
			for (Element link : l1) {
				String templ1 = link.attr("abs:href");
				String val1 = link.attr("href");
				if (val1.startsWith(Host)
						&& (url.toString().endsWith(".html") || url.toString()
								.endsWith(".htm"))) {
					
					val1 = val1.substring(Host.length());
					Path p = Paths.get(f2.getAbsolutePath());
					Charset characterset = StandardCharsets.UTF_8;

					String cont = new String(Files.readAllBytes(p),
							characterset);
					cont = cont.replaceAll(Host, "");
					Files.write(p, cont.getBytes(characterset));
					
				}
				if (templ1.endsWith(".pdf") || templ1.endsWith(".txt")) {
					continue;
				}
				if (templ1.endsWith("index.html")) {
					templ1 = templ1.substring(0, templ1.lastIndexOf('i'));
				}
				if (templ1.startsWith(Host) && !urlcheck(templ1)) {
					inputurls.add(templ1.toString());
					k1.add(new URL(templ1));
				}
			}

			Elements e = document.select("[src]");
			for (Element src : e) {
				downloadMediaFiles(src);
			}
		} catch (Exception e) {
			System.out.println(" Invalid URL : " + url.toString() + " : "
					+ e.getClass().getName());
			return k1;
		}
		return k1;
	}
	public void downloadMediaFiles(Element src) throws IOException {

		String medurl = src.attr("abs:src");
		String p1 = medurl.substring(Host.length());
		StringTokenizer st1 = new StringTokenizer(p1, "/");
		String loc = SrcFolder;
		int count2 = st1.countTokens() - 1;
		for (int i = 0; i < count2; i++) {
			String d1 = st1.nextToken();
			loc = loc + File.separator + d1;
			if (!new File(loc).exists()) {
				new File(loc).mkdir();
			}
		}
		InputStream inst = new URL(medurl).openStream();
		OutputStream oust = new BufferedOutputStream(new FileOutputStream(
				loc + File.separator + st1.nextToken()));

		byte[] array = new byte[2048];
		int len;

		while ((len = inst.read()) != -1) {
			oust.write(len);
		}

		inst.close();
		oust.close();
	}



	public File downloadOtherFiles(URL url, boolean hut, boolean index)
			throws IOException {
		String website = url.toString();
		File f3;
		String p = website.substring(Host.length());
		StringTokenizer st = new StringTokenizer(p, "/");
		String location = SrcFolder;
		int count1 = st.countTokens();
		if (hut)
			count1 = count1 - 1;
		for (int i = 0; i < count1; i++) {
			String d = st.nextToken();
			location = location + File.separator + d;
			if (!new File(location).exists()) {
				new File(location).mkdir();
			}
		}
		if (index) {
			f3 = new File(location + File.separator + "index.html");
		} else {
			f3 = new File(location + File.separator + st.nextToken());
		}
		FileUtils.copyURLToFile(url, f3);
		return f3;

	}

}