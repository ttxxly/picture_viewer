<?php
/**
 * Created by PhpStorm.
 * User: ��
 * Date: 2017/6/27
 * Time: 14:10
 */
//�������ݿ�
$conn=mysql_connect("localhost","root","") or die("���ݿ����Ӵ���".mysql_error());
mysql_select_db("photograph",$conn) or die("���ݿ���ʴ���".mysql_error());
mysql_query("SET NAMES UTF8");

//��½
$nickname =@$_GET['nickname'];
$password =@$_GET['password'];
$mobile =@$_GET['mobile'];
//
//$nickname = '123';
//$password = '123';
//$mobile = '';
//

//echo $nickname.$password.$mobile."���ȹ����";
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
			'message'=>'��½�ɹ�',
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
            'message'=>'��½ʧ��',
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
            'message'=>'��½�ɹ�',
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
            'message'=>'��½ʧ��',
			'nickname1'=>$nickname,
			'password1'=>$password,
			'mobile1'=>$mobile
        );
    }
}

echo(json_encode($arr));	//�����ͻ��� JSON ����
?>
