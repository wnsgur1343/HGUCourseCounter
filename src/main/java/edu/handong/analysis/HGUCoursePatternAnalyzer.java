package edu.handong.analysis;

import java.io.File;
import java.io.FileInputStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;

import edu.handong.analysis.datamodel.Course;
import edu.handong.analysis.datamodel.Student;
import edu.handong.analysis.utils.NotEnoughArgumentException;
import edu.handong.analysis.utils.Utils;

public class HGUCoursePatternAnalyzer {
	
	public int Year;
	public String startYear;
	public String endYear;
	public int Semester;
	public String CourseCode;
	public String CourseName;
	public int TotalStudents = 0;
	public int StudentsTaken = 0;
	public float Rate;
	int Type; // Type== 1 --> a = 1 // Type ==2 --> a =2
	
	String dataPath; // csv file to be analyzed
	String resultPath; // the file path where the results are saved.
	
	
	private HashMap<String,Student> students;
	
	/**
	 * This method runs our analysis logic to save the number courses taken by each student per semester in a result file.
	 * Run method must not be changed!!
	 * @param args
	 */
	public void run(String[] args) {
		Options options = new Options();
		createOption(options);
		parseOption(options, args);
		
		
		try {
			// when there are not enough arguments from CLI, it throws the NotEnoughArgmentException which must be defined by you.
			if(args.length<2)
				throw new NotEnoughArgumentException();
		} catch (NotEnoughArgumentException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		//String dataPath = args[0]; // csv file to be analyzed
		//String resultPath = args[1]; // the file path where the results are saved.
		
		//CSV 파일 읽어들이는 LOGIC 구현.
		ArrayList<CSVRecord> reallines = new ArrayList<CSVRecord>();
		CSVParser parser;
		
		try {
			Reader reader = Files.newBufferedReader(Paths.get(dataPath));
			parser = new CSVParser(reader, CSVFormat.EXCEL.withTrim());
			
			Iterator<CSVRecord> iterator = parser.iterator();
			
			while(iterator.hasNext()) {
				reallines.add(iterator.next());
			}
			
		} catch(Exception e) {
			System.exit(0);
		}
		
		
		ArrayList<String> lines = Utils.getLines(dataPath, true); // String -> CSV, 필요없음..! 여기서 그냥 CSV 읽으면 됌! PARSER를 만듬.
		
		students = loadStudentCourseRecords(reallines); // 이 METHOD에서 CSVPARSER를 넘김..
		
		// To sort HashMap entries by key values so that we can save the results by student ids in ascending order.
		Map<String, Student> sortedStudents = new TreeMap<String,Student>(students); 
		
		// Generate result lines to be saved.
		ArrayList<String> linesToBeSaved = countNumberOfCoursesTakenInEachSemester(sortedStudents, Type);
		
		// Write a file (named like the value of resultPath) with linesTobeSaved.
		Utils.writeAFile(linesToBeSaved, resultPath, Type);
	}
	
	/**
	 * This method create HashMap<String,Student> from the data csv file. Key is a student id and the corresponding object is an instance of Student.
	 * The Student instance have all the Course instances taken by the student.
	 * @param lines
	 * @return
	 */
	private HashMap<String,Student> loadStudentCourseRecords(ArrayList<CSVRecord> lines) {
		
		// TODO: Implement this method 1
		HashMap<String,Student> newHash = new HashMap<String,Student>();
		
		
		for(CSVRecord str : lines) {
			Course newCourse = new Course(str);
			Student newStudent = new Student(newCourse.getStudentId());
			
			if(newHash.containsKey(newCourse.getStudentId())) {
				newHash.get(newStudent.getStudentId()).addCourse(newCourse);
			}
			else {
				newStudent.addCourse(newCourse);
				newHash.put(newStudent.getStudentId(), newStudent);
			}
		
		}
	
		
		return newHash; // do not forget to return a proper variable.
	}

	/**
	 * This method generate the number of courses taken by a student in each semester. The result file look like this:
	 * StudentID, TotalNumberOfSemesterssRegistered, Semester, NumCoursesTakenInTheSemester
	 * 0001,14,1,9
     * 0001,14,2,8
	 * ....
	 * 
	 * 0001,14,1,9 => this means, 0001 student registered 14 semeters in total. In the first semeter (1), the student took 9 courses.
	 * 
	 * 
	 * @param sortedStudents
	 * @return
	 */
	private ArrayList<String> countNumberOfCoursesTakenInEachSemester(Map<String, Student> sortedStudents, int Type) {
		
		// TODO: Implement this method
		ArrayList<String> savedList = new ArrayList<String>();
		int sumOfSemester = 0;
		
		for(Map.Entry<String,Student> entry : sortedStudents.entrySet()) {
	         Student nowStudent = entry.getValue();
	         String studentId = nowStudent.getStudentId();
			
			sumOfSemester = nowStudent.getSemestersByYearAndSemester().size();
			
			TotalStudents = 0;
			StudentsTaken = 0;
			
			if(Type == 1) {
				for(int j = 1; j<=sumOfSemester ; j++) {
					
					int numOfCoursesInNthSemester = nowStudent.getNumCourseInNthSemester(j);
					String savedline;
					
					savedline = studentId + "," + sumOfSemester + "," + j + "," + numOfCoursesInNthSemester;
					
					
					
					savedList.add(savedline);
					
				}
			}
			
			if(Type == 2) {
				//option a == 2
				//어레이 리스트에 year, semester, courseCode, CourseName, TotalStudents, StudentsTaken, Rate를 집어 넣는다. (,로 연결해서)
				
				ArrayList<Course> coursesPerStudent = new ArrayList<Course>();
				coursesPerStudent = nowStudent.getCoursesTaken();
				
				for(Course cour : coursesPerStudent) {
					// set CourseName
					if(cour.getcourseCode().equals(CourseCode)) {
						CourseName = cour.getcourseName();
						Year = cour.getYearTaken();
						Semester = cour.getSemesterCourseTaken();
						
						//set StudentsTaken
						++StudentsTaken;
						
						//set TotalStudents
						//set TotalStudents
						HashMap<String, Integer> YearAndSemesterPerStudent = new HashMap<String, Integer>();
						YearAndSemesterPerStudent = nowStudent.getSemestersByYearAndSemester();
						
						for(String key : YearAndSemesterPerStudent.keySet()) {
							if(key.equals(Year+"-"+Semester)) {
								++TotalStudents;
							}
						}
				
					}
				}
				
				Rate = (float)StudentsTaken / TotalStudents;
				
				
				// save lines
				String savedline;
				savedline = Year + "," + Semester + "," + CourseCode + "," + CourseName + "," + TotalStudents + "," + StudentsTaken + "," + Rate + "%";
				
				savedList.add(savedline);
				
			}
			
		}
		
		return savedList; // do not forget to return a proper variable.
	}
	
	private void createOption(Options options) {
		options.addOption(Option.builder("i").longOpt("input")
				.desc("Set an input file path")
				.hasArg()
				.argName("Input path")
				.required()
				.build());
		
		options.addOption(Option.builder("o").longOpt("output")
				.desc("Set an output file path")
				.hasArg()
				.argName("Output path")
				.required()
				.build());
		
		options.addOption(Option.builder("a").longOpt("analysis")
				.desc("1: Count courses per semester, 2: Count per course name and year")
				.hasArg()
				.argName("Analysis option")
				.required()
				.build());
		
		options.addOption(Option.builder("c").longOpt("coursecode")
				.desc("Course code for '-a 2' option")
				.hasArg()
				.argName("course code")
				.required()
				.build());
		
		options.addOption(Option.builder("s").longOpt("startyear")
				.desc("Set the start year for analysis e.g., -s 2002")
				.hasArg()
				.argName("Start year for analysis")
				.required()
				.build());
		
		options.addOption(Option.builder("e").longOpt("endyear")
				.desc("Set the end year for analysis e.g., -e 2005")
				.hasArg()
				.argName("End year for analysis")
				.required()
				.build());
		
		options.addOption(Option.builder("h").longOpt("help")
				.desc("Show a Help page")
				//.hasArg()
				.argName("Help")
				//.required() // No, option value No
				.build());
	}
	
	private boolean parseOption(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();
		
		try {
			CommandLine cmd = parser.parse(options, args);
			
			
			dataPath = cmd.getOptionValue("i");
			resultPath = cmd.getOptionValue("o");
			Type = Integer.parseInt(cmd.getOptionValue("a"));
			startYear = cmd.getOptionValue("s");
			endYear = cmd.getOptionValue("e");
			CourseCode = cmd.getOptionValue("c");
			
			
			
		} catch(Exception e) {
			printHelp(options);
			return false;
		}
		
		return true;
	}

	private void printHelp(Options options) {
		// TODO Auto-generated method stub
		HelpFormatter formatter = new HelpFormatter();
		String header = "HGU Course Analyzer";
		String footer = "";
		formatter.printHelp("HGUCourseCounter", header, options, footer, true);
	}
	
	
	public String getKey(HashMap<String, Integer> semesters, int value) {
		// TODO Auto-generated method stub
		for(String key : semesters.keySet()) {
			if(value == semesters.get(key)) {
				return key;
			}
		}
		return null;
	}
}
