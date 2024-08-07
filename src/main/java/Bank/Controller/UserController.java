package Bank.Controller;

import Bank.Model.User;
import Bank.Service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class UserController {
    UserService userService;
    Javalin app;

    public UserController(UserService userService, Javalin app) {
        this.userService = userService;
        this.app = app;

        //endpoints here
        app.post("/register", this::postNewUser);   //done
        app.get("/user", this::getLogin);
        app.put("/user/{userId}", this::putInfo);   //done

    }

    //registerUser
    public void postNewUser(Context ctx) {
        User newUser = userService.registerUser(ctx.bodyAsClass(User.class));
        ctx.json(newUser);
    }

    //processLogin
    public void getLogin(Context ctx) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        User user = om.readValue(ctx.body(), User.class);
        User testAccount = userService.processLogin(user.getUsername(), user.getPassword());
        if(testAccount != null){
            ctx.json(testAccount).result("Login successful");
        } else {
            ctx.status(401).result("invalid login");
        }
    } //tried different logic

    //changeUserInfo
    public void putInfo(Context ctx){
        int userId = Integer.parseInt(ctx.pathParam("userId"));
        User user = ctx.bodyAsClass(User.class);    //want to pull from the json body and use User.class to
        user.setUserId(userId);
        userService.changeUserInfo(user);
        ctx.json(user);
        ctx.status(200);
    }

    //end class
}
