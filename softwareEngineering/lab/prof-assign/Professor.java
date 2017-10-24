/**
 * Class Professor
 * @author Steve Kunimura, Kyle Hartshorn
 * @version 0.1 July 23, 2008
 */

import java.util.*;

public class Professor implements Comparable<Professor>{
	
	private String name;
	private int numAssigns;
	private Vector<Attribute> attributes;
	
	public Professor(String name) {
		this.name = name;
		this.attributes = new Vector<Attribute>();
		this.numAssigns = 0;
	}
	
	public Professor(String name, Vector<Attribute> attributes) {
		this.name = name;
		this.attributes = attributes;
		this.numAssigns = 0;
	}
	
	public String getName() {
		return name;
	}
	
	public Attribute getAttribute(int index) {
		if(index < attributes.size() && index >= 0)
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
	
	public void addAttribute(Attribute a) { 
		if(a != null) 
			attributes.add(a);
	}
	
	public void deleteAttribute(String attrName) {
		Iterator<Attribute> i = attributes.iterator();
		while(i.hasNext()) {
			if(i.next().getName().equals(attrName)) {
				i.remove();
			}
		}
	}
	
	public int getAssigns()
	{
		return this.numAssigns;
	}
	
	public void incrementAssigns()
	{
		this.numAssigns++;
	}
	
	public void decrementAssigns()
	{
		this.numAssigns--;
		
		if( numAssigns <= 0)
		{
			numAssigns = 0;
		}
	}
	
	@Override
	public boolean equals(Object that){
	     if (! this.getClass().isInstance(that))
	    	 return false;
	     return name.equals(((Professor)that).getName());
	}
	
	@Override
	public int hashCode(){ return name.hashCode(); }
	
	public int compareTo(Professor that){
        return name.compareToIgnoreCase(that.getName());
	}
	
	@Override
	public String toString() { return name;}
	
}