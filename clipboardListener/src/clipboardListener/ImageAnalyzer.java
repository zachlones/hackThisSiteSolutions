package clipboardListener;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

/**
 * A class for solving challenge 2 on HackThisSite.org's programming challenges
 * @author Zach
 *
 */
public class ImageAnalyzer {
	BufferedImage buffered;
	HashMap<String,String> keyValues;
	/**
	 * Constructs a new analyzer from the copied image by storing its bytes into a byte array
	 * @param t
	 * @throws IOException 
	 * @throws UnsupportedFlavorException 
	 */
	public ImageAnalyzer(Transferable t) throws UnsupportedFlavorException, IOException{
		//ensure an image is actually copied
		generateKeyList();
		if (t != null && t.isDataFlavorSupported(DataFlavor.imageFlavor))
        {
            Image image = (Image)t.getTransferData(DataFlavor.imageFlavor);
            buffered = toBufferedImage(image);
        }
	}
	
	public String getStringFromImage(){
		System.out.println("Reading Morse Code");
		if(buffered == null){
			System.out.println("buffered is null :/");
			return null;
		}
		String morseCode = "";
		int distance=0;
		for(int i = 0; i<buffered.getHeight();i++){
			for(int j=0; j<buffered.getWidth();j++){
				//System.out.print(buffered.getRGB(j, i) + ",");
				if(buffered.getRGB(j, i)==-1){
					//Pixel is white
					System.out.println("Distance from last white pixel: "+distance);
					morseCode = morseCode+new String(Character.toChars(distance));
					distance = 1;
				}
				else{
					//Pixel is not white
					distance++;
				}
			}
		}
		System.out.println(morseCode);
		return morseCode;
	}
	
	/**
	   * Returns the decoded message contained in the image
	   */
	  public String toString(){
		  String code ="";
		  String[] letters = getStringFromImage().split(" ");
		  for(int i = 0; i<letters.length;i++){
			  code=code+keyValues.get(letters[i]);
		  }
		  return code;
	  }
	
	/**
	 * Converts a given Image into a BufferedImage
	 * https://stackoverflow.com/questions/13605248/java-converting-image-to-bufferedimage
	 * @param img The Image to be converted
	 * @return The converted BufferedImage
	 */
	public static BufferedImage toBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage)
	    {
	    	System.out.println("Converted");
	        return (BufferedImage) img;
	    }

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    System.out.println("Converted");
	    return bimage;
	}
	
	/**
	 * Generates the morse code key
	 * Is there a better way to do this?..
	 */
	public void generateKeyList(){
		HashMap<String,String> key = new HashMap<String,String>();
		key.put(".-","a");
		key.put("-...","b");
		key.put("-.-.","c");
		key.put("-..","d");
		key.put(".","e");
		key.put("..-.","f");
		key.put("--.","g");
		key.put("....","h");
		key.put("..","i");
		key.put(".---","j");
		key.put("-.-","k");
		key.put(".-..","l");
		key.put("--","m");
		key.put("-.","n");
		key.put("---","o");
		key.put(".--.","p");
		key.put("--.-","q");
		key.put(".-.","r");
		key.put("...","s");
		key.put("-","t");
		key.put("..-","u");
		key.put("...-","v");
		key.put(".--","w");
		key.put("-..-","x");
		key.put("-.--","y");
		key.put("--..","z");
		key.put("-----","0");
		key.put(".----","1");
		key.put("..---","2"); 
		key.put("...--","3"); 
		key.put("....-","4");
		key.put(".....","5");
		key.put("-....","6"); 
		key.put("--...","7"); 
		key.put("---..","8");
		key.put("----.","9");
		key.put(".-.-.-",".");
		key.put("--..--",",");
		key.put("..--..","?");
		keyValues = key;
	}
}
