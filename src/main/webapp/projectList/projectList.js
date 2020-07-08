function loadProjectInfo() {
      const projectInfoPromise = fetch('/project-info').then(response => response.json()); //we're fetching information from the 'project-list' server
      projectInfoPromise.then((response) => { //fecth() returns a 'promise' object, saying that when the server sends back information, then do "X"
        for (let i = 0; i < response.length; i++) { //response = the arrayList in Json
            let projectInfo = response[i];
            let project = document.createElement('project-post');
            let postUserName = document.createElement('post-user-name');
            let postProjectName = document.createElement('post-project-name');
            let postProjectDescription = document.createElement('post-project-description');
            console.log(projectInfo); 
            postUserName.innerHTML = "Posted By: " + projectInfo["userName"] + "";
            postProjectName.innerHTML = "Project Title: " + projectInfo["projectName"] + "";
            postProjectDescription.innerHTML = "Description: " + projectInfo["projectDescription"] + "";
            project.appendChild(postUserName);
            project.appendChild(postProjectName);
            project.appendChild(postProjectDescription);
            document.body.appendChild(project);
        }

    })
}