/*
 * Robert Koeninger
 * Section 002
 * Project 4 - Event Driven Simulation
 * Friday, May 26, 2006
 *
 * This include file contains the definition of the Event structure.
 */

#ifndef EVENTS_DEF
    #define EVENTS_DEF

    #define ARRIVAL   1
    #define DEPARTURE 2

struct Customer
{

    int id;              // The customer ID number, assigned by simulation
    int transactionTime; // The transaction time the customer needs
    
};

struct Event
{

    int type;            // The type of event (Arrival, Departure)
    int time;            // The time of arrival or departure
    
    int transactionTime; // Valid only for arrival events
    int customer;        // Valid only for departure events
                         // (customers are numbered on there way in)

};
    
#endif
