<?php
require_once __DIR__ . '/control/GestioneVenditeBigliettiNetbus.php';

$ctrl = GestioneVenditeBigliettiNetbus::getInstance();

// Se non loggato, mando alla login
if (!$ctrl->getLoggedCliente()) {
    header('Location: login.php');
    exit;
}

$biglietto = $ctrl->getBiglietto($_POST['id_biglietto']);

// Contenuto del file
$contenuto = "== BIGLIETTO NETBUS ==\nBiglietto ID: " . $biglietto->getIdBiglietto() . "\nCorsa ID: " . $biglietto->getIdCorsa() . "\nData Emissione: " . $biglietto->getDataEmissione()->format('Y/m/d') . ($biglietto->getIdClienteRegistrato() !== null ? "\nEmesso da Cliente Registrato: " . $biglietto->getIdClienteRegistrato() : "\nEmesso da Impiegato: " . $biglietto->getIdImpiegato()) . "\nPrezzo: " . $biglietto->getPrezzoVendita() . "€\n";

$name = "netbus_biglietto_" . $biglietto->getIdBiglietto() . ".txt";

// Impostazioni header per forzare il download
header('Content-Type: text/plain');
header('Content-Disposition: attachment; filename="' . $name . '"');
header('Content-Length: ' . strlen($contenuto));

// Stampa il contenuto (che diventa il contenuto del file scaricato)
echo $contenuto;
exit;
