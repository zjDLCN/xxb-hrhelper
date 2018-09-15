// 遮罩层控制
function ajaxLoading(){

  $("<div id='z1' class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height(),zIndex:99001}).appendTo("body");
  $("<div id='z2' class=\"datagrid-mask-msg\"></div>").html("正在处理，请稍候。。。").appendTo("body").css({display:"block",zIndex:99002,left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2});
  // $('#z1').dialog({zIndex: 99900});
  // $('#z2').dialog({zIndex: 99901});
};
function ajaxLoadEnd(){
  $(".datagrid-mask").remove();
  $(".datagrid-mask-msg").remove();
};

// 计算frame高度
function caculateFrameHeight() {
  var tabHeight = $("#tt").height();
  var tabTitleHeight = $(".tabs-inner").outerHeight(true);
  return tabHeight - tabTitleHeight - 5;
};
