package com.kbtg.bootcamp.posttest.user;


import com.kbtg.bootcamp.posttest.exception.ApiExceptionHandler;
import com.kbtg.bootcamp.posttest.exception.StatusNotFoundTicketException;
import com.kbtg.bootcamp.posttest.lottery.LotteryResponse;
import com.kbtg.bootcamp.posttest.user.impl.UserService;
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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    MockMvc mockMvc;
    @Mock
    UserService userService;
    @BeforeEach
    void setUp() {
        UserController userController = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new ApiExceptionHandler())
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("when perform on POST: /users/{userId}/lotteries/{ticketId} with ticket exist should return id")
    void buyLotteryWithTicketExist() throws Exception {

        UserTicketRequest userTicketRequest = new UserTicketRequest();
        userTicketRequest.setTicketId("123456");
        userTicketRequest.setUserId("1234567890");

        UserTicketResponse response = new UserTicketResponse();
        response.setId(1);
        when(userService.addLotteryInUserTicket(any(),any()))
                .thenReturn(response);

        mockMvc.perform(
                        post("/users/"+userTicketRequest.getUserId()+"/lotteries/"+userTicketRequest.getTicketId())
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)));

    }

    @Test
    @DisplayName("when perform on POST: /users/{userId}/lotteries/{ticketId} with ticketId not found in database should return Status is NOT_FOUND and message is Lottery number {ticketId} out of stock")
    void buyLotteryWithTicketAndNotFoundTicketInDB() throws Exception {

        UserTicketRequest userTicketRequest = new UserTicketRequest();
        userTicketRequest.setTicketId("999999");
        userTicketRequest.setUserId("1234567890");

        when(userService.addLotteryInUserTicket(any(),any()))
                .thenThrow(new StatusNotFoundTicketException("Lottery number " + userTicketRequest.getTicketId() + " out of stock"));

        mockMvc.perform(
                        post("/users/"+userTicketRequest.getUserId()+"/lotteries/"+userTicketRequest.getTicketId())
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Lottery number " + userTicketRequest.getTicketId() + " out of stock")));

    }

    @Test
    @DisplayName("when perform on POST: /users/{userId}/lotteries/{ticketId} with TicketId not 6 digits should Status is BAD_REQUEST and message is Ticket should contain number 6 digits")
    void buyLotteryWithTicketAndTicketIdNot6Digits() throws Exception {

        UserTicketRequest userTicketRequest = new UserTicketRequest();
        userTicketRequest.setTicketId("1234567");
        userTicketRequest.setUserId("1234567890");

        UserTicketResponse response = new UserTicketResponse();
        response.setId(1);
        when(userService.addLotteryInUserTicket(any(),any()))
                .thenReturn(response);

        mockMvc.perform(
                        post("/users/"+userTicketRequest.getUserId()+"/lotteries/"+userTicketRequest.getTicketId())
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Ticket should contain number 6 digits")));

    }

    @Test
    @DisplayName("when perform on POST: /users/{userId}/lotteries/{ticketId} with UserId not 10 digits should return Status is BAD_REQUEST and message is User should contain number 10 digits")
    void buyLotteryWithTicketAndUserIdIdNot10Digits() throws Exception {

        UserTicketRequest userTicketRequest = new UserTicketRequest();
        userTicketRequest.setTicketId("123456");
        userTicketRequest.setUserId("12345678901");

        UserTicketResponse response = new UserTicketResponse();
        response.setId(1);
        when(userService.addLotteryInUserTicket(any(),any()))
                .thenReturn(response);

        mockMvc.perform(
                        post("/users/"+userTicketRequest.getUserId()+"/lotteries/"+userTicketRequest.getTicketId())
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("User should contain number 10 digits")));

    }

    @Test
    @DisplayName("when perform on POST: /users/{userId}/lotteries/{ticketId} with TicketId not number should Status is BAD_REQUEST and message is Ticket should contain number 6 digits")
    void buyLotteryWithTicketAndTicketIdNotNumber() throws Exception {

        UserTicketRequest userTicketRequest = new UserTicketRequest();
        userTicketRequest.setTicketId("aaaaa");
        userTicketRequest.setUserId("1234567890");

        UserTicketResponse response = new UserTicketResponse();
        response.setId(1);
        when(userService.addLotteryInUserTicket(any(),any()))
                .thenReturn(response);

        mockMvc.perform(
                        post("/users/"+userTicketRequest.getUserId()+"/lotteries/"+userTicketRequest.getTicketId())
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Ticket should contain number 6 digits")));

    }

    @Test
    @DisplayName("when perform on POST: /users/{userId}/lotteries/{ticketId} with UserId not number should return Status is BAD_REQUEST and message is User should contain number 10 digits")
    void buyLotteryWithTicketAndUserIdIdNotNumber() throws Exception {

        UserTicketRequest userTicketRequest = new UserTicketRequest();
        userTicketRequest.setTicketId("123456");
        userTicketRequest.setUserId("aaaaa");

        UserTicketResponse response = new UserTicketResponse();
        response.setId(1);
        when(userService.addLotteryInUserTicket(any(),any()))
                .thenReturn(response);

        mockMvc.perform(
                        post("/users/"+userTicketRequest.getUserId()+"/lotteries/"+userTicketRequest.getTicketId())
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("User should contain number 10 digits")));

    }


    @Test
    @DisplayName("when perform on GET: /lotteries should return tickets as List now is 123456,234567,345678 ")
    void getUserLottery() throws Exception {

        UserRequest userRequest = new UserRequest();
        userRequest.setUserId("1234567890");

        List<String> lottery = new ArrayList<>();
        lottery.add("123456");
        lottery.add("234567");
        lottery.add("345678");

        UserLotteryResponse response = new UserLotteryResponse();
        response.setTickets(lottery);
        response.setCount(4);
        response.setCost(320);
        when(userService.getLottery(userRequest.getUserId()))
                .thenReturn(response);

        mockMvc.perform(
                        get("/users/"+userRequest.getUserId()+"/lotteries")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tickets", is(lottery)))
                .andExpect(jsonPath("$.count", is(4)))
                .andExpect(jsonPath("$.cost", is(320)));

    }

    @Test
    @DisplayName("when perform on GET: /lotteries with UserId not 10 digits should return Status is BAD_REQUEST and message is User should contain number 10 digits")
    void getUserLotteryAndUserIdIdNot10Digits() throws Exception {

        UserRequest userRequest = new UserRequest();
        userRequest.setUserId("12345678901");

        List<String> lottery = new ArrayList<>();
        lottery.add("123456");
        lottery.add("234567");
        lottery.add("345678");

        UserLotteryResponse response = new UserLotteryResponse();
        response.setTickets(lottery);
        response.setCount(4);
        response.setCost(320);
        when(userService.getLottery(userRequest.getUserId()))
                .thenReturn(response);

        mockMvc.perform(
                        get("/users/"+userRequest.getUserId()+"/lotteries")
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("User should contain number 10 digits")));

    }

    @Test
    @DisplayName("when perform on GET: /lotteries with UserId not number should return Status is BAD_REQUEST and message is User should contain number 10 digits")
    void getUserLotteryAndUserIdIdNotNumber() throws Exception {

        UserRequest userRequest = new UserRequest();
        userRequest.setUserId("aaaaa");

        List<String> lottery = new ArrayList<>();
        lottery.add("123456");
        lottery.add("234567");
        lottery.add("345678");

        UserLotteryResponse response = new UserLotteryResponse();
        response.setTickets(lottery);
        response.setCount(4);
        response.setCost(320);
        when(userService.getLottery(userRequest.getUserId()))
                .thenReturn(response);

        mockMvc.perform(
                        get("/users/"+userRequest.getUserId()+"/lotteries")
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("User should contain number 10 digits")));

    }



    @Test
    @DisplayName("when perform on DELETE: /users/{userId}/lotteries/{ticketId} should return tickets now is 123456")
    void sellUserLottery() throws Exception {

        UserTicketRequest userTicketRequest = new UserTicketRequest();
        userTicketRequest.setTicketId("123456");
        userTicketRequest.setUserId("1234567890");

        LotteryResponse response = new LotteryResponse();
        response.setTicket("123456");

        when(userService.sellLottery(any(),any()))
                .thenReturn(response);

        mockMvc.perform(
                        delete("/users/"+userTicketRequest.getUserId()+"/lotteries/"+userTicketRequest.getTicketId())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticket", is(userTicketRequest.getTicketId())));

    }

    @Test
    @DisplayName("when perform on DELETE: /users/{userId}/lotteries/{ticketId} with TicketId not 6 digits should Status is BAD_REQUEST and message is Ticket should contain number 6 digits")
    void sellUserLotteryWithTicketAndTicketIdNot6Digits() throws Exception {

        UserTicketRequest userTicketRequest = new UserTicketRequest();
        userTicketRequest.setTicketId("1234567");
        userTicketRequest.setUserId("1234567890");

        LotteryResponse response = new LotteryResponse();
        response.setTicket("123456");

        when(userService.sellLottery(any(),any()))
                .thenReturn(response);

        mockMvc.perform(
                        delete("/users/"+userTicketRequest.getUserId()+"/lotteries/"+userTicketRequest.getTicketId())
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Ticket should contain number 6 digits")));

    }


    @Test
    @DisplayName("when perform on DELETE: /users/{userId}/lotteries/{ticketId} with ticketId or userId not found in database should return Status is NOT_FOUND and message is Lottery number {ticketId} in user {userId} not found")
    void sellUserLotteryWithTicketAndNotFoundTicketInDB() throws Exception {

        UserTicketRequest userTicketRequest = new UserTicketRequest();
        userTicketRequest.setTicketId("999999");
        userTicketRequest.setUserId("1234567890");

        when(userService.sellLottery(any(),any()))
                .thenThrow(new StatusNotFoundTicketException("Lottery number "+ userTicketRequest.getTicketId() +" in user "+userTicketRequest.getUserId()+" not found"));

        mockMvc.perform(
                        delete("/users/"+userTicketRequest.getUserId()+"/lotteries/"+userTicketRequest.getTicketId())
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Lottery number "+ userTicketRequest.getTicketId() +" in user "+userTicketRequest.getUserId()+" not found")));

    }

    @Test
    @DisplayName("when perform on DELETE: /users/{userId}/lotteries/{ticketId} with UserId not 10 digits should return Status is BAD_REQUEST and message is User should contain number 10 digits")
    void sellUserLotteryWithTicketAndUserIdIdNot10Digits() throws Exception {

        UserTicketRequest userTicketRequest = new UserTicketRequest();
        userTicketRequest.setTicketId("123456");
        userTicketRequest.setUserId("12345678901");

        LotteryResponse response = new LotteryResponse();
        response.setTicket("123456");

        when(userService.sellLottery(any(),any()))
                .thenReturn(response);

        mockMvc.perform(
                        delete("/users/"+userTicketRequest.getUserId()+"/lotteries/"+userTicketRequest.getTicketId())
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("User should contain number 10 digits")));

    }

    @Test
    @DisplayName("when perform on DELETE: /users/{userId}/lotteries/{ticketId} with TicketId not number should Status is BAD_REQUEST and message is Ticket should contain number 6 digits")
    void sellUserLotteryWithTicketAndTicketIdNotNumber() throws Exception {

        UserTicketRequest userTicketRequest = new UserTicketRequest();
        userTicketRequest.setTicketId("aaaaa");
        userTicketRequest.setUserId("1234567890");

        LotteryResponse response = new LotteryResponse();
        response.setTicket("123456");

        when(userService.sellLottery(any(),any()))
                .thenReturn(response);

        mockMvc.perform(
                        delete("/users/"+userTicketRequest.getUserId()+"/lotteries/"+userTicketRequest.getTicketId())
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Ticket should contain number 6 digits")));

    }

    @Test
    @DisplayName("when perform on DELETE: /users/{userId}/lotteries/{ticketId} with UserId not number should return Status is BAD_REQUEST and message is User should contain number 10 digits")
    void sellUserLotteryWithTicketAndUserIdIdNotNumber() throws Exception {

        UserTicketRequest userTicketRequest = new UserTicketRequest();
        userTicketRequest.setTicketId("123456");
        userTicketRequest.setUserId("aaaaa");

        LotteryResponse response = new LotteryResponse();
        response.setTicket("123456");

        when(userService.sellLottery(any(),any()))
                .thenReturn(response);

        mockMvc.perform(
                        delete("/users/"+userTicketRequest.getUserId()+"/lotteries/"+userTicketRequest.getTicketId())
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("User should contain number 10 digits")));

    }


}