package com.pena.banking_web_app.controller;

import com.pena.banking_web_app.model.Account;
import com.pena.banking_web_app.model.Transaction;
import com.pena.banking_web_app.model.User;

import com.pena.banking_web_app.service.AccountService;
import com.pena.banking_web_app.service.TransactionService;
import com.pena.banking_web_app.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainController {

    private final UserService userService;
    private final AccountService accountService;
    private final TransactionService transactionService;

    private User user;
    private Account account;



    MainController(UserService userService, AccountService accountService, TransactionService transactionService){
        this.userService = userService;
        this.accountService = accountService;
        this.transactionService = transactionService;
    }


@GetMapping(path = "/test")
@ResponseBody
String test(){
        user = userService.findByNumber("0987654321");
        return user.getUsername();
}
@GetMapping("/login")
String loginPage(){
        return "login";
}
    @GetMapping(path = "/test/reset")
    @ResponseBody
    String testReset(){
        user = null;
        return user.getNumber();
    }
    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        account = accountService.findByUser(user);
        List<Transaction> transactions = transactionService.findByAccount(user);

        model.addAttribute("user", user);
        model.addAttribute("account", account);
        model.addAttribute("transactions", transactions);

        return "dashboard";
    }
    @PostMapping(path = "/deposit")
    public String depositCash(@RequestParam Double amount){
        if(accountService.transact(amount,account)){
            transactionService.updateAmount(user, amount,"Deposit", user.getNumber(), user.getNumber());
        }

        return "redirect:/dashboard";
    }
    @PostMapping(path = "/withdraw")
    public String withdrawCash(@RequestParam Double amount){
        if(accountService.transact(-amount,account)){
            transactionService.updateAmount(user, -amount,"Withdraw", user.getNumber(), user.getNumber());
        }
        return "redirect:/dashboard";

    }
    @PostMapping(path = "/transfer")
    public String transfer(@RequestParam Double amount, @RequestParam String to_number){
        User recipient = userService.findByNumber(to_number);
        Account recipientAcc = accountService.findByUser(recipient);
        if(!to_number.equalsIgnoreCase(user.getNumber())
                && recipient!= null && accountService.transact(-amount, account)
                && accountService.transact(amount,recipientAcc)){
            transactionService.updateAmount(user,-amount,"Transfer",to_number, user.getNumber());
            transactionService.updateAmount(recipient,amount,"Receive", user.getNumber(),to_number);
        }
        return "redirect:/dashboard";
    }

    @GetMapping(path = "/logout")
    String logout(){
        user=null;
        account =null;
        return "login";
    }

    @PostMapping(path = "/login")
    String login(@RequestParam String number,  @RequestParam String pin,  Model model){
        System.out.println(number);
        user = userService.findByNumber(number);

        if(user != null && pin.equalsIgnoreCase(user.getPin())){

            return "redirect:/dashboard";
        }
        model.addAttribute("error", true);
        return "login";
    }
}
