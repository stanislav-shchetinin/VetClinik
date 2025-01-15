import {authHeader, HOME, LOGIN, MEMBERS, URL_BACKEND, URL_FRONTEND, validInput} from "./main.js";

const groupId = localStorage.getItem("groupId");
const groupName = localStorage.getItem("groupName");

function isNumeric(value) {
    return /^-?\d+$/.test(value);
}

function deleteUser(id) {
    fetch(URL_BACKEND + HOME + "/" + groupId + MEMBERS + "/delete?userId=" + id, {
        method: 'DELETE',
        headers: {
            'Authorization' : authHeader()
        },
    }).then(response => {
        if (response.ok){
            window.location.replace(URL_FRONTEND + HOME + "/" + id + MEMBERS)
        } else if (response.status === 401){
            window.location.replace(URL_FRONTEND + LOGIN);
        } else {
            alert('Couldn\'t delete user');
        }
    });
}

fetch(URL_BACKEND + HOME + "/" + groupId + MEMBERS, {
    method: 'GET',
    headers : {
        'Authorization' : authHeader()
    }
}).then(r => {
    if (r.status === 401){
        window.location.replace(URL_FRONTEND + LOGIN);
    } else {
        document.querySelector(".header").insertAdjacentHTML('beforeend', ` in ${groupName}`)
        return r.json().then( json => {
            console.log(json)
            const membersList = document.querySelector('.members');
            json.sort();
            json.forEach(member => {
                let paidText = "Admin";
                if (member.numberClasses != null){
                    localStorage.setItem(member.username, "user" + crypto.randomUUID())
                    paidText = "Paid classes: " + member.numberClasses;
                    membersList.insertAdjacentHTML('beforeend', `
                        <li> 
                            <p><b>${member.username}</b></p>
                            <p>${paidText}</p>
                            <button class="button button-shadow btn-number-classes minus ${localStorage.getItem(member.username)}">Minus</button>
                            <br>
                            <button class="button button-shadow btn-number-classes plus ${localStorage.getItem(member.username)}">Add</button>
                            <input class="input-number-classes ${localStorage.getItem(member.username)}">  
                            <br>
                            <button class="button button-shadow btn-number-classes btn-delete ${localStorage.getItem(member.username)}">Delete</button>       
                        </li>
                    `)
                    const btnPlus = document.getElementsByClassName(`plus ${localStorage.getItem(member.username)}`);
                    const btnMinus = document.getElementsByClassName(`minus ${localStorage.getItem(member.username)}`);
                    btnPlus[0].addEventListener('click', () => {
                        const input =
                            document.getElementsByClassName(`input-number-classes ${localStorage.getItem(member.username)}`);
                        if (!isNumeric(input[0].value)){
                            alert('Invalid input. Please, enter number');
                        } else {
                            changeNumberClasses("plus", member.username, input[0].value);
                        }

                    })
                    btnMinus[0].addEventListener('click', () => {
                        changeNumberClasses("minus", member.username, 1);
                    })
                    const btnDelete = document.getElementsByClassName(`btn-delete ${localStorage.getItem(member.username)}`);
                    btnDelete[0].addEventListener('click', () => {
                        deleteUser(member.username);
                    })
                } else {
                    membersList.insertAdjacentHTML('afterbegin', `
                        <li> 
                            <p><b>${member.username}</b></p>
                            <p>${paidText}</p>
                        </li>
                    `)
                }

            })

        });
    }
});

const changeNumberClasses = (operation, username, cnt) => {
    const json = {"username": username};
    if (operation === "plus") {
        json["plus"] = cnt;
    } else if (operation === "minus") {
        json["minus"] = cnt;
    }
    console.log(json);
    fetch(URL_BACKEND + HOME + "/" + groupId + MEMBERS + "/numberOfClasses/" + operation, {
        method: 'POST',
        headers : {
            'Authorization' : authHeader(),
            'Content-Type': 'application/json;charset=utf-8'
        },
        body : JSON.stringify(json)
    }).then(r => {
        if (r.status === 401){
            window.location.replace(URL_FRONTEND + LOGIN);
        } else {
            location.reload();
        }
    });
}

const btnBack = document.querySelector('.btn-back');
btnBack.addEventListener('click', () => {
    window.location.replace(URL_FRONTEND + HOME)
})

const btnAdd = document.querySelector('.btn-crete');

btnAdd.addEventListener('click', () => {

    const inputName = document.querySelector('.name');

    if (!validInput(inputName.value)){
        alert('Not correct username');
    } else {
        fetch(URL_BACKEND + HOME + "/" + groupId + MEMBERS + "/add?userId=" + inputName.value, {
            method: 'POST',
            headers: {
                'Authorization' : authHeader()
            },
        }).then(response => {
            if (response.ok){
                window.location.replace(URL_FRONTEND + HOME + "/" + groupId + MEMBERS)
            } else if (response.status === 401){
                window.location.replace(URL_FRONTEND + LOGIN);
            } else {
                alert('Couldn\'t add a person');
            }
        });
    }



})