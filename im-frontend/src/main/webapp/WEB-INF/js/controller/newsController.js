define(['views/newsView','utils'], function (View,utils) {

	var _id;
	
	var bindings = [{
        element: '.news-item',
        event: 'click',
        handler: gotoNewsDetail
    },{
    	element: '.infinite-scroll',
        event: 'infinite',
        handler: pahgeScroll
    },{
    	element: '.pull-to-refresh-content',
        event: 'refresh',
        handler: pageRefresh
    }];
	
	return {
        init: init
    };
    
    function init(query) {
        myApp.showIndicator();
        $$.ajax({
            url: '/auth/home/newsList',
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                View.render({
                    model: data,
                    bindings: bindings
                });
                myApp.hideIndicator();
            }
        });
    }

    function gotoNewsDetail() {
    	_id = $$(this).attr("id");
    	viewNews.loadPage('/pages/news/newsDetail.html?id='+_id);
    }
    
    
    // 加载flag
    var loading = false;
    // 注册'infinite'事件处理函数
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
	        var lastNewsId = $$('.news-item').eq($$('.news-item').length-1).attr('id');
	        $$.ajax({
	            url: '/auth/home/newsList',
	            type: 'GET',
	            dataType: 'json',
	            data: {
	            	lastNewsId: lastNewsId
	            },
	            success: function (data) {
	            	var html = '';
	            	var len = data.list.length;
	                if (len > 0) {
	                	for(var i=0;i<len;i++){
	                		var json = data.list[i];
	                		html += '<div class="card news-item" id="'+json.id+'">';
		                	html += ' <div class="card-content">';
		                	html += '  <p style="padding:20px 20px 0px 20px;">'+json.news+'</p>';
		                	html += ' </div>';
		                	html += ' <div class="card-footer" style="color:#aaa;">';
		                	html += '  '+json.user_nick+' | '+json.create_str+' | 聊天';
		                	html += ' </div>';
		                	html += '</div>';
	                	}
	                	$$('.list-block').append(html);
	                	utils.bindEvents(bindings);
	                } else {
	                	 myApp.detachInfiniteScroll($$('.infinite-scroll'));
	                     $$('.infinite-scroll-preloader').remove();
	                }
	            }
	        });
    	}, 1000);
    }
    
    // 添加'refresh'监听器
    function pageRefresh() {
        // 模拟1s的加载过程
        setTimeout(function () {
	        $$.ajax({
	            url: '/auth/home/newsList',
	            type: 'GET',
	            dataType: 'json',
	            data: {
	            	firstNewsId: $$('.news-item').eq(0).attr('id')
	            },
	            success: function (data) {
	            	var html = '';
	            	var len = data.list.length;
	                if (len > 0) {
	                	for(var i=0;i<len;i++){
	                		var json = data.list[i];
	                		html += '<div class="card news-item" id="'+json.id+'">';
		                	html += ' <div class="card-content">';
		                	html += '  <p style="padding:20px 20px 0px 20px;">'+json.news+'</p>';
		                	html += ' </div>';
		                	html += ' <div class="card-footer" style="color:#aaa;">';
		                	html += '  '+json.user_nick+' | '+json.create_str+' | 聊天';
		                	html += ' </div>';
		                	html += '</div>';
	                	}
	                	$$('.list-block').prepend(html);
	                }
	            }
	        });
            // 加载完毕需要重置
            myApp.pullToRefreshDone();
        }, 10);
    }
    
});
