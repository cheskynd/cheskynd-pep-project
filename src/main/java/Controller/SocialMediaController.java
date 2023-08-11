package Controller;

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
    private MessageService messageService = new MessageService();
    private AccountService accountService = new AccountService();


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
     * this handler is used to register an account
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void register(Context context) {
    
        Account account = context.bodyAsClass(Account.class);
        String password = account.getPassword();
        String username = account.getUsername();

        if (username == null || username.isBlank() || password.length() < 4){
            context.status(400);
            return;
        }

        boolean exists = accountService.usernameExist(username);
        if(exists){
            context.status(400);
            return;
        }


        Account newAccount = accountService.createAccount(username,password);
        if (newAccount != null){
                context.status(200);
                context.json(newAccount);
            }else {
                context.status(400);
            }
    }

    /**
     * This handler is needed to allow a user to login
     * @param context
     */
    private void login(Context context){
        Account account = context.bodyAsClass(Account.class);
        Account accountInfo = accountService.login(account.getUsername(),account.getPassword());
        if(accountInfo != null){
            context.json(accountInfo);
            context.status(200);

        }else{
            context.status(401);
        }       
    }

    /**
     * This handler is used to create messages
     * @param context
     */
    private void createMessage(Context context){
        Message message = context.bodyAsClass(Message.class);

        if(message != null && message.getMessage_text().length() < 255 && !message.getMessage_text().isBlank()){

            int postedByCount = messageService.getPostedByCount(message.getPosted_by());
            if(postedByCount > 0){
                Message newMessage = messageService.insertMessage(message);
                if(newMessage != null){
                    context.json(newMessage);
                    context.status(200);}
                else{context.status(400);}
            }else{context.status(400);}
        }else{context.status(400);}
    }
    
    private void deleteMessage(Context context){
        
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.getMessageById(message_id);
        if(message!=null){
            messageService.deleteMessageById(message_id);
            context.json(message);
            context.status(200);}
        else{
            context.status(200);
        }

    }

    private void getAllMessages(Context context){
        context.json(messageService.getAllMessages());
    }

    private void getMessageByID(Context context){
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.getMessageById(message_id);
        if(message != null){
            context.json(message);
            context.status(200);}
        else{
            context.status(200);
        }

    }


    private void updateMessageByMessageID(Context context){
         int message_id = Integer.parseInt(context.pathParam("message_id"));
        Message existingMessage = messageService.getMessageById(message_id); 
        if (existingMessage != null) { 
            Message Nmessage = context.bodyAsClass(Message.class); 
            String newMessageText = Nmessage.getMessage_text(); 
            if (!newMessageText.isBlank() && newMessageText.length() < 255) { 
                Message updatedMessage = messageService.updateMessage(message_id, newMessageText); 
                if (updatedMessage != null) { context.json(updatedMessage);
                     context.status(200); } 
                    else { context.status(400); } 
                } else { context.status(400); }

            } 
                else { context.status(400); } 
            }

    private void getMessageByUserID(Context context){
        int account_id = Integer.parseInt(context.pathParam("account_id"));
        context.json(messageService.getMessageByUserId(account_id));
        context.status(200);
    }


}