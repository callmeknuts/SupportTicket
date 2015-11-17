package com.mike;

import java.util.Date;

/*
 * Created by Mike on 11/11/2015.
 */
public class Ticket {
    private int priority;
    private String reporter; //Stores person or department who reported issue
    private String description;
    private Date dateReported;
    private Date resolvedDate;
    private String resolution;

    //STATIC Counter - accessible to all Ticket objects.
//If any Ticket object modifies this counter, all Ticket objects will have the modified value
//Make it private - only Ticket objects should have access
    private static int staticTicketIDCounter = 1;
    //The ID for each ticket - instance variable. Each Ticket will have it's own ticketID variable
    protected int ticketID;

    public Ticket(String desc, int prior, String rep, Date date) {
        this.description = desc;
        this.priority = prior;
        this.reporter = rep;
        this.dateReported = date;
        this.ticketID = staticTicketIDCounter;
        staticTicketIDCounter++;
    }

    protected int getPriority() {return priority;}
    protected int getTicketID() {return ticketID;}
    public void setTicketID(int ticketID) {this.ticketID = ticketID;}
    public String getReporter() {return reporter;}
    public void setReporter(String reporter) {this.reporter = reporter;}
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public Date getResolvedDate() {return resolvedDate;}
    public void setResolvedDate(Date resolvedDate) {this.resolvedDate = resolvedDate;}
    public String getResolution() {return resolution;}
    public void setResolution(String resolution) {this.resolution = resolution;}

    public String toString() {
        return ("ID = " + this.ticketID + "\n" + "Issue: " + this.description + "\n" + "Priority: " + this.priority + "\n" +  "Reported by: "
                + this.reporter + "\n" + "Reported on: " + this.dateReported + "\n");
    }
}