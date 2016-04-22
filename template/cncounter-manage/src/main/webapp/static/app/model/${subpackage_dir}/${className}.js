<#include "/java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
<#assign shortName = table.shortName>
/**
 *model - ${table.tableAlias}
 */
Ext.define('ESSM.model.${subpackage}.${className}',{
	extend: 'Ext.data.Model',
	fields: [
	<#list table.columns as column>
	// ${column.columnAlias}
	'${column.columnNameLower}'<#sep>,
	</#list>
	]
});

