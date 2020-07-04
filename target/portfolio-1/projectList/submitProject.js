function getNewProjectInfo() {
      const projectInfoPromise = fetch('/project-info').then(response => response.text()); //we're fetching information from the 'project-list' server
      loginInfoPromise.then((response) => { //fecth() returns a 'promise' object, saying that when the server sends back information, then do "X"
        let comeBack = document.getElementById('come-back');
        comeBack.innerHTML = "Congrats, your project was posted to the project board!";
        // if (response.includes("Logged in")) { //this is the "X" that happens.  Here, I want to specifically write to the projectList.html file,
        //     let comeBack = document.getElementById('come-back');//so to write to the projectList.html file, should I put the 'come-back' ul tag in the
        //     // projectList.js file, and put a body onload function for this function within projectList.html as well?  Then I could put the ul tag in that function...?
        //     comeBack.innerHTML = "Hi, I'm Paul";
        // } else {
        //     let comeBack = document.getElementById('come-back');//here i want to direct the user to a login page
        //     comeBack.innerHTML = "Hi, I'm Derek";
        // }
    })
}