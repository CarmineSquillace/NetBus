<?php

class EntityTratta {
    private int $idTratta;
    private int $idPartenza;
    private int $idDestinazione;

    public function __construct(int $idTratta, int $idPartenza, int $idDestinazione) {
        $this->idTratta = $idTratta;
        $this->idPartenza = $idPartenza;
        $this->idDestinazione = $idDestinazione;
    }

    // Getter e Setter per idTratta
    public function getIdTratta(): int {
        return $this->idTratta;
    }

    public function setIdTratta(int $idTratta): void {
        $this->idTratta = $idTratta;
    }

    // Getter e Setter per idPartenza
    public function getIdPartenza(): int {
        return $this->idPartenza;
    }

    public function setIdPartenza(int $idPartenza): void {
        $this->idPartenza = $idPartenza;
    }

    // Getter e Setter per idDestinazione
    public function getIdDestinazione(): int {
        return $this->idDestinazione;
    }

    public function setIdDestinazione(int $idDestinazione): void {
        $this->idDestinazione = $idDestinazione;
    }

}
?>