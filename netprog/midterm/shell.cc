// Midterm Problem 2
// Robert Koeninger (m02477822)
// May 12, 2009

#include "stdio.h"
#include "unistd.h"
#include "iostream"
#include "stdlib.h"
#include "string"

using namespace std;

int main(int argc, char** argv){
	string cmdline;
	while (true){ // Main loop
		cout << "prompt> ";
		getline(cin, cmdline);
		if (cmdline.compare("exit") == 0){
			cout << "bye" << endl;
			return 0;
		}
		else if (cmdline.substr(0, 2).compare("ps") == 0){
			system(cmdline.c_str());
		}
		else if (cmdline.substr(0, 2).compare("df") == 0){
			system(cmdline.c_str());
		}
		else if (cmdline.substr(0, 3).compare("cat") == 0){
			system(cmdline.c_str());
		}
		else if (cmdline.substr(0, 2).compare("ls") == 0){
			system(cmdline.c_str());
		}
		else if (cmdline.compare("clear") == 0){
			system("clear");
		}
		else {
			cout << "command not recognized" << endl;
		}
	}
}
