package edu.westga.dsdm.mvd.fileinput;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import edu.westga.dsdm.mvd.util.ArrayUtil;

/**
 * Class FileMVDCBuilder
 * 
 * Construct an MVDC instance as specified in a given text file. See the readme
 * file for the file format.
 * 
 * @author CS3152
 * @version Fall 2022
 */
public class MVDCFileReader {
	private int lineNumber;
	private Scanner scanner;
	private String filename;

	// data of MVDC instance
	private String name;
	private int numberSets;
	private int numberLabels;
	private int[] labels;
	private int numberRegions;
	private int[][] regions;

	/**
	 * Instantiates a new MVDCFileReader; sets the MVDC instance data to an empty
	 * MVDC instance, i.e. an instance without sets and without labels.
	 * 
	 * @pre none
	 * @post getName().equals("") AND getNumberSets() == 0 AND getNumberLabels() ==0
	 *       AND getLabels().length == 0 AND getNumberRegions() == 0 AND
	 *       getRegions().length == 0
	 */
	public MVDCFileReader() {
		this.name = "";
		this.numberSets = 0;
		this.numberLabels = 0;
		this.labels = new int[0];
		this.numberRegions = 0;
		this.regions = new int[0][0];
	}

	/**
	 * Gets the name of the read MVDC instance
	 * 
	 * @pre none
	 * @post none
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Gets the number of sets of the read MVDC instance
	 * 
	 * @pre none
	 * @post none
	 * @return the number of sets
	 */
	public int getNumberSets() {
		return this.numberSets;
	}

	/**
	 * Gets the number of labels of the read MVDC instance
	 * 
	 * @pre none
	 * @post none
	 * @return the number of labels
	 */
	public int getNumberLabels() {
		return this.numberLabels;
	}

	/**
	 * Gets the labels of the read MVDC instance
	 * 
	 * @pre none
	 * @post none
	 * @return the labels
	 */
	public int[] getLabels() {
		return this.labels;
	}

	/**
	 * Gets the number of regions of the read MVDC instance
	 * 
	 * @pre none
	 * @post none
	 * @return the number of regions
	 */
	public int getNumberRegions() {
		return this.numberRegions;
	}

	/**
	 * Gets the regions of the read MVDC instance
	 * 
	 * @pre none
	 * @post none
	 * @return the regions
	 */
	public int[][] getRegions() {
		return this.regions;
	}

	/**
	 * Reads the MVDC instance from the specified file and stores the data
	 * 
	 * @pre filename != null
	 * @post getName(), getNumberSets(), getNumberLabels(), getLabels(),
	 *       getNumberRegions(), getRegions() return the corresponding data of the
	 *       MVDC instance specified in the file with the specified name
	 * @param filename name of the file with an MVDC instance
	 */
	public void read(String filename) {
		if (filename == null) {
			throw new IllegalArgumentException("File name cannot be null");
		}
		this.filename = filename;
		File inputFile = new File(this.filename);
		try (Scanner inputScanner = new Scanner(inputFile)) {
			this.scanner = inputScanner;
			this.readMVDCInstanceFromScanner();
		} catch (Exception exc) {
			String msg = "Reading from file " + this.filename + " failed";
			msg += System.lineSeparator() + exc.getMessage();
			throw new RuntimeException(msg);
		}
	}

	/**
	 * Reads the MVDC instance from the specified scanner and stores the data
	 */
	private void readMVDCInstanceFromScanner() {
		this.readNameFromScanner();
		this.readNumberSetsFromScanner();
		this.readLabelsFromScanner();
		this.readRegionsFromScanner();
	}

	/**
	 * Reads the name of the instance from the scanner
	 */
	private void readNameFromScanner() {
		if (!this.scanner.nextLine().equalsIgnoreCase("Name:")) {
			throw new RuntimeException("Line 1: Keyword \"Name\" expected");
		}
		this.name = this.scanner.nextLine();
	}

	/**
	 * Reads the number of sets of the instance from the scanner
	 */
	private void readNumberSetsFromScanner() {
		if (!this.scanner.nextLine().equalsIgnoreCase("Sets:")) {
			throw new RuntimeException("Line 3: Keyword \"Sets\" expected");
		}
		try {
			String line = this.scanner.nextLine();
			this.numberSets = Integer.parseInt(line);
		} catch (Exception exc) {
			throw new RuntimeException("Line 4: integer number specifiyng the number of sets expected");
		}
		if (this.numberSets < 1) {
			throw new RuntimeException("Line 4: number of sets must be positive");
		}
	}

	/**
	 * Reads the labels of the instance from the scanner
	 */
	private void readLabelsFromScanner() {
		if (!this.scanner.nextLine().equalsIgnoreCase("Labels:")) {
			throw new RuntimeException("Line 5: Keyword \"Labels\" expected");
		}
		this.lineNumber = 6;
		ArrayList<Integer> labelList = new ArrayList<Integer>();
		try {
			String line = this.scanner.nextLine();
			while (!line.equalsIgnoreCase("Regions:")) {
				int label = Integer.parseInt(line);
				labelList.add(label);
				this.lineNumber++;
				line = this.scanner.nextLine();
			}
		} catch (Exception exc) {
			throw new RuntimeException(
					"Line " + this.lineNumber + ": integer number specifiyng a label or keyword \"Region\" expected");
		}
		if (labelList.size() < 1) {
			throw new RuntimeException("MVDC instance must have at least one label");
		}
		this.numberLabels = labelList.size();
		this.labels = ArrayUtil.returnAsIntArray(labelList);
	}

	private void readRegionsFromScanner() {
		ArrayList<int[]> regionList = new ArrayList<int[]>();
		this.numberRegions = 0;
		while (this.scanner.hasNextLine()) {
			this.lineNumber++;
			String regionLine = this.scanner.nextLine();
			if (regionLine.length() > 0) {
				ArrayList<Integer> sets = new ArrayList<Integer>();
				String[] setStrings = regionLine.split(",");
				for (String setString : setStrings) {
					int set = 0;
					try {
						set = Integer.parseInt(setString);
						if (set < 1 || set > this.numberSets) {
							throw new RuntimeException();
						}
					} catch (Exception exc) {
						throw new RuntimeException("Line " + this.lineNumber + ": invalid set");
					}
					sets.add(set);
				}
				regionList.add(ArrayUtil.returnAsIntArray(sets));
				this.numberRegions++;
				if (this.numberRegions > this.numberLabels) {
					throw new RuntimeException("The number regions cannot be greater than the number labels");
				}
			}
			this.regions = ArrayUtil.returnAsArrayOfIntArrays(regionList);
		}
	}

	/**
	 * Prints the data of this MDVC instance read by this file reader to the console
	 * 
	 * @pre none
	 * @post none
	 */
	public void printToConsole() {
		System.out.println("***** MVDC Instance *****");
		System.out.println("name: " + this.name);
		System.out.println("number sets: " + this.numberSets);
		System.out.println("number labels: " + this.numberLabels);
		System.out.print("labels: ");
		for (int label : this.labels) {
			System.out.print(" " + label);
		}
		System.out.println();
		System.out.println("number regions: " + this.numberRegions);
		System.out.println("regions: ");
		for (int[] setsOfRegion : this.regions) {
			for (int set : setsOfRegion) {
				System.out.print(" " + set);
			}
			System.out.println();
		}
	}
}
