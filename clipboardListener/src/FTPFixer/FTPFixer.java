package FTPFixer;

import java.awt.datatransfer.Transferable;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

import utils.FileUtils;

public class FTPFixer implements Runnable{
	/**
	 * 
	 * @param t a transferable from the clipboard. Should contain the file name of the corrupted ftp transfer
	 * @throws IOException 
	 */
	public int iteration;
	public byte[] bytes;
	public FTPFixer(int iteration) throws IOException{
		this.iteration = iteration;
		File file = new File("C:\\Users\\Zach\\Downloads\\corrupted.png.bz2");
		bytes = Files.readAllBytes(Paths.get("C:\\Users\\Zach\\Downloads\\corrupted.png.bz2"));
		boolean unzipped = false;
	}


	public static void main(String[] args) throws IOException{
		Thread t = new Thread(new FTPFixer(0));
		Thread t2 = new Thread(new FTPFixer((int)Math.pow(2, 26)));
		Thread t3 = new Thread( new FTPFixer(2 * (int) Math.pow(2,26)));
		Thread t4 = new Thread(new FTPFixer( 3 * (int) Math.pow(2, 26)));
		Thread t5 = new Thread(new FTPFixer(4 * (int) Math.pow(2,26)));
		Thread t6 = new Thread(new FTPFixer(5*(int)Math.pow(2, 26)));
		Thread t7 = new Thread( new FTPFixer(5 * (int) Math.pow(2,26)));
		Thread t8 = new Thread(new FTPFixer( 6 * (int) Math.pow(2, 26)));
		t.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		t6.start();
		t7.start();
		t8.start();
		
	}
	
	public boolean unzipFile(byte[] byteArray, File unzipped){
		int currentReturnCarriage = 0;
		try{
			InputStream in = new ByteArrayInputStream(byteArray);
			FileOutputStream out = new FileOutputStream(unzipped);
			BZip2CompressorInputStream bzIn = new BZip2CompressorInputStream(in);
			byte[] buffer = byteArray.clone();
			int n = 0;
			while (-1 != (n = bzIn.read(buffer))) {
				byte[] writing = buffer;
			for(int i = 0; i<writing.length; i++){
				if(writing[i]==0x0a && buffer[i+1]==0x0d){
					if(iteration%Math.pow(2, currentReturnCarriage++)==0){
						byte[] part1 = new byte[i];
						byte[] part2 = new byte[writing.length-i-1];
						System.arraycopy(writing, 0, part1, 0, i);
						System.arraycopy(writing, writing.length-i+1, part2, 0, writing.length-i-1);
						writing = new byte[writing.length -1];
						System.arraycopy(part1, 0, writing, 0, i);
						System.arraycopy(part2, 0, writing, i+1, part2.length);
					}
				}
			}
			  out.write(writing, 0, n);
			}
			out.close();
			bzIn.close();
		}catch(Exception e){
			unzipped.delete();
			return false;
		}
		return true;
	}


	@Override
	public void run() {
		boolean unzipped = false;
		File fixed;
		while (!unzipped){
			try {
				
				fixed = File.createTempFile("T://file"+iteration, ".bz2");
				unzipped = unzipFile(bytes,fixed);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println(iteration++);
		}
		System.exit(0);
	}
}
