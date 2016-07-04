define(['views/chatListView','GS'], function (View,GS) {

    function init(query) {
        myApp.showIndicator();
        var chatList = GS.getLocalStorageByKey('chat_list');
        View.render({
            model: chatList
        });
        myApp.hideIndicator();
    }

    return {
        init: init
    };
    
    
    
});
