package com.kbtg.bootcamp.posttest.user;


import java.util.List;

public class UserLotteryResponse {

    private List<String> tickets;
    private int count;
    private int cost;

    public List<String> getTickets() {
        return tickets;
    }

    public void setTickets(List<String> tickets) {
        this.tickets = tickets;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
