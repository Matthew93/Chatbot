package chatbot;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class App {
	public static void main(String[] args) {

	
		Scanner scanner = new Scanner(System.in);
		
		//user insert the question:
		System.out.print("You: -");
		
		 String userInputString = scanner.nextLine(); 
			 
		 
		 
		 //while the string isn't stop
		while(userInputString.equals("stop") == false) {
			
			if(userInputString.contains("?")) 
				// replacing the question mark to avoid directory name error
				 userInputString= userInputString.replace("?", "");
				 
				 
			//Split the string inserted by the user
			String[] userInputArray = userInputString.split(" "); 
			
			//create the path where the answer should be
			String path = pathToAnswer(userInputArray) + "answer.txt";
			
			//verify if there is an answer for the user question in the path mentioned above
			File f = new File(path);
			
			if(f.exists() && !f.isDirectory()) { 
				//if there is an answer, will be printed and the user can insert another question
			    System.out.println("Chatbot: -"+readAnswer(path));
			    
			    System.out.print("You: -");
			    
			    userInputString = scanner.nextLine();
			}
			else {
				//otherwise the chatbot will ask for the answer and will store the answer in the coresponding txt file
				directoriesStructure("C:\\chatbot\\", userInputArray, userInputArray.length);
				
				System.out.println("Chatbot: -Care este raspunsul pentru aceasta intrebare?");
				
				System.out.print("You: -");
				
				String userInputAnswer = scanner.nextLine(); 
				
				writeAnswer(path, userInputAnswer);
				
				//after that, the user can insert another question or stop to close the conversation
				System.out.print("You: -");
				
				userInputString = scanner.nextLine();
			}
				
			 
		}
		scanner.close();
		System.out.println("Chatbot: -Bye, bye!");
		

	}
	
	//this method will return a path to a possible answer by spliting the String Array
	public static String pathToAnswer(String[] array) {
		
		String path = "C:\\chatbot\\";
		
		for(int i = 0; i< array.length;i++) {
			path = path + array[i] + "\\";
		}
		
		return path;
	}

	//this method will return the text readed from the txt file.
	public static String readAnswer(String fileName) {
		
		String answer = "";
		
		try {
			File myObj = new File(fileName);
			
			Scanner myReader = new Scanner(myObj);
			
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				answer = answer + data;
			}
			
			myReader.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		
		return answer;

	}

	//this method will write the answer from the user to txt file 
	public static void writeAnswer(String fileName, String answer) {
		
		try {
			FileWriter myWriter = new FileWriter(fileName);
			
			myWriter.write(answer);
			
			myWriter.close();
			
		} catch (IOException e) {
			System.out.println("An error occurred.");
			
			e.printStackTrace();
		}

	}


	//this method is used to create the directories structure depending on user input text
	public static void directoriesStructure(String makeDirectory, String[] path, int depth) {

		// makeDirectory stores the starting path
		// each string in path represents new
		// directory, depth stores the number
		// of directories to be created

		if (depth == 0)
			return;

		// decrementing the depth by 1
		depth -= 1;

		// checking if the path exists
		if (path.length == 0)
			
			System.out.println("Path does not exist");

		// execute if the path has more directories
		else {

			// appending the next directory
			makeDirectory = makeDirectory + "/" + path[0];

			// removing the first string
			// from path array

			List<String> list = new ArrayList<String>(Arrays.asList(path));
			
			list.remove(0);
			
			path = list.toArray(new String[0]);

			// creating File object
			File file = new File(makeDirectory);

			// if the directory doesn't exists
			if (!file.exists()) {
				// creating the directory
				boolean val = file.mkdir();
				if (val==false)
					System.out.println("Unable to " + "create Directory");
			} 
		}

		// recursive call
		directoriesStructure(makeDirectory, path, depth);

	}

}
