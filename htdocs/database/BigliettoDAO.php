<?php

require_once __DIR__ . '/DBManager.php';
require_once __DIR__ . '/../entity/EntityBiglietto.php';

class BigliettoDAO {
   /**
   * Recupera il biglietto avente un certo id.
   * @param id id del biglietto
   * @return EntityBiglietto|null
   * @throws Exception
   */
    public static function readById(int $id): ?EntityBiglietto {
        if($id <= 0)
            throw new Exception('ID Biglietto non valido');

        try{
            $db = DBManager::getInstance()->getConnection();

            $stmt = $db->prepare(
                'SELECT * FROM biglietti WHERE ID = :id'
            );
            $stmt->execute(['id' => $id]);
            $row = $stmt->fetch();
            if (!$row) return null;
            return new EntityBiglietto(
                (int)$row['ID'], // ID biglietto
                (int)$row['Corsa'], // ID Corsa
                (float)$row['Prezzo'], // Prezzo
                new DateTime($row['Data']), // Data
                $row['Cliente'], // Nome utente
                (int)$row['Impiegato']  // ID Impiegato
            );            
        }catch(Exception $e){
            throw new Exception('Errore durante la lettura del biglietto.');
        }

    }

   /**
   * Recupera i biglietti acquistati da un cliente.
   * @param nomeUtente username del cliente
   * @return EntityBiglietto[]
   * @throws Exception
   */
    public static function readByCliente(String $nomeUtente): array {
        if($nomeUtente === null)
            throw new Exception('nome utente non valido');

        try{
            $db = DBManager::getInstance()->getConnection();

            $stmt = $db->prepare(
                'SELECT * FROM biglietti WHERE Cliente = :cid'
            );
            $stmt->execute(['cid' => $nomeUtente]);
            $list = [];
            while ($row = $stmt->fetch()) {
                $list[] = new EntityBiglietto(
                    (int)$row['ID'], // ID Biglietto
                    (int)$row['Corsa'], // ID Corsa
                    (float)$row['Prezzo'], // Prezzo di emissione del biglietto
                    new DateTime($row['Data']), // Data di emissione del biglietto
                    (int)$row['Impiegato'], // ID Impiegato
                    (int)$row['Cliente'] // Nome Utente Cliente
                );
            }
            return $list;            
        }catch(Exception $e){
            throw new Exception('Errore durante la lettura del biglietto.');
        }

    }

   /**
   * Salva un biglietto.
   * @param biglietto biglietto da salvare
   * @return EntityBiglietto
   * @throws Exception
   */
    public static function createBiglietto(EntityBiglietto $biglietto): EntityBiglietto {
        if($biglietto === null)
            throw new Exception('biglietto non valido');

        try{
            $db = DBManager::getInstance()->getConnection();

            $stmt = $db->prepare(
                'INSERT INTO biglietti (Corsa, Prezzo, Data, Impiegato, Cliente)
                VALUES (:corsa, :prezzo, :data, :impiegato, :cliente)'
            );

            $stmt->execute([
                'corsa'    => $biglietto->getIdCorsa(),
                'prezzo'   => $biglietto->getPrezzoVendita(),
                'data'     => $biglietto->getDataEmissione()->format('Y-m-d H:i:s'),
                'impiegato'=> $biglietto->getIdImpiegato(),
                'cliente'  => $biglietto->getIdClienteRegistrato()
            ]);
            $biglietto->setIdBiglietto((int)$db->lastInsertId());

            return $biglietto;            
        }catch(Exception $e){
            throw new Exception('Errore durante il salvataggio del biglietto.');
        }

    }

   /**
   * Restituisce il numero di biglietti emessi per una data corsa.
   * @param id id della corsa
   * @return int|null
   * @throws Exception
   */
    public static function bigliettiPerCorsa(int $id): ?int {
        if($id <= 0)
            throw new Exception('ID Corsa non valido');
        
        try{
            $db = DBManager::getInstance()->getConnection();

            $stmt = $db->prepare(
                'SELECT COUNT(ID) AS tot FROM biglietti WHERE Corsa = :id'
            );

            $stmt->execute(['id' => $id]);
            $row = $stmt->fetch();
            if (!$row) return null;

            return $row['tot'];            
        }catch(Exception $e){
            throw new Exception('Errore durante la lettura del biglietto.');
        }

    }
}
?>