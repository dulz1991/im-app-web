// Initialize your app
var myApp = new Framework7();

// Export selectors engine
var $$ = Dom7;

// Add views
var view1 = myApp.addView('#view-1',{
	dynamicNavbar: true	
});
var view2 = myApp.addView('#view-2',{
    dynamicNavbar: true
});
var view3 = myApp.addView('#view-3',{
	dynamicNavbar: true
});
var view4 = myApp.addView('#view-4',{
	dynamicNavbar: true
});
var view5 = myApp.addView('#view-5',{
	dynamicNavbar: true
});


$$('#view-2').on('show', function () {
    view2.reloadPage('page/shopping.html');
});
$$('#view-3').on('show', function () {
	view3.reloadPage('page/community.html');
});
$$('#view-4').on('show', function () {
	view4.reloadPage('page/myTaobao.html');
});
$$('#view-5').on('show', function () {
	view5.reloadPage('page/token.html');
});

view1.loadPage('page/home.html');

$$(document).on('pageInit', '.page[data-page="home"]', function (e) {
	var mySlider = myApp.swiper('.swiper-container', {
        pagination:'.swiper-pagination',
        autoplay: 1000,
        speed: 2000,
        loop: true,
        autoplayDisableOnInteraction: false
		});
}) 
