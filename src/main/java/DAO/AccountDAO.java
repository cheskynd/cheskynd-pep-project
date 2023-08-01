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

    /*
     * create table account (
    account_id int primary key auto_increment,
    username varchar(255) unique,
    password varchar(255)
        );
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

    public Account insertAccount(String userName, String password){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO account (username, password) values ?,?;";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, userName);
            ps.setString(2, password);

            ps.executeUpdate();
            ResultSet pkeyResultSet = ps.getGeneratedKeys();

            if(pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id, userName, password);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
}
