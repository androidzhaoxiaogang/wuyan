function switchTab(tabpage,tabid){
var oItem = document.getElementById(tabpage).getElementsByTagName("li"); 
    for(var i=0; i<oItem.length; i++){
        var x = oItem[i];    
        x.className = "";
}
	document.getElementById(tabid).className = "Selected";
	var dv1=document.getElementById("dTab1");
	var dv2=document.getElementById("dTab2");
	var dv3=document.getElementById("dTab3");
	var dv4=document.getElementById("dTab4");
	var dv5=document.getElementById("dTab5");
	if(tabid=='Tab1'){
		getStatistics();
		dv1.style.display='block';
		dv2.style.display='none';
		dv3.style.display='none';
		dv4.style.display='none';
		dv5.style.display='none';
	}
	if(tabid=='Tab2'){
		getTag();
		dv2.style.display='block';
		dv1.style.display='none';
		dv3.style.display='none';
		dv4.style.display='none';
		dv5.style.display='none';
	}
	if(tabid=='Tab3'){
		getFunTag();
		dv3.style.display='block';
		dv1.style.display='none';
		dv2.style.display='none';
		dv4.style.display='none';
		dv5.style.display='none';
	}
	if(tabid=='Tab4'){
		getActivity();
		dv4.style.display='block';
		dv1.style.display='none';
		dv2.style.display='none';
		dv3.style.display='none';
		dv5.style.display='none';
	}
	if(tabid=='Tab5'){
		dv5.style.display='block';
		dv1.style.display='none';
		dv2.style.display='none';
		dv3.style.display='none';
		dv4.style.display='none';
	}
}
$(function() {
	getStatistics();
})
function verifyDate(){
	var time=$("#expire_time").val();
	var reg=/((^((1[8-9]\d{2})|([2-9]\d{3}))([-\/\._])(10|12|0?[13578])([-\/\._])(3[01]|[12][0-9]|0?[1-9])$)|(^((1[8-9]\d{2})|([2-9]\d{3}))([-\/\._])(11|0?[469])([-\/\._])(30|[12][0-9]|0?[1-9])$)|(^((1[8-9]\d{2})|([2-9]\d{3}))([-\/\._])(0?2)([-\/\._])(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)([-\/\._])(0?2)([-\/\._])(29)$)|(^([3579][26]00)([-\/\._])(0?2)([-\/\._])(29)$)|(^([1][89][0][48])([-\/\._])(0?2)([-\/\._])(29)$)|(^([2-9][0-9][0][48])([-\/\._])(0?2)([-\/\._])(29)$)|(^([1][89][2468][048])([-\/\._])(0?2)([-\/\._])(29)$)|(^([2-9][0-9][2468][048])([-\/\._])(0?2)([-\/\._])(29)$)|(^([1][89][13579][26])([-\/\._])(0?2)([-\/\._])(29)$)|(^([2-9][0-9][13579][26])([-\/\._])(0?2)([-\/\._])(29)$))/;
	if(!reg.test(time)){
		layer.alert("请输入正确时间格式！");
	}
}
function changeLineColor(o, a, b, c, d) {
	var t = document.getElementById(o).getElementsByTagName("tr");
	for (var i = 0; i < t.length; i++) {
		t[i].style.backgroundColor = (t[i].sectionRowIndex % 2 == 0) ? a
				: b;
		t[i].onclick = function() {
			if (this.x != "1") {
				this.x = "1";
				this.style.backgroundColor = d;
			} else {
				this.x = "0";
				this.style.backgroundColor = (this.sectionRowIndex % 2 == 0) ? a
						: b;
			}
		}
		t[i].onmouseover = function() {
			if (this.x != "1")
				this.style.backgroundColor = c;
		}
		t[i].onmouseout = function() {
			if (this.x != "1")
				this.style.backgroundColor = (this.sectionRowIndex % 2 == 0) ? a
						: b;
		}
	}
}
function getStatistics() {
	var token = $.cookies.get("wuyan_token");
	if(token!=null){
		$.get(addr+"/statistics", {
			access_token : token
		}, function(data) {
			$("#countNum").html(0);
			$("#todayNum").html(0);
			$("#monthNum").html(0);
			$("#yearNum").html(0);
			var result = data.result;
			if (result != null) {
				var countNum = result.countNum;
				var todayNum = result.todayNum;
				var monthNum = result.monthNum;
				var yearNum = result.yearNum;
				$("#countNum").html(countNum);
				$("#todayNum").html(todayNum);
				$("#monthNum").html(monthNum);
				$("#yearNum").html(yearNum);
			}
		}, "json")
	}else{
		layer.alert("重新登录！");
		window.location.href =localhostPaht+projectName;
	}
}
function getTag(page) {
	if (page == null) {
		page = 0;
	}
	var token = $.cookies.get("wuyan_token");
	var name =$("#tagName").val();
	var type=$("#tagType option:selected").val();
	if(token!=null){
	$
			.get(addr+
					"/tags?cursor="
							+ (page - 1),
					{
						access_token : token,
						name:name,
						type:type
					},
					function(data) {
						$("#list").html("");
						var tags = data.result;
						var count = data.total % 10;
						if(count==0){
							count=parseInt(data.total / 10);
						}else{
							count=parseInt(data.total / 10)+1;
						}
						var start = parseInt(data.cursor / 10) + 1;
						if (start < 1) {
							start = 1;
						}
						$("#current").val(start);
						if (tags != null) {
							var str = "<table width='750' border='0' cellpadding='2' cellspacing='0' color='#666' style='margin:10px;'>";
							str += "<tr><td width='100%'>";
							str += "<table id='tableId' border='0' cellpadding='3' cellspacing='1' class='tab' width='100%'; align='center';>";
							str += "<tr style='line-height:25px;background:#fff; text-align:center;'><td>编号</td><td>名称</td><td>权重</td><td>类型</td><td>操作</td></tr>";
							for (var i = 0; i < tags.length; i++) {
								var id = tags[i].id;
								var name = tags[i].name;
								var weight = tags[i].weight;
								var type = tags[i].type;
								str += "<tr style='line-height:15px;background:#fff; color:#666666; text-align:center;'>"
								str += "<td>" + (i+1) + "</td>";
								str += "<td>" + name + "</td>";
								str += "<td>" + weight + "</td>";
								str += "<td>" + type + "</td>";
								str += "<td><a href='javascript:getById("
										+ id
										+ ")'>编辑</a>  | <a href='javascript:del("
										+ id + ")'>删除</a></td>";
								str += "</tr>"
							}
							str += "</table>";
							str += "</td></tr></table>";
							$("#list").html(str);
							$("#demo2").paginate({
								count : count,
								start : start,
								display : 10,
								border : false,
								text_color : '#888',
								background_color : '#EEE',
								text_hover_color : 'black',
								background_hover_color : '#CFCFCF',
								onChange : function(page) {
									getTag(page * 10 - 9)
								}
							});
							changeLineColor("tableId","#fff","#F5F5F5","#FFFFCC","#FFFFCC");
						}else{
							layer.alert("获取失败！");
						}
						
					}, "json")
	}else{
		layer.alert("重新登录！");
		window.location.href =localhostPaht+projectName;
	}

}
function getById(id) {
	var token = $.cookies.get("wuyan_token");
	if(token!=null){
	$.get(addr+"/tag/" + id + "?access_token="
			+ token, function(data) {
		var result = data.result;
		if (result != null) {
			var id = result.id;
			var name = result.name;
			var weight = result.weight;
			$("#edi_tag_id").val(id);
			$("#edi_tag_name").val(name);
			$("#edi_tag_weight").val(weight);
		}
	}, "json");
	$.layer({
		type : 1,
		title : '修改标签',
		fix : true,
		offset : [ '50', '' ],
		area : [ '300px', '200px' ],
		page : {
			dom : '#ediTagDIV'
		}
	});
}else{
	layer.alert("重新登录！");
	window.location.href =localhostPaht+projectName;
}
}
function addTag() {
	$.layer({
		type : 1,
		title : '添加标签',
		fix : true,
		offset : [ '50', '' ],
		area : [ '300px', '200px' ],
		page : {
			dom : '#addTagDIV'
		}
	});
}
function add() {
	var token = $.cookies.get("wuyan_token");
	if(token!=null){
	var name = $("#add_tag_name").val();
	var weight = $("#add_tag_weight").val();
	var type = $("#add_tag_type option:selected").val();
	var data1 = {
		name : name,
		weight : weight,
		type : type
	};
	$.ajax({
		type : "POST",
		url : addr+"/tag/?access_token=" + token,
		dataType : "json",
		contentType : "application/json",
		data : JSON.stringify(data1),
		success : function(data) {
			layer.msg('添加成功', 1, 1);
			
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			layer.msg('添加失败', 1, 1);
		}
	});
	getTag();
	layer.closeAll();
	
}else{
	layer.alert("重新登录！");
	window.location.href =localhostPaht+projectName;
}
}
function edi() {
	var token = $.cookies.get("wuyan_token");
	var current=$("#current").val();
	if(current==null||current==""){
		current=1;
	}
	if(token!=null){
	var id = $("#edi_tag_id").val();
	var name = $("#edi_tag_name").val();
	var weight = $("#edi_tag_weight").val();

	var data1 = {
		name : name,
		weight : weight
	};
	$.ajax({
		type : "POST",
		url : addr+"/tag/" + id
				+ "/update?access_token=" + token,
		dataType : "json",
		contentType : "application/json",
		data : JSON.stringify(data1),
		success : function(data) {
			var result = data.result;
			if (result == "ok") {
			layer.msg('修改成功', 1, 1);
			getTag(current* 10 - 9);
			}else{
				layer.msg('修改失败', 1, 1);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			layer.msg('修改失败', 1, 1);
		}
	});
	layer.closeAll();
	
}else{
	layer.alert("重新登录！");
	window.location.href =localhostPaht+projectName;
}
}
function del(id) {
	var token = $.cookies.get("wuyan_token");
	if(token!=null){
	$.layer({
		shade : [ 0 ],
		area : [ 'auto', 'auto' ],
		dialog : {
			msg : '确定要删除么？',
			btns : 2,
			type : 4,
			btn : [ '确定', '取消' ],
			yes : function() {
				var url = addr+"/tag/" + id
						+ "/destroy?access_token=" + token;
				$.post(url, function(data) {
					var result = data.result;
					if (result == "ok") {
						layer.msg('删除成功', 1, 1);
					} else {
						layer.msg('删除失败', 1, 1);
					}
				}, "json");
			},
			no : function() {
				layer.closeAll()
			}
		}
	});
	getTag();
}else{
	layer.alert("重新登录！");
	window.location.href =localhostPaht+projectName;
}

}
//趣味标签
function getFunTag() {
	var token = $.cookies.get("wuyan_token");
	if(token!=null){
	$
			.get(addr+"/tags/funnny",
					{
						access_token : token
					},
					function(data) {
						$("#fun_list").html("");
						var tags = data.result;
						if (tags != null) {
							var str = "<table width='750' border='0' cellpadding='2' cellspacing='0' color='#666' style='margin:10px;'>";
							str += "<tr><td width='100%'>";
							str += "<table  border='0' cellpadding='3' cellspacing='1' class='tab' width='100%'; align='center';>";
							str += "<tr style='line-height:25px;background:#fff; text-align:center;'><td>编号</td><td>名称</td><td>clazz</td><td>type</td><td>value</td><td>category</td><td>操作</td></tr>";
							for (var i = 0; i < tags.length; i++) {
								
								var id=tags[i]._id;
								var name = tags[i].name;
								var value = tags[i].value;
								var clazz = tags[i].clazz;
								var type = tags[i].type;
								var category = tags[i].category;
								str += "<tr style='line-height:15px;background:#fff; color:#666666; text-align:center;'>"
								str += "<td>" + (i+1) + "</td>";
								str += "<td>" + name + "</td>";
								str += "<td>" + clazz + "</td>";
								str += "<td>" + type + "</td>";
								str += "<td>" + value + "</td>";
								str += "<td>" + category + "</td>";
								str += "<td><a href='javascript:delFun(\""
										+ id + "\")'>删除</a></td>";
								str += "</tr>"
							}
							str += "</table>";
							str += "</td></tr></table>";
							$("#fun_list").html(str);
						}
						
					}, "json")
	}else{
		layer.alert("重新登录！");
		window.location.href =localhostPaht+projectName;
	}
}
function delFun(id) {
	var token = $.cookies.get("wuyan_token");
	if(token!=null){
	$.layer({
		shade : [ 0 ],
		area : [ 'auto', 'auto' ],
		dialog : {
			msg : '确定要删除么？',
			btns : 2,
			type : 4,
			btn : [ '确定', '取消' ],
			yes : function() {
				var url = addr+"/tags/funny/"+id
						+ "/destroy?access_token=" + token;
				$.post(url, function(data) {
					var result = data.result;
					if (result == "ok") {
						layer.msg('删除成功', 1, 1);
						getFunTag();
					} else {
						layer.msg('删除失败', 1, 1);
					}
				}, "json");
			},
			no : function() {
				layer.closeAll();
			}
		}
	});
}else{
	layer.alert("重新登录！");
	window.location.href =localhostPaht+projectName;
}

}
function addFunTag() {
	$.layer({
		type : 1,
		title : '添加趣味标签',
		fix : true,
		offset : [ '50', '' ],
		area : [ '300px', '200px' ],
		page : {
			dom : '#addFunTagDIV'
		}
	});
}
function addFun() {
	var token = $.cookies.get("wuyan_token");
	if(token!=null){
	var name = $("#add_fun_tag_name").val();
	var category=$("#add_fun_tag_category option:selected").val();
	var clazz;
	var type;
	var value;
	if(category==1){
		clazz=1;
		type=0;
		value=0;
	}
	if(category==2){
		clazz=1;
		type=0;
		value=1;
	}
	if(category==3){
		clazz=1;
		type=0;
		value=2;			
	}
	if(category==4){
		clazz=1;
		type=0;
		value=3;
	}
	if(category==5){
		clazz=2;
		type=0;
	}
	if(category==6){
		clazz=3;
		type=0;
		value=0;
	}
	if(category==7){
		clazz=3;
		type=0;
		value=1;
	}
	if(category==8){
		clazz=3;
		type=0;
		value=2;
	}
	if(category==9){
		clazz=4;
		type=0;
	}
	if(category==10){
		clazz=5;
		type=1;
	}
	if(category==11){
		clazz=5;
		type=2;
	}
	if(category==12){
		clazz=5;
		type=3;
	}
	if(category==13){
		clazz=5;
		type=4;
	}
	if(category==14){
		clazz=5;
		type=5;
	}
	if(category==15){
		clazz=5;
		type=6;
	}
	if(category==16){
		clazz=5;
		type=7;
	}
	if(category==17){
		clazz=5;
		type=8;
	}
	if(category==18){
		clazz=5;
		type=9;
	}
	if(category==19){
		clazz=5;
		type=10;
	}
	if(category==20){
		clazz=5;
		type=11;
	}
	var data1 = {
		name : name,
		category:category,
		clazz:clazz,
		type:type,
		value:value
	};
	$.ajax({
		type : "POST",
		url : addr+"/tags/funny?access_token=" + token,
		dataType : "json",
		contentType : "application/json",
		data : JSON.stringify(data1),
		success : function(data) {
			layer.alert("添加成功！");
			layer.closeAll();
			getFunTag();
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			layer.alert("添加失败！")
		}
	});
	}else{
		layer.alert("重新登录！");
		window.location.href =localhostPaht+projectName;
	}
}
//活动
function getActivity(page){
	if (page == null) {
		page = 0;
	}
	var token = $.cookies.get("wuyan_token");
	if(token!=null){
		$.get(addr+"/activity?access_token="+token+"&cursor="+ (page - 1),function (data){
			$("#activity_list").html("");
			var activity=data.result;
			var count = data.total/data.limit;
			if(count==0){
				count=data.total/data.limit
			}else{
				count=parseInt(data.total/data.limit)+1
			}
			var start = parseInt(data.cursor/data.limit)+1;
			if(start<1){
				start=1;
			}
			if(activity!=null&&activity!=""){
				var str = "<table width='750' border='0' cellpadding='2' cellspacing='0' color='#666' style='margin:10px;'>";
				str += "<tr><td width='100%'>";
				str += "<table id='tableId1' border='0' cellpadding='3' cellspacing='1' class='tab' width='100%'; align='center';>";
				str += "<tr style='line-height:25px;background:#fff; text-align:center;'><td>编号</td><td>名称</td><td>操作</td><td>详细页操作</td></tr>";
				for (var i = 0; i < activity.length; i++) {
					var describes = activity[i].describes;
					var id = activity[i].id;
					str += "<tr style='line-height:15px;background:#fff; color:#666666; text-align:center;'>"
					str += "<td>" + (i+1) + "</td>";
					str += "<td>" + describes + "</td>";
					str += "<td><a href='javascript:getActivityByid("
							+ id + ")'>查看</a>|<a href='javascript:delActivity(\""
							+ id + "\")'>删除</a></td>";
					str+="<td><a href='javascript:getDetail("+id+")'>查看</a>|<a href='javascript:addDetail("+id+")'>添加</a></td>";
					str += "</tr>";
				}
				str += "</table>";
				str += "</td></tr></table>";
				$("#activity_list").html(str);
				$("#demo3").paginate({
					count : count,
					start : start,
					display : 10,
					border : false,
					text_color : '#888',
					background_color : '#EEE',
					text_hover_color : 'black',
					background_hover_color : '#CFCFCF',
					onChange : function(page) {
						getActivity(page * 10 - 9);
					}
				});
				changeLineColor("tableId1","#fff","#F5F5F5","#FFFFCC","#FFFFCC");
			}
		},"json")
		
	}else{
		layer.alert("重新登录！");
		window.location.href =localhostPaht+projectName;
	}
} 
function delActivity(id){
	var token = $.cookies.get("wuyan_token");
	if(token!=null){
		$.layer({
			shade : [ 0 ],
			area : [ 'auto', 'auto' ],
			dialog : {
				msg : '确定要删除么？',
				btns : 2,
				type : 4,
				btn : [ '确定', '取消' ],
				yes : function() {
					$.post(addr+"/activity/"+id+"/destroy?access_token="+token,function (data){
						var result = data.result;
						if (result == "ok") {
							layer.msg('删除成功', 1, 1);
							
						} else {
							layer.msg('删除失败', 1, 1);
						}
						getActivity();
					},"json");
				},
				no : function() {
					layer.closeAll();
				}
			}
		});
		
	}else{
		layer.alert("重新登录！");
		window.location.href =localhostPaht+projectName;
	}
}
function getActivityByid(id){
	var token = $.cookies.get("wuyan_token");
	var url=addr+"/activity/"+id+"/cover"+"?access_token="+token;
	$("#activityImg").attr("src","");
	if(token!=null){
		$("#activityImg").attr("src",url);
	}else{
		layer.alert("重新登录！");
		window.location.href =localhostPaht+projectName;
	}
	var i = $.layer({
	    type : 1,
	    title : false,
	    fix : false,
	    offset:['50px' , ''],
	    area : ['300px','300px'],
	    page : {dom : '#activityImg'}
	});
}
function getDetail(id){
	var token = $.cookies.get("wuyan_token");
	var url=addr+"/activity/"+id+"/detail"+"?access_token="+token;
	$("#detailImg").attr("src","");
	if(token!=null){
		$("#detailImg").attr("src",url);
	}else{
		layer.alert("重新登录！");
	}
	var i = $.layer({
	    type : 1,
	    title : false,
	    fix : false,
	    offset:['50px' , ''],
	    area : ['300px','300px'],
	    page : {dom : '#detailImg'}
	});
	
}
function addDetail(id){
	$("#detail").val(id);
	$.layer({
		type : 1,
		title : '添加活动',
		fix : true,
		offset : [ '50', '' ],
		area : [ '300px', '200px' ],
		page : {
			dom : '#detailDIV'
		}
	});
	adddetails(id);
}
function adddetails(id){
	var token = $.cookies.get("wuyan_token");
    var uploader1 = new plupload.Uploader({
        runtimes: 'gears,flash,silverlight,browserplus,html5',
        browse_button: 'pickfiles2', // you can pass in id...
        container: document.getElementById('uploader1'), // ... or DOM Element itself
        url: addr+'/activity/'+id+'/detail?access_token='+token,
        flash_swf_url: '/assets/plugins/plupload/Moxie.swf',
        silverlight_xap_url: '/assets/plugins/plupload/Moxie.xap',
		multipart:false,
        filters: {
            max_file_size: '10mb',
            mime_types: [
                {title : "Image files", extensions : "jpg,gif,png"},
                {title : "Zip files", extensions : "zip"}
            ]
        },
        init: {
            PostInit: function () {
                $('#uploadfiles1').html("上传图片");
                $("#uploadfiles1").on('click', function () {
                    uploader1.start();
                    return false;
                });
            },
            BeforeUpload:function(up,file){
            	//重点在这里，上传的时候自定义参数信息
            	uploader1.setOption("multipart_params",{"fileName":file.name});
            }, 
            FilesAdded: function (up, files) {
                var html = "";
                plupload.each(files, function (file) {
                    html = '<span>图片：</span><input type="text" name="body" class="form-control '+file.id+'" value="' + file.name + '"/><label for="none" class="control-label processbar col-md-1 ' + file.id + '" ></label>';
                });
                $("#filelist1").html(html);
            },
            UploadProgress: function (up, file) {
                $("label." + file.id).html(file.percent + '%');
            },
            FileUploaded: function (up, file,response) {
                var res = $.parseJSON(response.response);
                $("input." + file.id).val(res.result);
                getActivity();
                layer.closeAll();
                layer.msg('添加成功', 1, 1);
            },
            Error: function (up, err) {
                document.getElementById('console1').innerHTML += "\nError #" + err.code + ": " + err.message;
            }
        }
    });
    uploader1.init();
}
function addFile(){
	$.layer({
		type : 1,
		title : '添加活动',
		fix : true,
		offset : [ '50', '' ],
		area : [ '300px', '200px' ],
		page : {
			dom : '#fileDIV'
		}
	});
	addpicture();
 }
function addpicture(){
	var describe= $("#describe").val();
    var expire_time=$("#expire_time").val();
    var token = $.cookies.get("wuyan_token");
        var uploader = new plupload.Uploader({
            runtimes: 'gears,flash,silverlight,browserplus,html5',
            browse_button: 'pickfiles1', // you can pass in id...
            container: document.getElementById('uploader'), // ... or DOM Element itself
            url: addr+'/activity?access_token='+token,
            flash_swf_url: '/assets/plugins/plupload/Moxie.swf',
            silverlight_xap_url: '/assets/plugins/plupload/Moxie.xap',
 			multipart:false,
            filters: {
                max_file_size: '10mb',
                mime_types: [
                    {title : "Image files", extensions : "jpg,gif,png"},
                    {title : "Zip files", extensions : "zip"}
                ]
            },
            init: {
                PostInit: function () {
                    $('#uploadfiles').html("上传图片");
                    $("#uploadfiles").on('click', function () {
                    	describe= $("#describe").val();
                    	expire_time=$("#expire_time").val();
                        uploader.start();
                        return false;
                    });
                },
                BeforeUpload:function(up,file){
                	//重点在这里，上传的时候自定义参数信息
                	uploader.setOption("multipart_params",{"expire_time":expire_time,"describe":describe});
                }, 
                FilesAdded: function (up, files) {
                    var html = "";
                    plupload.each(files, function (file) {
                        html = '<span>图片：</span><input type="text" name="body" class="form-control '+file.id+'" value="' + file.name + '"/><label for="none" class="control-label processbar col-md-1 ' + file.id + '" ></label>';
                    });
                    $("#filelist").html(html);
                },
                UploadProgress: function (up, file) {
                    $("label." + file.id).html(file.percent + '%');
                },
                FileUploaded: function (up, file,response) {
                    var res = $.parseJSON(response.response);
                    $("input." + file.id).val(res.result);
                    getActivity();
                    layer.closeAll();
                    layer.msg('添加成功', 1, 1);
                },
                Error: function (up, err) {
                    document.getElementById('console').innerHTML += "\nError #" + err.code + ": " + err.message;
                }
            }
        });
        uploader.init();
}
//推送
function selectUser(){
	var select=$("#pushUser option:selected").val();
	if(select==1){
		$("#province").html("");
		var str="";
		$.get(addr+"/getProvince",function(data){
			var result=data.result;
			if(result!=null){
				for(var i=0;i<result.length;i++){
					var code=result[i].code;
					var name=result[i].name;
					str+="<option value="+code+">"+name+"</option>";
				}
			}
			$("#province").html(str);
		},"json");
		document.getElementById("province").style.display='block';
		document.getElementById("city").style.display='none';
		document.getElementById("area").style.display='none';
	}else{
		document.getElementById("province").style.display='none';
	}
	
}
function province(){
	var code=$("#province option:selected").val();
	$("#city").html("");
	var str="";
	$.get(addr+"/getCity",{provincecode:code},function (data){
		var result=data.result;
		if(result!=null){
			for(var i=0;i<result.length;i++){
				var code=result[i].code;
				var name=result[i].name;
				str+="<option value="+code+">"+name+"</option>";
			}
		}
		$("#city").html(str);
	},"json");
	document.getElementById("city").style.display='block';
	document.getElementById("area").style.display='none';
}
function city(){
	var code=$("#city option:selected").val();
	$("#area").html("");
	var str="";
	$.get(addr+"/getArea",{citycode:code},function (data){
		var result=data.result;
		if(result!=null){
			for(var i=0;i<result.length;i++){
				var code=result[i].code;
				var name=result[i].name;
				str+="<option value="+code+">"+name+"</option>";
			}
		}
		$("#area").html(str);
	},"json");
	document.getElementById("area").style.display='block';
}
function send(){
	var token = $.cookies.get("wuyan_token");
//	var pushTitle=$("#pushTitle").val();
	var pushContent=$("#pushContent").val();
	if(pushContent.replace(/[ ]/g,"")!=""){
		var select=$("#pushUser option:selected").val();
		if(select==1){
			var code=$("#province option:selected").val();
			if(code==null||code==""){
				layer.alert("选择省份!");
			}else{
				var city=$("#city option:selected").val();
				if(city==null||city==""){
					layer.alert("选择城市!");
				}else{
					var area=$("#area option:selected").val();
					if(area==null||area==""){
						layer.alert("选择区域!");
					}else{
						$.get(addr+"/push",{state:1,province:code,city:city,area:area,content:pushContent,access_token:token},function(data){
							var result=data.result;
							if(result=="ok"){
								layer.alert("发送成功");
							}
						},"json");
						
					}
				}
			}
		}else{
			$.get(addr+"/push",{state:0,access_token:token,content:pushContent},function(data){
				var result=data.result;
				if(result=="ok"){
					layer.alert("发送成功");
				}
			},"json");
		}
		
	}else{
		layer.alert("输入内容");
		
	}
	
}
