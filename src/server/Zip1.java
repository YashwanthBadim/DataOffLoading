package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zip1 {

	private List<String> List;
	private static String OUTPUTZippedFile = "";
	private static String SrcFolder = ""; 

	public Zip1(String src, String dest) {
		List = new ArrayList<String>();
		SrcFolder = src;
		OUTPUTZippedFile = dest;
	}

	public void zipfile(String zip1) {
		byte[] array = new byte[1024];
		String src = "";
		FileOutputStream foust = null;
		ZipOutputStream zoust = null;
		try {
			try {
				src = SrcFolder.substring(
						SrcFolder.lastIndexOf("\\") + 1,
						SrcFolder.length());
			} catch (Exception e) {
				src = SrcFolder;
			}
			foust = new FileOutputStream(zip1);
			zoust = new ZipOutputStream(foust);

			System.out.println("Zip Output: " + zip1);
			FileInputStream finst = null;
System.out.println("Files being zipped are:");
			for (String f1 : this.List) {
				System.out.println("" + f1);
				ZipEntry zien = new ZipEntry(src + File.separator + f1);
				zoust.putNextEntry(zien);
				try {
					finst = new FileInputStream(SrcFolder + File.separator
							+ f1);
					int length;
					while ((length = finst.read(array)) > 0) {
						zoust.write(array, 0, length);
					}
				} finally {
					finst.close();
				}
			}

			zoust.closeEntry();
			System.out.println("Folder zipped Successfully");

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				zoust.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private String createZipEntry(String f2) {
		return f2.substring(SrcFolder.length() + 1, f2.length());
	}

	public void createList(File n) {

		
		if (n.isFile()) {
			List.add(createZipEntry(n.toString()));

		}

		if (n.isDirectory()) {
			String[] subNote = n.list();
			for (String filename : subNote) {
				createList(new File(n, filename));
			}
		}
	}

	
}