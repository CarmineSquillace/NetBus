<?php

class EntityAutobus {
    private int $idAutobus;
    private int $capienza;

    public function __construct(int $idAutobus, String $capienza) {
        $this->idAutobus = $idAutobus;
        $this->capienza = $capienza;
    }

    // Getter e Setter per idAutobus
    public function getIdAutobus(): int {
        return $this->idAutobus;
    }

    public function setIdAutobus(int $idAutobus): void {
        $this->idAutobus = $idAutobus;
    }

    // Getter e Setter per capienza
    public function getCapienza(): int {
        return $this->capienza;
    }

    public function setCapienza(int $capienza): void {
        $this->capienza = $capienza;
    }
}
?>