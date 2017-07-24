/* 秒转化时间字符串 */
function timeStamp2String(time) {
	if (time == null || time == 0) {
		return "";
	} else {
		return (new Date(time * 1000)).format("yyyy-MM-dd");
	}
}

/* 获取时间 返回秒数 */
function TimeMillGet(date) {
	var str = date.split("-");
	var year = str[0];
	var month = str[1];
	var day = str[2];
	return new Date(year + '/' + month + '/' + day).getTime() / 1000;
}

function timeStamp2String2(time, pattern) {
    if (time == null || time == 0) {
        return "";
    } else {
        if (pattern) {
            return (new Date(time*1000)).format(pattern);
        }
        return (new Date(time*1000)).format("yyyy-MM-dd hh:mm:ss");
    }
}