package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/** On a GET request to the URL, /profile-data, fetch user profile data.*/
@WebServlet("/profile-data")
public class ProfileServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    UserService userService = UserServiceFactory.getUserService();
    if (userService.isUserLoggedIn()) {
        String id = userService.getCurrentUser().getUserId();

        Query query = new Query("ProfileData").setFilter(new Query.FilterPredicate("id", Query.FilterOperator.EQUAL, id));

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        PreparedQuery results = datastore.prepare(query);
        Entity entity = results.asSingleEntity(); // A single entity as you only want the currently logged in user's info

        HashMap<String, String> info = new HashMap<String, String>();
        
        //long id = entity.getKey().getId();
        String firstname = (String) entity.getProperty("firstname");
        String lastname = (String) entity.getProperty("lastname");
        String username = (String) entity.getProperty("username");
        String birthday = (String) entity.getProperty("birthday");
        String email = (String) entity.getProperty("email");
        
        info.put("firstname", firstname);
        info.put("lastname", lastname);
        info.put("username", username);
        info.put("birthday", birthday);
        info.put("email", email);

        Gson gson = new Gson();

        // Send the JSON as the response
        response.setContentType("application/json;");
        response.getWriter().println(gson.toJson(info));
    }
    
  }
  /**
  * When the user clicks the Submit button, the form sends a POST request to the URL specified in the form's action attribute. 
  * The server looks for a servlet that maps to that URL, and then runs its doPost() function. Data sent via POST is stored in DataService.
  * In Order to send this POST request to /profile-data URL, user must be logged in. (NOT YET IMPLEMENTED)
  */
    @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    UserService userService = UserServiceFactory.getUserService();
    if (!userService.isUserLoggedIn()) {
      String urlToRedirectToAfterUserLogsIn = "/userInfo/userProfile.html"; // later change maybe to home page ?
      String loginUrl = userService.createLoginURL(urlToRedirectToAfterUserLogsIn);
      response.sendRedirect(loginUrl);
      return;
    }

    // Get the attributes from the form
    String firstname = request.getParameter("firstname");
    String lastname = request.getParameter("lastname");
    String username = request.getParameter("username");
    String birthday = request.getParameter("birthday");
    String email = request.getParameter("email");

    //String userEmail = userService.getCurrentUser().getEmail();
    String id = userService.getCurrentUser().getUserId();

    Entity ProfileEntity = new Entity("ProfileData", id);
    ProfileEntity.setProperty("firstname", firstname);
    ProfileEntity.setProperty("lastname", lastname);
    ProfileEntity.setProperty("username", username);
    ProfileEntity.setProperty("birthday", birthday);
    ProfileEntity.setProperty("email", email);
    ProfileEntity.setProperty("id", id);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    // The put() function automatically inserts new data or updates existing data based on ID
    datastore.put(ProfileEntity);

    // Redirect client back to the HTML page (i.e. Home page)
    response.sendRedirect("/userInfo/userProfile.html");
  }

}