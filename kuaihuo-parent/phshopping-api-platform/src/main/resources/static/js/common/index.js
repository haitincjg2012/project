function PopupBase() {
    this.show = function(btnBox, btn, con){
        $(btnBox).on('click', btn ,function(){
            $(con).fadeIn();
        });
    };
    this.hide = function PopupHide(btn, con) {
        $(btn).on('click',function(){
            $(con).fadeOut();
        })
    };
    this.showHide = function PopupHide(btn, con, box) {
        $(btn).on('click',function(){
            $(con).fadeOut();
            $(box).fadeIn();
        })
    };
}
$(function() {
    //获取短信验证码
    var validCode = true;
    $(".realNameCode").click(function () {
        var time = 60;
	        var code = $(this);
        if (validCode) {
            validCode = false;
            code.addClass("disableCode");
            code.attr('disabled',true);
            code.css('cursor','default');
            var t = setInterval(function () {
                time--;
                code.val(time + "秒");
                if (time == 0) {
                    clearInterval(t);
                    code.val("重新获取");
                    validCode = true;
                    code.removeClass("disableCode");
                    code.attr('disabled',false);
                    code.css('cursor','pointer');
                }
            }, 1000)
        }
    });

    //左侧导航
    var Accordion = function(el, multiple) {
        this.el = el || {};
        this.multiple = multiple || false;
        // Variables privadas
        var links = this.el.find('.link');
        var submenu = this.el.find('.submenu').find('a');
        // Evento
        links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown);
        submenu.on('click',function(){
            $(this).addClass('link_a').parent('li').siblings().find('a').removeClass('link_a');
            $(this).addClass('link_a').parents('.navBox li').siblings().find('a').removeClass('link_a');
        });
        //console.log(submenu);
    };
    Accordion.prototype.dropdown = function(e) {
        var $el = e.data.el;
        $this = $(this);
        $next = $this.next();

        $next.slideToggle();
        $this.parent().toggleClass('open');
        //$el.find('.submenu a').removeClass('link_a');
        //console.log($this.parent().find('.submenu a'));

        if (!e.data.multiple) {
            $el.find('.submenu').not($next).slideUp().parent().removeClass('open');
        }
    };

    var accordion = new Accordion($('#accordion'), false);

    //操作按钮
    $('.ph_wareBox').on('click', '.ph_operate', function(event){
        //取消事件冒泡
        event.stopPropagation();
        if($(this).siblings('.ph_operateList').hasClass('hide')){
            $(this).addClass('hover');
            $(this).siblings('.ph_operateList').removeClass('hide');
        }else{
            $(this).removeClass('hover');
            $(this).siblings('.ph_operateList').addClass('hide');
        }
        $(this).parents('tr').siblings('tr').find('.ph_operateList').addClass('hide');
        $(this).parents('tr').siblings('tr').find('.ph_operate').removeClass('hover');
        return false;
    });
    //点击空白处隐藏弹出层，下面为滑动消失效果和淡出消失效果。
    $(document).click(function(event){
        var _con = $('.ph_operateList');   // 设置目标区域
        if(!_con.is(event.target) && _con.has(event.target).length === 0){ // Mark 1
            //$('#divTop').slideUp('slow');   //滑动消失
            $('.ph_operateList').addClass('hide');          //淡出消失
            $('.ph_operate').removeClass('hover');
        }
    });

    $('.ph_wareBox').on('click','.ph_operateList a',function(){
        $(this).parents('.ph_operateList').addClass('hide');
        $(this).parents('.ph_operateList').siblings('.ph_operate').removeClass('hover');
    });
    //全选
    $('.ph_wareBox').on('click','.check-all',function(){
        $('.ids').prop('checked',this.checked);
        //console.log($('.ids:checkbox:disabled').prop('disabled'));
        if($('.ids:disabled').prop('disabled')){
            $('.ids:disabled').prop('checked',false);
        }
    });
    //全选/全不选
    var flag=true;
    $('.PopupBtn').on('click','.mitAll',function () {
        if(flag==true){
            $('.ids').prop('checked', true);
            $('.check-all').prop('checked', true);
            $('input.mitAll').val('全不选');
            flag=false;
        }else {
            $('.ids').prop('checked', false);
            $('.check-all').prop('checked', false);
            $('input.mitAll').val('全选');
            flag=true;
        }
        if ($('.ids:disabled').prop('disabled')) {
            $('.ids:disabled').prop('checked', false);
        }

    });
    $('.ph_wareBox').on('click','.mitAll',function(){
        $('.ids').prop('checked',true);
        $('.check-all').prop('checked',true);
        if($('.ids:disabled').prop('disabled')){
            $('.ids:disabled').prop('checked',false);
        }
    });
    $('.ph_wareBox').on('click','.ids',function(){
        var option = $('.ids');
        var Cnum=option.not(":disabled").size();
        var count=0;

        option.each(function(i) {
            if($(this).is(':checked')) {
                count++;
            }
        });
        if( Cnum == count) {
            $('.check-all').prop('checked',true);
        }else{
            $('.check-all').prop('checked',false);
        }

    });
    //编辑、保存切换;限制数字输入
    var fl=true;
    $('.table_input').attr('onkeyup','');
    $('.table_input').attr('onblur','');
    $('.readonly').click(function () {
        if(fl==true){
            $('.table_input').attr('readonly',false);
            $('.table_input').attr('onkeyup','(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,"");}).call(this)');
            $('.table_input').attr('onblur','this.v()');
            $(this).val('保存');
            fl=false;
        }else{
            $('.table_input').attr('readonly',true);
            $('.table_input').attr('onkeyup','');
            $('.table_input').attr('onblur','');
            $(this).val('编辑');
            fl=true;
        }

    });
    //申请代理
    $('.agency_apply').addClass('apply_disabled');
    $('.agency_apply').click(function () {
        $(this).addClass('apply_disabled');
        $(this).val('申请已提交请等待客服联系您');
        $(this).attr('disabled','disabled');
    });

    $('.agency_check').change(function () {
        if($(this).is(':checked')){
            $('.agency_apply').removeClass('apply_disabled');
            $('.agency_apply').attr('disabled', false);
        }else{
            $('.agency_apply').addClass('apply_disabled');
            $('.agency_apply').attr('disabled', true);
        }
    });
    //IC上传
    //作者：刘弘愿 时间：20170518
    $(".icCard_save").addClass('apply_disabled');
    $('.icCard_save').attr('disabled', true);
    $(".upload-url").val('未选择任何文件');
    $(".upload .upload-input-file").change(function () {
        if ($(this).parent().html().indexOf("class=\"upload-url\"") != -1) {
            var fileUrl = $(this).val();
            var urlArr = fileUrl.split("\\");
            var getName = urlArr[urlArr.length - 1];
            var index1=getName.lastIndexOf(".");
            var index2=getName.length;
            var suffix=getName.substring(index1+1,index2);//后缀名
            if (suffix=='xls'||suffix=='xlsx') {
                $(this).parent().children(".upload-url").val(getName);
            }else{
                alert('上传的文件不是xls或者xlsx格式，请重新选择文件');
                $(this).parent().children(".upload-url").val('未选择任何文件');
                $(".icCard_save").addClass('apply_disabled');
                $('.icCard_save').attr('disabled',true );
                return;
            }
            $(this).parent().children(".upload-url").val(getName);

            if(fileUrl.length>0){
                $(".upload-url").css('width',fileUrl.length*10+50+'px');
                $(".icCard_save").removeClass('apply_disabled');
                $('.icCard_save').attr('disabled', false);
            }
            if(fileUrl==''){
                $(this).parent().children(".upload-url").val('未选择任何文件');
                $(".icCard_save").addClass('apply_disabled');
                $('.icCard_save').attr('disabled',true );
            }
        }
    });
    //推广师列表中的账号类型
    //作者：刘弘愿 时间：20170518
    $('#accountType').children('option').first().hide();
    $('#accountType').parent('selection').parent('option').first().hide();
    // 添加分类页面默认初始化
    $('.Category-parent').css('display','none');
    $('input.Category-1').attr('checked',true);
    $('input[name="Category"]').click(function () {
        if($('input.Category-1').is(":checked")){
            $('.Category-parent').css('display','none');
        }
        if($('input.Category-2').is(":checked")){
            $('.Category-parent').css('display','block');
            $('.level3').css('visibility','hidden');
        }
        if($('input.Category-3').is(":checked")){
            $('.Category-parent').css('display','block');
            $('.level3').css('visibility','visible');
        }
    })

});