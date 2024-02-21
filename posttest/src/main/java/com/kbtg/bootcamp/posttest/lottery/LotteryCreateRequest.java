package com.kbtg.bootcamp.posttest.lottery;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;

public class LotteryCreateRequest {
    @NotNull(message="Ticket should contain number 6 digits")
    @Pattern(regexp="[\\d]{6}", message="Ticket should contain number 6 digits")
    String ticket;

    @NotNull(message = "Price should positive or Zero")
    @PositiveOrZero(message = "Price should positive or Zero")
    Integer price;


    @NotNull(message = "Amount should positive or Zero")
    @PositiveOrZero(message = "Amount should positive or Zero")
    Integer amount;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
