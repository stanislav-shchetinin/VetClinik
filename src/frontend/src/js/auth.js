import {HOME, URL_BACKEND, URL_FRONTEND, validInput} from "./main.js";

export const auth = () => {
    const form = document.querySelector('.sign-in-container');
    const inputPassword = form.querySelector('.password')
    const inputEmail = form.querySelector('.email')

    const json = {"username": inputEmail.value, "password": inputPassword.value}

    for (const [key, val] of Object.entries(json)) {
        if (!validInput(val)){
            alert(`Поле ${key} не может быть пустым`);
            return;
        }
    }
    fetch(URL_BACKEND + '/auth', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8',
        },
        body: JSON.stringify(json)
    }).then(response => {
        if (response.ok){
            response.json().then(json => localStorage.setItem("jwt", json.token.toString()));
            window.location.replace(URL_FRONTEND + HOME)
        } else if (response.status === 401){
            alert("Please activate the mail");
        } else if (response.status === 404){
            alert("Invalid username or password");
        }
        else {
            alert("Oops.. is something wrong")
        }
    });

}



