/**
 * Class Course
 * @author Steve Cottier, Kyle Hartshorn
 * @version August 18, 2008
 */

import java.util.*;
import java.util.Vector;

public class Course implements Comparable<Course>{

    private String name;
    private Vector<Attribute> attributes;

    public Course(String name, Vector<Attribute> attributes) {
        this.name = name;
        this.attributes = attributes;
    }

    public Course(String name) {
        this.name = name;
        this.attributes = new Vector<Attribute>();
    }

    public String getName() { return name; }

    public Attribute getAttribute(int index) {
    	if(index < attributes.size())
    		return attributes.get(index);
    	return null;
    }

    public Attribute getAttribute(String name) {
    	Iterator<Attribute> i = attributes.iterator();
    	Attribute a;
    	while(i.hasNext()) {
    		a = i.next();
    		if(a.getName().equals(name))
    			return a;
    	}
    	return null;
    }
    
    public Vector<Attribute> getAttributes() { return new Vector<Attribute>(attributes); }
    
    public void addAttribute(Attribute a) {
        if(a != null)
        	attributes.add(a);
    }

    public void deleteAttribute(String attrName) {
    	Iterator<Attribute> i = attributes.iterator();
    	while(i.hasNext()) {
    		if(i.next().getName().equals(attrName)) {
    			i.remove();
    			break;
    		}
    	}
    }
    
	public int compareTo(Course that){
		return name.compareToIgnoreCase(that.getName());
	}
	
	@Override
	public int hashCode(){ return name.hashCode(); }
	
	@Override
	public boolean equals(Object that){
		if (! this.getClass().isInstance(that)){
		    return false;
		}
        return name.equals(((Course)that).getName());
	}
	
	@Override
    public String toString() { return name; }
}