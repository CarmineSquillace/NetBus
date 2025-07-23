<?php

require_once __DIR__ . '/../control/GestioneVenditeBigliettiNetbus.php';

class ClienteNonRegistrato {

    public static function registrazione(string $nome, string $email, string $password, string $passwordConfirm): EntityCliente {
        try{
            $ctrl = GestioneVenditeBigliettiNetbus::getInstance();
    
            // Chiamo la control per la registrazione
            return $ctrl->registrazione($nome, $email, $password, $passwordConfirm);
        }catch(Exception $e){
            throw $e;
        }
    }

    public static function autenticazione(string $email, string $password): EntityCliente{
        try{
            $ctrl = GestioneVenditeBigliettiNetbus::getInstance();
    
            // Chiamo la control per l'autenticazione
            return $ctrl->autenticazione($email, $password);
        }catch(Exception $e){
            throw $e;
        }
    }
}
?>