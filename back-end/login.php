<?php
/**
 * Created by PhpStorm.
 * User: 鑫
 * Date: 2017/6/27
 * Time: 14:10
 */
//链接数据库
require dirname(__FILE__).'/conn.php';//转换成硬路径，速度更快

//登陆
//$nickname =@$_GET['nickname'];
$password ='qq';
$mobile ='666';

if (empty($nickname)) {
    $sql = "select * from user where mobile='$mobile' and password='$password'";
    $result = mysql_query($sql);
    if($row=@mysql_fetch_object($result)){
    $arr=array(
        'flat'=>'success',
        'message'=>'登陆成功',
        'id'=>$row->id,
        'nickname'=>$row->nickname,
        'password'=>$row->password,
        'mobile'=>$row->mobile,
        'portrait'=>$row->portrait
    );
    }
    else{
        $arr=array(
            'flat'=>'sfail',
            'message'=>'登陆失败'
        );
    }
}
elseif(empty($mobile)){
    $sql = "select * from user where nickname='$nickname' and password='$password'";
    $result = mysql_query($sql);
    if($row=@mysql_fetch_object($result)){
        $arr=array(
            'flat'=>'success',
            'message'=>'登陆成功',
            'id'=>$row->id,
            'nickname'=>$row->nickname,
            'password'=>$row->password,
            'mobile'=>$row->mobile,
            'portrait'=>$row->portrait
        );
    }
    else{
        $arr=array(
            'flat'=>'fail',
            'message'=>'登陆失败'
        );
    }
}
$strr=json_encode($arr);
echo($strr);

//$sql = "select * from user where nickname='bababa' and password='baba'";
//$result = mysql_query($sql);
//$row=@mysql_fetch_object($result);
//print_r($row);
//echo "$row->nickname";
//echo "$row->password";
//echo "$row->mobile";


?>