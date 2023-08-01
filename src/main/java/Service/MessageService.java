package Service;
import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {

    private MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(Message message){
        this.messageDAO = messageDAO;
    }

    public List<Message> getMessageByUserId(int id){
        return messageDAO.getMessageByUserId(id);
    }


    public Message insertMessage(Message message){
        return messageDAO.insertMessage(message);
    }


    public Message updateMessage(Message message){
        return messageDAO.updateMessageById(message.getMessage_id(), message.getMessage_text());
    }

    public Message getMessageById(int id){
        return messageDAO.getMessageById(id);
    }


    public void deleteMessageById(int id){
        messageDAO.deleteMessageById(id);
    }



    
}
