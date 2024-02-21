package com.kbtg.bootcamp.posttest.admin;

import com.kbtg.bootcamp.posttest.admin.impl.AdminService;
import com.kbtg.bootcamp.posttest.lottery.LotteryCreateRequest;
import com.kbtg.bootcamp.posttest.lottery.LotteryResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private IAdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/lotteries")
    public ResponseEntity<LotteryResponse> createLottery(@Valid @RequestBody LotteryCreateRequest lotteryRequest) {
        return new ResponseEntity<>(adminService.createLotteryToStock(lotteryRequest), HttpStatus.CREATED);
    }

}
