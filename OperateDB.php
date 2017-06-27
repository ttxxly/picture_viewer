<?php

/**
 * @author phpadmin
 * @copyright 2017
 */

class DB{
    static $server = 'localhost';
    static $user = 'root';
    static $password = '';
    static $port = '3306';
    
    static $charset = 'utf8';
    
    static $database = 'photodb';
    private static $link;
    private static $result;
    
    public static function connect(){
        if(empty(self::$database) 
            || empty(self::$server)
            || empty(self::$user)) {
          return false;  
        }
        
        if(empty(self::$port)) self::$port = '3306';
        
        self::$server = self::$port == '3306' ? self::$server : self::$server.':'.self::$port;
        self::$link = mysql_connect(self::$server,self::$user,self::$password);
        mysql_select_db(self::$database,self::$link);
        mysql_query('SET NAMES '.self::$charset); 
    }
    
    public static function query($sql){
        if(empty(self::$link)) self::connect();
        
        $result = mysql_query($sql,self::$link);
        
        if(!stristr($sql,'select')){// ²éÑ¯Óï¾ä
            $result = mysql_affected_rows(self::$link);
        }
        
        self::close();
        
        return $result;
    }
    
    public static function close(){
        if(!empty(self::$result)){
            mysql_free_result(self::$result);
        }
        
        if(!empty(self::$link)){
            mysql_close(self::$link);
        }
    }    
}

?>