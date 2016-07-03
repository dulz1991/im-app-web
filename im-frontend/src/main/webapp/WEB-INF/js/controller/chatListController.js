define(['views/chatListView','LS'], function (View,LS) {

    function init(query) {
        myApp.showIndicator();
        var chatList = getLocalStorageByKey('chat_list');
        View.render({
            model: chatList
        });
        myApp.hideIndicator();
    }

    return {
        init: init
    };
    
    
    
});
