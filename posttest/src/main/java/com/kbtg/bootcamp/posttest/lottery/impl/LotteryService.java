package com.kbtg.bootcamp.posttest.lottery.impl;

import com.kbtg.bootcamp.posttest.lottery.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LotteryService implements ILotteryService {

    private final LotteryRepository lotteryRepository;

    public LotteryService(LotteryRepository lotteryRepository) {
        this.lotteryRepository = lotteryRepository;
    }

    @Override
    @Transactional
    public LotteryListResponse getLotteryInStock() {
        List<String> listLottery = new ArrayList<>();
        LotteryListResponse lotteryListResponse = new LotteryListResponse();

        List<Lottery> lotteryInStock = lotteryRepository.findByAmountGreaterThan(0);
        for (Lottery lottery : lotteryInStock) {
            listLottery.add(lottery.getTicket());
        }
        lotteryListResponse.setTickets(listLottery);
        return lotteryListResponse;
    }

}
