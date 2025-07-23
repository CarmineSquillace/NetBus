<?php

class EntityCitta {
    private int $idCitta;
    private String $nome;

    public function __construct(int $idCitta, String $nome) {
        $this->idCitta = $idCitta;
        $this->nome = $nome;
    }

    // Getter e Setter per idCitta
    public function getIdCitta(): int {
        return $this->idCitta;
    }

    public function setIdCitta(int $idCitta): void {
        $this->idCitta = $idCitta;
    }

    // Getter e Setter per nome
    public function getNome(): String {
        return $this->nome;
    }

    public function setNome(String $nome): void {
        $this->nome = $nome;
    }
}
?>