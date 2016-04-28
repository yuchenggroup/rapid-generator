<#include "/java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
<#assign shortName = table.shortName>
/**
 * store - ${table.tableAlias}
 */
Ext.define('ESSM.store.${subpackage}.${className}Store',{
	extend: 'Ext.data.Store',
	// autoLoad : true,
	model : 'ESSM.model.${subpackage}.${className}',
	remoteSort : true,
	pageSize : 20,
	proxy : {
		type: 'ajax',
		api : {
			read:'rest/${subpackage}/${classNameLower}/list.json',
			create:'rest/${subpackage}/${classNameLower}/add.json',
			update:'rest/${subpackage}/${classNameLower}/edit.json',
			destroy:'rest/${subpackage}/${classNameLower}/delete.json',
		},
        actionMethods: {
            read   : 'POST' // by default GET
        },
		reader: {
			type: 'json',
			root: 'data',
			totalProperty: 'total'
		},
		limitParam : 'pageSize',
		pageParam :'page',

	},
	sorters : [{
		property : 'id',
		direction : 'asc'
	}]
});
