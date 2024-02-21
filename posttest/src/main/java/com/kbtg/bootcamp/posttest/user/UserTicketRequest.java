package com.kbtg.bootcamp.posttest.user;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;

public class UserTicketRequest {

    @Pattern(regexp="[\\d]{10}", message="User should contain number ten digits!")
    String userId;

    @Pattern(regexp="[\\d]{6}", message="User should contain number six digits!")
    String ticketId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }
}
