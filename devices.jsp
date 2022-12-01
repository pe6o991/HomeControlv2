<%@ page import="com.device"%>
<%@ page import="com.main"%>
<%@ page import="com.DBHelper"%>
<%@ page import="java.util.*" %>
<html>
	<head>
	<title>Add Device</title>
		<link rel="icon" type="image/x-icon" href="resources/icons/favicon.ico">
		<link rel="stylesheet" href="resources/stylesheets/devices.css">
		<link rel="stylesheet" href="resources/stylesheets/color.css">
		<script src="resources/scripts/common.js"></script>
		<script src="resources/scripts/devices.js"></script>
	</head>

	<body onload="loaded()">
	<div class="devices">
		<p class="add">ADD DEVICE</p>
		<div class="center_from">
		<form id="dat" method="POST" action="main">
		<input type="hidden" name="type" value="add">
		<div class="form">
			<div class="form_data"><span>Name:</span><input type="text" name="name"></div>
			<div class="form_data"><span>Type:</span><select name="devtype">
			<%
				DBHelper db = new DBHelper();
				Map<Integer,String> params = db.getDeviceTypes();
			%>
			<%for(Map.Entry<Integer, String> set : params.entrySet()){ %>
				<option value="<%= set.getKey() %>"><%= set.getValue() %> </option>
			<% } %>
			</select></div>
			<div class="form_data"><span>Interface:</span><select name="interface">
			<% params = db.getInterfaceTypes(); %>
				<%for(Map.Entry<Integer, String> set : params.entrySet()){ %>
					<option value="<%= set.getKey() %>"><%= set.getValue() %> </option>
				<% } %>
			</select></div>
			<div class="form_data"><span>Address:</span><input type="text" name="address"></div>
			<div class="form_data"><span>Options:</span><input type="text" name="options"></div>
			<div class="form_data"><input type="submit" id="button" name="sub" value="Add"></div>
		</div>
		</form>
		</div>
		<hr>
	</div>
	<div class="container">
		<div class="Listdevices">
		<table>
			<th class="list" colspan="6">List</th>
			<tr class="columnName"><td class="column"><span>Id</span></td><td class="column"><span>Type</span></td><td class="column"><span>Name</span></td><td class="column"><span>Interface</span></td><td class="column"><span>Address</span></td><td class="column"><span>Operation</span></td></tr>
			<% for(device dev : main.devices){ %>
				<tr class="data"><td><%= dev.getId() %></td><td><%= dev.getType() %></td><td><%= dev.getName() %></td> <td><%= dev.getInterface() %></td><td><%= dev.getAddress() %></td><td class="btn"><Button onclick="remove(<%= dev.getId() %>)">Remove</button></td></tr>
			<% } %>
		</table>
		</div>
	</div>
	</body>
</html>
