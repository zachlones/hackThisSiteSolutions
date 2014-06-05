package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class WebUtils {

	/**
	 * A helper method that downloads a file and saves it to the specified output file path
	 * Shamelessly stolen from: https://stackoverflow.com/questions/14413774/download-file-via-http-with-unknown-length-with-java
	 * @param url
	 * @param outputFile
	 * @return Returns true or false if it was successful
	 * @throws IOException 
	 */
	public static boolean downloadFile(URL url, File outputFile) throws IOException {
		boolean success = true;
		InputStream is = null;
		FileOutputStream fos = null;
		try {
			URLConnection urlConn = url.openConnection();//connect

			is = urlConn.getInputStream();               //get connection inputstream
			fos = new FileOutputStream(outputFile);   //open outputstream to local file

			byte[] buffer = new byte[4096];	//declare 4KB buffer
			int len;

			//while we have availble data, continue downloading and storing to local file
			while ((len = is.read(buffer)) > 0) {  
				fos.write(buffer, 0, len);
			}
		} catch (Exception e){
			success = false;
			e.printStackTrace();
		}finally {

			try {
				if (is != null) {
					is.close();
				}
			} finally {
				if (fos != null) {
					fos.close();
				}
			}
		}
		return success;
	}
}
