<?php

require_once __DIR__ . '/DBManager.php';
require_once __DIR__ . '/../entity/EntityAutobus.php';

class AutobusDAO {
  /**
   * Recupera l'autobus associato a una corsa specificata.
   * @param idCorsa id della corsa
   * @return EntityAutobus|null
   * @throws Exception
   */
    public static function readAutobusPerCorsa(int $idCorsa): ?EntityAutobus {
        if($idCorsa <= 0)
            throw new Exception('ID corsa non valido');

        try{
            $db = DBManager::getInstance()->getConnection();

            $stmt = $db->prepare(
                'SELECT a.ID as ID, Capienza FROM autobus a INNER JOIN corse c ON a.ID=c.Autobus WHERE c.ID = :id'
            );

            $stmt->execute(['id' => $idCorsa]);
            $row = $stmt->fetch();
            if (!$row) return null;
            $autobus = new EntityAutobus(
                $row['ID'], // ID autobus
                $row['Capienza'] // Capienza autobus
            );

            return $autobus;
        }catch(Exception $e){
            throw new Exception('Errore durante la lettura dell\'autobus.');
        }
    }
}
?>