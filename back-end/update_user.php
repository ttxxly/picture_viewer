<?php
/**
 * Created by PhpStorm.
 * User: 鑫
 * Date: 2017/6/28
 * Time: 14:10
 */

//链接数据库
require dirname(__FILE__).'/conn.php';//转换成硬路径，速度更快

//接收数据
$userid='1';//@$_GET['userid'];
$nickname='易学习';//@$_GET['nickname'];
$password=@$_GET['passwd'];
$mobile=@$_GET['mobile'];

//判断修改
if($userid){
if($nickname){
    $sql="update user set nickname='$nickname' where id='$userid'";
}
elseif($password){
    $sql="update user set password='$password' where id='$userid'";
}
elseif($mobile){
    $sql="update user set mobile='$mobile' where id='$userid'";
}

$result=mysql_query($sql);
if($result==1){
    $arr=array(
        'flat'=>'success',
        'message'=>'修改资料成功',
        'id'=>$userid
    );
}
else{
    $arr=array(
        'flat'=>'fail',
        'message'=>'修改资料失败',
        'id'=>$userid
    );
}
}
else{
    $arr=array(
        'flat'=>'fail',
        'message'=>'没有id'
    );
}
$strr=json_encode($arr);
echo($strr);