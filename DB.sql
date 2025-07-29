-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Lug 11, 2025 alle 16:05
-- Versione del server: 10.4.32-MariaDB
-- Versione PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `netbus`
--
CREATE DATABASE IF NOT EXISTS `netbus`;
USE `netbus`;
-- --------------------------------------------------------

--
-- Struttura della tabella `autobus`
--

CREATE TABLE `autobus` (
  `ID` int(11) NOT NULL,
  `Capienza` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `autobus`
--

INSERT INTO `autobus` (`ID`, `Capienza`) VALUES
(1, 70),
(2, 50),
(3, 100);

-- --------------------------------------------------------

--
-- Struttura della tabella `biglietti`
--

CREATE TABLE `biglietti` (
  `ID` int(11) NOT NULL,
  `Prezzo` decimal(10,2) NOT NULL,
  `Data` date NOT NULL,
  `Corsa` int(11) NOT NULL,
  `Impiegato` varchar(5) DEFAULT NULL,
  `Cliente` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `citta`
--

CREATE TABLE `citta` (
  `ID` int(11) NOT NULL,
  `Nome` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `citta`
--

INSERT INTO `citta` (`ID`, `Nome`) VALUES
(4, 'Cesena'),
(3, 'Milano'),
(1, 'Napoli'),
(2, 'Roma');

-- --------------------------------------------------------

--
-- Struttura della tabella `clienti`
--

CREATE TABLE `clienti` (
  `Username` varchar(30) NOT NULL,
  `Email` varchar(50) NOT NULL,
  `Password` varchar(30) NOT NULL,
  `CartaCredito` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `corse`
--

CREATE TABLE `corse` (
  `ID` int(11) NOT NULL,
  `Costo` decimal(10,2) NOT NULL,
  `Data` date NOT NULL,
  `OrarioPartenza` time NOT NULL,
  `OrarioArrivo` time NOT NULL,
  `Autobus` int(11) NOT NULL,
  `Tratta` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `corse`
--

INSERT INTO `corse` (`ID`, `Costo`, `Data`, `OrarioPartenza`, `OrarioArrivo`, `Autobus`, `Tratta`) VALUES
(1, 5.00, '2025-07-30', '08:30:00', '10:00:00', 3, 1),
(2, 12.50, '2025-07-30', '13:00:00', '17:30:00', 1, 3),
(3, 8.30, '2025-07-31', '17:15:00', '19:25:00', 2, 2);

-- --------------------------------------------------------

--
-- Struttura della tabella `impiegati`
--

CREATE TABLE `impiegati` (
  `ID` varchar(5) NOT NULL,
  `Password` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `impiegati`
--

INSERT INTO `impiegati` (`ID`, `Password`) VALUES
('MI018', 'Hell0W0rld_'),
('NA128', 'Passw0rd!'),
('RO049', 'N3t_BUs_');

-- --------------------------------------------------------

--
-- Struttura della tabella `tratte`
--

CREATE TABLE `tratte` (
  `ID` int(11) NOT NULL,
  `Partenza` int(11) NOT NULL,
  `Destinazione` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `tratte`
--

INSERT INTO `tratte` (`ID`, `Partenza`, `Destinazione`) VALUES
(1, 1, 2),
(2, 3, 4),
(3, 3, 2);

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `autobus`
--
ALTER TABLE `autobus`
  ADD PRIMARY KEY (`ID`);

--
-- Indici per le tabelle `biglietti`
--
ALTER TABLE `biglietti`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_BIGLIETTI_CORSE` (`Corsa`),
  ADD KEY `FK_BIGLIETTI_IMPIEGATI` (`Impiegato`),
  ADD KEY `FK_BIGLIETTI_CLIENTI` (`Cliente`);

--
-- Indici per le tabelle `citta`
--
ALTER TABLE `citta`
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE KEY `UK_CITTA` (`Nome`);

--
-- Indici per le tabelle `clienti`
--
ALTER TABLE `clienti`
  ADD PRIMARY KEY (`Username`),
  ADD UNIQUE KEY `UQ_CLIENTI` (`Email`);

--
-- Indici per le tabelle `corse`
--
ALTER TABLE `corse`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_CORSE_AUTOBUS` (`Autobus`),
  ADD KEY `FK_CORSE_TRATTE` (`Tratta`);

--
-- Indici per le tabelle `impiegati`
--
ALTER TABLE `impiegati`
  ADD PRIMARY KEY (`ID`);

--
-- Indici per le tabelle `tratte`
--
ALTER TABLE `tratte`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_TRATTE_CITTA_PARTENZA` (`Partenza`),
  ADD KEY `FK_TRATTE_CITTA_DESTINAZIONE` (`Destinazione`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `autobus`
--
ALTER TABLE `autobus`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT per la tabella `biglietti`
--
ALTER TABLE `biglietti`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `citta`
--
ALTER TABLE `citta`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT per la tabella `corse`
--
ALTER TABLE `corse`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT per la tabella `tratte`
--
ALTER TABLE `tratte`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `biglietti`
--
ALTER TABLE `biglietti`
  ADD CONSTRAINT `FK_BIGLIETTI_CLIENTI` FOREIGN KEY (`Cliente`) REFERENCES `clienti` (`Username`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_BIGLIETTI_CORSE` FOREIGN KEY (`Corsa`) REFERENCES `corse` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_BIGLIETTI_IMPIEGATI` FOREIGN KEY (`Impiegato`) REFERENCES `impiegati` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `corse`
--
ALTER TABLE `corse`
  ADD CONSTRAINT `FK_CORSE_AUTOBUS` FOREIGN KEY (`Autobus`) REFERENCES `autobus` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_CORSE_TRATTE` FOREIGN KEY (`Tratta`) REFERENCES `tratte` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `UQ_TRATTA_DATA` UNIQUE (`Tratta`, `Data`);
  
--
-- Limiti per la tabella `tratte`
--
ALTER TABLE `tratte`
  ADD CONSTRAINT `FK_TRATTE_CITTA_DESTINAZIONE` FOREIGN KEY (`Destinazione`) REFERENCES `citta` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_TRATTE_CITTA_PARTENZA` FOREIGN KEY (`Partenza`) REFERENCES `citta` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
