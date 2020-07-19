

function loadSearchResults() {
 const projectSearchPromise = fetch('/project-search').then(response => response.json());


  projectSearchPromise.then((response) => {

      for(let i = 0; i < response.length; i++){
                //Now will populate my html with porject titles anddescriptions
       let project = response[i]; //THis saves each the hashmap into the variable project
       var projects = document.getElementById("ProjectsReturnedList");
       var projectTitle = document.getElementById("ProjectTitle");
       var projectDescription = document.getElementById("ProjectDescription");

       projectTitle.innerHTML =  "Project Title:" + "  " + project["projectName"];
       projectDescription.innerHTML = "Description:" +  "  " + project["projectDescription"];

       projects.appendChild(projectTitle);
       projects.appendChild(projectDescription);
       

      }


  })
  
}

/** Fetches login status. */
function displayLoginContent() {
  fetch('/login-status').then(response => response.json()).then((info) => {

    const projectNav = document.getElementById("project-item");
    const forumNav = document.getElementById('forum-item');
    const profileNav = document.getElementById('profile-item');

    const loginNav = document.getElementById("login-link");
    const loginHeader = document.getElementById("login-header");

    if (info["status"] === "true") { // User is logged in
        profileNav.style.display = "block"; //display
        forumNav.style.display = "block"; //display
        projectNav.style.display = "block"; //display

        // Display Logout header tab
        loginHeader.style.display = "block";  //display
        loginNav.href = info["url"];
        loginNav.innerText = "Logout";
    } else {
        profileNav.style.display = "none"; //hide
        forumNav.style.display = "none"; //hide
        projectNav.style.display = "none"; //hide

        // Display Login header tab
        loginHeader.style.display = "block";  //display
        loginNav.href = info["url"];
        loginNav.innerText = "Login";

  }

  });
}
