package com.kbtg.bootcamp.posttest.lottery;

import com.kbtg.bootcamp.posttest.lottery.impl.LotteryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class LotteryControllerTest {
    MockMvc mockMvc;
    @Mock
    LotteryService lotteryService;

    @BeforeEach
    void setUp() {
        LotteryController lotteryController = new LotteryController(lotteryService);
        mockMvc = MockMvcBuilders.standaloneSetup(lotteryController)
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("when perform on GET: /lotteries should return tickets as List now is 123456,234567,345678 ")
    void getLotteryInStock() throws Exception {

        List<String> lottery = new ArrayList<>();
        lottery.add("123456");
        lottery.add("234567");
        lottery.add("345678");

        LotteryListResponse response = new LotteryListResponse();
        response.setTickets(lottery);
        when(lotteryService.getLotteryInStock())
                .thenReturn(response);

        mockMvc.perform(
                        get("/lotteries")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tickets", is(lottery)));

    }
}