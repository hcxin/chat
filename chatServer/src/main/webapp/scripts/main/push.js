$(document).ready(function(){
	//start ko
	var ViewModel = function() {
		var self = this;
		self.userList = ko.observableArray();
		self.selectUserId = ko.observable();
		self.singlePushWindow = function(vm, evt){
			$("#singlePushModal").modal();
			self.selectUserId = vm.userName;
		};
	 
	};
	viewModel = new ViewModel();
	ko.applyBindings(viewModel);
	//end ko
    $('#userListBtn').bind('click', function() {
        var self = this;
        url = "/chatServer/chat/getUserList";

        var ajaxConfig = {
                url : url,
                data : {},
                type : 'post',
                contentType : 'application/json',
                dataType : 'json',
                success:function(result){
                	viewModel.userList(result);
                },
                error:function(result){
                //TODO
                }
        };
        $.ajax(ajaxConfig);
    });
    
    $('#pushBtn').bind('click', function() {
        $("#warnModal").modal("hide");
        url = "/chatServer/chat/pushMessage";
        var content = $('#pushContent').val();
        if (!content){
        	 alert("消息不能为空!");
        	 return;
        }
        
        var param = {
        		content:content
        }
        var data = JSON.stringify(param || {});
        var ajaxConfig = {
                url : url,
                data : data,
                type : 'post',
                contentType : 'application/json',
                dataType : 'json',
                success:function(result){
                alert("推送成功! 已推送给"+result+"人");
                },
                error:function(result){
                }
        };
        $.ajax(ajaxConfig);
    });
    
    $('#singlePushBtn').bind('click', function() {
        $("#singlePushModal").modal("hide");
        url = "/chatServer/chat/pushMessageToSingle";
        var content = $('#singlePushContent').val();
        if (!content){
        	 alert("消息不能为空!");
        	 return;
        }
        
        var param = {
        		content:content,
        		userId:viewModel.selectUserId
        }
        var data = JSON.stringify(param || {});
        var ajaxConfig = {
                url : url,
                data : data,
                type : 'post',
                contentType : 'application/json',
                dataType : 'json',
                success:function(result){
                alert("推送成功! 已推送给 "+userId);
                },
                error:function(result){
                }
        };
        $.ajax(ajaxConfig);
    });

});