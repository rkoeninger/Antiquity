#include <iostream>
#include "power.h"

using namespace std;

int main(int argc, char** argv)
{
    if (argc != 3){cout << "Usage: "<<argv[0]<<" <base> <exponent>" << endl;}
    int x = atoi(argv[1]),y = atoi(argv[2]);
	Power p;
	cout << "x ^ y = " << p.pow(x,y) << endl;
	return 0;
}
