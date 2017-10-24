/*
 * Robert Koeninger
 * Section 002
 * Project 4 - Event Driven Simulation
 * Friday, May 26, 2006
 *
 * This include file contains the client program and main function.
 */

#include <cstddef>
#include <iostream>
#include <conio.h>
#include <vector>
#include "events.h"
#include "queue.h"
#include "linkedlist.h"

using namespace std;

struct CustomerStats
{

    int arrivalTime, departureTime, transactionTime, waitTime;

};

// Define 'VERBOSE' to show live event information

//#define VERBOSE

int main()
{

    LinkedList events;
    Queue waitLine;
    vector<CustomerStats> stats;
    int currentCustomer = 0;

    cout << "Bank Customer Service Simulation\n\n";
    cout << "Enter in the times of arrival and the time\n";
    cout << "required to complete their transaction.\n";
    cout << "Enter a \'q\' for the arrival time for a\n";
    cout << "customer, and that customer will be ignored\n";
    cout << "and input will stop\n";
    cout << "\n";

    int arrivalTime, transactionTime;

    for (;;)
    {

        cout << "Arrival Time:     ";
        cin >> arrivalTime;
        
        if (! cin) goto inputdone;
        
        cout << "Transaction Time: ";
        cin >> transactionTime;
        
        if (! cin) goto inputdone;
        
        Event* arrival = new Event;
        arrival->type = ARRIVAL;
        arrival->time = arrivalTime;
        arrival->transactionTime = transactionTime;
        events.add(int(arrival));
        
        cout << "\n";

    }
    
    inputdone:

    system("cls");

    if (events.getSize() == 0)
    {

        cout << "No customers entered.";
        getch();
        return 0;

    }

    stats.resize(events.getSize()); // Customer 1 is @ [0],
                                    // Customer n is @ [n-1]
    
    bool customerAtTeller = false;
    
    
    // Move thought the list of chronological events, tracking customer moves
    while ((events.getSize() > 0) || (waitLine.getSize() > 0))
    {

        Event* currentEvent = (Event*) events.removeFirst();
        int currentTime = currentEvent->time;
        
        if (currentEvent->type == ARRIVAL)
        {

            // A customer has arrived at the bank

            currentCustomer++; // Increment; a new customer has arrived
            Customer* customer = new Customer;
            customer->id = currentCustomer;
            customer->transactionTime = currentEvent->transactionTime;
            waitLine.enqueue(int(customer));
            
            // Fill-in stats
            stats[currentCustomer-1].arrivalTime = currentTime;
            stats[currentCustomer-1].transactionTime =
            currentEvent->transactionTime;
#ifdef VERBOSE
            cout << "Customer " << currentCustomer << " has arrived\n";
            cout << "\tArrival time: " << currentTime << "\n";
#endif
        }
        else if (currentEvent->type == DEPARTURE) // Departure event
        {

            // A customer is done with the teller

            customerAtTeller = false; // The teller is free

            // Fill-in stats
            stats[currentEvent->customer-1].departureTime = currentTime;
            stats[currentEvent->customer-1].waitTime = currentTime -
            stats[currentEvent->customer-1].arrivalTime -
            stats[currentEvent->customer-1].transactionTime;
#ifdef VERBOSE
            cout << "Customer " << currentEvent->customer << " is leaving\n";
            cout << "\tDeparture time: " << currentTime << "\n";
#endif
        }
#ifdef VERBOSE
        cout << "Teller available: " << customerAtTeller;
        cout << "\n";
#endif
        // If teller is available, the next customer moves to the teller
        if ((! customerAtTeller) && (waitLine.getSize() > 0))
        {

            // A customer is moving from the line to the teller

            customerAtTeller = true;
            Customer* customer = ((Customer*) waitLine.dequeue());
            Event* departing = new Event;
            departing->type = DEPARTURE;
            departing->time = currentTime + customer->transactionTime;
            departing->customer = customer->id;
            events.add(int(departing));
#ifdef VERBOSE
            cout << "Customer " << departing->customer << " is at teller\n";
            cout << "\tReached teller at: " << currentTime;
            cout << "\n";
#endif
        }
#ifdef VERBOSE
        cout << endl;
#endif
    }
    
    cout << "Customer Arrival Wait\tTransaction\tDeparture\n\n";
    
    int sum = 0; // The sum of all waiting times
    
    for (int x = 0; x < stats.size(); ++x)
    {

        CustomerStats stat = stats[x];
        cout << (x+1);
        cout << "\t " << stat.arrivalTime << "\t " << stat.waitTime;
        cout << "\t" << stat.transactionTime << "\t\t" << stat.departureTime;
        cout << "\n";
        
        sum += stat.waitTime;

    }
    
    cout << "\nAverage Wait Time: " << (sum / double(stats.size()));
    cout << "\n\nThe End";
    getch();
    return 0;

}
