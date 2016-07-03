define(['utils'], function (Utils) {
    function render(params) {
        var template = $$('#chatRoomTemplate').html();
        var compiledTemplate = Template7.compile(template);
        var renderTemplate = compiledTemplate(params);

        $$('#chatRoomContent').html(renderTemplate);
        Utils.bindEvents(params.bindings);
    }

    return {
        render: render
    };
});
