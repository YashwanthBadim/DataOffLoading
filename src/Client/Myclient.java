package Client;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import server.WebsiteDL1;
public class Myclient {

	public static void main(String[] args) throws IOException {

		try {
			String serveripv4address = "192.168.1.7";
			int portno = 9977;
			String Url = "";
			long time = System.currentTimeMillis();
			startClient(serveripv4address, portno, Url);
			System.out.println("Time takes : " + (System.currentTimeMillis()-time));
		} catch (Exception e) {
			System.err.println(e);
		}
		

	}

	public static void startClient(String serveripv4address, int portno, String Url)
			throws IOException {

		Socket socket1 = null;
		int filesize = 6022386;
		FileOutputStream foust = null;
		BufferedOutputStream boust = null;
		OutputStream oust = null;
		BufferedWriter buwr = null;
		String filetobereceived = System.getProperty("user.dir") + File.separator + "OutputClient.zip";
		int bytesread;
		int present = 0;
		boolean urlsent = false;
		boolean filereceived = false;
		try {
			socket1 = new Socket(serveripv4address, portno);

			oust = socket1.getOutputStream();

			PrintWriter out = new PrintWriter(socket1.getOutputStream(), true);

			if (!urlsent) {
				String inputUrl = "http://www.nku.edu/~raghavan/";
				System.out.println(" Input url : " + inputUrl);
				out.println(inputUrl);
				oust.flush();
				urlsent = true;
			}

			while (true) {
				if (!filereceived) {
					System.out.println(" File Transfer will start from server. Please Wait");
					byte[] bytearray = new byte[filesize];
					InputStream inst = socket1.getInputStream();
					foust = new FileOutputStream(filetobereceived);
					boust = new BufferedOutputStream(foust);
					bytesread = inst.read(bytearray, 0, bytearray.length);
					present = bytesread;
					if (present <= -1)
						continue;
					System.out.println("File transfer has begun");

					do {
						bytesread = inst.read(bytearray, present,
								(bytearray.length - present));
						if (bytesread >= 0)
							present += bytesread;
					} while (bytesread > -1);

					boust.write(bytearray, 0, present);
					boust.flush();
					System.out.println(" File " + filetobereceived
							+ " downloaded (" + present + " bytes read)");
					unZipFolder(filetobereceived);
					ZipFile zipf = new ZipFile(filetobereceived);
					zipf.close();
					File f1 = new File(filetobereceived);
					f1.delete();
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (oust != null) {
			}
			if (foust != null) {
				foust.close();
			}
			if (boust != null) {
				boust.flush();
				boust.close();
			}
			if (socket1 != null) {
				socket1.close();
			}
		}
	}

	public static void unZipFolder(String f2) {
		System.out.println(" Unzipping has started: " + f2);
		byte[] array = new byte[1024];
		String opFolder = System.getProperty("user.dir");
		ZipInputStream zinst = null;
		FileInputStream finst1 = null;
		FileOutputStream foust1 = null;
		ZipEntry zien1 = null;
		File folder1 = new File(opFolder);
		if (!folder1.exists()) {
			folder1.mkdir();
		}
		try {
			finst1 = new FileInputStream(f2);
			zinst = new ZipInputStream(finst1);
			zien1 = zinst.getNextEntry();

			while (zien1 != null) {
				String f3 = zien1.getName();
				File newfile = new File(opFolder + File.separator
						+ f3);
				new File(newfile.getParent()).mkdirs();

				foust1 = new FileOutputStream(newfile);

				int length;
				while ((length = zinst.read(array)) > 0) {
					foust1.write(array, 0, length);
				}

				foust1.close();
				zien1 = zinst.getNextEntry();
			}
			System.out.println(" UnZip Completed Successfully");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			if (zinst != null) {
				try {
					zinst.closeEntry();
					zinst.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (finst1!= null)
				try {
					finst1.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			if (foust1 != null)
				try {
					foust1.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

	}
}
