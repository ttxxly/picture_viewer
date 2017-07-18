<?php
/**
 * Created by PhpStorm.
 * User: 鑫
 * Date: 2017/7/12
 * Time: 16:41
 *
 * 用户根据分类title查找图片
 步骤：传入category表的title和 userid
 *     根据category表的title和 userid查找得到分类id
 *    根据分类id在photograph表查找图片详细信息，并返回
 */
//链接数据库
require dirname(__FILE__).'/conn.php';//转换成硬路径，速度更快

$title=$_GET['title'];
$userid=$_GET['userid'];

$res=null;
$i = '0';

if(empty($userid)||empty($title)){
    $arr = array(
        'flat' => 'fail',
        'message' => '输入错误，请检查title和userid'
    );
}
else {
    $sql = "select id from category where title='$title' and userid='$userid'";
    $result = mysql_query($sql);
	$number = mysql_affected_rows();
//    @$cs=mysql_fetch_array($result);
    if ($number > 0) {

        $row = mysql_fetch_assoc($result);
        $categoryid = $row['id'];
        $sqll = "select * from photograph where categoryid='$categoryid'";
        $result1 = mysql_query($sqll);
		$num = mysql_affected_rows();
        if ($num > 0) {
            while ($row = mysql_fetch_object($result1)) {
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
                'photos' => $res
            );
		}
		else{
            $arr = array(
                'flat' => 'fail',
                'message' => '查找失败'
            );
        }
    }
    else {
        $arr = array(
            'flat' => 'fail',
            'message' => '搜索失败,不存在该分类'
        );
    }
}
$arr['flat'] = urlencode($arr['flat']);
$arr['message'] = urlencode($arr['message']);



//返给客户端 JSON 数据
echo urldecode(json_encode($arr));
    ?>
