-- phpMyAdmin SQL Dump
-- version 4.7.7
-- https://www.phpmyadmin.net/
--
-- Host: dberpsc.mysql.dbaas.com.br
-- Generation Time: 27-Ago-2020 às 09:45
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
-- Estrutura da tabela `representantes`
--

CREATE TABLE `representantes` (
  `id` int(11) NOT NULL,
  `status` varchar(45) DEFAULT NULL,
  `nome` varchar(45) DEFAULT NULL,
  `cpf` varchar(45) DEFAULT NULL,
  `rg` varchar(45) DEFAULT NULL,
  `nomeempresa` varchar(45) DEFAULT NULL,
  `cnpj` varchar(45) DEFAULT NULL,
  `logradouro` varchar(45) DEFAULT NULL,
  `numero` varchar(45) DEFAULT NULL,
  `complemento` varchar(45) DEFAULT NULL,
  `bairro` varchar(45) DEFAULT NULL,
  `cidade` varchar(45) DEFAULT NULL,
  `uf` varchar(45) DEFAULT NULL,
  `cep` varchar(45) DEFAULT NULL,
  `regiao` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `representantes`
--

INSERT INTO `representantes` (`id`, `status`, `nome`, `cpf`, `rg`, `nomeempresa`, `cnpj`, `logradouro`, `numero`, `complemento`, `bairro`, `cidade`, `uf`, `cep`, `regiao`) VALUES
(1, 'Ativo', 'Marcos Antonio de Almeida Filho', '389.085.748-59', '46.469.111-4', 'Speed Cut', '05.576.671/0001-19', 'Rua Manuel Nunes da Costa', '271', '', 'Vila Galvão', 'Caçapava', 'SP', '12286-300', 'Vale do Paraíba');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `representantes`
--
ALTER TABLE `representantes`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `representantes`
--
ALTER TABLE `representantes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
