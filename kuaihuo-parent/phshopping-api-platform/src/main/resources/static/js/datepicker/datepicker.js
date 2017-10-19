// JavaScript Document
$(function() {
    // 日期控件区间
    $('.dayBox .start_rise').datepicker({
        'format': 'yyyy-mm-dd',
        'autoclose': true
    });
    $('.dayBox').datepair();
});