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


@WebServlet("/project-search")
public class ProjectSearchServlet extends HttpServlet {
String enteredText = "";


//Implement a search feature
public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

            //Now I search through Datastore and willcheck if the project description or name has the words searched for if it does 
            //will save it
         Query searchQuery = new Query("Project").addSort("userName",SortDirection.DESCENDING)
         PreparedQuery searchresults = datastore.prepare(searchquery);
            ArrayList<HashMap<String,String>> projectsReturned = new ArrayList<>();

for(Entity entity: searchresults.asIterable()) {
        String projectDescription = (String)
}
            //


}

//I was thinking that this should parse what was entered in the text box?
public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    //Now will get string from search Box
   enteredText = getParameter(request, "searchfield", "");

 

    //now store it,no actually query the database usign the entered text



}
}

