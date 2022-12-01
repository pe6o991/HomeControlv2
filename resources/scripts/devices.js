var xhttp = new XMLHttpRequest();

function loaded(){
	checkTheme();
	var form = document.getElementById("dat");
	form.onsubmit = function(event){
		alert("formSubmit");
		window.stop();
		var formData = new FormData(document.getElementById("dat"));
		xhttp.open("POST", "main", true);
		xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		xhttp.send(urlencodeFormData(formData));
		xhttp.send(formData);
		event.preventDefault();
	}
}

xhttp.onreadystatechange = function() {
	if (this.readyState == 4 && this.status == 200) {
		//data = JSON.parse(xhttp.responseText);
		alert(xhttp.responseText);
		location.reload();
		}
};

function remove(id){
	xhttp.open("POST", "main", true);
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhttp.send("type=remove&id="+id);
}

function urlencodeFormData(fd){
	var s = '';
	function encode(s){ return encodeURIComponent(s).replace(/%20/g,'+'); }
	for(var pair of fd.entries()){
		if(typeof pair[1]=='string'){
				s += (s?'&':'') + encode(pair[0])+'='+encode(pair[1]);
		}
	}
	return s;
}


