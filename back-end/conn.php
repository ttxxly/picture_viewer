<?php
/**
 * Created by PhpStorm.
 * User: ��
 * Date: 2017/6/27
 * Time: 13:30
 */

//�������ݿ�
$conn=mysql_connect("localhost","root","") or die("���ݿ����Ӵ���".mysql_error());
mysql_select_db("photograph",$conn) or die("���ݿ���ʴ���".mysql_error());
mysql_query("SET NAMES UTF8");

?>

