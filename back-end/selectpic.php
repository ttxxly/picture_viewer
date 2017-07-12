
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

//$userid = @$_GET['userid'];
//$keys = @$_GET['keys'];
$userid ="6";
$keys="portrait";

$i = '0';
if (!empty($keys) && !empty($userid)) {
    $sql = "select * from photograph where keywords='$keys' and userid='$userid' ";
} else if(!empty($userid) && empty($keys)) {
    $sql = "select * from photograph where userid='$userid' ";
}else if (empty($keys) && empty($userid)){
    $sql = "select * from photograph";
}
$result = mysql_query($sql);
if ($result && $userid) {

    while ($row = mysql_fetch_object($result)) {
        $res[$i] = array(
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
    $len = $i;
    while ($len-- > 0) {
        foreach ($res[$len] as $key => $value) {
            $res[$len][$key] = urlencode($value);
        }
    }
    $arr = array(
        'flat' => 'success',
        'message' => '搜索成功',
        'photos' => $res,
        'count' => $i
    );
} else {
    $arr = array(
        'flat' => 'fail',
        'message' => '没有搜到相关信息',
        'count' => $i
    );
}

$arr['flat'] = urlencode($arr['flat']);
$arr['message'] = urlencode($arr['message']);
$arr['count'] = urlencode($arr['count']);



//返给客户端 JSON 数据
echo urldecode(json_encode($arr));
?>
