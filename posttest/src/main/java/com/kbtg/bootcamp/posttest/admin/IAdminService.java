package com.kbtg.bootcamp.posttest.admin;

import com.kbtg.bootcamp.posttest.lottery.LotteryCreateRequest;
import com.kbtg.bootcamp.posttest.lottery.LotteryResponse;



public interface IAdminService {
    LotteryResponse createLotteryToStock(LotteryCreateRequest request);


}
