import {authHeader, HOME, LOGIN, MEMBERS, URL_BACKEND, URL_FRONTEND, validInput} from "./main.js";

function deleteGroup(id) {
    fetch(URL_BACKEND + HOME + "/" + id + "/delete", {
        method: 'DELETE',
        headers: {
            'Authorization' : authHeader()
        },
    }).then(response => {
        if (response.ok){
            window.location.replace(URL_FRONTEND + HOME)
        } else if (response.status === 401){
            window.location.replace(URL_FRONTEND + LOGIN);
        } else {
            alert('Couldn\'t delete a group');
        }
    });
}

fetch(URL_BACKEND + HOME, {
    method: 'GET',
    headers : {
        'Authorization' : authHeader()
    }
}).then(r => {
    if (r.status === 401){
        window.location.replace(URL_FRONTEND + LOGIN);
    } else {
        return r.json().then( json => {
            console.log(json);
            const adminGroups = json.adminGroups;
            const memberGroups = json.memberGroups;
            const adminList = document.querySelector('.admin');
            const memberList = document.querySelector('.member');

            adminGroups.forEach(group => {
                const groupId = "group" + group.id;
                adminList.insertAdjacentHTML('beforeend', `
                    <li> 
                        <p><b>${group.name}</b></p>
                        <p>${group.description}</p>
                        <button class="button button-shadow info ${groupId}">Info</button>
                        <button class="button button-shadow delete ${groupId}">Delete</button>
                    </li>
                `)
                const btnInfo = document.getElementsByClassName(`info ${groupId}`);
                btnInfo[0].addEventListener('click', () => {
                    localStorage.setItem("groupId", group.id);
                    localStorage.setItem("groupName", group.name);
                    //console.log(URL_FRONTEND + HOME + "/" + group.id + MEMBERS);
                    window.location.replace(URL_FRONTEND + HOME + "/" + group.id + MEMBERS);
                })
                const btnDelete = document.getElementsByClassName(`delete ${groupId}`);
                btnDelete[0].addEventListener('click', () => {
                    deleteGroup(group.id);
                })
            })

            memberGroups.forEach(group => {
                memberList.insertAdjacentHTML('beforeend', `
                    <li> 
                        <p><b>${group.group.name}</b></p>
                        <p>${group.group.description}</p>
                        <p>Paid classes: ${group.numberClasses}</p>
                     
                    </li>
                `)
            })

        });
    }
});

const btnCreate = document.querySelector('.btn-crete');
btnCreate.addEventListener('click', () => {

    const inputName = document.querySelector('.name');
    const inputDescription = document.querySelector('.description');
    const json = {"id": crypto.randomUUID(), "name": inputName.value, "description": inputDescription.value};

    for (const [key, val] of Object.entries(json)) {
        if (!validInput(val)){
            alert(`Поле ${key} не может быть пустым`);
            return;
        }
    }

    fetch(URL_BACKEND + HOME + "/create", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8',
            'Authorization' : authHeader()
        },
        body: JSON.stringify(json)
    }).then(response => {
        if (response.ok){
            window.location.replace(URL_FRONTEND + HOME)
        } else if (response.status === 401){
            window.location.replace(URL_FRONTEND + LOGIN);
        } else {
            alert('Couldn\'t create a group');
        }
    });

})

const btnLogout = document.querySelector('.logout');
btnLogout.addEventListener('click', () => {
    localStorage.removeItem("jwt");
    window.location.replace(URL_FRONTEND + LOGIN);
})