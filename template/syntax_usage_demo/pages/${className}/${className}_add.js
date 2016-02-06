<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<#include "/macro.include"/>
<form class="form-horizontal" action="${table.shortName}/add" method="post"
	id="defForm" callfn="refreshTable">
	<div class="modal-header">
		<div class='bootstrap-dialog-header'>
			<div class='bootstrap-dialog-close-button'
				style='display: block;'>
				<button class='close' data-dismiss='modal' aria-label='Close'>×</button>
			</div>
			<div class='bootstrap-dialog-title'>新增${bussinessName}</div>
		</div>
	</div>
	<div class="modal-body">
		<div class="container-fluid">
			<#list table.columns as column>
			<@generateBycondition column.sqlName>
			<div class="form-group">
				<label for="${column.columnNameFirstLower}" class="col-sm-2 control-label">${column.columnAlias}</label>
				<div class="col-sm-7">
			<#if column.sqlName?index_of("desc") == -1>
				<input id="${column.columnNameFirstLower}" name="${column.columnNameFirstLower}" type="text" maxlength="50"
						minlength="2" class="form-control required" placeholder="请输入${column.columnAlias}">
			<#else>
				<textarea id="${column.columnNameFirstLower}" name="${column.columnNameFirstLower}" class="form-control"
						rows="3"></textarea>
			</#if>
				</div>
			</div>
			</@generateBycondition>
			</#list>
		</div>
	</div>
	<div class="modal-footer">
		<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
		<button type="submit" class="btn btn-primary">保存</button>
	</div>
</form>
<script type="text/javascript">
	$("#defForm").validate();
</script>


<div class="pageContent">
	<form:form method="post" action="../${table.shortName}/add" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<input type="hidden" name="navTabId" value="<@jspEl "navTabId"/>" />
		<div class="pageFormContent" layoutH="56">
			<#list table.columns as column>
			<@generateBycondition column.sqlName>
			<p>
				<label>${column.columnAlias}：</label>
			<#if column.sqlName?index_of("_desc") == -1>
				<input name="${column.columnNameFirstLower}" class="required" type="text" size="30" maxlength="50" alt="请输入${column.columnAlias}" />
			<#else>
				<textarea name="${column.columnNameFirstLower}" cols="30" rows="6" alt="简介/备注"></textarea>
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
