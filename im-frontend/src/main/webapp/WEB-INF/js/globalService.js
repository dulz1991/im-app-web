define([], function () {
    init();
    var ls = window.localStorage;
   
    function init() {
    }

    function getCurrentUser() {
    	var curentUser = null;
    	$.ajax({
            url: '/account/getCurrentUser',
            type: 'GET',
            dataType: 'json',
            async: false,
            success: function (data) {
            	if (data.errorNo==200) {
            		curentUser = data.user;
            	}
            }
        });
    	return curentUser;
    }
    
    function getCurrentUserId() {
    	var curentUser = getCurrentUser();
    	if(curentUser!=null || curentUser!=undefined){
    		return curentUser.id;
    	}
    	return null;
    }

    function isLogin() {
    	var currentUserId = getCurrentUserId(); 
    	if(currentUserId != null || currentUserId != undefined){
    		return true;
    	}else{
    		return false;
    	}
    }

    function logout() {
        haApp.confirm('您确定要退出登录吗？', function () {
            var currentPage = $$('.page-on-center').data('page');
            removeCurrentUser();
            haApp.closeModal();
            // haApp.closePanel();
            if (currentPage === 'video') {
                clearTimeout(window.videoQueryTimer);
                console.log('quit video query successful');
            }
            mainView.loadPage(startPage);
        });
    }

    function checkUpdate() {
        haApp.modal({
            title: '当前版本',
            text: window.appParams.version,
            buttons: [{
                text: '检测更新',
                onClick: function () {
                    haApp.alert('您当前的版本已经是最新');
                }
            }, {
                text: '返回'
            }]
        });
    }


    return {
        isLogin: isLogin,
        logout: logout,
        checkUpdate: checkUpdate,
        getCurrentUserId: getCurrentUserId,
        getLocalStorageByKey: getLocalStorageByKey,
        setLocalStorage: setLocalStorage,
        clearLocalStorage: clearLocalStorage,
        updateLocalStorageChatList: updateLocalStorageChatList,
        refreshToolbar: refreshToolbar,
        clearChatStatus: clearChatStatus
    };
    
    function clearLocalStorage(key) {
    	if(ls){
        	if(key==''||key==null||key==undefined){
        		ls.clear();
        		myApp.alert('删除成功','提示');
        	} else {
        		ls.removeItem(key);
        		myApp.alert('删除成功','提示');
        	}
        } else {
        	alert('This browser does NOT support localStorage');
        }
    }

    function getLocalStorageByKey(key) {
    	return ls.getItem(key);
    }

    function setLocalStorage(key, value) {
    	ls.setItem(key,value);
    }

    function updateLocalStorageChatList(value) {
    	var flag = false;
    	//获取本地存储chat_list
    	var jsonObj =  JSON.parse(getLocalStorageByKey("chat_list"));
    	//是否存在数据
    	if (jsonObj) {
    		$$.each(jsonObj.chatList, function(index, json) {
    			if (json.sendUserId == value.sendUserId) {
    				json.message = value.message;
    				json._createTimeStr = new Date().format("yyyy-MM-dd HH:mm:ss");
    				json.hasNew = true;
    				if($$('.item-title-row_'+value.sendUserId).text().indexOf('new')<0){
    					$$('.item-title-row_'+value.sendUserId).append('<div class="item-title item-title_'+value.sendUserId+'"><span class="badge bg-red">new</span></div>');
    				}
    				$$('.item-text_'+value.sendUserId).text(value.message);
    				flag = true;
    				localStorage.setItem("chat_list", JSON.stringify(jsonObj));
    				return;
    			}
    		});
    		if (!flag) {
    			value.hasNew = true;
    			jsonObj.chatList.push(value);
    			localStorage.setItem("chat_list", JSON.stringify(jsonObj));
    		}
    	} else {
    		jsonObj = {"chatList":[]};
    		jsonObj.chatList.push(value);
    		localStorage.setItem("chat_list", JSON.stringify(jsonObj));
    	}
    }
    
    function clearChatStatus(friendUserId) {
    	var jsonObj =  JSON.parse(getLocalStorageByKey("chat_list"));
    	if (jsonObj) {
    		$$.each(jsonObj.chatList, function(index, json) {
    			if (json.sendUserId == friendUserId) {
    				json.hasNew = false;
    				$$('.item-title_'+friendUserId).remove();
    				localStorage.setItem("chat_list", JSON.stringify(jsonObj));
    				return false;
    			}
    		});
    	}
    }
    
    function refreshToolbar() {
    	var jsonObj =  JSON.parse(getLocalStorageByKey("chat_list"));
		var len = 0;
		if (jsonObj) {
    		$$.each(jsonObj.chatList, function(index, json) {
    			if (json.hasNew) {
    				len++;
    			}
    		});
    		if(len==0){
    			$$('.tab-link i.icon-chat').html('');
    		}else{
    			$$('.tab-link i.icon-chat').html('<span class="badge bg-red">'+len+'</span>');	
    		}
    	} else {
    		$$('.tab-link i.icon-chat').html('');
    	}
    }
    
});
