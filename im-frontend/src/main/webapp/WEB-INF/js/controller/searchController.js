define(['views/searchView'], function (View) {

	var bindings = [{
		element: '.do-search',
        event: 'click',
        handler: search
	}];

    function init(query) {
        myApp.showIndicator();
        View.render({
            bindings: bindings
        });
        myApp.hideIndicator();
    }

    return {
        init: init
    };
    
    function search(){
    	var usernick = $$('input[name="usernick"]').val();
    	$$.ajax({
            url: '/auth/friend/getFriendByUsernick',
            data: {usernick: usernick},
            type: 'GET',
            dataType: 'json',
            success: function (data) {
            	if (data.errorNo==200) {
            		$$('.searchbar-not-found').hide();
            		$$('.search-friend').show();
            		$$('.search-friend a').attr('href','/pages/friend/friendDetail.html?friendUserId='+data.userProfile.id);
            		$$('.search-friend img').attr('src',data.userProfile.avatar);
            		$$('.search-friend .item-title').text(data.userProfile.usernick);
            		$$('.search-friend .item-text').text(data.userProfile.personalNote);
            	} else {
            		$$('.search-friend').hide();
            		$$('.searchbar-not-found').show();
            	}
            }
        });
    }
    
});
