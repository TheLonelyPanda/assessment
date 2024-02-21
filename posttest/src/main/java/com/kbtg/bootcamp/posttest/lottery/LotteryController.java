package com.kbtg.bootcamp.posttest.lottery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lotteries")
public class LotteryController {

    @Autowired
    private ILotteryService lotteryService;

    public LotteryController(ILotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }

    @GetMapping("")
    public ResponseEntity<LotteryListResponse> getLotteries() {
        LotteryListResponse lotteryInStock = lotteryService.getLotteryInStock();
        return new ResponseEntity<>(lotteryInStock, HttpStatus.OK);
    }

}
