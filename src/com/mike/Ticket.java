package com.mike;

import java.util.Date;

/**
 * Created by Mike on 11/11/2015.
 */
public class Ticket {
    private int priority;
    private String reporter; //Stores person or department who reported issue
    private String description;
    private Date dateReported;
    //STATIC Counter - accessible to all Ticket objects.
//If any Ticket object modifies this counter, all Ticket objects will have the modified value
//Make it private - only Ticket objects should have access
    private static int staticTicketIDCounter = 1;
    //The ID for each ticket - instance variable. Each Ticket will have it's own ticketID variable
    protected int ticketID;

    public Ticket(String desc, int p, String rep, Date date) {
        this.description = desc;
        this.priority = p;
        this.reporter = rep;
        this.dateReported = date;
        this.ticketID = staticTicketIDCounter;
        staticTicketIDCounter++;
    }

    protected int getPriority() {return priority;}
    protected int getTicketID() {return ticketID;}

    public void setTicketID(int ticketID) {this.ticketID = ticketID;
    }

    public String toString() {
        return ("ID = " + this.ticketID + " Issued: " + this.description + "\n" + "Priority: " + this.priority + "\n" +  "Reported by: "
                + this.reporter + "\n" + "Reported on: " + this.dateReported);
    }
}