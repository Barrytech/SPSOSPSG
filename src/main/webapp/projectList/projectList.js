function loadProjectInfo() {
      const projectInfoPromise = fetch('/project-info').then(response => response.json()); //we're fetching information from the 'project-list' server
      projectInfoPromise.then((response) => { //fecth() returns a 'promise' object, saying that when the server sends back information, then do "X"
      let projectsContainer = document.getElementById("projects-container");
        for (let i = 0; i < response.length; i++) { //response = the arrayList in Json
            let projectInfo = response[i];
            let project = document.createElement('ul');
            project.className = 'project-post'; //creats a class with the classname in red, editable with CSS
            let postUserName = document.createElement('li');
            postUserName.className = 'post-user-name';
            let postProjectName = document.createElement('li');
            postProjectName.className = 'post-project-name';
            let postProjectDescription = document.createElement('li');
            postProjectDescription.className = 'post-project-description';
            console.log(projectInfo);
            postUserName.innerHTML = "Posted By: " + projectInfo["userName"] + "";
            postProjectName.innerHTML = "Project Title: " + projectInfo["projectName"] + "";
            postProjectDescription.innerHTML = "Description: " + projectInfo["projectDescription"] + "";
            project.appendChild(postUserName);
            project.appendChild(postProjectName);
            project.appendChild(postProjectDescription);
            projectsContainer.appendChild(project); //here, we append the data that the javascript receives to the container id tag
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
