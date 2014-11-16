<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.concurrent.*" %>
<%@ page import="jk.kamoru.util.*" %>
<% 
String 	 ip  	 = WebUtils.getParameter(request, "ip", "127.0.0.1");
int 	 port_s  = WebUtils.getParameterInt(request, "ports", 0);
int 	 port_e  = WebUtils.getParameterInt(request, "porte", 0);
String[] portArr = WebUtils.getParameterArray(request, "portArr", ",");

List<Integer> ports = new ArrayList<Integer>(); 
if (port_s > 0 && port_s < port_e)
	for(int port=port_s; port<= port_e; port++)
		ports.add(port);
else if (portArr != null)
	for(String port : portArr)
		if (port.trim().length() > 0)
			ports.add(Integer.parseInt(port.trim()));

final List<Future<Object[]>> futures = new ArrayList<Future<Object[]>>(); 
if (ports.size() > 0) {
	final ExecutorService es = Executors.newFixedThreadPool(50);
	final int timeout = 200;
	for(int port : ports) {
		futures.add(NetUtils.portIsOpen(es, ip, port, timeout));
	}
	es.shutdown();
}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Port Sacn</title>
<style type="text/css">
#postScan-container {
}
.result-li {
	display: inline-block;
	border:1px solid orange;
	font-size: 9pt;
	color: gray;
	border-radius: 5px;
	padding:2px;
}
.result-listen {
	color: red;
}
.result-close {
	display: none;
}
</style>
</head>
<body>

<div id="postScan-container">
	<h2>port scan</h2>
 
 	<form>
 		<div>
	 		<label>IP Address
	 			<input name="ip" size="11" value="<%=ip %>" placeHolder="ip address"></label>
	 		<label>Port from
	 			<input name="ports" size="5" value="<%=port_s %>" placeHolder="port"></label>
	 		<label>to
	 			<input name="porte" size="5" value="<%=port_e %>" placeHolder="port"></label>
	 		<input type="submit">
 		</div>
 		<textarea name="portArr" style="width:600px; height:100px;" placeHolder="ex. 8080, 8081, 8082"><%=ArrayUtils.toStringComma(portArr) %></textarea>
 	</form>
 	
 	<div>
		<h3>Result - <span class="result-listen">LISTEN</span></h3>
 		<ol>
<%
for (final Future<Object[]> f : futures) {
	Object[] result = f.get();
	//System.out.println(String.format("%s : %s : %s", result[0], result[1], result[2]));
%> 	
		<li class="result-li <%=Boolean.valueOf(result[2].toString()) ? "result-listen" : "result-close" %>"><%=String.format("%s", result[1]) %></li>
<%	
}
%> 	
		</ol>
	</div>
</div>

</body>
</html>
<%! 
%>