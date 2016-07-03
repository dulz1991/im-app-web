define(['views/userInfoView'], function (View) {

	var bindings = [{
		element: '.sex',
        event: 'click',
        handler: modifySex
	},{
		element: '.hide-screen',
        event: 'click',
        handler: hideScreen
	},{
		element: '.select-sex',
        event: 'click',
        handler: selectSex
	}];

    function init(query) {
    	myApp.showIndicator();
        $$.ajax({
            url: '/auth/mine/index',
            data: {},
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

    return {
        init: init
    };
    
    function modifySex() {
    	myApp.loginScreen();
    }
    
    function hideScreen() {
    	myApp.closeModal();
    }
    
    function selectSex() {
    	var sex = $$('input[name="sex-radio"]:checked').val();
    	$.ajax({  
			type:'post',      
			url:'/auth/mine/update',  
			data:{sex:Number(sex)},  
			cache:false,  
			dataType:'json',  
			success:function(data){  
				if(data.errorNo!=200){
					myApp.alert(data.errorInfo,'提示：');
				} else {
					if(sex==1){
						$$('.sex-content').html('男<img src="/img/icon/boy.png" width="18" />');
					} else {
						$$('.sex-content').html('女<img src="/img/icon/girl.png" width="18" />');	
					}
					myApp.closeModal();
				}
			}  
		}); 
    }
    
});
