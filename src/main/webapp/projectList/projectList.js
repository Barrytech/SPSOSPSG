function loadProjectInfo() {
      const projectInfoPromise = fetch('/project-info').then(response => response.json()); //we're fetching information from the 'project-list' server
      projectInfoPromise.then((response) => { //fecth() returns a 'promise' object, saying that when the server sends back information, then do "X"
        for (let i = 0; i < response.length; i++) { //response = the arrayList in Json
            let projectInfo = response[i];
            let comeBack2 = document.getElementById('come-back-2');
            let comeBack3 = document.getElementById('come-back-3');
            let comeBack4 = document.getElementById('come-back-4');
            console.log(projectInfo);
            comeBack2.innerHTML = "Posted By: " + projectInfo["userName"] + "";
            comeBack3.innerHTML = "Project Title: " + projectInfo["projectName"] + "";
            comeBack4.innerHTML = "Description: " + projectInfo["projectDescription"] + "";
        }

    })
}