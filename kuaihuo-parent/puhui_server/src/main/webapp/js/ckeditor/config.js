/**
 * @license Copyright (c) 2003-2015, CKSource - Frederico Knabben. All rights
 *          reserved. For licensing, see LICENSE.md or
 *          http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function(config) {
	/*// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	//config.filebrowserUploadUrl = "http://localhost:8082/alqsoft-easyui/view/merchantAttachment/import-merchantAttachment-ckeditor.do";
	//var pathName = window.document.location.pathname;
	// 获取带"/"的项目名，如：/uimcardprj
	//var projectName = pathName
			//.substring(0, pathName.substr(1).indexOf('/') + 1);
	//config.filebrowserImageUploadUrl = "http://localhost:8082/alqsoft-easyui/view/merchantAttachment/import-merchantAttachment-ckeditor.do"; // 固定路径
	config.removeDialogTabs = 'image:advanced;image:Upload;image:Link';
	
	config.height = 'auto';
	config.width = 'auto';
	config.removeButtons = 'Flash';
    config.enterMode = CKEDITOR.ENTER_BR;
    config.shiftEnterMode = CKEDITOR.ENTER_BR; */
	
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	config.filebrowserUploadUrl = "/pc/after/adshome/detailpicture";

	var pathName = window.document.location.pathname;
	// 获取带"/"的项目名，如：/uimcardprj
	var projectName = pathName
			.substring(0, pathName.substr(1).indexOf('/') + 1);
	config.filebrowserImageUploadUrl = projectName + '/pc/after/adshome/detailpicture'; // 固定路径
	config.height = 'auto';
	config.width = 'auto';
	config.removeButtons = 'Flash';

};

