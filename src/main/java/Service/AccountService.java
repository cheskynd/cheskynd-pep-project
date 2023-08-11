package Service;
import java.util.List;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    /**
     * Creates a new AccountService object having a default AccountDAO object that is used to interact with the database.
     */
    public AccountService(){
        this.accountDAO = new AccountDAO();
    }

    
    /**
     * Creates a new AccountService object with an given AccountDAO object that is used to interact with the database.
     * @param accountDAO an AccountDAO object 
     */
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }


    /**
     * This method is used to create a new accout
     * @param userName username for the new account
     * @param password password for the new account
     * @return a new account
     */
    public Account createAccount(String userName, String password){
        return accountDAO.insertAccount(userName,password);

    }


    /**
     *  This method is used in logging to an account
     * @param userName The username for a new account
     * @param password The password for a new account
     * @return a new account
     */
    public Account login(String userName, String password){
        List<Account> accounts= accountDAO.getAllAccounts(); 
        for(Account account:accounts){
            if(account.getPassword().equals(password) && account.getUsername().equals(userName)){
                return account;
            }
        }
        return null;

    }


    /**
     *checks an account with the given username exists
     * @param userName username to be checked of its existance in the database.
     * @return true/false 
     */
    public boolean usernameExist(String userName){
        return accountDAO.accountExists(userName);
    }
}
