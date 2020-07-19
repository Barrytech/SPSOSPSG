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

import java.util.HashMap;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

// When a GET request is sent to the URL /login-status, fetch info to determine if user is logged in
@WebServlet("/login-status")
public class LoginServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    UserService userService = UserServiceFactory.getUserService();
    HashMap<String, String> info = new HashMap<String, String>();
    if (userService.isUserLoggedIn()) {
      String userEmail = userService.getCurrentUser().getEmail();
      String urlToRedirectToAfterUserLogsOut = "/index.html";  //maybe change later: /userInfo/userProfile.html
      String logoutUrl = userService.createLogoutURL(urlToRedirectToAfterUserLogsOut);
      info.put("status", "true");
      info.put("url", logoutUrl);
      info.put("email", userEmail);
    } else {
      String urlToRedirectToAfterUserLogsIn = "/index.html";
      String loginUrl = userService.createLoginURL(urlToRedirectToAfterUserLogsIn);
      info.put("status", "false");
      info.put("url", loginUrl);
      info.put("email", "NONE");
    }
    
    Gson gson = new Gson();

    // Send the JSON as the response
    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(info));
  }
}