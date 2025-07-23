<?php

require_once __DIR__ . '/DBManager.php';
require_once __DIR__ . '/../entity/EntityCorsa.php';

class CorsaDAO {
   /**
   * Recupera la corsa avente un certo id.
   * @param id id della corsa
   * @return EntityCorsa|null
   * @throws Exception
   */
    public static function readById(int $id): ?EntityCorsa {
        if($id <= 0)
            throw new Exception('ID corsa non valido');

        try{
            $db = DBManager::getInstance()->getConnection();

            $stmt = $db->prepare(
                'SELECT * FROM corse WHERE ID = :id'
            );
            $stmt->execute(['id' => $id]);
            $row = $stmt->fetch();
            if (!$row) return null;
            return new EntityCorsa(
                (int)$row['ID'],
                (int)$row['Tratta'],
                new DateTime($row['Data']),
                new DateTime($row['OrarioPartenza']),
                new DateTime($row['OrarioArrivo']),
                (float)$row['Costo'],
                (int)$row['Autobus']
            );            
        }catch(Exception $e){
            throw new Exception('Errore durante la lettura della corsa.');
        }

    }

   /**
   * Recupera tutte le corse disponibili in programmazione.
   * @return EntityCorsa[]
   * @throws Exception
   */
    public static function findUpcoming(): array {
        try{
            $db = DBManager::getInstance()->getConnection();

            $stmt = $db->query(
                'SELECT * FROM corse WHERE Data >= CURDATE()'
            );

            $list = [];
            while ($row = $stmt->fetch()) {
                $list[] = new EntityCorsa(
                    (int)$row['ID'], //ID corsa
                    (int)$row['Tratta'], //ID tratta
                    new DateTime($row['Data']),
                    new DateTime($row['OrarioPartenza']),
                    new DateTime($row['OrarioArrivo']),
                    (float)$row['Costo'],
                    (int)$row['Autobus'] //ID Autobus
                );
            }
            return $list;            
        }catch(Exception $e){
            throw new Exception('Errore durante la lettura delle corse.');
        }
    }
}
?>