<!DOCTYPE html>
<html lang="it">

<head>
    <meta charset="utf8">
    <title>I miei biglietti</title>

    <!-- CSS -->
    <link rel="stylesheet" href="style/style.css" type="text/css">

    <!-- Bootstrap v5.3.7 -->
    <link rel="stylesheet" href="style/bootstrap.css" type="text/css">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap Icons v1.13.1 -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">

    <meta name="author" content="Carmine Squillace & Alessandro Mascolo">
</head>

<body>
    <?php
    /**    Header      **/
    include 'site/header.php';
    //////////////////////
    ?>

    <?php
    require_once __DIR__ . '/control/GestioneVenditeBigliettiNetbus.php';

    $ctrl = GestioneVenditeBigliettiNetbus::getInstance();

    // Se non loggato, mando alla login
    if (!$ctrl->getLoggedCliente()) {
        header('Location: login.php');
        exit;
    }

    // Se il cliente ha acquistato almeno un biglietto
    if (count($ctrl->getMyTickets())) {   
        ?>         
        <div class="container-fluid miei-biglietti-title">
                <p>BIGLIETTI ACQUISTATI</p>
        </div>
        <?php
        foreach ($ctrl->getMyTickets() as $biglietto) { // vengono mostrati tutti i biglietti acquistati

            $corsa = $ctrl->getCorsa($biglietto->getIdCorsa());
        ?>
            <div class="corsa container">
                <div class="info-citta">
                    <div class="biglietto">
                        <span class="etichetta">ID Biglietto:</span>
                        <span class="valore"><?php echo $biglietto->getIdBiglietto(); ?></span>
                    </div>
                    <div class="partenza">
                        <span class="etichetta">da:</span>
                        <span class="valore"><?php echo htmlspecialchars($ctrl->getCities($corsa->getIdTratta())[0]->getNome()); ?></span>
                        <br>
                        <span class="etichetta">a:</span>
                    <span class="valore"><?php echo htmlspecialchars($ctrl->getCities($corsa->getIdTratta())[1]->getNome()); ?></span>
                    </div>
                </div>

                <div class="info-tempo">
                    <div class="data">
                        <span class="etichetta">Emesso il:</span>
                        <span class="valore"><?php echo $biglietto->getDataEmissione()->format('d/m/Y'); ?></span>
                    </div>
                    <div class="orari">
                        <span class="etichetta">Orario di Partenza:</span>
                        <span class="valore"> <?php echo $corsa->getOrarioPartenza()->format('H:i'); ?></span>
                        <br>
                        <span class="etichetta">Orario di Arrivo:</span>
                        <span class="valore"> <?php echo $corsa->getOrarioArrivo()->format('H:i'); ?></span>
                    </div>
                </div>

                <div class="info-prezzo">
                    <div class="prezzo">
                        <span class="etichetta">Prezzo:</span>
                        <span class="valore"><?php echo $biglietto->getPrezzoVendita(); ?>€</span>
                    </div>
                </div>

                <div class="info-button">
                    <form action="print.php" method="post">
                        <input type='hidden' name='id_biglietto' value='<?php echo $biglietto->getIdBiglietto(); ?>'>
                        <button type="submit">
                            <strong>Stampa Biglietto</strong>
                        </button>
                    </form>
                </div>
            </div>

        <?php
        }
    } else { // altrimenti viene informato che non ha acquistato alcun biglietto
        ?>
        <div class="container miei-biglietti-title">
            <p>Non hai acquistato alcun biglietto</p>
        </div>
    <?php
    }
    ?>

    <?php
    /**    Footer      **/
    include 'site/footer.html';
    //////////////////////
    ?>

</body>

</html>