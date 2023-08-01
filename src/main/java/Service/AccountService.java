package Service;
import java.util.List;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
        this.accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public Account createAccount(Account account){
        return accountDAO.insertAccount(account);
    }
    public Account login(String userName, String password){
        List<Account> accounts= accountDAO.getAllAccounts(); 
        for(Account account:accounts){
            if(account.getPassword().equals(password) && account.getUsername().equals(userName)){
                return account;
            }
        }
        return null;

    }


    
}
