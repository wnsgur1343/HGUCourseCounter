package edu.handong.analysis.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Utils {
	
	public static ArrayList<String> getLines(String file, boolean removeHeader){
		Scanner inputStream = null;
		ArrayList<String> data = new ArrayList<String>();
		
		try {
			inputStream = new Scanner(new File(file));
		} catch (FileNotFoundException e) {
			System.out.println("Error: "+file+"is not opened.");
			e.printStackTrace();
		}
		if(removeHeader == true) {
			inputStream.nextLine();
		}
		while(inputStream.hasNextLine()) {
			String line = inputStream.nextLine();
			data.add(line);
		}
		
		inputStream.close();
		return data;
	}
	
	
	
	
	public static void writeAFile(ArrayList<String> lines, String targetFileName, int Type) {
		
		String FileName = null;
		String input = targetFileName;
		
		for(String targetDirectory : targetFileName.split("/")) {
			FileName = targetDirectory;
		}
		
		int index = input.indexOf(FileName);
		String dirName = input.substring(0, index);
		
		File dir = new File(dirName);
		
		if(!dir.exists()) {
			dir.mkdirs();
		}
		
		String fileName = targetFileName;
		PrintWriter outputStream = null;
		
		try {
			outputStream = new PrintWriter(fileName);
		} catch(FileNotFoundException e) {
			System.out.println("The file path does not exist. Please check your CLI argument!");
			System.exit(0);
		}
		
		if(Type == 1) {
			outputStream.println("StudentID, TotalNumberOfSemestersRegistered, Semester, NumCourse");
		}
		if(Type == 2) {
			outputStream.println("Year, Semester, CourseCode, CourseName, TotalStudents, StudentsTaken, Rate");
		}
		
		for(String line : lines) {
			outputStream.println(line);
		}
		
		outputStream.close();
	}
	
}
