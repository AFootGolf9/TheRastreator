function register(){
    var name = document.getElementById("username").value;
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;
    var confirmPassword = document.getElementById("confirmPassword").value;

    if(name == "" || email == "" || password == "" || confirmPassword == ""){
        alert("Please fill all the fields");
        return
    }

    // length of 4 for testing purposes
    if(password.length < 4){
        alert("Password must be at least 4 characters long");
        return
    }

    if(password != confirmPassword){
        alert("Passwords do not match");
        return
    }

    if (!validateEmail(email)) {
        alert("Invalid email address");
        return
    }

    fetch('http://localhost:8080/create', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            user: name,
            email: email,
            pass: password
        })
    })

}