define(['utils'], function (Utils) {
    function render(params) {
        var template = $$('#friendDetailTemplate').html();
        var compiledTemplate = Template7.compile(template);
        var renderTemplate = compiledTemplate(params.model);

        $$('#friendDetailContent').html(renderTemplate);
        Utils.bindEvents(params.bindings);
    }

    return {
        render: render
    };
});
