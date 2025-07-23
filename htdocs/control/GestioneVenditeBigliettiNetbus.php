<?php

require_once __DIR__ . '/../database/ClienteDAO.php';
require_once __DIR__ . '/../database/BigliettoDAO.php';
require_once __DIR__ . '/../database/CorsaDAO.php';
require_once __DIR__ . '/../database/TrattaDAO.php';
require_once __DIR__ . '/../database/CittaDAO.php';
require_once __DIR__ . '/../database/AutobusDAO.php';

require_once __DIR__ . '/../entity/EntityCliente.php';
require_once __DIR__ . '/../entity/EntityBiglietto.php';
require_once __DIR__ . '/../entity/EntityCorsa.php';
require_once __DIR__ . '/../entity/EntityTratta.php';
require_once __DIR__ . '/../entity/EntityCitta.php';
require_once __DIR__ . '/../entity/EntityAutobus.php';

class GestioneVenditeBigliettiNetbus
{
    private static ?GestioneVenditeBigliettiNetbus $instance = null;

    private function __construct() {}

    // Metodo statico per ottenere l'istanza (singleton)
    public static function getInstance(): GestioneVenditeBigliettiNetbus
    {
        
        if (self::$instance === null) {
            session_start();
            self::$instance = new GestioneVenditeBigliettiNetbus();
        }

        return self::$instance;
    }

    /**
     * Registra un nuovo cliente
     * @param string $nome
     * @param string $email
     * @param string $password
     * @param string $passwordConfirm
     * @return EntityCliente
     * @throws Exception
     */
    public static function registrazione(string $nome, string $email, string $password, string $passwordConfirm): EntityCliente
    {
        if ($password !== $passwordConfirm)
            throw new Exception('Le password non coincidono.');

        if (strlen($nome) > 30)
            throw new Exception('Nome utente troppo lungo.');

        if ($nome === "")
            throw new Exception('Il nome utente non può essere vuoto.');

        if (preg_match('/[^a-zA-Z0-9]/', $nome))
            throw new Exception('Il nome utente non può contenere simboli speciali.');

        if (ClienteDAO::readByEmail($email))
            throw new Exception('Email già registrata.');

        if (ClienteDAO::readByUsername($nome))
            throw new Exception('Nome già registrato.');

        if (strlen($password) < 8)
            throw new Exception('La password deve avere una lunghezza minima di 8 caratteri.');

        if (!preg_match('/\d/', $password) and !preg_match('/[^a-zA-Z0-9]/', $password))
            throw new Exception('La password deve contenere almeno un numero o un simbolo speciale.');

        $cliente = new EntityCliente($nome, $email, $password, null);
        return ClienteDAO::createCliente($cliente);
    }

    /**
     * Effettua il login
     * @param string $email
     * @param string $password
     * @return EntityCliente
     * @throws Exception
     */
    public static function autenticazione(string $email, string $password): EntityCliente
    {
        $cliente = ClienteDAO::readByEmail($email);

        if (!$cliente || !($password === $cliente->getPassword()))
            throw new Exception('Credenziali non valide.');
        

        $_SESSION['cliente'] = $cliente->getNomeUtente();
        return $cliente;
    }

    /**
     * Restituisce il cliente autenticato
     * @return EntityCliente|null
     */
    public static function getLoggedCliente(): ?EntityCliente
    {
        if (empty($_SESSION['cliente'])) 
            return null;
        
        return ClienteDAO::readByUsername($_SESSION['cliente']);
    }

    /**
     * Restituisce l'elenco delle corse future
     * @return EntityCorsa[]
     */
    public static function listCorse(): array
    {
        return CorsaDAO::findUpcoming();
    }

    /**
     * Restituisce la corsa selezionata in base al suo id
     * @param id id della corsa
     * @return EntityCorsa|null
     */
    public static function getCorsa(int $id): ?EntityCorsa
    {
        return CorsaDAO::readById($id);
    }

    /**
     * Restituisce il biglietto selezionato in base al suo id
     * @param id di del biglietto
     * @return EntityBiglietto|null
     */
    public static function getBiglietto(int $id): ?EntityBiglietto
    {
        return BigliettoDAO::readById($id);
    }


    /**
     * Restituisce la città di Partenza e la città di Destinazione di una tratta
     * @return EntityCitta[]
     */
    public static function getCities(int $id): array
    {
        $tratta = TrattaDAO::read($id);

        $list[] = CittaDAO::read($tratta->getIdPartenza());
        $list[] = CittaDAO::read($tratta->getIdDestinazione());

        return $list;
    }

    /**
     * Acquista uno o più biglietti per la corsa indicata
     * @return EntityBiglietto[]
     * @throws Exception
     */
    public static function acquistaBiglietti(int $idCorsa, String $cartaCredito, int $numBiglietti): array
    {
        $cliente = self::getLoggedCliente();
        
        // validazione cliente
        if (!$cliente)
            throw new Exception('Devi essere autenticato per acquistare.');
        
        // validazione corsa
        $corsa = CorsaDAO::readById($idCorsa);
        if (!$corsa)
            throw new Exception('Corsa non trovata.');

        // validazione numero biglietti
        if($numBiglietti<=0)
            throw new Exception('Devi acquistare almeno un biglietto.');
        
		$autobusAssociato = AutobusDAO::readAutobusPerCorsa($idCorsa);
			
		if ($autobusAssociato === null)
			throw new Exception("Autobus associato alla corsa non trovato.");

        $numBigliettiTotaliEmessi = BigliettoDAO::bigliettiPerCorsa($idCorsa);

		if ($numBigliettiTotaliEmessi + $numBiglietti > $autobusAssociato->getCapienza())
			throw new Exception("Impossibile emettere " . $numBiglietti . " biglietti: supererebbe la capienza dell'autobus (" . $autobusAssociato->getCapienza() . ").");
			
        // validazione metodo di pagamento
        if (strlen($cartaCredito)!=16 || preg_match('/[^0-9]/', $cartaCredito))
            throw new Exception('Metodo di pagamento non valido.');
        
        // Determina il totale
        $tot = $corsa->getCosto()*$numBiglietti;

        // ....effettua la transazione richiamando una API dell'istituto di credito....

        // emetti i biglietti
        $biglietto = new EntityBiglietto(-1, $corsa->getIdCorsa(), $corsa->getCosto(), new DateTime(), $cliente->getNomeUtente(), null);
        
        $list = [];
        for($i=0; $i<$numBiglietti; $i++)
            $list[] = BigliettoDAO::createBiglietto($biglietto);
        
        return $list;
    }

    /**
     * Restituisce i biglietti del cliente autenticato
     * @return EntityBiglietto[]
     * @throws Exception
     */
    public static function getMyTickets(): array
    {
        $cliente = self::getLoggedCliente();
        if (!$cliente)
            throw new Exception('Non autenticato.');
        
        return BigliettoDAO::readByCliente($cliente->getNomeUtente());
    }

    /**
     * Aggiorna la carta di credito del cliente autenticato
     * @param string $cartaDiCredito
     * @throws Exception
     */
    public static function updateCreditCard(string $cartaDiCredito): void
    {
        $cliente = self::getLoggedCliente();

        if (!$cliente)
            throw new Exception('Non autenticato.');
        
        $cliente->setCartaDiCredito($cartaDiCredito);
        ClienteDAO::updateCartaDiCredito($cliente->getNomeUtente(), $cartaDiCredito);
    }
}
