<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<#include "/macro.include"/>

<div class="page">
	<div class="pageContent">
		<div class="pageFormContent" layoutH="56">
			<div class="viewInfo" layoutH="97">
			<#list table.columns as column>
			<@generateBycondition column.sqlName>
				<dl>
					<dt>${column.columnAlias}：</dt>
					<dd><c:out value="<@jspEl "${table.shortName}.${column.columnNameFirstLower}"/>" /></dd>				  
				</dl>
			</@generateBycondition>
			</#list>
				<dl>
					<dt>创建人：</dt>
					<dd><c:out value="<@jspEl "${table.shortName}.createdBy"/>" /></dd>				  
				</dl>
				<dl>
					<dt>创建时间：</dt>
					<dd><fmt:formatDate value="<@jspEl "${table.shortName}.createdDate"/>" pattern="yyyy-MM-dd HH:mm:ss"/></dd>				  
				</dl>
				<dl>
					<dt>最近更新人：</dt>
					<dd><c:out value="<@jspEl "${table.shortName}.lastUpdatedBy"/>" /></dd>				  
				</dl>
				<dl>
					<dt>最近更新时间：</dt>
					<dd><fmt:formatDate value="<@jspEl "${table.shortName}.lastUpdatedDate"/>" pattern="yyyy-MM-dd HH:mm:ss"/></dd>				  
				</dl>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div>
				</li>
			</ul>
		</div>
	</div>
</div>