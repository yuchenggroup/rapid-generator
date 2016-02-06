<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<#include "/macro.include"/>

<div class="pageContent">
	<form:form method="post" action="../${table.shortName}/edit" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<input type="hidden" name="navTabId" value="<@jspEl "navTabId"/>" />
		<input type="hidden" name="id" value="<@jspEl "${table.shortName}.id"/>" />
		<div class="pageFormContent" layoutH="56">
			<#list table.columns as column>
			<@generateBycondition column.sqlName>
			<p>
				<label>${column.columnAlias}：</label>
			<#if column.sqlName?index_of("_desc") == -1>
				<input name="${column.columnNameFirstLower}" class="required" type="text" value="<@jspEl "${table.shortName}.${column.columnNameFirstLower}"/>" size="30" maxlength="50" alt="请输入${column.columnAlias}" />
			<#else>
				<textarea name="${column.columnNameFirstLower}" cols="30" rows="6" alt="简介/备注"><@jspEl "${table.shortName}.${column.columnNameFirstLower}"/></textarea>
			</#if>
			</p>
			</@generateBycondition>
			</#list>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form:form>
</div>
