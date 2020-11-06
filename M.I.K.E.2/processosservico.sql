-- phpMyAdmin SQL Dump
-- version 4.7.7
-- https://www.phpmyadmin.net/
--
-- Host: dberpsc.mysql.dbaas.com.br
-- Generation Time: 21-Out-2020 às 08:49
-- Versão do servidor: 5.6.35-81.0-log
-- PHP Version: 5.6.40-0+deb8u12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dberpsc`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `processosservico`
--

CREATE TABLE `processosservico` (
  `id` int(11) NOT NULL,
  `nome` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `processosservico`
--

INSERT INTO `processosservico` (`id`, `nome`) VALUES
(1, 'Corte'),
(2, 'Ponta'),
(3, 'Retífica de Desbaste'),
(4, 'Retífica de Acabamento'),
(5, 'Canal'),
(6, 'Polimento'),
(7, 'Detalonagem de Escalonado'),
(8, 'CNC'),
(9, 'Gravação'),
(10, 'Inspeção'),
(11, 'Etiquetagem e Embalagem'),
(12, 'Industrialização'),
(13, 'Inspeção de industrialização'),
(14, 'Frontal'),
(15, 'Ticar'),
(17, 'Em Revestimento'),
(18, 'Faturamento');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `processosservico`
--
ALTER TABLE `processosservico`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `processosservico`
--
ALTER TABLE `processosservico`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
