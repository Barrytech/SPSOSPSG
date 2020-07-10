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
        String imageURL = (String) entity.getProperty("imageURL"); //profile image url
        
        info.put("firstname", firstname);
        info.put("lastname", lastname);
        info.put("username", username);
        info.put("birthday", birthday);
        info.put("email", email);
        info.put("imageURL", imageURL);

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
    // Get the URL of the image that the user uploaded to Blobstore.
    String imageURL = getUploadedFileUrl(request, "profile-image");

    //String userEmail = userService.getCurrentUser().getEmail();
    String id = userService.getCurrentUser().getUserId();

    Entity ProfileEntity = new Entity("ProfileData", id);
    ProfileEntity.setProperty("firstname", firstname);
    ProfileEntity.setProperty("lastname", lastname);
    ProfileEntity.setProperty("username", username);
    ProfileEntity.setProperty("birthday", birthday);
    ProfileEntity.setProperty("email", email);
    ProfileEntity.setProperty("id", id);
    if(imageURL != null){ //  if no image is uploaded
        ProfileEntity.setProperty("imageURL", imageURL);
    }
    else { // Allow previous uploaded images to persist
        Query query = new Query("ProfileData").setFilter(new Query.FilterPredicate("id", Query.FilterOperator.EQUAL, id));
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        PreparedQuery results = datastore.prepare(query);
        Entity entity = results.asSingleEntity(); // A single entity as you only want the currently logged in user's info
        imageURL = (String) entity.getProperty("imageURL"); //profile image url
        ProfileEntity.setProperty("imageURL", imageURL);
    }

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    // The put() function automatically inserts new data or updates existing data based on ID
    datastore.put(ProfileEntity);

    // Redirect client back to the HTML page (i.e. Home page)
    response.sendRedirect("/userInfo/userProfile.html");
  }

    /** Returns a URL that points to the uploaded file, or null if the user didn't upload a file. */
  private String getUploadedFileUrl(HttpServletRequest request, String formInputElementName) {
    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
    Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(request);
    List<BlobKey> blobKeys = blobs.get("profile-image");

    // User submitted form without selecting a file, so we can't get a URL. (dev server)
    if (blobKeys == null || blobKeys.isEmpty()) {
      return null;
    }

    // Our form only contains a single file input, so get the first index.
    BlobKey blobKey = blobKeys.get(0);

    // User submitted form without selecting a file, so we can't get a URL. (live server)
    BlobInfo blobInfo = new BlobInfoFactory().loadBlobInfo(blobKey);
    if (blobInfo.getSize() == 0) {
      blobstoreService.delete(blobKey);
      return null;
    }

    // We could check the validity of the file here, e.g. to make sure it's an image file
    // https://stackoverflow.com/q/10779564/873165

    // Use ImagesService to get a URL that points to the uploaded file.
    ImagesService imagesService = ImagesServiceFactory.getImagesService();
    ServingUrlOptions options = ServingUrlOptions.Builder.withBlobKey(blobKey);
    String url = imagesService.getServingUrl(options);

    // GCS's localhost preview is not actually on localhost,
    // so make the URL relative to the current domain.
    if(url.startsWith("http://localhost:8080/")){
      url = url.replace("http://localhost:8080/", "/");
    }
    return url;
  }

}

