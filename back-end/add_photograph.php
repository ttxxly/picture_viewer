<?php
/**
 * Created by PhpStorm.
 * User: 鑫
 * Date: 2017/6/28
 * Time: 14:18
 */

//链接数据库
require dirname(__FILE__).'/conn.php';//转换成硬路径，速度更快

//
//$icon=@$_GET['icon'];
//$description=@$_GET['description'];
//$url=@$_GET['url'];
//$categoryid=@$_GET['cate'];
$time=time();      //当前时间戳
$datetime=date('Y-m-d-G-i-s',$time+28800);  //将时间戳转化为时间格式，28800是八个小时，因为东八区
$title='风景99';
$description='这是美景';
$keywords='风景';
$image='./pic/fengjing8.jpg';
$categoryid='66';
$icon='33';

//$sql="update photograph set datetime='$datetime' description='$description' keywords='$keywords' image='$image' categoryid='$categoryid' icon='$icon'";
$sql="insert into photograph(categoryid,title,description,keywords,image,icon,datetime) values('$categoryid','$title','$description','$keywords','$image','$icon','$datetime')";
$result=mysql_query($sql);
if($result)
{
    $arr=array(
        'flat'=>'success',
        'message'=>'添加图片成功'
    );
}
else
{
    $arr=array(
        'flat'=>'fail',
        'message'=>'添加失败成功'
    );
}
foreach ($arr as $key => $value) {
    $arr[$key] = urlencode($value);
}
//返给客户端 JSON 数据
echo urldecode(json_encode($arr));