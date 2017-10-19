;(function ($) {
  $.fn.spinner = function (opts) {
	var defa = {
		Price:0,
		keyClass:null,
		SurplusClass:null,
		ratio:1.1,
		CheckClass:null
	};
    return this.each(function () {
      var defaults = {value:1, min:1};
      var options = $.extend(defaults, opts);
      var keyCodes = {up:38, down:40};
      var container = $('<div></div>');
      container.addClass('ph_spinnerBox');
      var textField = $(this).addClass('value').attr('maxlength', '4').val(options.value)
        .bind('keyup paste change', function (e) {
          var field = $(this)
          if (e.keyCode == keyCodes.up) changeValue(1);
          else if (e.keyCode == keyCodes.down) changeValue(-1);
          else if (getValue(field) != container.data('lastValidValue')) validateAndTrigger(field)
        });
      textField.wrap(container);

      var increaseButton = $('<button class="increase">+</button>').click(function () {
	  	changeValue(1);
	    setTotal();
	  });
      var decreaseButton = $('<button class="decrease">-</button>').click(function () {
		changeValue(-1);
		setTotal();
	  });

      validate(textField);
      container.data('lastValidValue', options.value);
      textField.before(decreaseButton);
      textField.after(increaseButton);

      function changeValue(delta) {
        textField.val(getValue() + delta);
        validateAndTrigger(textField)
      }

      function validateAndTrigger(field) {
        clearTimeout(container.data('timeout'));
        var value = validate(field);
        if (!isInvalid(value)) {
          textField.trigger('update', [field, value])
        }
      }

      function validate(field) {
        var value = getValue();
        if (value <= options.min) decreaseButton.attr('disabled', 'disabled');
        else decreaseButton.removeAttr('disabled');
        field.toggleClass('invalid', isInvalid(value)).toggleClass('passive', value === 1);

        if (isInvalid(value)) {
          var timeout = setTimeout(function () {
            textField.val(container.data('lastValidValue'));
            validate(field)
          }, 500);
          container.data('timeout', timeout)
		  
        } else {
          container.data('lastValidValue', value)
        }
        return value
      }

      function isInvalid(value) { return isNaN(+value) || value < options.min; }

      function getValue(field) {
        field = field || textField;
        return parseInt(field.val() || 1, 10)
      }
	  
	  function setTotal(){
		var opt = $.extend(defa, options);
	  	$(opt.keyClass).html((parseInt(getValue())*opt.Price).toFixed(2));
		var Br=$(opt.keyClass).html();
		var Ht=$(opt.SurplusClass).html();
		$(opt.CheckClass).on('click',function(){
			if($(this).is(":checked")){
				$(opt.keyClass).html((Br-(Ht*opt.ratio)).toFixed(2));
			}else{
				$(opt.keyClass).html(Br);
			}
		})
	  }
	  setTotal();
    })
  }
})(jQuery);