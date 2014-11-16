<%@ tag language="java" pageEncoding="UTF-8" body-content="tagdependent"%>
<%@ taglib prefix="c"    uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s"    uri="http://www.springframework.org/tags"%>

<div>
	<a class="label" onclick="$('#stack-trace').toggle()"><s:message code="default.stacktrace"/></a>
	<a class="label" onclick="$('#request-attribute').toggle()"><s:message code="default.request-attribute"/></a>
	<a class="label" onclick="$('#session-attribute').toggle()"><s:message code="default.session-attribute"/></a>
</div>

<div id="stack-trace" style="display:none;">
	<h4>${exception}</h4>
	<ul class="stack-view">
		<c:forEach items="${exception.stackTrace}" var="stackTrace">
		<li><code class="code-value">${stackTrace }</code>
		</c:forEach>
	</ul>
</div>

<div id="request-attribute" style="display:none;">
	<h4><s:message code="default.request-attribute"/></h4>
	<ol class="code-view">
<%
	@SuppressWarnings("rawtypes")
	java.util.Enumeration e = request.getAttributeNames();
	while (e.hasMoreElements()) {
		String name = (String)e.nextElement();
		Object value = request.getAttribute(name);
		String clazz = value.getClass().getName();
%>
		<li><code class="code-name"><%=name %></code>   
			<code class="code-value"><%=value %></code>
			<code class="code-clazz"><%=clazz %></code>
		</li>
<%
	}
%>
	</ol>
</div>

<div id="session-attribute" style="display:none;">
	<h4><s:message code="default.session-attribute"/></h4>
	<ol class="code-view">
<%
	@SuppressWarnings("rawtypes")
	java.util.Enumeration names = session.getAttributeNames();
	while (names.hasMoreElements()) {
		String name = (String)names.nextElement();
		Object value = session.getAttribute(name);
		String clazz = value.getClass().getName();
		%>
		<li><code class="code-name"><%=name %></code>   
			<code class="code-value"><%=value %></code>
			<code class="code-clazz"><%=clazz %></code>
		</li>
<%
	}
%>
	</ol>
</div>
	