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
	},{
		element: '.personal-note',
        event: 'click',
        handler: modifyPersonalNote
	},{
		element: '.submit-personal-note',
        event: 'click',
        handler: doModifyPersonalNote
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
    
    function showScreen() {
    	myApp.loginScreen();
    }
    
    function hideScreen() {
    	myApp.closeModal();
    }
    
    function modifySex(){
    	$$('.display').hide();
    	$$('.display-sex').show();
    	showScreen();
    }
    
    function modifyPersonalNote(){
    	$$('.display').hide();
    	$$('.display-personal-note').show();
    	var curNote = $$(this).find('.mine-personal-note').text();
    	$$('textarea[name="personalNote"]').val(curNote);
    	showScreen();
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
					hideScreen();
				}
			}  
		}); 
    }
    
    function doModifyPersonalNote(){
    	var personalNote = $$('textarea[name="personalNote"]').val();
    	$.ajax({  
			type:'post',      
			url:'/auth/mine/update',  
			data:{personalNote:personalNote},  
			cache:false,  
			dataType:'json',  
			success:function(data){  
				if(data.errorNo!=200){
					myApp.alert(data.errorInfo,'提示：');
				} else {
					$$('.mine-personal-note').text(personalNote);
					hideScreen();
				}
			}  
		}); 
    }
    
});
