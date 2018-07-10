function checkedCheSun(val) {
    if (parseInt(val) == 0) {
        $('#buJiMianCheSunItem').addClass('hidden');
        $('#cheSunAdd').addClass('hidden');
    } else if (parseInt(val) == 1) {
        $('#buJiMianCheSunItem').removeClass('hidden');
        $('#cheSunAdd').removeClass('hidden');
    }
}

function checkedZiRan(val) {
    if (parseInt(val) == 0) {
        $('#buJiMianZiRanItem').addClass('hidden');
    } else if (parseInt(val) == 1) {
        $('#buJiMianZiRanItem').removeClass('hidden');
    }
}

function checkedSheShui(val) {
    if (parseInt(val) == 0) {
        $('#buJiMianSheShuiItem').addClass('hidden');
    } else if (parseInt(val) == 1) {
        $('#buJiMianSheShuiItem').removeClass('hidden');
    }
}

function checkedHuaHen(val) {
    if (parseInt(val) == 0 ) {
        $('#buJiMianHuaHenItem').addClass('hidden');
    } else {
        $('#buJiMianHuaHenItem').removeClass('hidden');
    }
}

function checkedDaoQiang(val) {
    if (val == '1') {
        $('#buJiMianDaoQiangItem').removeClass('hidden');
    } else {
        $('#buJiMianDaoQiangItem').addClass('hidden');
    }
}

function checkedSanZhe(val) {
    if (parseInt(val) == 0 ) {
        $('#buJiMianSanZheItem').addClass('hidden');
    } else {
        $('#buJiMianSanZheItem').removeClass('hidden');
    }
}

function checkedSiJi(val) {
    if (parseInt(val) == 0 ) {
        $('#buJiMianSiJiItem').addClass('hidden');
    } else {
        $('#buJiMianSiJiItem').removeClass('hidden');
    }
}

function checkedChengKe(val) {
    if (parseInt(val) == 0 ) {
        $('#buJiMianChengKeItem').addClass('hidden');
    } else {
        $('#buJiMianChengKeItem').removeClass('hidden');
    }
}

// 设置状态，'peddingCar' — 未输入车牌，'inQueryCar' — 正在查询车辆信息，'sucQueryCar' — 查到车辆信息，'nullQueryCar' — 未查到车辆信息
function setState(val) {
    switch (val) {
        case 'peddingCar' :
            $('#licenseNo').removeAttr('readOnly');
            $('#engineNo').attr('readOnly', 'readOnly');
            $('#vinNo').attr('readOnly', 'readOnly');
            $('#regDate').attr('readOnly', 'readOnly');
            $('#model').attr('readOnly', 'readOnly');
            $('#quoteCont').addClass('hidden');
            break;
        
        case 'inQueryCar' :
            $('#licenseNo').attr('readOnly', 'readOnly');
            if ( $.getUrlParam('licenseNo') == null ) {
                $('#btn_searchLicenseNo').attr('disabled', true);
            }
            $('#engineNo').attr('readOnly', 'readOnly').val('');
            $('#vinNo').attr('readOnly', 'readOnly').val('');
            $('#regDate').attr('readOnly', 'readOnly').val('');
            $('#model').attr('readOnly', 'readOnly').val('');
            break;
            
        case 'sucQueryCar' :
            if ( $.getUrlParam('licenseNo') == null ) {
                $('#licenseNo').removeAttr('readOnly');
                $('#btn_searchLicenseNo').removeAttr('disabled');
            }
            
            $('a[id="btn_showReInfo"]').removeClass('hidden');
            
            $('#regDate').unbind();
            
            $('#quoteCont').removeClass('hidden');
            $('#btn_query').removeClass('hidden');
            break;
        case 'nullQueryCar' :
            $('#licenseNo').removeAttr('readOnly');
            if ( $.getUrlParam('licenseNo') == null ) {
                $('#btn_searchLicenseNo').removeAttr('disabled');
            }
            $('#engineNo').removeAttr('readOnly').val('');
            $('#vinNo').removeAttr('readOnly').val('');
            $('#regDate').removeAttr('readOnly').val('');
            $('#model').removeAttr('readOnly').val('');
            
            $('#regDate').focus(function(){
                $(this)[0].blur();
            });
            $('#regDate').click(function(){
                laydate({
                    elem : '#regDate',
                    format : 'YYYY-MM-DD'
                });
            });
            
            
            $('#quoteCont').removeClass('hidden');
            $('#btn_query').removeClass('hidden');
            break;
    }
}

// 查询车辆信息
function queryCar(licenseNo) {
    setState('inQueryCar');
    
    var layerIndex = layer.load(2);
    // 获取车辆信息
    $.ajax({
        type : 'POST',
        url : 'viQuote/getReInfo.json',
        data : {
            'licenseNo' : licenseNo
        },
        dataType : 'json',
        success : function(ret, textStatus) {
            layer.close( layerIndex );
            if (ret.success) {
                var d = ret.data;
                
                var car = d.viCar;
                
                if (car == null) {
                    setState('nullQueryCar');
                    
                    Alert({
                        msg : '系统查不到该车辆，需补充车辆信息！',
                        ok : function() {
                            $('#engineNo')[0].focus();
                        }
                    });
                    return;
                }
                
                // 车辆信息
                var attrs = ['licenseNo', 'engineNo', 'vinNo', 'regDate', 'model'];
                for (var i=0; i<attrs.length; i++) {
                    var attr = attrs[i];
                    $('#' + attr).val( car[attr] );
                }
                
                var attrs = ['licenseNo', 'engineNo', 'vinNo', 'regDate', 'model', 
                             'ownerName', 'ownerIdTypeName', 'ownerIdNo'];
                for (var i=0; i<attrs.length; i++) {
                    var attr = attrs[i];
                    $('#info_' + attr).text( car[attr] );
                }
                
                
                // 以往购买信息
                var purchased = d.viPurchased;
                
                if (purchased == null) {
                    setState('sucQueryCar');
                    return;
                }
                
                var attrs = ['insCorpName', 'applicantName', 'applicantPhone', 'applicantIdTypeName', 'applicantIdNo',
                             'insuredName', 'insuredPhone', 'insuredIdTypeName', 'insuredIdNo', 'forceExpireDate', 'businessExpireDate', 
                             'cheSunBE', 'ziRanBE', 'boLiTxt', 'huaHenBE', 'daoQiangBE', 'sanZheBE', 
                             'siJiBE', 'chengKeBE', 'jingShenSunShiBE', 'useCiStartDate', 'useCiEndDate', 'useBiStartDate', 'useBiEndDate'];
                for (var i=0; i<attrs.length; i++) {
                    var attr = attrs[i];
                    $('#info_' + attr).text( purchased[attr] );
                }
                
                var attrs = ['buJiMianCheSun', 'buJiMianZiRan', 'sheShui', 'buJiMianSheShui', 
                             'buJiMianHuaHen', 'cheSunSanFangTeYue', 'buJiMianDaoQiang', 'buJiMianSanZhe', 
                             'buJiMianSiJi', 'buJiMianChengKe'];
                for (var i=0; i<attrs.length; i++) {
                    var attr = attrs[i];
                    var tmp = purchased["is" + attr.substr(0, 1).toUpperCase() + attr.substr(1)];
                    $('#info_' + attr).text( tmp? '有投保' : '无' );
                }
                
                // 投保城市
                if ( car.licenseNo.startWith('京') ) {
                    $("input[name='city'][value='1']").parent().iCheck('check');
                } else if ( car.licenseNo.startWith('闽') ) {
                    $("input[name='city'][value='2']").parent().iCheck('check');
                }
                
                // 保险公司
                $("input[name='insCorp']").each(function(){
                    $(this).parent().iCheck( $(this).val() == purchased.insCorp ? 'check': 'uncheck');
                });
                
                // 车损
                $("input[name='cheSun'][value='" + ( purchased.cheSunBE > 0 ? '1': '0') + "']").parent().iCheck('check');
                $("input[name='buJiMianCheSun'][value='" + (purchased.isBuJiMianCheSun ? '1': '0') + "']").parent().iCheck('check');
                
                // 自燃
                $("input[name='ziRan'][value='" + (purchased.ziRanBE > 0 ? '1': '0') + "']").parent().iCheck('check');
                $("input[name='buJiMianZiRan'][value='" + (purchased.isBuJiMianZiRan ? '1': '0') + "']").parent().iCheck('check');
                
                // 玻璃
                $("input[name='boLi'][value='" + purchased.boLi + "']").parent().iCheck('check');
                
                // 涉水
                $("input[name='sheShui'][value='" + (purchased.isSheShui ? '1': '0') + "']").parent().iCheck('check');
                $("input[name='buJiMianSheShui'][value='" + (purchased.isBuJiMianSheShui ? '1': '0') + "']").parent().iCheck('check');
                
                // 划痕
                var opts = $('#huaHen').find('option');
                for (var i=0; i<opts.length; i++) {
                    var opt = $(opts[i]);
                    if ( opt.val() >= purchased.huaHenBE || i==(opts.length-1) ) {
                        var sel;
                        if (purchased.huaHenBE==0) {
                            sel = 0;
                        } else {
                            sel = opt.val();
                        }
                        $('#huaHen').val(sel).trigger('chosen:updated');
                        checkedHuaHen(sel);
                        break;
                    }
                }
                
                $("input[name='buJiMianHuaHen'][value='" + (purchased.isBuJiMianHuaHen ? '1': '0') + "']").parent().iCheck('check');
                
                $("input[name='cheSunSanFangTeYue'][value='" + (purchased.isCheSunSanFangTeYue ? '1': '0') + "']").parent().iCheck('check');
                
                // 盗抢
                $("input[name='daoQiang'][value='" + (purchased.daoQiangBE > 0 ? '1': '0') + "']").parent().iCheck('check');
                $("input[name='buJiMianDaoQiang'][value='" + (purchased.isBuJiMianDaoQiang ? '1': '0') + "']").parent().iCheck('check');
                
                // 三者
                var opts = $('#sanZhe').find('option');
                for (var i=0; i<opts.length; i++) {
                    var opt = $(opts[i]);
                    if ( opt.val() >= purchased['sanZheBE'] || i==(opts.length-1) ) {
                        var sel;
                        if (purchased.sanZheBE==0) {
                            sel = 0;
                        } else {
                            sel = opt.val();
                        }
                        $('#sanZhe').val(sel).trigger('chosen:updated');
                        checkedSanZhe(sel);
                        break;
                    }
                }
                
                $("input[name='buJiMianSanZhe'][value='" + (purchased.isBuJiMianSanZhe ? '1': '0') + "']").parent().iCheck('check');
                
                // 司机
                var opts = $('#siJi').find('option');
                for (var i=0; i<opts.length; i++) {
                    var opt = $(opts[i]);
                    if ( opt.val() >= purchased['siJiBE'] || i==(opts.length-1) ) {
                        var sel;
                        if (purchased.siJiBE==0) {
                            sel = 0;
                        } else {
                            sel = opt.val();
                        }
                        $('#siJi').val(sel).trigger('chosen:updated');
                        checkedSiJi(sel);
                        break;
                    }
                }
                
                $("input[name='buJiMianSiJi'][value='" + (purchased.isBuJiMianSiJi ? '1': '0') + "']").parent().iCheck('check');
                
                // 乘客
                var opts = $('#chengKe').find('option');
                for (var i=0; i<opts.length; i++) {
                    var opt = $(opts[i]);
                    if ( opt.val() >= purchased['chengKeBE'] || i==(opts.length-1) ) {
                        var sel;
                        if (purchased.chengKeBE==0) {
                            sel = 0;
                        } else {
                            sel = opt.val();
                        }
                        $('#chengKe').val(sel).trigger('chosen:updated');
                        checkedChengKe(sel);
                        break;
                    }
                }
                
                $("input[name='buJiMianChengKe'][value='" + (purchased.isBuJiMianChengKe ? '1': '0') + "']").parent().iCheck('check');
                
                setState('sucQueryCar');
                
            } else if(ret.msg != null) {
                
                setState('nullQueryCar');
                
                Alert({
                    msg : '系统查不到该车辆，需补充车辆信息！系统提示：' + msg,
                    ok : function() {
                        $('#engineNo')[0].focus();
                    }
                });
                
            } else {
                setState('nullQueryCar');
                
                Alert({
                    msg : '程序错误，请联系管理员！',
                    ok : function() {
                        $('#engineNo')[0].focus();
                    }
                });
            }
        },
        error : function(xmlHttpRequest, textStatus, errorThrown) {
            layer.close( layerIndex );
            setState('nullQueryCar');
            Alert({
                msg : '连接错误，请联系管理员！',
                ok : function() {
                    $('#licenseNo')[0].focus();
                }
            });
        }
    });
}


//输入校验
function validateInput() {
    
    var licenseNo = $.trim($('#licenseNo').val());
    if (licenseNo == "") {
        Alert({
            msg : '请输入车牌号',
            ok : function() {
                $('#licenseNo')[0].focus();
            }
        });
        return false;
    }
    
    var engineNo = $.trim($('#engineNo').val());
    if (engineNo == "") {
        
        if ( $('#engineNo').attr('readOnly') == 'readOnly' ) {
            Alert('请先点 车牌号“查询” 按钮！');
            return false;
        }
        
        Alert({
            msg : '请输入发动机号',
            ok : function() {
                $('#engineNo')[0].focus();
            }
        });
        return false;
    }
    
    var vinNo = $.trim($('#vinNo').val());
    if (vinNo == "") {
        Alert({
            msg : '请输入车架号',
            ok : function() {
                $('#vinNo')[0].focus();
            }
        });
        return false;
    }
    
    var regDate = $.trim($('#regDate').val());
    if (regDate == "") {
        Alert({
            msg : '请输入注册日期',
            ok : function() {
                $('#regDate')[0].click();
            }
        });
        return false;
    }
    
    var model = $.trim($('#model').val());
    if (model == "") {
        Alert({
            msg : '品牌型号',
            ok : function() {
                $('#model')[0].focus();
            }
        });
        return false;
    }
    
    var selCitys = $('input[name="city"]:checked');
    if ( selCitys.length == 0 ) {
        Alert('请选择投保城市！');
        return false;
    }
    
    var selInsCorps = $('input[name="insCorp"]:checked');
    if ( selInsCorps.length == 0 ) {
        Alert('请选择保险公司！');
        return false;
    }
    
    return true;
}


// 处理表单数据
function dealForm() {
    
    var cheSun = $('input:radio[name="cheSun"]:checked').val();
    if (cheSun == '0') {
        
        $("input[name='buJiMianCheSun'][value='0']").parent().iCheck('check');
        
        $("input[name='ziRan'][value='0']").parent().iCheck('check');
        $("input[name='buJiMianZiRan'][value='0']").parent().iCheck('check');
        
        $("input[name='boLi'][value='0']").parent().iCheck('check');
        
        $("input[name='sheShui'][value='0']").parent().iCheck('check');
        $("input[name='buJiMianSheShui'][value='0']").parent().iCheck('check');
        
        $('#huaHen').val(0).trigger('chosen:updated');
        checkedHuaHen(0);
        $("input[name='buJiMianHuaHen'][value='0']").parent().iCheck('check');
        
        $("input[name='cheSunSanFangTeYue'][value='0']").parent().iCheck('check');
        
    } else if (cheSun == '1') {
        
        var ziRan = $('input:radio[name="ziRan"]:checked').val();
        if (ziRan == '0')
            $("input[name='buJiMianZiRan'][value='0']").parent().iCheck('check');
        
        var sheShui = $('input:radio[name="sheShui"]:checked').val();
        if (sheShui == '0')
            $("input[name='buJiMianSheShui'][value='0']").parent().iCheck('check');
        
        var huaHen = $('#huaHen').val();
        if (huaHen == 0)
            $("input[name='buJiMianHuaHen'][value='0']").parent().iCheck('check');
        
    }
    
    var daoQiang = $('input:radio[name="daoQiang"]:checked').val();
    if (daoQiang == '0')
        $("input[name='buJiMianDaoQiang'][value='0']").parent().iCheck('check');
    
    var sanZhe = $('#sanZhe').val();
    if (sanZhe == 0)
        $("input[name='buJiMianSanZhe'][value='0']").parent().iCheck('check');
    
    var siJi = $('#siJi').val();
    if (siJi == 0)
        $("input[name='buJiMianSiJi'][value='0']").parent().iCheck('check');
    
    var chengKe = $('#chengKe').val();
    if (chengKe == 0)
        $("input[name='buJiMianChengKe'][value='0']").parent().iCheck('check');
    
}

//组织报价结果模板
function initQuotePage( corpArray, insPlan ) {
    // 车牌号
    $('#quoteResult_licenseNo').text($('#licenseNo').val());
    
    var insColW = 200;  // 险种列宽，单位px
    var beColW = 150;   // 保额列宽，单位px
    var bfColW = 150;   // 保费列宽，单位px
    var corpW = beColW + bfColW;    // 每家保险公司列宽
    var totalW = insColW + corpW * corpArray.length; // 总宽度
    
    var $sheet = $('<div class="table-responsive"></div>').appendTo( $('#quoteResultCont').empty() );
    
    // 组织 表头
    var buf = new StringBuffer();
    buf.append('<table class="table table-bordered" style="table-layout:fixed; width:').append( totalW ).append('px; margin-bottom:0px;">');
    buf.append('<thead>');
    buf.append('<tr>');
    buf.append('<th rowspan="2" class="text-center" style="width:').append( insColW ).append('px;border-bottom:0px;">险　种</th>');
    for (var i=0; i<corpArray.length; i++) {
        var corpObj = corpArray[i];
        buf.append('<th colspan="2" class="text-center" style="width:').append( corpW ).append('px;">');
        buf.append( corpObj.insCorpName ).append('（').append( corpObj.cityName ).append('）');
        buf.append('</th>');
    }
    buf.append('</tr>');
    buf.append('<tr>');
    for (var i=0; i<corpArray.length; i++) {
        buf.append('<th class="text-center" style="width:').append( beColW ).append('px;border-bottom:0px;">保　额</th>');
        buf.append('<th class="text-center" style="width:').append( bfColW ).append('px;border-bottom:0px;">保　费</th>');
    }
    buf.append('</tr>');
    buf.append('</thead>');
    buf.append('</table>');
    
    var $head = $( buf.toString() ).appendTo( $sheet );
    
    // 组织 表身
    var buf = new StringBuffer();
    buf.append('<div style="line-height:0px;">');
    buf.append('<div style="height:200px; width:').append( totalW + 1 ).append('px; overflow-x:hidden; overflow-y:auto; display:inline-block; margin-bottom:0px;">');
    buf.append('<table class="table table-bordered" style="table-layout:fixed; width:').append( totalW ).append('px; border-top:0px; border-bottom:0px; margin-bottom:0px; ">');
    buf.append('<tbody>');
    
    buf.append('<tr style="height:0px;">');
    buf.append('<td style="width:').append( insColW ).append('px;padding-top:0px;padding-bottom:0px;border-top:0px;border-bottom:0px;"></td>');
    for(var i=0; i<corpArray.length; i++) {
        buf.append('<td style="width:').append(beColW).append('px;padding-top:0px;padding-bottom:0px;border-top:0px;border-bottom:0px;"></td>');
        buf.append('<td style="width:').append(bfColW).append('px;padding-top:0px;padding-bottom:0px;border-top:0px;border-bottom:0px;"></td>');
    }
    buf.append('</tr>');
    
    var inses = {
            'cheSun' : '车辆损失险', 
            'buJiMianCheSun' : '　　不计免赔（车损）', 
            'ziRan' : '自燃损失险', 
            'buJiMianZiRan' : '　　不计免赔（自燃）', 
            'boLi' : '玻璃险', 
            'sheShui' : '涉水险', 
            'buJiMianSheShui' : '　　不计免赔（涉水）', 
            'huaHen' : '车身划痕险', 
            'buJiMianHuaHen' : '　　不计免赔（划痕）', 
            'cheSunSanFangTeYue' : '机动车无法找到第三方特约险', 
            'daoQiang' : '全车盗抢险', 
            'buJiMianDaoQiang' : '　　不计免赔（盗抢）', 
            'sanZhe' : '第三者责任险', 
            'buJiMianSanZhe' : '　　不计免赔（三者）', 
            'siJi' : '司机责任险', 
            'buJiMianSiJi' : '　　不计免赔（司机）', 
            'chengKe' : '车上乘客险', 
            'buJiMianChengKe' : '　　不计免赔（乘客）'
    }
    
    for (var ins in inses) {
        if ( insPlan['is' + ins.substr(0, 1).toUpperCase() + ins.substr(1)] ) {
            buf.append('<tr>');
            buf.append('<td>').append( inses[ins] ).append('</td>');
            for (var i=0; i<corpArray.length; i++) {
                var corpObj = corpArray[i];
                var prefix = 'city' + corpObj.cityId + '_corp' + corpObj.insCorpId + '_';
                buf.append('<td class="text-right" style="padding-right:20px;">');
                buf.append('<span id="').append( prefix + ins + 'BE' ).append('"></span>');
                buf.append('</td>');
                buf.append('<td class="text-right" style="padding-right:20px;">');
                buf.append('<span id="').append( prefix + ins + 'BF' ).append('"></span>');
                buf.append('</td>');
            }
            buf.append('</tr>');
        }
    }
    
    var rateFactors = {
            'rateFactor1' : '无赔款优惠系数', 
            'rateFactor2' : '自主渠道系数', 
            'rateFactor3' : '自主核保系数', 
            'rateFactor4' : '交通违法浮动系数', 
    }
    
    for (var rateFactor in rateFactors) {
        buf.append('<tr>');
        buf.append('<td>').append( rateFactors[rateFactor] ).append('</td>');
        for (var i=0; i<corpArray.length; i++) {
            var corpObj = corpArray[i];
            var prefix = 'city' + corpObj.cityId + '_corp' + corpObj.insCorpId + '_';
            buf.append('<td class="text-center" colspan="2">');
            buf.append('<span id="').append( prefix + rateFactor ).append('"></span>');
            buf.append('</td>');
        }
        buf.append('</tr>');
    }
    
    buf.append('</tbody>');
    buf.append('</table>');
    buf.append('</div>');
    buf.append('</div>');
    
    var $main = $( buf.toString() ).appendTo( $sheet );
    
    // 组织 表尾
    var buf = new StringBuffer();
    buf.append('<table class="table table-bordered" style="table-layout:fixed; width:').append( totalW ).append('px; margin-bottom:0px; ">');
    buf.append('<tfoot>');
    
    buf.append('<tr style="height:0px;">');
    buf.append('<td style="width:').append( insColW ).append('px;padding-top:0px;padding-bottom:0px;border-top:0px;border-bottom:0px;"></td>');
    for (var i=0; i<corpArray.length; i++) {
        buf.append('<td style="width:').append( beColW ).append('px;padding-top:0px;padding-bottom:0px;border-top:0px;border-bottom:0px;"></td>');
        buf.append('<td style="width:').append( bfColW ).append('px;padding-top:0px;padding-bottom:0px;border-top:0px;border-bottom:0px;"></td>');
    }
    buf.append('</tr>');
    
    buf.append('<tr class="active">');
    buf.append('<td class="text-center" style="border-top:0px;">商业险保费</td>');
    for (var i=0; i<corpArray.length; i++) {
        var corpObj = corpArray[i];
        var prefix = 'city' + corpObj.cityId + '_corp' + corpObj.insCorpId + '_';
        buf.append('<td colspan="2" class="text-center" style="border-top:0px;">');
        buf.append('<span id="').append( prefix + 'bizTotalBF' ).append('"></span>');
        buf.append('</td>');
    }
    buf.append('</tr>');
    
    var inses = {
            'force' : '交强险',
            'tax' : '车船税'
    }
    for (var ins in inses) {
        if ( insPlan['is' + ins.substr(0, 1).toUpperCase() + ins.substr(1)] ) {
            buf.append('<tr class="warning">');
            buf.append('<td class="text-center">').append( inses[ins] ).append('</td>');
            for (var i=0; i<corpArray.length; i++) {
                var corpObj = corpArray[i];
                var prefix = 'city' + corpObj.cityId + '_corp' + corpObj.insCorpId + '_';
                buf.append('<td class="text-right" style="padding-right:20px;">');
                buf.append('<span id="').append( prefix + ins + 'BE' ).append('"></span>');
                buf.append('</td>');
                buf.append('<td class="text-right" style="padding-right:20px;">');
                buf.append('<span id="').append( prefix + ins + 'BF' ).append('"></span>');
                buf.append('</td>');
            }
            buf.append('</tr>');
        }
    }
    
    buf.append('<tr class="active">');
    buf.append('<td class="text-center">保费合计</td>');
    for (var i=0; i<corpArray.length; i++) {
        var corpObj = corpArray[i];
        var prefix = 'city' + corpObj.cityId + '_corp' + corpObj.insCorpId + '_';
        buf.append('<td colspan="2" class="text-center">');
        buf.append('<span id="').append( prefix + 'totalBF' ).append('"><i class="fa fa-spinner fa-spin"></i>等待报价...</span>');
        buf.append('</td>');
    }
    buf.append('</tr>');
    
    buf.append('<tr>');
    buf.append('<td class="text-center">操作提示</td>');
    for (var i=0; i<corpArray.length; i++) {
        var corpObj = corpArray[i];
        var prefix = 'city' + corpObj.cityId + '_corp' + corpObj.insCorpId + '_';
        buf.append('<td colspan="2">');
        buf.append('<span id="').append( prefix + 'inQueryTip').append('"></span>');
        buf.append('<div id="').append( prefix + 'tool' ).append('"></div>');
        buf.append('</td>');
    }
    buf.append('</tr>');
    
    buf.append('</tfoot>');
    buf.append('</table>');
    
    var $foot = $( buf.toString() ).appendTo( $sheet );
    
    $('#quoteResult').removeClass('hidden');
}


var quoteAjaxes = new Array();
var underwriAjaxes = new Array();
var quoteTimers = new Array();          // 请求下一报价 延时器
var underwriTimers = new Array();       // 请求核保 延时器

// 核保
function underwriQuote(prefix, viQuoteId) {
    var underwriAjax = $.ajax({
        type : 'POST',
        url : 'viQuote/underwriQuote.json',
        data : {
            'viQuoteId' : viQuoteId
        },
        dataType : 'json',
        success : function(ret, textStatus) {
            
            if (ret.success) {
                var d = ret.data;
                
                if ( d.code == 0 ) {            // 核保通过
                    
                    $('#' + prefix + 'inQueryTip').addClass('hidden');
                    
                    $btn = $('<button viQuoteId="' + viQuoteId + '" class="btn btn-sm btn-primary" style="margin-bottom:0px;">生成订单</button>').appendTo( $('#' + prefix + 'tool').empty() );
                    $btn.click(function(){
                        var viQuoteId = $(this).attr('viQuoteId');
                        var url = 'viOrder/viOrderRecord.do?viQuoteId=' + encodeURIComponent( viQuoteId );
                        
                        // window.open( url );
                        
                        layer.open({
                            type : 2, 
                            title : false, 
                            closeBtn : 0, 
                            content : url, 
                            area : ['95%', '95%'], 
                            scrollbar: false,
                            success: function(layero) {
                                //苹果 iframe 滚动条失效解决方式
                                $(layero).addClass('scroll-wrapper');
                            }
                        });
                        
                    });
                    
                } else if ( d.code == 1 || d.code == 4 ) {     // 1 — 失败，4 — 高风险车
                    
                    $('#' + prefix + 'inQueryTip').addClass('hidden');
                    
                    $('<pre class="toolTipPre text-truncate">' + d.msg + '</pre>').appendTo( $('#' + prefix + 'tool').empty() );
                    $('<a class="pull-right">详情</a>').appendTo( $('#' + prefix + 'tool') ).click(function(){
                        layer.open({
                            type : 1, 
                            title : '提示信息', 
                            content : '<div style="padding:15px;"><pre class="toolTipPre">' + d.msg + '</pre></div>', 
                            scrollbar: false
                        });
                    });
                    
                } else if ( d.code == 3 ) {     // 核保中
                    $('#' + prefix + 'inQueryTip').html('<i class="fa fa-spinner fa-spin"></i>核保中...');
                    // 延迟30秒 异步请求核保接口
                    var timer = setTimeout(function(){
                        underwriQuote(prefix, viQuoteId);
                    }, 30000);
                    quoteTimers.push( timer );
                }
            } else if (ret.msg != null) {
                $('#' + prefix + 'inQueryTip').addClass('hidden');
                
                $('<pre class="toolTipPre text-truncate">' + ret.msg + '</pre>').appendTo( $('#' + prefix + 'tool').empty() );
                $('<a class="pull-right">详情</a>').appendTo( $('#' + prefix + 'tool') ).click(function(){
                    layer.open({
                        type : 1, 
                        title : '提示信息', 
                        content : '<div style="padding:15px;"><pre class="toolTipPre">' + ret.msg + '</pre></div>', 
                        scrollbar: false
                    });
                });
                
            } else {
                $('#' + prefix + 'inQueryTip').addClass('hidden');
                $('#' + prefix + 'tool').html('程序错误，请联系管理员！');
            }
            
        },
        error : function(xmlHttpRequest, textStatus, errorThrown) {
            
            if ( textStatus == 'abort' ) return;
            
            $('#' + prefix + 'inQueryTip').addClass('hidden');
            $('#' + prefix + 'tool').html('核保时，连接错误，请联系管理员！');
        }
    });
    
    underwriAjaxes.push( underwriAjax );
}


// 查询报价
function quoteByPlan(corpArray) {
    
    if (corpArray==null || corpArray.length==0)
        return;
    
    var corpObj = corpArray[0];
    var prefix = 'city' + corpObj.cityId + '_corp' + corpObj.insCorpId + "_";
    
    // alert( prefix );
    // alert( JSON.stringify(corpObj.data) );
    
    $('#' + prefix + 'totalBF').html('<i class="fa fa-spinner fa-spin"></i>报价中...');
    
    var quoteAjax = $.ajax({
        type : 'POST',
        url : 'viQuote/quoteByPlan.json',
        data : corpObj.data,
        dataType : 'json',
        success : function(ret, textStatus) {
            
            if (ret.success) {
                var d = ret.data;
                
                var viQuote = d.viQuote;
                
                //alert( JSON.stringify(viQuote) );
                
                $('#' + prefix + 'forceBE').text( viQuote.forceTotal>0? '要投保': '不投保' );
                $('#' + prefix + 'forceBF').text( viQuote.forceTotal );
                $('#' + prefix + 'taxBE').text( viQuote.taxTotal>0? '要投保': '不投保' );
                $('#' + prefix + 'taxBF').text( viQuote.taxTotal );
                
                var bizInses = ['cheSun', 'ziRan', 'huaHen', 'daoQiang', 
                                'sanZhe', 'siJi', 'chengKe', 'jingShenSunShi'];
                for (var i=0; i< bizInses.length; i++) {
                    var bizIns = bizInses[i];
                    $('#' + prefix + bizIns + 'BE').text( viQuote[bizIns + 'BE'] );
                    $('#' + prefix + bizIns + 'BF').text( viQuote[bizIns + 'BF'] );
                }
                
                $('#' + prefix + "boLiBE").text( viQuote.boLiName );
                $('#' + prefix + "boLiBF").text( viQuote.boLiBF );
                
                var bizInses = ['buJiMianCheSun', 'buJiMianZiRan', 'sheShui', 'buJiMianSheShui', 'buJiMianHuaHen', 
                                'cheSunSanFangTeYue', 'buJiMianDaoQiang', 'buJiMianSanZhe', 'buJiMianSiJi', 'buJiMianChengKe', 
                                'buJiMianJingShenSunShi'];
                for (var i=0; i< bizInses.length; i++) {
                    var bizIns = bizInses[i];
                    $('#' + prefix + bizIns + 'BE').text( viQuote['is' + bizIns.substr(0, 1).toUpperCase() + bizIns.substr(1)]? '要投保': '不投保' );
                    $('#' + prefix + bizIns + 'BF').text( viQuote[bizIns + 'BF'] );
                }
                
                $('#' + prefix + 'rateFactor1').text( viQuote.rateFactor1 );
                $('#' + prefix + 'rateFactor2').text( viQuote.rateFactor2 );
                $('#' + prefix + 'rateFactor3').text( viQuote.rateFactor3 );
                $('#' + prefix + 'rateFactor4').text( viQuote.rateFactor4 );
                
                $('#' + prefix + 'bizTotalBF').text( viQuote.bizTotal );
                $('#' + prefix + 'totalBF').text( viQuote.bizTotal.add( viQuote.forceTotal ).add( viQuote.taxTotal ) );
                
                if ( d.code == 0 ) {            // 报价核保通过
                    $('#' + prefix + 'inQueryTip').addClass('hidden');
                    $btn = $('<button viQuoteId="' + viQuote.id + '" class="btn btn-sm btn-primary" style="margin-bottom:0px;">生成订单</button>').appendTo( $('#' + prefix + 'tool').empty() );
                    $btn.click(function(){
                        var viQuoteId = $(this).attr('viQuoteId');
                        var url = 'viOrder/viOrderRecord.do?viQuoteId=' + encodeURIComponent( viQuoteId );
                        
                        //window.open( url );
                        
                        layer.open({
                            type : 2, 
                            title : false, 
                            closeBtn : 0, 
                            content : url, 
                            area : ['95%', '95%'], 
                            scrollbar: false,
                            success: function(layero) {
                                //苹果 iframe 滚动条失效解决方式
                                $(layero).addClass('scroll-wrapper');
                            }
                        });
                        
                    });
                } else if ( d.code == 1 || d.code == 4 ) {     // 1 — 失败，4 — 高风险车
                    $('#' + prefix + 'inQueryTip').addClass('hidden');
                    
                    $('<pre class="toolTipPre text-truncate">' + d.msg + '</pre>').appendTo( $('#' + prefix + 'tool').empty() );
                    $('<a class="pull-right">详情</a>').appendTo( $('#' + prefix + 'tool') ).click(function(){
                        layer.open({
                            type : 1, 
                            title : '提示信息', 
                            content : '<div style="padding:15px;"><pre class="toolTipPre">' + d.msg + '</pre></div>', 
                            scrollbar: false
                        });
                    });
                    
                } else if ( d.code == 2 ) {     // 待核保
                    $('#' + prefix + 'inQueryTip').html('<i class="fa fa-spinner fa-spin"></i>正在核保...');
                    // 立即异步请求核保接口
                    underwriQuote(prefix, viQuote.id);
                } else if ( d.code == 3 ) {     // 核保中
                    $('#' + prefix + 'inQueryTip').html('<i class="fa fa-spinner fa-spin"></i>核保中...');
                    // 延迟30秒 异步请求核保接口
                    var timer = setTimeout(function(){
                        underwriQuote(prefix, viQuote.id);
                    }, 30000);
                    quoteTimers.push( timer );
                }
            } else if (ret.msg != null) {
                $('#' + prefix + 'totalBF').empty();
                $('#' + prefix + 'inQueryTip').addClass('hidden');
                
                $('<pre class="toolTipPre text-truncate">' + ret.msg + '</pre>').appendTo( $('#' + prefix + 'tool').empty() );
                $('<a class="pull-right">详情</a>').appendTo( $('#' + prefix + 'tool') ).click(function(){
                    layer.open({
                        type : 1, 
                        title : '提示信息', 
                        content : '<div style="padding:15px;"><pre class="toolTipPre">' + ret.msg + '</pre></div>', 
                        scrollbar: false
                    });
                });
                
            } else {
                $('#' + prefix + 'totalBF').empty();
                $('#' + prefix + 'inQueryTip').addClass('hidden');
                $('#' + prefix + 'tool').html('程序错误，请联系管理员！');
            }
            
            corpArray.splice(0, 1);
            
            // 延迟20秒请求下一保险公司，防止被锁
            var timer = setTimeout(function(){
                quoteByPlan(corpArray);
            }, 20000);
            quoteTimers.push( timer );
            
        },
        error : function(xmlHttpRequest, textStatus, errorThrown) {
            
            if ( textStatus == 'abort' ) return;
            
            $('#' + prefix + 'totalBF').empty();
            $('#' + prefix + 'inQueryTip').addClass('hidden');
            $('#' + prefix + 'tool').html('查询报价时，连接错误，请联系管理员！');
            
            corpArray.splice(0, 1);
            
            // 延迟20秒请求下一保险公司，防止被锁
            var timer = setTimeout(function() {
                quoteByPlan(corpArray);
            }, 20000);
            quoteTimers.push( timer );
            
        }
    });
    
    quoteAjaxes.push( quoteAjax );
}


//显示报价结果
function showQuoteResult() {
    
    //$('#quoteResult').removeClass('hidden');
    //return;
    
    // 根据不同城市、不同保险公司报价
    var array = $('#quoteForm').serializeArray();
    for (var i=array.length-1; i>=0; i--) {
        var obj = array[i];
        if (obj.name=='city' || obj.name=='insCorp') {
            array.splice(i, 1);
        }
    }
    
    var corpArray = new Array();
    
    var selCitys = $('input[name="city"]:checked');
    $.each(selCitys, function(){
        
        var cityId = $(this).val();
        var cityName = $(this).attr('text');
        
        var selInsCorps = $('input[name="insCorp"]:checked');
        $.each(selInsCorps, function(){
            
            var insCorpId = $(this).val();
            var insCorpName = $(this).attr('text');
            
            corpArray.push({
                'cityId' : cityId, 
                'cityName' : cityName, 
                'insCorpId' : insCorpId, 
                'insCorpName' : insCorpName, 
                'data' : $.merge([{'name' : 'city', 'value' : cityId}, {'name' : 'insCorp', 'value' : insCorpId}], array)
            });
            
            
        });
        
    });
    
    // 要报价的险种
    var insPlan = new Object();
    insPlan.isForce = $('input:radio[name="forceTax"]:checked').val() != '0'? true: false;
    insPlan.isTax = $('input:radio[name="forceTax"]:checked').val() != '0'? true: false;
    insPlan.isCheSun = $('input:radio[name="cheSun"]:checked').val()=='1'? true: false;
    insPlan.isBuJiMianCheSun = $('input:radio[name="buJiMianCheSun"]:checked').val()=='1'? true: false;
    insPlan.isZiRan = $('input:radio[name="ziRan"]:checked').val()=='1'? true: false;
    insPlan.isBuJiMianZiRan = $('input:radio[name="buJiMianZiRan"]:checked').val()=='1'? true: false;
    insPlan.isBoLi = $('input:radio[name="boLi"]:checked').val() != '0'? true: false;
    insPlan.isSheShui = $('input:radio[name="sheShui"]:checked').val()=='1'? true: false;
    insPlan.isBuJiMianSheShui = $('input:radio[name="buJiMianSheShui"]:checked').val()=='1'? true: false;
    insPlan.isHuaHen = $('#huaHen').val()!='0'? true: false;
    insPlan.isBuJiMianHuaHen = $('input:radio[name="buJiMianHuaHen"]:checked').val()=='1'? true: false;
    insPlan.isCheSunSanFangTeYue = $('input:radio[name="cheSunSanFangTeYue"]:checked').val()=='1'? true: false;
    insPlan.isDaoQiang = $('input:radio[name="daoQiang"]:checked').val()=='1'? true: false;
    insPlan.isBuJiMianDaoQiang = $('input:radio[name="buJiMianDaoQiang"]:checked').val()=='1'? true: false;
    insPlan.isSanZhe = $('#sanZhe').val()!='0'? true: false;
    insPlan.isBuJiMianSanZhe = $('input:radio[name="buJiMianSanZhe"]:checked').val()=='1'? true: false;
    insPlan.isSiJi = $('#siJi').val()!='0'? true: false;
    insPlan.isBuJiMianSiJi = $('input:radio[name="buJiMianSiJi"]:checked').val()=='1'? true: false;
    insPlan.isChengKe = $('#chengKe').val()!='0'? true: false;
    insPlan.isBuJiMianChengKe = $('input:radio[name="buJiMianChengKe"]:checked').val()=='1'? true: false;
    
    // 组织报价结果模板
    initQuotePage( corpArray, insPlan );
    
    //return;
    
    // 请求报价
    quoteByPlan( corpArray );
}

// 返回 报价申请 界面
function toQuoteApply() {
    $('#quoteResult').addClass('hidden');
    
    // 中断请求
    for (var i=0; i<quoteTimers.length; i++) {
        clearTimeout(quoteTimers[i]);
    }
    quoteTimers.splice(0, quoteTimers.length);
    
    for (var i=0; i<underwriTimers.length; i++) {
        clearTimeout(underwriTimers[i]);
    }
    underwriTimers.splice(0, underwriTimers.length);
    
    for (var i=0; i<quoteAjaxes.length; i++) {
        var quoteAjax = quoteAjaxes[i];
        quoteAjax.abort();
    }
    quoteAjaxes.splice(0, quoteAjaxes.length);
    
    for (var i=0; i<underwriAjaxes.length; i++) {
        var underwriAjax = underwriAjaxes[i];
        underwriAjax.abort();
    }
    underwriAjaxes.splice(0, underwriAjaxes.length);
    
    $('#quoteApply').removeClass('hidden').addClass('animated fadeInUp');
}

