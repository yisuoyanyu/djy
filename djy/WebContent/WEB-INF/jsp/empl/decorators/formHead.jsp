<%@ page pageEncoding="UTF-8"%>
		
		<script src="js/form.js" type="text/javascript"></script>
		
		<!-- jQuery Validation -->
		<script src="plugins/jQueryValidation/jquery.validate.min.js?v=1.16.0" type="text/javascript"></script>
		<script src="plugins/jQueryValidation/localization/messages_zh.min.js" type="text/javascript"></script>
<script type="text/javascript">
    // 以下为修改jQuery Validation插件兼容Bootstrap的方法，没有直接写在插件中是为了便于插件升级
    $.validator.setDefaults({
        highlight: function (element) {
            $( element ).closest('.form-group').removeClass('has-success').addClass('has-error');
        }, 
        success: function (element) {
            element.closest('.form-group').removeClass('has-error').addClass('has-success');
        }, 
        errorElement: "span", 
        errorPlacement: function (error, element) {
            if (element.is(":radio") || element.is(":checkbox")) {
                error.appendTo(element.parent().parent().parent());
            } else {
                error.appendTo(element.parent());
            }
        }, 
        errorClass: "help-block m-b-none", 
        validClass: ""
    });
    $(function() {
        $("[data-rule-required='true']").closest('.form-group').children('.control-label').append(' *');
    });
</script>