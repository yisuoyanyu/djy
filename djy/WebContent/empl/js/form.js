
/**
 * 关闭当前表单
 *  @param param {object} JSON数据
 *                  {
 *                      refreshOpener : {boolean},    //true — 刷新父窗口
 *                      reloadOpener : {boolean}      //true — 重新加载父窗口
 *                  }
 */
function closeForm( param ) {
    var winName = window.name;
    
    if ( winName.startWith( 'J_iframe_' ) ) {
        var index = winName.substr(9);
        var param1 = { 'dataIndex' : index };
        if ( param ) {
            param1.refreshOpener = param.refreshOpener;
            param1.reloadOpener = param.reloadOpener;
        }
        parent.closeMenuTab( param1 );
    } else {
        if ( param ) {
            if ( param.refreshOpener ) {
                refreshOpener();
            } else if ( param.reloadOpener ) {
                reloadOpener();
            }
        }
        window.close();
    }
    
}


/**
 * 刷新当前表单
 *  @param param {object} JSON数据
 *                  {
 *                      refreshOpener : {boolean},    //true — 刷新父窗口
 *                      reloadOpener : {boolean}      //true — 重新加载父窗口
 *                  }
 */
function refreshForm( param ) {
    var winName = window.name;
    
    if ( winName.startWith('J_iframe_') ) {
        var index = winName.substr(9);
        var param1 = { 'dataIndex' : index };
        if ( param ) {
            param1.refreshOpener = param.refreshOpener;
            param1.reloadOpener = param.reloadOpener;
        }
        parent.refreshMenuTab( param1 );
    } else {
        if ( param ) {
            if ( param.refreshOpener ) {
                refreshOpener();
            } else if ( param.reloadOpener ) {
                reloadOpener();
            }
        }
        refreshWin();
    }
    
}


/**
 * 刷新打开本窗口的父窗口
 */
function refreshOpener() {
    var opener = window.opener;
    if (typeof(opener)=='undefined')
        return;
    
    if ( $.isFunction(opener.refreshWin) ) {
        opener.refreshWin();
    } else {
        opener.location.reload();
    }
    
}


/**
 * 重载打开本窗口的父窗口
 */
function reloadOpener() {
    var opener = window.opener;
    if (typeof(opener)=='undefined')
        return;
    
    opener.location.reload();
}


//--------------------------------------------------------------

/**
 * 上传控件 预先提交
 *  @param param {object} JSON数据
 *                  {
 *                      'success' : {function},
 *                      'error' : {function}
 *                  }
 */
function preUpload( param ) {
    
    var win = window;
    
    var fileUploads = win.Sys_FileUploads;
    var imageUploads = win.Sys_ImageUploads;
    
    if ( !$.isArray( fileUploads ) && !$.isArray( imageUploads ) ) {
        param.success.call();
    } else {
        var uploads = new Array();
        if ( $.isArray( fileUploads ) )
            uploads = uploads.concat( fileUploads );
        if ( $.isArray( imageUploads ) )
            uploads = uploads.concat( imageUploads );
        
        ( function submitUploads( uploads ) {
            
            if ( uploads.length == 0 ) {
                param.success.call();
                return;
            }
            
            var upload = uploads.pop();
            upload.submit( {
                success : function() {
                    submitUploads( uploads );
                }
            } );
            
        } )( uploads );
        
    }
    
}

