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
	 * @return the unzipped xml file
	 * @throws IOException
	 */
	public static File unzip(File zipped) throws IOException{
		File xml = File.createTempFile("uncompressed", ".xml");
		FileInputStream in = new FileInputStream(zipped);
		FileOutputStream out = new FileOutputStream(xml);
		BZip2CompressorInputStream bzIn = new BZip2CompressorInputStream(in);
		final byte[] buffer = new byte[4096];
		int n = 0;
		while (-1 != (n = bzIn.read(buffer))) {
		  out.write(buffer, 0, n);
		}
		out.close();
		bzIn.close();
		return xml;
	}

}
