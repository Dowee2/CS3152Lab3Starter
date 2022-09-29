package edu.westga.dsdm.mvd;

import edu.westga.dsdm.mvd.fileinput.MVDCFileReader;

/**
 * Class Solver
 * 
 * Demonstrates the use of the MVDCounter classes
 * 
 * @author CS3152
 * @version Fall 2022
 */
public class Solver {

	/**
	 * Entry point into the application
	 * 
	 * @pre args is a non-empty array of file names
	 * @post none
	 * @param args not used
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			System.err.println("Pass in the name of MVDC file(s) as command-line argument(s)");
			return;
		}
		MVDCFileReader fileReader = new MVDCFileReader();
		for (String filename : args) {
			try {
				fileReader.read(filename);
				fileReader.printToConsole();
				// ToDo solve the MVDC instance read from file and print the results to the
				// console
			} catch (Exception exc) {
				System.err.println(exc.getMessage());
			}
		}
	}
}
