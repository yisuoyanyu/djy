

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

