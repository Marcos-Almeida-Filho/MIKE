-- phpMyAdmin SQL Dump
-- version 4.7.7
-- https://www.phpmyadmin.net/
--
-- Host: dberpsc.mysql.dbaas.com.br
-- Generation Time: 27-Ago-2020 às 09:44
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
-- Estrutura da tabela `condicaodepagamento`
--

CREATE TABLE `condicaodepagamento` (
  `id` int(11) NOT NULL,
  `nome` varchar(45) DEFAULT NULL,
  `parcelas` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `condicaodepagamento`
--

INSERT INTO `condicaodepagamento` (`id`, `nome`, `parcelas`) VALUES
(1, '35', 1),
(2, '30/45/60', 3),
(3, '28', 1),
(4, '30', 1),
(5, '45', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `condicaodepagamento`
--
ALTER TABLE `condicaodepagamento`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `condicaodepagamento`
--
ALTER TABLE `condicaodepagamento`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
