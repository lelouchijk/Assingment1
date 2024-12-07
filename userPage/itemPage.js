document.querySelectorAll('.element-button').forEach(button => {
    button.addEventListener('click', () => {
        const outerDiv = button.nextElementSibling; 
        if (outerDiv) {
            outerDiv.classList.toggle('active');
        }
    });
});