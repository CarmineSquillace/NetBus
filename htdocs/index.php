<!--
/*!
 * NetBus v1.0 (http://localhost/)
 * Copyright 2025 - Carmine Squillace e Alessandro Mascolo
 */
-->
<!DOCTYPE html>
<html lang="it">

<head>
    <meta charset="utf8">
    <title>NetBus</title>

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

    <!-- hero content -->
    <div class="hero">
        <div class="hero-content">
            <h1>Salta a bordo di <strong style="color:#c1e5fc">NetBus</strong>!</h1>
            <h6>Viaggia comodo, sicuro e puntuale.</h6>
        </div>
    </div>

    <!-- corse -->
    <div class="banner-corse">
        <h1>Corse disponibili:</h1>
    </div>

    <?php
    require_once __DIR__ . '../control/GestioneVenditeBigliettiNetbus.php';

    $ctrl = GestioneVenditeBigliettiNetbus::getInstance();

    foreach ($ctrl->listCorse() as $corsa) {
    ?>
        <div class="corsa">
            <div class="info-citta">
                <div class="partenza">
                    <span class="etichetta">da:</span>
                    <span class="valore"><?php echo htmlspecialchars($ctrl->getCities($corsa->getIdTratta())[0]->getNome()); ?></span>
                </div>
                <div class="destinazione">
                    <span class="etichetta">a:</span>
                    <span class="valore"><?php echo htmlspecialchars($ctrl->getCities($corsa->getIdTratta())[1]->getNome()); ?></span>
                </div>
            </div>

            <div class="info-tempo">
                <div class="data">
                    <span class="etichetta">Partenza il:</span>
                    <span class="valore"><?php echo $corsa->getData()->format('d/m/Y'); ?></span>
                </div>
                <div class="orari">
                    <span class="etichetta">Orari:</span>
                    <span class="valore"> <?php echo $corsa->getOrarioPartenza()->format('H:i'); ?> → <?php echo $corsa->getOrarioArrivo()->format('H:i'); ?></span>
                </div>
            </div>

            <div class="info-button">
                <?php
                if (!$ctrl->getLoggedCliente()) {
                ?>
                    <p style="color: red;opacity: 0.7;">Registrati o accedi per acquistare biglietti</p>
                <?php
                } else {
                ?>
                    <form action="buy.php" method="post">
                        <input type='hidden' name='id_corsa' value='<?php echo $corsa->getIdCorsa(); ?>'>
                        <button>
                            <strong>Acquista Biglietti</strong>
                        </button>
                    </form>
                <?php
                }
                ?>
            </div>

        </div>
    <?php
    }
    ?>

    <!-- features -->
    <div class="container">
        <div class="row">
            <div class="feature col"><svg xmlns="http://www.w3.org/2000/svg" width="52" height="52" fill="#4c84a6"
                    class="bi bi-bus-front" viewBox="0 0 16 16">
                    <path
                        d="M5 11a1 1 0 1 1-2 0 1 1 0 0 1 2 0m8 0a1 1 0 1 1-2 0 1 1 0 0 1 2 0m-6-1a1 1 0 1 0 0 2h2a1 1 0 1 0 0-2zm1-6c-1.876 0-3.426.109-4.552.226A.5.5 0 0 0 3 4.723v3.554a.5.5 0 0 0 .448.497C4.574 8.891 6.124 9 8 9s3.426-.109 4.552-.226A.5.5 0 0 0 13 8.277V4.723a.5.5 0 0 0-.448-.497A44 44 0 0 0 8 4m0-1c-1.837 0-3.353.107-4.448.22a.5.5 0 1 1-.104-.994A44 44 0 0 1 8 2c1.876 0 3.426.109 4.552.226a.5.5 0 1 1-.104.994A43 43 0 0 0 8 3" />
                    <path
                        d="M15 8a1 1 0 0 0 1-1V5a1 1 0 0 0-1-1V2.64c0-1.188-.845-2.232-2.064-2.372A44 44 0 0 0 8 0C5.9 0 4.208.136 3.064.268 1.845.408 1 1.452 1 2.64V4a1 1 0 0 0-1 1v2a1 1 0 0 0 1 1v3.5c0 .818.393 1.544 1 2v2a.5.5 0 0 0 .5.5h2a.5.5 0 0 0 .5-.5V14h6v1.5a.5.5 0 0 0 .5.5h2a.5.5 0 0 0 .5-.5v-2c.607-.456 1-1.182 1-2zM8 1c2.056 0 3.71.134 4.822.261.676.078 1.178.66 1.178 1.379v8.86a1.5 1.5 0 0 1-1.5 1.5h-9A1.5 1.5 0 0 1 2 11.5V2.64c0-.72.502-1.301 1.178-1.379A43 43 0 0 1 8 1" />
                </svg>
                <title>Placeholder</title>
                <rect width="100%" height="100%" fill="var(--bs-secondary-color)"></rect>
                </svg>
                <h4 class="feature-title">Comfort in viaggio</h2>
                    <p class="feature-descr">Goditi il wi-fi gratuito, le prese di corrente e tanto spazio per le gambe.</p>
            </div>

            <div class="feature col"><svg xmlns="http://www.w3.org/2000/svg" width="52" height="52" fill="#4c84a6"
                    class="bi bi-pc-display" viewBox="0 0 16 16">
                    <path
                        d="M8 1a1 1 0 0 1 1-1h6a1 1 0 0 1 1 1v14a1 1 0 0 1-1 1H9a1 1 0 0 1-1-1zm1 13.5a.5.5 0 1 0 1 0 .5.5 0 0 0-1 0m2 0a.5.5 0 1 0 1 0 .5.5 0 0 0-1 0M9.5 1a.5.5 0 0 0 0 1h5a.5.5 0 0 0 0-1zM9 3.5a.5.5 0 0 0 .5.5h5a.5.5 0 0 0 0-1h-5a.5.5 0 0 0-.5.5M1.5 2A1.5 1.5 0 0 0 0 3.5v7A1.5 1.5 0 0 0 1.5 12H6v2h-.5a.5.5 0 0 0 0 1H7v-4H1.5a.5.5 0 0 1-.5-.5v-7a.5.5 0 0 1 .5-.5H7V2z" />
                </svg>
                <title>Placeholder</title>
                <rect width="100%" height="100%" fill="var(--bs-secondary-color)"></rect>
                </svg>
                <h4 class="feature-title">Scegli, prenota, viaggia</h2>
                    <p class="feature-descr">Dallo schermo al tuo posto a sedere in pochi secondi. Tu prenota, al resto ci pensiamo
                        noi.</p>
            </div>

            <div class="feature col"><svg xmlns="http://www.w3.org/2000/svg" width="52" height="52" fill="#4c84a6"
                    class="bi bi-leaf-fill" viewBox="0 0 16 16">
                    <path
                        d="M1.4 1.7c.217.289.65.84 1.725 1.274 1.093.44 2.885.774 5.834.528 2.02-.168 3.431.51 4.326 1.556C14.161 6.082 14.5 7.41 14.5 8.5q0 .344-.027.734C13.387 8.252 11.877 7.76 10.39 7.5c-2.016-.288-4.188-.445-5.59-2.045-.142-.162-.402-.102-.379.112.108.985 1.104 1.82 1.844 2.308 2.37 1.566 5.772-.118 7.6 3.071.505.8 1.374 2.7 1.75 4.292.07.298-.066.611-.354.715a.7.7 0 0 1-.161.042 1 1 0 0 1-1.08-.794c-.13-.97-.396-1.913-.868-2.77C12.173 13.386 10.565 14 8 14c-1.854 0-3.32-.544-4.45-1.435-1.124-.887-1.889-2.095-2.39-3.383-1-2.562-1-5.536-.65-7.28L.73.806z" />
                </svg>
                <title>Placeholder</title>
                <rect width="100%" height="100%" fill="var(--bs-secondary-color)"></rect>
                </svg>
                <h4 class="feature-title">Viaggi rispettosi del pianeta</h2>
                    <p class="feature-descr">Lascia perdere l’auto, parti con noi. Viaggia in autobus e riduci la tua impronta
                        ecologica.</p>
            </div>
        </div>
    </div>

    <?php
    /**    Footer      **/
    include 'site/footer.html';
    //////////////////////
    ?>
</body>

</html>