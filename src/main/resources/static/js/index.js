$(function() {
	// 下拉菜单
	$('#KWHproduct')
			.on(
					'hidden.bs.collapse',
					function() {
						$('.KWHleftTitle')
								.css('background',
										'#f8f9fb url(img/KWHup.png) no-repeat right 10% center')
					});
	$('#KWHproduct')
			.on(
					'shown.bs.collapse',
					function() {
						$('.KWHleftTitle')
								.css('background',
										'#f8f9fb url(img/KWHdown.png) no-repeat right 10% center')
					});
	$('#KWHuser')
			.on(
					'hidden.bs.collapse',
					function() {
						$('.KWHrightTitle')
								.css('background',
										'#f8f9fb url(img/KWHup.png) no-repeat right 10% center')
					});
	$('#KWHuser')
			.on(
					'shown.bs.collapse',
					function() {
						$('.KWHrightTitle')
								.css('background',
										'#f8f9fb url(img/KWHdown.png) no-repeat right 10% center')
					});

	var oBodyHeight = $('body').height();
	$('.KWHmainLeft').css('height', oBodyHeight + 200);
	// 获取右侧宽度
	getRightWidth();
	// 改变左侧菜单
	changeLeftMen();
	$(window).resize(function() {
		changeLeftMen();
	});

	// 点击左侧小图标
	$('.KWHcontentIcon li').click(function() {
		$('.KWHmainLeft').css('width', '200px');
		$('.KWHleftMenu').show();
		$('.KWHcontentIcon').hide();
		getRightWidth();
	})
	// 划过左侧小图标
	$('.KWHcontentIcon li').hover(function() {
		$(this).find('.KWHiconLeft').stop().animate({
			'width' : '100px',
			'opacity' : '1'
		});
	}, function() {
		$(this).find('.KWHiconLeft').stop().animate({
			'width' : '0px',
			'opacity' : '0'
		});
	})

	$("#userDataTable").bootstrapTable({ // 对应table标签的id
	      url: "job/getElasticJobConfigList", // 获取表格数据的url
	      cache: false, // 设置为 false 禁用 AJAX 数据缓存， 默认为true
	      striped: true,  //表格显示条纹，默认为false
	      pagination: true, // 在表格底部显示分页组件，默认false
	      pageList: [10, 20], // 设置页面可以显示的数据条数
	      pageSize: 10, // 页面数据条数
	      pageNumber: 1, // 首页页码
	      sidePagination: 'server', // 设置为服务器端分页
	      queryParams: function (params) { // 请求服务器数据时发送的参数，可以在这里添加额外的查询参数，返回false则终止请求

	          return {
	              size: params.limit, // 每页要显示的数据条数
	              startIndex: params.offset, // 每页显示数据的开始行号
	              jobName:$("#jobNames").val()
	          }
	      },
	      columns: [
	    	  {
	              field: 'id', // 返回json数据中的name
	              title: '编号', // 表格表头显示文字
	              align: 'center', // 左右居中
	              valign: 'middle', // 上下居中
	              formatter : function(value, row, index) {
						return index + 1;
				  }
	            	  
	          }, {
	              field: 'jobName',
	              title: '任务名称',
	              align: 'center',
	              valign: 'middle'
	          }, {
	              field: 'cron',
	              title: '表达式',
	              align: 'center',
	              valign: 'middle'
	          }, {
	              field: 'shardingTotalCount',
	              title: '分片数',
	              align: 'center',
	              valign: 'middle'
	          }, {
	              field: 'shardingItemParameters',
	              title: '分片参数',
	              align: 'center',
	              valign: 'middle'
	          }, {
	              field: 'jobParameter',
	              title: '任务参数',
	              align: 'center',
	              valign: 'middle'
	          }, {
	              field: 'failover',
	              title: '任务失效转移',
	              align: 'center',
	              valign: 'middle'
	          }, {
	              field: 'misfire',
	              title: '错过任务重新执行',
	              align: 'center',
	              valign: 'middle'
	          }, {
	              field: 'jobClass',
	              title: '任务类',
	              align: 'center',
	              valign: 'middle'
	          }, {
	              field: 'description',
	              title: '任务描述',
	              align: 'center',
	              valign: 'middle'
	          }, {
	              field: 'streamingProcess',
	              title: '流式处理',
	              align: 'center',
	              valign: 'middle'
	          }, {
	              title: "操作",
	              align: 'center',
	              valign: 'middle',
	              width: 160, // 定义列的宽度，单位为像素px
	              formatter: function (value, row, index) {
	                  return '<button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#myModal" onclick="update(\'' + row.id + '\')">修改</button>'
	                  + '<button class="btn btn-warning btn-sm" onclick="deleteData(\'' + row.id + '\')">删除</button>';
	              }
	          }
	      ],
	      onLoadSuccess: function(){  //加载成功时执行
	            console.info("加载成功");
	      },
	      onLoadError: function(){  //加载失败时执行
	            console.info("加载数据失败");
	      }

	})

	// 分页
	$('#KWHcontrollLeftMenu').click(function() {
		var KWHmainLeftWIdth = $('.KWHmainLeft').css('width');
		if (KWHmainLeftWIdth == (200 + 'px')) {
			$('.KWHmainLeft').css('width', '100px');
			$('.KWHleftMenu').hide();
			$('.KWHcontentIcon').show();
		} else {
			$('.KWHmainLeft').css('width', '200px');
			$('.KWHleftMenu').show();
			$('.KWHcontentIcon').hide();
		}
		getRightWidth();
	})

	function getRightWidth() {
		var oBodyWidth = $('body').width();
		var KWHmainLeftWidth = $('.KWHmainLeft').width();
		var KWHmainRight = oBodyWidth - KWHmainLeftWidth - 18;
		$('.KWHmainRight').css('width', KWHmainRight);
	}

	function changeLeftMen() {
		var windowWidth = $(window).width();
		if (windowWidth <= 768 && windowWidth >= 320) {
			$('.KWHmainLeft').css('width', '100px');
			$('.KWHleftMenu').hide();
			$('.KWHcontentIcon').show();
		} else {
			$('.KWHmainLeft').css('width', '200px');
			$('.KWHleftMenu').show();
			$('.KWHcontentIcon').hide();

		}
		getRightWidth();
	}

	var trigger = function(jobGroup, jobName) {
		alert(jobName);
	}

})
function logout() {
	bootbox.confirm("<b><big><big>确定退出？</></></>", function(flag) {  
        if (flag) {  
        	window.location.href = "/logout?a=" + new Date().getTime();
        }  
      });

}

function operate() {
	bootbox.confirm("<b><big><big>确定执行操作？</></></>", function(flag) {  
        if (flag) {  
        	var count = $("#shardingTotalCount").val();
        	if (count < 1) {
        		bootbox.alert("分片数必须大于1");
        		return;
        	}
        	var jobName = $("#jobName").val();
        	if(!jobName){
        		bootbox.alert("请补全任务名称");
        		return;
        	}
        	var cron = $("#cron").val();
        	if(!cron){
        		bootbox.alert("请补全表达式");
        		return;
        	}
        	var jobClass = $("#jobClass").val();
        	if(!jobClass){
        		bootbox.alert("请补全任务类");
        		return;
        	}
        	var description = $("#description").val();
        	if(!description){
        		bootbox.alert("请补全任务描述");
        		return;
        	}
        	var shardingItemParameters = $("#shardingItemParameters").val();
        	if (shardingItemParameters == null || shardingItemParameters.length == 0) {
        		shardingItemParameters = null;
        	}
        	var jobParameter = $("#jobParameter").val();
        	if (jobParameter == null || jobParameter.length == 0) {
        		jobParameter = null;
        	}
        	var jobConfig = $("#jobConfig").val();
        	if (jobConfig == null || jobConfig.length == 0) {
        		jobConfig = null;
        	}
        	
        	$.ajax({
        		type : 'post',
        		url : 'job/addOrUpdateElasticJobConfig',
        		async : false,
        		data : {
        			id: $("#id").val(),
        			jobName: $("#jobName").val(),
        			cron: $("#cron").val(),
        			shardingTotalCount: $("#shardingTotalCount").val(),
        			shardingItemParameters: shardingItemParameters,
        			jobParameter: jobParameter,
        			description: $("#description").val(),
        			jobClass: $("#jobClass").val(),
        			jobConfig: jobConfig,
        			failover: $("#failover").val(),
        			misfire: $("#misfire").val(),
        			streamingProcess: $("#streamingProcess").val()
        		},
//        		dataType : 'json',
        		success : function(result) {
        			if (result == 1) {
        				bootbox.alert("操作成功");
            			$("#userDataTable").bootstrapTable("refresh", {
            				silent : true
            			});
            			clearData();
            			$('#myModal').modal('hide');
        			} else {
        				bootbox.alert("操作失败");
        			}
        		},
        		error : function(result) {
        			bootbox.alert("操作失败");
        		}
        	});
        }  
      });
}
function clearData() {
	$("#id").val('');
	$("#jobName").val('');
	$("#cron").val('');
	$("#shardingTotalCount").val('');
	$("#shardingItemParameters").val('');
	$("#jobParameter").val('');
	$("#description").val('');
	$("#jobClass").val('');
	$("#jobConfig").val('');
	$("#failover").val('true');
	$("#misfire").val('true');
	$("#streamingProcess").val('true');
	
}
function update(id) {
	clearData();
	$.ajax({
		type : 'get',
		url : 'job/getElasticJobConfig',
		async : false,
		data : {
			'id': id
		},
		dataType : 'json',
		success : function(result) {
			if (result != null) {
				$("#id").val(result.id);
				$("#jobName").val(result.jobName);
				$("#cron").val(result.cron);
				$("#shardingTotalCount").val(result.shardingTotalCount);
				$("#shardingItemParameters").val(result.shardingItemParameters);
				$("#jobParameter").val(result.jobParameter);
				$("#description").val(result.description);
				$("#jobClass").val(result.jobClass);
				$("#jobConfig").val(result.jobConfig);
				$("#failover").val(result.failover);
				$("#misfire").val(result.misfire);
				$("#streamingProcess").val(result.streamingProcess);
				
			}
		},
		error : function(result) {
			bootbox.alert("操作失败");
		}
	});
}
function deleteData(id) {
	bootbox.confirm("<b><big><big>确定执行操作？</></></>", function(flag) {  
        if (flag) {
        	$.ajax({
        		type : 'post',
        		url : 'job/delete',
        		async : false,
        		data : {
        			'id': id
        		},
        		dataType : 'json',
        		success : function(result) {
        			if (result != null && result == 1) {
        				bootbox.alert("删除成功");
        				$("#userDataTable").bootstrapTable("refresh", {
            				silent : true
            			});
        			} else {
        				bootbox.alert("删除失败");
        			}
        		},
        		error : function(result) {
        			bootbox.alert("删除失败");
        		}
        	});
        }  
      });
}
function refreshTable(){
	$("#userDataTable").bootstrapTable("refresh", {
		silent : true
	});
	$("#jobNames").val("");
}