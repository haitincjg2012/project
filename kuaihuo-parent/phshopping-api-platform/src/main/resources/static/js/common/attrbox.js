/*
 *模块：属性输入框的增加和删除
 *时间：20170512
 *作者：刘弘愿
 */
$(function () {
    var count = 1;
    var num = 0;
    var flag = false;
    var saveflag=false;
    var numArr=[];
    var countNum;
    // 添加属性值
    $('.MainCont').on('click', '.add_inputbox_attribute', function () {
        var html = '<div class="input-box relative left">' +
            '<input type="text" placeholder="" name="" ref="proValue" ide="' + $(this).parents('.mainFrom').prev('.mainFrom').find('input').attr('id') + '" class="validate[maxSize[100]]"/>' +
            '<img src="/static/images/input-del.png" alt="" class="absolute del_inputbox"/>' +
            '</div>';
        $(this).before(html);
    });
    // 添加规格值
    $('.ph_classify').on('click', '.add_inputbox_Specifications', function () {
        saveBtn();
        var html = '<div class="input-box relative left">' +
            '<input type="text" placeholder=""  name="" ide="' + $(this).parent().find('input').attr('ide') + '" class="validate[maxSize[100]] ' + $(this).parent().find('input').attr('class') + '"/>' +
            '<img src="/static/images/input-del.png" alt="" class="absolute del_inputbox1"/>' +
            '</div>';
        $(this).before(html);
    });
    //属性值和规格值删除
    $('.MainCont').on('click', 'img.del_inputbox', function () {
        $(this).parent('.input-box').remove();
    });
    $('.MainCont').on('click', 'img.del_inputbox1', function () {
        saveBtn();
        if ($(this).parent().parent().find('.input-box').length <= 1) {

            layer.alert('至少保留一个输入框', {
                icon: 2,
                title: '警告',
                closeBtn: 0,
                btn: ['确定'],
                btnAlign: 'c'
            });
            saveflag=true;
        } else {
            $(this).parent('.input-box').remove();
        }
    });
    //属性名称和规格名称删除
    $('.MainCont').on('click', 'img.del_inputbox_all', function () {
        $(this).parent('.input-box').parent('.inputbox_big').parent('.mainFrom').parent().remove();
    });
    $('.MainCont').on('click', 'img.del_inputbox_all_sp', function () {
        saveBtn();
        count--;
        if(count<1){
            count=1;
        }
        numArr=[];
        if ($(this).parent('.input-box').parent('.inputbox_big').parent('.mainFrom').parent().parent('.ph_classify').find('.del_inputbox_all_sp').length <= 1) {

            layer.alert('至少保留一个参数项', {
                icon: 2,
                title: '警告',
                closeBtn: 0,
                btn: ['确定'],
                btnAlign: 'c'
            });
            saveflag=true;
        } else {
            $(this).parent('.input-box').parent('.inputbox_big').parent('.mainFrom').parent().remove();
        }
    });
    //添加规格项
    $('.MainCont').on('click', 'input.add_specifications', function () {
        saveBtn();
        count++;
        num++;
        // eval("flag" + num + "=true");
        var html =
            '<div>' +
            '<div class="mainFrom">' +
            '<label class="left">' +
            '<span class="mainLab">规格名称</span>' +
            '</label>' +
            '<div class="inputbox_big">' +
            '<div class="input-box relative left">' +
            '<input type="text" class="validate[required,maxSize[20]]" ref="spnValue" id="' + num + '"/>' +
            '<img src="/static/images/input-del.png" alt="" class="absolute del_inputbox_all_sp"/>' +
            '</div>' +
            '</div>' +
            '</div>' +
            '<div class="mainFrom">' +
            '<label class="left">' +
            '<span class="mainLab">规格值</span>' +
            '</label>' +
            '<div class="inputbox_big">' +
            '<div class="input-box relative left">' +
            '<input type="text" class="validate[required,maxSize[20]] Specification_value' + count + ' Sp_' + num + '" />' +
            '<img src="/static/images/input-del.png" alt="" class="absolute del_inputbox1"/>' +
            '</div>' +
            '<input type="button" value="添加规格值" class="left add_inputbox add_inputbox_Specifications">' +
            '</div>' +
            '</div>' +
            '</div>';
        $('.ph_classify').append(html);
    });
    // 生成按钮
    $('body').on('click', 'input.table_save', function () {
    	 countNum = $('img.del_inputbox_all_sp');
    	Specifications_chk(countNum);
        if(saveflag){
            $('input.save').removeAttr('disabled');
            $('input.save').removeClass('disable').addClass('submit');
        }

    });
    $('.ph_classify input[type="text"]').change(function () {
        saveBtn();
    })
    function saveBtn() {
        $('input.table_save').removeAttr('disabled');
        $('input.table_save').removeClass('disable').addClass('submit');
        $('input.save').attr('disabled',true);
        $('input.save').removeClass('submit').addClass('disable');
    }

    //表格生成方法
    function table_add(num,countNum) {

        var tmpc = [];

        function combine(arr) {
            var r = [];
            (function f(t, a, n) {
                if (n == 0) return r.push(t);
                for (var i = 0; i < a[n - 1].length; i++) {
                    f(t.concat(a[n - 1][i]), a, n - 1);
                }
            })([], arr, arr.length);
            return r;
        }

        var res = combine(num);
        //table tr td
        var str = "";
        var len = countNum.length;  //规格值个数
        for (var i = 0; i < res.length; i++) {  //规格项的循环
            var tmp = "";
            for (var j = 0; j < len; j++) {  //规格值的循环
                tmp += res[i][j] + "-";
            }
            tmp = tmp.substr(0, tmp.length - 1);
            tmp = tmp.split("-").reverse().join("-");
            tmpc.push(tmp);
            $.unique(tmpc);

        }
        for (var k = 0; k < tmpc.length; k++) {
        	var countID=""+k+"one";
            str +=
                '<tr>' +
                '<td  class="td_input">' + tmpc[k] + '<input type="hidden" ref="skus"   name="skuName" value="' + tmpc[k] + '" /></td>' +
                '<td class="td_input"><input type="text" class="table_input validate[required,custom[price], min[0]]" ref="skus"   name="referencePrice" value="" /></td>' +
                '<td class="td_input"><input type="text" class="table_input validate[required,custom[price], min[0]]" ref="skus" name="retailPrice" id="'+countID+'" onblur="setPurchasePrice('+k+',\'' +countID+'\');"   value="" /></td>' +
                '<td class="td_input"><input type="text" class="table_input" ref="skus"  name="purchasePrice"  id="'+k+'"   readonly="readonly"  value="" /></td>' +
                '<td class="td_input"><input type="text" class="table_input validate[required,custom[price], min[0]]" ref="skus"  name="settlementPrice" value="" /></td>' +
                '<td class="td_input"><input type="text" class="table_input validate[required,custom[price], min[0]]" ref="skus"  name="municipalBatchQuantity" value="" /></td>' +
                '<td class="td_input"><input type="text" class="table_input validate[required,custom[price], min[0]]" ref="skus" name="sellerbAtchQuantity" value="" /></td>' +
                '<td class="td_input"><input type="text" class="table_input validate[custom[integer], min[1],max[2147483647]]" ref="skus"  name="saleQuantity" value="" /></td>' +
                '<td class="td_input"><input type="text" class="table_input validate[custom[integer], min[1],max[2147483647]]" ref="skus"name="numberOfPackages" value="" /></td>' +
                '<td class="td_input"><input type="text" class="table_input validate[custom[price], min[0]]"  ref="skus"  name="freight" value="" /></td>' +
                '<td class="td_input"><input type="text" class="table_input validate[required, custom[integer], min[1],max[2147483647]]" ref="skus" name="skuCount" value=""  onblur="compute();" /></td>' +
                '</tr>';

        }
        $('.ph_wareList tbody').html(str);
        numArr = [];
        saveflag=true;
    }

    // 重复规格项校验方法
    function Specifications_chk(countNum) {
        var k_length = $('img.del_inputbox_all_sp').length;
        var sArr = [];
        // 空值和重复项校验
        for (var k = 0; k < k_length; k++) {
            var k_name = $('img.del_inputbox_all_sp').siblings();

            var Specification_val = $('.Sp_' + k + '');


            for (var i = 0; i < Specification_val.length; i++) {
                if (Specification_val[i].value != ""&&k_name[k].value!=''&&Specification_val[i]!=k_name[k]) {
                    sArr.push('规格' + k_name[k].value + '中===>规格值:' + Specification_val[i].value);
                    flag=true;
                }
                else {
                    layer.alert('有空值输入项,请删除空值项或重新输入', {
                        icon: 2,
                        title: '警告',
                        closeBtn: 0,
                        btn: ['确定'],
                        btnAlign: 'c'
                    });
                    flag = false;
                    saveflag=false;
                    numArr = [];
                    return;
                }
            }

// 数组的长度

        }
        sArr = sArr.sort();
        var len = sArr.length;
        var inputArr=$('.ph_classify').find('input[type="text"]');
        inputArr=inputArr.sort();
        var len_input=inputArr.length;
// 外面的for是遍历每一项数组元素（总共是12项）
        for (var i = 0; i < len; i++) {
// 里面的for是用来遍历比较数组元素（换句话说，就是找到相同的数组）
            for (var j = i + 1; j < len; j++) {
// 判断是否相同
                if (sArr[i] == sArr[j]) {
// 删除相同的数组元素
                    layer.alert(sArr[j] + ':是重复的输入项,请重新输入', {
                        icon: 2,
                        title: '警告',
                        closeBtn: 0,
                        btn: ['确定'],
                        btnAlign: 'c'
                    });
                    flag = false;
                    numArr = [];
                    saveflag=false;
                    return;
                }
                else {
                    flag = true;

                }
            }

        }
        for (var i = 0; i < len_input; i++) {
// 里面的for是用来遍历比较数组元素（换句话说，就是找到相同的数组）
            for (var j = i + 1; j < len_input; j++) {
// 判断是否相同
                if (inputArr[i].value == inputArr[j].value) {
// 删除相同的数组元素
                    layer.alert('有规格名称和规格值内容重复,请重新输入', {
                        icon: 2,
                        title: '警告',
                        closeBtn: 0,
                        btn: ['确定'],
                        btnAlign: 'c'
                    });
                    flag = false;
                    numArr = [];
                    saveflag=false;
                    return;
                }
                else {
                    flag = true;

                }
            }

        }
        //校验通过创建表格
        if (flag) {
            //count为规格对象的总个数
            for (var j = 0; j < countNum.length; j++) {
                numArr[j] = [];
                // 定义一个空数组
                var txt = $('img.del_inputbox_all_sp').eq(j).parent().parent().parent().siblings().find('input[type="text"]');
                // 获取所有文本框
                for (var i = 0; i < txt.length; i++) {
                    if(txt.eq(i).val()!=''){
                        numArr[j].push(txt.eq(i).val()); // 将文本框的值添加到数组中
                    }
                    else{
                        numArr=[];
                        return;
                    }
                }
            }
            console.log(numArr);
            table_add(numArr,countNum);
            //重新禁用按钮
            $('input.table_save').attr('disabled','disabled');
            $('input.table_save').removeClass('submit').addClass('disable');
        }
    }


});