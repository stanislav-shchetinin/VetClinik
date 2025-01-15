import {reg} from "./reg.js";
import {auth} from "./auth.js";

const signUpButton = document.getElementById('signUp');
const signInButton = document.getElementById('signIn');
const container = document.getElementById('container');

signUpButton.addEventListener('click', () => {
    container.classList.add("right-panel-active");
});

signInButton.addEventListener('click', () => {
    container.classList.remove("right-panel-active");
});

const btnReg = document.getElementById('btn-reg');
btnReg.addEventListener('click', reg);

const btnAuth = document.getElementById('btn-auth');
btnAuth.addEventListener('click', auth);