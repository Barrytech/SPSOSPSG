// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

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
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
 


/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {
   
    private ArrayList<String> facts;
    public void init() {
        facts = new ArrayList<String>();

      facts.add(" I was born and raised in Guinea, West-Africa.");
      facts.add(" I have never had McDonal's.");
      facts.add(" I will probably graduate in my junior year.");
    }


    static String CommentsKey = "com";

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
  Query query = new Query("Task").addSort("firstname", SortDirection.DESCENDING);
        PreparedQuery results = datastore.prepare(query);
        for (Entity entity : results.asIterable()) {

            String comments = (String) entity.getProperty(CommentsKey);

            entities.add(new Task( comments));
            }
            String json = gson.toJson(entities);
            response.getWriter().println(entities);



            //authentication code:
            response.setContentType("text/html;");
    PrintWriter out = response.getWriter();
    out.println("<h1>Shoutbox</h1>");

    // Only logged-in users can see the form
    UserService userService = UserServiceFactory.getUserService();
    if (userService.isUserLoggedIn()) {
      out.println("<p>Hello " + userService.getCurrentUser().getEmail() + "!</p>");
      out.println("<p>Type a message and click submit:</p>");
      out.println("<form method=\"POST\" action=\"/shoutbox\">");
      out.println("<textarea name=\"text\"></textarea>");
      out.println("<br/>");
      out.println("<button>Submit</button>");
      out.println("</form>");
    } else {
      String loginUrl = userService.createLoginURL("/shoutbox");
      out.println("<p>Login <a href=\"" + loginUrl + "\">here</a>.</p>");
    }

    // Everybody can see the messages
    out.println("<ul>");
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    // Query query = new Query("Message").addSort("timestamp", SortDirection.DESCENDING);
    // PreparedQuery results = datastore.prepare(query);
    for (Entity entity : results.asIterable()) {
      String text = (String) entity.getProperty("text");
      String email = (String) entity.getProperty("email");
      out.println("<li>" + email + ": " + text + "</li>");
    }
    out.println("</ul>");
  }


 public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
      // read form fields
        String firstname = request.getParameter("first-name-field");
        String lastname = request.getParameter("last-name-field");
        String email = request.getParameter("email-field");
        String comments = request.getParameter("feedback-field");
         
        System.out.println("First name: " + firstname);
        System.out.println("Last name: " + lastname);
        System.out.println("Email: " + email);
        System.out.println("Comments: " + comments);
        // do some processing here...
        // get response writer
        PrintWriter writer = response.getWriter();  
        // build HTML code
        String htmlRespone = "<html>";
     
        htmlRespone += " Comment is: " + comments + "<h2>"; 
        htmlRespone += "</html>"; 
        // return response
        writer.println(htmlRespone);

 
 // here starts the code for datastoring
        Entity taskEntity = new Entity("Task");
        taskEntity.setProperty( CommentsKey, comments);      
       
        datastore.put(taskEntity);
        response.sendRedirect("/index.html");


        //here starts the code for authentication:
        UserService userService = UserServiceFactory.getUserService();

    // Only logged-in users can post messages
        if (!userService.isUserLoggedIn()) {
        response.sendRedirect("/index.html");
        return;
        }
        String text = request.getParameter("text");
        String imail = userService.getCurrentUser().getEmail();

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Entity messageEntity = new Entity("Message");
        messageEntity.setProperty("text", text);
        messageEntity.setProperty("timestamp", System.currentTimeMillis());
        datastore.put(messageEntity);

        // Redirect to /shoutbox. The request will be routed to the doGet() function above.
        response.sendRedirect("/index.html");
  }
         
    }




 
 








 // this would be another method to explore on how to do it

//     // Get the input from the form.
//     String text = getParameter(request, "text-input", "");
//     boolean upperCase = Boolean.parseBoolean(getParameter(request, "upper-case", "false"));
//     boolean sort = Boolean.parseBoolean(getParameter(request, "sort", "false"));

//     // Convert the text to upper case.
//     if (upperCase) {
//       text = text.toUpperCase();
//     }

//     // Break the text into individual words.
//     String[] words = text.split("\\s*,\\s*");

//     // Sort the words.
//     if (sort) {
//       Arrays.sort(words);
//     }

//     // Respond with the result.
//     response.setContentType("text/html;");
//     response.getWriter().println(Arrays.toString(words));
//   }
//    private String getParameter(HttpServletRequest request, String name, String defaultValue) {
//     String value = request.getParameter(name);
//     if (value == null) {
//       return defaultValue;
//     }
//     return value;


