#include <iostream>
#include <conio.h>
#include <vector>
using namespace std;
int kaprekarProcess(int);
void sort(vector<int>&, bool);
int main(){/* Lite Version - No vari-size, doc or xtra space (shortest code)*/
    int input;
    while (cin){
        cout << "Enter a positive, 3-digit integer: ";
        cin >> input;
        if (input <= 0){
            cout << "\nGoodbye!";
            getch();
            return 0;
        }
        else if ((input > 998) | (input < 100) | (((input/100)%10) == ((input/10)%10) && ((input/10)%10) == (input%10)))
            cout << "\nERROR: Invalid number entered\n\n";
        else{
            int steps = kaprekarProcess(input);
            if (steps == 0)
                steps = 1;
            cout << "The number " << input << " required " << steps << " steps to reach 495\n\n";
        }
    }
}
int kaprekarProcess(int input){
    if (input == 495)
        return 0;
    vector<int> accendingPlaceValues(3);
    accendingPlaceValues[0]=(input/100)%10;
    accendingPlaceValues[1]=(input/10)%10;
    accendingPlaceValues[2]=input%10;
    vector<int> descendingPlaceValues(accendingPlaceValues);
    sort(accendingPlaceValues, true);
    sort(descendingPlaceValues, false);
    int accendingInteger = (accendingPlaceValues[0]*100)+ (accendingPlaceValues[1]*10)+(accendingPlaceValues[2]);
    int descendingInteger = (descendingPlaceValues[0]*100)+ (descendingPlaceValues[1]*10)+(descendingPlaceValues[2]);
    return 1 + kaprekarProcess(descendingInteger - accendingInteger);
}
void sort(vector<int> &array, bool accending){
    for (int first = 0; first < 3; ++first)
        for (int second = first + 1; second < 3; ++second)
            if (accending ? array[first] > array[second] : array[first] < array[second]){
                int swap = array[first];
                array[first] = array[second];
                array[second] = swap;
            }
}
