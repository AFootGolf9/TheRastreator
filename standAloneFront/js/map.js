async function makeMap() {
    token = getCookie("token");

    mapSpace = document.getElementById('mapSpace');
    if (mapSpace.innerHTML == "") {
        mapSpace.innerHTML = "Something went wrong";
        return;
    }

    request = fetch('http://localhost:8080/locations', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            'token': token
        })
    }).then(async response => {
        if (response.ok) {
            var data = await response.json()
            mapSpace.innerHTML = await makeStringTest(data.locations);
        } else {
            throw new Error('Something went wrong');
        }
    }).catch(error => {
        console.error('There has been a problem with your fetch operation:', error);
    });
}


async function checkLogin() {
    token = getCookie("token");

    request = fetch('http://localhost:8080/login', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': token
        }
    }).then(async response => {
        if (response.ok) {
            var data = await response.json()
            document.getElementById('mapSpace').innerHTML = data;
            makeMap();
        } else {
            throw new Error('Something went wrong');
        }
    }).catch(error => {
        console.error('There has been a problem with your fetch operation:', error);
    });
}

async function makeStringTest(jsonList) {
    console.log("passed here 1")
    out = ""
    for (i = 0; i < jsonList.length; i++) {
        console.log("passed here 2")
        out += "<br>";
        for (key in jsonList[i]) {
            out += key + ": " + jsonList[i][key] + "<br>";
        }
    }
    return out;
}