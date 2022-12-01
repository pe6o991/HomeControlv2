function Default(){
	document.querySelector(':root').style.setProperty('--background-color', '#EEEEEE');
	document.querySelector(':root').style.setProperty('--button-color', '#9E9E9E');
	document.querySelector(':root').style.setProperty('--text-color', '#424242');
	document.querySelector(':root').style.setProperty('--secondary-color','gray');
	setCookie("theme","default",365);
}

function BlueGray(){
	document.querySelector(':root').style.setProperty('--background-color', 'rgb(29,32,37)');
	document.querySelector(':root').style.setProperty('--button-color', 'rgb(16,42,67)');
	document.querySelector(':root').style.setProperty('--text-color', 'rgb(174,238,152)');
	document.querySelector(':root').style.setProperty('--secondary-color','rgb(39,61,84)');
	setCookie("theme","BlueGray",365);
}

function CoolGray(){
	document.querySelector(':root').style.setProperty('--background-color', 'rgb(29,32,37)');
	document.querySelector(':root').style.setProperty('--button-color', 'rgb(46,46,46)');
	document.querySelector(':root').style.setProperty('--text-color', 'rgb(237,194,152)');
	document.querySelector(':root').style.setProperty('--secondary-color','rgb(51,60,69)');
	setCookie("theme","CoolGray",365);
}

function WarmGray(){
	document.querySelector(':root').style.setProperty('--background-color', 'rgb(37,32,29)');
	document.querySelector(':root').style.setProperty('--button-color', 'rgb(39,36,29)');
	document.querySelector(':root').style.setProperty('--text-color', 'rgb(244,143,177)');
	document.querySelector(':root').style.setProperty('--secondary-color','rgb(59,56,49)');
	setCookie("theme","WarmGray",365);
}

function setCookie(cname, cvalue, exdays) {
	const d = new Date();
	d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
	let expires = "expires="+d.toUTCString();
	document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}

function getCookie(cname) {
	let name = cname + "=";
	let ca = document.cookie.split(';');
	for(let i = 0; i < ca.length; i++) {
		let c = ca[i];
		while (c.charAt(0) == ' ') {
			c = c.substring(1);
		}
		if (c.indexOf(name) == 0) {
			return c.substring(name.length, c.length);
		}
	}
	  return "";
}

function checkTheme() {
	let theme = getCookie("theme");
	if (theme != "") {
		loadTheme(theme);
	} else {
		setCookie("theme", "default", 365);
	}
}

function loadTheme(theme){
	switch(theme){
		case "default":
			Default();
		break;
		case "BlueGray":
			BlueGray();
		break;
		case "CoolGray":
			CoolGray();
		break;
		case "WarmGray":
			WarmGray();
		break;
	}
}

