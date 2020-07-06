function loadProjectInfo() {
      const projectInfoPromise = fetch('/project-info').then(response => response.json()); //we're fetching information from the 'project-list' server
      projectInfoPromise.then((response) => { //fecth() returns a 'promise' object, saying that when the server sends back information, then do "X"
        for (let i = 0; i < response.length; i++) { //response = the arrayList in Json
            let projectInfo = response[i];
            let comeBack = document.getElementById('come-back-2');
            console.log(projectInfo);
            comeBack.innerHTML = "Posted By: " + projectInfo["userName"] + "";
            comeBack.innerHTML = "Project Title: " + projectInfo["projectName"] + "";
            comeBack.innerHTML = "Description: " + projectInfo["projectDescription"] + "";
            
        }

    })
}