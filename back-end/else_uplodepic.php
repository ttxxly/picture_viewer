<?php
/**
 * Created by PhpStorm.
 * User: 鑫
 * Date: 2017/7/4
 * Time: 11:06
 * 此文件为测试上传图片文件，与本项目无关
 */

function uploadfile($type='',$name,$ext,$size,$error,$tmp_name,$targetname,$upload_dir)
{
    $MAX_SIZE = 2000000;
    $FILE_MIMES = array('image/pjpeg','image/jpeg','image/jpg','image/gif','image/png');
    $FILE_EXTS = array('.jpg','.gif','.png','.JPG','.GIF','.PNG');
    $file_path = $upload_dir.$targetname;

    if(!is_dir($upload_dir))
    {
        if(!mkdir($upload_dir))
            die("文件上传目录不存在并且无法创建文件上传目录");
        if(!chmod($upload_dir,0755))
            die("文件上传目录的权限无法设定为可读可写");
    }

    if($size>$MAX_SIZE)
        die("上传的文件大小超过了规定大小");
    if($size == 0)
        die("请选择上传的文件");
    if(!in_array($type,$FILE_MIMES) || !in_array($ext,$FILE_EXTS))
        die("请上传符合要求的文件类型");
    if(!move_uploaded_file($tmp_name, $file_path))
        die("复制文件失败，请重新上传");
    switch($error)
    {
        case 0:
            return ;
        case 1:
            die("上传的文件超过了 php.ini 中 upload_max_filesize 选项限制的值");
        case 2:
            die("上传文件的大小超过了 HTML 表单中 MAX_FILE_SIZE 选项指定的值");
        case 3:
            die("文件只有部分被上传");
        case 4:
            die("没有文件被上传");
    }
}
//if(isset($_POST['post'])){
//    $pic=request.getParamete('upfile');
//    echo $pic;
//    echo "老子接收到了";
//}
uploadfile();
?>