﻿<?php
//链接数据库///qwq
require dirname(__FILE__).'/conn.php';//转换成硬路径，速度更快
//$username=$_REQUEST['username'];
//$message=$_REQUEST['message'];
$file=$_FILES['myfile']['tmp_name'];  //文件被上传后在服务端储存的临时文件地址
$user_path=$_SERVER['DOCUMENT_ROOT']."/picture_viewer/photos/photos/";  //上传图片放置地址  $_SERVER['DOCUMENT_ROOT']==C:/wamp/www/
//$user_path=iconv("utf-8", "gb2312", $user_path);
$fileadd=$user_path."/".$_FILES['myfile']['name'];  //上传文件保存后的地址
//ar_dump($_FILES['myfile']);
//echo "<br/>";
if(is_uploaded_file($file)){
    if(!file_exists($user_path)){
        mkdir($user_path);
    }
    if(move_uploaded_file(iconv("utf-8", "gb2312", $file), iconv("utf-8", "gb2312", $fileadd))){
        echo /*$_FILES['myfile']['name'].*/"上传成功！";
    }else
        echo "上传失败";
}

$image='/photos/photos/'.$_FILES['myfile']['name'];
$time=time();      //当前时间戳
//将时间戳转化为时间格式，28800是八个小时，因为东八区
$datetime=date('Y-m-d-G-i-s',$time+28800);  
$title=$_POST['title'];//'999';//
$description=$_POST['description'];//'999'; //
$keywords=$_POST['keywords'];  //'999';   //
$userid=$_POST['userid'];  //'999';//
$sqll="select id from category where userid='$userid' and title='default'";
$resultt=mysql_query($sqll);
$rows=mysql_fetch_object($resultt);
if(!$rows) {
    $sa="insert into category(userid,title,description,keywords,datetime) 
	values('$userid','default','default','default','$datetime')";
    mysql_query($sa);
    $categoryid=mysql_insert_id();

}
else{
    $categoryid=$rows->id;
}



//$sql="update photograph set datetime='$datetime' description='$
//description' keywords='$keywords' image='$image' categoryid='$categoryid' icon='$icon'";
$sql="insert into photograph(categoryid,title,description,keywords,image,userid,datetime) 
values('$categoryid','$title','$description','$keywords','$image','$userid','$datetime')";
$result=mysql_query($sql);
if($result)
{
    $arr=array(
        'flat'=>'success',
        'message'=>'添加图片成功'
    );
}
else {
    $arr=array(
        'flat'=>'fail',
        'message'=>'添加失败'
    );
}
foreach ($arr as $key => $value) {
    $arr[$key] = urlencode($value);
}
//返给客户端 JSON 数据
echo urldecode(json_encode($arr));
?>
