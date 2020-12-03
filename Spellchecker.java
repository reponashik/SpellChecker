//Importing modules
import java.util.*;
import java.io.*;
public class Spellchecker {
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
	//This method makes sure that a new word is positioned correctly in the dictionary when added
	public static void correctOrder(String file) throws FileNotFoundException{
		Scanner fileinput = new Scanner(new File(file));
		//Create Array List
	ArrayList<String> linesinfile = new ArrayList<String>();
		//while loop to parse through all lines
	while(fileinput.hasNextLine()) {
			linesinfile.add(fileinput.nextLine());
		}
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
		}
		});
		fileinput.close();
		//All sorted elements 
		//Dictionary is also updated at this stage
		System.out.println("The dictionary is now sorted in the correct order");
		PrintStream result = new PrintStream(new File("Dictionary.txt"));
		for(int i = 0;i<linesinfile.size();i++){
			result.println(linesinfile.get(i));
		}
		//close to write on result
		result.close();
		}	
	
public static void spellChecker(String name) throws FileNotFoundException {
		//adding txt as suffix
		Scanner input = new Scanner(new File(name + ".txt"));
		PrintStream out = new PrintStream(new File("result.txt"));
		//while loop to loop through all the lines of the file
		//the exit condition of the loop must be when the 
		//lenght reaches 0
		while(input.hasNextLine()) {
			String nextline = input.nextLine();
			if(nextline.length()==0){ //when length of line is 0 the file has ended
				out.println("                   ");
				continue;
				//out.println speaks to the print stream
			}
		String[] dicwords = nextline.split(" ");
			int j = 0;
            for(j = 0;j<dicwords.length;) {
            	//scanner for the dictionary file
            	Scanner dic = new Scanner(new File("Dictionary.txt"));
            	//constructing the option that deals with the word not being there
            	boolean present = false;
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
            if(present == false) {
            		System.out.println("The word " + dicwords[j] + " is not in the dictionary, you can type 'insert' to insert the word manually, or just add an appropriate replacement  ");
            		Scanner nextword = new Scanner(System.in);
            		//next word cannot be closed otherwise it will not check for multiple
            		//errors in one sentence in the tests
            		String insert = nextword.next();
            		if(insert.equals("insert")) {
            			//Printwriter 
            			PrintWriter print = new PrintWriter(new FileOutputStream("Dictionary.txt", true));
            			print.close();
            			//correctOrder() is a function that makes sure
            			//that the word inserted from the user is then
            			//correctly placed in the dictionary
            			correctOrder("Dictionary.txt");
            			System.out.println("The word you inserted is now present in your dictionary file");
            			
            			j++; 
            		}
            else {
            			//Option to not insert a new word
            			//Option for directly typing a replacement
            			dicwords[j] = insert;
            			//the line above exchanges the word not present
            			//with the replacement value
            			
            			nextline = String.join(" ", dicwords);
            			//String.join does the concatenation part 
            		}
            	}
            	
            }
            out.println(nextline); //prints to print stream
		}
		input.close(); //closes scanner
		out.close(); //closes print stream
     }
	
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

