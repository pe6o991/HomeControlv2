function Default(){
	document.querySelector(':root').style.setProperty('--background-color', '#EEEEEE');
	document.querySelector(':root').style.setProperty('--secondary-color','gray');
	document.querySelector(':root').style.setProperty('--button-color', '#9E9E9E');
	document.querySelector(':root').style.setProperty('--text-color', '#424242');
	setCookie("theme","default",365);

}

function BlueGray(){
	document.querySelector(':root').style.setProperty('--background-color', 'rgb(29,32,37)');
	document.querySelector(':root').style.setProperty('--secondary-color','rgb(39,61,84)');
	document.querySelector(':root').style.setProperty('--button-color', 'rgb(16,42,67)');
	document.querySelector(':root').style.setProperty('--text-color', 'rgb(174,238,152)');
	setCookie("theme","BlueGray",365);
}

function CoolGray(){
	document.querySelector(':root').style.setProperty('--background-color', 'rgb(29,32,37)'); //#1D2025
	document.querySelector(':root').style.setProperty('--secondary-color','rgb(51,60,69)'); //#333C45
	document.querySelector(':root').style.setProperty('--button-color', 'rgb(46,46,46)'); //#2E2E2E
	document.querySelector(':root').style.setProperty('--text-color', 'rgb(237,194,152)'); //#EDC298
	setCookie("theme","CoolGray",365);

}

function WarmGray(){
	document.querySelector(':root').style.setProperty('--background-color', 'rgb(37,32,29)'); //#25201D
	document.querySelector(':root').style.setProperty('--secondary-color','rgb(59,56,49)'); //#3B3831
	document.querySelector(':root').style.setProperty('--button-color', 'rgb(39,36,29)'); //#27241D
	document.querySelector(':root').style.setProperty('--text-color', 'rgb(244,143,177)'); //#F48FB1
	setCookie("theme","WarmGray",365);
}

