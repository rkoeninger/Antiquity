#include <iostream>
#include <conio.h>
using namespace std;
int gcd(int a, int b){
    cout << "a=" << a << " b=" << b << "\n";
    if (a % b == 0){
        return b;
    } else {
        return gcd(b, a % b);
    }
}
int main(){
    int a, b;
    cout << "enter a and b";
    cin >> a >> b;
    cout << gcd(a,b);
    getch();
    return 0;
}
