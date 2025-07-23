<?php

class EntityCliente {
    private string $nomeUtente;
    private string $email;
    private string $password;
    private ?string $cartaDiCredito;

    public function __construct(string $nomeUtente, string $email, string $password, ?string $cartaDiCredito = null) {
        $this->nomeUtente = $nomeUtente;
        $this->email = $email;
        $this->password = $password;
        $this->cartaDiCredito = $cartaDiCredito;
    }

    // Getter e Setter per nome
    public function getNomeUtente(): string {
        return $this->nomeUtente;
    }

    public function setNomeUtente(string $nomeUtente): void {
        $this->nomeUtente = $nomeUtente;
    }

    // Getter e Setter per email
    public function getEmail(): string {
        return $this->email;
    }

    public function setEmail(string $email): void {
        $this->email = $email;
    }

    // Getter e Setter per password
    public function getPassword(): string {
        return $this->password;
    }

    public function setPassword(string $password): void {
        $this->password = $password;
    }

    // Getter e Setter per cartaDiCredito
    public function getCartaDiCredito(): ?string {
        return $this->cartaDiCredito;
    }

    public function setCartaDiCredito(string $cartaDiCredito): void {
        $this->cartaDiCredito = $cartaDiCredito;
    }
}
?>