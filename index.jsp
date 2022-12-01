<%@ page import="com.auth"%>
<%@ page import="com.main"%>
<%@ page import="com.device"%>
<!DOCTYPE html>
<html>
	<head>
		<title>Home Control</title>
		<link rel="icon" type="image/x-icon" href="resources/icons/favicon.ico">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
		<link rel="stylesheet" href="resources/stylesheets/color.css">
		<link rel="stylesheet" href="resources/stylesheets/index.css">
		<script src="resources/scripts/common.js"></script>
		<script src="resources/scripts/index.js"></script>
	</head>
	<body onload="loaded()">
		<% 
			String address = request.getRemoteAddr();
			String ip[] = address.split("\\.",4);
			boolean authenticated=false;
			if(!ip[0].equals("192") & !ip[1].equals("168")){
				for(String val : auth.authenticated.keySet()){
					if(address.equals(val)) authenticated=true;
				}
				if(authenticated==false){
					response.sendRedirect("auth.jsp");
				}	
			}
		%>

		<div class="menu">
			<a class="navlink" href="devices.jsp">Devices</a>
			<a class="navlink" href="automation.jsp">Rules</a>
			<a class="navlink" href="#" id="redir">Statistics</a>
			<button id="options"><i class='fa fa-gear'></i></button>
		</div>
		
		<div id="content" class="content">
			<%      
			for(device dev : main.devices){
				out.write(dev.toHTML());
			}
			%>
		</div>
		
		<script>
		function update(){
		     xhttp.open("POST", "main", true);
		     xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		     xhttp.send("type=read");
		     setTimeout(update,timeout);
		}
		update();
	</script>
		
		<div id="myModal" class="modal">
		<div id="modal-content" class="modal-content">
			<span class="close">&times;</span>
			<p>SETTINGS</p>
			<div class="themeLabel">
			<span class="thmLabel">Theme</span>
			<button class="theme theme1" onclick="Default()">Default</button>
			<button class="theme theme2" onclick="CoolGray()">CoolGray</button>
			<button class="theme theme3" onclick="WarmGray()">WarmGray</button>
			<button class="theme theme4" onclick = "BlueGray()">BlueGray</button>
			</div>
			<div class="DBLabel">
			<span class="dbLabel">DB</span>
			<button class="dbbtn" id="dellog">Delete Log</button>
			</div>
			<div class="Time">
			<span class="timer">Refresh</span>
			<select id="setTime">
			<option value="1">1 sec</option>
			<option value="2">2 sec</option>
			<option value="3">3 sec</option>
			<option value="5">5 sec</option>
			<option value="10">10 sec</option>
			<option value="15">15 sec</option>
			<option value="30">30 sec</option>
			</select>
			</div>
		</div>
		</div>
	</body>
</html>
