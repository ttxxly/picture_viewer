<?php
/**
 * Created by PhpStorm.
 * User: 鑫
 * Date: 2017/6/27
 * Time: 14:10
 */
//链接数据库
$conn=mysql_connect("localhost","root","") or die("数据库连接错误".mysql_error());
mysql_select_db("photograph",$conn) or die("数据库访问错误".mysql_error());
mysql_query("SET NAMES UTF8");

//登陆
$nickname =@$_GET['nickname'];
$password =@$_GET['password'];
$mobile =@$_GET['mobile'];
//
//$nickname = '123';
//$password = '123';
//$mobile = '';
//

//echo $nickname.$password.$mobile."发热管如果";
$arr=array(
	'nickname1'=>$nickname,
	'password1'=>$password,
	'mobile1'=>$mobile
);


if (!empty($nickname)) {
    $sql = "select * from user where nickname='$nickname' and password='$password'";
    $result = mysql_query($sql);
    if($row=@mysql_fetch_object($result)){
		$arr=array(
			'flat'=>'success',
			'message'=>'登陆成功',
			'nickname'=>$row->nickname,
			'password'=>$row->password,
			'mobile'=>$row->mobile,
			'portrait'=>$row->portrait,
			'nickname1'=>$nickname,
			'password1'=>$password,
			'mobile1'=>$mobile
		);
    }
    else{
        $arr=array(
            'flat'=>'fail',
            'message'=>'登陆失败',
			'nickname1'=>$nickname,
			'password1'=>$password,
			'mobile1'=>$mobile
        );
    }
}else if(!empty($mobile)){
    $sql = "select * from user where mobile='$mobile' and password='$password'";
    $result = mysql_query($sql);
    if($row=@mysql_fetch_object($result)){
        $arr=array(
            'flat'=>'success',
            'message'=>'登陆成功',
            'nickname'=>$row->nickname,
            'password'=>$row->password,
            'mobile'=>$row->mobile,
            'portrait'=>$row->portrait,
			'nickname1'=>$nickname,
			'password1'=>$password,
			'mobile1'=>$mobile
        );
    }else{
        $arr=array(
            'flat'=>'fail',
            'message'=>'登陆失败',
			'nickname1'=>$nickname,
			'password1'=>$password,
			'mobile1'=>$mobile
        );
    }
}

echo(json_encode($arr));	//返给客户端 JSON 数据
?>
