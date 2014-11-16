<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%-- 
This source is under a creative common license. (Attribution Non-Commercial)
You can use it for non-commercial purposes only.

Author: Dongyoul Kim
Blog: http://greatkim91.tistory.com
Twitter: greatkim91
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Collections"%>
<%@ page import="java.util.Comparator"%>

<%@ page import="java.lang.management.ManagementFactory"%>
<%@ page import="java.lang.management.MemoryMXBean"%>
<%@ page import="java.lang.management.MemoryUsage"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.text.NumberFormat"%>

<%@ page import="java.lang.management.ThreadInfo"%>
<%@ page import="java.lang.management.ThreadMXBean"%>

<%!
	private static String kbytes(long l) {
		NumberFormat nf = new DecimalFormat("#,###");
		return nf.format(l/1000);
	}
	
	private static String percent(double l) {
		NumberFormat nf = new DecimalFormat("#.#");
		return nf.format(l);
	}
	
	@SuppressWarnings({"rawtypes","unchecked"})
	private static List<ThreadInfo> loadThreadInfo() {
		ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
		ThreadInfo[] threadInfoArray = threadMXBean.getThreadInfo(threadMXBean.getAllThreadIds(), Integer.MAX_VALUE);
		
		List<ThreadInfo> threadInfos = new ArrayList<ThreadInfo>(threadInfoArray.length);
		for (ThreadInfo threadInfo : threadInfoArray) {
			threadInfos.add(threadInfo);
		}
		
		Collections.sort(
				threadInfos,
				new Comparator() {
					public int compare(Object o1, Object o2) {
						if (o1 == null || o2 == null) return 0;
						ThreadInfo t1 = (ThreadInfo) o1;
						ThreadInfo t2 = (ThreadInfo) o2;
						return t1.getThreadName().compareTo(t2.getThreadName());
					}
					
				}
				
		);
		return threadInfos;
	}
%>
<html>
<head>
<title>Thread dump</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="Pragma" content="no-cache" />
<style type="text/css">
<!--
/* body {
	font: 12px sans-serif;
}
 */
#body-wapper {
	font: 12px sans-serif;
}
table {
	border: 0px solid #DEDEDE;
}

td, th {
	border: 1px solid #DEDEDE;
	padding: 1px 10px;
}

.right {
	text-align: right;
}
-->
</style>
<script type="text/javascript">
function fnSubmit() {
	var val = document.forms[0].name.value;
	//alert(val.replace(/</gi, '&lt;'));
	document.forms[0].submit();
}
</script>
</head>
<body>
<div id="body-wapper">
<%
	String uri = request.getRequestURI();
	String namePrefix = request.getParameter("name");
	String stateParam = request.getParameter("state");
	long threadId;
	try {
		threadId = Long.parseLong(request.getParameter("tid"));
	} catch(Exception e) {
		threadId = -1;
	}
%>

<%
	// Memory Info
	MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
	MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
	MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();
	
	double heapUsedPercent = ((double)heapMemoryUsage.getUsed()/(double)heapMemoryUsage.getMax()) * 100.0D;
	double nonHeapUsedPercent = ((double)nonHeapMemoryUsage.getUsed()/(double)nonHeapMemoryUsage.getMax()) * 100.0D;
%>

<table>
<tr>
<th>Area</th>
<th>Init (KB)</th>
<th>Max (KB)</th>
<th>Used (KB)</th>
<th>Used(%)</th>
</tr>

<tr>
<td>Heap</td>
<td class="right"><%=kbytes(heapMemoryUsage.getInit())%></td>
<td class="right"><%=kbytes(heapMemoryUsage.getMax())%></td>
<td class="right"><%=kbytes(heapMemoryUsage.getUsed())%></td>
<td class="right"><%=percent(heapUsedPercent)%></td>
</tr>

<tr>
<td>Non Heap</td>
<td class="right"><%=kbytes(nonHeapMemoryUsage.getInit())%></td>
<td class="right"><%=kbytes(nonHeapMemoryUsage.getMax())%></td>
<td class="right"><%=kbytes(nonHeapMemoryUsage.getUsed())%></td>
<td class="right"><%=percent(nonHeapUsedPercent)%></td>
</tr>

</table>

<hr/>
<form method="get">
	Show only what thread name starts with <input type="text" name="name" value="<%=(namePrefix != null ? namePrefix : "")%>"/> and thread state is 
	<select name="state">
		<option value="all">All</option>
		<%
			for(Thread.State threadState: Thread.State.values()) {
				boolean selected = threadState.toString().equals(stateParam); 
		%>
		<option value="<%=threadState%>"<%=selected ? " selected=\"true\"" : ""%>><%=threadState%></option>
		<% } %>
	</select>
	<input type="button" onclick="fnSubmit()" value="go!"/>
</form>
<% if(threadId != -1 || (namePrefix != null && namePrefix.trim().length() > 0)) { %>
<a href="<%=uri%>">Show all threads</a>
<% } %>

<%
	// Thread info
	for(ThreadInfo threadInfo : loadThreadInfo()) {
		StackTraceElement[] stackTraces = threadInfo.getStackTrace();
		
		// filter the current thread
		if (threadInfo.getThreadId() == Thread.currentThread().getId()) continue;
		
		// filter name
		if (namePrefix != null && namePrefix.trim().length() > 0) {
			if (!threadInfo.getThreadName().startsWith(namePrefix)) continue;
		}
		
		// filter state
		if (stateParam != null && !stateParam.equals("all")) {
			if (!threadInfo.getThreadState().toString().equals(stateParam)) continue;
		}
		
		// show only specific thread id
		if (threadId != -1) {
			if (threadInfo.getThreadId() != threadId) continue;
		}
%>
<dl>
<dt>
<b><%=threadInfo.getThreadName()%> (<%=threadInfo.getThreadState()%>)</b> - 
<a href="<%=uri%>?tid=<%=threadInfo.getThreadId()%>"><%=threadInfo.getThreadId()%></a>
</dt>
<dd>
<pre>
<%
		for(int i = 0; i < stackTraces.length; i++) {
			out.println(stackTraces[i]);
		}
%>
</pre>
</dd>
</dl>
<%
	}
%>
</div>
</body>
</html>