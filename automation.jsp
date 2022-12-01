<%@ page import="com.device"%>
<%@ page import="com.automation"%>
<%@ page import="com.main"%>
<%@ page import="com.DBHelper"%>
<%@ page import="java.util.*"%>
<%@ page import="com.google.gson.Gson"%>

<html>
	<head>
	<title>Add Rule</title>
		<link rel="icon" type="image/x-icon" href="resources/icons/favicon.ico">
		<link rel="stylesheet" href="resources/stylesheets/automation.css">
		<link rel="stylesheet" href="resources/stylesheets/color.css">
		<script src="resources/scripts/common.js"></script>
		<script src="resources/scripts/automation.js"></script>
		<% Gson encoder = new Gson(); %>
		<% Map<String,String> data = new HashMap<>(); %>
		<% for(int i=0;i<main.devices.size();i++) data.putAll(main.devices.get(i).getParam()); %>
		<script> var devs = JSON.parse(' <%= encoder.toJson(data) %>'); </script>
	</head>

	<body onload="loaded()">
	<div class="devices">
		<p class="add">ADD Rule</p>
		<div class="center_from">
		<form id="dat">
		<input type="hidden" name="type" value="automation">
		<div class="form">
			<div class="form_data"><span>Device 1:</span><select id="device1" name="device1">
			<option value="0">Time</option>
			<%for(device dev : main.devices){ %>
				<% if(dev.getType().equals("TempSensor") || dev.getType().equals("PowerSensor")){ %>
				<option value="<%= dev.getId() %>"><%= dev.getName() %> </option>
			<% }} %>
			</select></div>
			<div class="form_data"><span>Device 2:</span><select id="device2" name="device2">
			<%for(device dev : main.devices){ %>
				<% if(dev.getType().equals("IrControl") || dev.getType().equals("RelayControl")){ %>
				<option value="<%= dev.getId() %>"><%= dev.getName() %> </option>
			<% }} %>
			</select></div>
			<div class="form_data"><span>Operation:</span><select id="operation" name="operation">
			<option value="==">==</option>
			<option value=">=">>=</option>
			<option value="<=">&lt=</option>
			<option value=">">&gt </option>
			<option value="<">&lt </option>
			</select></div>
			<div class="form_data"><span class = "compare">If:</span><select id="dev1data" name="device1Param"></select><span id = "compare">==</span><input type="text" name="value"><span class = "compare">then</span><select class = "compare" id="dev2data" name="device2Param"></select></div>
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
			<tr class="columnName"><td class="column"><span>Device1</span></td><td class="column"><span>Device2</span></td><td class="column"><span>Device1param</span></td><td class="column"><span>Operation</span></td><td class="column"><span>value</span></td><td class="column"><span>Device2Param</span></td><td class="column"><span>Action</span></td></tr>
			<% int i = 0;%>
			<% for(automation dev : main.automations){ %>
				<tr class="data"><td><%= dev.getDevice1() %></td><td><%= dev.getDevice2() %></td><td><%= dev.getDevice1Param() %></td><td><%= dev.getOperation() %></td><td><%= dev.getValue() %></td><td><%= dev.getDevice2Param() %></td><td class="btn"><Button onclick="remove(<%= i %>)">Remove</button></td></tr>
				<% i++; %>
			<% } %>
		</table>
		</div>
	</div>
	</body>
</html>