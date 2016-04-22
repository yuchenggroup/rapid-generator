<#include "/java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
<#assign shortName = table.shortName>
/**
 * controller - ${table.tableAlias}
 */
Ext.define('ESSM.controller.${subpackage}.${className}Controller', {
	extend  : 'Ext.app.Controller',
	views : ['${subpackage}.${className}View','${subpackage}.${className}Form','${subpackage}.${className}Grid'],
	stores : ['${subpackage}.${className}Store'],
	models : ['${subpackage}.${className}'],
	refs : [{
		ref : 'form',
		selector : '${classNameLower}Form'
	},{
		ref : 'grid',
		selector : '${classNameLower}Grid'
	}],
	getMainView : function(){
		return this.getView('${subpackage}.${className}Grid');
	},
	
	init : function() {
		this.control({

			'${classNameLower}Grid': {
				'itemdblclick':function (grid,row){
					this.onUpdate${className}();
				}
			},
			'${classNameLower}Grid button[action=create]' : {
				click : this.onCreate${className}
			}
		});
	},
	
	/**
	 *新增${table.tableAlias}
	 */
	onCreate${className} : function() {
		var me = this;
	},
	
	/**
	 *更新${table.tableAlias}
	 */
	onUpdate${className} : function() {
		var records = this.getGrid().getSelectionModel().getSelection(),
		me = this;
		if(records.length==0) {
			Ext.MessageBox.alert('提示','请选择一条记录！');
			return;
		}
		var record = records[0];
		this.getForm().loadRecord(record);
	},
	
	/**
	 * 删除${table.tableAlias}
	 */
	onDelete${className} : function(){
		var records = this.getGrid().getSelectionModel().getSelection(),
			url = this.getGrid().getStore().getProxy().api['remove'],
			me = this;
		if(records.length==0) {
			Ext.MessageBox.alert('提示','请选择一条记录！');
			return;
		}
		Ext.MessageBox.confirm('提示','您确实要删除选定的记录吗？', function(btn){
		if(btn=='yes'){
			Ext.Ajax.request({
				url : url,
				params : {id : records[0].get('id')},
				success:function(){
					Ext.MessageBox.alert("成功","删除成功！");
					me.getGrid().getStore().load();
				}
			});
		}
	});
	},
	
	/**
	 *查询
	 */
	onQuery : function(btn) {
		var me = this,
		 	form = btn.up('form'),
			values = form.getForm().getValues();
		
		//查询
		me.getStore('${subpackage}.${className}Store').loadPage(1,{
			params : values
		});
	}
});