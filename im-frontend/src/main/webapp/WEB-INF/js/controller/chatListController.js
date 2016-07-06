define(['views/chatListView','GS'], function (View,GS) {

    function init(query) {
        myApp.showIndicator();
        var chatList = GS.getLocalStorageByKey('chat_list');
        if(chatList!=null){
        	View.render({
                model: JSON.parse(chatList)
            });
        }
        
        myApp.hideIndicator();
    }

    return {
        init: init
    };
    
    
    
});
