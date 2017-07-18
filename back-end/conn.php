<?php
/**
 * Created by PhpStorm.
 * User: 鑫
 * Date: 2017/6/27
 * Time: 13:30
 */

//链接数据库
$conn=mysql_connect("localhost","root","") or die("数据库连接错误".mysql_error());
mysql_select_db("photograph",$conn) or die("数据库访问错误".mysql_error());
mysql_query("SET NAMES UTF8");

?>

