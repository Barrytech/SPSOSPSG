
function displayProfileContent() {
  fetch('/profile-data').then(response => response.json()).then((info) => {
    const ul = document.getElementById("profile-info");
    const headerWithName = document.getElementById("nameHeader");
    headerWithName.innerHTML = info["firstname"] + info["lastname"] + 'Profile';
    ul.innerHTML = '';
    
    ul.appendChild(createListElement('First Name:' + info["firstname"]));
    ul.appendChild(createListElement('Last Name:' + info["lastname"]));
    ul.appendChild(createListElement('Email:' + info["email"]));
    ul.appendChild(createListElement('Username:' + info["username"]));
    ul.appendChild(createListElement('Birthday:' + info["birthday"]));
    
  });
}

/** Creates an <li> element containing text. */
function createListElement(text) {
  const liElement = document.createElement('li');
  liElement.innerText = text;
  return liElement;
}

/** Fetches login status. */
function displayLoginContent() {
  fetch('/login-status').then(response => response.json()).then((info) => {

    const profile_body = document.getElementById("profile-container");
    const login_button = document.getElementById('login-button-1');

    const a = document.getElementById("logout-1-a");

    if (info["status"] === "true") { // User is logged in
        profile_body.style.display = "block"; //display
        login_button.style.display = "none";  //Hide
        
        a.href = info["url"];
    } else {
        profile_body.style.display = "none";
        login_button.style.display = "block";
        login_button.onclick = function() {window.location=info["url"]};

  }

  });
}