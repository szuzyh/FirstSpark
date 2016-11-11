import spark.Spark;

/**
 * Created by Leo on 2016/11/11.
 */
public class SimpleExample {


    public static void main(String[] agrs){

        Spark.get("/hello",(req,res)->"Hei");

        Spark.post("/hello",(req,res)->"hahah"+req.body());

        Spark.get("/private",(req,res)->{
        res.status(401);
        return "Go Away!";});

        Spark.get("/users/:name", (request, response) -> "Selected user: " + request.params(":name"));

        Spark.get("/news/:section",(req,res)->
        {
            res.type("text.xml");
            return "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><news>"+req.params("section")+"</news>";
        });

        Spark.get("/protected",(req,res)->
        {
            Spark.halt(403,"I dont think so!");
            return null;
        });
        Spark.get("/redirect",(req,res)->
        {
            res.redirect("/news/world");
            return null;
        });

        Spark.get("/",(req,res)->"root");
    }

}
