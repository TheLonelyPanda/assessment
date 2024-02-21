package com.kbtg.bootcamp.posttest.user;

import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryResponse;

public interface IUserService {
    UserTicketResponse addLotteryInUserTicket(String userId,String ticketId);
    UserLotteryResponse getLottery (String userId);
    LotteryResponse sellLottery(String userId, String ticketId);

}
