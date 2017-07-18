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
$userid=@$_GET['userid'];
$title=@$_GET['title'];
if($title == 'default') {
	$arr=array(
		'flat'=>'fail',
		'message'=>'默认分类不能修改!!!',
		'title'=>$title
	);
}else{
		//1. 获取该用户默认分类id
$sql_getDefault="select id from category where userid='$userid' and title='default'";
$result_getDefault = mysql_query($sql_getDefault);
$num_getDefault = mysql_affected_rows();
if($num_getDefault > 0) {
	$row = mysql_fetch_assoc($result_getDefault);
	$id_default = $row['id'];
	//echo $id_default;
	$sql_selectId="select id from category where userid='$userid' and title='$title'";
	$sql_deleteCategory = "delete from category where userid='$userid' and title='$title'";
	$result_selectId=mysql_query($sql_selectId);
	$num_selectId = mysql_affected_rows();
	if($num_selectId > 0){
		$row = mysql_fetch_assoc($result_selectId);
		$id_select = $row['id'];
		$sql_searchPic="select id from photograph where userid='$userid' and categoryid='$id_select'";
		$result_searchPic = mysql_query($sql_searchPic);
		$num_searchPic = mysql_affected_rows();
		if($num_searchPic > 0) {
			while($row = mysql_fetch_object($result_searchPic)) {
				$sql_editCategoryId = "update photograph set categoryid='$id_default' where id='$row->id'";
				mysql_query($sql_editCategoryId);
			}
			$arr=array(
					'flat'=>'success',
					'message'=>'删除分类成功!!!',
					'title'=>$title
			);
		}else{
			$result_delete = mysql_query($sql_deleteCategory);
			$num_delete = mysql_affected_rows();
			if($num_delete > 0) {
					$arr=array(
					'flat'=>'success',
					'message'=>'删除分类成功!!!',
					'title'=>$title
				);
			}else{
				$arr=array(
					'flat'=>'fail',
					'message'=>'删除分类失败!!!',
					'title'=>$title
				);
			}
		}
	}else {
		$arr=array(
			'flat'=>'fail',
			'message'=>'该分类不存在!!!!',
			'title'=>$title
		);
	}
}else{
	$arr=array(
        'flat'=>'fail',
        'message'=>'默认分类找不到！！！！',
        'title'=>$title
    );
}

}

 $arr['flat'] = urlencode($arr['flat']);
 $arr['message'] = urlencode($arr['message']);
 echo urldecode(json_encode($arr));

