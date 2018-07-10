/*
 * 2017.07.21 puzd 
 *      1、左导航新增data-tab-show-refresh="true"表示切换页签时是否刷新内容。
 *      2、调大页签标题的关闭按钮及按钮跟左边文字的距离
 * */


// 计算元素集合的总宽度
function calSumWidth(elements) {
    var width = 0;
    $(elements).each(function () {
        width += $(this).outerWidth(true);
    });
    return width;
}

// 滚动到指定选项卡
function scrollToTab(element) {
    var marginLeftVal = calSumWidth($(element).prevAll()), marginRightVal = calSumWidth($(element).nextAll());
    // 可视区域非tab宽度
    var tabOuterWidth = calSumWidth($(".content-tabs").children().not(".J_menuTabs"));
    //可视区域tab宽度
    var visibleWidth = $(".content-tabs").outerWidth(true) - tabOuterWidth;
    //实际滚动宽度
    var scrollVal = 0;
    if ($(".page-tabs-content").outerWidth() < visibleWidth) {
        scrollVal = 0;
    } else if (marginRightVal <= (visibleWidth - $(element).outerWidth(true) - $(element).next().outerWidth(true))) {
        if ((visibleWidth - $(element).next().outerWidth(true)) > marginRightVal) {
            scrollVal = marginLeftVal;
            var tabElement = element;
            while ((scrollVal - $(tabElement).outerWidth()) > ($(".page-tabs-content").outerWidth() - visibleWidth)) {
                scrollVal -= $(tabElement).prev().outerWidth();
                tabElement = $(tabElement).prev();
            }
        }
    } else if (marginLeftVal > (visibleWidth - $(element).outerWidth(true) - $(element).prev().outerWidth(true))) {
        scrollVal = marginLeftVal - $(element).prev().outerWidth(true);
    }
    $('.page-tabs-content').animate({
        marginLeft: 0 - scrollVal + 'px'
    }, "fast");
}


/**
 * 打开菜单页签
 *  @param param {object} JSON数据
 *                  {
 *                      menuName : {string},            // 页签标题
 *                      dataIndex : {int},              // 标识符
 *                      dataUrl : {string},             // 页签内容的iframe对应url
 *                      dataShowRefresh : {boolean},    // 切换显示时是否刷新页签内容
 *                      dataOpener : {string}           // 打开该页签所在窗口的名称
 *                  }
 */
function openMenuTab(param) {
    
    var menuName = param.menuName;
    var dataIndex = param.dataIndex;
    var dataUrl = param.dataUrl;
    var dataShowRefresh = param.dataShowRefresh;
    var dataOpener = typeof(param.dataOpener)=='undefined' ? '' : param.dataOpener;
    
    var flag = true;
    // 选项卡菜单已存在
    $('.J_menuTab').each(function () {
        if ( $(this).data('url') == dataUrl ) {
            if ( !$(this).hasClass('active') ) {
                var index = $(this).data('index');
                $(this).addClass('active').siblings('.J_menuTab').removeClass('active');
                scrollToTab(this);
                // 显示tab对应的内容区
                $('.J_mainContent .J_iframe').each(function () {
                    if ($(this).data('index') == index) {
                        $(this).show().siblings('.J_iframe').hide();
                        
                        // 判断是否需要刷新内容
                        var showRefresh = $(this).data('show-refresh');
                        if ( showRefresh ) {
                            refreshMenuTab({
                                dataIndex : $(this).data('index')
                            });
                        }
                        
                        return false;
                    }
                });
            }
            flag = false;
            return false;
        }
    });
    
    // 选项卡菜单不存在
    if ( flag ) {
        
        $('.J_menuTab').removeClass('active');
        
        if (typeof(dataIndex) == 'undefined')
            dataIndex = Math.random().toString().substr(2);
        
        // 添加选项卡对应的iframe
        var frameStr = '<iframe class="J_iframe"' 
            + ' name="J_iframe_' + dataIndex + '"' 
            + ' src="' + dataUrl + '"' 
            + ' data-index="' + dataIndex + '"' 
            + ' data-url="' + dataUrl + '"' ;
        
        if ( dataShowRefresh )
            frameStr += ' data-show-refresh="' + dataShowRefresh + '"' ;
        
        frameStr += ' data-opener="' + dataOpener + '"' 
            + ' width="100%" height="100%" frameborder="0" seamless></iframe>';
        $('.J_mainContent').find('iframe.J_iframe').hide().parents('.J_mainContent').append(frameStr);
        
        // 显示loading提示
        var loading = layer.load(2);
        $('.J_mainContent iframe:visible').load(function () {
            // iframe加载完成后隐藏loading提示
            layer.close(loading);
        });
        
        // 添加选项卡
        var tabStr = '<a ' 
            + ' data-index="' + dataIndex + '"' 
            + ' data-url="' + dataUrl + '"' ;
        
        if (dataShowRefresh)
            tabStr += ' data-show-refresh="' + dataShowRefresh + '"' ;
        
        tabStr += ' data-opener="' + dataOpener + '"' 
            + ' href="javascript:;" class="active J_menuTab">' 
            + menuName + '&nbsp;&nbsp;<i class="fa fa-times-circle fa-lg"></i>' 
            + '</a>';
        $('.J_menuTabs .page-tabs-content').append(tabStr);
        
        scrollToTab( $('.J_menuTab.active') );
    }
    
}


/**
 * 关闭菜单页签
 *  @param param {object} JSON数据
 *                  {
 *                      dataIndex : {int},              // 标识符
 *                      refreshOpener : {boolean},      // true — 刷新父窗口
 *                      reloadOpener : {boolean}        // true — 重新加载父窗口
 *                  }
 */
function closeMenuTab(param) {
    
    var activeIndex;
    
    // 刷新 或 重新加载 父窗口
    if ( param.refreshOpener || param.reloadOpener ) {
        var dataOpener = $('.J_menuTab[data-index="' + param.dataIndex + '"]').attr('data-opener');
        var opener = window[dataOpener];
        if ( typeof(opener) != 'undefined' ) {
            if ( param.refreshOpener ) {
                if ( $.isFunction(opener.refreshWin) ) {
                    opener.refreshWin();
                } else {
                    opener.location.reload();
                }
            } else if ( param.reloadOpener ) {
                opener.location.reload();
            }
            
            if ( dataOpener.startWith('J_iframe_') ) {
                activeIndex = dataOpener.substr(9);
            }
        }
    }
    
    var closeIndex = param.dataIndex;
    
    var $menuTab = $('.J_menuTab[data-index="' + closeIndex + '"]');
    
    if ( $menuTab.hasClass('active') ) {   // 当前元素处于活动状态
        
        if ( $menuTab.next('.J_menuTab').size() ) {     // 当前元素后面有同辈元素，使后面的一个元素处于活动状态
            if ( typeof(activeIndex) == 'undefined' )
                activeIndex = $menuTab.next('.J_menuTab:eq(0)').data('index');
        } else if ( $menuTab.prev('.J_menuTab').size() ) {      // 当前元素后面没有同辈元素，使当前元素的上一个元素处于活动状态
            if ( typeof(activeIndex) == 'undefined' ) 
                activeIndex = $menuTab.prev('.J_menuTab:last').data('index'); 
        }
        
        if ( typeof(activeIndex) != 'undefined' ) {
            $('.J_menuTab[data-index="' + activeIndex + '"]').addClass('active');
            $('.J_mainContent .J_iframe').each(function () {
                if ($(this).data('index') == activeIndex) {
                    $(this).show().siblings('.J_iframe').hide();
                    
                    // 判断是否需要刷新内容
                    var showRefresh = $(this).data('show-refresh');
                    if ( showRefresh ) {
                        refreshMenuTab({
                            dataIndex : $(this).data('index')
                        });
                    }
                    
                    return false;
                }
            });

            //  移除当前选项卡
            $menuTab.remove();

            // 移除tab对应的内容区
            $('.J_mainContent .J_iframe').each(function () {
                if ($(this).data('index') == closeIndex) {
                    $(this).remove();
                    return false;
                }
            });
            
            scrollToTab($('.J_menuTab.active'));
        }
        
    } else {    // 当前元素不处于活动状态
        
        // 移除当前选项卡
        $menuTab.remove();
        
        // 移除相应tab对应的内容区
        $('.J_mainContent .J_iframe').each(function () {
            if ($(this).data('index') == closeIndex) {
                $(this).remove();
                return false;
            }
        });
        scrollToTab( $('.J_menuTab.active') );
    }
}



/**
 * 刷新菜单页签
 *  @param param {object} JSON数据
 *                  {
 *                      dataIndex : {int},              // 标识符
 *                      refreshOpener : {boolean},      // true — 刷新父窗口
 *                      reloadOpener : {boolean}        // true — 重新加载父窗口
 *                  }
 */
function refreshMenuTab(param) {
    
    // 刷新 或 重新加载 父窗口
    if ( param.refreshOpener || param.reloadOpener ) {
        var dataOpener = $('.J_menuTab[data-index="' + param.dataIndex + '"]').attr('data-opener');
        var opener = window[dataOpener];
        if ( typeof(opener) != 'undefined' ) {
            if ( param.refreshOpener ) {
                if ( $.isFunction(opener.refreshWin) ) {
                    opener.refreshWin();
                } else {
                    opener.location.reload();
                }
            } else if ( param.reloadOpener ) {
                opener.location.reload();
            }
        }
    }
    
    var win = window['J_iframe_' + param.dataIndex];
    if ( typeof(win) != 'undefined' ) {
        if ( $.isFunction(win.refreshWin) ) {
            win.refreshWin();
        } else {
            win.location.reload();
        }
    }
}


$(function () {
    
    // 通过遍历给菜单项加上data-index属性
    $(".J_menuItem").each(function (index) {
        if (!$(this).attr('data-index')) {
            $(this).attr('data-index', index);
        }
    });
    
    
    function menuItem() {
        // 获取标识数据
        var dataUrl = $(this).attr('href');
        if (dataUrl == undefined || $.trim(dataUrl).length == 0)
            return false;
        
        var menuName = $(this).data('tab-title');
        if ( typeof(menuName)=='undefined' || menuName==null || menuName=='' ) 
            menuName = $.trim( $(this).text() );
        
        var dataIndex = $(this).data('index');
        
        var dataShowRefresh = $(this).data('tab-show-refresh');
        
        openMenuTab({
            menuName : menuName,
            dataIndex : dataIndex,
            dataUrl : dataUrl,
            dataShowRefresh : dataShowRefresh
        });
        
        return false;
    }

    $('.J_menuItem').on('click', menuItem);
    
    
    
    // 关闭选项卡菜单
    function closeTab() {
        closeMenuTab({
            'dataIndex' : $(this).parents('.J_menuTab').data('index')
        });
    }
    $('.J_menuTabs').on('click', '.J_menuTab i', closeTab);
    
    
    // 关闭其他选项卡
    function closeOtherTabs(){
        $('.page-tabs-content').children("[data-index]").not(":first").not(".active").each(function () {
            $('.J_iframe[data-index="' + $(this).data('index') + '"]').remove();
            $(this).remove();
        });
        $('.page-tabs-content').css("margin-left", "0");
    }
    
    $('.J_tabCloseOther').on('click', closeOtherTabs);
    
    
    
    //滚动到已激活的选项卡
    function showActiveTab(){
        scrollToTab($('.J_menuTab.active'));
    }
    $('.J_tabShowActive').on('click', showActiveTab);
    
    

    // 点击选项卡菜单
    function activeTab() {
        if (!$(this).hasClass('active')) {
            var currentIndex = $(this).data('index');
            // 显示tab对应的内容区
            $('.J_mainContent .J_iframe').each(function () {
                if ($(this).data('index') == currentIndex) {
                    $(this).show().siblings('.J_iframe').hide();
                    
                    // 判断是否需要刷新内容
                    var showRefresh = $(this).data('show-refresh');
                    if ( showRefresh ) {
                        refreshMenuTab({
                            dataIndex : $(this).data('index')
                        });
                    }
                    
                    return false;
                }
            });
            $(this).addClass('active').siblings('.J_menuTab').removeClass('active');
            scrollToTab(this);
        }
    }

    $('.J_menuTabs').on('click', '.J_menuTab', activeTab);
    
    
    
    // 刷新iframe
    function refreshTab() {
        var target = $('.J_iframe[data-index="' + $(this).data('index') + '"]');
        var url = target.attr('src');
        // 显示loading提示
        var loading = layer.load(2);
        target.attr('src', url).load(function () {
            // 关闭loading提示
            layer.close(loading);
        });
    }
    
    $('.J_menuTabs').on('dblclick', '.J_menuTab', refreshTab);

    
    
    // 查看左侧隐藏的选项卡
    function scrollTabLeft() {
        var marginLeftVal = Math.abs(parseInt($('.page-tabs-content').css('margin-left')));
        // 可视区域非tab宽度
        var tabOuterWidth = calSumWidth($(".content-tabs").children().not(".J_menuTabs"));
        //可视区域tab宽度
        var visibleWidth = $(".content-tabs").outerWidth(true) - tabOuterWidth;
        //实际滚动宽度
        var scrollVal = 0;
        if ($(".page-tabs-content").width() < visibleWidth) {
            return false;
        } else {
            var tabElement = $(".J_menuTab:first");
            var offsetVal = 0;
            while ((offsetVal + $(tabElement).outerWidth(true)) <= marginLeftVal) {//找到离当前tab最近的元素
                offsetVal += $(tabElement).outerWidth(true);
                tabElement = $(tabElement).next();
            }
            offsetVal = 0;
            if (calSumWidth($(tabElement).prevAll()) > visibleWidth) {
                while ((offsetVal + $(tabElement).outerWidth(true)) < (visibleWidth) && tabElement.length > 0) {
                    offsetVal += $(tabElement).outerWidth(true);
                    tabElement = $(tabElement).prev();
                }
                scrollVal = calSumWidth($(tabElement).prevAll());
            }
        }
        $('.page-tabs-content').animate({
            marginLeft: 0 - scrollVal + 'px'
        }, "fast");
    }
    
    // 左移按扭
    $('.J_tabLeft').on('click', scrollTabLeft);
    
    
    
    // 查看右侧隐藏的选项卡
    function scrollTabRight() {
        var marginLeftVal = Math.abs(parseInt($('.page-tabs-content').css('margin-left')));
        // 可视区域非tab宽度
        var tabOuterWidth = calSumWidth($(".content-tabs").children().not(".J_menuTabs"));
        //可视区域tab宽度
        var visibleWidth = $(".content-tabs").outerWidth(true) - tabOuterWidth;
        //实际滚动宽度
        var scrollVal = 0;
        if ($(".page-tabs-content").width() < visibleWidth) {
            return false;
        } else {
            var tabElement = $(".J_menuTab:first");
            var offsetVal = 0;
            while ((offsetVal + $(tabElement).outerWidth(true)) <= marginLeftVal) {//找到离当前tab最近的元素
                offsetVal += $(tabElement).outerWidth(true);
                tabElement = $(tabElement).next();
            }
            offsetVal = 0;
            while ((offsetVal + $(tabElement).outerWidth(true)) < (visibleWidth) && tabElement.length > 0) {
                offsetVal += $(tabElement).outerWidth(true);
                tabElement = $(tabElement).next();
            }
            scrollVal = calSumWidth($(tabElement).prevAll());
            if (scrollVal > 0) {
                $('.page-tabs-content').animate({
                    marginLeft: 0 - scrollVal + 'px'
                }, "fast");
            }
        }
    }
    
    // 右移按扭
    $('.J_tabRight').on('click', scrollTabRight);
    
    
    
    // 关闭全部
    $('.J_tabCloseAll').on('click', function () {
        $('.page-tabs-content').children("[data-index]").not(":first").each(function () {
            $('.J_iframe[data-index="' + $(this).data('index') + '"]').remove();
            $(this).remove();
        });
        $('.page-tabs-content').children("[data-index]:first").each(function () {
            
            $('.J_iframe[data-index="' + $(this).data('index') + '"]').show();
            
            // 判断是否需要刷新内容
            var showRefresh = $(this).data('show-refresh');
            if ( showRefresh ) {
                refreshMenuTab({
                    dataIndex : $(this).data('index')
                });
            }
            
            $(this).addClass("active");
        });
        $('.page-tabs-content').css("margin-left", "0");
    });
    
    
});
