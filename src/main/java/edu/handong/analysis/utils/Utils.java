package edu.handong.analysis.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
	
	public static void writeAFile(ArrayList<String> lines, String targetFileName) {
		try {
	         BufferedWriter fw = new BufferedWriter(new FileWriter(targetFileName));
	         
	        fw. write("StudentID"+","+"TotalNumberOfSemestersRegistered"+","+"Semester"+","+" NumCoursesTakenInTheSemester");
	        fw.newLine();
	        
	        for(String result : lines) {
	        	fw.write(result);
	        	fw.newLine();
	        	}
	        fw.flush();
            fw.close();
            
		} catch(Exception e ) {
			System.out.println(e.getMessage());
	        System.out.println("The file path does not exist. Please check your CLI argument!");    
		}
	}
}
