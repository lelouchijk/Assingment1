const loginsec=document.querySelector('.login-section')
const loginlink=document.querySelector('.login-link')
const registerlink=document.querySelector('.register-link')
registerlink.addEventListener('click',(e)=>{
	e.preventDefault();
    loginsec.classList.add('active')
})
loginlink.addEventListener('click',(e)=>{
	e.preventDefault();
    loginsec.classList.remove('active')
})


