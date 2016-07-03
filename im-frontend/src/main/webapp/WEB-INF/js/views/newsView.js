define(['utils'], function (Utils) {
    function render(params) {
        var template = $$('#newsTemplate').html();
        var compiledTemplate = Template7.compile(template);
        var renderTemplate = compiledTemplate(params.model);

        $$('#newsContent').html(renderTemplate);
        Utils.bindEvents(params.bindings);
    }

    function replaceHeader(title) {
        $$('.newstitle').each(function () {
            $$(this).text($$(this).text().replace(/\{\{header\}\}/g,title));
        });
    }


    return {
        render: render,
        replaceHeader: replaceHeader
    };
});
