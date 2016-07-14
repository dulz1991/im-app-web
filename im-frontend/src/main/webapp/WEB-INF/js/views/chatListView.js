define(['utils'], function (Utils) {
    function render(params) {
        var template = $$('#chatListTemplate').html();
        var compiledTemplate = Template7.compile(template);
        var renderTemplate = compiledTemplate(params.model);

        $$('#chatListContent').html(renderTemplate);
        Utils.bindEvents(params.bindings);
    }

    return {
        render: render
    };
});
