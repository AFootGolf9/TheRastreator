async function verifyLogin() {
    coockie = getCookie("token");

    request = fetch('http://localhost:8080/login', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': coockie
        }
    }).then(async response => {
        if (response.ok) {
            var data = await response.json()
            document.getElementById('test').innerHTML = data.username;
        } else {
            throw new Error('Something went wrong');
        }
    }).catch(error => {
        console.error('There has been a problem with your fetch operation:', error);
    });
}