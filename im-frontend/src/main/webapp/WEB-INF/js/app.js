require.config({
    baseUrl: '/js',
    paths: {
    	Framework7: '/js/libs/framework7.min',
    	router: '/js/router',
    	LS: '/js/localStorage',
    	GS: '/js/globalService'
    },
    shim: {
        Framework7: {
            exports: 'Framework7'
        }
    }
})

require(['Framework7', 'router'], function (Framework7, Router) {
	// Initialize your myApp
	window.myApp = new Framework7({
		
	});

	// Export selectors engine
	window.$$ = window.Dom7;
	
	var device = Framework7.prototype.device;

	// Add views
	window.viewNews = myApp.addView('#view-news',{
		dynamicNavbar: true,
		preloadPreviousPage:false
		
	});
	window.viewChat = myApp.addView('#view-chat', {
	    dynamicNavbar: true,
	    preloadPreviousPage:false
	});
	window.viewFriend = myApp.addView('#view-friend', {
		dynamicNavbar: true,
		preloadPreviousPage:false
	});
	window.viewMine = myApp.addView('#view-mine', {
		dynamicNavbar: true,
		preloadPreviousPage:false
	});
	
	Router.init();
	
	viewNews.reloadPage('/pages/news/news.html');
	/*viewMine.reloadPage('/pages/mine/mineHome.html');*/
	
	$$('.panel-close').on('click', function (e) {
        myApp.closePanel();
    });
});


