<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<#include "/macro.include"/>

<jsp:include page="../common/pagerForm.jsp">
	<jsp:param value="../${table.shortName}/list" name="formAction"/>
</jsp:include>


<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="../${table.shortName}/list" method="post">
	<input type="hidden" name="numPerPage" value="<@jspEl "page.numPerPage"/>" />
	<input type="hidden" name="navTabId" value="<@jspEl "navTabId"/>" />
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					关键字：<input type="text" name="keyword" value="<@jspEl "keyword"/>" />
				</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="../${table.shortName}/bfAdd?navTabId=<@jspEl "navTabId"/>" target="dialog" mask="true" title="添加${bussinessName}"><span>添加</span></a></li>
			<li><a title="确实要删除这些记录吗?" target="selectedTodo" rel="ids" postType="string" href="../${table.shortName}/delete?navTabId=<@jspEl "navTabId"/>" class="delete"><span>批量删除</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="1%"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th width="5%" orderField="id" class="<@jspEl "orderDirection"/>">ID</th>
			<#list table.columns as column>
			<@generateBycondition column.sqlName>
				<th width="10%" >${column.columnAlias}</th>
			</@generateBycondition>
			</#list>
				<th width="30%" >操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="<@jspEl "page.list"/>" var="${table.shortName}">
			<tr>
				<td><input name="ids" value="<@jspEl "${table.shortName}.id"/>" type="checkbox"></td>
				<td><@jspEl "${table.shortName}.id"/></td>
			<#list table.columns as column>
			<@generateBycondition column.sqlName>
				<td><c:out value="<@jspEl "${table.shortName}.${column.columnNameFirstLower}"/>" /></td>
			</@generateBycondition>
			</#list>
				<td>
					<a title="编辑" target="dialog" mask="true" href="../${table.shortName}/bfEdit?id=<@jspEl "${table.shortName}.id"/>&navTabId=<@jspEl "navTabId"/>" class="btnEdit">编辑</a>
					<a title="查看详情" target="dialog" mask="true" width="800" href="../${table.shortName}/view?id=<@jspEl "${table.shortName}.id"/>&navTabId=<@jspEl "navTabId"/>" class="btnView">查看详情</a>
					<a title="确实要删除${bussinessName}<@jspEl "${table.shortName}.${table.shortName}Name"/>吗?请谨慎操作!" target="ajaxTodo" href="../${table.shortName}/delete?ids=<@jspEl "${table.shortName}.id"/>&navTabId=<@jspEl "navTabId"/>" class="btnDel">删除</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<jsp:include page="../common/panelBar.jsp">
		<jsp:param value="navTab" name="targetType"/>
		<jsp:param value="" name="rel"/>
	</jsp:include>
</div>

