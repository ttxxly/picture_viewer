<?php
/**
 * Created by PhpStorm.
 * User: 鑫
 * Date: 2017/6/27
 * Time: 14:57
 */

//链接数据库
require dirname(__FILE__).'/conn.php';//转换成硬路径，速度更快

//注册
$nickname =@$_GET['nickname'];
$password =@$_GET['password'];
$mobile =@$_GET['mobile'];

$sqla = "select * from user where nickname='$nickname'";
$sqlb = "select * from user where mobile='$mobile'";
$result1 = mysql_query($sqla);
$row1 = mysql_fetch_row($result1);
$result2 = mysql_query($sqlb);
$row2 = mysql_fetch_row($result2);
if (!$row1 && !$row2) {
		if ($nickname && $mobile) {
			$sql = "INSERT INTO `user` (`nickname`, `password`, `mobile`, `portrait`) VALUES('$nickname','$password','$mobile','/photos/portrait/1.jpg')";
	    $result = mysql_query($sql);
	    $id = mysql_insert_id($conn);
	    if ($result) {
	        $arr = array(
	            'flat' => 'success',
	            'message' => '注册成功',
	            'userid' => $id,
	            'nickname' => $nickname,
	            'password' => $password,
	            'mobile' => $mobile,
	            'portrait' => '/photos/portrait/1.jpg'
	        );
	    } else {
	        $arr = array(
	            'flat' => 'fail',
	            'message' => '数据库错误'
	        );
	    }
		}else {
			$arr = array(
        'flat' => 'fail',
        'message' => '手机号或者账户名为空'
    );
		}
} else {
    $arr = array(
        'flat' => 'fail',
        'message' => '手机号或者账户名已存在'
    );
}

foreach ($arr as $key => $value) {
    $arr[$key] = urlencode($value);
}
//返给客户端 JSON 数据
echo urldecode(json_encode($arr));

?>
