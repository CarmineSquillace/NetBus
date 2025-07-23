<?php
// register.php

require_once __DIR__ . '/boundary/ClienteNonRegistrato.php';
require_once __DIR__ . '/boundary/ClienteRegistrato.php';

// Se già loggato, mando alla home
if (ClienteRegistrato::getCliente()) {
  header('Location: index.php');
  exit;
}

$error = null;
$success = false;

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
  try {
    /*
            Quando un utente invia un form tramite method="POST":
            I dati inseriti dall’utente vengono inviati al server 
            nel corpo della richiesta HTTP POST
            e in PHP li trovi nel superarray associativo $_POST
    */

    $nome = trim($_POST['nome'] ?? '');
    $email = trim($_POST['email'] ?? '');
    $password = $_POST['password'] ?? '';
    $passwordConfirm = $_POST['password_confirm'] ?? '';

    // Chiamo la boundary per la registrazione
    $cliente = ClienteNonRegistrato::registrazione($nome, $email, $password, $passwordConfirm);

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
  <meta charset="utf-8">
  <title>NetBus – Registrazione</title>

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

  <div class="container-fluid">
    <div class="row">
      <div class="col-6 auth_back">
        <img src="img/hero-bus-2-back.png" alt="background NetBus">
      </div>

      <div class="col-6 auth_form">
        <img src="img/logo.png" alt="Logo di NetBus" class="auth_form_logo">

        <div class="container mt-5" style="max-width: 400px; margin-bottom: 5rem;">
          <h2 class="mb-4">Registati a NetBus</h2>

          <?php if ($error): ?>
            <div class="alert alert-danger"><?= htmlspecialchars($error) ?></div>
          <?php elseif ($success): ?>
            <div class="alert alert-success">
              Registrazione avvenuta con successo!
              <a href="login.php">Accedi ora</a>.
            </div>
          <?php endif; ?>

          <form method="post">
            <div class="mb-3">
              <label class="form-label">Nome</label>
              <input type="text" name="nome" placeholder="Nome" class="form-control" required
                value="<?= htmlspecialchars($_POST['nome'] ?? '') ?>" maxlenght="80">
            </div>
            <div class="mb-3">
              <label class="form-label">Email</label>
              <input type="email" name="email" placeholder="E-mail" class="form-control" required
                value="<?= htmlspecialchars($_POST['email'] ?? '') ?>" maxlenght="80">
            </div>
            <div class="mb-3">
              <label class="form-label">Password</label>
              <input type="password" name="password" placeholder="Nuova password" class="form-control" required maxlenght="80" minlength="8">
            </div>
            <div class="mb-3">
              <label class="form-label">Conferma Password</label>
              <input type="password" name="password_confirm" placeholder="Nuova password" class="form-control" required>
            </div>
            <button type="submit" class="btn btn-primary w-100">Registrati</button>
            <p class="mt-3 text-center">
              Hai già un account? <a href="login.php">Accedi qui</a>
            </p>
          </form>
        </div>
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