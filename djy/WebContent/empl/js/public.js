

String.prototype.startWith = function ( str ) {
    if ( str==null || str=="" || this.length==0 || str.length>this.length )
      return false;  
    if ( this.substr( 0, str.length ) == str )
      return true;
    else
      return false;
}


String.prototype.endWith = function ( str ) {
    if ( str==null || str=="" || this.length==0 || str.length>this.length )
      return false;
    if ( this.substring( this.length - str.length ) == str )
      return true;
    else
      return false;
}


Number.prototype.add = function ( addend ) {
    var r1, r2, m; 
    try {
        r1 = this.toString().split(".")[1].length;
    } catch (e) {
        r1 = 0;
    } 
    try {
        r2 = addend.toString().split(".")[1].length;
    } catch (e) {
        r2 = 0;
    } 
    m = Math.pow(10, Math.max(r1, r2)); 
    return (this * m + addend * m) / m;
}


Number.prototype.sub = function ( subtrahend ) {
    var r1, r2, m, n;
    try {
        r1 = this.toString().split(".")[1].length;
    } catch (e) {
        r1 = 0;
    }
    try {
        r2 = subtrahend.toString().split(".")[1].length;
    } catch (e) {
        r2 = 0;
    }
    m = Math.pow(10, Math.max(r1, r2));
    n = (r1 >= r2) ? r1 : r2;
    return ((this * m - subtrahend * m) / m).toFixed(n);
}

/**
 * 日期格式转为字符串
 *  @param fmt {string} yyyy-MM-dd HH:mm:ss SSS
 */
Date.prototype.format = function ( fmt ) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "H+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if ( /(y+)/.test(fmt) )
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o) {
        if ( new RegExp("(" + k + ")").test(fmt) )
            fmt = fmt.replace( RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)) );
    }
    return fmt;
};


//高效字符串拼接
function StringBuffer() {
    this.buffer = [];
}
StringBuffer.prototype.append = function append( string ) {
    this.buffer.push(string);
    return this;
};
StringBuffer.prototype.toString = function toString(  ) {
    return this.buffer.join("");
};

//--------------------------------------------------------------

//扩展$
( function ($) {
    
    // 判断 obj 是否为'undefined'类型 或 null
    $.isNull = function( obj ) {
        return ( typeof( obj ) == 'undefined' || obj==null ) ? true : false;
    }
    
    // 获取url参数
    $.getUrlParam = function ( name ) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if ( $.isNull )
            return null;
        return decodeURIComponent(r[2]);
    }
    
} )( jQuery );


/**
 * 限制文本框只能输入数值（注：可以是小数）
 * 使用方法：
 * $('选择器').onlyNum();
 */
$.fn.onlyNum = function (  ) {
    $( this ).keypress( function (event) {
        var eventObj = event || e;
        var keyCode = eventObj.keyCode || eventObj.which;
        
        if ( keyCode == 8 || keyCode == 9 || keyCode == 46 || keyCode >= 37 && keyCode <= 40 )
            return true;
        
        if ( /^[0-9]+(\.[0-9]+)?$/.test( this.value + String.fromCharCode(keyCode) + 0 ) )
            return true;
        else
            return false;
    } ).focus( function () {
        // 禁用输入法
        this.style.imeMode = 'disabled';
    } ).bind( "paste", function () {
        // 获取剪切板的内容
        var clipboard = window.clipboardData.getData("Text");
        if (/^\d+$/.test(clipboard))
            return true;
        else
            return false;
    } );
    
    $(this).blur(function() {
        this.value = this.value.replace(/[^(0-9|\.)]+/, '');
    });
};


/**
 * 限制文本框只能输入字母
 * 使用方法：
 * $('选择器').onlyAlpha();
 */
$.fn.onlyAlpha = function () {
    $( this ).keypress( function (event) {
        var eventObj = event || e;
        var keyCode = eventObj.keyCode || eventObj.which;
        if ( (keyCode >= 65 && keyCode <= 90) 
                || (keyCode >= 97 && keyCode <= 122) )
            return true;
        else
            return false;
    } ).focus( function () {
        // 禁用输入法
        this.style.imeMode = 'disabled';
    } ).bind( "paste", function () {
        // 获取剪切板的内容
        var clipboard = window.clipboardData.getData("Text");
        if ( /^[a-zA-Z]+$/.test(clipboard) )
            return true;
        else
            return false;
    } );
};


/**
 * 限制文本框只能输入数字和字母
 * 使用方法：
 * $('选择器').onlyNumAlpha();
 */
$.fn.onlyNumAlpha = function () {
    $( this ).keypress( function (event) {
        var eventObj = event || e;
        var keyCode = eventObj.keyCode || eventObj.which;
        if ( (keyCode >= 48 && keyCode <= 57) 
                || (keyCode >= 65 && keyCode <= 90) 
                || (keyCode >= 97 && keyCode <= 122) )
            return true;
        else
            return false;
    } ).focus( function () {
        // 禁用输入法
        this.style.imeMode = 'disabled';
    } ).bind( "paste", function () {
        // 获取剪切板的内容
        var clipboard = window.clipboardData.getData("Text");
        if ( /^(\d|[a-zA-Z])+$/.test(clipboard) )
            return true;
        else
            return false;
    } );
};


//--------------------------------------------------------------


/**
 * 弹出alert窗口
 *  @param param {object} JSON数据
 *                  {
 *                      msg : {string},
 *                      ok : {function}
 *                  }
 */
function Alert( param ) {
    var obj;
    if ( typeof(param)=='undefined' || param==null ) {
        obj = {};
    } else if ( typeof(param)=='number' || typeof(param)=='string' || typeof(param)=='boolean' ) {
        obj = {msg : param};
    } else {
        obj = param;
    }
    
    if ( typeof(obj.msg) == 'undefined' || obj.msg==null || obj.msg=='' )
        obj.msg = '　';
    
    layer.open( {
        title : '信息提示', 
        content : obj.msg, 
        yes : function ( index ) {
            layer.close(index);
            if (typeof(obj.ok) == 'function')
                obj.ok();
        }, 
        cancel : function ( index ) {
            layer.close( index );
            if ( typeof( obj.ok ) == 'function' )
                obj.ok();
        }, 
        scrollbar : false
    } );
    
}


/**
 * 弹出confirm窗口
 *  @param param {object} JSON数据
 *                  {
 *                      'msg' : {string},
 *                      'cancelBtn' : {string},
 *                      'okBtn' : {string},
 *                      'cancel' : {function},
 *                      'ok' : {function}
 *                  }
 */
function Confirm( param ) {
    var obj = ( typeof(param)=='undefined' || param==null ) ? {} : param;
    
    if ( typeof(obj.msg) == 'undefined' || obj.msg==null )
        obj.msg = '';
    if ( typeof(obj.cancelBtn) == 'undefined' || obj.cancelBtn==null )
        obj.cancelBtn = '取消';
    if ( typeof(obj.okBtn) == 'undefined' || obj.okBtn==null )
        obj.okBtn = '确定';
    
    layer.confirm( obj.msg, {
        title : '信息提示',
        btn: [obj.okBtn, obj.cancelBtn]
    }, function ( index, layero ) {
        layer.close( index );
        if ( typeof(obj.ok) == 'function' )
            obj.ok();
    }, function ( index ) {
        if ( typeof(obj.cancel) == 'function' )
            obj.cancel();
    } );
    
}


//--------------------------------------------------------------


/**
 * 打开窗口
 *  @param param {object} JSON数据
 *                  {
 *                      'title' : {string},     // 窗口标题
 *                      'target' : {string},    // 目标位置。'_blank'—新窗口，'menuTab'—页签窗口。默认'_blank'。
 *                      'url' : {string}        // 窗口页面url
 *                  }
 */
function openWin( param ) {
    if ( typeof(param) == 'string' ) {
        url = param;
        window.open(url);
        return;
    }
    var url = param.url;
    var target = param.target;
    if ( target == "menuTab" ) {
        if ( $.isFunction( top.openMenuTab ) ) {
            var title = param.title;
            top.openMenuTab({
                menuName : title,
                dataUrl : url,
                dataOpener : window.name
            });
            return;
        }
        target = "_blank";
    }
    
    window.open(url, target);
}

/**
 * 刷新当前窗口数据
 */
function refreshWin() {
    
    var win = window;
    if ( $.isFunction( win.Sys_Refresh ) ) {
        win.Sys_Refresh();
    } else {
        var reloadWin = true;
        
        var listViews = win.Sys_ListViews;
        if ( $.isArray( listViews ) ) {
            for ( var i=0; i<listViews.length; i++ ) {
                var oView = listViews[i];
                oView.refresh();
                reloadWin = false;
            }
        }
        
        var treeViews = win.Sys_TreeViews;
        if ( $.isArray( treeViews ) ) {
            for ( var i=0; i<treeViews.length; i++ ) {
                var oView = treeViews[i];
                oView.refresh();
                reloadWin = false;
            }
        }
        
        if ( reloadWin )
            win.location.reload();
    }
}
