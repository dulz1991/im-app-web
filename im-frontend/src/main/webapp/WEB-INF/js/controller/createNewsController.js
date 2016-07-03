define(['views/createNewsView'], function (View) {

	var bindings = [{
		element: '.submit-news',
        event: 'click',
        handler: submitNews
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
    
    function submitNews() {
    	var news = $$('textarea[name="news"]').val();
		if(news=="" || news == null || news == undefined){
			myApp.alert('内容不能为空','提示：');
		} else {
			$.ajax({  
				type:'post',      
				url:'/auth/mine/doCreateNews',  
				data:{content : news},  
				cache:false,  
				dataType:'json',  
				success:function(data){  
					if(data.errorNo!=200){
						myApp.alert(data.errorInfo,'提示：');
					} else {
						myApp.modal({
			                title: ' ',
			                text: '发布成功',
			                buttons: [
			                    /*{
			                        text: '查看发现',
			                        onClick: function () {
			                            viewNews.loadPage('/pages/news/news.html');
			                            viewNews.router.load('/pages/news/news.html')
			                        }
			                    },*/
			                    {
			                        text: '确定',
			                        onClick: function () {
			                            viewMine.loadPage('/pages/mine/mineHome.html');
			                        }
			                    }
			                ]
			            });
					}
				}  
			});
		}
    }
    
});
