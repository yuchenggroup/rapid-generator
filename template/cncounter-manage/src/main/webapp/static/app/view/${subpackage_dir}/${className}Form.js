<#include "/java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
<#assign shortName = table.shortName>
/**
 * form - ${table.tableAlias}
 */
Ext.define("ESSM.view.${subpackage}.${className}Form",{
	extend:"Ext.form.Panel",
	alias:"widget.${classNameLower}Form",
	width:600,
	bodyPadding: '10',
	border : 0,
	fieldDefaults: {
        msgTarget: 'side',
        labelWidth: 90,
        anchor : '80%'
    },
    initComponent : function(){
		this.items =  [
		<#list table.columns as column>
			{
				xtype: 'textfield',
				fieldLabel: '${column.columnAlias}',
				// hidden:true,
				// allowBlank: false,
				tooltip: '请输入${column.columnAlias}',
				name:'${column.columnNameLower}'
			}<#sep>,
			</#list>
		],
		this.callParent();
	}
});