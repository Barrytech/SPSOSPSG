
function displayProfileContent() {
  fetch('/profile-data').then(response => response.json()).then((info) => {

    /*
    const ul = document.getElementById("profile-info");
    ul.innerHTML = ''; 
    ul.appendChild(createListElement('First Name:' + info["firstname"]));
    ul.appendChild(createListElement('Last Name:' + info["lastname"]));
    ul.appendChild(createListElement('Email:' + info["email"]));
    ul.appendChild(createListElement('Username:' + info["username"]));
    ul.appendChild(createListElement('Birthday:' + info["birthday"]));
    */
    document.getElementById('name').innerText = "Name: " + info["firstname"] + " " + info["lastname"];
    document.getElementById('username').innerText = "Username: " + info["username"];
    document.getElementById('email').innerText = "Email: " + info["email"];
    document.getElementById('birthday').innerText = "Birthday: " + info["birthday"];
    document.getElementById("profile-description").innerText = "Description: " + info["profileDescription"];

    // Add image to container and modify its attributes
    var img = document.createElement('img');
    img.src =  info["imageURL"];
    img.style.borderRadius = "30px";
    img.style.height = "300px";
    img.style.width = "270px";
    const image_holder = document.getElementById("profile-image-holder");
    image_holder.appendChild(img);

  });
}

// Pre fills form input fields based on prevouis input stored in Datastore
function fillProfileContent() {
  fetch('/profile-data').then(response => response.json()).then((info) => {

    document.getElementById('firstname').value = info["firstname"];
    document.getElementById('lastname').value = info["lastname"];
    document.getElementById('username').value = info["username"];
    document.getElementById('email').value = info["email"];
    document.getElementById('birthday').value = info["birthday"];
    document.getElementById("profile-description").value = info["profileDescription"];
    //document.getElementById('profile-image').value = info["imageURL"];

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

// Get Blobstore URL from the response of the servlet
function fetchBlobstoreUrlAndShowForm() {
  fetch('/blobstore-upload-url')
      .then((response) => {
        return response.text();
      })
      .then((imageUploadUrl) => {
        const messageForm = document.getElementById('profile-form');
        messageForm.action = imageUploadUrl;
        messageForm.classList.remove('hidden');
      });
}