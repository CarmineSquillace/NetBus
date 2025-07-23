<header class="container-fluid d-flex flex-wrap align-items-center justify-content-center justify-content-md-between py-3 border-bottom">
    <!-- logo -->
    <div class="col-4">
        <a href="http://localhost/"><img src="img/logo.png" alt="Logo NetBus" class="menu-logo"></a>
    </div>

    <!-- nav bar -->
    <div class="col-4 col-md-auto mb-2 justify-content-center mb-md-0">
        <ul class="nav">
            <li><a href="http://localhost/" class="nav-link px-2 link-secondary">Home</a></li>
            <li><a href="" class="nav-link px-2">Informazioni</a></li>
            <li><a href="" class="nav-link px-2">Monitora il viaggio</a></li>
            <li><a href="" class="nav-link px-2">Aiuto</a></li>
        </ul>
    </div>

    <div class="col-4 text-end">
        <?php
            require_once __DIR__ . '../../control/GestioneVenditeBigliettiNetbus.php';

            $ctrl = GestioneVenditeBigliettiNetbus::getInstance();

            if(!$ctrl->getLoggedCliente()){
                ?>
                    <a href="/login.php"><button type="button" class="btn btn-outline-primary me-2">Accedi</button></a>
                    <a href="/register.php"><button type="button" class="btn btn-primary">Registrati</button></a>
                <?php
            }else{
                ?>
                    <a href="/biglietti.php"><p style="display:inline; margin-right:1rem">I miei biglietti</p></a>
                    <a href="/logout.php"><button type="button" class="btn btn-primary">Esci</button></a>
                <?php
            }
        ?>

    </div>
</header>