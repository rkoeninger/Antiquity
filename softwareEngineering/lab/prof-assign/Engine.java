/**
 * @author Kyle Hartshorn, Rob Koeninger
 * @file Engine.java
 * 
 * Created: 7/12/08
 * Updated: 7/16/08
 * 
 * Notes: 
 */



import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;

public class Engine {

	private static Database database;
	private static ObjectiveFunction func;
	
	public static Database getDatabase() {
		if(database == null)
			database = new Database();
		return database;
	}
	public static ObjectiveFunction getObjectiveFunction() {
		if(func == null)
			func = new ObjectiveFunction();
		return func;
	}
	
	public static void save() {
		getDatabase().save();
		getObjectiveFunction().save("config.txt");
	}
	
	public static void main(String [] args) {
		//Create and initialize database
		Database data = getDatabase();
		data.load();
		ObjectiveFunction objFunc = getObjectiveFunction();
		objFunc.load("config.txt");
		objFunc.initialize();

		/*Course c = data.getCourse("TESTCOURSE".hashCode());
		Professor p = data.getProfessor("TESTPROF".hashCode());
		
		System.out.println("Val: " + objFunc.value(c,p));*/
		mainFrame mf = new mainFrame();
		
		WindowAdapter adapter = new WindowAdapter()	{
			public void windowClosing(WindowEvent e) { 
				save();	//Save the database to file when the window closes
			}
		}; 
		
		mf.addWindowListener(adapter);
		mf.setVisible(true);//*/
		//save();
	}


}
