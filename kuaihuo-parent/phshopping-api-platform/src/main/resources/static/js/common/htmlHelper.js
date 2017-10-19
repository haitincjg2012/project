var HtmlHelper = {
    adapterStringlHandler: function (str, data) {
        for (var col in data) {
            eval("var replaceStr = /{" + col + "}/g");
            str = str.replace(replaceStr, data[col]);
        }
        return str;
    },
    generateUrl: function (href, map) {

        if (isNull(href)) {
            return false;
        }

        if (isNotNull(map)) {
            var keyValue = [];

            for (var key in map) {
                if (isNotNull(map[key])) {
                    keyValue.push(key + "=" + map[key]);
                }
            }

            return href + "?" + keyValue.join("&");
        } else {
            return href;
        }
    },
    generateAutoSubmitForm: function (options) {
        var defaults = {
            target: "_self",
            data: null,
            url: null,
            method: "post"
        };
        var opt = $.extend(defaults, options);

        if (isNull(opt.url) || isNull(opt.data)) {
            return false;
        }

        var form = '<form method="' + opt.method + '" id="autoSubmitForm" action="' + opt.url + '" target="' + opt.target + '">';

        for (var key in  opt.data) {
            if (isNotNull(opt.data[key])) {
                var input = '<input type="hidden" name="{name}" value="{value}" />';
                var inputModel = {
                    name: null,
                    value: null
                };
                inputModel.name = key;
                inputModel.value = opt.data[key];

                form += HtmlHelper.adapterStringlHandler(input, inputModel);
            }

        }

        form += '</form>';

        $("body:eq(0)").append(form);
        $("#autoSubmitForm").submit();
    },
    //统一返回list页面方法
    list: function (options) {
        var defaults = {
            searchFormId: "searchForm",
            searchButtonId: "searchButton",
            tableId: "table_list",
            pageDivId: "table_page",
            domainName:"",//域名
            type: "get",
            data: {},
            url: null,
            multiple: true,//允许多选 默认true
            pageNum: 1, //默认当前页数
            pageSize: 10, //默认分页条数
            count: 0, //默认总条数
            htmlTemplate: null, //指定的list模板
            backFun: null ,//回调函数
            selectAllInputId:"select_all" //默认全选InputId
        };
        var opt = $.extend(defaults, options);
        opt.data.pageNum = opt.pageNum;
        opt.data.pageSize = opt.pageSize;
        var $searchButton = $("#" + opt.searchButtonId);
        var $searchForm = $("#" + opt.searchFormId) || $("body").find("form:first");
        if (isNotNull($searchForm)) {
            $.extend(opt.data, $searchForm.formDataToJson());
            //绑定button事件
            if (isNotNull($searchButton)) {
                //解除绑定事件
                $searchButton.unbind("click");
                $searchButton.on("click", function () {
                    $(this).searchButtonClick(opt);
                })
            }
        }

        $.ajax({
            url:opt.domainName + opt.url,
            type: opt.type,
            data: opt.data,
            success: function (list) {
                if (isNotNull(list.code) && list.code === 200) {
                    generateList(list, opt);
                    //分页
                    var pageContent = $("#" + opt.pageDivId);
                    $(pageContent).extendPagination({
                        totalCount: opt.count,
                        showCount: opt.pageNum,
                        limit: opt.pageSize,
                        callback: function (curr) {
                            opt.pageNum = curr;
                            HtmlHelper.list(opt);
                        }
                    });

                    //回调函数
                    if (opt.backFun) {
                        eval("opt.backFun(list.data)");
                    }
                } else {
                    layer.msg(list.message || "返回异常,未返回错误信息");
                }
            }
        });
    }
};

//填充html元素
$.fn.adapterHtmlHandler = function (data) {
    var thisHtml = $(this).html();
    return HtmlHelper.adapterStringlHandler(thisHtml, data);
};

//清空form所有的值
$.fn.formEmpty = function () {
    $(this).find("[name]").val("");
    return this;
};

//ajax from提交
$.fn.formSubmit = function (options) {
    var defaults = {
        startFun: null,
        url: null,
        type: "post",
        data: null, //扩展form表单提交字段
        dataType: "json",
        backFun: null,
        initValidate: true,
        needContinue: true
    };

    var $submitButton = $(this);
    var opt = $.extend(defaults, options);
    var $submitForm = $submitButton.recursiveParentNode("form");

    //表单验证初始化
    if (opt.initValidate) {
        $submitForm.validationEngine({
            addPromptClass: 'formError-text',
            showArrow: false,
            promptPosition: 'bottomLeft:20 0',
            scroll: false
        });
        $submitForm.on("change", "select", function () {
            $(this).validationEngine("validate");
        });
        opt.initValidate = false;
    }
    //参数通过jquery validate
    if (!$submitForm.validationEngine("validate")) {
        return false;
    }

    if (isNotNull(opt.startFun) && typeof opt.startFun === "function") {
        opt.startFun(opt);
        if (!opt.needContinue) {
            return false;
        }
    }

    var data = $.extend($submitForm.formDataTransJson(), opt.data);

    if (isNotNull(opt.url)) {
        $.ajax({
            url: opt.url,
            type: opt.type,
            data: data,
            dataType: opt.dataType,
            success: function (result) {
                if (isNotNull(opt.backFun) && typeof opt.backFun === "function") {
                    opt.backFun(result);
                }
            }

        })
    } else {
        return false;
    }

    return this;
};

//将form中所有含有name的标签 值返回一个json,用来保存
$.fn.formDataTransJson = function () {
    var returnJson = {};
    $(this).find("[name]").each(function () {
        var $this = $(this);
        if ((isNotNull($this.val()) && $this.prop("type") !== "radio" ) || ($this.prop("type") === "radio" && $this.is(":checked"))) {
            putValue(returnJson, $this.prop("name"), $this.val());
        }

        function putValue(json, key, value) {
            json[key] === undefined ? json[key] = value : isArray(json[key]) ? json[key].push(value) : json[key] = [json[key], value];
        }
    });
    return returnJson;
};

//判断obj是否为空 空true 非空false
function isNull(obj) {
    return obj === undefined || obj === null || (typeof obj === "string" && obj.replace("/s+/g", "") === "");
}

function isNotNull(obj) {
    return !isNull(obj);
}

//时间处理
function fullZero(m){return m<10?'0'+m:m }

function getDate(timestamp) {
    //timestamp，否则要parseInt转换
    var time = new Date(parseInt(timestamp));
    var y = time.getFullYear();
    var m = time.getMonth()+1;
    var d = time.getDate();
    var h = time.getHours();
    var mm = time.getMinutes();
    var s = time.getSeconds();
    return y+'-'+fullZero(m)+'-'+fullZero(d)+' '+fullZero(h)+':'+fullZero(mm)+':'+fullZero(s);
}

function ifNullDefaultString(obj,Default) {
    return obj == null || obj == "" || obj == undefined || (obj instanceof Array  && obj.length ==0)?Default?Default:"":obj;
}

function isArray(o) {
    return Object.prototype.toString.call(o) === "[object Array]";
}



//递归返回指定的parent节点
$.fn.recursiveParentNode = function (specifyParentTagName) {

    if (isNull(specifyParentTagName) || typeof specifyParentTagName !== "string") {
        return false;
    }

    var $parent = $(this).parent(), currentParentTagName = $parent[0].tagName;

    if (isNull(currentParentTagName)) {
        return false;
    }

    if (specifyParentTagName.toLowerCase() === currentParentTagName.toLowerCase()) {
        return $parent;
    } else {
        return $parent.recursiveParentNode(specifyParentTagName);
    }
};

$.fn.searchButtonClick = function (opt) {
    var $this = $(this);
    $this.prop("disabled", "disabled");
    $this.html("查询中...");
    $this.css("background-color", "#666");
    setTimeout(function () {
        $this.removeAttr("disabled");
        $this.css("background-color", "#337ab7");
        $this.html("查询");
    }, 1000);
    HtmlHelper.list(opt);
};

function generateList(list, opt) {
    opt.count = list.count;
    var $table = $("#" + opt.tableId) || $("body").find("table:first");
    //表头
    var $th = $table.find('th');

    if (opt.count === 0) {
        $table.after("<h4 style='width: 100px'>暂无数据！</h4>");
    } else {
        var tbody = " <tbody>";
        $(list.data).each(function (i, record) {
            tbody += $th.generateListTrByTh(record, opt);
        });
        tbody += "</tbody>";
        var $tbody = $table.find("tbody:first");
        if ($tbody) {
            $tbody.remove();
        }
        $table.append(tbody);
    }
}

/** 渲染list返回值 */
$.fn.generateListTrByTh = function (record, opt) {
    var $selectAllInput = $("#"+opt.selectAllInputId);
    var selectAllDateName = $selectAllInput.attr("data-name");
    var returnTr = opt.multiple ? "<tr><td ><input name='"+selectAllDateName+"' type='checkbox'></td>" : "<tr>";
    $(this).each(function (i, th) {
        //如果多选 跳过第一个
        if (opt.multiple && i === 0) {
            return true;
        } else {
            var $listTr = $(th);
            var data_type = $listTr.attr("data-type");
            var data_name = $listTr.attr("data-name");
            var data_fun = $listTr.attr("data-fun");
            var value = record[data_name];
            returnTr +=  "<td>" + renderValue(data_type,data_fun,value) + "</td>";
        }
    });
    return returnTr + "</tr>";
};

/** 渲染tr */
$.fn.renderElementByInnerHtml = function () {
    var dataType = $(this).attr("data-type");
    var dataFun = $(this).attr("data-fun");
    var value = $(this).html();
    value = value.indexOf(",") !== -1 ? value.split(",") : value;
    var $this = $(this);
    $(this).html(renderValue(dataType, dataFun, value, $this));
};

/*渲染返回值*/
function renderValue(dataType, dataFun, value, $this) {
    if (isNotNull(dataType)) {
        switch (dataType) {
            case "date_00":
                return  getDate(ifNullDefaultString(value,""));
                break;
        }
    } else if (isNotNull(dataFun)) {
        if(Array.isArray(value)){
            var length = value.length;
            switch (length){
                case 1:
                    return eval("" + dataFun + "(" + value[0] + ", $this)");
                    break;
                case 2:
                    return eval("" + dataFun + "(" + value[0] + "," + value[1] + ", $this)");
                    break;
                case 3:
                    return eval("" + dataFun + "(" + value[0] + "," + value[1] + "," + value[2] + ", $this)");
                    break;
                case 4:
                    return eval("" + dataFun + "(" + value[0] + "," + value[1] + "," + value[2] + "," + value[3] + ", $this)");
                    break;
            }
        }else{
            return eval("" + dataFun + "(" + value + ", $this)");
        }

    } else {
        return ifNullDefaultString(value,"");
    }
}


//全选按钮操作事件
$.fn.selectAll = function (opt) {
    var $selectAllInput = $("#"+opt.selectAllInputId);
    var $table = $("#"+opt.tableId);
    var $checkInput = $table.find("input[type='checkbox']");
    $checkInput.prop("checked",$selectAllInput.is(":checked"));
    generateIds($selectAllInput,$table);
};

function generateIds($selectAllInput,$table) {
    var idArr = [];
    $table.find("tbody:first").each("tr",function () {
        var input = $(this).find("td:first").find("input:first");
        input.is(":checked")?idArr.push(input.val()):"";
    });
    return $selectAllInput.val(idArr.join(","));
}

//数组过滤
Array.prototype.unique = function(){
    var res = [],json = {};
    this.forEach(function(_this){
        if(!json[_this]){
            res.push(_this);
            json[_this] = 1;
        }
    });
    return res;
};

//数组去重
Array.prototype.filterByStr = function(str){
    var res = [];
    this.forEach(function(_this){
        if(_this.indexOf(str)!==-1)
            res.push(_this);
    });
    return res;
};




