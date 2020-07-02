package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import com.google.gson.Gson;
import java.lang.String;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.util.HashMap;



@WebServlet("/project-info")
public class DataServlet extends HttpServlet { //ADD YOUR FUNCTIONS IN HERE
//
//ELIAS
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //response.setContentType("text/html");
        String userName = request.getParameter("userName"); //request is getting the info from the html through the javascript
        String projectName = request.getParameter("projectName");
        String projectDescription = request.getParameter("projectDescription");
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Entity newProjectEntity = new Entity("Project"); //"Project" is the entity 'kind' I'm creating
        newProjectEntity.setProperty("userName", userName); //entity properties have a (key, value), and are stored in datastore
        newProjectEntity.setProperty("projectName", projectName);
        newProjectEntity.setProperty("projectDescription", projectDescription);
        datastore.put(newProjectEntity); //stores the entity
        response.getWriter().println("Save Successful");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        //Going through the database and posting the projects
        Query query = new Query("Project").addSort("userName", SortDirection.DESCENDING); //allows us to query through all the entities with kind "Project" and Property "userName"
        PreparedQuery results = datastore.prepare(query); //resuls contains all the entities in Datastore with that kind
        ArrayList<HashMap<String, String>> projects = new ArrayList<>(); //this is an arrayList of HashMaps, each of which contain a specific project's details
        for (Entity entity: results.asIterable()) {
            String userName = (String) entity.getProperty("userName"); //gets the properties set on each entity. 
            String projectName = (String) entity.getProperty("projectName");
            String projectDescription = (String) entity.getProperty("projectDescription");

            HashMap<String, String> projectInfo = new HashMap();
            projectInfo.put("userName", userName);
            projectInfo.put("projectName", projectName);
            projectInfo.put("projectDescription", projectDescription);
            projects.add(projectInfo);
        }
        Gson gson = new Gson();
        response.setContentType("application/json;");
        response.getWriter().println(gson.toJson(projects)); //response contains all of the project's information in "application/json" form
    } 
//ELIAS
//

}
