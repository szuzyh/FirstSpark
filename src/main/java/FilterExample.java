import spark.Spark;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;

/**
 * Created by Leo on 2016/11/11.
 */
public class FilterExample {

    private static Map<String,String>userNameAndPasswords=new HashMap<String,String>();
    public static void main(String[] args){
        userNameAndPasswords.put("foo","bar");
        userNameAndPasswords.put("admin","admin");
        Spark.before((request, response) -> {
            String user=request.queryParams("user");
            String password=request.queryParams("password");
            String dbPassword=userNameAndPasswords.get(user);
            if (!(password!=null&&password.equals(dbPassword))){
                Spark.halt(404,"不对哦！");
            }else {
                Spark.halt(201,"正确");
            }
        });
        Spark.before("/Ace",(request, response) ->
        response.header("Foo","Set by second b efore filter"));
        Spark.get("/hello", (request, response) -> "Hello World!");

        Spark.after("/hello", (request, response) -> response.header("spark", "added by after-filter"));
    }
}
