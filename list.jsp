<%@ page import="com.SnC" %>
<%@ page import="com.sensor" %>
<%@ page import="com.control" %>
<html>
	<head>
		<center>
		<h1>List of sensors</h1>
		<style>
			body{
				background-color:black;
			}
			h1{
				color:red;
				margin:0;
			}
			th{
				color:red;
			}
			td{
				color:orange;
			}
			table{
				background-color:#2c2c2c;
			}
			td,th,table{
				border:1px solid red;
				border-collapse:collapse;
			}
		</style>
	</head>
	<body>
		<table>
			<th colspan="4">List</th>
			<tr><td><h1>Id</h1></td><td><h1>Type</h1></td><td><h1>Name</h1></td><td><h1>Address</h1></td></tr>
			<% for(sensor sens : SnC.sensors){ %>
				<tr><td><%= sens.id %></td><td><%= sens.type %></td><td><%= sens.name %></td> <td><%= sens.address %></td></tr>
			<% } %>
		</table>
		<br>
		<h1>List of controls</h1>
		<table>
			<th colspan="4">List</th>
			<tr><td><h1>Id</h1></td><td><h1>Type</h1></td><td><h1>Name</h1></td><td><h1>Address</h1></td></tr>
			<% for(control cons : SnC.controls){ %>
				<tr><td><%= cons.id %></td><td><%= cons.type %></td><td><%= cons.name %></td><td><%= cons.address %></td></tr>
			<% } %>
		</table>	
	</body>
</html>
