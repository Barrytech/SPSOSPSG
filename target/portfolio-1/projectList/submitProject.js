function getNewProjectInfo() {
      const projectInfoPromise = fetch('/project-info').then(response => response.text()); //we're fetching information from the 'project-list' server
      projectInfoPromise.then((response) => { //fecth() returns a 'promise' object, saying that when the server sends back information, then do "X"
        let comeBack = document.getElementById('come-back');
        comeBack.innerHTML = "Something can go here later if you like";
    })
}