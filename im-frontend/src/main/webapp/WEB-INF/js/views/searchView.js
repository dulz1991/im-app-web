define(['utils'], function (Utils) {
    function render(params) {
/*        var template = $$('#searchTemplate').html();
        var compiledTemplate = Template7.compile(template);
        var renderTemplate = compiledTemplate();

        $$('#searchContent').html(renderTemplate);*/
        Utils.bindEvents(params.bindings);
    }

    return {
        render: render
    };
});
