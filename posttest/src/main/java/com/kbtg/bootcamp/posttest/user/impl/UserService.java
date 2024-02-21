package com.kbtg.bootcamp.posttest.user.impl;

import com.kbtg.bootcamp.posttest.exception.StatusNotFoundTicketException;
import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryRepository;
import com.kbtg.bootcamp.posttest.lottery.LotteryResponse;
import com.kbtg.bootcamp.posttest.user.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final LotteryRepository lotteryRepository;

    public UserService(UserRepository userRepository, LotteryRepository lotteryRepository) {
        this.userRepository = userRepository;
        this.lotteryRepository = lotteryRepository;
    }


    @Override
    @Transactional
    public UserTicketResponse addLotteryInUserTicket(String userId,String ticketId) {
        UserTicketResponse response = new UserTicketResponse();
        Optional<Lottery> lotteryByQuery = lotteryRepository.findByTicketAndAmountGreaterThan(ticketId, 0);
        if (lotteryByQuery.isEmpty()) {
            throw new StatusNotFoundTicketException(
                    "Lottery number " + ticketId + " out of stock");
        }else{
            int amount;
            UserTicket userTicketObj;
            Lottery lottery = lotteryByQuery.get();
            Optional<UserTicket> userTicketByQuery = userRepository.findByTicketIdAndUserId(ticketId,userId);
            if (userTicketByQuery.isEmpty()) {
                amount = 1;
                userTicketObj = new UserTicket(ticketId, userId, lottery.getPrice() ,amount);
            }else{
                UserTicket userTicket = userTicketByQuery.get();
                amount = userTicket.getAmount()+1;
                userTicketObj = new UserTicket(userTicket.getId(),ticketId, userId, lottery.getPrice() ,amount);
            }
            UserTicket userTicketSave = userRepository.save(userTicketObj);
            response.setId(userTicketSave.getId());

            int updateAmount = lottery.getAmount() - 1;
            lottery.setAmount(updateAmount);
            lotteryRepository.save(lottery);
        }
        return response;
    }

    @Override
    @Transactional
    public  UserLotteryResponse getLottery (String userId) {
        UserLotteryResponse response = new UserLotteryResponse();
        List<UserTicket> userTickets = userRepository.findByUserId(userId);
        List<String> tickets = new ArrayList<>();
        int count = 0;
        int price = 0;

        for (UserTicket userTicket : userTickets) {
            tickets.add(userTicket.getTicketId());
            count += userTicket.getAmount();
            price += userTicket.getPrice()*userTicket.getAmount();
        }
        response.setTickets(tickets);
        response.setCount(count);
        response.setCost(price);
        return response;
    }

    @Override
    @Transactional
    public LotteryResponse sellLottery(String userId, String ticketId) {
        LotteryResponse response = new LotteryResponse();
        Optional<UserTicket> userTicketByQuery = userRepository.findByTicketIdAndUserId(ticketId,userId);
        if (userTicketByQuery.isEmpty()) {
            throw new StatusNotFoundTicketException(
                    "Lottery not found");
        }else{
            UserTicket userTicket = userTicketByQuery.get();
            userRepository.delete(userTicket);
        }
        response.setTicket(ticketId);
        return response;
    }

}
