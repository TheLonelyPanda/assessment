package com.kbtg.bootcamp.posttest.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "user_ticket", schema = "public")
public class UserTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 6 ,name = "ticket_id")
    private String ticketId;
    @Column(length = 10 ,name = "user_id")
    private String userId;
    private int price;
    private int amount;

    public UserTicket(String ticketId, String userId, int price, int amount) {
        this.ticketId = ticketId;
        this.userId = userId;
        this.price = price;
        this.amount = amount;
    }

    public UserTicket(int id, String ticketId, String userId, int price, int amount) {
        this.id = id;
        this.ticketId = ticketId;
        this.userId = userId;
        this.price = price;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
