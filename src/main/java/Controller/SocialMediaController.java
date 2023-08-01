package Controller;

import static org.mockito.ArgumentMatchers.contains;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    private MessageService mService = new MessageService();
    private AccountService aService = new AccountService();


    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */

    
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::register);
        app.post("/login",this::login);
        app.post("/messages",this::createMessage);
        app.get("/messages",this::getAllMessages);
        app.get("/messages/{message_id}", this::getMessageByID);
        app.get("/accounts/{account_id}/messages",this::getMessageByUserID);
        app.delete("/messages/{message_id}", this::deleteMessage);
        app.patch("/messages/{message_id}",this::updateMessageByMessageID);
        

        return app;
    }


    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void register(Context context) {
    
        Account account = context.bodyAsClass(Account.class);
        int passLength = account.getPassword().length();
        String uName = account.getUsername();
        Account newAccount = aService.createAccount(account);

        if(uName != null && passLength >= 4){
            context.status(200);
            context.json(newAccount);
        }{
            context.status(400);
        }
        

    }
    private void login(Context context){
        Account account = context.bodyAsClass(Account.class);
        Account accountInfo = aService.login(account.getUsername(),account.getPassword());
        if(accountInfo != null){
            context.json(accountInfo);
            context.status(200);

        }else{
            context.status(401);
        }       
        
       //TODO: Finish LoginHandler
    }
    private void createMessage(Context context){
        Message message = context.bodyAsClass(Message.class);
        int messageLength = message.message_text.length();


        if(message != null && messageLength > 255){
            Message newMessage = mService.insertMessage(message);
            context.json(newMessage);
            context.status(200);
        }else{context.status(400);}

    }
    private void deleteMessage(Context context){
        
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        mService.deleteMessageById(message_id);

        //TODO: Finish method

    }
    private void getAllMessages(Context context){
        context.json(mService.getAllMessages());
    }
    private void getMessageByID(Context context){
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        Message message = mService.getMessageById(message_id);
        if(message != null){
            context.json(message);
            context.status(200);}
        else{
            context.status(200);
        }

    }
    private void updateMessageByMessageID(Context context){
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        int count = mService.getMessageCount(message_id);
        String change = context.body();
        if (change.length() < 255 && count>0){
            Message updatedMessage = mService.updateMessage(message_id,change);
            context.json(updatedMessage);
            context.status(200);
        }else{
            context.status(400);
        }
    }

    private void getMessageByUserID(Context context){
        int message_id = Integer.parseInt(context.pathParam("account_id"));
        context.json(mService.getMessageByUserId(message_id));
        context.status(200);
    }


}