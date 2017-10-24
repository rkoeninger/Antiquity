Prof-assign is a Java based desktop application for organizing the teaching schedules
of instructors and courses.

Version 1.0 - August 2008

Developers:
    Avinash Athi
    Steve Cottier
    Kyle Hartshorn
    Robert Koeninger
    Steven Kunimura

This software has not been released on any license. It was developed as an
undergraduate-level project by students at the University of Cincinnati.

-== Compliation Notes ==-

The windows and linux versions compile and run under JDK1.6, the mac version requires
JDK1.5

-== Installation Notes ==-

You will not need to install a Java Runtime Environment, one is bundled with the
application.

For Windows:
Run the setup.exe and select an install directory. A shortcut to run the program will
be added to the start menu.

For Linux:
Extract the tar.gz to the location of your choice. Execute the script "run.sh" to start
the program. The program will not run in a headless environment.

For Mac OSX:
Extract the .dmg file to the location of your choice and run the "run" script to start
the program.

-== User Guide ==-
The Professor's Tab:
	1. Click the tab labeled "Professor".

Add Professor:
	1. Click the "Add Professor" button.
	2. The "Add Professor" dialogue will display.
	2. Type the professor's name in the "Professor Name" text field.
	3. Click the "Add" button.
	4. The "Professors" list will update with the recently added professor.

Delete Professor:
	1. select a professor in the "Professors" list to delete.
	2. Click the "Delete Professor" button.
	3. The "Delete Professor" prompt will display.
	4. Click the "Yes" button.
	5. The "Professors" list will update without the recently deleted professor.

Add Professor Attribute:
	1. Click the "Add Attribute" button.
	2. The "Add Professor Attribute" dialogue will display.
	3. Type the attribute's name in the "Name" text field.
	4. Type the attribute's default value in the "Value" text field.
	5. Select the attribute type from the "Type" combobox.
	6. Click the "Add" button.'
	7. Dismiss the message box.
	8. The "Professor Attributes" pane will be updated with the new attribute.

Delete Professor Attribute:
	1. Click the "Delete Attribute" button.
	2. The "Delete Professor Attribute" dialogue will display.
	3. Select an attribute from the "Attribute" combobox.
	4. Click the "Delete Button"
	5. Dismiss the message box.
	6. The "Professor Attributes" pane will be updated without the deleted attribute.

Update Professor Attributes:
	1. Select a professor from the "Professors" list.
	2. Change attributes in the attribute text fields in the
	"Professor Attributes" pane.
	3. Click the "Update Attributes" button.
	4. Dismiss the message box.
	5. The selected professors attributes are updated with changes.


Edit Professor Attribute Default:
	1. Click the "Edit Attribute Default" button.
	2. Select an attribute from the "Name" combobox.
	3. Enter a new default value for the attribute.
	4. Click the "Edit" button.
	5. Dismiss the message box.
	6. When a professor is added they will get the new default value for the attribute.

Create Professor Reports:
	1. Click the "Professor Reports" button.
	2. Dismiss the message box.
	3. The Professor report "profReport.txt" will be generated.
	4. The reports can be found in the install folder at
	"..\profassign\profassign\distro".

The Course Tab:
	1. Click the "Course" tab.

Add Course:
	1. Click the "Add Course" button.
	2. Type a name in the "Name" text field.
	3. Type a course number in the "Number" text field.
	4. Type a quarter in the "Quarter" text field.
	5. Click the "Add" button.
	6. The "Courses" list will update with the newly added course.

Delete Course
	1. Select a course in the "Courses" list to delete.
	2. Click the "Delete Course" button.
	3. The "Delete Course" prompt will display.
	4. Click the "Yes" button.
	5. The "Courses" list will update without the recently deleted course.


Add Course Attribute:
	1. Click the "Add Attribute" button.
	2. The "Add Course Attribute" dialogue will display.
	3. Type the attribute's name in the "Name" text field.
	4. Type the attribute's default value in the "Value" text field.
	5. Select the attribute type from the "Type" combobox.
	6. Click the "Add" button.'
	7. Dismiss the message box.
	8. The "Course Attributes" pane will be updated with the new attribute.

Delete Course Attribute:
	1. Click the "Delete Attribute" button.
	2. The "Delete Course Attribute" dialogue will display.
	3. Select an attribute from the "Attribute" combobox.
	4. Click the "Delete Button"
	5. Dismiss the message box.
	6. The "Course Attributes" pane will be updated without the deleted attribute.

Update Course Attributes:
	1. Select a course from the "Courses" list.
	2. Change attributes in the attribute text fields in the "Course Attributes" pane.
	3. Click the "Update Attributes" button.
	4. Dismiss the message box.
	5. The selected Courses attributes are updated with changes.


Edit Courses Attribute Default:
	1. Click the "Edit Attribute Default" button.
	2. Select an attribute from the "Name" combobox.
	3. Enter a new default value for the attribute.
	4. Click the "Edit" button.
	5. Dismiss the message box.
	6. When a Course is added it will get the new default value for the attribute.

Create Course Reports:
	1. Click the "Course Reports" button.
	2. Dismiss the message box.
	3. The Course report "courseReport.txt" will be generated.
	4. The report can be found in the install folder at
	"..\profassign\profassign\distro"

The Assignment Tab:
	1. Click the "Assignment" tab.	

Add Assignment:
	1. Click the "Add Assignment" button.
	2. Select a course from the "Course" combobox.
	3. Select a professor from the "Professor" combobox.
	4. Click "Ok" button.
	5. The assignment will be added to the "Assignments" list.

Delete Assignement:
	1. Select an assignment from the "Assignments" list to delete.
	2. Click the "Delete Assignment" button.
	3. The "Delete Assignment" dialogue will display.
	4. Click the "Yes" button.
	5. The "Assignements" list will update without the deleted assignment.

Create Assignement Reports:
	1. Click the "Assignment Reports" button.
	2. Dismiss the message box.
	3. The Assignement report "assignementReport.txt" will be generated.
	4. The report can be found in the install folder at
	"..\profassign\profassign\distro"

Auto Assign:
	1. Click the "Auto Assign" button.
	2. Dismiss the message box.
	3. The "Assignments" list will be updated with auto generated assignments
	from the objective function.

-== Configuration File Format ==-

The config file for the Professor Assigner has three main components: an objective
function equation, a set of variables for use in the objective function, and a maximum
number of teaching points that a professor can accumulate.  The style guidelines must be
followed precisely (i.e. proper whitespace, formatting, etc.) so that the program can
parse it correctly.  Any problem with the parsing may lead to errors, or even the loss of
the contents of the config file.  

-------------------------------------------------------------------------------

The objective function equation, prefixed by the designator “eq” and a comma, is used by
the program to make assignments automatically.  The equation can use integers, floating
point numbers, and variables.  Valid operations include addition, subtraction,
multiplication, and division.  Parentheses are also supported.  However, note that
each “token” of the equation (i.e. operator, variable, or number) must be separated
by whitespace in order to be parsed correctly.  The only exception to this is negative
numbers or variables, where the minus sign must be adjacent to the modified expression.  

Examples:

Valid: eq,x + 10 * y – 1 / z
Invalid: eq,x+10*y-1/z

Valid: eq,x + -y
Valid: eq,x – y
Invalid: eq,x + - y
Invalid: eq,x-y

-------------------------------------------------------------------------------

The variables set is a series of lines delimited by “var” and a comma, each with the
format:

var,<variable name>,<attribute type>,<attribute name>

The variable name must be one character long (e.g. x, y, z, etc.), and it must be unique.  

The attribute type tells whether the attribute belongs to a course or professor.
The only two possible values are PROFESSOR_ATTRIBUTE or COURSE_ATTRIBUTE.  

The attribute name is the name of the attribute in the course or professor that the
variable represents.  For example, “Teaching Points” would be a valid attribute for
both courses and professors.  Spaces are allowed in the name, but do be sure that the
name in the config file is the same as the name of the attribute.  Also, though both
dynamic and permanent attributes can be used in the equation, if a dynamic attribute
that is being used in the equation is deleted, errors will likely result.  

Example:

var,x,COURSE_ATTRIBUTE,Teaching Points

-------------------------------------------------------------------------------

The maximum number of teaching points is delimited by “max” and a comma.  The maximum
number of teaching points is used by the program’s automatic assigner to determine
whether a course should be assigned to a professor.  Basically, if an assignment would
set the professor over the max allowable teaching points, the assignment cannot be made
automatically.  

The max value can be any floating point number, though it is recommended to be greater
than zero.  

Example:

max,20.0
