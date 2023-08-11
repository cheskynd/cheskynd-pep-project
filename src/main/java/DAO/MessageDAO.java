package DAO;
import Model.Message;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class MessageDAO {

    /**
     * Gets all of the messages that are saved in the database
     * @return A list of all Messages saved in a database in a Message object type 
     */
    public List<Message>getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        
        try {
            String sql = "SELECT * FROM message";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),rs.getInt("posted_by"),rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messages.add(message);
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    /**
     * Gets the messages of a specified user using their account id.
     * @param account_id account id of the user
     * @return A list of all user Messages saved in a database in a Message object type 
     */
    public List<Message>getMessageByUserId(int account_id){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        
        try {
            String sql = "SELECT * FROM message where posted_by = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, account_id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),rs.getInt("posted_by"),rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messages.add(message);
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }


    /**
     * Used to insert a new message into a database
     * @param message A Message type object used to extract the message an its details to be stored 
     * @return Message object type of the newly created if successuly inserted.
     */
    public Message insertMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) values (?,?,?);";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, message.getPosted_by());
            ps.setString(2,message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());

            ps.executeUpdate();
            ResultSet pkeyResultSet = ps.getGeneratedKeys();

            if(pkeyResultSet.next()){
                int generated_message_id = (int) pkeyResultSet.getLong(1);
                message.setMessage_id(generated_message_id);
                return message;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Return a message using its ID
     * @param message_id id of the message
     * @return A Message type object of tied to the ID.
     */
    public Message getMessageById(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "Select * from message where message_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, message_id);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"),rs.getLong("time_posted_epoch"));
                return message;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Delete a message from the database using its ID.
     * @param message_id ID of the message.
     */
    public void deleteMessageById(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "DELETE from message where message_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, message_id);
            int rowsAffected = ps.executeUpdate();
            System.out.println("Rows Affected: " + rowsAffected);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    } 

    /**
     * Return the numer of messages a specific user has
     * @param account_id account id of the user.
     * @return Number of messages a user has.
     */
    public int getPostedByCount(int account_id){
        Connection connection = ConnectionUtil.getConnection();
        int count = 0;
        try{
            String sql = "SELECT * FROM message where posted_by = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, account_id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                count++;
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return count;

    }

    /**
     * Updates an existing message in the database 
     * @param id message id of the message in question.
     * @param newMessage The new message replacement
     * @return A Message type object of the updated message.
     */
    public Message updateMessageById(int id, String newMessage){

        Connection connection = ConnectionUtil.getConnection();
        try {
                String sql = "SELECT * FROM message where message_id = ?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()){
                    String newSql = "Update message set message_text = ? where message_id = ?";
                    PreparedStatement newps = connection.prepareStatement(newSql);
                    newps.setString(1, newMessage);
                    newps.setInt(2, id);
                    int rowsAffected = newps.executeUpdate();

                    if (rowsAffected >= 1){

                        int message_id = rs.getInt("message_id");
                        int posted_by = rs.getInt("posted_by");
                        long time_posted_epoch = rs.getLong("time_posted_epoch");
                        
                        Message updatedMesaged = new Message();
                        updatedMesaged.setMessage_id(message_id);
                        updatedMesaged.setMessage_text(newMessage);
                        updatedMesaged.setPosted_by(posted_by);
                        updatedMesaged.setTime_posted_epoch(time_posted_epoch);
                        return updatedMesaged;
                }
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    } 
}
