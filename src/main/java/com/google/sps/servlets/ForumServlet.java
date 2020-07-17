package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import com.google.gson.Gson;
import java.io.PrintWriter;
import com.google.sps.data.Task;


/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class ForumServlet extends HttpServlet {
   

    static String CommentsKey = "cmt";

    //initialize datastore
   private  DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // response.setContentType("text/html;");
    // response.getWriter().println("<h1>Hello Barry!</h1>");

    response.setContentType("application/json");

    Gson gson = new Gson();

//  String json = gson.toJson(facts);
//  response.getWriter().println(json);

ArrayList<Task> entities = new ArrayList<>();
//code for comments form:
  Query query = new Query("Task").addSort("cmt", SortDirection.DESCENDING);
        PreparedQuery results = datastore.prepare(query);
        for (Entity entity : results.asIterable()) {
            String comments = (String) entity.getProperty(CommentsKey);

            entities.add(new Task(comments));
            }
            String json = gson.toJson(entities);
            response.getWriter().println(entities);

}

public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
      // read form fields

        String comments = request.getParameter("comments-input");
        System.out.println("Comments: " + comments);
        // do some processing here...
        // get response writer
        PrintWriter writer = response.getWriter();  
        // build HTML code
        String htmlRespone = "<html>";
        htmlRespone += "<h2> Comment is: " + comments + "<h2>"; 
        htmlRespone += "</html>"; 
        // return response
        writer.println(htmlRespone);


        // here starts the code for datastoring
        Entity taskEntity = new Entity("Task");
        taskEntity.setProperty( CommentsKey, comments);      
       
        datastore.put(taskEntity);
        response.sendRedirect("/forum.html");

            
        }


        //  //here starts the code for authentication:

        // UserService userService = UserServiceFactory.getUserService();
        // if (userService.isUserLoggedIn()) {
        //     String userEmail = userService.getCurrentUser().getEmail();
        //     String urlToRedirectToAfterUserLogsOut = "/";
        //     String logoutUrl = userService.createLogoutURL(urlToRedirectToAfterUserLogsOut);

        // response.getWriter().println("<p>Hello " + userEmail + "!</p>");
        // response.getWriter().println("<p>Logout <a href=\"" + logoutUrl + "\">here</a>.</p>");
        // }else {
        //     String urlToRedirectToAfterUserLogsIn = "/";
        //     String loginUrl = userService.createLoginURL(urlToRedirectToAfterUserLogsIn);

        //     response.getWriter().println("<p>Hello stranger.</p>");
        //     response.getWriter().println("<p>Login <a href=\"" + loginUrl + "\">here</a>.</p>");
        // }
        // //associating data:
        // String email = userService.getCurrentUser().getEmail();
        // messageEntity.setProperty("email", email);
         
    }


