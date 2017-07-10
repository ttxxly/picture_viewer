
<?php
//$username=$_REQUEST['username'];
//$message=$_REQUEST['message'];
$file=$_FILES['myfile']['tmp_name'];  //文件被上传后在服务端储存的临时文件地址
$user_path=$_SERVER['DOCUMENT_ROOT']."photograph/upload/";  //上传图片放置地址  $_SERVER['DOCUMENT_ROOT']==C:/wamp/www/
//$user_path=iconv("utf-8", "gb2312", $user_path);
$fileadd=$user_path."/".$_FILES['myfile']['name'];  //上传文件保存后的地址
//var_dump($_FILES['myfile']);
echo "./upload".$_FILES['myfile']['name'];
//echo "<br/>";
if(is_uploaded_file($file)){
    if(!file_exists($user_path)){
        mkdir($user_path);
    }
    if(move_uploaded_file(iconv("utf-8", "gb2312", $file), iconv("utf-8", "gb2312", $fileadd))){
        echo $_FILES['myfile']['name']."上传成功！";
    }else
        echo "上传失败";
}
?>