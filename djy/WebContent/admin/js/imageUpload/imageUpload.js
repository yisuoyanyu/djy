
/**
 * 图片上传控件对象
 *  @param param {object} JSON数据
 *                  {
 *                      // 容器的id
 *                      'id' : {string}, 
 *                      
 *                      // 是否允许编辑。默认true。
 *                      // true — 允许编辑（上传、删除），false — 不允许编辑。
 *                      'edit' : {boolean}, 
 *                      
 *                      // 是否初始化“上传”按钮。默认false。
 *                      // true — 显示，false — 不显示。
 *                      'showUpload' : {boolean}, 
 *                      
 *                      // 文件数限制（含已上传、未上传）。默认不限制。
 *                      'fileNumLimit' : {int}, 
 *                      
 *                      // 允许上传文件的后缀，多值用“|”分隔，默认“.gif|.jpg|.jpeg|.bmp|.png”
 *                      'acceptSuffix' : {string}, 
 *                      
 *                      // 临时上传文件夹属性
 *                      'tmpUpload' : {
 *                          
 *                          // 上传到临时文件夹的文件路径参数名称（后端通过该参数名称取值）
 *                          'param' : {string}, 
 *                          
 *                          // 上传临时文件夹路径
 *                          'path' : {string}
 *                          
 *                      }, 
 *                      
 *                      // 目标文件夹属性
 *                      'uploadPath' : {
 *                          
 *                          // 目标文件夹参数名称（后端通过该参数名称取值）
 *                          'param' : {string}, 
 *                          
 *                          // 目标文件夹路径值
 *                          'value' : {string}
 *                          
 *                      }, 
 *                      
 *                      // 原有图片的属性
 *                      'initImg' : {
 *                          
 *                          // 原有图片的参数名称（后端通过该参数名称取值）
 *                          'param' : {string}, 
 *                          
 *                          // 初始化的图片列表
 *                          'imgs' : [
 *                              {
 *                                  // 图片标题
 *                                  'title' : {string}, 
 *                                  
 *                                  // 图片相对路径
 *                                  'imgPath' : {string}, 
 *                                  
 *                                  // 原图url源路径
 *                                  'imgSrc' : {string}, 
 *                                  
 *                                  // 缩略图url源路径
 *                                  'imgThumb' : {string}
 *                              },
 *                              ……
 *                          ]
 *                          
 *                      }
 *                      
 *                  }
 */
var ImageUpload = function(param) {
    
    var oUpload = new Object();
    
    oUpload._id = param.id;
    
    oUpload._edit = typeof(param.edit)=='boolean' ? param.edit : true;
    
    oUpload._showUpload = typeof(param.showUpload)=='boolean' ? param.showUpload : false;
    
    oUpload._fileNumLimit = param.fileNumLimit;
    
    var acceptSuffix = param.acceptSuffix;
    if ( typeof(param.acceptSuffix) == 'undefined' || param.acceptSuffix==null || param.acceptSuffix=='' ) {
        acceptSuffix = '.gif|.jpg|.jpeg|.bmp|.png';
    }
    var suffixs = acceptSuffix.split('|');
    var extensions = new Array();
    var mimeTypes = new Array();
    for (var i=0; i<suffixs.length; i++) {
        var suffix = suffixs[i]
        var extension = suffix.substr(suffix.lastIndexOf('.') + 1);
        extensions.push(extension);
        mimeTypes.push('image/' + extension);
    }
    oUpload._accept = {
        'extensions' : extensions.join(','),
        'mimeTypes' : mimeTypes.join(',')
    }
    
    oUpload._tmpUpload = param.tmpUpload;
    oUpload._uploadPath = param.uploadPath;
    oUpload._initImg = param.initImg;
    
    oUpload._uploader;
    
    oUpload._isSubmit;
    oUpload._submitSuccess;
    oUpload._submitError;
    
    
    oUpload._getInitImgCount = function() {
        if (oUpload._initImg) {
            var imgs = oUpload._initImg.imgs;
            if ($.isArray(imgs))
                return imgs.length;
        }
        return 0;
    };
    
    oUpload._oldImgCount = 0;
    
    
    // 状态：
    // pedding：等待添加
    // ready：准备上传
    // uploading：上传中
    // paused: 暂停上传
    // confirm： 上传结束
    // finish：上传成功
    // done：上传失败
    oUpload._state = 'pedding';
    
    
    /**
     * 初始化
     */
    oUpload.init = function() {
        
        var $imageUpload = $('#' + oUpload._id);
        
        $imageUpload.addClass('imageUpload');
        
        $('<input name="imageUploadId" type="hidden" value="' + oUpload._id + '" />').appendTo( $imageUpload );
        $('<input name="' + oUpload._uploadPath.param + '" type="hidden" value="' + oUpload._uploadPath.value + '" />').appendTo( $imageUpload );
        
        var $container = $('<div class="container"></div>').appendTo( $imageUpload );
        
        
        //---------------------------------------------------------------------------
        if ( !WebUploader.Uploader.support('flash') && WebUploader.browser.ie ) {
            
            // 检测是否已经安装flash，检测flash的版本
            var flashVersion = ( function() {
                var version;
                
                try {
                    version = navigator.plugins[ 'Shockwave Flash' ];
                    version = version.description;
                } catch ( ex ) {
                    try {
                        version = new ActiveXObject('ShockwaveFlash.ShockwaveFlash').GetVariable('$version');
                    } catch ( ex2 ) {
                        version = '0.0';
                    }
                }
                version = version.match( /\d+/g );
                return parseFloat( version[ 0 ] + '.' + version[ 1 ], 10 );
            } )();
            
            // flash 安装了但是版本过低。
            if ( flashVersion ) {
                (function(container) {
                    window['expressinstallcallback'] = function( state ) {
                        switch(state) {
                            case 'Download.Cancelled':
                                alert('您取消了更新！');
                                break;
                                
                            case 'Download.Failed':
                                alert('安装失败');
                                break;
                                
                            default:
                                alert('安装已成功，请刷新！');
                                break;
                                
                        }
                        delete window['expressinstallcallback'];
                    };
                    
                    var swf = './expressInstall.swf';
                    
                    // insert flash object
                    var html = '<object type="application/x-shockwave-flash" data="' +  swf + '" ';
                    
                    if (WebUploader.browser.ie) {
                        html += 'classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" ';
                    }
                    
                    html += 'width="100%" height="100%" style="outline:0">' 
                        + '<param name="movie" value="' + swf + '" />' 
                        + '<param name="wmode" value="transparent" />' 
                        + '<param name="allowscriptaccess" value="always" />' 
                        + '</object>';
                    
                    container.html(html);
                    
                })( $container );
                
            } else {	// 压根就没有安转。
                $container.html('<a href="http://www.adobe.com/go/getflashplayer" target="_blank" border="0"><img alt="get flash player" src="http://www.adobe.com/macromedia/style_guide/images/160x41_Get_Flash_Player.jpg" /></a>');
            }
            
            return;
            
        } else if (!WebUploader.Uploader.support()) {
            alert( 'Web Uploader 不支持您的浏览器！');
            return;
        }
        //---------------------------------------------------------------------------
        
        
        var $uploader = $('<div id="' + oUpload._id + '_uploader" + class="uploader"></div>').appendTo( $container );
        
        var $queueList = $(
              '    <div class="queueList">' 
            + '        <div id="' + oUpload._id + '_dndArea" class="placeholder">' 
            + '            <div id="' + oUpload._id + '_filePicker"></div>' 
            + '            <p>或将照片拖到这里，单次最多可选300张</p>' 
            + '        </div>' 
            + '    </div>' 
            ).appendTo( $uploader );
        
        // 图片容器
        var $fileList = $( '<ul class="filelist"></ul>' ).appendTo( $queueList );
        
        // 状态栏，包括进度和控制按钮
        var $statusBar = $(
              '    <div class="statusBar" style="display:none;">' 
            + '        <div class="progress">' 
            + '            <span class="text">0%</span>' 
            + '            <span class="percentage"></span>' 
            + '        </div>' 
            + '        <div class="info"></div>' 
            + '        <div class="btns">' 
            + '            <div id="' + oUpload._id + '_filePicker2" class="filePicker2"></div>' 
            + '        </div>' 
            + '    </div>' 
            ).appendTo( $uploader );
        
        if ( oUpload._showUpload ) {
            //（注：按钮的不能用<button></button>，不然会上传失败，应该是webupload控件的bug）
            $('<div class="uploadBtn">开始上传</div>').appendTo( $statusBar.find('.btns') );
        }
        
        // 进度条
        var $progress = $statusBar.find( '.progress' ).hide();
        
        // 文件总体选择信息。
        var $info = $statusBar.find( '.info' );
        
        // 上传按钮
        var $uploadBtn = $uploader.find( '.uploadBtn' );
        
        // 没选择文件之前的内容。
        var $placeHolder = $uploader.find( '.placeholder' );
        
        
        // 添加的文件数量
        var fileCount = 0;
        
        // 添加的文件总大小
        var fileSize = 0;
        
        // 优化retina, 在retina下这个值是2
        var ratio = window.devicePixelRatio || 1;
        
        // 缩略图大小
        var thumbnailWidth = 110 * ratio;
        var thumbnailHeight = 110 * ratio;
        
        // 所有文件的进度信息，key为file id
        var percentages = {};
        
        
        // 实例化
        var uploader = WebUploader.create( {
            
            // swf文件 路径
            swf: 'plugins/upload/webuploader/Uploader.swf', 
            
            // 指定Drag And Drop拖拽的容器，如果不指定，则不启动。 
            dnd: '#' + oUpload._id + '_dndArea', 
            
            // 禁掉全局的拖拽功能。这样不会出现图片拖进页面的时候，把图片打开。
            disableGlobalDnd: true,
            
            // 指定监听paste事件的容器，如果不指定，不启用此功能。此功能为通过粘贴来添加截屏的图片。
            paste: '#' +  oUpload._id + '_uploader', 
            
            // 定选择文件的按钮容器，不指定则不创建按钮。
            pick: {
                
                // 指定选择文件的按钮容器，不指定则不创建按钮。注意 这里虽然写的是 id, 但是不是只支持 id, 还支持 class, 或者 dom 节点。
                id: '#' + oUpload._id + '_filePicker', 
                
                // 请采用 innerHTML 代替
                label: '点击选择图片'
                
            }, 
            
            // 指定接受哪些类型的文件。 由于目前还有ext转mimeType表，所以这里需要分开指定。
            accept: {
                
                // 文字描述
                title: 'Images', 
                
                // 允许的文件后缀，不带点，多个用逗号分割。
                extensions: oUpload._accept.extensions, 
                
                // 多个用逗号分割。
                mimeTypes: oUpload._accept.mimeTypes
                
            }, 
            
            // 配置压缩的图片的选项。如果此选项为false, 则图片在上传前不进行压缩。 
            compress: false, 
            
            // 文件接收服务端
            server: 'fileUpload/webuploader.json', 
            
            // 文件上传请求的参数表，每次发送都会发送此对象中的参数。
            formData: {
                'path' : oUpload._tmpUpload.path
            }, 
            
            // 是否要分片处理大文件上传。
            chunked: false, 
            
            // 如果要分片，分多大一片？ 默认大小为5M。
            chunkSize: 512 * 1024, 
            
            // 验证文件总数量, 超出则不允许加入队列。
            fileNumLimit: oUpload._fileNumLimit, 
            
            // 200M 验证文件总大小是否超出限制, 超出则不允许加入队列。
            fileSizeLimit: 200 * 1024 * 1024, 
            
            // 50M 验证单个文件大小是否超出限制, 超出则不允许加入队列。
            fileSingleSizeLimit: 50 * 1024 * 1024, 
            
        } );
        
        
        // 拖拽时不接受 txt、js 文件。
        uploader.on( 'dndAccept', function( items ) {
            var denied = false;
            
            var unAllowed = 'text/plain;application/javascript';   // 修改js类型
			
            for (var i=0; i < items.length; i++ ) {
                // 如果在列表里面
                if ( ~unAllowed.indexOf( items[ i ].type ) ) {
                    denied = true;
                    break;
                }
            }
            
            return !denied;
        } );
        
        
        // 添加“添加文件”的按钮
        uploader.addButton({
            id: '#' + oUpload._id + '_filePicker2', 
            label: ( oUpload._fileNumLimit==1 ? '选择图片' : '继续添加' ) 
        });
        
        
        // 查看大图
        function viewBigImg( imgSrc ) {
            var $bigImgView = $uploader.find('.bigImgView');
            if ($bigImgView.length == 0) {
                $bigImgView = $('<div class="bigImgView"><div class="bg"><img src="' + imgSrc + '"/></div></div>').appendTo( $uploader );
                $bigImgView.on('click', function(){
                    $(this).hide();
                });
            } else {
                $bigImgView.find('img')[0].src = imgSrc;
            }
            $bigImgView.show();
        }
        
        
        // 添加 初始化图片
        function addInitImg( param ) {
            
            var imgThumb;
            if ( param.imgThumb ) {
                imgThumb = param.imgThumb;
            } else {
                imgThumb = param.imgSrc ? param.imgSrc : param.imgPath ;
            }
            
            var $item = $( '<li></li>' );
            
            $( '<p class="title">' + param.title + '</p>' ).appendTo( $item );
            
            var $imgWrap = $( '<p class="imgWrap" title="双击查看大图"><img src="' + imgThumb + '" /></p>' ).appendTo( $item );
            
            $imgWrap.on( 'dblclick', function() {
                var imgSrc = param.imgSrc ? param.imgSrc : param.imgPath ;
                viewBigImg(imgSrc);    // 查看大图
            } );
            
            var $hidden = $('<input name="' + oUpload._initImg.param + '" type="hidden" value="' + param.imgPath + '"/>').appendTo( $item );
            
            if ( oUpload._edit ) {
                
                var $btns = $('<div class="file-panel"></div>').appendTo( $item );
                var $btnCancel = $('<span class="cancel">删除</span>').appendTo( $btns );
                
                $item.on( 'mouseenter', function() {
                    $btns.stop().animate({height: 30});
                } );
                
                
                $item.on( 'mouseleave', function() {
                    $btns.stop().animate({height: 0});
                } );
                
                
                $btns.on( 'click', 'span', function() {
                    var index = $(this).index();
                    switch ( index ) {
                        case 0:         // “删除”按钮 点击事件
                            $item.off().find('.file-panel').off().end().remove();
                            oUpload._oldImgCount --;
                            if ( oUpload._oldImgCount==0 && oUpload._state==='pedding' ) {
                                $placeHolder.removeClass( 'element-invisible' );
                                $fileList.hide();
                                $statusBar.addClass( 'element-invisible' );
                                uploader.refresh();
                            }
                            return;
                    }
                } );
                
            }
            
            $item.appendTo( $fileList );
            oUpload._oldImgCount ++;
        }
        
        
        // 初始化图片数量大于0时，初始化图片列表
        if ( oUpload._getInitImgCount() > 0 ) {
            $placeHolder.addClass( 'element-invisible' );
            $( '#' + oUpload._id + '_filePicker2' ).removeClass( 'element-invisible');
            $fileList.show();
            
            var initImgs = oUpload._initImg.imgs;
            for ( var i=0; i<initImgs.length; i++ ) {
                addInitImg( initImgs[i] );
            }
            
            if ( oUpload._edit ) {
                $statusBar.show();
                $uploadBtn.addClass( 'hidden' );
            }
        } else {
            if ( !oUpload._edit )
                $imageUpload.hide();
        }
        
        
        // 当有文件添加进来时执行，负责view的创建
        function addFile( file ) {
            var $item = $( '<li id="' + file.id + '">' 
                    + '<p class="title">' + file.name + '</p>' 
                    + '<p class="imgWrap"></p>' 
                    + '<p class="progress"><span></span></p>' 
                    + '</li>' );
            
            var $hidden = $('<input id="' + file.id + '_hidden" name="' + oUpload._tmpUpload.param + '" type="hidden" />').appendTo( $item );
            
            var $btns = $('<div class="file-panel"></div>').appendTo( $item );
            var $btnCancel = $('<span class="cancel">删除</span>').appendTo( $btns );
            var $btnRotateRight = $('<span class="rotateRight">向右旋转</span>').appendTo( $btns );
            var $btnRotateLeft = $('<span class="rotateLeft">向左旋转</span></div>').appendTo( $btns );
            
            var $percent = $item.find('p.progress span');
            var $imgWrap = $item.find( 'p.imgWrap' );
            
            
            $item.on( 'mouseenter', function() {
                $btns.stop().animate({height: 30});
            } );
            
            
            $item.on( 'mouseleave', function() {
                $btns.stop().animate({height: 0});
            } );
            
            
            $btns.on( 'click', 'span', function() {
                var index = $(this).index();
                switch ( index ) {
                    case 0:
                        uploader.removeFile( file );
                        return;
                        
                    case 1:
                        file.rotation += 90;
                        break;
                        
                    case 2:
                        file.rotation -= 90;
                        break;
                }
                
                var supportTransition = (function(){
                    var s = document.createElement('p').style;
                    var r = 'transition' in s 
							|| 'WebkitTransition' in s 
							|| 'MozTransition' in s 
							|| 'msTransition' in s 
							|| 'OTransition' in s;
                    s = null;
                    return r;
                })();
                
                if ( supportTransition ) {
                    var deg = 'rotate(' + file.rotation + 'deg)';
                    $imgWrap.css({
                        '-webkit-transform': deg, 
                        '-mos-transform': deg, 
                        '-o-transform': deg, 
                        'transform': deg
                    });
                } else {
                    $imgWrap.css( 
                        'filter', 
                        'progid:DXImageTransform.Microsoft.BasicImage(rotation='+ (~~((file.rotation/90)%4 + 4)%4) +')' );
                }
            });
            
            $item.appendTo( $fileList );
            
            var $error = $('<p class="error"></p>');
            
            var showError = function( code ) {
                switch( code ) {
                    case 'exceed_size':
                        text = '文件大小超出';
                        break;
                        
                    case 'interrupt':
                        text = '上传暂停';
                        break;
                        
                    default:
                        text = '上传失败，请重试';
                        break;
                }
                
                $error.text( text ).appendTo( $item );
            };
            
            if ( file.getStatus() === 'invalid' ) {
                showError( file.statusText );
            } else {
                $imgWrap.text( '预览中' );
                
                uploader.makeThumb( file, function( error, src ) {
                    var img;
                    
                    if ( error ) {
                        $imgWrap.text( '不能预览' );
                        return;
                    }
                    
                    // 判断浏览器是否支持图片的base64
                    var isSupportBase64 = ( function() {
                        var data = new Image();
                        var support = true;
                        data.onload = data.onerror = function() {
                            if( this.width != 1 || this.height != 1 ) {
                                support = false;
                            }
                        }
                        data.src = "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///ywAAAAAAQABAAACAUwAOw==";
                        return support;
                    } )();
                    
                    if( isSupportBase64 ) {
                        img = $('<img src="'+src+'">');
                        $imgWrap.empty().append( img );
                        $imgWrap.on( 'dblclick', function() {
                            viewBigImg(src);  // 查看图片
                        });
                    } else {
                        $imgWrap.text("暂不支持预览");
                    }
                }, thumbnailWidth, thumbnailHeight );
                
                percentages[ file.id ] = [ file.size, 0 ];
                file.rotation = 0;
            }
            
            
            // 文件状态（File.Status）
            // inited 初始状态
            // queued 已经进入队列, 等待上传
            // progress 上传中
            // complete 上传完成
            // error 上传出错，可重试
            // interrupt 上传中断，可续传
            // invalid 文件不合格，不能重试上传。会自动从队列中移除。
            // cancelled 文件被移除
            file.on( 'statuschange', function( cur, prev ) {
                if ( prev === 'progress' ) {
                    $percent.closest('.progress').hide();
                    $percent.hide().width(0);
                } else if ( prev === 'queued' ) {
                    $btnRotateRight.remove();
                    $btnRotateLeft.remove();
                }
                
                // 成功
                if ( cur === 'error' || cur === 'invalid' ) {
                    console.log( file.statusText );
                    showError( file.statusText );
                    percentages[ file.id ][ 1 ] = 1;
                } else if ( cur === 'interrupt' ) {     // 上传中断，可续传
                    showError( 'interrupt' );
                } else if ( cur === 'queued' ) {        // 已经进入队列, 等待上传
                    percentages[ file.id ][ 1 ] = 0;
                } else if ( cur === 'progress' ) {      // 上传中
                    $error.remove();
                    $percent.closest('.progress').css('display', 'block');
                    $percent.css('display', 'block');
                } else if ( cur === 'complete' ) {      // 上传完成
                    $item.append( '<span class="success"></span>' );
                }
                
                $item.removeClass( 'state-' + prev ).addClass( 'state-' + cur );
            } );
            
        }
        
        
        // 当文件删除时执行，负责view的销毁
        function removeFile( file ) {
            var $item = $('#'+file.id);
            delete percentages[ file.id ];
            updateTotalProgress();
            $item.off().find('.file-panel').off().end().remove();
        }
        
        
        
        function updateTotalProgress() {
            var loaded = 0, total = 0;
            $.each( percentages, function( k, v ) {
                total += v[ 0 ];
                loaded += v[ 0 ] * v[ 1 ];
            } );
            
            var percent = total ? loaded / total : 0;
            
            var spans = $progress.children();
            spans.eq( 0 ).text( Math.round( percent * 100 ) + '%' );
            spans.eq( 1 ).css( 'width', Math.round( percent * 100 ) + '%' );
            updateStatus();
        }
        
        
        
        function updateStatus() {
            var text = '';
            if ( oUpload._state === 'ready' ) {
                text = '选中' + fileCount + '张图片，共' + WebUploader.formatSize( fileSize ) + '。';
            } else if ( oUpload._state === 'confirm' ) {
                var stats = uploader.getStats();
                if ( stats.uploadFailNum ) {
                    text = '已成功上传' + stats.successNum+ '张，' 
                        + stats.uploadFailNum + '张上传失败，<a class="retry" href="#">重新上传</a>失败图片或<a class="ignore" href="#">忽略</a>';
                }
            } else {
                var stats = uploader.getStats();
                text = '选中' + fileCount + '张（' + WebUploader.formatSize( fileSize ) + '），已上传' + stats.successNum + '张';
                if ( stats.uploadFailNum ) {
                    text += '，失败' + stats.uploadFailNum + '张';
                }
            }
            $info.html( text );
        }
        
        
        
        function setState( val ) {
            
            if ( val === oUpload._state )
                return;
            
            $uploadBtn.removeClass( 'state-' + oUpload._state );
            $uploadBtn.addClass( 'state-' + val );
            
            oUpload._state = val;
            
            switch ( oUpload._state ) {
            
                case 'pedding':
                    if (oUpload._oldImgCount == 0) {
                        $placeHolder.removeClass( 'element-invisible' );
                        $fileList.hide();
                        $statusBar.addClass( 'element-invisible' );
                        uploader.refresh();
                    }
                    break;
                    
                case 'ready':
                    $placeHolder.addClass( 'element-invisible' );
                    $( '#' + oUpload._id + '_filePicker2' ).removeClass( 'element-invisible');
                    $fileList.show();
                    $statusBar.removeClass('element-invisible');
                    uploader.refresh();
                    break;
                    
                case 'uploading':
                    $( '#' + oUpload._id + '_filePicker2' ).addClass( 'element-invisible' );
                    $progress.show();
                    $uploadBtn.text( '暂停上传' );
                    break;
                    
                case 'paused':
                    $progress.show();
                    $uploadBtn.text( '继续上传' );
                    break;
                    
                case 'confirm':
                    $progress.hide();
                    $( '#' + oUpload._id + '_filePicker2' ).removeClass( 'element-invisible' );
                    $uploadBtn.text( '开始上传' );
                    
                    var stats = uploader.getStats();
                    if ( stats.successNum && !stats.uploadFailNum ) {
                        setState( 'finish' );
                        return;
                    }
                    break;
                    
                case 'finish':
                    var stats = uploader.getStats();
                    if ( stats.successNum ) {
                        if ( oUpload._showUpload ) {
                            Alert( '上传成功' );
                        }
                        if (oUpload._isSubmit && $.isFunction(oUpload._submitSuccess)) {
                            oUpload._submitSuccess.call();
                        }
                    } else {
                        // 没有成功的图片，重设
                        oUpload._state = 'done';
                        if (oUpload._isSubmit && $.isFunction(oUpload._submitError)) {
                            oUpload._submitError.call();
                        }
                    }
                    break;
            }
            
            updateStatus();
        }
        
        
        // 当文件被加入队列之前触发，此事件的handler返回值为false，则此文件不会被添加进入队列。
        uploader.on( 'beforeFileQueued', function( file ) {
            if ( typeof(oUpload._fileNumLimit)!='undefined' && oUpload._fileNumLimit != null ) {
                if ( oUpload._fileNumLimit == 1 ) {
                    
                    // 删除现有文件
                    // TODO （遗留问题：在IE下只执行一次trigger('click')不能生效，未查不出原因。）
                    $fileList.find('.file-panel .cancel').trigger('click').trigger('click');
                    
                    return true;
                } else {
                    if ( oUpload._oldImgCount + fileCount >= oUpload._fileNumLimit ) {
                        Alert('文件数量超出！');
                        return false;
                    }
                }
            }
            return true;
        } );
        
        
        // 当文件被加入队列以后触发。
        uploader.on( 'fileQueued', function( file ) {
            fileCount++;
            fileSize += file.size;
            
            if ( fileCount === 1 ) {
                $placeHolder.addClass( 'element-invisible' );
                $statusBar.show();
                $uploadBtn.removeClass( 'hidden' );
            }
            
            addFile( file );
            setState( 'ready' );
            updateTotalProgress();
        } );
        
        
        // 当文件被移除队列后触发。
        uploader.on( 'fileDequeued', function( file ) {
            fileCount--;
            fileSize -= file.size;
            
            if ( !fileCount ) {
                setState( 'pedding' );
                $uploadBtn.addClass( 'hidden' );
            }
            removeFile( file );
            
            updateTotalProgress();
        } );
        
        
        // 上传过程中触发，携带上传进度。
        uploader.on( 'uploadProgress', function( file, percentage ) {
            var $item = $('#' + file.id);
            var $percent = $item.find('.progress span');
            $percent.css( 'width', percentage * 100 + '%' );
            
            percentages[ file.id ][ 1 ] = percentage;
            updateTotalProgress();
        } );
        
        
        // 当文件上传成功时触发。
        uploader.on( 'uploadSuccess', function( file, response ) {
            $('#' + file.id + '_hidden').val( file.name + "|" + response.filePath );
        } );
        
        
        // 特殊事件all，所有的事件触发都会响应到。
        uploader.on( 'all', function( type ) {
            
            switch( type ) {
                
                // 当开始上传流程时触发。
                case 'startUpload':
                    setState( 'uploading' );
                    break;
                    
                // 当开始上传流程暂停时触发。
                case 'stopUpload':
                    setState( 'paused' );
                    break;
                    
                // 当所有文件上传结束时触发。
                case 'uploadFinished':
                    setState( 'confirm' );
                    break;
                    
            }
            
        } );
        
        
        
        // 当validate不通过时，会以派送错误事件的形式通知调用者。通过upload.on('error', handler)可以捕获到此类错误。
        uploader.on( 'error', function( type ) {
            if ( type === 'F_DUPLICATE' ) {
                Alert('重复选择文件！');
            } else if ( type === 'Q_EXCEED_NUM_LIMIT' ) {
                Alert('上传文件数量超出！');
            } else if ( type === 'Q_EXCEED_SIZE_LIMIT' ) {
                Alert('上传文件总大小超出！');
            } else if ( type === 'Q_TYPE_DENIED' ) {
                Alert('上传文件类型错误！');
            } else {
                alert( 'Error: ' + type );
            }
        } );
        
        
        
        // “上传”按钮 点击事件
        $uploadBtn.on('click', function() {
            
            if ( $(this).hasClass( 'disabled' ) ) {
                return false;
            }
            
            if ( oUpload._state === 'ready' ) {
                uploader.upload();
            } else if ( oUpload._state === 'paused' ) {
                uploader.upload();
            } else if ( oUpload._state === 'uploading' ) {
                uploader.stop();
            }
            
        } );
		
		
        
        $info.on( 'click', '.retry', function() {
            uploader.retry();
        } );
        
        
        $info.on( 'click', '.ignore', function() {
            alert( 'todo' );
        } );
        
        
        $uploadBtn.addClass( 'state-' + oUpload._state );
        updateTotalProgress();
        
        
        oUpload._uploader = uploader;
    };
    
    
    
    /**
     * 图片控件提交
     *  @param param {object} JSON数据
     *                  {
     *                      'success' : {function},
     *                      'error' : {function}
     *                  }
     */
    oUpload.submit = function(param) {
        oUpload._isSubmit = true;
        if ( param ) {
            if ( param.success ) {
                oUpload._submitSuccess = param.success;
            }
            if ( param.error ) {
                oUpload._submitError = param.error;
            }
        }
        
        // 判断是否有需要上传的文件
        if ( oUpload._state === 'pedding' ) {
            if ( $.isFunction(oUpload._submitSuccess) )
                oUpload._submitSuccess.call();
        } else if ( oUpload._state === 'ready' || oUpload._state === 'paused' ) {
            oUpload._uploader.upload();
        } else if ( oUpload._state === 'finish' ) {
            if ( $.isFunction(oUpload._submitSuccess) )
                oUpload._submitSuccess.call();
        }
    };
    
    
    oUpload.init();
    
    if (typeof(window.Sys_ImageUploads) == 'undefined') {
        window.Sys_ImageUploads = new Array();
    }
    window.Sys_ImageUploads.push(oUpload);
    
    return oUpload;
    
};