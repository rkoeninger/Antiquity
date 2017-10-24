/*
 * Robert Koeninger
 * 20ECES121-001
 * Project 7
 * 24/01/06
 *
 * Calculates the average grade for 3 exams, midterm, and final. Get this
 * information from a file named "scores.txt". Displays the average and
 * corresponding letter grade.
 */
 
#include <iostream>  // Include for cout and cin
#include <fstream>   // Include for ifstream and ofstream
#include <conio.h>   // Include for getch()

using namespace std;

// The number of tests taken by each student
#define EXAM_COUNT 3

// Accepts a grade as a percentage and returns the letter grade for that grade
char getLetterGrade(double);

int main()
{

    // Open streams to input and output files
    ifstream scoreFile;
    scoreFile.open("scores.txt");
    ofstream resultFile;
    resultFile.open("proj7out.txt");
    
    // Variables for the test sum, average,
    // current grade and id for each student
    int examGrade, studentID;
    double sum, average;

    cout << "Results from input file: scores.txt\n\n";
    resultFile << "Results from input file: scores.txt\n\n";

    // Read each line until the file has reached its end
    while (! scoreFile.fail())
    {

        sum = 0.0;
        scoreFile >> studentID;
        
        // Sum and average every row
        for (int exam = 0; exam < EXAM_COUNT; ++exam)
        {

            scoreFile >> examGrade;
            sum += examGrade;

        }
            
        average = sum / EXAM_COUNT;
        
        // Display student ID and average
        cout << studentID << "\t" << average << "\t";
        cout << getLetterGrade(average) << "\n";
        resultFile << studentID << "\t" << average << "\t";
        resultFile << getLetterGrade(average) << "\n";

    }

    // Close handles to files
    scoreFile.close();
    resultFile.close();

    // Wait for user to exit
    cout << "\n";
    cout << "Press any key to exit";
    getch();
    return 0;

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
