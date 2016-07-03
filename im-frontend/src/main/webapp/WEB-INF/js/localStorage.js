//删除h5本地存储
var localStorage = window.localStorage;

function clearLocalStorage(key) {
	if(localStorage){
    	if(key==''||key==null||key==undefined){
    		localStorage.clear();
    		alert("删除成功");
    	} else {
    		localStorage.removeItem(key);
    		alert("删除成功");
    	}
    } else {
    	alert('This browser does NOT support localStorage');
    }
}

function getLocalStorageByKey() {
	return localStorage.getItem("chat_list");
}

function updateLocalStorageChatList(value) {
	var flag = false;
	var jsonArr;
	//获取本地存储chat_list
	var jsonStr =  JSON.parse(getLocalStorageByKey("chat_list"));
	//是否存在数据
	if (jsonStr != null) {
		$.each(jsonStr,function(index) {
			if (jsonStr[index].userId == value.userId) {
				jsonStr[index].message = value.message;
				jsonStr[index]._createTimeStr = new Date().format("yyyy-MM-dd HH:mm:ss");
				flag = true;
				return false;
			}
		});
		if (!flag) {
			jsonStr.push(value);
			localStorage.setItem("chat_list", JSON.stringify(jsonStr));
		}
	} else {
		jsonStr = [];
		jsonStr.push(value);
		localStorage.setItem("chat_list", JSON.stringify(jsonStr));
	}
	
}

