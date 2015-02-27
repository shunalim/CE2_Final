/** Program is done by
 * Name: Lim Shu Na
 * Matriculation Number: A0112502A
 * Tutorial Group: F13
 */

/**
 * This program allows user to key in their command and this class will execute the command. 
 * User can perform basic functionalities like adding a text, deleting a line of text, 
 * displaying and clearing all the text in the text file 'mytextfile.txt', searching for a text from 
 * the text file and not forgetting, sorting the text according to alphabetical order.
 */

/**
 * @author SHUNA
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class TextBuddy {
	
	/**
	 * Description: Declaring message Strings, Scanner object and Array List as a constants
	 */
	
	private static Scanner sc = new Scanner(System.in);
	private static ArrayList<String> list = new ArrayList<String>(); // store the list of contents
	
	private static final String NO_COMMAND_ENTERED = "There is no command!";
	private static final String NO_FILE_DELETED = "No file to be deleted";
	private static final String WORD_NOT_EXIST = "No such word";
	private static final String FILE_NOT_EXIST = "Nothing in the file to sort";
	private static final String INVALID_COMMAND = "Invalid command";
	private static final String WRONG_FILE_FORMAT = "Wrong file format";
	private static final String INVALID_COMMAND_TO_CLEAR = "Invalid command to clear";
	private static final String NO_CONTENT_TO_CLEAR = "No contents to be clear from the file";
	private static final String INVALID_COMMAND_TO_SEARCH = "Invalid search arguments";
	private static final String INVALID_COMMAND_TO_SORT = "Invalid sort arguments";
	private static final String FILE_SORTED = "File sorted";
	
	private static final String COMMAND_MESSAGE = "command: ";
	private static final String COMMANDTYPE_ERROR_MESSAGE = "Command type string cannot be null/empty!";
	private static final String UNRECOGNIZED_MESSAGE = "Unrecognized command type!";
	private static final String ERROR_MESSAGE = "No arguments found!";
	private static final String EXCEEDING_FILE_MESSAGE = "More than one file name!";
	
	private static final String WELCOME_MESSAGE = "Welcome to TextBuddy. %s is ready for use";
	private static final String ADDED_MESSAGE = "added to %s: \"%s\"";
	private static final String FILEISEMPTY_MESSAGE = "%s is empty";
	private static final String DELETE_MESSAGE = "deleted from %s: \"%s\"";
	private static final String CLEAR_MESSAGE = "all content cleared from %s";
	
	private static final int START_INDEX = 0;
	
	/**
	 * Description: These are the possible list of command types
	 */
	public enum COMMAND_TYPE {
		ADD, DISPLAY, DELETE, CLEAR, EXIT, INVALID, SEARCH, SORT
	};
	
	/**
	 * Description: The program will start off from this main function
	 * @param args contains command-line arguments as an array of String objects
	 *
	 */
	public static void main(String[] args) {
		
		String fileName = createOrSaveFile(args); // call method to check if the file exists
		readUserCommand(fileName);
	}

	/**
	 * Description: This method is to read in user's command and will stop until condition is met
	 * @param fileName will get this fileName from the main function 
	 * 			it is used as a name for the file for saving purposes 
	 */
	private static void readUserCommand(String fileName) {

		String[] userCommand;
		System.out.print(COMMAND_MESSAGE);

		while (true) {

			// This will separate the sentence into two when it meet a white space
			userCommand = sc.nextLine().split(" ");
			showToUser(executeCommand(fileName, userCommand)); // call method for the user to enter commands
			System.out.print(COMMAND_MESSAGE);
		}
	}

	/**
	 * Description: This method is to print out the text message
	 * @param text the text that are returned from the different methods
	 */
	private static void showToUser(String text) {
		System.out.println(text);
	}
	
	/**
	 * Description: This method is also to print out the text message, but from a bunch of arguments in String format
	 * @param text the text that are returned from the different methods
	 * @param args this is a object argument that allows us to string a bunch of arguments 
	 */
	private static void showToUser(String text, Object args){
		System.out.println(String.format(text, args));
	}

	/**
	 * Description: This method will exit if argument length is lesser than or equal to 0
	 * @param args this is to check the length of the argument
	 */
	private static void checkArgument(String[] args) {

		if (args.length <= 0) {
			showToUser(ERROR_MESSAGE);
			System.exit(0);
		} else if (args.length > 1) {
			showToUser(EXCEEDING_FILE_MESSAGE);
			System.exit(0);
		} else {
			String fileName = args[0];
			showToUser(WELCOME_MESSAGE, fileName);
		}
	}

	/**
	 * Description: This method will exit if the file type is in the wrong format
	 * @param fileName passed by the createOrSaveFile as a string for comparison
	 * @param args this is to check the format of the argument
	 */
	private static void checkFormat(String fileName, String[] args) {

		if (!fileName.contains(".")) {
			System.out.println(WRONG_FILE_FORMAT);
			System.exit(0);
		}

		int dotIndex_ = args[0].indexOf(".");
		String fileFormat_ = args[0].substring(dotIndex_, args[0].length());
		
		// ".txt" is a length of 4, so this if loop is checking if the file format is in the length of 4
		if (!(fileFormat_.length() == 4 && fileFormat_.equalsIgnoreCase(".txt"))) {
			System.out.println(WRONG_FILE_FORMAT);
			System.exit(0);
		}
	}

	/**
	 * Description: This method check if file exists and save it to the list
	 * @param args contains the file name and send to this method for saving purposes
	 * @return if condition is met, will return the file name 
	 */
	public static String createOrSaveFile(String[] args) {

		checkArgument(args); // to check if there is any argument
		String fileName = args[START_INDEX];
		checkFormat(fileName, args); // to check if file type is in the wrong format
		
		File file = new File(fileName);

		// if file exist, load the contents in the file to a arrayList
		if (file.exists() && !file.isDirectory())
			saveToArrayList(fileName);
		else {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return fileName;
	}

	/**
	 * Description: This method will save the text from the text file to an ArrayList
	 * @param fileName will save all the text to the file
	 */
	private static void saveToArrayList(String fileName) {

		try {
			FileReader reader = new FileReader(fileName);
			BufferedReader br = new BufferedReader(reader);
			String line = "";

			try {
				while ((line = br.readLine()) != null) {
					list.add(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Description: Writing contents to the text file
	 * @param fileName save and update the text to/from the file 
	 */
	private static void writeToFile(String fileName) {

		// this try and catch statement capture the behavior of the
		// file and adding the string to the file as well
		try {
			// setup a file writer
			FileWriter fw = new FileWriter(fileName);
			fw.flush();
			BufferedWriter bw = new BufferedWriter(fw);

			for (int i = 0; i < list.size(); i++) {
				bw.write(list.get(i).toString());
				bw.newLine();
			}
			bw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Description: This method is to execute the different types of commands based 
	 * 				what has the user keyed in
	 * @param fileName will save all the text to the file
	 * @param userCommand commands to be executed
	 * @return will return necessary string depending on the command type entered, else
	 * 			will return error message
	 */
	public static String executeCommand(String fileName, String[] userCommand) {

		COMMAND_TYPE commandType = determineCommandType(userCommand[0]);

		switch (commandType) {
		case ADD:
			return add(fileName, userCommand);
		case DISPLAY:
			return display(fileName, userCommand);
		case DELETE:
			return delete(fileName, userCommand);
		case CLEAR:
			return clear(fileName, userCommand);
		case INVALID:
			return INVALID_COMMAND;
		case SEARCH:
			return search(userCommand);
		case SORT:
			return sort(fileName, userCommand);
		case EXIT:
			System.exit(0);
			break;
		default:
			// throw an error if the command is not recognized
			return UNRECOGNIZED_MESSAGE;
		}
		
		return NO_COMMAND_ENTERED;
	}

	/**
	 * Description: This method is to determine the command type that is 
	 * 				keyed in do or do not exist
	 * @param commandTypeString contains command type that is to be executed
	 * @return will return necessary string depending on the command type entered
	 */
	private static COMMAND_TYPE determineCommandType(String commandTypeString) {

		if (commandTypeString == null)
			throw new Error(COMMANDTYPE_ERROR_MESSAGE);

		if (commandTypeString.equalsIgnoreCase("add"))
			return COMMAND_TYPE.ADD;
		else if (commandTypeString.equalsIgnoreCase("display"))
			return COMMAND_TYPE.DISPLAY;
		else if (commandTypeString.equalsIgnoreCase("delete"))
			return COMMAND_TYPE.DELETE;
		else if (commandTypeString.equalsIgnoreCase("clear"))
			return COMMAND_TYPE.CLEAR;
		else if (commandTypeString.equalsIgnoreCase("invalid"))
			return COMMAND_TYPE.INVALID;
		else if (commandTypeString.equalsIgnoreCase("search"))
			return COMMAND_TYPE.SEARCH;
		else if (commandTypeString.equalsIgnoreCase("sort"))
			return COMMAND_TYPE.SORT;
		else if (commandTypeString.equalsIgnoreCase("exit"))
			return COMMAND_TYPE.EXIT;
		else
			return COMMAND_TYPE.INVALID;
	}

	/** 
	 * Description: Adding text to file
	 * @param fileName will save the text that is to be added to the file
	 * @param text contains command type and text that is to be added
	 * @return depending on the condition met, and return as a String
	 */
	private static String add(String fileName, String[] text) {

		String inputText = "";
		int i = 1;
		
		if(text.length < 2){
			return "There is nothing to add";
		}

		while(i < text.length) {
			inputText += text[i];

			if ((i + 1) != text.length)
				inputText += " ";
			
			i++;
		}

		list.add(inputText);
		writeToFile(fileName);
		return String.format(ADDED_MESSAGE, fileName, inputText);
	}
	
	/** 
	 * Description: Displaying the content that is inside the file
	 * @param fileName will display all the text in the file
	 * @return depending on the condition met, and return as a String
	 */
	private static String display(String fileName, String[] text) {

		String display = new String();
		int i = 0;
		
		if(text.length != 1)
			return "Invalid command to display";

		// If list is empty
		if (list.isEmpty())
			return String.format(FILEISEMPTY_MESSAGE, fileName);

		while(i < list.size())
		{
			display += ((i + 1) + "." + list.get(i));

			// If there is still text left in the list
			if ((i + 1) != list.size())
				display += "\n";
			
			i++;
		}

		return display;
	}

	/** 
	 * Description: Deleting a line/sentence from the file
	 * @param fileName will delete the text that is to be deleted from the file
	 * @param index will delete the text according to the number assigned
	 * @return depending on the condition met, and return as a String
	 */
	private static String delete(String fileName, String[] text) {
		
		if (text.length !=  2){
			return "Arguments invalid";
		}
		
		if(!checkInteger(text[1])){
			return "Invalid delete argument";
		}
		
		int index = Integer.parseInt(text[1]);
		
		if (index > 0 && index <= list.size()) {
			String removedText = list.get(index - 1);
			list.remove(index - 1);
			writeToFile(fileName);
			return String.format(DELETE_MESSAGE, fileName, removedText);
		}

		return NO_FILE_DELETED;
	}
	
	/**
	 * Description: This method is to check if the text is an integer before
	 * 				it can be deleted
	 * @param text text passed down from the delete method for checking
	 * @return true or false based on the condition met
	 */
	private static boolean checkInteger(String text){
		try{
			Integer.parseInt(text);
		} catch (NumberFormatException e) {
			return false;
		}
		
		return true;
	}

	/** 
	 * Description: Clear all contents from the file, assume user only keyed in 'Clear"
	 * @param fileName will clear all the text from the file
	 * @return depending on the condition met, and return as a String
	 */
	public static String clear(String fileName, String[] text) {

		if(list.isEmpty())
			return NO_CONTENT_TO_CLEAR;
		
		if(text.length != 1)
			return INVALID_COMMAND_TO_CLEAR;
			
		clearList();
		
		try {
			// setup a file writer with nothing inside
			FileWriter fw = new FileWriter(fileName);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		return String.format(CLEAR_MESSAGE, fileName);
	}

	/**
	 * Description: Clear contents from the arrayList
	 */
	public static void clearList() {
		list.clear();
	}
	
	/** 
	 * Description: Search the words that you want to search
	 * @param userCommand contains the command type and the words to be search
	 * @return depending on the condition met, and return as a String
	 */
	private static String search(String[] userCommand) {
		
		if(userCommand.length != 2)
			return INVALID_COMMAND_TO_SEARCH;
		
		String searchWords = new String();

		System.out.println("List of words that contains the word " + userCommand[1] + ":");
		
		// Run through the whole arrayList and find the line which contain
		// the words that you want to search
		for (int i = 0; i < list.size(); i++) 
		{
			if (list.get(i).contains(userCommand[1])) 
			{
				searchWords += ((i + 1) + "." + list.get(i));
				
				// If there is still text left in the list
				if ((i + 1) != list.size()){
					searchWords += "\n";
				}
			}
		}
		
		if(searchWords.isEmpty()){
			return WORD_NOT_EXIST; // no such words
		}
		
		return searchWords;
	}
	
	/** 
	 * Description: Sorting an array list
	 * @param fileName contains the text from the text file that will be used for sorting
	 * @return depending on the condition met, and return as a String
	 */
	private static String sort(String fileName, String[] text) {
		
		if(text.length != 1)
			return INVALID_COMMAND_TO_SORT;
		
		if(list.isEmpty()){
			return FILE_NOT_EXIST; // no such words
		}
		
		// This is to sort the arrayList according to alphabetical order
		Collections.sort(list);
		writeToFile(fileName);
		return FILE_SORTED;
	}
}
