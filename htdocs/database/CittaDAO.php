<?php

require_once __DIR__ . '/DBManager.php';
require_once __DIR__ . '/../entity/EntityCitta.php';

class CittaDAO {
   /**
   * Recupera la città avente un certo id.
   * @param id id della città
   * @return EntityCitta|null
   * @throws Exception
   */
    public static function read(int $id): ?EntityCitta {
        if($id <= 0)
            throw new Exception('ID città non valido');
        
        try{
            $db = DBManager::getInstance()->getConnection();

            $stmt = $db->prepare(
                'SELECT * FROM citta WHERE ID = :id'
            );

            $stmt->execute(['id' => $id]);
            $row = $stmt->fetch();
            if (!$row) return null;
            $citta = new EntityCitta(
                $row['ID'], // ID Città
                $row['Nome'] // Nome Città
            );

            return $citta;            
        }catch(Exception $e){
            throw new Exception('Errore durante la lettura della città.');
        }

    }

}
?>