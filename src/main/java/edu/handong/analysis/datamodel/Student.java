package edu.handong.analysis.datamodel;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.math3.geometry.spherical.oned.ArcsSet.Split;

@SuppressWarnings("unused")
public class Student {
	private String studentId;
	private ArrayList<Course> coursesTaken = new ArrayList<Course>();
	private HashMap<String,Integer> semestersByYearAndSemester = new HashMap<String,Integer>();

	public Student(String studentId) {
		this.studentId = studentId;
	}
	
	public void addCourse(Course newRecord) {
		coursesTaken.add(newRecord);
	}
	
	public HashMap<String,Integer> getSemestersByYearAndSemester(){
		ArrayList<Course> Icourse = new ArrayList<Course>();
		Icourse = getCoursesTaken();
		
		String yearTaken;
		String semesterTaken;
		int semesternum = 0;
		
		for(Course cour : Icourse) {
			yearTaken = Integer.toString(cour.getYearTaken());
			semesterTaken = Integer.toString(cour.getSemesterCourseTaken());
			
			
			if(!semestersByYearAndSemester.containsKey(yearTaken +"-"+ semesterTaken)) {
				++semesternum;
				semestersByYearAndSemester.put(yearTaken + "-" + semesterTaken, semesternum);
			}
			
		}
		
		
		return semestersByYearAndSemester;
	}
	
	public int getNumCourseInNthSemester(int semester) {
		HashMap<String, Integer> semesters = this.getSemestersByYearAndSemester();
		int semestercount = 0;
		
		for(int key : semesters.values()) {
			if(key == semester) {
				String targetKey = this.getKey(semesters, key);
				
				for(Course courseTaken : coursesTaken) {
					int yearTaken = courseTaken.getYearTaken();
					int semesterTaken = courseTaken.getSemesterCourseTaken();
					String target = String.valueOf(yearTaken) + "-" + String.valueOf(semesterTaken);
					
					if(target.contentEquals(targetKey)) {
						semestercount++;
					}
				}
			}
		}
		
		return semestercount;
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

	public ArrayList<Course> getCoursesTaken(){
		return coursesTaken;
	}
	
	
	public String getStudentId() {
		return studentId;
	}
	
}
