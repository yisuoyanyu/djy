/**
 * 弹出alert窗口
 * @param {Object} JSON数据
 *                 {
 *                     msg : '',
 *                     ok : function,
 *                     relatedTarget : element
 *                 }
 */
function Alert(param){
	var obj;
	if (typeof(param)=='undefined'||param==null) {
		obj = {};
	} else if (typeof(param)=='number' || typeof(param)=='string' || typeof(param)=='boolean') {
		obj = {msg : param};
	} else {
		obj = param;
	}
	
	if (typeof(obj.msg) == 'undefined' || obj.msg==null || obj.msg=='') obj.msg = '　';
	
	$('#ID_Alert').remove();
	
	var html = '<div id="ID_Alert" class="am-modal am-modal-confirm alert" tabindex="-1">'
		+ '<div class="am-modal-dialog">'
		+ '<div id="ID_Alert_Msg" class="am-text-left am-padding-horizontal-sm am-padding-vertical"></div>'
		+ '<div><span data-am-modal-confirm class="am-modal-btn ok">确定</span></div>'
		+ '</div></div>';
	$('body').append(html);
	
	$('#ID_Alert_Msg').text(obj.msg);
	
	$('#ID_Alert').modal({
		relatedTarget: obj.relatedTarget,
		onConfirm: obj.ok,
		closeViaDimmer : false
	});
}

/**
 * 弹出confirm窗口
 * @param {Object} JSON数据
 *                 {
 *                     msg : '',
 *                     cancelBtn : '',
 *                     okBtn : '',
 *                     cancel : function,
 *                     ok : function,
 *                     relatedTarget : element
 *                 }
 */
function Confirm(param){
	var obj = (typeof(param)=='undefined'||param==null)? {}: param;
	
	if (typeof(obj.msg) == 'undefined' || obj.msg==null) obj.msg = '';
	if (typeof(obj.cancelBtn) == 'undefined' || obj.cancelBtn==null) obj.cancelBtn = '取消';
	if (typeof(obj.okBtn) == 'undefined' || obj.okBtn==null) obj.okBtn = '确定';
	
	$('#ID_Confirm').remove();
	
	var html = '<div id="ID_Confirm" class="am-modal am-modal-confirm confirm" tabindex="-1">'
		+ '<div class="am-modal-dialog">'
		+ '<div id="ID_Confirm_Msg" class="am-text-left am-padding-horizontal-sm am-padding-vertical"></div>'
		+ '<div>'
		+ '<ul class="am-avg-sm-2">'
		+ '<li><span id="ID_Confirm_Cancel" data-am-modal-cancel class="am-modal-btn cancel">取消</span></li>'
		+ '<li><span id="ID_Confirm_OK" data-am-modal-confirm class="am-modal-btn ok">确定</span></li>'
		+ '</ul></div></div></div>';
	$('body').append(html);
	
	$('#ID_Confirm_Msg').text(obj.msg);
	$('#ID_Confirm_Cancel').text(obj.cancelBtn);
	$('#ID_Confirm_OK').text(obj.okBtn);
	
	$('#ID_Confirm').modal({
		relatedTarget: obj.relatedTarget,
		onConfirm: obj.ok,
		onCancel: obj.cancel,
		closeViaDimmer : false
	});
}