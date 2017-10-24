/**
 * @author Kyle Hartshorn
 * @file Database.java
 * 
 * Created: 7/11/08
 * Updated: 7/25/08
 * 
 * Notes:
 */

import java.util.*;
import java.io.*;

public class Database 
{
	private Vector<Assignment> assignments;
	
	private Hashtable<Integer,Course> courseTable; 
	private Hashtable<Integer,Professor> profTable;
	private Hashtable<Integer,Assignment> assignmentTable;
	
	private Vector<Attribute> courseAttributes;
	private Vector<Attribute> professorAttributes;
	
	public Database() {
		assignments = new Vector<Assignment>();
		courseAttributes = new Vector<Attribute>();
		professorAttributes = new Vector<Attribute>();
		
		profTable = new Hashtable<Integer,Professor>();
		courseTable = new Hashtable<Integer,Course>();
		assignmentTable = new Hashtable<Integer,Assignment>();
	}
	
	public void load() {
		loadAttributes("attributes.txt");
		loadCourses("courses.txt");
		loadProfessors("profs.txt");
		loadAssignments("assignments.txt");
	}
	
	public void save() {
		saveAttributes("attributes.txt");
		saveCourses("courses.txt");
		saveProfessors("profs.txt");
		saveAssignments("assignments.txt");
	}
	
	public Vector<Course> getCourses() { return new Vector<Course>(courseTable.values()); }
	public Vector<Professor> getProfessors() { return new Vector<Professor>(profTable.values()); }
	public Vector<Assignment> getAssignments() { return new Vector<Assignment>(assignments); }
	public Vector<Assignment> getAssignments2() { 
		return new Vector<Assignment>(assignmentTable.values());
	}
	public Vector<Attribute> getCourseAttributes() { return new Vector<Attribute>(courseAttributes); }
	public Vector<Attribute> getProfessorAttributes() { return new Vector<Attribute>(professorAttributes); }
	
	public void addCourseAttribute(Attribute a) {
		if(a != null) {
			courseAttributes.add(a);
			Iterator<Course> i = courseTable.values().iterator();
			while(i.hasNext()) {
				i.next().addAttribute(a.clone());
			}
		}
	}
	
	public Attribute getCourseAttribute(String name) {
		Iterator<Attribute> i = courseAttributes.iterator();
		Attribute a;
		while(i.hasNext()) {
			a = i.next();
			if(a.getName().equals(name))
				return a;
		}
		return null;
	}
	
	public void deleteCourseAttribute(String name) {
		Iterator<Attribute> aIt = courseAttributes.iterator();
		while(aIt.hasNext()) {
			if(aIt.next().getName().equals(name)) {
				aIt.remove();
				break;
			}
		}
		Iterator<Course> cIt = courseTable.values().iterator();
		while(cIt.hasNext()) {		//Delete the attribute from each professor
			cIt.next().deleteAttribute(name);
		}
	}
	
	public void addProfessorAttribute(Attribute a) {
		if(a != null) {
			professorAttributes.add(a);
			Iterator<Professor> i = profTable.values().iterator();
			while(i.hasNext()) {
				i.next().addAttribute(a.clone());
			}
		}
	}
	
	public Attribute getProfessorAttribute(String name) {
		Iterator<Attribute> i = professorAttributes.iterator();
		Attribute a;
		while(i.hasNext()) {
			a = i.next();
			if(a.getName().equals(name))
				return a;
		}
		return null;
	}
	
	public void deleteProfessorAttribute(String name) {
		Iterator<Attribute> aIt = professorAttributes.iterator();
		while(aIt.hasNext()) {
			if(aIt.next().getName().equals(name)) {
				aIt.remove();
				break;
			}
		}
		
		Iterator<Professor> pIt = profTable.values().iterator();
		while(pIt.hasNext()) {		//Delete the attribute from each professor
			pIt.next().deleteAttribute(name);
		}
	}
	
	public Vector<Attribute> courseAttributesClone() { 
		Vector<Attribute> va = new Vector<Attribute>();
		Iterator<Attribute> i = courseAttributes.iterator();
		while(i.hasNext()) {
			va.add(i.next().clone());
		}
		return va;
	}
	
	public Vector<Attribute> professorAttributesClone() { 
		Vector<Attribute> va = new Vector<Attribute>();
		Iterator<Attribute> i = professorAttributes.iterator();
		while(i.hasNext()) {
			va.add(i.next().clone());
		}
		return va;
	}
	
	public void loadAttributes(String filename) {
		
		StringTokenizer st;
		String line;
		Attribute a;
		
		try {
			FileInputStream fstream = new FileInputStream(filename);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			    
			while ((line = br.readLine()) != null)   {
				st = new StringTokenizer(line,",");
				line = st.nextToken();
				
				//<designator>,<key>,<name>,<type>,<default_value>
				if(line.equals("ca"))	{	//Course attribute	
					a = new Attribute(Integer.valueOf(st.nextToken()),st.nextToken(),
							Attribute.AttributeType.valueOf(st.nextToken()),
							st.nextToken());
					courseAttributes.add(a);
				}
				else if(line.equals("pa")) {	//Professor attribute
					a = new Attribute(Integer.valueOf(st.nextToken()),st.nextToken(),
							Attribute.AttributeType.valueOf(st.nextToken()),
							st.nextToken());
					professorAttributes.add(a);
				}
			}

			in.close();
		}
		catch(Exception e) {
			System.err.println("Error: Attributes could not be loaded to database:\n" + e);
		}
		
	}
	
	public void saveAttributes(String filename) {
		try{ 
			FileWriter fstream = new FileWriter(filename);
        	BufferedWriter out = new BufferedWriter(fstream);
        	
        	Attribute a;
        	Iterator<Attribute> i = courseAttributes.iterator();
        	
        	//Write course attributes to file
        	while(i.hasNext()) {
        		a = i.next();
        		out.write("ca," + a.getKey() + "," + a.getName() + "," +
        				a.getType() + "," + a.getDefaultValue() + "\r\n");
        	}
        	
        	//Write professor attributes to file
        	i = professorAttributes.iterator();
        	while(i.hasNext()) {
        		a = i.next();
        		out.write("pa," + a.getKey() + "," + a.getName() + "," +
        				a.getType() + "," + a.getDefaultValue() + "\r\n");
        	}
        	
        	out.close();
		}
		catch (Exception e) {
			System.err.println("Error: Attributes could not be written to file:\n" + e.getMessage());
		}
	}
	
	public boolean containsCourseAttribute(String name) {
		Iterator<Attribute> i = courseAttributes.iterator();
		while(i.hasNext()) {
			if(i.next().getName().equals(name))
				return true;
		}
		return false;
	}
	
	public boolean containsProfessorAttribute(String name) {
		Iterator<Attribute> i = professorAttributes.iterator();
		while(i.hasNext()) {
			if(i.next().getName().equals(name))
				return true;
		}
		return false;
	}
	
	public Attribute getProfessorAttributeByIndex(int index) { return professorAttributes.get(index);}
	public Attribute getCourseAttributeByIndex(int index) { return courseAttributes.get(index); }
	public int getNumberCourseAttributes() { return courseAttributes.size(); }
	public int getNumberProfessorAttributes() { return professorAttributes.size(); }
	
	public void addCourse(Course c) {
		if(c != null)
			courseTable.put(c.hashCode(),c);
	}

	public boolean containsCourse(String name) { 
		return courseTable.containsKey(name.hashCode());
	}
	public Course getCourse(int key) { return courseTable.get(key); }
	public int getNumberCourses() { return courseTable.size(); }
	public void deleteCourse(int key) { courseTable.remove(key); }
	public Iterator<Course> getCourseIterator() { return courseTable.values().iterator(); }
	
	private void loadCourses(String filename) { 

		Course c;
		String line;
		StringTokenizer st;
		
		try {
			FileInputStream fstream = new FileInputStream(filename);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			    
			int i, nAttributes = courseAttributes.size();
			
			while ((line = br.readLine()) != null)   {
				st = new StringTokenizer(line,",");

				if(st.nextToken().equals("c"))
				{		
					c = new Course(st.nextToken(), 
							this.courseAttributesClone());
					
					i = 0;
					while(st.hasMoreTokens() && i < nAttributes) {
						c.getAttribute(i).setValue(st.nextToken());
						i++;
					}

					this.addCourse(c);
				}
			}

			in.close();
		}
		catch(Exception e) {
			System.err.println("Error: Course data could not be loaded to database:\n" + e);
		}
	}
	
	private void saveCourses(String filename) {
		
		try{ 
			FileWriter fstream = new FileWriter(filename);
        	BufferedWriter out = new BufferedWriter(fstream);
        	
        	int j, nAttributes = this.getNumberCourseAttributes();
        	Iterator<Course> i = courseTable.values().iterator();
        	//Iterator<Attribute> atIt;
        	Course c;
        	
        	while(i.hasNext()) {
        		c = i.next();
        		out.write("c," + c.getName());
        		
        		for(j = 0; j < nAttributes; j++) {
        			out.write("," + c.getAttribute(j).getValue().toString());
        		}
        		out.write("\r\n");
        	}
        	
        	out.close();
		}
		catch (Exception e) {
			System.err.println("Error: Course data could not be written to file:\n" + e.getMessage());
		}
    
	}
	
	public void addProfessor(Professor p) { 
		if(p != null)
			profTable.put(p.hashCode(),p);
	}

	public boolean containsProfessor(String name) { 
		return profTable.containsKey(name.hashCode());
	}
	
	public Professor getProfessor(int key)	{ return profTable.get(key); }
	public int getNumberProfessors() { return profTable.size(); }
	public void deleteProfessor(int key) { profTable.remove(key); }
	public Iterator<Professor> getProfessorIterator() { return profTable.values().iterator(); }
	
	private void loadProfessors(String filename) {

		Professor p;
		String line;
		StringTokenizer st;
		
		try {
			FileInputStream fstream = new FileInputStream(filename);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			    
			int i, nAttributes = professorAttributes.size();
			
			while ((line = br.readLine()) != null)   {
				st = new StringTokenizer(line,",");

				if(st.nextToken().equals("p")) {	
					p = new Professor(st.nextToken(),
							this.professorAttributesClone());
					
					i = 0;
					while(st.hasMoreTokens() && i < nAttributes) {
						p.getAttribute(i).setValue(st.nextToken());
						i++;
					}
					
					this.addProfessor(p);
				}
			}

			in.close();
		}
		catch(Exception e) {
			System.err.println("Error: Professor data could not be loaded to database:\n" + e);
		}
	}
	
	private void saveProfessors(String filename) {
		
		try{ 
			FileWriter fstream = new FileWriter(filename);
        	BufferedWriter out = new BufferedWriter(fstream);
        	
        	Iterator<Professor> i = profTable.values().iterator();
        	//Iterator<Attribute> atIt;
        	Professor p;
        	
        	int j, nAttributes = getNumberProfessorAttributes();
        	
        	while(i.hasNext()) {
        		p = i.next();
        		out.write("p," + p.getName());
        		
        		for(j = 0; j < nAttributes; j++) {
        			
            		out.write("," + p.getAttribute(j).getValue().toString());
            	}
            	out.write("\r\n");
        	}
        	
        	out.close();
		}
		catch (Exception e) {
			System.err.println("Error: Professor data could not be written to file:\n" + e.getMessage());
		}
	}
	
	public void addAssignment(Assignment a) { assignments.add(a); }
	public void addAssignment2(Assignment a) {
		if(a != null) {
			assignmentTable.put(a.hashCode(),a);
		}
	}
	
	public boolean containsCourseAssignment(String courseName) {
		Iterator<Assignment> i = assignments.iterator();
		while(i.hasNext()) {
			if(i.next().getCourse().getName().equals(courseName)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean containsCourseAssignment(Course c) {
		Iterator<Assignment> i = assignments.iterator();
		while(i.hasNext()) {
			if(i.next().getCourse().equals(c)) {
				return true;
			}
		}
		return false;
	}
	
	public Assignment getAssignment(int key) { return assignments.get(key); }
	public Assignment getAssignment2(int key) { return assignmentTable.get(key); }
	
	public Iterator<Assignment> getAssignmentIterator() {
		return assignments.iterator();
	}
	
	public int getNumberAssignments() { return assignments.size(); }
	public int getNumberAssignments2() { return assignmentTable.size(); }

 	public void deleteAssignment2(int key) { assignmentTable.remove(key); }
 	
 	public void deleteAssignment(String courseName) {
 		Iterator<Assignment> i = assignments.iterator();
		while(i.hasNext()) {
			if(i.next().getCourse().getName().equals(courseName)) {
				i.remove();
				break;
			}
		}
 	}
 	
	private void loadAssignments(String filename) {
		//File format: a,<assignmentID>,<profID>,<courseID>\n
		
		StringTokenizer st;
		Assignment a; Professor p;  Course c;
		String line;
		int key;
		
		try {
			FileInputStream fstream = new FileInputStream(filename);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			    
			while ((line = br.readLine()) != null)   {
				st = new StringTokenizer(line,",");

				if(st.nextToken().equals("a"))
				{		
					p = this.getProfessor(Integer.valueOf(st.nextToken()));
					c = this.getCourse(Integer.valueOf(st.nextToken()));
					if(p != null && c != null)
					{
						a = new Assignment(p,c);
						addAssignment(a);
					}
					else
						System.err.println("Error: An assignment could not be loaded.");
				}
			}

			in.close();
		}
		catch(Exception e) {
			System.err.println("Error: Assignment data could not be loaded to database:\n" + e);
		}
	}
	
	private void saveAssignments(String filename) {
		//line format: a,<assignmentID>,<profID>,<courseID>\r\n
		
		try{ 
			FileWriter fstream = new FileWriter(filename);
        	BufferedWriter out = new BufferedWriter(fstream);
        	
        	Iterator<Assignment> i = assignments.iterator();
        	Assignment a;
        	
        	while(i.hasNext()) {		//Write all assignments to file
        		a = i.next();
        		out.write("a," + a.getProfessor().hashCode() + "," +
        				a.getCourse().hashCode() + "\r\n");
        	} 
        	
        	out.close();
		}
		catch (Exception e) {
			System.err.println("Error: Course data could not be written to file:\n" + e.getMessage());
		}
	}
}
