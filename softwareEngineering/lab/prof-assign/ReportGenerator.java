/**
 * @author Kyle Hartshorn
 * @file ReportGenerator.java
 * 
 * Created: 7/11/08
 * Updated: 7/12/08
 * 
 * Note: Reporting functionality
 */

import java.io.*;
import java.util.*;
import java.text.*;

public class ReportGenerator 
{
	public ReportGenerator(Database db) { data = db; }
	
	private Database data;	//Reference to central database
	
	private String spacer(int a, int b) {
		//Adds spaces for proper spacing between columns
		//a is the desired distance
		//b is the length that's already taken up
		a -= b;
		String spaces = "";
		for(int i = 0; i < a; i++) 
			spaces += " ";
		return spaces;
	}
	
	public void outputCourseReport()
	{
		this.outputCourseReportFile("courseReport.txt");
		/*//Outputs a course report to standard output 
		int nCourses = data.getNumberCourses();	//Number of courses
		
		//Write header
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		Date date = new Date();
		System.out.println("Course Listing (" + dateFormat.format(date) + 
				")\n-----------------------------------");
		
		if (nCourses <= 0)
			System.out.println("No courses listed\n-----------------------------------");
		else
		{
			int i,j;
			Course c;  Attribute a;
			
			System.out.println(nCourses + " courses listed\n-----------------------------------");
			
			//Output column headings
			int nAttributes = data.getNumberCourseAttributes();
			System.out.print("Course Name" + spacer(35,11));
			for(i = 0; i < nAttributes; i++) {
				a = data.getCourseAttributeByIndex(i);
				System.out.print(a.getName() + spacer(10,a.getName().length()));
			}
			
			System.out.println("\n-----------");
			Vector<Course> courses = data.getCourses();
			Collections.sort(courses);
			Iterator<Course> cIt = courses.iterator();
			while(cIt.hasNext()) {
				c = cIt.next();
				System.out.print(c.getName() + spacer(35,c.getName().length()));
				for(j = 0; j < nAttributes; j++) {
					a = c.getAttribute(j);
					System.out.print(a.getValue() + spacer(10,a.getValue().toString().length()));
				}
				System.out.print("\n");
			}
		}//*/
	}
	
	public void outputCourseReportFile(String filename) {
		//Outputs a course report to a file
		
		try {
			FileOutputStream out = new FileOutputStream(filename);
			PrintStream p = new PrintStream(out);
			
			int nCourses = data.getNumberCourses();	//Number of courses
			
			//Write header
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
			Date date = new Date();
			p.println("Course Report (" + dateFormat.format(date) + 
					")\r\n-----------------------------------");
			
			if (nCourses <= 0)
				p.println("No courses listed\r\n-----------------------------------");
			else
			{
				int i;
				String temp;
				p.println(nCourses + " courses listed\r\n-----------------------------------");
				
				int nAttributes = data.getNumberCourseAttributes();
				//Output column headings
				p.print("Course Name" + spacer(20, "Course Name".length()));
				for(i = 0; i < nAttributes; i++) {
					temp = data.getCourseAttributeByIndex(i).getName();
					p.print(temp + spacer(20,temp.length()));
				}
				p.println("\r\n-----------------------------------");
				
				Vector<Course> courses = data.getCourses();
				Collections.sort(courses);
				
				Iterator<Course> cIt = courses.iterator();
				Course c;
				while(cIt.hasNext()) {
					c = cIt.next();
					p.print(c.getName() + spacer(20,c.getName().length()));
					//Print attributes
					for(i = 0; i < nAttributes; i++) {
						temp = c.getAttribute(i).getValue();
						p.print(temp + spacer(20,temp.length()));
					}
					p.print("\r\n");
				}
			}
			
			p.close();
		}
		catch(Exception e) {
			System.err.println("Error: Course report could not be written:\n" + e.getMessage());
		}
	}

	public void outputProfessorReport() {
		this.outputProfessorReportFile("profReport.txt");
		/*//Output professor report to standard output 
		int nProfs = data.getNumberProfessors();	//Number of professors
		
		//Write header
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		Date date = new Date();
		System.out.print("Professor Report (" + dateFormat.format(date) + 
				")\n--------------------------------------\n");
		
		if(nProfs <= 0)
			System.out.print("No professors listed\n--------------------------------------\n");
		else
		{
			int i = 0;
			System.out.print(nProfs + " professors listed\n--------------------------------------\n");
			
			int nAttributes;
			//Output column headings
			
			Iterator<Professor> pIt = data.getProfessorIterator();
			Professor p;
			while(pIt.hasNext()) {
				p = pIt.next();
				System.out.print((i+1) + ": " + p.getName() + "\n");
			}
		}*/
	}
	
	public void outputProfessorReportFile(String filename) 
	{ 
		FileOutputStream out;
		PrintStream p;
		
		try {
			out = new FileOutputStream(filename);
			p = new PrintStream(out);
		
			int nProfs = data.getNumberProfessors();	//Number of courses
			
			//Write header
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
			Date date = new Date();
			p.println("Professor Report (" + dateFormat.format(date) + 
					")\r\n--------------------------------------");
			
			if(nProfs <= 0)
				p.println("No professors listed\r\n--------------------------------------");
			else
			{
				int i;
				String temp;
				p.println(nProfs + " professors listed\r\n--------------------------------------");
				
				int nAttributes = data.getNumberProfessorAttributes();
				//Output column headings
				p.print("Professor Name" + spacer(20, "Professor Name".length()));
				for(i = 0; i < nAttributes; i++) {
					temp = data.getProfessorAttributeByIndex(i).getName();
					p.print(temp + spacer(20,temp.length()));
				}
				p.println("\r\n-----------------------------------");
				
				Vector<Professor> profs = data.getProfessors();
				Collections.sort(profs);
				Iterator<Professor> pIt = profs.iterator();
				Professor prof;
				while(pIt.hasNext()) {
					prof = pIt.next();
					p.print(prof.getName() + spacer(20, prof.getName().length()));
					//Output attributes
					for(i = 0; i < nAttributes; i++) {
						temp = prof.getAttribute(i).getValue();
						p.print(temp + spacer(20,temp.length()));
					}
					p.print("\r\n");
				}
			}
			p.close();
		}
		catch (Exception e) {
			System.err.println("Professor report could not be written");
		}
	}
	
	public void outputAssignmentReport() {
		this.outputAssignmentReportFile("assignmentReport.txt");
		/*//Output assignment report to standard output
		
		int nAssignments = data.getNumberAssignments();	//Number of assignments
		
		//Write header
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		Date date = new Date();
		System.out.print("Assignment Listing (" + dateFormat.format(date) + 
				")\n--------------------------------------\n");
		
		if(nAssignments <= 0)
			System.out.print("No assignments listed\n--------------------------------------\n");
		else
		{
			System.out.print(nAssignments + 
					" assignments listed\n--------------------------------------\n");
			
			for(int i = 0; i < nAssignments; i++)
				System.out.print((i+1) + ": " + data.getAssignment(i).toString() + "\n");
		}*/
	}
	
	public void outputAssignmentReportFile(String filename) {
		//Output assignment report to file
		try {
			FileOutputStream out = new FileOutputStream(filename);
			PrintStream p = new PrintStream(out);
			
			int nAssignments = data.getNumberAssignments();	//Number of Assignments
			
			//Write header
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
			Date date = new Date();
			p.println("Assignment Report (" + dateFormat.format(date) + 
					")\r\n--------------------------------------");
			
			if(nAssignments <= 0)
				p.println("No assignments listed\r\n--------------------------------------");
			else
			{
				p.println(nAssignments + " assignments listed\r\n--------------------------------------");
				
				Vector<Assignment> assignments = data.getAssignments();
				Collections.sort(assignments);	//Sort the assignments
				Iterator<Assignment> i = assignments.iterator();
				
				while(i.hasNext()) {
					p.println(i.next().toString());
				}
			}
			p.close();
		}
		catch(Exception e) {
			System.err.println("Error: Assignment report could not be written:\n" + 
					e.getMessage());
		}
	}
}
