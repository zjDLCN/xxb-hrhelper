var FormatUtils = function () {};
// 格式化用户状态
FormatUtils.getUserState = function (data) {
    if (data === 1){
      return '正常';
    } else if(data === 0){
      return "未激活";
    } else{
      return "锁定"
    }
};
// 格式化数据状态
FormatUtils.getDataValid = function (data) {
  if (data) {
    return "启用";
  } else {
    return "停用"
  }
};
// 格式化日期格式
FormatUtils.getDate = function (data) {
  if (data === undefined) {
    return "";
  }
  var tmp = new Date(data);
  return tmp.getFullYear()+"-"+(tmp.getMonth()+1)+"-"+tmp.getDate();
};
// 格式化时间格式
FormatUtils.getTime = function (data) {
  if (data === undefined){
    return "";
  }
  var tmp = new Date(data);
  return tmp.getHours() + ":" + tmp.getMinutes();
};
// 格式化角色状态
FormatUtils.getRoleState = function (data) {
  if (data === 1){
    return '正常';
  } else if(data === 0){
    return "未使用";
  } else{
    return "停用"
  }
};