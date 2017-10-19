/** 级联js  xkw */
var blankSelect = '<option value="">请选择</option>';
function cascade(options) {
    var defaults = {
        startFun: null,
        url: "/web/position/getPositionList",
        totalSelect: 5,
        DivId: "area_div",
        pid: 0,
        extendsData: null, //扩展提交data
        cascadeArr: ["", "", "", ""],
        spaceArrLeft: [0, 0, 0, 0, 0],
        spaceArrRight: [0, 0, 0, 0, 0],
        bindCols: ["provinceId", "cityId", "countyId", "townId"],
        selectNames: ["provinceId", "cityId", "countyId", "townId"],
        requestIds: ["provinceId", "cityId", "countyId", "townId"],
        requestNames: ["provinceName", "cityName", "countyName", "townName"],
        selectCode: ["provinceId", "cityId", "countyId", "townId"],
        selectClasses: ["validate[required]", "validate[required]", "validate[required]", "validate[required]"],
        bindData: null,
        backFun: null,
        frozenSelect: true
    };
    var opt = $.extend(defaults, options), $opt_div = $("#" + opt.DivId), i;
    $opt_div.html("");
    $opt_div.removeAttr("data-area-position-id-selected");
    if ($opt_div.find("select").length === 0) {
        for (i = 0; i < opt.totalSelect; i++) {
            var bindValue = opt.bindData === null ? "" : opt.bindData[i] || "";
            var select = '<select data-cascade-val="' + bindValue + '" data-area-index=' + i + ' class="selectUp selectpicker linkage ' + opt.selectClasses[i] + '" data-live-search="true" name=' + opt.selectNames[i] + '>' + blankSelect + '</select>';
            $opt_div.append(outSpace(opt.spaceArrLeft[i]) + opt.cascadeArr[i] + outSpace(opt.spaceArrRight[i]) + select);
        }
    }
    $opt_div.find('select').each(function (i, select) {
        bootstrapSelectInit($(select), opt.frozenSelect);
    });
    $opt_div.fullSelect(opt, 0).areaClick(opt);
}
$.fn.fullSelect = function (opt, indexSelect) {
    var $this = $(this);
    if (opt.totalSelect === indexSelect) {
        return false;
    }
    var idPosition = opt.requestIds[indexSelect - 1 < 0 ? 0 : indexSelect - 1];
    var _idPosition = opt.selectCode[indexSelect];
    var bindPosition = opt.bindCols[indexSelect];
    var namePosition = opt.requestNames[indexSelect];
    var data = {};
    data[idPosition] = opt.pid;
    data = $.extend(data, opt.extendsData);
    $.ajax({
        type: "post",
        dataType: "json",
        data: data,
        url: opt.url,
        success: function (data) {
            var select = $this.find("select:eq(" + indexSelect + ")")[0];
            var dataCascadeVal = $(select).attr("data-cascade-val");
            var areaIds = [];
            $.each(data, function (index, item) {
                if (isNotNull(item[_idPosition])) {
                    if ($.inArray(item[_idPosition], areaIds) !== -1) {
                        return true;
                    }
                }
                $(select).append('<option data-area-code=' + item[_idPosition] + ' ' +
                    'value=' + item.id + ' ' + (dataCascadeVal == item[bindPosition] ? " selected=selected " : "") + '>'
                    + item[namePosition] + '</option>');
                areaIds.push(item[_idPosition]);
            });
            if (isNotNull(dataCascadeVal)) {
                $(select).attr("data-area-code-selected", dataCascadeVal);
            }
            if (indexSelect++ < opt.totalSelect) {
                opt.pid = $(select).attr("data-cascade-val") || "";
                if (opt.pid !== "") {
                    $this.fullSelect(opt, indexSelect);
                }
            }
            bootstrapSelectInit($(select), opt.frozenSelect);
            if (typeof opt.backFun === "function" && opt.backFun) {
                opt.backFun();
            }
        }
    });
    return this;
};

$.fn.areaClick = function (opt) {
    //移除之前的change事件
    $(this).off("change", "select");
    $(this).on("change", "select", function () {
        if (typeof opt.startFun === "function" && opt.startFun) {
            opt.startFun();
        }
        $(this).attr("data-area-code-selected", $(this).find("option:selected").attr("data-area-code")); //增加选择的区域code
        var index = parseInt($(this).attr("data-area-index")), $parent = $(this).parent().parent();
        if (index === (opt.totalSelect - 1)) {
            if ($(this).val() === "") {
                $(this).removeAttr("data-area-code-selected");
            }
            $parent.attr("data-area-position-id-selected", $parent.find("select[data-area-code-selected]:last").val() || "");
            return;
        }
        if ($(this).val() === "") {
            $(this).areaClickBlank(opt, index);
        } else {
            var pid = $(this).attr("data-area-code-selected") === "0" || $(this).attr("data-area-code-selected") === "undefined" ? $(this).val() : $(this).attr("data-area-code-selected");
            $(this).parent().next().find('select:eq(0)').removeAttr("disabled");
            $(this).parent().next().find('select:eq(0)').nextSelectShow(opt, index, pid);
        }
        $parent.attr("data-area-position-id-selected", $parent.find("select[data-area-code-selected]:last").val() || "");
    });
    return this;
};

$.fn.nextSelectShow = function (opt, _index, pid) {
    var $this = $(this);
    var data = {};
    var idPosition = opt.requestIds[_index];
    var _idPosition = opt.selectCode[(1 + parseInt(_index))];
    var namePosition = opt.requestNames[(1 + parseInt(_index))];
    data[idPosition] = pid;
    if (++_index === (opt.totalSelect)) {
        return;
    }
    data = $.extend(data, opt.extendsData);
    if ($this !== undefined) {
        $.ajax({
            type: "post",
            dataType: "json",
            data: data,
            url: opt.url,
            success: function (data) {
                var html = blankSelect;
                var areaIds = [];
                $.each(data, function (index, item) {
                    if (isNotNull(item[_idPosition])) {
                        if ($.inArray(item[_idPosition], areaIds) !== -1 && item[_idPosition] !== undefined) {
                            return true;
                        }
                    }
                    html += '<option data-area-code=' + item[_idPosition] + ' value=' + item.id + ' >' + item[namePosition] + '</option>';
                    areaIds.push(item[_idPosition]);
                });
                $this.html(html);
                if (isNotNull($this.attr("data-cascade-val"))) {
                    $this.find("option[data-area-code=" + $this.attr("data-cascade-val") + "]").attr("selected", "selected");
                }
                bootstrapSelectInit($this, opt.frozenSelect);
                var selectArr = $("#" + opt.DivId).find("select:eq(" + _index + ")");
                selectArr.areaClickBlank(opt, selectArr.attr("data-area-index"))
            }
        })
    }
    return this;
};

$.fn.areaClickBlank = function (opt, index) {
    if ($(this).find("option").length === 1) {
        return;
    }
    $(this).removeAttr("data-area-code-selected");
    index = parseInt(index);
    var $parent = $(this).parent().parent();
    if (++index === opt.totalSelect) {
        $parent.attr("data-area-position-id-selected", $parent.find("select[data-area-code-selected]:last").val() || "");
        return;
    }
    var next = $(this).parent().next();
    if (next !== undefined) {
        var $thisSelect = next.find("select");
        $thisSelect.html(blankSelect);
        bootstrapSelectInit($thisSelect, opt.frozenSelect);
        $thisSelect.areaClickBlank(opt, index);
    }
    $parent.attr("data-area-position-id-selected", $parent.find("select[data-area-code-selected]:last").val() || "");
    return this;
};

function outSpace(number) {
    return number-- ? "&nbsp;" + outSpace(number) : "";
}

function bootstrapSelectInit($select, frozenSelect) {
    if (frozenSelect) {
        if ($select.find("option").length === 2) {
            $select.attr("data-area-code-selected", $select.find("option:eq(1)").attr("data-area-code"));
            $select.find("option:eq(1)").attr("selected", "selected");
            $select.removeClass("validate[required]");
            $select.find("option:eq(0)").remove();
            $select.attr("disabled", "true");
            $select.change();
        }
    }
    $select.selectpicker("refresh");
    $select.selectpicker("render");
}

