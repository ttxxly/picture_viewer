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

$userid = @$_GET['userid'];
$keys = @$_GET['keys'];
//$userid ="6";
//$keys="portrait";

$i = '0';
if (!empty($keys) && !empty($userid)) {
    $sql = "select * from photograph where keywords='$keys' and userid='$userid' ";
} else if(!empty($userid) && empty($keys)) {
    $sql = "select * from photograph where userid='$userid' ";
}else if (empty($keys) && empty($userid)){
    $sql = "select * from photograph";
}else if(empty($userid) && !empty($keys)) {
	$sql = "select * from photograph where keywords='$keys' ";
}
$result = mysql_query($sql);
if ($result) {
	$flag = false;
	$ca11 = '';
    while ($row = mysql_fetch_object($result)) {
		
		$sqllll = "select title from category where id='$row->categoryid'";
		$result111 = mysql_query($sqllll);
		if($result111 != false){
			$ca  = mysql_fetch_assoc($result111);
			$ca11 = $ca['title'];
		}
		$flag = true;
        $res[$i] = array(
            'photoid' => $row->id,
            'categoryid' => $ca11,
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
	if($flag == true) {
		$arr = array(
			'flat' => 'success',
			'message' => '搜索成功',
			'photos' => $res,
			'count' => $i
		);
	}else {
    $arr = array(
        'flat' => 'fail',
        'message' => '没有搜到相关信息',
        'count' => $i
    );
}
    
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