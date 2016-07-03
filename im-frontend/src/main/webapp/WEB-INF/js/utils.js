define([], function () {

    /**
     * Bind DOM event to some handler function in controller
     * @param  {Array} bindings
     */
    function bindEvents(bindings) {
        if ($$.isArray(bindings) && bindings.length > 0) {
            for (var i in bindings) {
                if (bindings[i].live) {
                    $$('body').on(bindings[i].event, bindings[i].element, bindings[i].handler);
                } else {
                    $$(bindings[i].element).on(bindings[i].event, bindings[i].handler);
                }
            }
        }
    }

    /**
     * Set the position of buttons, which queried by selector, dynamic
     * @param {String} selector
     */
    function setButtonPosition(selector, binded) {
        var pageContent = $$(selector).parents('.page-content');
        if (isScroll(pageContent[0])) {
            $$(selector).removeClass('fixed-bottom');
        } else {
            $$(selector).addClass('fixed-bottom');
        }

        if (binded) return;

        // When window resizing(such as keyboard popup), button need to be reposition
        $$(window).on('resize', function () {
            setButtonPosition.call(this, selector, true);
        });
    }

    /**
     * Detect whether the element has scrollbar
     * @param  {HTMLElement}  elem
     * @return {Boolean}      true: has scrollbar; false: hasn't
     */
    function isScroll(elem) {
        return elem.scrollHeight > elem.clientHeight;
    }


    /**
     * tipPopup
     */

    function tipPopup (head, sub, time) {
        $$('body').append('<div class="tipPopup"><h4 class="pop-head">'+ head + '</h4><span class="pop-sub">'+ sub +'</span></div>');
        setTimeout(function () {
            $$('.tipPopup').remove();
        }, time);
    }

    return {
        bindEvents: bindEvents,
        setButtonPosition: setButtonPosition,
        tipPopup: tipPopup
    };
});
