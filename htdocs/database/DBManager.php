<?php
/**
 * Classe per la gestione della connessione al database.
 */
class DBManager {
    private static ?DBManager $instance = null;
    private PDO $conn;

    private function __construct() {
        $host = 'localhost';
        $db   = 'netbus';
        $user = 'root';
        $pass = '';
        $dsn  = "mysql:host=$host;dbname=$db;charset=utf8mb4";
        $options = [
            PDO::ATTR_ERRMODE            => PDO::ERRMODE_EXCEPTION,
            PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
        ];
        
        try{
            $this->conn = new PDO($dsn, $user, $pass, $options);
        }catch(Exception $e){
            throw new Exception('errore nella connesione al database');
        }
    }

    public static function getInstance(): DBManager {
        if (self::$instance === null)
            self::$instance = new DBManager();
        
        return self::$instance;
    }   

    public function getConnection(): PDO {
        return $this->conn;
    }

    // non è strettamente necessario definire un metodo esplicito per la chiusura della connessione al db
    // poichè quando lo script PHP termina, l'oggetto PDO viene distrutto e la connessione viene chiusa 
    // automaticamente dal garbage collector
}
?>