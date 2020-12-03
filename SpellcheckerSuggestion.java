//Importing modules
import java.util.*;
import java.io.*;
public class SpellcheckerSuggestion {
	public static void main(String[] args) throws FileNotFoundException{
		//The error throw filenotfoundexception is needed to gracefully
		//deal with the program in case the file is not entered as 
		//a command line input
	System.out.println("Make sure that you already have created a file to spellcheck");
	System.out.println("You do not need to put '.txt' at the end of your file name"); //further indications
	System.out.println("-------------------------------------------------------------");
	System.out.print("Enter the filename that you wish to spellcheck: ");
		//user input collected
		Scanner inputuser = new Scanner(System.in);
		//nextLine() is going to keep looping 
		//until it reaches the end of a file 
		String fileName = inputuser.nextLine();
		//calling spellchecker function 
		//passing file name input from user as parameter
		spellChecker(fileName);
		//close scanner input
		inputuser.close();
		System.out.println("Thank you for submitting your file, your spellchecked output is stored in result.txt");
	}
	//Levenshtein algorithm implemented 
	public static int distancefromword(String first, String second) {
		first = first.toLowerCase();//tolowercase changes to lower case
		second = second.toLowerCase();
		int[] substitute = new int [second.length()+1];
		int i = 0;
		for(i = 0;i<substitute.length;i++) 
		substitute[i]=i; //switch
		int j = 1;
		for(j = 1;j<=first.length();j++){
			substitute[0] = j;
			int substr1 = j - 1; //compare similar substring
			for(i = 1;i<=second.length();i++) { //loops to find combinations
				int substr2 = Math.min(1+Math.min(substitute[i],substitute[i-1]), first.charAt(j-1)== second.charAt(i-1) ? substr1 : substr1 +1);
				//the line above seeks between two words which one is the minimum number of single-character edit
				substr1 = substitute[i];
				substitute[i] = substr2; //suggest close match
			}
		}
		//return result
		return substitute[second.length()];	
}
	
public static void spellChecker(String name) throws FileNotFoundException {
		//adding txt as suffix
		ArrayList<String> hints=new ArrayList<String>();
		Scanner input = new Scanner(new File(name + ".txt"));
		PrintStream out = new PrintStream(new File("result.txt"));
		//while loop to loop through all the lines of the file
		//the exit condition of the loop must be when the 
		//lenght reaches 0
		while(input.hasNextLine()) {
			String nextline = input.nextLine();
			if(nextline.length()==0){
				out.println("                          ");
				continue;
			}
		String[] dicwords = nextline.split(" "); //split separates the element with a space
			int j = 0; //counting variable
            for(j = 0;j<dicwords.length;) {
            	//scanner for the dictionary file
            	Scanner dic = new Scanner(new File("Dictionary.txt")); //passing dictionary as command input
            	boolean present = false; //set to false to start with
            	//another while loop to check
            	//all the lines of the file
            	while(dic.hasNextLine()){
            		//This while loop seeks for the word in question
            		//if it finds it in Dictionary.txt
            		//then this loop stops
            		if(dicwords[j].equalsIgnoreCase(dic.nextLine())){
            			present = true;
            		j++;
            			
            		break;
            		}
            	}
            	//close dictionary scanner
            	dic.close();
            	//the upcoming block is the code 
            	//that deals with the word 
            	//not being in the dictionary 
            if(present == false) 
            {
            	Scanner hintdict = new Scanner(new File("Dictionary.txt")); //new scanner
            	while(hintdict.hasNextLine()) { //check all the lines
            		String str = hintdict.nextLine(); //str contains the value that has been passed in first place
            		if(distancefromword(str, dicwords[j]) ==1) { //calling the distance algorithm
            			hints.add(str);
            		}
            	}
            hintdict.close();
            		
            	
            	////////////////////////////////////////////////////////////////////////////////////////////
            		System.out.println("The word " + dicwords[j] + " is not in the dictionary, here are some suggested words  ");
            		System.out.println("You can now add a replacement picking from the hints and just manually type it out ");
            		System.out.println("or you can insert the word in your file should it be required to go in the dictionary");
            		System.out.println("You can still add the word in the dictionary by typing 'insert'");
            		System.out.println("/////////////////////SOME SUGGESTIONS////////////////////////////////////////////");
            		for(int i = 0; i<hints.size();i++) {
            			System.out.println(hints.get(i)); //loops and prints possible suggestions from the dictionary
            		}
            		
            		//for(int j = 0; j < suggestions.size(); j++) {
					//System.out.println(suggestions.get(j));
				//}	
            		Scanner nextword = new Scanner(System.in);
            		String insert = nextword.next();
            		if(insert.equals("insert")) {
            			//Printwriter 
            			PrintWriter print = new PrintWriter(new FileOutputStream("Dictionary.txt", true));
            			print.println(dicwords[j]);
            			print.close();
            			//correctOrder() is a function that makes sure
            			//that the word inserted from the user is then
            			//correctly placed in the dictionary
            			correctOrder("Dictionary.txt");
            			System.out.println("The word you inserted is now present in your dictionary file");
            			nextword.close();
            			j++; 
            		}
            else {
            			//Option to not insert a new word
            			//Option for directly typing a replacement
            			dicwords[j] = insert;
            			//the line above exchanges the word not present
            			//with the replacement value
            			nextword.close();
            			nextline = String.join(" ", dicwords);
            			//String.join does the concatenation part 
            		}
            	}
            	
            }
            out.println(nextline); //prints to print stream
		}
		input.close(); //close
		out.close();
     }
//function to position new word in right order
public static void correctOrder(String file) throws FileNotFoundException{
		Scanner fileinput = new Scanner(new File(file));
		//Create Array List
	ArrayList<String> linesinfile = new ArrayList<String>();
		//while loop to parse through all lines
	while(fileinput.hasNextLine()) {
			linesinfile.add(fileinput.nextLine());
		}
	//.add appends list at the end of the list
	Collections.sort(linesinfile, new Comparator<String>() {
			//java.util.Collections.sort() method is present in java.util.Collections class. 
			//It is used to sort the elements present in the specified list 
			//of Collection in ascending order.
			@Override
			//Overriding is a feature that allows a subclass or child class 
			//to provide a specific implementation of a method that is 
			//already provided by one of its super-classes or parent classes
		public int compare(String firststring, String secondstring) {
		//public int compare is a method for comparing values and return a different 
		//value that follows a condition
				return firststring.compareToIgnoreCase(secondstring);	
	    //compare to ignore case 
		}
		});
		fileinput.close();
		//All sorted elements 
		//Dictionary is also updated
		System.out.println("The dictionary is now sorted in the correct order");
		PrintStream finaldic = new PrintStream(new File("Dictionary.txt"));
for(int i = 0;i<linesinfile.size();i++){
			finaldic.println(linesinfile.get(i));
		}
		finaldic.close();
		}	
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

