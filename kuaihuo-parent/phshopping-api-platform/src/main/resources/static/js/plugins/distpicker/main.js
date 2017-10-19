$(function () {

  'use strict';

  var $distpicker = $('#distpicker');

  $distpicker.distpicker({
      autoSelect: false,
      province: '选择省',
      city: '选择市',
      district: '选择区'
  });
});
