<?php
/**
 * Created by PhpStorm.
 * User: 鑫
 * Date: 2017/7/3
 * Time: 14:04
 * 搜索图片
 */

//链接数据库
$conn=mysql_connect("localhost","root","") or die("数据库连接错误".mysql_error());
mysql_select_db("photograph",$conn) or die("数据库访问错误".mysql_error());
mysql_query("SET NAMES UTF8");

//$keys=$_GET['keys'];
$keys='风景';
$i='0';
$sql="select image from photograph where keywords='$keys'";
$result=mysql_query($sql);
    while($row=mysql_fetch_assoc($result))
    {
       // echo "$row[image]<br/>";
        $res[$i]=$row['image'];
        $i++;
    }
$arr=array(
    'flat'=>'success',
    'message'=>'注册成功',
    'arra'=>$res
);
$strr=json_encode($arr);
echo($strr);
//print_r($res);
?>