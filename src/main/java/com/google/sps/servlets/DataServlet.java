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
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;



@WebServlet("/project-list")
public class DataServlet extends HttpServlet { //class
//
//ELIAS
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Entity newProjectEntity = new Entity("Project"); //"Project" is the entity 'kind' I'm creating
        newProjectEntity.setProperty("userName", userName); //entity properties have a (key, value), and are stored in datastore
        newProjectEntity.setProperty("project", projectName);
        newProjectEntity.setProperty("projectDescription", projectDescription);
        datastore.put(newProjectEntity); //stores the entity
    }
    //Going through the database and posting the projects
    Query query = new Query("Project").addSort("userName". SortDirection.DESCENDING); //allows us to query through all the entities with kind "Project" and Property "userName"
    PreparedQuery results = datasore.prepare(query); //resuls contains all the entities in Datastore with that kind
    for (Entity entity: results.asIterable()) {
        entity.getProperty(); //gets the properties set on each entity.  
    }
//ELIAS
//

}
