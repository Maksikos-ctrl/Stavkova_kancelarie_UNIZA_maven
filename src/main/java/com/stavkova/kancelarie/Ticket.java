package com.stavkova.kancelarie;
import java.util.ArrayList;
import java.util.List;


class Ticket {
    private int id;
    private List<Integer> selectedNums;
    private double betAmount;

    public Ticket(int id, List<Integer> selectedNums, double betAmount) {
        this.id = id;
        this.selectedNums = selectedNums;
        this.betAmount = betAmount;
    }

    public int getId() {
        return id;
    }

    public List<Integer> getSelectedNums() {
        return selectedNums;
    }

    public double getBetAmount() {
        return betAmount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSelectedNums(List<Integer> selectedNums) {
        this.selectedNums = selectedNums;
    }

    public void setBetAmount(double betAmount) {
        this.betAmount = betAmount;
    }
}

class TicketManager {
    private List<Ticket> tickets;

    public TicketManager(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    public void removeTicket(int id) {
        tickets.removeIf(ticket -> ticket.getId() == id);
    }

    public void selectNumbers(int id, List<Integer> selectedNums) {
        for (Ticket ticket : tickets) {
            if (ticket.getId() == id) {
                ticket.setSelectedNums(selectedNums);
                break;
            }
        }
    }

    public void setBetAmount(int id, double betAmount) {
        for (Ticket ticket : tickets) {
            if (ticket.getId() == id) {
                ticket.setBetAmount(betAmount);
                break;
            }
        }
    }

    public void printTickets() {
        for (Ticket ticket : tickets) {
            System.out.println("Ticket ID: " + ticket.getId());
            System.out.println("Selected numbers: " + ticket.getSelectedNums());
            System.out.println("Bet amount: " + ticket.getBetAmount());
            System.out.println();
        }
    }

    public ArrayList<Ticket> getTickets() {
        return new ArrayList<>(tickets);
    }
}