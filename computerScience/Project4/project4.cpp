/*
 * Robert Koeninger
 * 20ECES121-001
 * Project 4
 * 17/01/06
 *
 * Calculates the average grade for 3 exams, midterm, and final. Gets this
 * information from the user. Display the average and corresponding letter
 * grade. Prompts the user if he/she would like to calculate the average for
 * another student. Quits when the user requests program termination.
 */

#include <iostream>  // Include for cout and cin
#include <conio.h>   // Include for getch()

using namespace std;

// Displays a grade-input prompt using the given exam name
// Returns the value inputted by the user
// Once this function is entered, it will not return until the user has
// entered a valid grade
double consoleReadGrade(char*);

// Accepts a grade as a percentage and returns the letter grade for that grade
char getLetterGrade(double);

// Adds all of the grades in the double array and divides them by the count
// of grades. The second argument is the count of grades.
double calculateAverage(double*, int);

int main()
{

    // Declare exam name and grade arrays
    const int examCount = 5;
    char* examNames[] = {"Exam 1", "Exam 2",
    "Exam 3", "Midterm Exam", "Final Exam"};
    double grades[examCount];

    // Program description for user
    cout << "Enter the percentage grades for each of a students exams,\n";
    cout << "the average will be displayed\n\n";

    // Main program loop; runs as long as the user presses 'y' after each
    // set of grades
    for (;;)
    {

        // Prompt for input of each exam using that exam's name
        for (int exam = 0; exam < examCount; ++exam)
            grades[exam] = consoleReadGrade(examNames[exam]);

        // Calculate the average of all exams
        double average = calculateAverage(grades, examCount);

        // Display results and prompt for another set of grades
        cout << "Average: " << average << " " << getLetterGrade(average) << "\n\n";
        cout << "Press the \'y\' key to enter another student's grades,\n";
        cout << "or any other key to exit";

        // If the user presses any key except 'y', then program terminates
        if (getch() != 'y')
            return 0;
        
        system("cls"); // clear the screen for another input
        
    }

}

double consoleReadGrade(char* examName)
{

    double input = -1.0;
    
    for(;;)
    {

        cout << "Grade for " << examName << ": ";
        cin >> input;

        // Only accepts values 0 <= input <= 100
        if ((input >= 0.0) && (input <= 100.0))
            return input;
            
        cout << "\nError: Invalid grade value, please enter again.\n";

    }

}

char getLetterGrade(double grade)
{

    // Anything less than 0 still gets an 'F'
    // Anything above a 100 gets an 'A'
    if (grade < 60.0)
        return 'F';
    else if (grade < 70.0)
        return 'D';
    else if (grade < 80.0)
        return 'C';
    else if (grade < 90.0)
        return 'B';
    else
        return 'A';

}

double calculateAverage(double* grades, int examCount)
{

    double total = 0.0;

    // Sum grades
    for (int exam = 0; exam < examCount; ++exam)
        total += grades[exam];

    // Find and return average
    return total / examCount;

}
