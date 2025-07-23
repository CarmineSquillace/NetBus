<?php
// login.php

require_once __DIR__ . '/boundary/ClienteNonRegistrato.php';
require_once __DIR__ . '/boundary/ClienteRegistrato.php';

// Se già loggato, mando alla home
if (ClienteRegistrato::getCliente()) {
  header('Location: index.php');
  exit;
}

$error = null;
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
  try {
    $email = trim($_POST['email'] ?? '');
    $password = $_POST['password'] ?? '';

    // Chiamo la boundary per il login
    ClienteNonRegistrato::autenticazione($email, $password);

    // Redirect alla homepage
    header('Location: index.php');
    exit;
  } catch (Exception $e) {
    $error = $e->getMessage();
  }
}
?>
<!DOCTYPE html>
<html lang="it">

<head>
  <meta charset="utf-8">
  <title>NetBus – Autenticazione</title>

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
        <img src="img/hero-bus-back.png" alt="background NetBus">
      </div>

      <div class="col-6 auth_form">
        <img src="img/logo.png" alt="Logo di NetBus" class="auth_form_logo">

        <div class="container mt-5" style="max-width: 400px;">
          <h2 class="mb-4">Accedi a NetBus</h2>

          <?php if ($error): ?>
            <div class="alert alert-danger"><?= htmlspecialchars($error) ?></div>
          <?php endif; ?>

          <form method="post" action="">
            <div class="mb-3">
              <label class="form-label">Email</label>
              <input type="email" name="email" placeholder="E-mail" class="form-control" required
                value="<?= htmlspecialchars($_POST['email'] ?? '') ?>">
            </div>
            <div class="mb-3">
              <label class="form-label">Password</label>
              <input type="password" name="password" placeholder="Password" class="form-control" required>
            </div>
            <button type="submit" class="btn btn-primary w-100">Accedi</button>
            <p class="mt-3 text-center">
              Non hai un account? <a href="register.php">Registrati ora</a>
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