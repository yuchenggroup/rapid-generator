<#include "/java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
<#assign shortName = table.shortName>
/**
 * grid - ${table.tableAlias}
 */
Ext.define("ESSM.view.${subpackage}.${className}Grid", {
	extend: "Ext.grid.Panel",
	alias: "widget.${classNameLower}Grid",
	store: "${subpackage}.${className}Store",
	viewConfig: {
		loadMask: true
	},
	initComponent : function(){
		this.selModel = Ext.create('Ext.selection.CheckboxModel', {mode: "SINGLE", allowDeselect: true});
		this.callParent();
	},
    tbar: [
		{
			xtype: 'authcbutton',
			action: 'create',
			iconCls: 'add',
			text: '新增'
		},
		{
			xtype : 'authcbutton',
			action : 'update',
			iconCls : 'edit',
			disabled : true,
			text :'修改'
		},
			{
			xtype : 'authcbutton',
			action :'delete',
			iconCls : 'delete',
			disabled : true,
			text : '删除'
		}
	],

	columns: [
		<#list table.columns as column>
				{
					header: '${column.columnAlias}', 
					dataIndex: '${column.columnNameLower}', 
					width: 120,
					align:'left',
					// hidden: true,
					renderer : function(value, row, record) {
						if(value) {
							return value;
						}
						return "";
					}
				}<#sep>,
		</#list>
	],
	bbar: {
		xtype: 'pagingtoolbar',
		store: '${subpackage}.${className}Store',
		displayInfo: true,
		displayMsg: '显示 {0} - {1} 条，共计 {2} 条',
		emptyMsg: '无数据'
	}
});