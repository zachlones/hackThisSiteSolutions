package StringManipulator;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class StringManipulator {

	public static void main(String[] args){

	}
	public String stringToAnalyze;

	public StringManipulator(Transferable t) throws UnsupportedFlavorException, IOException{
		if (t != null && t.isDataFlavorSupported(DataFlavor.stringFlavor)){

			stringToAnalyze = (String) t.getTransferData(DataFlavor.stringFlavor);
			firstTwentyFiveNonNumericCharacters();
			System.out.println(numberMathStuff());
		}


	}

	public String firstTwentyFiveNonNumericCharacters(){
		String returning = "";
		for(char character:	stringToAnalyze.replaceAll("\\d", "").substring(0,25).toCharArray()){
			returning = returning + (char) (character+1);
		}
		System.out.println(returning);
		return returning;
	}

	public int numberMathStuff(){
		String numbers = stringToAnalyze.replaceAll("\\D","");
		int primes = 0;
		int composite = 0;
		for(char character:numbers.toCharArray()){
			switch(character){
			case '0': break;
			case '1': break;
			case '2': primes += 2; break;
			case '3': primes +=3; break;
			case '4': composite +=4; break;
			case '5': primes +=5; break;
			case '6': composite +=6; break;
			case '7': primes +=7; break;
			case'8': composite +=8; break;
			case '9': composite +=9; break;
			default: break;
			}
		}
		return primes*composite;
	}

	public String toString(){
		return firstTwentyFiveNonNumericCharacters() + numberMathStuff();
	}
	
}
