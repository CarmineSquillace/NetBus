<?php

class EntityCorsa {
    private int $idCorsa;
    private int $idTratta;
    private DateTime $data;
    private DateTime $orarioPartenza;
    private DateTime $orarioArrivo;
    private float $costo;
    private int $idAutobus;

    public function __construct(int $idCorsa, int $idTratta, DateTime $data, DateTime $orarioPartenza, DateTime $orarioArrivo, float $costo, int $idAutobus) {
        $this->idCorsa = $idCorsa;
        $this->idTratta = $idTratta;
        $this->data = $data;
        $this->orarioPartenza = $orarioPartenza;
        $this->orarioArrivo = $orarioArrivo;
        $this->costo = $costo;
        $this->idAutobus = $idAutobus;
    }

    // Getter e Setter per idCorsa
    public function getIdCorsa(): int {
        return $this->idCorsa;
    }

    public function setIdCorsa(int $idCorsa): void {
        $this->idCorsa = $idCorsa;
    }

    // Getter e Setter per idTratta
    public function getIdTratta(): int {
        return $this->idTratta;
    }

    public function setIdTratta(int $idTratta): void {
        $this->idTratta = $idTratta;
    }

    // Getter e Setter per data
    public function getData(): DateTime {
        return $this->data;
    }

    public function setData(DateTime $data): void {
        $this->data = $data;
    }

    // Getter e Setter per orarioPartenza
    public function getOrarioPartenza(): DateTime {
        return $this->orarioPartenza;
    }

    public function setOrarioPartenza(DateTime $orarioPartenza): void {
        $this->orarioPartenza = $orarioPartenza;
    }

    // Getter e Setter per orarioArrivo
    public function getOrarioArrivo(): DateTime {
        return $this->orarioArrivo;
    }

    public function setOrarioArrivo(DateTime $orarioArrivo): void {
        $this->orarioArrivo = $orarioArrivo;
    }

    // Getter e Setter per costo
    public function getCosto(): float {
        return $this->costo;
    }

    public function setCosto(float $costo): void {
        $this->costo = $costo;
    }

    // Getter e Setter per idAutobus
    public function getIdAutobus(): int {
        return $this->idAutobus;
    }

    public function setIdAutobus(int $idAutobus): void {
        $this->idAutobus = $idAutobus;
    }
}
?>