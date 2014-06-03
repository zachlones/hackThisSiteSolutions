package clipboardListener;

import java.awt.*;  
import java.awt.datatransfer.*;  
import java.io.IOException;
   

/**
 * A clipboard listener created to aid in solving the HackThisSite programming challenges.
 * Reads clipboard contents and then alters the contents.
 * Change the code in processContents to switch between challenges
 * Mostly copied from http://www.coderanch.com/t/377833/java/java/listen-clipboard
 * @author Zach
 *
 */
class ClipboardListener extends Thread implements ClipboardOwner {  
  Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();  
   
  /**
   * Starts the clipboard listener.
   */
  public void run() {  
    Transferable trans = sysClip.getContents(this);  
    regainOwnership(trans);  
    System.out.println("Listening to board...");  
    while(true) {}  
  }  
   
  /**
   * Upon another process changing the clipboard's contents, lostOwnership is called.
   */
  public void lostOwnership(Clipboard c, Transferable t) {
	  try {  
		  Thread.sleep(20);   //Sleeping for a short amount of time reduces problems where another process is also altering clipboard contents
	  } catch(Exception e) {  
		  System.out.println("Exception: " + e);  
	  }  
	  Transferable contents = sysClip.getContents(this);
	  contents = processContents(contents);  
	  regainOwnership(contents);  
  }  
  
  /**
   * Processes the contents of the clipboard
   * If the contents is a String, then it is altered
   * otherwise the original contents are returned
   * @param t the current clipboard's contents
   * @return the modified contents
   */
  Transferable processContents(Transferable t) {
	  String result = "";
	  boolean hasTransferableText = (t != null) && t.isDataFlavorSupported(DataFlavor.stringFlavor);
	 // System.out.println(t.isDataFlavorSupported(DataFlavor.stringFlavor));
	  //System.out.println("Processing: " + t.toString());  
	  if (hasTransferableText) {
		  System.out.println("Has transferable text");
		  try {
			  result = (String)t.getTransferData(DataFlavor.stringFlavor);
			  System.out.println(result);
			  Unscrambler u = new Unscrambler(result);
			  result = u.toString();
			  System.out.println(result);
		  }
		  catch (UnsupportedFlavorException e){
			  e.printStackTrace();
		  } catch (IOException e) {
			  e.printStackTrace();
		  }
		  return new StringSelection(result);
	  }
	  else{
		  return t;
	  }
  }  
 
 /**
  * Sets the contents of the clipboard and sets this as the owner of the clipboard
  * @param t
  */
  void regainOwnership(Transferable t) {  
    sysClip.setContents(t, this);  
  }  
  
  public static void main(String[] args) {  
    ClipboardListener b = new ClipboardListener();  
    b.start();  
  }  
}  