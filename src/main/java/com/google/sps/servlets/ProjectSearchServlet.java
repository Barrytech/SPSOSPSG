package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import com.google.gson.Gson;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

import java.util.HashMap;
import java.util.ArrayList;
import java.lang.String;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;


@WebServlet("/project-search")
public class ProjectSearchServlet extends HttpServlet {
String enteredText = "";


public void setEnteredText(String text){
    enteredText = text;
}

public String getEnteredText() {
    return enteredText;
}
//Implement a search feature 
@Override
public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

            //Now I search through Datastore and willcheck if the project description or name has the words searched for if it does 
            
         Query searchQuery = new Query("Project"); //Searching throug datastore for all types project
         PreparedQuery searchresults = datastore.prepare(searchQuery);  
            ArrayList<HashMap<String,String>> projectsReturned = new ArrayList<>();

for(Entity entity: searchresults.asIterable()) {
       String projectDescription = (String) entity.getProperty("projectDescription");
       String projectTitle  = (String) entity.getProperty("projectName");
       

        //now if the projectDescription or Title contains the entered text, then we will store it into a hashmap to be posted
        if((projectDescription != null && projectTitle != null) && (projectDescription.contains(getEnteredText()) || projectTitle.contains(getEnteredText()))) {
            HashMap<String,String> chosenProjects = new HashMap();
            chosenProjects.put("projectName", projectTitle);
            chosenProjects.put("projectDescription",projectDescription);
            projectsReturned.add(chosenProjects);
        }
        
        
}
        Gson gson = new Gson();
        response.setContentType("application/json;");
    
        response.getWriter().println(gson.toJson(projectsReturned)); 
}


//I was thinking that this should parse what was entered in the text box?
@Override
public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    //Now will get string from search Box
 String newText  = request.getParameter("searchfield");
setEnteredText(newText);


response.sendRedirect("projectSpace.html");

 



}
}

