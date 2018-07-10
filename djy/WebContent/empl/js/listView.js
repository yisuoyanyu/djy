
/**
 * 列表视图对象
 *  @param param {object} JSON数据
 *                  {
 *                      'id' : {string},
 *                      'height' : {number},
 *                      'rowAttributes' : {function},
 *                      'onClickRow' : {function}
 *                  }
 */
var ListView = function(param) {

    var oView = new Object();
    
    oView._id = param.id;
    oView._height = param.height;
    oView._rowAttributes = param.rowAttributes;  //行属性
    oView._onClickRow = param.onClickRow;        //行点击事件
    
    oView._searchParams = null;
    
    
    oView._queryParams = function(params) {  // 查询参数
        var obj = {
            'pbPageSize' : params.limit,    //页面大小
            'pbStart' : params.offset,      //页面开始的索引号
            'sortOrder' : params.order,
            'sortName' : params.sort,
            'searchText' : params.search
        };
        
        var p = oView._searchParams;
        if (p != null) {
            for (var k in p) {
                obj[k] = p[k];
            }
        }
        
        return obj;
    };
    
    
    /**
     * 初始化视图
     */
    oView.init = function() {
        var obj = {
            'queryParams' : oView._queryParams
        };
        
        var $view = $('#' + oView._id);
        
        if ( typeof($view.attr('data-content-type')) == 'undefined' ) {
            obj.contentType = 'application/x-www-form-urlencoded; charset=UTF-8';
        }
        
        if ( typeof($view.attr('data-method')) == 'undefined' ) {
            obj.method = 'post';
        }
        
        if ( typeof($view.attr('data-cache')) == 'undefined' ) {
            obj.cache = false;
        }
        
        if ( typeof($view.attr('data-page-size')) == 'undefined' ) {
            obj.pageSize = 10;
        }
        
        if ( typeof($view.attr('data-page-list')) == 'undefined' ) {
            obj.pageList = [5, 10, 25, 50, 100];
        }
        
        if ( $.isNumeric(oView._height) ) {
            obj.height = oView._height;
        } else if ( typeof($view.attr('data-height')) == 'undefined' ) {
            if (obj.pageSize == 10)
                obj.height = 470;
        }
        
        if ( $.isFunction(oView._rowAttributes) ) {
            obj.rowAttributes = oView._rowAttributes;
        }
        
        if ( $.isFunction(oView._onClickRow) ) {
            obj.onClickRow = oView._onClickRow;
        }
        
        $view.bootstrapTable(obj);
    };
    
    
    /**
     * 执行查询
     *  @param params {object} JSON数据
     */
    oView.search = function(params) {
        $('#' + oView._id).bootstrapTable('destroy');
        oView._searchParams = params;
        oView.init();
    };
    
    
    /**
     * 清除搜索
     */
    oView.clearSearch = function() {
        if (oView._searchParams != null) {
            $('#' + oView._id).bootstrapTable('destroy');
            oView._searchParams = null;
            oView.init();
        }
    };
    
    
    /**
     * 获取选中行的id数组
     *  @return {array} 选中行的id数组
     */
    oView.getSelIds = function() {
        return $.map($('#' + oView._id).bootstrapTable('getSelections'), function (row) {
            return row.id;
        });
    };
    
    
    /**
     * 刷新视图
     */
    oView.refresh = function() {
        $('#' + oView._id).bootstrapTable('refresh');
    };
    
    
    /**
     * 重设视图参数
     */
    oView.resetView = function(param) {
        if ( $.isNumeric(param.height) ) {
            oView._height = param.height;
        }
        $('#' + oView._id).bootstrapTable('resetView', param);
    };
    
    
    oView.init();
    
    if (typeof(window.Sys_ListViews) == 'undefined') {
        window.Sys_ListViews = new Array();
    }
    window.Sys_ListViews.push(oView);
    
    return oView;
};
