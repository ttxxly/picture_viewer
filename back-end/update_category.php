<?php
/**
 * Created by PhpStorm.
 * User: 鑫
 * Date: 2017/6/28
 * Time: 10:28
 */

//修改分类

//链接数据库
$conn=mysql_connect("localhost","root","") or die("数据库连接错误".mysql_error());
mysql_select_db("photograph",$conn) or die("数据库访问错误".mysql_error());
mysql_query("SET NAMES UTF8");

//获取数据
$id=$_GET['id'];   //图片id
$title=$_GET['tital'];       //标题
$description=$_GET['description'];   //描述
$keywords=$_GET['keywords'];     //关键字
$parentid=$_GET['parentid'];  //分类级别

$sql="update category set title='$title',description='$description',keywords='$keywords',parentid='$parentid' where id='$id'";
//$sql="update category set title='标题标题',description='描述描述',keywords='贼关键',parentid='8888' where id='3'";  //测试
$result=mysql_query($sql);
if($result==1){
    $arr=array(
        'flat'=>'success',
        'message'=>'修改图片分类成功'
    );
}
else{
    $arr=array(
        'flat'=>'fail',
        'message'=>'修改图片分类失败'
    );
}
$strr=json_encode($arr);
echo($strr);

