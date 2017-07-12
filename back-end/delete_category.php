<?php
/**
 * Created by PhpStorm.
 * User: 鑫
 * Date: 2017/6/28
 * Time: 10:42
 *
 * 删除分类
 *    获取分类title和userid
 *      删除分类，返回删除的分类title
 */



//链接数据库
require dirname(__FILE__).'/conn.php';//转换成硬路径，速度更快

//获取数据
$userid=$_GET['userid'];
$title=$_GET['tite'];

$sql="delete from category where userid='$userid' and title='$title'";
$result=mysql_query($sql);

if($result==1){
    $arr=array(
        'flat'=>'success',
        'message'=>'删除图片分类成功',
        'title'=>$title
    );
}
else{
    $arr=array(
        'flat'=>'fail',
        'message'=>'删除图片分类失败',
        'title'=>$title
    );
}
$arr['flat'] = urlencode($arr['flat']);
$arr['message'] = urlencode($arr['message']);
echo urldecode(json_encode($arr));

