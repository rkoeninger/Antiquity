
/**
 * This class's main function is to provide an intermediate interface
 * between the GUI components and input going to the data model.
 * 
 * The functions take the input provided from the user, check to see
 * if it is appropriate, then add/remove those items, and return
 * true/false to indicate success or failure, which the gui will then
 * respond to (showing an error message on fail, showing an info
 * message for success?)
 * 
 * ?This is a good place for an item's unique key to be generated?
 * 
 * @author koeninrc
 */
import java.util.*;

public class UIValidator {
	
	private Database data;
	
	public UIValidator(Database data){ this.data = data; }
	
	public int makeAutomaticAssignments() {
		int nAssignments = 0;
		ObjectiveFunction func = Engine.getObjectiveFunction();
		Iterator<Course> cIt = data.getCourses().iterator();
		Course c;
		Professor p;
		Assignment a;
		Iterator<Professor> pIt;
		while(cIt.hasNext()) {
			c = cIt.next();
			if(!data.containsCourseAssignment(c)) {		//If course has no assignment
				pIt = data.getProfessors().iterator();
				p = func.bestProfessor(c, pIt);
				if(p != null && c != null) {
					a = new Assignment(p,c);
					p.incrementAssigns();
					data.addAssignment(a);
					nAssignments++;
				}
			}
		}
		return nAssignments;
	}
	
	public boolean addProfessor(String profName){
		Professor newProf = new Professor(profName);
		if (data.getProfessors().contains(newProf))
			return false;
		data.addProfessor(new Professor(profName,data.professorAttributesClone()));
		return true;
	}
	
	public boolean addCourse(String courseName){
		Course newCourse = new Course(courseName);
		if (data.getCourses().contains(newCourse))	//Check if course already exists
			return false;
		data.addCourse(new Course(courseName,data.courseAttributesClone()));
		return true;
	}
	
	public boolean removeProfessor(String profName){
		if (!data.containsProfessor(profName))
			return false;
		data.deleteProfessor(new Professor(profName).hashCode());
		return true;
	}
	
	public boolean removeCourse(String courseName){
		if (! data.containsCourse(courseName))
			return false;
		data.deleteCourse(new Course(courseName).hashCode());
		
		//Delete any assignments for this course
		if(data.containsCourseAssignment(courseName))
			data.deleteAssignment(courseName);
		
		return true;
	}
	
	public boolean editCourseAttribute(String courseName,String attrName, String value){
		Course c = data.getCourse(courseName.hashCode());
		if(c == null)
			return false;
		Attribute a = c.getAttribute(attrName);
		if(a == null)
			return false;
		a.setValue(value);
		return true;
	}
	
	public boolean addCourseAttribute(String attrName, String type, String defaultValue) {
		if(data.containsCourseAttribute(attrName))
			return false;
		Attribute a = new Attribute(0,attrName,Attribute.AttributeType.valueOf(type.toUpperCase()),defaultValue);
		data.addCourseAttribute(a);
		return true;
	}
	
	public boolean editCourseAttributeProperties(String attrName, String newDefaultValue) {
		if(!data.containsCourseAttribute(attrName)) 
			return false;
		
		Attribute a = data.getCourseAttribute(attrName);
		a.setDefaultValue(newDefaultValue);
		a.setValue(newDefaultValue);
		Iterator<Course> i = data.getCourses().iterator();
		Course c;
		while(i.hasNext()) {
			c = i.next();
			c.getAttribute(attrName).setDefaultValue(newDefaultValue);
		}
		return true;
	}
	
	public boolean removeCourseAttribute(String attrName) {
		if(!data.containsCourseAttribute(attrName))
			return false;
		data.deleteCourseAttribute(attrName);
		return true;
	}
	
	public boolean editProfessorAttribute(String profName,
	String attrName, String value){
		Professor p = data.getProfessor(profName.hashCode());
		if(p == null)
			return false;
		Attribute a = p.getAttribute(attrName);
		if(a == null)
			return false;
		a.setValue(value);
		return true;
	}
	
	public boolean addProfessorAttribute(String attrName, String type, String defaultValue) {
		if(data.containsProfessorAttribute(attrName))
			return false;
		Attribute a = new Attribute(0,attrName,Attribute.AttributeType.valueOf(type.toUpperCase()),defaultValue);
		data.addProfessorAttribute(a);
		return true;
	}
	
	public boolean editProfessorAttributeProperties(String attrName, String newDefaultValue) {
		if(!data.containsProfessorAttribute(attrName))
			return false;
		Attribute a = data.getProfessorAttribute(attrName);
		a.setDefaultValue(newDefaultValue);
		a.setValue(newDefaultValue);
		Iterator<Professor> i = data.getProfessors().iterator();
		Professor p;
		while(i.hasNext()) {
			p = i.next();
			p.getAttribute(attrName).setDefaultValue(newDefaultValue);
		}
		return true; 
	}
	
	public boolean removeProfessorAttribute(String attrName) {
		if(!data.containsProfessorAttribute(attrName))
			return false;
		data.deleteProfessorAttribute(attrName);
		return true;
	}
	
	public boolean addAssignment(String profName, String courseName) {
		Professor p = data.getProfessor(profName.hashCode());
		Course c = data.getCourse(courseName.hashCode());
		if(p == null || c == null || data.containsCourseAssignment(c)) 
			return false;
		Assignment a = new Assignment(p,c);
		p.incrementAssigns();
		data.addAssignment(a);
		return true;
	}
	
	public boolean removeAssignment(String courseName) {
		if(!data.containsCourseAssignment(courseName))
			return false;
		else
		{
			Iterator<Assignment> i = data.getAssignmentIterator();
			Assignment p;
			
			while(i.hasNext()) {
				p = i.next();
				if ( p.getCourse().getName().equals(courseName) )
				{
					p.getProfessor().decrementAssigns();
				}
			}
			
			data.deleteAssignment(courseName);
			
			return true;
		}
		
	}
}
