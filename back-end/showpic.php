<?php
/**
 * Created by PhpStorm.
 * User: 鑫
 * Date: 2017/7/12
 * Time: 16:41
 *
 * 用户根据分类title查找图片
 */

$title=$_GET['title'];
$userid=$_GET['userid'];

if(empty($userid)||empty($title)){
    echo "用户id和标题都不能为空";
}
else{
    $sql="select ";
}
