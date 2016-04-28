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
		ref : 'view',
		selector : '${classNameLower}View'
	},{
		ref : 'grid',
		selector : '${classNameLower}Grid'
	}],
	getMainView : function(){
		return this.getView('${subpackage}.${className}Grid');
	},
	
	init : function() {
		this.control({
			'grid': 
			{
				'itemdblclick':function (grid,row){
					this.onUpdate${className}();
				},
                //选择行事件
                'select' : function(view,record,item,index,e,oPts) {
                    //
                    this.getGrid().query('button[action=update]')[0].enable();
                    this.getGrid().query('button[action=delete]')[0].enable();
                },
                'deselect' : function(view,record,item,index,e,oPts) {
                    //
                    this.getGrid().query('button[action=update]')[0].disable();
                    this.getGrid().query('button[action=delete]')[0].disable();
                },
                //渲染显示事件
                'render' : function() {
                    //加载数据
                    this.onQuery();
                }
			},
            'grid button[action=create]' : {
                click : this.onCreate${className}
            },
            'grid button[action=update]' : {
                click : this.onUpdate${className}
            },
            'grid button[action=delete]' : {
                click : this.onDelete${className}
            }
		});
        // 如果有某些需要延迟执行的操作
        var me = this;
        window.setTimeout(function(){
            //me.onQuery();
        }, 1);
	},
	
	/**
	 *新增${table.tableAlias}
	 */
	onCreate${className} : function() {
		var me = this;
        var title = '新增${table.tableAlias}';
        var win = me.getEditWin(title, null , me.saveFn, me);
        win && win.show();
	},
	
	/**
	 *更新${table.tableAlias}
	 */
	onUpdate${className} : function() {
        var me = this;
        var title = '修改${table.tableAlias}';
        //
        var record = this.getGrid().getSelectionModel().getLastSelected();
        if(!record || !record.data) {
            Ext.MessageBox.alert(title ,'请选择一条记录！');
            return;
        }
        //
        var win = me.getEditWin(title, record, me.saveFn, me);
        win && win.show();
	},
	
	/**
	 * 删除${table.tableAlias}
	 */
	onDelete${className} : function(){
        var me = this;
		var records = this.getGrid().getSelectionModel().getSelection();
        var url = this.getGrid().getStore().getProxy().api['destroy'];
		if(records.length==0) {
            Ext.MessageBox.alert('提示','请选择一条记录！');
            return;
		}
		Ext.MessageBox.confirm('提示','您确实要删除选定的记录吗？', function(btn){
			if(btn=='yes'){
				Ext.Ajax.request({
					url : url,
					method : 'POST',
					params : {id : records[0].get('id')},
					success: function(){
						Ext.MessageBox.alert("成功","删除成功！");
						//me.onQuery();
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
		//
        var me = this;
        var store = me.getGrid().getStore();
        var params = me.getQueryParams();
        //
        if(!this.validateQueryParam(params)){
            return false;
        }
        //
        store.proxy.extraParams = params;
        // 加载1页
        store.loadPage(1);
	},
    /**
     * 获取查询参数
     * @returns {{id: string}}
     */
    getQueryParams : function(){
        var me = this;
        var id = "";
        var id_input = me.getGrid().query("input[name=id]")[0];
        if(id_input){
            id = id_input.getValue();
        }
        //
        var params = {
            //'id' : id
        };
        return params;
    },
    validateQueryParam : function(params){
        //
        var id = params["id"];
        if(id && id < 0){
            wr.alertError("id不能为负数!");
            return false;
        }
        return true;
    },

    getEditWin : function(title, record, saveFn, saveFnContext){
        //
        var editForm = Ext.create('ESSM.view.${subpackage}.${className}Form',{
            flex:8,
            saveFn : saveFn,
            saveFnContext : saveFnContext
        });
        if(!editForm){
            return null;
        }
        if(record){
            window.setTimeout(function(){
                editForm.loadRecord(record);
                // 如果要求明确赋值... 可在此处处理
            }, 10);
        }
        //
        var winCfg = {
            title :  title || '编辑',
            layout:{
                type:'vbox',
                align:'stretch'
            },
            items :[editForm],
            height: 360,
            width: 480,
            //maximized : true,
            modal : true,
            border : 0,
            waitMsgTarget : true,
            buttonAlign :  'center'
        };
        var win = new Ext.window.Window(winCfg);
        return win;
    },

    saveFn: function (editForm){
        var me = this;
        //
        var baseForm = editForm.getForm();
        if (!baseForm.isValid()) {
            //Ext.MessageBox.alert('提示' ,'请输入相关参数！');
            return;
        }
        //
        var params = getFormParam(editForm);
        //
        var loadMask = new Ext.LoadMask(editForm, {msg : '正在处理...'});
        loadMask.show();
        //
        if(params["id"]){
            var url = this.getGrid().getStore().proxy.api.update;
        } else {
            var url = this.getGrid().getStore().proxy.api.create;
        }
        //
        Ext.Ajax.request({
            url : url,  //地址
            method : 'POST',
            params : params,
            success : function(response) {
                if(ESSM.processResultData(response)) {
                    editForm.up("window").close();
                    // 取消选中
                    me.getGrid().getSelectionModel().deselectAll();
                    me.onQuery();
                }
            },
            callback :  function() {
                loadMask.hide();
            }
        });
        function getFormParam(editForm){
            if(!editForm){
                return {};
            }
            var me = this;
            //
            var values = editForm.getForm().getValues();
            //
            var params = values;
            //
            return params;
        }
    }
});