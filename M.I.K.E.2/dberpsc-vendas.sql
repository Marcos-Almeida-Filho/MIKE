-- phpMyAdmin SQL Dump
-- version 4.7.7
-- https://www.phpmyadmin.net/
--
-- Host: dberpsc.mysql.dbaas.com.br
-- Generation Time: 20-Jul-2020 às 11:03
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
-- Estrutura da tabela `vendas_cotacao`
--

CREATE TABLE `vendas_cotacao` (
  `id` int(11) NOT NULL,
  `cotacao` varchar(20) NOT NULL,
  `cadastrado` tinyint(1) NOT NULL,
  `cliente` varchar(300) NOT NULL,
  `condicao` varchar(15) NOT NULL,
  `representante` varchar(300) NOT NULL,
  `vendedor` varchar(300) NOT NULL,
  `total` double NOT NULL,
  `status` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `vendas_cotacao_docs`
--

CREATE TABLE `vendas_cotacao_docs` (
  `id` int(11) NOT NULL,
  `cotacao` varchar(20) COLLATE latin1_general_ci NOT NULL,
  `descricao` varchar(200) COLLATE latin1_general_ci NOT NULL,
  `local` varchar(300) COLLATE latin1_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `vendas_cotacao_itens`
--

CREATE TABLE `vendas_cotacao_itens` (
  `id` int(11) NOT NULL,
  `cotacao` varchar(20) NOT NULL,
  `codigo` varchar(60) NOT NULL,
  `descricao` varchar(120) NOT NULL,
  `qtd` int(11) NOT NULL,
  `valorunitario` double NOT NULL,
  `valortotal` double NOT NULL,
  `prazo` varchar(10) NOT NULL,
  `pedido` varchar(20) NOT NULL,
  `dav` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `vendas_cotacao_obs`
--

CREATE TABLE `vendas_cotacao_obs` (
  `id` int(11) NOT NULL,
  `cotacao` varchar(20) NOT NULL,
  `data` varchar(15) NOT NULL,
  `usuario` varchar(300) NOT NULL,
  `obs` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `vendas_materiais`
--

CREATE TABLE `vendas_materiais` (
  `id` int(11) NOT NULL,
  `codigo` varchar(60) NOT NULL,
  `descricao` varchar(110) NOT NULL,
  `estoque` double NOT NULL,
  `local` varchar(100) NOT NULL,
  `status` varchar(35) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `vendas_materiais_alt`
--

CREATE TABLE `vendas_materiais_alt` (
  `id` int(11) NOT NULL,
  `idmaterial` int(11) NOT NULL,
  `data` datetime NOT NULL,
  `user` varchar(300) NOT NULL,
  `valor` varchar(200) NOT NULL,
  `valoranterior` varchar(200) NOT NULL,
  `valornovo` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `vendas_materiais_cod_cli`
--

CREATE TABLE `vendas_materiais_cod_cli` (
  `id` int(11) NOT NULL,
  `idmaterial` int(11) NOT NULL,
  `cliente` varchar(200) COLLATE latin1_general_ci NOT NULL,
  `codigo` varchar(60) COLLATE latin1_general_ci NOT NULL,
  `descricao` varchar(120) COLLATE latin1_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `vendas_materiais_doc`
--

CREATE TABLE `vendas_materiais_doc` (
  `id` int(11) NOT NULL,
  `idmaterial` int(11) NOT NULL,
  `descricao` varchar(150) COLLATE latin1_general_ci NOT NULL,
  `local` varchar(600) COLLATE latin1_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `vendas_materiais_mov`
--

CREATE TABLE `vendas_materiais_mov` (
  `id` int(11) NOT NULL,
  `idmaterial` int(11) NOT NULL,
  `qtdinicial` double NOT NULL,
  `qtdmov` double NOT NULL,
  `saldo` double NOT NULL,
  `tipo` varchar(100) NOT NULL,
  `data` date NOT NULL,
  `usuario` varchar(300) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela `vendas_materiais_obs`
--

CREATE TABLE `vendas_materiais_obs` (
  `id` int(11) NOT NULL,
  `idmaterial` int(11) NOT NULL,
  `data` date NOT NULL,
  `usuario` varchar(300) NOT NULL,
  `obs` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `vendas_cotacao`
--
ALTER TABLE `vendas_cotacao`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `vendas_cotacao_docs`
--
ALTER TABLE `vendas_cotacao_docs`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `vendas_cotacao_itens`
--
ALTER TABLE `vendas_cotacao_itens`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `vendas_cotacao_obs`
--
ALTER TABLE `vendas_cotacao_obs`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `vendas_materiais`
--
ALTER TABLE `vendas_materiais`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `codigo` (`codigo`),
  ADD UNIQUE KEY `descricao` (`descricao`);

--
-- Indexes for table `vendas_materiais_alt`
--
ALTER TABLE `vendas_materiais_alt`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `vendas_materiais_cod_cli`
--
ALTER TABLE `vendas_materiais_cod_cli`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `vendas_materiais_doc`
--
ALTER TABLE `vendas_materiais_doc`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `vendas_materiais_mov`
--
ALTER TABLE `vendas_materiais_mov`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `vendas_materiais_obs`
--
ALTER TABLE `vendas_materiais_obs`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `vendas_cotacao`
--
ALTER TABLE `vendas_cotacao`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `vendas_cotacao_docs`
--
ALTER TABLE `vendas_cotacao_docs`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `vendas_cotacao_itens`
--
ALTER TABLE `vendas_cotacao_itens`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `vendas_cotacao_obs`
--
ALTER TABLE `vendas_cotacao_obs`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `vendas_materiais`
--
ALTER TABLE `vendas_materiais`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `vendas_materiais_alt`
--
ALTER TABLE `vendas_materiais_alt`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `vendas_materiais_cod_cli`
--
ALTER TABLE `vendas_materiais_cod_cli`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `vendas_materiais_doc`
--
ALTER TABLE `vendas_materiais_doc`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `vendas_materiais_mov`
--
ALTER TABLE `vendas_materiais_mov`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `vendas_materiais_obs`
--
ALTER TABLE `vendas_materiais_obs`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
