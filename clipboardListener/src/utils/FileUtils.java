package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

public class FileUtils {

	/**
	 * A method which decompresses the file
	 * @param zipped the zipped xml file
	 * @throws IOException
	 */
	public static boolean unzip(File zipped, File unzipped){
		try{FileInputStream in = new FileInputStream(zipped);
		FileOutputStream out = new FileOutputStream(unzipped);
		BZip2CompressorInputStream bzIn = new BZip2CompressorInputStream(in);
		final byte[] buffer = new byte[4096];
		int n = 0;
		while (-1 != (n = bzIn.read(buffer))) {
		  out.write(buffer, 0, n);
		}
		out.close();
		bzIn.close();}
		catch(Exception e){
			return false;
		}
		return true;
	}

}
