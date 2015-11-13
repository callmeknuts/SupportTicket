package com.mike;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import  java.util.*;

public class TicketManager {

    public static void main(String[] args) throws IOException{
        LinkedList<Ticket> ticketQueue = new LinkedList<>(); //current tickets (unresolved)
        LinkedList<Ticket> resolvedTickets = new LinkedList<>(); //deleted tickets ( resolved)


        BufferedWriter buffWriter = new BufferedWriter(new FileWriter("Unresolved_tickets_as_of" + SimpleDateFormat.getDateInstance()  +".txt"));
        BufferedWriter buffWriter2 = new BufferedWriter(new FileWriter("Resolved_tickets_as_of" + SimpleDateFormat.getDateInstance() +".txt", true));


        Scanner scan = new Scanner(System.in);
        while(true){
            System.out.println("1. Enter Ticket\n2. Delete by ID\n3. Delete by Issue\n4. Search by Name\n5. View Tickets\n6. Exit");
            int task = Integer.parseInt(scan.nextLine());
            if (task == 1) {
//Call addTickets, which will let us enter any number of new tickets
                addTickets(ticketQueue);
            } else if (task == 2) {
//delete a ticket
                deleteTicket(ticketQueue, resolvedTickets);
            } else if (task == 3){ //by issue
                searchByKeyword(ticketQueue, task);
                System.out.println("Do you want to delete a ticket? (y or n)");
                String delete = scan.nextLine();
                if (delete.equalsIgnoreCase("y")) {
                    deleteTicket(ticketQueue, resolvedTickets);
                }

            } else if ( task == 4 ) { //by name
                searchByKeyword(ticketQueue, task);
            } else if (task == 5){
                printAllTickets(ticketQueue);
            } else if (task ==6){
                // quit program
                System.out.println("Quitting program");
                for (Object t : ticketQueue){
                    buffWriter.write(t.toString());
                }
                buffWriter.close();
                for (Object t : resolvedTickets){
                    buffWriter2.write(t.toString());
                }
                buffWriter2.close();

                break;
            }
            else {
//this will happen for 3 or any other selection that is a valid int
//TODO Program crashes if you enter anything else - please fix
//Default will be print all tickets
                printAllTickets(ticketQueue);
            }
        }
        scan.close();
    }

//    protected static void printToFile (LinkedList<Ticket> ticketQueue, LinkedList<Ticket> re)

    protected static void deleteTicket(LinkedList<Ticket> ticketQueue, LinkedList<Ticket> resolvedTickets) {
//What to do here? Need to delete ticket, but how do we identify the ticket to delete?
        if (ticketQueue.size() == 0) { //no tickets!
            System.out.println("No tickets to delete!\n");
            return;
        }
        Scanner deleteScanner = new Scanner(System.in);
        boolean found = false;
        do {
            System.out.println("Enter ID of ticket to delete");
            while (!deleteScanner.hasNextInt()) {
                deleteScanner.nextInt();
                System.out.println("Error - Please enter ticket ID using integer (1-99) \n");
            }
            int deleteID = deleteScanner.nextInt();
            //Loop over all tickets. Delete the one with this ticket ID
            for (Ticket ticket : ticketQueue) {
                if (ticket.getTicketID() == deleteID) {
                    found = true;
                    System.out.println("Enter problem resolution:");
                    String resolution = deleteScanner.next();
                    ticket.setResolvedDate(new Date());
                    ticket.setResolution(resolution);
                    resolvedTickets.add(ticket);
                    ticketQueue.remove(ticket);
                    System.out.println(String.format("Ticket %d deleted", deleteID));
                    break; //don't need loop any more.
                }
            }
        } while (!found);

//            while (!found) {
//                for (Ticket ticket : ticketQueue) {
//                    if (ticket.getTicketID() == deleteID) {
//                        found = true;
//                        System.out.println("Enter problem resolution:");
//                        String resolution = deleteScanner.next();
//                        ticket.setResolvedDate(new Date());
//                        ticket.setResolution(resolution);
//                        resolvedTickets.add(ticket);
//                        ticketQueue.remove(ticket);
//                        System.out.println(String.format("Ticket %d deleted", deleteID));
//                        break; //don't need loop any more.
//                    }
//                }
//                System.out.println("Ticket ID not found, no ticket deleted \n" + "Please choose a valid ID");
//                deleteID = deleteScanner.nextInt();
                printAllTickets(ticketQueue); //print updated list
            }


    //Move the adding ticket code to a method
    protected static void addTickets(LinkedList<Ticket> ticketQueue) {
        Scanner sc = new Scanner(System.in);
        boolean moreProblems = true;
        String description;
        String reporter;
//let's assume all tickets are created today, for testing. We can change this later if needed
        Date dateReported = new Date(); //Default constructor creates date with current date/time
        int priority;
        while (moreProblems){
            System.out.println("Enter problem");
            description = sc.nextLine();
            System.out.println("Who reported this issue?");
            reporter = sc.nextLine();
            System.out.println("Enter priority of " + description);
            priority = Integer.parseInt(sc.nextLine());
            Ticket t = new Ticket(description, priority, reporter, dateReported);
//ticketQueue.add(t);
            addTicketInPriorityOrder(ticketQueue, t);
            printAllTickets(ticketQueue);
            System.out.println("More tickets to add?");
            String more = sc.nextLine();
            if (more.equalsIgnoreCase("N")) {
                moreProblems = false;
            }
        }
    }	protected static void addTicketInPriorityOrder(LinkedList<Ticket> tickets, Ticket newTicket){
//Logic: assume the list is either empty or sorted
        if (tickets.size() == 0 ) {//Special case - if list is empty, add ticket and return
            tickets.add(newTicket);
            return;
        }
//Tickets with the HIGHEST priority number go at the front of the list. (e.g. 5=server on fire)
//Tickets with the LOWEST value of their priority number (so the lowest priority) go at the end
        int newTicketPriority = newTicket.getPriority();
        for (int x = 0; x < tickets.size() ; x++) { //use a regular for loop so we know which element we are looking at
//if newTicket is higher or equal priority than the this element, add it in front of this one, and return
            if (newTicketPriority >= tickets.get(x).getPriority()) {
                tickets.add(x, newTicket);
                return;
            }
        }
//Will only get here if the ticket is not added in the loop
//If that happens, it must be lower priority than all other tickets. So, add to the end.
        tickets.addLast(newTicket);
    }

// make method for return, see if name contains "input"

    protected static void printAllTickets(LinkedList<Ticket> tickets) {
        System.out.println(" ------- All open tickets ----------");
        for (Ticket t : tickets ) {
            System.out.println(t); //Write a toString method in Ticket class
//println will try to call toString on its argument
        }
        System.out.println(" ------- End of ticket list ----------");
    }

    String getTimeStamp(){
        DateFormat format = new SimpleDateFormat("MMMMM_dd_yyyy");
        return format.format(new Date());
    }

    protected static LinkedList<Ticket> searchByKeyword(LinkedList<Ticket> tickets, int task ){

        // create new linkedList to store matches
        LinkedList<Ticket> ticketSearch = new LinkedList<>();
        System.out.println("Enter keyword to search tickets:");
        Scanner searchScanner = new Scanner(System.in);
        String search = searchScanner.nextLine();
        boolean matches = false;
        // iteration loop to check for matches
        // 1st if loop checks tech names, second checks problem description
        for(Ticket t : tickets) {
            if (task == 3) {
                if (t.getDescription().toLowerCase().contains(search.toLowerCase())) {
                    ticketSearch.add(t);
                    matches = true;
                }
            }

            if (task == 4) {
            if ( t.getReporter().toLowerCase().contains(search.toLowerCase())) {
                    ticketSearch.add(t);
                    matches = true;
                }
            }

        }
        if (!matches) {
            System.out.println("Nothing matching keyword found");
        }

        printAllTickets(ticketSearch);
        return ticketSearch;
        }
}

