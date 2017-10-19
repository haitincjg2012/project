//重写ajax方法
(function ($) {
    //首先备份下jquery的ajax方法
    var _ajax = $.ajax;

    //重写jquery的ajax方法
    $.ajax = function (opt) {
        //备份opt中error和success方法
        var fn = {
            error: function (XMLHttpRequest, textStatus, errorThrown) {
            },
            success: function (data, textStatus) {
            }
        };
        if (opt.error) {
            fn.error = opt.error;
        }
        if (opt.success) {
            fn.success = opt.success;
        }

        var index = "";
        //扩展增强处理
        var _opt = $.extend(opt, {
            dataType: opt.dataType || "json",
            type: opt.type || "post",
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                //错误方法增强处理
                if (textStatus === "error") {
                    if (layer) {
                        var XMLHttpRequestCode = XMLHttpRequest.status;
                        switch (XMLHttpRequestCode) {
                            case 0:
                                layer.msg("你请求的资源不存在");
                                break;
                            case 500:
                                layer.msg("服务器内部异常");
                                break;
                            default:
                                layer.msg("出错了,请联系管理员!");
                                break;
                        }

                    } else {
                        alert("出错了,请联系管理员!");
                    }
                }

                fn.error(XMLHttpRequest, textStatus, errorThrown);
            },
            success: function (data, textStatus) {
                //成功回调方法增强处理
                fn.success(data, textStatus);
            },
            beforeSend: function (XHR) {
                //错误方法增强处理
                if (layer) {
                    index = layer.load(0, {shade: 0.05});
                } else {
                    alert("出错了,请联系管理员!");
                }
            },
            complete: function (XHR, TS) {
                //请求完成后回调函数 (请求成功或失败之后均调用)。
                layer.close(index);
            }
        });
        return _ajax(_opt);
    };
})(jQuery);