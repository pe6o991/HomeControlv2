var xhttp = new XMLHttpRequest();
var sxhttp = new XMLHttpRequest();
var timeout = 1000;
xhttp.onreadystatechange = function() {
	if (this.readyState == 4 && this.status == 200) {
		data = JSON.parse(xhttp.responseText);
		for (var key in data) {
			if(key.includes("bar")){
				var id = key.split("-")[0];
				var element = document.getElementById(id);
				var root = element.parentNode;
				root.children[1].style.width = data[key];
				
			}else if(key.includes("btn")){
				var id = key.split("-")[0];
				var element = document.getElementById(id);
				var root = getComputedStyle(document.documentElement);
				if(data[key]=="off"){
					//console.log(id);
					element.style.color = root.getPropertyValue('--text-color');
					element.style.backgroundColor = root.getPropertyValue('--background-color');
				}else{
					element.style.color = root.getPropertyValue('--background-color');
					element.style.backgroundColor = root.getPropertyValue('--text-color');
				}
			}else{
			document.getElementById(key).innerHTML=data[key];
			}
		}
	}
};

sxhttp.onreadystatechange = function() {
	if (this.readyState == 4 && this.status == 200) {
		var stats = sxhttp.responseText;
		if(stats!="OK"){
			alert(stats);
		}
	}
};



function loaded(){
 var modal = document.getElementById("myModal");
 var btn = document.getElementById("options");
 var span = document.getElementsByClassName("close")[0];
 var delLog = document.getElementById("dellog");
 var redir = document.getElementById("redir");
 var timer = document.getElementById("setTime");
 console.log('window loaded');
 checkTheme();
 checkTimeout(timer);
 //timeout = parseInt(checkTimeout()) * 1000;

// // When the user clicks on the button, open the modal
 btn.onclick = function() {
 	modal.style.display = "block";
   }
//
//   // When the user clicks on <span> (x), close the modal
   span.onclick = function() {
     modal.style.display = "none";
     }

//     // When the user clicks anywhere outside of the modal, close it
     window.onclick = function(event) {
       if (event.target == modal) {
           modal.style.display = "none";
       }
    }
	
	delLog.onclick = function(){
		sxhttp.open("POST", "main", true);
		sxhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		sxhttp.send("type=dellog");
	}
		
	redir.onclick = function(){
		var host = location.host;
		var ip = host.split(":")[0];
		ip = "http://" + ip + ":3000";
		//alert(ip);
		window.location.href=ip; 
	}
	
	timer.onchange = function(){
		//alert(timer.value);
		timeout = timer.value * 1000;
		setCookie("timer",timer.value.toString(), 365);
	}
			 
}

function checkTimeout(element) {
	let theme = getCookie("timer");
	if (theme != "") {
		timeout = parseInt(theme) * 1000;
		setSelect(element,parseInt(theme));
	} else {
		setCookie("timer","1", 365);
	}
}

function setSelect(selectElement,value){
	for(i=0;i<selectElement.options.length;i++){
		if(selectElement.options[i].value==value){
			selectElement.selectedIndex = i;
			break;
		}
	}
}

async function loadHTML(){
	const content = document.getElementById("content");
	xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function (e) { 
		    if (xhr.readyState == 4 && xhr.status == 200) {
			          content.innerHTML = xhr.responseText;
			        }
		  }
	xhr.open("GET", "http://192.168.1.8:8080/Diplomna/test.html", true);
	xhr.setRequestHeader('Content-type', 'text/html');
	xhr.send();
}

function consoleTest(){
	alert(timeout);
}

function send(btnId){
	sxhttp.open("POST", "main", true);
	sxhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	sxhttp.send("type=command&command="+btnId);
	//alert(btnId);
}