package com.kbtg.bootcamp.posttest.admin;

import com.kbtg.bootcamp.posttest.admin.impl.AdminService;
import com.kbtg.bootcamp.posttest.exception.ApiExceptionHandler;
import com.kbtg.bootcamp.posttest.lottery.LotteryResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class AdminControllerTest {
    MockMvc mockMvc;
    @Mock
    AdminService adminService;

    @BeforeEach
    void setUp() {
        AdminController adminController = new AdminController(adminService);
        mockMvc = MockMvcBuilders.standaloneSetup(adminController)
                .setControllerAdvice(new ApiExceptionHandler())
                .alwaysDo(print())
                .build();
    }
    @Test
    @DisplayName("when perform on POST: /admin/lotteries with ticket 6 digit should return ticket as input now is 123456 ")
    void createLotteryWithTicket() throws Exception {

        String content = "{\n" +
                "  \"ticket\": \"123456\",\n" +
                "  \"price\": 1,\n" +
                "  \"amount\": 80\n" +
                "}";

        LotteryResponse response = new LotteryResponse();
        response.setTicket("123456");
        when(adminService.createLotteryToStock(any()))
                .thenReturn(response);

        mockMvc.perform(
                        post("/admin/lotteries")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(content)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.ticket", is("123456")));

    }

    @Test
    @DisplayName("when perform on POST: /admin/lotteries with ticket and ticket not number should return Status is BAD_REQUEST and message is Ticket should contain number 6 digits ")
    void createLotteryWithTicketAndTicketNotNumber() throws Exception {

        String content = "{\n" +
                "  \"ticket\": \"aaaaa\",\n" +
                "  \"price\": 1,\n" +
                "  \"amount\": 80\n" +
                "}";

        LotteryResponse response = new LotteryResponse();
        response.setTicket("123456");
        when(adminService.createLotteryToStock(any()))
                .thenReturn(response);

        mockMvc.perform(
                        post("/admin/lotteries")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(content)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Ticket should contain number 6 digits")));

    }

    @Test
    @DisplayName("when perform on POST: /admin/lotteries with ticket and ticket is Null should return Status is BAD_REQUEST and message is Ticket should contain number 6 digits ")
    void createLotteryWithTicketAndTicketIsNull() throws Exception {

        String content = "{\n" +
                "  \"ticket\": null,\n" +
                "  \"price\": 1,\n" +
                "  \"amount\": 80\n" +
                "}";

        LotteryResponse response = new LotteryResponse();
        response.setTicket("123456");
        when(adminService.createLotteryToStock(any()))
                .thenReturn(response);

        mockMvc.perform(
                        post("/admin/lotteries")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(content)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Ticket should contain number 6 digits")));

    }

    @Test
    @DisplayName("when perform on POST: /admin/lotteries with ticket and ticket not 6 digits should return Status is BAD_REQUEST and message is Ticket should contain number 6 digits ")
    void createLotteryWithTicketAndTicketIsNot6Digits() throws Exception {

        String content = "{\n" +
                "  \"ticket\": \"1234567\",\n" +
                "  \"price\": 1,\n" +
                "  \"amount\": 80\n" +
                "}";

        LotteryResponse response = new LotteryResponse();
        response.setTicket("123456");
        when(adminService.createLotteryToStock(any()))
                .thenReturn(response);

        mockMvc.perform(
                        post("/admin/lotteries")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(content)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Ticket should contain number 6 digits")));

    }

    @Test
    @DisplayName("when perform on POST: /admin/lotteries with ticket and amount not number should return Status is BAD_REQUEST and message is Value should number")
    void createLotteryWithTicketAndAmountNotNumber() throws Exception {


        String content = "{\n" +
                "  \"ticket\": \"164415\",\n" +
                "  \"price\": 80,\n" +
                "  \"amount\": \"aaa\"\n" +
                "}";

        LotteryResponse response = new LotteryResponse();
        response.setTicket("123456");
        when(adminService.createLotteryToStock(any()))
                .thenReturn(response);

        mockMvc.perform(
                        post("/admin/lotteries")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(content)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Value should number")));

    }

    @Test
    @DisplayName("when perform on POST: /admin/lotteries with ticket and amount is Null should return Status is BAD_REQUEST and message is Amount should positive or Zero")
    void createLotteryWithTicketAndAmountIsNull() throws Exception {


        String content = "{\n" +
                "  \"ticket\": \"164415\",\n" +
                "  \"price\": 80,\n" +
                "  \"amount\": null\n" +
                "}";

        LotteryResponse response = new LotteryResponse();
        response.setTicket("123456");
        when(adminService.createLotteryToStock(any()))
                .thenReturn(response);

        mockMvc.perform(
                        post("/admin/lotteries")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(content)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Amount should positive or Zero")));

    }

    @Test
    @DisplayName("when perform on POST: /admin/lotteries with ticket and amount is negative should return Status is BAD_REQUEST and message is Amount should positive or Zero")
    void createLotteryWithTicketAndAmountIsNegative() throws Exception {


        String content = "{\n" +
                "  \"ticket\": \"164415\",\n" +
                "  \"price\": 80,\n" +
                "  \"amount\": -2\n" +
                "}";

        LotteryResponse response = new LotteryResponse();
        response.setTicket("123456");
        when(adminService.createLotteryToStock(any()))
                .thenReturn(response);

        mockMvc.perform(
                        post("/admin/lotteries")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(content)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Amount should positive or Zero")));

    }

    @Test
    @DisplayName("when perform on POST: /admin/lotteries with ticket and price not number should return Status is BAD_REQUEST and message is Value should number")
    void createLotteryWithTicketAndPriceNotNumber() throws Exception {

        String content = "{\n" +
                "  \"ticket\": \"164415\",\n" +
                "  \"price\": \"bbb\",\n" +
                "  \"amount\": 2\n" +
                "}";

        LotteryResponse response = new LotteryResponse();
        response.setTicket("123456");
        when(adminService.createLotteryToStock(any()))
                .thenReturn(response);

        mockMvc.perform(
                        post("/admin/lotteries")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(content)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Value should number")));

    }

    @Test
    @DisplayName("when perform on POST: /admin/lotteries with ticket and price is Null should return Status is BAD_REQUEST and message is Price should positive or Zero")
    void createLotteryWithTicketAndPriceIsNull() throws Exception {

        String content = "{\n" +
                "  \"ticket\": \"164415\",\n" +
                "  \"price\": null,\n" +
                "  \"amount\": 2\n" +
                "}";

        LotteryResponse response = new LotteryResponse();
        response.setTicket("123456");
        when(adminService.createLotteryToStock(any()))
                .thenReturn(response);

        mockMvc.perform(
                        post("/admin/lotteries")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(content)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Price should positive or Zero")));

    }

    @Test
    @DisplayName("when perform on POST: /admin/lotteries with ticket and price is negative should return Status is BAD_REQUEST and message is Price should positive or Zero")
    void createLotteryWithTicketAndPriceIsNegative() throws Exception {

        String content = "{\n" +
                "  \"ticket\": \"164415\",\n" +
                "  \"price\": -80,\n" +
                "  \"amount\": 2\n" +
                "}";

        LotteryResponse response = new LotteryResponse();
        response.setTicket("123456");
        when(adminService.createLotteryToStock(any()))
                .thenReturn(response);

        mockMvc.perform(
                        post("/admin/lotteries")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(content)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Price should positive or Zero")));

    }
}