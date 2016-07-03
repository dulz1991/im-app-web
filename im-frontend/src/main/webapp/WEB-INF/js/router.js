define(['GS'], function (GS) {

    function init() {
        $$(document).on('pageBeforeInit', function(e) {
            var page = e.detail.page;
            load(page.name, page.query);
        });

        $$('#view-news').on('show', function () {
        	viewNews.reloadPage('/pages/news/news.html');
        });

        $$('#view-chat').on('show', function () {
        	viewChat.reloadPage('/pages/chat/index.html');
        });

        $$('#view-friend').on('show', function () {
            //GS.haLoadPage('/pages/friend/friend.html', viewFriend, false);
        	viewFriend.reloadPage('/pages/friend/friend.html');
        });

        $$('#view-mine').on('show', function () {
        	viewMine.reloadPage('/pages/mine/mineHome.html');
        });
    }

    function load(controllerName, query) {
    	if(!GS.isLogin()){
    		window.location.href="/account/signIn.html";
    	}
        if(!controllerName) { return; }
        if (controllerName.indexOf('smart-select') !== -1) { return; }
        require(['controller/' + controllerName + 'Controller'], function (controller) {
            controller.init(query);
        });
    }

    return {
        init: init,
        load: load
    };
});
