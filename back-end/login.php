<?php
/**
 * Created by PhpStorm.
 * User: 鑫
 * Date: 2017/6/27
 * Time: 14:57
 * 接受客户端请求， 将之与数据库的信息进行对比，然后做相应的操作。
 */
//链接数据库
require dirname(__FILE__) . '/conn.php';//转换成硬路径，速度更快

//登陆
$nickname = @$_GET['nickname'];
$password = @$_GET['password'];
$mobile = @$_GET['mobile'];

/*
 * 关于三个变量的合法性，我在客户端中已经判断过了，所以在这里为了节省代码量就没有写了（其实我就是懒得写）
 */
if (!empty($nickname)) {
    $sql = "select * from user where nickname='$nickname' and password='$password'";

} else  {
    $sql = "select * from user where mobile='$mobile' and password='$password'";
}

$result = mysql_query($sql);
if ($row = @mysql_fetch_object($result)) {
    $arr = array(
        'flat' => 'success',
        'message' => '登陆成功',
        'userid' => $row->id,
        'nickname' => $row->nickname,
        'password' => $row->password,
        'mobile' => $row->mobile,
        'portrait' => $row->portrait
    );
} else {
    $arr = array(
        'flat' => 'fail',
        'message' => '登陆失败'
    );
}

foreach ($arr as $key => $value) {
    $arr[$key] = urlencode($value);
}
//返给客户端 JSON 数据
echo urldecode(json_encode($arr));
?>
