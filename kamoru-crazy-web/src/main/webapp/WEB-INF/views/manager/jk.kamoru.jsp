<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.lang.management.ManagementFactory"%>
<%@ page import="java.lang.management.MemoryMXBean"%>
<%@ page import="java.lang.management.MemoryUsage"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.text.NumberFormat"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>kAmOrU</title>
<style type="text/css">
.right {
	text-align: right;
}
section > article {
	margin-left: 20px;
}
</style>
</head>
<body>

<header>
	<h1>I'm alive!</h1>
</header>

<section>
	<header>
		<h2>VM status</h2>
	</header>
<%	// Memory Info
	MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
	MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
	MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();
	double heapUsedPercent = ((double)heapMemoryUsage.getUsed()/(double)heapMemoryUsage.getMax()) * 100.0D;
	double nonHeapUsedPercent = ((double)nonHeapMemoryUsage.getUsed()/(double)nonHeapMemoryUsage.getMax()) * 100.0D;
%>
	<article>
		<table>
			<tr>
				<th>Area</th>
				<th>Init (MB)</th>
				<th>Max (MB)</th>
				<th>Used (MB)</th>
				<th>Used(%)</th>
			</tr>
			<tr>
				<td>Heap</td>
				<td class="right"><%=mbytes(heapMemoryUsage.getInit())%></td>
				<td class="right"><%=mbytes(heapMemoryUsage.getMax())%></td>
				<td class="right"><%=mbytes(heapMemoryUsage.getUsed())%></td>
				<td class="right"><%=percent(heapUsedPercent)%></td>
			</tr>
			<tr>
				<td>Non Heap</td>
				<td class="right"><%=mbytes(nonHeapMemoryUsage.getInit())%></td>
				<td class="right"><%=mbytes(nonHeapMemoryUsage.getMax())%></td>
				<td class="right"><%=mbytes(nonHeapMemoryUsage.getUsed())%></td>
				<td class="right"><%=percent(nonHeapUsedPercent)%></td>
			</tr>
		</table>
	</article>
</section>

</body>
</html>
<%!
@SuppressWarnings("unused")
private static String kbytes(long l) {
	NumberFormat nf = new DecimalFormat("#,###");
	return nf.format(l/1024);
}
private static String mbytes(long l) {
	NumberFormat nf = new DecimalFormat("#,###");
	return nf.format(l/1024/1024);
}
private static String percent(double l) {
	NumberFormat nf = new DecimalFormat("#.#");
	return nf.format(l);
}
%>