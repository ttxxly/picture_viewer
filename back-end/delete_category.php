<?php
/**
 * Created by PhpStorm.
 * User: 鑫
 * Date: 2017/6/28
 * Time: 10:42
 */

//修改分类

//链接数据库
require dirname(__FILE__).'/conn.php';//转换成硬路径，速度更快

//获取数据
$id=$_GET['id'];   //图片id
//$id=2;

$sql="delete from category where id='$id'";
//$sql="delete from category where id='1'";  //测试
$result=mysql_query($sql);
if($result==1){
    $arr=array(
        'flat'=>'success',
        'message'=>'删除图片分类成功',
        'id'=>$id
    );
}
else{
    $arr=array(
        'flat'=>'fail',
        'message'=>'删除图片分类失败',
        'id'=>$id
    );
}
$strr=json_encode($arr);
echo($strr);

