package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.lottery.LotteryResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/{userId}/lotteries/{ticketId}")
    public ResponseEntity<UserTicketResponse> buyLottery(@Valid UserTicketRequest userTicketRequest) {
        UserTicketResponse response = userService.addLotteryInUserTicket(userTicketRequest.getUserId(),userTicketRequest.getTicketId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}/lotteries")
    public UserLotteryResponse getUserLottery(@Valid UserRequest userRequest) {
        return userService.getLottery(userRequest.getUserId());
    }

    @DeleteMapping("/{userId}/lotteries/{ticketId}")
    public LotteryResponse sellUserLottery(@Valid UserTicketRequest userTicketRequest) {
        return userService.sellLottery(userTicketRequest.getUserId(), userTicketRequest.getTicketId());
    }

}
