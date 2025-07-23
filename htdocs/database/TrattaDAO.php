<?php

require_once __DIR__ . '/DBManager.php';
require_once __DIR__ . '/../entity/EntityTratta.php';

class TrattaDAO {
   /**
   * Recupera la tratta avente un certo id.
   * @param id id della tratta
   * @return EntityTratta|null
   * @throws Exception
   */
    public static function read(int $id): ?EntityTratta {
        if($id <= 0)
            throw new Exception('ID tratta non valido');

        try{
            $db = DBManager::getInstance()->getConnection();

            $stmt = $db->prepare('SELECT * FROM tratte WHERE ID = :id');

            $stmt->execute(['id' => $id]);
            $row = $stmt->fetch();
            if (!$row) return null;
            $tratta = new EntityTratta(
                $row['ID'], // ID Tratta
                $row['Partenza'], // ID Citta Partenza
                $row['Destinazione'] //ID Città Destinazione
            );

            return $tratta;            
        }catch(Exception $e){
            throw new Exception('Errore durante la lettura della tratta.');
        }
    }
}
?>