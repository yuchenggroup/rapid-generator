<#include "/java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
<#assign shortName = table.shortName>
/**
 * view - ${table.tableAlias}
 */
Ext.define("ESSM.view.${subpackage}.${className}View",{
	extend: "Ext.panel.Panel",
	alias : "widget.${classNameLower}View",
    requires : [
        'ESSM.store.${subpackage}.${className}Store',
        "ESSM.view.${subpackage}.${className}Grid",
        "ESSM.view.${subpackage}.${className}Form"
    ],
	tbar : [
		{
			xtype : 'authcbutton',
			action : 'create',
			iconCls : 'add',
			disabled : false,
			text :'新增'
		},
		{
			xtype : 'authcbutton',
			action : 'update',
			iconCls : 'edit',
			disabled : true,
			text :'修改'
		}
		,{
			xtype : 'authcbutton',
			action :'delete',
			iconCls : 'delete',
			disabled : false,
			text : '删除'
		}
	],
	items : [
		{
			xtype : "${className}Grid",
			anchor: "100% -60",
			border : false
		}
	]
});