import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        Spark.get("/Hello", (req,res)->"Hello ACE!");

    }
}