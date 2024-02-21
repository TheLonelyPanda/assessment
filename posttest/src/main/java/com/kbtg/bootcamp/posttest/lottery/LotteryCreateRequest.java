package com.kbtg.bootcamp.posttest.lottery;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class LotteryCreateRequest {
    @NotNull(message="Ticket should contain number six digits")
    @Pattern(regexp="[\\d]{6}", message="Ticket should contain number six digits")
    String ticket;

    @NotNull(message = "Price should positive or Zero")
    @PositiveOrZero(message = "Price should positive or Zero")
    int price;

    @NotNull(message = "Price should positive or Zero")
    @PositiveOrZero(message = "Amount should positive or Zero")
    int amount;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
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
