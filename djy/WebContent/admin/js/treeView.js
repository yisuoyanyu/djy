
/**
 * 树形视图对象
 *  @param param {object} JSON数据
 *                  {
 *                      'id' : {string},
 *                      'data' : {function},
 *                      'changed' : {function},
 *                      'isSys' : {boolean}
 *                  }
 */
var TreeView = function(param) {

    var oView = new Object();
    
    oView._id = param.id;
    oView._data = param.data;
    oView._changed = param.changed;
    oView._isSys = typeof(param.isSys)=='boolean'? param.isSys: true;
    
    /**
     * 初始化
     */
    oView.init = function() {
        
        var $jstree = $('#' + oView._id).jstree({
            'core' : {
                'check_callback' : true,
                'data' : oView._data
            }
        });
        
        if ( $.isFunction(oView._changed) ) {
            $jstree.on('changed.jstree', oView._changed);
        }
        
    };
    
    
    /**
     * 获取选中行的id数组
     *  @return {array} 选中行的id数组
     */
    oView.getSelIds = function() {
        var selIds = $('#' + oView._id).jstree('get_selected');
        return selIds;
    };
    
    
    /**
     * 获取选中行的节点值数组
     *  @return {array} 选中行的节点值数组
     */
    oView.getSelVals = function() {
        var vals = new Array();
        
        var nodes = $('#' + oView._id).jstree('get_selected', true);
        for (var i=0; i<nodes.length; i++) {
            var node = nodes[i];
            vals.push(node.original);
        }
        
        return vals;
    };
    
    
    /**
     * 刷新视图
     */
    oView.refresh = function() {
        $('#' + oView._id).jstree('refresh');
    };
    
    
    oView.init();
    
    if ( oView._isSys ) {
        if (typeof(window.Sys_TreeViews) == 'undefined') {
            window.Sys_TreeViews = new Array();
        }
        window.Sys_TreeViews.push(oView);
    }
    
    return oView;
};
