async function login(){
    var name = document.getElementById("username").value;
    var password = document.getElementById("password").value;

    if(name == "" || password == ""){
        alert("Please fill all the fields");
        return
    }

    // if (!validateEmail(email)) {
    //     alert("Invalid email address");
    //     return
    // }

    const response = await fetch('http://localhost:8080/login', {
        method: 'POST',
        mode: 'no-cors',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            user: name,
            pass: password
        })
    }).then(response => (response) => {
        document.cookie = `token=${response.token}; path=/`
        console.log("setou o cookie")
        document.getElementById("test").innerHTML = response

        // window.location.href = "/"
    }   
    ).catch((error) => {
        console.error('Error:', error);
    });
}