<?php
/**
 * Created by PhpStorm.
 * User: 鑫
 * Date: 2017/6/28
 * Time: 14:18
 */

//链接数据库
$conn=mysql_connect("localhost","root","") or die("数据库连接错误".mysql_error());
mysql_select_db("photograph",$conn) or die("数据库访问错误".mysql_error());
mysql_query("SET NAMES UTF8");

//
//$icon=@$_GET['icon'];
//$description=@$_GET['description'];
//$url=@$_GET['url'];
//$categoryid=@$_GET['cate'];
$time=time();      //当前时间戳
$datetime=date('Y-m-d-G-i-s',$time+28800);  //将时间戳转化为时间格式，28800是八个小时，因为东八区
$title='风景8';
$description='这是美景';
$keywords='风景';
$image='./pic/fengjing8.jpg';
$categoryid='33';
$icon='33';

//$sql="update photograph set datetime='$datetime' description='$description' keywords='$keywords' image='$image' categoryid='$categoryid' icon='$icon'";
$sql="insert into photograph(categoryid,title,description,keywords,image,icon,datetime) values('$categoryid','$title','$description','$keywords','$image','$icon','$datetime')";
$result=mysql_query($sql);
echo $result;
if($result)
    echo "sucess";
else
    echo "fail";
?>