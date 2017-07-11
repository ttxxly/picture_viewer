<?php
/**
 * Created by PhpStorm.
 * User: 鑫
 * Date: 2017/6/28
 * Time: 8:43
 */
//创建图片分类

//链接数据库
require dirname(__FILE__).'/conn.php';//转换成硬路径，速度更快


//获取数据
$id=$_GET['id'];   //图片id
$parentid=$_GET['parentid'];  //分类级别
$userid=$_GET['userid'];     //用户id
$title=$_GET['tital'];       //标题
$description=$_GET['description'];   //描述
$keywords=$_GET['keywords'];     //关键字
$time=time();      //当前时间戳
$datetime=date('Y-m-d-G-i-s',$time+28800);   //将时间戳转化为时间
////echo $datetime;
//
$sql="INSERT INTO 'add_category'('id','parentid','userid','tital','description') VALUES('$id','$parentid','$userid','$tital','$description')";
//$sql="insert into category(parentid,userid,title,description,keywords,datetime) values('7546','6521','呀呀','咦','keey','$datetime')";  //测试
@$result=mysql_query($sql);   //成功返回1 失败返回0
    if($result==1){
        $arr=array(
            'flat'=>'success',
            'message'=>'插入图片分类成功'
        );
    }
else{
    $arr=array(
        'flat'=>'fail',
        'message'=>'插入图片分类失败'
    );
}
foreach ($arr as $key => $value) {
    $arr[$key] = urlencode($value);
}
//返给客户端 JSON 数据
echo urldecode(json_encode($arr));

//关闭数据库链接  打开的非持久链接会在脚本执行完自动关闭，所以不需要使用mysql_close();

?>