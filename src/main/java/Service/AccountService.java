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

    public Account createAccount(String userName, String password){
        return accountDAO.insertAccount(userName,password);

    }

    public boolean accountExists(String userName){
        List<Account> accounts= accountDAO.getAllAccounts(); 
        for(Account account:accounts){

            if(account.getUsername().equals(userName)){
                return true;
            } 
        }return false;

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
