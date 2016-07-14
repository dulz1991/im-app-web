define(['views/newsDetailView','utils'], function (View,utils) {

	var _userId;
	var _newsId;
	var _replyToUserId="";
	
	var bindings = [{
        element: '.chat',
        event: 'click',
        handler: gotoChat
    },{
    	element: '.comment',
        event: 'click',
        handler: gotoComment
    },{
    	element: '.complete-input',
        event: 'click',
        handler: sumbitComment
    },{
    	element: '.infinite-scroll-reply',
        event: 'infinite',
        handler: pahgeScroll
    },{
    	element: '.view-user',
        event: 'click',
        handler: viewUser
    },{
    	element: '.reply-user',
        event: 'click',
        handler: replyUser
    }];

    function init(query) {
        myApp.showIndicator();
        _newsId = query.id;
        $$.ajax({
            url: '/auth/home/newsDetail',
            data: {id: _newsId},
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                View.render({
                    model: data,
                    bindings: bindings
                });
                myApp.hideIndicator();
                /*myApp.hideToolbar('.toolbar');*/
            }
        });
    }

    return {
        init: init
    };
    
    function gotoChat() {
    	_userId = $$('input[name="hidden_userid"]').val();
    	/*viewChat.loadPage("/pages/chat/chat.html?friendUserId="+_userId);*/
    }
    
    function gotoComment() {
    	$$('input[name="comment"]').attr('placeholder','评论：');
    	_replyToUserId = $$(this).attr('replyuserid');
    	myApp.pickerModal('.picker-info');
    }
    
    function sumbitComment() {
    	_userId = $$('input[name="hidden_userid"]').val();
    	var replyToUserId = _userId;
    	if(_replyToUserId==""){
    		_replyToUserId = _userId;
    	}
    	var replyContent = $$('input[name="comment"]').val();
    	if(replyContent==""){
    		return false;
    	}
    	$$.ajax({
            url: '/auth/home/sumbitComment',
            data: {
            		newsId: _newsId,
            		replyToUserId: _replyToUserId,
            		replyContent: replyContent
            	},
            type: 'POST',
            dataType: 'json',
            success: function (data) {
            	if (data.errorNo == 200) {
            		var html = '';
            		html += '<div class="card-content-inner comment-item" replyid='+data.reply.id+' style="font-size:16px;padding:10px;">';
            		html += data.reply.replyUserNick +' | '+ data.reply.reply_content;
                	html += '</div>';
                	$$('.comment-list').prepend(html);
                	$$('input[name="comment"]').val("");
                	utils.bindEvents(bindings);
            	} else {
            		
            	}
            	myApp.closeModal('.picker-info');
            }
        });
    }
    
    // 加载flag
    var loading = false;
    function pahgeScroll() {
    	// 如果正在加载，则退出
    	if (loading) return;
    	// 设置flag
    	loading = true;
    	// 模拟1s的加载过程
    	$$('.infinite-scroll-preloader').show();
    	setTimeout(function () {
	        // 重置加载flag
	        loading = false;
	        //加载数据
	        var lastReplyId = $$('.comment-tetm').eq($$('.comment-tetm').length-1).attr('replyid');
	        $$.ajax({
	            url: '/auth/home/getcomments',
	            type: 'GET',
	            dataType: 'json',
	            data: {
	            	lastReplyId: lastReplyId,
	            	newsId : _newsId
	            },
	            success: function (data) {
	            	var html = '';
	            	var len = data.list.length;
	                if (len > 0) {
	                	for(var i=0;i<len;i++){
	                		var json = data.list[i];
	                		html += '<div class="card-footer comment-tetm" replyid='+json.id+'>';
	                		html += json.replyUserNick +' | '+ json.reply_content;
		                	html += '</div>';
	                	}
	                	$$('.comment-list').append(html);
	                } else {
	                	 myApp.detachInfiniteScroll($$('.infinite-scroll-reply'));
	                     $$('.infinite-scroll-preloader').remove();
	                }
	            }
	        });
    	}, 1000);
    }
    
    function viewUser(){
    	_userId = $$('input[name="hidden_userid"]').val();
    	viewFriend.router.loadPage("/pages/friend/friendDetail.html?friendUserId="+_userId);
    	/*viewChat.reloadPage("/pages/friend/friendDetail.html?friendUserId="+_userId);*/
    }
    
    function replyUser(){
    	var usernick = $$(this).prev('span').text();
    	_replyToUserId = $$(this).attr('replyuserid');
    	$$('input[name="comment"]').attr('placeholder','回复'+usernick+':');
    	myApp.pickerModal('.picker-info');
    }
    
});
