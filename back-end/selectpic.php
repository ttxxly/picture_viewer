<?php
/**
 * Created by PhpStorm.
 * User: 鑫
 * Date: 2017/7/3
 * Time: 14:04
 * 搜索图片
 */

//链接数据库
require dirname(__FILE__).'/conn.php';//转换成硬路径，速度更快

$userid ="33";// @$_GET['userid'];
$keys="";//@$_GET['keys'];

$i = '0';
if(!empty($keys)) {
    $sql = "select * from photograph where keywords='$keys' and userid='$userid' ";
}
else{
    $sql = "select * from photograph where and userid='$userid' ";
}
$result = mysql_query($sql);
if ($result && $keys && $userid) {
    $arr = array(
        'flat' => 'success',
        'message' => '搜索成功',
    );
    while ($row = mysql_fetch_object($result)){
        $arr['photos'.$i]= array(
            'photoid' => $row->id,
            'categoryid' => $row->categoryid,
            'userid' => $row->userid,
            'title' => $row->title,
            'keyword' => $row->keywords,
            'description' => $row->description,
            'url' => $row->image,
            'datetime' => $row->datetime
        );
        $i++;
    }
    $arr['count'] = $i;
}else {
    $arr = array(
        'flat' => 'fail',
        'message' => '没有搜到相关信息',
        'count' => $i
    );
}

$arr['flat'] = urlencode($arr['flat']);
$arr['message'] = urlencode($arr['message']);
$arr['count'] = urlencode($arr['count']);

$len = $arr['count'];

while($len-- > 0) {
    foreach ($arr['photos'.$len] as $key => $value) {
        $arr['photos'.$len][$key] = urlencode($value);
    }
}

//返给客户端 JSON 数据
echo urldecode(json_encode($arr));
?>