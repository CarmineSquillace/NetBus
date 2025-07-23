<?php

class EntityBiglietto {
    private int $idBiglietto;
    private int $idCorsa;
    private float $prezzoVendita;
    private DateTime $dataEmissione;
    private String $idClienteRegistrato;
    private ?int $idImpiegato;

    public function __construct(int $idBiglietto, int $idCorsa, float $prezzoVendita, DateTime $dataEmissione, String $idClienteRegistrato, ?int $idImpiegato = null) {
        $this->idBiglietto = $idBiglietto;
        $this->idCorsa = $idCorsa;
        $this->prezzoVendita = $prezzoVendita;
        $this->dataEmissione = $dataEmissione;
        $this->idImpiegato = $idImpiegato;
        $this->idClienteRegistrato = $idClienteRegistrato;
    }

    // Getter e Setter per idBiglietto
    public function getIdBiglietto(): int {
        return $this->idBiglietto;
    }

    public function setIdBiglietto(int $idBiglietto): void {
        $this->idBiglietto = $idBiglietto;
    }

    // Getter e Setter per idCorsa
    public function getIdCorsa(): int {
        return $this->idCorsa;
    }

    public function setIdCorsa(int $idCorsa): void {
        $this->idCorsa = $idCorsa;
    }

    // Getter e Setter per prezzoVendita
    public function getPrezzoVendita(): float {
        return $this->prezzoVendita;
    }

    public function setPrezzoVendita(float $prezzoVendita): void {
        $this->prezzoVendita = $prezzoVendita;
    }

    // Getter e Setter per dataEmissione
    public function getDataEmissione(): DateTime {
        return $this->dataEmissione;
    }

    public function setDataEmissione(DateTime $dataEmissione): void {
        $this->dataEmissione = $dataEmissione;
    }

    // Getter e Setter per idImpiegato
    public function getIdImpiegato(): ?int {
        return $this->idImpiegato;
    }

    public function setIdImpiegato(int $idImpiegato): void {
        $this->idImpiegato = $idImpiegato;
    }

    // Getter e Setter per idClienteRegistrato
    public function getIdClienteRegistrato(): String {
        return $this->idClienteRegistrato;
    }

    public function setIdClienteRegistrato(String $idClienteRegistrato): void {
        $this->idClienteRegistrato = $idClienteRegistrato;
    }
}
?>