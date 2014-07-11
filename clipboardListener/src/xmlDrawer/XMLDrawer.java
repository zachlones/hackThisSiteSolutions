package xmlDrawer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import utils.FileUtils;

/**
 * A class which draws the answer to challenge 4 of HackThisSite.org's programming challenges
 * Clipboard listener requires a filename to be copied. The filepath rest of the filepath is below.
 * @author Zach
 *
 */
public class XMLDrawer {
	File xml;
	LinesComponent comp;
	/**
	 * A constructor which downloads the xml file and unzips it
	 * @param t the link to download the zipped xml file
	 * @throws IOException 
	 * @throws UnsupportedFlavorException 
	 * @throws DocumentException 
	 */
	public XMLDrawer(Transferable t) throws UnsupportedFlavorException, IOException, DocumentException{
		//ensure an image is actually copied
		if (t != null && t.isDataFlavorSupported(DataFlavor.stringFlavor))
		{
			String fileLocation = (String)t.getTransferData(DataFlavor.stringFlavor);
			//File zipped = File.createTempFile("drawme", ".bz2");
			File zipped = new File("C:\\Users\\Zach\\Downloads\\"+fileLocation);
			xml = File.createTempFile("unzipped", ".xml");
			System.out.println(zipped.getAbsolutePath());
			FileUtils.unzip(zipped, xml);
			
		}
		JFrame testFrame = new JFrame();
		testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		comp = new LinesComponent();
		comp.setPreferredSize(new Dimension(1000, 1000));
		comp.setBackground(Color.black);
		testFrame.getContentPane().add(comp, BorderLayout.CENTER);
		testFrame.setBackground(Color.black);
		testFrame.pack();
		testFrame.setVisible(true);
		
		parseXML();
	}
	
	/**
	 * A helper method which parses the xml file and adds all of the lines and arcs
	 * @throws DocumentException 
	 */
	public void parseXML() throws DocumentException{
		Document parsed = parse();
		
		generateGraph(parsed);
	}
	
	public Document parse() throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(xml);
        return document;
    }
	public void generateGraph(Document document) throws DocumentException {

        Element root = document.getRootElement();

        // iterate through child elements of root with element name "foo"
        List list = document.selectNodes("//ppcPlot/Line" );
        Iterator iter=list.iterator();
        while(iter.hasNext()){
        	Element element=(Element)iter.next();
        	Iterator iterator=element.elementIterator();
        	Color color=Color.black;
        	int x1=0;
        	int x2=0;
        	int y1=0;
        	int y2=0;
        	while(iterator.hasNext()){
        		Element lineElement=(Element)iterator.next();
        		if(lineElement.getName().equals("Color")){
        			//System.out.println("Color should be "+ lineElement.getText());
        			String clr = lineElement.getText();
        			if (clr.equals("yellow")){
        				clr = "orange";
        			}
        			try {
        			    Field field = Color.class.getField(clr);
        			    color = (Color)field.get(null);
        			} catch (Exception e) {
        			    color = null; // Not defined
        			}
        		}
        		if(lineElement.getName().equals("XStart")){
        			x1 = (int) Double.parseDouble(lineElement.getText());
        		}
        		if(lineElement.getName().equals("XEnd")){
        			x2 = (int) Double.parseDouble(lineElement.getText());
        		}
        		if(lineElement.getName().equals("YStart")){
        			y1 = (int) Double.parseDouble(lineElement.getText());
        		}
        		if(lineElement.getName().equals("YEnd")){
        			y2 = (int) Double.parseDouble(lineElement.getText());
        		}
        	}
        	comp.addLine(x1, y1, x2, y2,color);
        }
        
        list = document.selectNodes("//ppcPlot/Arc" );
        iter=list.iterator();
        while(iter.hasNext()){
        	Element element=(Element)iter.next();
        	Iterator iterator=element.elementIterator();
        	int xCenter=0;
        	int radius=0;
        	int arcStart=0;
        	int yCenter=0;
        	int arcExtend=0;
        	Color color=Color.black;
        	while(iterator.hasNext()){
        		Element lineElement=(Element)iterator.next();
        		if(lineElement.getName().equals("Color")){
        			String clr = lineElement.getText();
        			if (clr.equals("yellow")){
        				clr = "orange";
        			}
        			//System.out.println("Color should be "+ lineElement.getText());
        			try {
        			    Field field = Color.class.getField(clr);
        			    color = (Color)field.get(null);
        			} catch (Exception e) {
        			    color = null; // Not defined
        			}
        		}
        		if(lineElement.getName().equals("XCenter")){
        			xCenter = (int) Double.parseDouble(lineElement.getText());
        		}
        		if(lineElement.getName().equals("Radius")){
        			radius = (int) Double.parseDouble(lineElement.getText());
        		}
        		if(lineElement.getName().equals("ArcStart")){
        			arcStart = (int) Double.parseDouble(lineElement.getText());
        		}
        		if(lineElement.getName().equals("YCenter")){
        			yCenter = (int) Double.parseDouble(lineElement.getText());
        		}
        		if(lineElement.getName().equals("ArcExtend")){
        			arcExtend = (int) Double.parseDouble(lineElement.getText());
        		}
        	}
        	comp.addArc(xCenter, radius, arcStart, yCenter, arcExtend, color);
        }
     }
}
