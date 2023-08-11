package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {

    /**
     * 
     * @return List of all accounts 
     */
    public List<Account>getAllAccounts(){
        Connection connection = ConnectionUtil.getConnection();
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM account";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"),rs.getString("username"),rs.getString("password"));
                accounts.add(account);
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return accounts;
    }
    /**
     * 
     * @param userName The username for a new account
     * @param password The password for a new account
     * @return Returns a new account
     */
    public Account insertAccount(String userName, String password){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO account (username, password) values (?,?);";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, userName);
            ps.setString(2, password);

            ps.executeUpdate();
            ResultSet pkeyResultSet = ps.getGeneratedKeys();

            if(pkeyResultSet.next()){
                int generated_account_id = pkeyResultSet.getInt(1);
                return new Account(generated_account_id, userName, password);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    
    public boolean accountExists(String userName){
        Connection connection = ConnectionUtil.getConnection();
        String sql = "SELECT * FROM account WHERE username = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,userName);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * 
     */
    public boolean accountExists(int account_id){
        Connection connection = ConnectionUtil.getConnection();
        String sql = "SELECT * FROM account WHERE account_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,account_id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
