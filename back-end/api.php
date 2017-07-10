<?php
/**
 * Created by PhpStorm.
 * User: 鑫
 * Date: 2017/6/26
 * Time: 20:56
 */



//$sqll="INSERT INTO `user` (`nickname`, `password`, `mobile`, `portrait`) VALUES('nn','mm','666','')";
$sql = "select * from user where nickname='nn'";
//mysql_query($sqll);
$result = mysql_query($sql);
$row=@mysql_fetch_object($result);
//print_r($row);
echo "$row->nickname";
echo "$row->password";
echo "$row->mobile";
echo "$row->protrait";
//登陆
//$action=@$_GET['action'];
//if($action=='login') {
//    $nickname ="as"; //@$_POST['nickname'];
//    $password ="xiaohei"; //@$_POST['password'];
////    $mobile = @$_POST['mobile'];
    if (empty($nickname)) {
        $sql = "select * from user where mobile='$mobile',password='$password'";
        $result = mysql_query($sql);
        $row=@mysql_fetch_array($result);
        echo "sssss";
    }
    elseif(empty($mobile)){
        $sql = "select * from user where nickname='$nickname',password='$password''";
        $result = mysql_query($sql);
        $row=@mysql_fetch_row($result);
        print_r($row);
    }
////}
//注册
//elseif($action=='regist'){
//    $id=@$_POST['id'];
//    $nickname=@$_POST['nickname'];
//    $password=@$_POST['password'];
//    $mobile=@$_POST['mobile'];
//    $portrait=@$_POST['portrait'];
//    $sql1="select nickname from user where $nickname=nickname";
//    $sql2="select mobile from user where $nickname=mobile";
//    if(!empty(mysql_query($sql1))){
//
//    }
//    elseif(!empty(mysql_query($sql2))){
//
//    }
//    else{
//        $sql="INSERT INTO `user` (`nickname`, `password`, `mobile`, `portrait`) VALUES($nickname,$password,$mobile,$portrait)";
//        mysql_query($sql);
//    }
//
//
//
//    echo "ok";
//}
////退出
//elseif($action=='login_out'){
//
//}
//
////添加分类接口
//elseif($action=='add_category'){
//
//}
//
////修改分类接口
//elseif($action=='update_category'){
//
//}
//
////删除分类接口
//elseif($action=='delete_category'){
//
//}
//
////按用户获取分类接口
//elseif($action=='get_category_list_byuserid'){
//$sql="select id,title,keywords,description,parentid,userid from category where userid='$userid'";
//$result=mysql_query($sql);
//while($row=mysql_fetch_row($result)){
//list($id,$title,$keywords,$description,$parentid,$userid)=$row;
//$arr=array(
//        'flat'=>'success',
//        'message'=>'删除图片分类成功',
//        'data'=>array(
//                    'id'=>$id,
//                    'title'=>$title,
//                    'keywords'=>$keywords,
//                    'description'=>$description,
//                    'parentid'=>$parentid,
//                    'userid'=>$userid
//)
//    );
//}
//}
//
////获取所有一级分类接口
//elseif($action=='get_category_first_list'){
//
//}
//
////获取子分类接口
//elseif($action=='get_category_sub_list'){
//
//}
//
////上传图片接口
//elseif($action=='uploade_photograph'){
//
//}a
//
////发表图片
//elseif($action=='add_photograph'){
//
//}
//
////获取缩略图接口
//elseif($action=='get_photograph_list'){
//
//}
//
////更新图片接口
//elseif($action=='update_photograph'){
//
//}
//
////删除图片接口
//elseif($action=='delete_user_photograph'){
//
//}
//
////获取图片详情接口
//elseif($action=='get_photograph_details'){
//
//}
//
////获取用户所有图片
//elseif($action=='get_user_photograph'){
//
//}
//else{
//
//}
//
//


//
?>