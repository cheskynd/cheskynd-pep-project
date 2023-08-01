package Controller;

import Model.Account;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
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
        // Account newAccount = 

        //TODO: Finish RegisterHandler
        context.json("sample text");
    }
    private void login(Context context){
       //TODO: Finish LoginHandler
    }
    private void createMessage(Context context){
        //TODO: Finish the CreateMessage
    }
    private void deleteMessage(Context context){
        //TODO: Finish method

    }
    private void getAllMessages(Context context){
        //TODO: Finish the getAllMessage
    }
    private void getMessageByID(Context context){

    }
    private void updateMessageByMessageID(Context context){

    }

    private void getMessageByUserID(Context context){

    }


}