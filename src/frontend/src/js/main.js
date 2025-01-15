const URL_BACKEND = 'http://localhost:8080';
const URL_FRONTEND = 'http://localhost:3000';
const HOME = '/groups'
const LOGIN = '/login'
const MEMBERS = '/members'
const validInput = (str) => {
    return str != null && str !== "";
}
const authHeader = () => {
    return 'Bearer ' + localStorage.getItem("jwt");
}

export {URL_BACKEND, URL_FRONTEND, HOME, LOGIN, MEMBERS, validInput, authHeader};