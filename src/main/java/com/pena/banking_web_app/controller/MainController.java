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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/")
    public String home() {
        return "redirect:/login";
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
    @GetMapping("/signup")
    String signupPage(){
        return "signup";
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
    public String depositCash(@RequestParam Double amount,
                              RedirectAttributes redirectAttributes){
        if (amount<0){
            redirectAttributes.addFlashAttribute("error", "Cannot deposit less than 0.");
            return "redirect:/dashboard";
        }
        if(accountService.transact(amount,account)){
            transactionService.updateAmount(user, amount,"Deposit", user.getNumber(), user.getNumber());
        }

        redirectAttributes.addFlashAttribute("success", "Successfully deposited "+ amount+ " into your account!");
        return "redirect:/dashboard";
    }
    @PostMapping(path = "/withdraw")
    public String withdrawCash(@RequestParam Double amount,
                               RedirectAttributes redirectAttributes){
        if(amount > account.getBalance()){
            redirectAttributes.addFlashAttribute("error", "Insufficient balance.");
            return  "redirect:/dashboard";
        }
        if(accountService.transact(-amount,account)){
            transactionService.updateAmount(user, -amount,"Withdraw", user.getNumber(), user.getNumber());
        }
        redirectAttributes.addFlashAttribute("success", "Withdrawal successful!");
        return "redirect:/dashboard";

    }
    @PostMapping(path = "/transfer")
    public String transfer(@RequestParam Double amount, @RequestParam String to_number,
                           RedirectAttributes redirectAttributes){
        User recipient = userService.findByNumber(to_number);
        Account recipientAcc = accountService.findByUser(recipient);
        if(recipient == null){
            redirectAttributes.addFlashAttribute("error", "Recipient not found.");
            return "redirect:/dashboard";
        }
        if (to_number.equalsIgnoreCase(user.getNumber())) {
            redirectAttributes.addFlashAttribute("error", "You cannot transfer to yourself.");
            return "redirect:/dashboard";
        }
        if(account.getBalance()< amount){
            redirectAttributes.addFlashAttribute("error", "Transfer failed. Insufficient balance or error occurred.");
            return "redirect:/dashboard";
        }
        if(accountService.transact(-amount, account) && accountService.transact(amount,recipientAcc)){
            transactionService.updateAmount(user,-amount,"Transfer",to_number, user.getNumber());
            transactionService.updateAmount(recipient,amount,"Receive", user.getNumber(),to_number);
        }
        redirectAttributes.addFlashAttribute("success", "Transfer successful!");
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
        user = userService.login(number, pin);
        if(user == null){
            model.addAttribute("error", true);
            return "login";
        }
        return "redirect:/dashboard";
    }

    @PostMapping("/signup")
    public String signup(@RequestParam String name,
                         @RequestParam String number,
                         @RequestParam String email,
                         @RequestParam String pin,
                         @RequestParam String confirmPin,
                         RedirectAttributes redirectAttributes) {
        if (!pin.equals(confirmPin)) {
            redirectAttributes.addFlashAttribute("error", "PIN does not match");
            return "redirect:/signup";
        }

        if(userService.findByUsername(name) != null || userService.findByNumber(number) != null){
            redirectAttributes.addFlashAttribute("error", "User already exists.");
            return "redirect:/signup";
        }
        User userCreated = userService.createUser(name, number, email, pin);
        accountService.createAccount(userCreated);
        redirectAttributes.addFlashAttribute("success", "Account created successfully!");
        return "redirect:/login";
    }
}
