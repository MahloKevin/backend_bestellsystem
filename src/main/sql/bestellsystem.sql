-- phpMyAdmin SQL Dump
-- version 5.2.1deb1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Erstellungszeit: 30. Apr 2024 um 06:19
-- Server-Version: 10.11.6-MariaDB-0+deb12u1
-- PHP-Version: 8.2.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `bestellsystem`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `groups`
--

CREATE TABLE `groups` (
  `ID` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `web_read` tinyint(1) NOT NULL,
  `web_edit` tinyint(1) NOT NULL,
  `web_delete` tinyint(1) NOT NULL,
  `web_new` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `groups_users`
--

CREATE TABLE `groups_users` (
  `ID` int(11) NOT NULL,
  `users_ID` int(11) NOT NULL,
  `groups_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `orders`
--

CREATE TABLE `orders` (
  `ID` int(11) NOT NULL,
  `date` datetime NOT NULL,
  `users_ID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `orders`
--

INSERT INTO `orders` (`ID`, `date`, `users_ID`) VALUES
(1, '2024-04-16 11:37:23', NULL),
(2, '2024-04-16 13:42:00', NULL);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `orders_products`
--

CREATE TABLE `orders_products` (
  `ID` int(11) NOT NULL,
  `orders_ID` int(11) NOT NULL,
  `products_ID` int(11) NOT NULL,
  `amount` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `products`
--

CREATE TABLE `products` (
  `ID` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `amount` int(11) NOT NULL,
  `picture` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `sessions`
--

CREATE TABLE `sessions` (
  `ID` int(11) NOT NULL,
  `date` datetime NOT NULL,
  `users_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `users`
--

CREATE TABLE `users` (
  `ID` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `mail` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `groups`
--
ALTER TABLE `groups`
  ADD PRIMARY KEY (`ID`);

--
-- Indizes für die Tabelle `groups_users`
--
ALTER TABLE `groups_users`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_users_groups` (`users_ID`),
  ADD KEY `FK_groups` (`groups_ID`);

--
-- Indizes für die Tabelle `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_users_orders` (`users_ID`);

--
-- Indizes für die Tabelle `orders_products`
--
ALTER TABLE `orders_products`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_orders` (`orders_ID`),
  ADD KEY `FK_products` (`products_ID`);

--
-- Indizes für die Tabelle `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`ID`);

--
-- Indizes für die Tabelle `sessions`
--
ALTER TABLE `sessions`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `sessions_users` (`users_ID`);

--
-- Indizes für die Tabelle `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `groups`
--
ALTER TABLE `groups`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT für Tabelle `groups_users`
--
ALTER TABLE `groups_users`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT für Tabelle `orders`
--
ALTER TABLE `orders`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT für Tabelle `orders_products`
--
ALTER TABLE `orders_products`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT für Tabelle `products`
--
ALTER TABLE `products`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT für Tabelle `sessions`
--
ALTER TABLE `sessions`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT für Tabelle `users`
--
ALTER TABLE `users`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `groups_users`
--
ALTER TABLE `groups_users`
  ADD CONSTRAINT `FK_groups` FOREIGN KEY (`groups_ID`) REFERENCES `groups` (`ID`),
  ADD CONSTRAINT `FK_users_groups` FOREIGN KEY (`users_ID`) REFERENCES `users` (`ID`);

--
-- Constraints der Tabelle `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `FK_users_orders` FOREIGN KEY (`users_ID`) REFERENCES `users` (`ID`);

--
-- Constraints der Tabelle `orders_products`
--
ALTER TABLE `orders_products`
  ADD CONSTRAINT `FK_orders` FOREIGN KEY (`orders_ID`) REFERENCES `orders` (`ID`),
  ADD CONSTRAINT `FK_products` FOREIGN KEY (`products_ID`) REFERENCES `products` (`ID`);

--
-- Constraints der Tabelle `sessions`
--
ALTER TABLE `sessions`
  ADD CONSTRAINT `sessions_users` FOREIGN KEY (`users_ID`) REFERENCES `users` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
