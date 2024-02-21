package com.kbtg.bootcamp.posttest.admin.impl;

import com.kbtg.bootcamp.posttest.admin.AdminRepository;
import com.kbtg.bootcamp.posttest.admin.IAdminService;
import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryCreateRequest;
import com.kbtg.bootcamp.posttest.lottery.LotteryResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService implements IAdminService {

    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    @Transactional
    public LotteryResponse createLotteryToStock(LotteryCreateRequest request) {
        LotteryResponse lotteryResponse = new LotteryResponse();
        Optional<Lottery> optionalLottery = Optional.ofNullable(adminRepository.findByTicket(request.getTicket()));

        Lottery lottery = new Lottery(request.getTicket(), request.getPrice(), request.getAmount());

        if(optionalLottery.isEmpty()){
            adminRepository.save(lottery);
        }else {
            int amountExist = optionalLottery.get().getAmount();
            lottery.setAmount(lottery.getAmount()+amountExist);
            adminRepository.save(lottery);
        }
        lotteryResponse.setTicket(lottery.getTicket());

        return lotteryResponse;
    }


}
