<?php
/**
 * Created by PhpStorm.
 * User: 鑫
 * Date: 2017/7/12
 * Time: 15:46
 *
 * 所有分类
 *
 */

//链接数据库
require dirname(__FILE__).'/conn.php';//转换成硬路径，速度更快

$userid='5';//$_GET['userid'];
$sql="select title from category where userid='$userid'";
$result=mysql_query($sql);
$i=0;
while($row=mysql_fetch_assoc($result))
{
    // echo "$row[image]<br/>";
    $res[$i]=$row['title'];
    $i++;
}
for($j=0;$j<$i;$j++){
    $res[$j] = urlencode($res[$j]);
}
$arr=array(
    'flat'=>'success',
    'message'=>'搜索分类成功',
    'arra'=>$res
);
$arr['flat'] = urlencode($arr['flat']);
$arr['message'] = urlencode($arr['message']);


echo urldecode(json_encode($arr));
//print_r($res);

?>

