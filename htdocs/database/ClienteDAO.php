<?php

require_once __DIR__ . '/DBManager.php';
require_once __DIR__ . '/../entity/EntityCliente.php';

class ClienteDAO {
   /**
   * Recupera il cliente avente un certo username.
   * @param username username dell'utente
   * @return EntityCliente|null
   * @throws Exception
   */
    public static function readByUsername(string $username): ?EntityCliente {
        if($username === null)
            throw new Exception('nome utente non valido');
        
        try{
            $db = DBManager::getInstance()->getConnection();

            $stmt = $db->prepare(
                'SELECT * FROM clienti WHERE Username = :username'
            );

            $stmt->execute(['username' => $username]);
            $row = $stmt->fetch();
            if (!$row) return null;
            $cliente = new EntityCliente(
                $row['Username'],
                $row['Email'],
                $row['Password'],
                $row['CartaCredito']
            );

            return $cliente;            
        }catch(Exception $e){
            throw new Exception('Errore durante la lettura del cliente.');
        }

    }

   /**
   * Recupera il cliente avente una certa email.
   * @param email email del cliente
   * @return EntityCliente|null
   * @throws Exception
   */
    public static function readByEmail(string $email): ?EntityCliente {
        if($email === null)
            throw new Exception('email non valida');

        try{
            $db = DBManager::getInstance()->getConnection();

            $stmt = $db->prepare(
                'SELECT * FROM clienti WHERE Email = :email'
            );

            $stmt->execute(['email' => $email]);
            $row = $stmt->fetch();
            if (!$row) return null;
            $cliente = new EntityCliente(
                $row['Username'],
                $row['Email'],
                $row['Password'],
                $row['CartaCredito']
            );
            return $cliente;            
        }catch(Exception $e){
            throw new Exception('Errore durante la lettura del cliente.');
        }

    }

   /**
   * Salva un cliente.
   * @param cliente
   * @return EntityCliente
   * @throws Exception
   */
    public static function createCliente(EntityCliente $cliente): EntityCliente {
        if($cliente === null)
            throw new Exception('cliente non valido');

        try{
            $db = DBManager::getInstance()->getConnection();

            $stmt = $db->prepare(
                'INSERT INTO clienti (Username, Email, Password, CartaCredito)
                VALUES (:nome, :email, :password, :carta)'
            );

            $stmt->execute([
                'nome'     => $cliente->getNomeUtente(),
                'email'    => $cliente->getEmail(),
                'password' => $cliente->getPassword(),
                'carta'    => $cliente->getCartaDiCredito()
            ]);

            return $cliente;            
        }catch(Exception $e){
            throw new Exception('Errore durante il salvataggio del cliente.');
        }

    }

   /**
   * aggiorna la carta di credito di un cliente.
   * @param nomeUtente username del cliente
   * @param carta carta di credito
   * @throws Exception
   */
    public static function updateCartaDiCredito(string $nomeUtente, ?string $carta): void {
        if($nomeUtente === null || $carta === null)
            throw new Exception('nome utente o carta non valida');

        try{
            $db = DBManager::getInstance()->getConnection();

            $stmt = $db->prepare(
                'UPDATE clienti SET CartaCredito = :carta WHERE Username = :username'
            );
            
            $stmt->execute(['carta' => $carta, 'username' => $nomeUtente]);            
        }catch(Exception $e){
            throw new Exception('Errore durante l\'aggiornamento della carta di credito del cliente.');
        }

    }
}
?>