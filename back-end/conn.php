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


$userid='23666';//$_GET['userid'];
$sqll="select id from category where userid='$userid'";
$resultt=mysql_query($sqll);
$rows=mysql_fetch_object($resultt);
if(!$rows) {
    $sa="insert into category(userid,title,description,keywords) values('233','saas','sasassasa','sa')";
    mysql_query($sa);
    $id=mysql_insert_id();

}
else{
      $id=$rows->id;
}

?>