<?php
// buy.php

require_once __DIR__ . '../control/GestioneVenditeBigliettiNetbus.php';
require_once __DIR__ . '/boundary/ClienteRegistrato.php';

// Se non loggato, mando alla login
$cliente = ClienteRegistrato::getCliente();
if ($cliente === null) {
  header('Location: login.php');
  exit;
}

$error = null;
$success = false;

// se ha cliccato su effettua pagamento
if (isset($_POST['card']) and isset($_POST['tickets']) and isset($_POST['id_corsa'])) {
  try {
    // Recupero i dati del form
    $numBiglietti = trim($_POST['tickets'] ?? '');
    $cartaCredito = trim($_POST['card'] ?? '');
    
    $ctrl = GestioneVenditeBigliettiNetbus::getInstance();
    $corsa = $ctrl->getCorsa($_POST['id_corsa']);

    // Chiamo la boundary per l'acquisto dei biglietti
    $biglietti = ClienteRegistrato::acquistaBiglietti($corsa->getIdCorsa(), $cartaCredito, $numBiglietti);

    if (isset($_POST['save'])) // Se il cliente vuole memorizzare il metodo di pagamento per acquisti futuri
      $ctrl->updateCreditCard($cartaCredito);

    // Se tutto OK, mostro un messaggio di successo
    $success = true;
  } catch (Exception $e) {
    $error = $e->getMessage();
  }
}
?>

<!DOCTYPE html>
<html lang="it">

<head>
  <meta charset="utf8">
  <title>Acquista</title>

  <!-- CSS -->
  <link rel="stylesheet" href="style/style.css" type="text/css">

  <!-- Bootstrap v5.3.7 -->
  <link rel="stylesheet" href="style/bootstrap.css" type="text/css">
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <!-- Bootstrap Icons v1.13.1 -->
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">

  <meta name="author" content="Carmine Squillace & Alessandro Mascolo">

  <?php if ($success): ?>
    <meta http-equiv="refresh" content="1;url=biglietti.php">
  <?php endif; ?>
</head>

<body>
  <?php
  /**    Header      **/
  include 'site/header.php';
  //////////////////////
  ?>

  <?php
  if (isset($_POST['id_corsa'])) {
    $corsa = $ctrl->getCorsa($_POST['id_corsa']);
  ?>
    <div class="container-fluid miei-biglietti-title">
      <p>DETTAGLI CORSA</p>
    </div>

    <div class="corsa container">
      <div class="info-citta">
        <div class="biglietto">
          <span class="etichetta">ID Corsa:</span>
          <span class="valore"><?php echo $corsa->getIdCorsa(); ?></span>
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
          <span class="etichetta">Partenza il:</span>
          <span class="valore"><?php echo $corsa->getData()->format('d/m/Y'); ?></span>
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
          <span class="valore" id="price"><?php echo $corsa->getCosto(); ?>€</span>
        </div>
      </div>
    </div>

    <!-- alert -->
    <svg xmlns="http://www.w3.org/2000/svg" class="d-none">
      <symbol id="check-circle-fill" viewBox="0 0 16 16">
        <path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zm-3.97-3.03a.75.75 0 0 0-1.08.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-.01-1.05z" />
      </symbol>
      <symbol id="info-fill" viewBox="0 0 16 16">
        <path d="M8 16A8 8 0 1 0 8 0a8 8 0 0 0 0 16zm.93-9.412-1 4.705c-.07.34.029.533.304.533.194 0 .487-.07.686-.246l-.088.416c-.287.346-.92.598-1.465.598-.703 0-1.002-.422-.808-1.319l.738-3.468c.064-.293.006-.399-.287-.47l-.451-.081.082-.381 2.29-.287zM8 5.5a1 1 0 1 1 0-2 1 1 0 0 1 0 2z" />
      </symbol>
      <symbol id="exclamation-triangle-fill" viewBox="0 0 16 16">
        <path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z" />
      </symbol>
    </svg>

    <div class="container">
      <div class="row">
        <div class="col-12 align-items-center justify-content-center">
          <div class="alert alert-primary d-flex align-items-center" role="alert">
            <svg class="bi flex-shrink-0 me-2" style="width:2rem" role="img" aria-label="Info:">
              <use xlink:href="#info-fill" />
            </svg>
            <div>
              <strong>Avviso ai passeggeri: </strong>Può essere portato un solo bagaglio che verrà stivato prima della partenza.
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="container-fluid miei-biglietti-title">
      <p>DETTAGLI PAGAMENTO</p>
    </div>

    <?php if ($error): ?>
      <div class="alert alert-danger container"><?= htmlspecialchars($error) ?></div>
    <?php elseif ($success): ?>
      <div class="alert alert-success container">
        Acquisto effettuato correttamente
      </div>
    <?php endif; ?>

    <form action="<?php echo htmlspecialchars($_SERVER['PHP_SELF']) ?>" method="post" class="container pagamento">
      <input type='hidden' name='id_corsa' value='<?php echo $corsa->getIdCorsa(); ?>'>
      <div class="mb-3">
        <label for="tickets">Numero di biglietti:</label>
        <input type="number" name="tickets" id="tickets" min="1" max="100" required>
      </div>

      <div class="mb-3">
        <label id="total"><strong>Totale: 0.00€</strong></label> <br>
      </div>

      <div>
        <label for="card">Carta di Credito:</label>
        <?php
        if ($cliente->getCartaDiCredito() === null) { // se il cliente non ha memorizzato alcun metodo di pagamento
        ?>
          <input type="text" name="card" id="card" placeholder="1234 5678 9012 3456" required maxlength="16">
      </div>
      <div class="mb-3">
        <label>
          <input type="checkbox" name="save" value="save">
          Memorizza la carta per acquisti futuri
        </label>
      </div>
    <?php
        } else { // viene utilizzato il metodo di pagamento memorizzato
    ?>
      <input type="number" name="card" id="card" value="<?php echo $cliente->getCartaDiCredito(); ?>" required maxlength="16">
      </div>
    <?php
        }
    ?>

    <button type="submit" class="btn btn-primary w-20">Effettua Pagamento</button>
    </form>
  <?php
  }
  ?>

  <?php
  /**    Footer      **/
  include 'site/footer.html';
  //////////////////////
  ?>

  <script> // Preventivo del totale
    document.addEventListener("DOMContentLoaded", function() {
      const priceElement = document.getElementById('price');
      const ticketsInput = document.getElementById('tickets');
      const totalDisplay = document.getElementById('total');

      // Estrai il valore numerico dal prezzo, rimuovendo il simbolo €
      const ticketPrice = parseFloat(priceElement.textContent.replace('€', '').trim());

      ticketsInput.addEventListener('input', () => {
        const quantity = parseInt(ticketsInput.value) || 0;
        const total = quantity * ticketPrice;
        if (total >= 0)
          totalDisplay.innerHTML = `<strong>Totale: ${total.toFixed(2)}€</strong>`;
        else
          totalDisplay.innerHTML = `<strong style="color:red">Numero di biglietti non valido.</strong>`;
      });
    });
  </script>

</body>

</html>