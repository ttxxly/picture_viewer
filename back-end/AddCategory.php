<?php
/**
 * Created by PhpStorm.
 * User: 鑫
 * Date: 2017/7/12
 * Time: 16:26
 *
 * 用户手动创建一个分类，
 *    1.若该用户id下相同分类标题已存在，返回错误信息
 *    2.若不存在，创建该分类，返回分类id
 */
//链接数据库
require dirname(__FILE__).'/conn.php';//转换成硬路径，速度更快

$time=time();      //当前时间戳
$datetime=date('Y-m-d-G-i-s',$time+28800);  //将时间戳转化为时间格式，28800是八个小时，因为东八区
$title=@$_GET['title'];//'风景99';
$description=@$_GET['description'];//'这是美景';
$keywords=@$_GET['keywords'];//'风景';
$userid=@$_GET['userid'];
// $title = '123';
// $description='123';
// $keywords='123';
// $userid='2';

$sql="select id from category where userid='$userid' and title='$title'";
$result=mysql_query($sql);
$rows=mysql_fetch_object($result);
if($rows == null) {
    $sa="insert into category(userid,title,description,keywords,datetime) values('$userid','$title','$description','$keywords','$datetime')";
    $result_insert = mysql_query($sa);
	if($result_insert != null) {
		$id=mysql_insert_id();
		$arr=array(
			'flat'=>'success',
			'message'=>'创建分类成功',
			'id'=>$id
		);
	}
    else{
		$arr=array(
			'flat'=>'success',
			'message'=>'插入失败！！！'
		);
	}
}
else{
    $arr=array(
        'flat'=>'success',
        'message'=>'该分类标题已存在'
    );
}

$arr['flat'] = urlencode($arr['flat']);
$arr['message'] = urlencode($arr['message']);

echo urldecode(json_encode($arr));
?>