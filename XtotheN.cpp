#include <iostream>
#include <conio.h>
using namespace std;
int callCount;
long xn(long x, int n){
    ++callCount;
    if (n == 0) {
        return 1;
    } else if (n == 1) {
        return x;
    } long xHalfN = xn(x,n/2);
    long xHalfNSquared = xHalfN * xHalfN;
    if (n % 2 != 0){
        xHalfNSquared = xHalfNSquared * x;
    } return xHalfNSquared;
} int main(){
    callCount = 0;
    long x,n;
    cout << "x^n: ";
    cin >> x >> n;
    cout << "x^n = " << xn(x,n) << "\n";
    cout << callCount << " calls";
    getch();
    return 0;
}
