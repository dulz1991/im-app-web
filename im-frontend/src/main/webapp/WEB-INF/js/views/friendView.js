define(['utils'], function (Utils) {
    function render(params) {
        var template = $$('#friendTemplate').html();
        var compiledTemplate = Template7.compile(template);
        var renderTemplate = compiledTemplate(params.model);

        $$('#friendContent').html(renderTemplate);
        Utils.bindEvents(params.bindings);
    }

    return {
        render: render
    };
});
