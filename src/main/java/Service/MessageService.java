package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {

    private MessageDAO messageDAO;

    /**
     * Creates a new MessageService object having a default MessageDAO object that is used to interact with the database.
     */
    public MessageService(){
        messageDAO = new MessageDAO();
    }

    /**
     * Creates a new MessageService object with an given MessageDAO object that is used to interact with the database.
     * @param messageDAO an MessageDAO object
     */
    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    /**
     * returns a list Message object tied to a specied user_id
     * @param user_id the user id of a user whose message are needed to be retrieved.
     * @return a list of Message objects by user_id
     */
    public List<Message> getMessageByUserId(int user_id){
        return messageDAO.getMessageByUserId(user_id);
    }

    /**
     * Used to insert new message into the database through MessageDAO
     * @param message the message object needed to be stored.
     * @return the newly created message
     */
    public Message insertMessage(Message message){
        return messageDAO.insertMessage(message);
    }

    /**
     * Used to update an existing message 
     * @param message_id Message id of the message that needs an update
     * @param message the new message 
     * @return a Message object of the updated messaged
     */
    public Message updateMessage(int message_id, String message){
        return messageDAO.updateMessageById(message_id, message);
    }
  
    /**
     * Gets a message by the message id
     * @param message_id message id of the message needed.
     * @return a Message object tied to the message_id.
     */
    public Message getMessageById(int message_id){
        return messageDAO.getMessageById(message_id);
    }

    /**
     * Deletes a message from the database by using its id.
     * @param id
     */
    public void deleteMessageById(int id){
        messageDAO.deleteMessageById(id);
    }

    /**
     * Get all of the messages in the database
     * @return a List of Message object types
     */
    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    /**
     * Returns a numbers of message tied to an account id
     * @param account_id account id of user whose number of messages are needed.
     * @return number of message tied to an account id
     */
    public int getPostedByCount(int account_id){
        return messageDAO.getPostedByCount(account_id);
    }
    
}
