const allSideMenu = document.querySelectorAll('#sidebar .side-menu.top li a');

allSideMenu.forEach(item=> {
	const li = item.parentElement;

	item.addEventListener('click', function () {
		allSideMenu.forEach(i=> {
			i.parentElement.classList.remove('active');
		})
		li.classList.add('active');
	})
});




// TOGGLE SIDEBAR

document.addEventListener("DOMContentLoaded", function () {
    const sidebar = document.querySelector('#sidebar');
    const toggleButton = document.querySelector('#sidebar .brand');

    const savedState = localStorage.getItem('sidebarState');

    if (savedState === 'active') {
        sidebar.classList.add('active');
    }

    toggleButton.addEventListener('click', function (e) {
        e.preventDefault();

        sidebar.classList.toggle('active');

        if (sidebar.classList.contains('active')) {
            localStorage.setItem('sidebarState', 'active');
        } else {
            localStorage.setItem('sidebarState', 'collapsed');
        }
    });
});





const searchButton = document.querySelector('#content nav form .form-input button');
const searchButtonIcon = document.querySelector('#content nav form .form-input button .bx');
const searchForm = document.querySelector('#content nav form');

searchButton.addEventListener('click', function (e) {
	if(window.innerWidth < 576) {
		e.preventDefault();
		searchForm.classList.toggle('show');
		if(searchForm.classList.contains('show')) {
			searchButtonIcon.classList.replace('bx-search', 'bx-x');
		} else {
			searchButtonIcon.classList.replace('bx-x', 'bx-search');
		}
	}
})





if(window.innerWidth < 768) {
	sidebar.classList.add('hide');
} else if(window.innerWidth > 576) {
	searchButtonIcon.classList.replace('bx-x', 'bx-search');
	searchForm.classList.remove('show');
}


window.addEventListener('resize', function () {
	if(this.innerWidth > 576) {
		searchButtonIcon.classList.replace('bx-x', 'bx-search');
		searchForm.classList.remove('show');
	}
})



const switchMode = document.getElementById('switch-mode');

switchMode.addEventListener('change', function () {
	if(this.checked) {
		document.body.classList.add('dark');
	} else {
		document.body.classList.remove('dark');
	}
})


document.addEventListener("DOMContentLoaded", function () {
    const profile = document.querySelector('.profile');
    const subMenuWrap = document.querySelector('.sub-menu-wrap');

    profile.addEventListener('click', (e) => {
        e.preventDefault();
        subMenuWrap.style.display = subMenuWrap.style.display === 'block' ? 'none' : 'block';
    });


    document.addEventListener('click', (e) => {
        if (!profile.contains(e.target) && !subMenuWrap.contains(e.target)) {
            subMenuWrap.style.display = 'none';
        }
    });
});
