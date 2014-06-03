package clipboardListener;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * A class to solve challenge 1 of hack this site
 * @author Zach
 *
 */
public class Unscrambler {
	String[] words;

	/**
	 * Separates the words into an array and sorts the letters alphabetically
	 * @param string the list of words delimited by new line characters
	 */
	public Unscrambler(String string){
		words = string.split("\n");
		for(int i = 0; i<words.length;i++){
			String word = words[i];
			char[] chars = word.toCharArray();
			Arrays.sort(chars);
			words[i]=new String(chars);
		}
	}
	
	/**
	 * A helper method which reads the word list's contents, sorts each line, and replaces
	 * matches with the dictionary word
	 */
	public void unscramble(){

		BufferedReader br = null;

		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader("C:\\Users\\Zach\\Downloads\\wordlist\\wordlist.txt"));

			while ((sCurrentLine = br.readLine()) != null) {
				//System.out.println("Comparing to: " + sCurrentLine);
				for(int j = 0; j<words.length;j++){
					String word = words[j];
					char[] chars =sCurrentLine.toCharArray();
					Arrays.sort(chars);
					String comparing = new String(chars);
					if(word.equals(comparing)){
						words[j] = sCurrentLine;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}


	}

	@Override
	/**
	 * Unscrambles the words and then returns the result as a single string with words delimited by a comma.
	 */
	public String toString(){
		unscramble();
		String s =words[0];
		for(int i = 1; i<words.length;i++){
			s=s+","+words[i];
		}
		return s;
	}
}
