<?php

require_once __DIR__ . '/../control/GestioneVenditeBigliettiNetbus.php';

class ClienteRegistrato {

    public static function acquistaBiglietti(int $idCorsa, String $cartaCredito, int $numBiglietti): array{
        try{
            $ctrl = GestioneVenditeBigliettiNetbus::getInstance();
    
            // Chiamo la control per la l'acquisto dei biglietti
            return $ctrl->acquistaBiglietti($idCorsa, $cartaCredito, $numBiglietti);
        }catch(Exception $e){
            throw $e;
        }
    }

    public static function getCliente(): ?EntityCliente {
        try{
            $ctrl = GestioneVenditeBigliettiNetbus::getInstance();

            return $ctrl->getLoggedCliente();
        }catch(Exception $e){
            throw $e;
        }
    }
}
?>