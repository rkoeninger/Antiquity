#include "iostream"

using namespace std;

char* EQUILATERAL = "Equilateral";
char* ISOSCELES = "Isosceles";
char* SCALENE = "Scalene";

char* getTriangleType(int a, int b, int c){
	if (a == b)
		if (b == c)
			return EQUILATERAL;
		else
			return ISOSCELES;
	else if (b == c)
		return ISOSCELES;
	else if (a == c)
		return ISOSCELES;
	return SCALENE;
}

int main(int argc, char** argv){
	int a, b, c;
	char* retval;
	char* expected;
	int testnum;

	// Test Case 1 - a!=b, b!=c, a!=c, scalene
	testnum = 1;
	a = 1;
	b = 2;
	c = 3;
	expected = SCALENE;
	retval = getTriangleType(a, b, c);
	if (retval == expected){
		cout << "Test " << testnum << " Passed" << endl;
	}else{
		cout << "Test " << testnum << " FAILED!!!" << endl;
	}
	// Test Case 2 - a!=b, b!=c, a==c, isosceles
	testnum = 2;
	a = 3;
	b = 2;
	c = 3;
	expected = ISOSCELES;
	retval = getTriangleType(a, b, c);
	if (retval == expected){
		cout << "Test " << testnum << " Passed" << endl;
	}else{
		cout << "Test " << testnum << " FAILED!!!" << endl;
	}

	// Test Case 3 - a!=b, b==c, a!=c, isosceles
	testnum = 3;
	a = 5;
	b = 2;
	c = 2;
	expected = ISOSCELES;
	retval = getTriangleType(a, b, c);
	if (retval == expected){
		cout << "Test " << testnum << " Passed" << endl;
	}else{
		cout << "Test " << testnum << " FAILED!!!" << endl;
	}

	// Test Case 4 - a==b, b!=c, a!=c, isosceles
	testnum = 4;
	a = 6;
	b = 6;
	c = 3;
	expected = ISOSCELES;
	retval = getTriangleType(a, b, c);
	if (retval == expected){
		cout << "Test " << testnum << " Passed" << endl;
	}else{
		cout << "Test " << testnum << " FAILED!!!" << endl;
	}

	// Test Case 5 - a==b, b==c, a==c, equilateral
	testnum = 5;
	a = 4;
	b = 4;
	c = 4;
	expected = EQUILATERAL;
	retval = getTriangleType(a, b, c);
	if (retval == expected){
		cout << "Test " << testnum << " Passed" << endl;
	}else{
		cout << "Test " << testnum << " FAILED!!!" << endl;
	}

	return 0;
}
