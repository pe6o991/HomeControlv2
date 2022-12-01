var xhttp = new XMLHttpRequest();
function loaded(){
	checkTheme();
	var device1 = document.getElementById("device1");
	var device2 = document.getElementById("device2");
	var dev1data = document.getElementById("dev1data");
	var dev2data = document.getElementById("dev2data");
	var operation = document.getElementById("operation");
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
	
	updateSelect(device1.value,dev1data);
	updateSelect(device2.value,dev2data);
	
	device1.onchange = function(){
		//alert("device1 change");
		updateSelect(device1.value,dev1data);
	}
	
	device2.onchange = function(){
		//alert("device2 change");
		updateSelect(device2.value,dev2data);
	}
	
	operation.onchange = function(){
		var items = document.getElementById("compare");
		items.innerHTML = operation.value;
		//console.log(items);
		// if(operation.value=="Log"){
			// for(var i=0;i < items.length;i++){
				// items[i].style.display = 'none';
			// }
		// }else{
			// for(var i=0;i<items.length;i++){
				// items[i].style.display = 'inline';
			// }
		// }
	}
	
}

xhttp.onreadystatechange = function() {
	if (this.readyState == 4 && this.status == 200) {
		//data = JSON.parse(xhttp.responseText);
		alert(xhttp.responseText);
		location.reload();
	} 
};

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

function remove(id){
	xhttp.open("POST", "main", true);
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhttp.send("type=rmrule&id="+id);
}

function updateSelect(id,selectElement){
	while (selectElement.options.length) selectElement.remove(0);
	if(id!=0){
		for (var key in devs) {
			if(key.includes("bar")){
				
			}else if(key.includes("btn")){
				var button = key.split("-")[0];
				var button_id = button.substring(0,1);
				var button_value = button.substring(2);
				if(button_id==id){
					option = document.createElement('option');
					option.value = option.text = button_value;
					selectElement.add(option);
				}
			}else{
				var ID = key.split("_")[0];
				var text = key.split("_")[1];
				if(ID==id){
					option = document.createElement('option');
					option.value = option.text = text;
					selectElement.add(option);
				}
			}
		}
	}else{
		option = document.createElement('option');
		option.value = option.text = "Time";
		selectElement.add(option);
	}
}