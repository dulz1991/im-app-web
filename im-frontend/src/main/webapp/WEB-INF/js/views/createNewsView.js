define(['utils'], function (Utils) {
    function render(params) {
        var template = $$('#createNewsTemplate').html();
        var compiledTemplate = Template7.compile(template);
        var renderTemplate = compiledTemplate(params.model);

        $$('#createNewsContent').html(renderTemplate);
        Utils.bindEvents(params.bindings);
    }

    return {
        render: render
    };
});
