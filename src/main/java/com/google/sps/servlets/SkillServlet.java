package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import java.io.PrintWriter;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/** On a GET request to the URL, /skill-data, fetch user skill data.*/
@WebServlet("/skill-data")
public class SkillServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    UserService userService = UserServiceFactory.getUserService();
    if (userService.isUserLoggedIn()) {
        String id = userService.getCurrentUser().getUserId();

        Query query = new Query("SkillData").setFilter(new Query.FilterPredicate("id", Query.FilterOperator.EQUAL, id));

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        PreparedQuery results = datastore.prepare(query);
        Entity entity = results.asSingleEntity(); // A single entity as you only want the currently logged in user's info

        HashMap<String, String> info = new HashMap<String, String>();
        
        //long id = entity.getKey().getId();
        String school = (String) entity.getProperty("school");
        String degree = (String) entity.getProperty("degree");
        String major = (String) entity.getProperty("major");
        String skill = (String) entity.getProperty("skill");
        String experience = (String) entity.getProperty("experience");

        info.put("school", school);
        info.put("degree", degree);
        info.put("major", major);
        info.put("skill", skill);
        info.put("experience", experience);

        Gson gson = new Gson();

        // Send the JSON as the response
        response.setContentType("application/json;");
        response.getWriter().println(gson.toJson(info));
    }
    
  }
  /**
  * When the user clicks the Submit button, the form sends a POST request to the URL specified in the form's action attribute. 
  * The server looks for a servlet that maps to that URL, and then runs its doPost() function. Data sent via POST is stored in DataService.
  * In Order to send this POST request to /skill-data URL, user must be logged in. (NOT YET IMPLEMENTED)
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
    String school = request.getParameter("school");
    String degree = request.getParameter("degree");
    String major = request.getParameter("major");
    String skill = request.getParameter("skill");
    String experience = request.getParameter("experience");

    //String userEmail = userService.getCurrentUser().getEmail();
    String id = userService.getCurrentUser().getUserId();

    Entity SkillEntity = new Entity("SkillData", id);
    SkillEntity.setProperty("school", school);
    SkillEntity.setProperty("degree", degree);
    SkillEntity.setProperty("major", major);
    SkillEntity.setProperty("skill", skill);
    SkillEntity.setProperty("experience", experience);
    SkillEntity.setProperty("id", id);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    // The put() function automatically inserts new data or updates existing data based on ID
    datastore.put(SkillEntity);

    // Redirect client back to the HTML page (i.e. Home page)
    response.sendRedirect("/userInfo/userProfile.html");
  }

}
