<?php
/**
 * Created by PhpStorm.
 * User: 鑫
 * Date: 2017/6/27
 * Time: 14:57
 */

//链接数据库
$conn=mysql_connect("localhost","root","") or die("数据库连接错误".mysql_error());
mysql_select_db("photograph",$conn) or die("数据库访问错误".mysql_error());
mysql_query("SET NAMES UTF8");

//注册
$nickname =@$_GET['nickname'];
$password =@$_GET['passwd'];
$mobile = @$_GET['mobile'];
$portrait=@$_GET['portrait'];
$sqla="select * from user where nickname='$nickname'";
$sqlb="select * from user where mobile='$mobile'";
$result1=mysql_query($sqla);
$row1=mysql_fetch_row($result1);
$result2=mysql_query($sqlb);
$row2=mysql_fetch_row($result2);
if(!$row1&&!$row2){
    $sql="INSERT INTO `user` (`nickname`, `password`, `mobile`, `portrait`) VALUES('$nickname','$password','$mobile','$portrait')";
    $result=mysql_query($sql);
    $row=@mysql_affected_rows($result);
    if($row){
    $arr=array(
        'flat'=>'success',
        'message'=>'注册成功'
    );
    }
    else{
        $arr=array(
            'flat'=>'fail',
            'message'=>'注册失败'
        );
    }
}
else{
    $arr=array(
        'flat'=>'fail',
        'message'=>'手机号或者账户名已存在'
    );
}
$strr=json_encode($arr);
echo($strr);

?>
