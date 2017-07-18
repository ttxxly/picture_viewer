<?php
/**
 * Created by PhpStorm.
 * User: 鑫
 * Date: 2017/6/28
 * Time: 14:10
 */

//链接数据库
require dirname(__FILE__) . '/conn.php';//转换成硬路径，速度更快

//接收数据
//$userid='1';
//$nickname='易学习';

$userid = @$_GET['userid'];
$nickname = @$_GET['nickname'];
$password = @$_GET['password'];
$mobile = @$_GET['mobile'];
$portrait = @$_GET['portrait'];
//判断修改
if ($userid) {
    if ($nickname) {
        $sql = "update user set nickname='$nickname' where id='$userid'";
    } elseif ($password) {
        $sql = "update user set password='$password' where id='$userid'";
    } elseif ($mobile) {
        $sql = "update user set mobile='$mobile' where id='$userid'";
    }elseif ($portrait) {
        $sql = "update user set portrait='$portrait' where id='$userid'";
    }

    if ($result = mysql_query($sql)) {
        $mysql = "select * from user where id='$userid'";
        $answer = mysql_query($mysql);
        $row = mysql_fetch_object($answer);
        $arr = array(
            'flat' => 'success',
            'message' => '修改资料成功',
            'nickname' => $row->nickname,
            'password' => $row->password,
            'mobile' => $row->mobile,
            'portrait' => $row->portrait,
        );
    } else {
        $arr = array(
            'flat' => 'fail',
            'message' => '修改资料失败',
        );
    }
} else {
    $arr = array(
        'flat' => 'fail',
        'message' => '没有id'
    );
}
foreach ($arr as $key => $value) {
    $arr[$key] = urlencode($value);
}
//返给客户端 JSON 数据
echo urldecode(json_encode($arr));

?>