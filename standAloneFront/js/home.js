async function verifyLogin() {
    coockie = getCookie("token");

    request = fetch('http://localhost:8080/login', {
        method: 'GET',
        mode: 'no-cors',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': coockie
        }
    })

    await request
    if(!request.ok){
        console.log(coockie)
        // window.location.href = "/login"
    }else{
        document.getElementById("test").innerHTML = "Welcome ${request.username}"
    }
}

verifyLogin()