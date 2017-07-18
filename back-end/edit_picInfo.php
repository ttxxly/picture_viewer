<?php
/**
 * Created by PhpStorm.
 * User: 鑫
 * Date: 2017/7/3
 * Time: 14:04
 * 搜索图片
 */

//链接数据库
require dirname(__FILE__) . '/conn.php';//转换成硬路径，速度更快


$id=$_GET['id'];
$title=$_GET['title'];
$keys=$_GET['keyword'];
$description=$_GET['description'];
$category=$_GET['category'];
$userid=$_GET['userid'];

$sql1="select id from category where title='$category' and userid='$userid'";
$result=mysql_query($sql1);
$cs=mysql_affected_rows();
if($cs>0){
    $row1=mysql_fetch_assoc($result);
    $cateid=$row1['id'];
    $sql2="update photograph set title='$title',categoryid='$cateid',description='$description',keywords='$keys' where id='$id'";
    $result1=mysql_query($sql2);
    $num=mysql_affected_rows();
    if($num>=0){
        $arr = array(
            'flat' => 'success',
            'message' => '更新图片信息成功'
        );
    }else {
        $arr = array(
            'flat' => 'fail',
            'message' => '更新图片信息失败'
        );
    }
}
else{
    $arr = array(
        'flat' => 'fail',
        'message' => '不存在此id'
    );
}

$arr['flat'] = urlencode($arr['flat']);
$arr['message'] = urlencode($arr['message']);

//返给客户端 JSON 数据
echo urldecode(json_encode($arr));
