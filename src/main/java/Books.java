
import spark.Spark;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Leo on 2016/11/11.
 */
public class Books {


    private static Map<String,Book>books =new HashMap<String,Book>();

    public static void main(String[] args){
        final Random random=new Random();
        Spark.post("/books",(req,res)->
                {   //postman 测试时要选定为x-www-urlencoded
                    String author=req.queryParams("author");
                    String title=req.queryParams("title");
                    Book book=new Book(author,title);
                    int id=random.nextInt(Integer.MAX_VALUE);
                    books.put(String.valueOf(id),book);
                    res.status(201);
                    return id;
                }
        );
        Spark.get("/books/:id",(req,res)->
                {
                    Book book=books.get(req.params(":id"));
                    if (book!=null){
                        return "title:"+book.getTitle()+",Author:"+book.getAuthor();
                    }else {
                        res.status(404);
                        return "book not found";
                    }
                }
        );
        Spark.put("/books/:id",(request, response) ->{
            String id=request.params(":id");
            Book book=books.get(id);
            if (book!=null){
                String newAuthor=request.queryParams("author");
                String newTitle=request.queryParams("title");
                if (newAuthor!=null){               //输入时应限制为空字符串不符合
                    book.setAuthor(newAuthor);
                }else {
                    response.status(404);
                    return "No NewAuthor!";
                }
                if (newTitle!=null){                //输入时应限制为空字符串不符合
                    book.setTitle(newTitle);
                }else {
                    response.status(404);
                    return "No NewTitle!";
                }
                return "Book with id "+id+" updated";
            }else {
                response.status(404);
                return "update fail";
            }
        } );

        Spark.delete("/books/:id",(request, response) -> {
            String id=request.params(":id");
            Book book=books.remove(id);
            if (book!=null){
                response.status(201);
                return "Book with id："+id+" is deleted!";
            }else {
                response.status(404);
                return "Book Not Found!";
            }
        });
        Spark.get("/books",(request, response) -> {
            String ids="";
            for(String id:books.keySet()){
                ids+=id+"  ";
            }
            return  ids;
        });

    }

    public static class Book {
        public String author,title;

        public Book(String author, String title) {
            this.author = author;
            this.title = title;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
