// NetProgramming Midterm Problem 1
// Robert Koeninger

#include "iostream"
#include "stdio.h"
#include "unistd.h"
#include "stdlib.h"

using namespace std;

extern char** environ;

static bool running1 = true, running2 = true, running3 = true;

int main(int argc, char** argv){
	int fork1, fork2, fork3;

	int numprocs;
	cout << "enter number of proccesses: ";
	cin >> numprocs;
	cout << endl;
	
	fork1 = fork();
	if (fork1 == -1){
		cerr << "fork1 error" << endl;
		return 1;}
	if (fork1 == 0){//display pid info
		cout << "pid: " << getpid();
	    cout << " ppid: " << getppid();
	    cout << endl;
		cout.flush();
		running1 = false;
		return 0;
	}

	fork2 = fork();
	if (fork2 == -1){
		cerr << "fork2 error" << endl;
		return 1;
	}
	if (fork2 == 0){
		system("ls -l");
		cout.flush();
		running2 = false;
		return 0;
	}

	fork3 = fork();
	if (fork3 == -1){
		cerr << "fork3 error" << endl;
		return 1;
	}
	if (fork3 == 0){//display env
		for (; *environ; ++environ){
			cout << *environ << endl;
		}
		cout.flush();
		running3 = false;
		return 0;
	}

	//parent process
	cout << "this is the parent process pid: " << getpid();
	cout << " ppid: " << getppid();
	cout << endl << endl;
	cout.flush();
	while (running1 && running2 && running3);
	cout << "parent process terminates" << endl;
	cout.flush();
	
	return 0;
}

