package com.kbtg.bootcamp.posttest.user;

import jakarta.validation.constraints.Pattern;

public class UserRequest {

    @Pattern(regexp="[\\d]{10}", message="User should contain number ten digits!")
    String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
