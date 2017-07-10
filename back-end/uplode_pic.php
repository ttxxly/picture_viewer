<html>
<head>
    <title>上传文件</title>
</head>
<body border="1" bgcolor="#ffebcd">
<form action="" method="post">
用户名:<input type="text" size="12" value="用户名"><br/>
密&nbsp;码:<input type="password" size="12"><br/>
上传文件:<input type="file" size="11" name="wj">
    <br/>
    <input type="submit" name="posttj" value="提交">
    <input type="reset" value="取消">
    </form>
</body>
</html>
<?php
define('NO_FILE', '不存在上传文件');
define('NOT_ALLOW_EXT', '文件类型不在允许范围内');
define('NOT_ALLOW_SIZE', '文件大小不在允许范围内');
define('HAS_THE_FILE', '该文件已经存在');
define('UPLOAD_FAILED', '上传失败');
define('UPLOAD_SUCCESS', '上传成功');
if(isset($_POST['posttj'])){
    $wj=$_POST['wj'];
    echo $wj;
}

class file_uploader
{
    var $_file;
    var $_filesize;
    var $_fileext;
    var $_filedir;
    var $_filename;
    var $_filetmpname;

    var $allowsize;
    var $allowext;

    var $neednewname;
    var $newname;
    var $syslang;

    var $report;

    function ready($filedir = '', $file, $allowsize = '', $allowext = '', $neednewname = false, $report = 0){
        $this->_filedir = is_dir($filedir) ? $filedir : '';
        if(empty($file) || !isset($file['size']) || $file['size'] == 0) $this->error(NO_FILE);
        $this->_filesize = $file['size'];
        $this->_filename = $file['name'];
        $this->_filetmpname = $file['tmp_name'];

        $this->allowsize = $allowsize;
        $this->allowext = $allowext;

        $this->neednewname = ($neednewname) ? true : false;
        $this->newname = '';

        $this->report = $report;
    }

    function do_upload(){
        if(!is_uploaded_file($this->_filetmpname)) $this->error(NO_FILE);
        if($this->chk_ext()){
            $this->error(NOT_ALLOW_EXT);
            return '';
        }
        if($this->chk_size()){
            $this->error(NOT_ALLOW_SIZE);
            return '';
        }
        if($this->neednewname) $this->newname = $this->generate_name().".".$this->get_fileext();
        if($this->chk_hasfile()){
            $this->error(HAS_THE_FILE);
            return '';
        }
        $filename = empty($this->newname) ? @iconv('utf-8','gb2312',$this->_filename) : $this->newname;
        @chmod($this->_filedir.$filename, 0777);
        if(move_uploaded_file($this->_filetmpname, $this->_filedir.$filename)){
            return $this->result();
        }else{
            $this->error(UPLOAD_FAILED);
            return '';
        }
    }

    function chk_ext(){
        if(empty($this->allowext) || in_array($this->get_fileext(), explode("|",$this->allowext))) return false;
        return true;
    }

    function chk_size(){
        if(empty($this->allowsize) || get_filesize <= $this->allowsize*1024*1024) return false;
        return true;
    }

    function get_filesize(){
        return $this->_filesize;
    }

    function get_fileext(){
        return substr($this->_filename,strrpos($this->_filename,".")+1);
    }

    function generate_name(){
        return substr(md5(time()),26);
    }

    function chk_hasfile(){
        return is_file($this->_filedir.$this->_filename);
    }

    function error($tip){
        echo $tip;
    }

    function result(){
        if($this->report){
            $filename = empty($this->newname) ? $this->_filename : $this->newname;
            $arr = array('filename' => $filename, 'filesize' => $this->_filesize, 'tip' => UPLOAD_SUCCESS);
            return $arr;
        }else{
            return UPLOAD_SUCCESS;
        }
    }
}
/***使用方法与参数说明***/
/***
第一个参数$dir 为上传文件存放的路径
第二个参数为$_FILES 为你那个上传文件变量
第三个参数允许文件大小 单位为MB
第四个参数允许的文件类型 格式为jpg|png|gif
第五个参数是否需要生成新的文件名
第六个参数为返回的提示格式 0为直接提示上传正确 1则返回一个数组array('filename' => $filename, 'filesize' => $this->_filesize, 'tip' => UPLOAD_SUCCESS);
 ***/
//require("类文件");
//$u = new file_uploader;
//$u->ready($dir, $_FILES['upload_file'], false, false, true, 0);
//echo $u->do_upload();

$dir = './picture';
//require("upload_class.php");
$u = new file_uploader;
$u->ready($dir,'C:\Users\鑫\Pictures\美女.jpg', false, false, true, 0);
echo $u->do_upload();
?>


