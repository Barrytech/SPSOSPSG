

function loadSearchResults() {
 const projectSearchPromise = fetch('/project-search').then(response => response.json());


  projectSearchPromise.then((response) => {

      for(let i = 0; i < response.length; i++){
                //Now will populate my html with porject titles anddescriptions
       let project = response[i]; //THis saves each the hashmap into the variable project
       var projects = document.getElementById("ProjectsReturnedList");
       var projectTitle = document.getElementById("ProjectTitle");
       var projectDescription = document.getElementById("ProjectDescription");

       projectTitle.innerHTML =  "Project Title" + " " + project["projectName"];
       projectDescription.innerHTML = "Description" +  " " + project["projectDescription"];

       projects.appendChild(projectTitle);
       projects.appendChild(projectDescription);
       

      }


  })
  
}