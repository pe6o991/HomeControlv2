<%@ page import="com.auth"%>
<html>
	<head>
		<title>AUTHENTICATE</title>
		<link rel="icon" type="image/x-icon" href="resources/icons/favicon.ico">
		<link rel="stylesheet" href="resources/stylesheets/color.css">
		<script src="resources/scripts/common.js"></script>
		<center>
		<h1>AUTHENTICATE</h1>
		<style>
			body{
				background-color:var(--background-color);
			}
			th,td{
				color:var(--text-color);
			}
			h1{
				color:var(--text-color);
			}
			input{
				background-color:var(--button-color);
				color:var(--text-color);
				border:0;
				border-radius:0.3rem;
			}
		</style>
	</head>
	<body>
		<% 
			String address = request.getRemoteAddr();
			String ip[] = address.split("\\.",4);
			boolean authenticated=false;
			if(!ip[0].equals("192") & !ip[1].equals("168")){
				for(String val : auth.authenticated.keySet()){
					if(address.equals(val)) authenticated=true;
				}
				if(authenticated==true){
					response.sendRedirect("index.jsp");
				}	
			}else{
				response.sendRedirect("index.jsp");
			}
		%>
		<form method="POST" action="auth">
			<table>
				<th colspan="3">Enter</th>
				<tr><td>Token:</td><td><input type="text" name="token"></td><td><input type="submit" value="Enter"></td></tr>
			</table>
		</form>
		<script>checkTheme();</script>
	</body>
</html>
