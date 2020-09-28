-- phpMyAdmin SQL Dump
-- version 4.7.7
-- https://www.phpmyadmin.net/
--
-- Host: dberpsc.mysql.dbaas.com.br
-- Generation Time: 20-Jul-2020 às 17:31
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
-- Estrutura da tabela `clientes`
--

CREATE TABLE `clientes` (
  `id` int(11) NOT NULL,
  `nome` varchar(45) DEFAULT NULL,
  `razaosocial` varchar(80) DEFAULT NULL,
  `cnpj` varchar(45) DEFAULT NULL,
  `ie` varchar(45) DEFAULT NULL,
  `im` varchar(20) DEFAULT NULL,
  `telefone` varchar(20) DEFAULT NULL,
  `grupo` varchar(45) DEFAULT NULL,
  `vendedor` varchar(45) DEFAULT NULL,
  `representante` varchar(45) DEFAULT NULL,
  `condicaodepagamento` varchar(20) DEFAULT NULL,
  `logradouro` varchar(60) DEFAULT NULL,
  `numero` varchar(12) DEFAULT NULL,
  `complemento` varchar(45) DEFAULT NULL,
  `bairro` varchar(45) DEFAULT NULL,
  `cidade` varchar(60) DEFAULT NULL,
  `uf` varchar(2) DEFAULT NULL,
  `cep` varchar(10) DEFAULT NULL,
  `visita` varchar(10) DEFAULT NULL,
  `idrepresentante` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Extraindo dados da tabela `clientes`
--

INSERT INTO `clientes` (`id`, `nome`, `razaosocial`, `cnpj`, `ie`, `im`, `telefone`, `grupo`, `vendedor`, `representante`, `condicaodepagamento`, `logradouro`, `numero`, `complemento`, `bairro`, `cidade`, `uf`, `cep`, `visita`, `idrepresentante`) VALUES
(1, 'AUTOCOM', 'Autocom Componentes Automotivos do Brasil LTDA', '02.582.890/0001-12', '688.155.074.111', '', '(12)3627-2000', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '35', 'Avenida Eurico Ambrogi Santos', '1200', '', 'Chácara Flórida', 'Taubaté', 'SP', '12042-010', NULL, 1),
(2, 'PIRAMIDE', 'Piramide Usinagem e Comercio de Pecas LTDA', '09.312.001/0001-46', '645522492112', '', '(12)2170-3991', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '35', 'Avenida Dois', '161', '', 'Eldorado', 'São José dos Campos', 'SP', '12238-580', NULL, 1),
(3, 'THYSSENKRUPP', 'THYSSENKRUPP AUTOMATA IND. DE PECAS LTDA', '96.163.993/0001-91', '688112055118', '', '(12)3627-2300', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'Avenida Eurico Ambrogi Santos', '1715', '', 'Chácara Flórida', 'Taubaté', 'SP', '12042-010', NULL, 1),
(4, 'OERLIKON BALZERS', 'OERLIKON BALZERS REVESTIMENTOS METALICOS LTDA', '02.044.059/0001-07', '407.228.074.118', '', '(11)2152-0464', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'Avenida Balzers', '250', '', 'Distrito Industrial', 'Jundiaí', 'SP', '13213-084', NULL, 1),
(5, 'ELEB', 'ELEB EQUIPAMENTOS LTDA', '55.763.775/0001-00', '645085863116', '', '(12)3935-5211', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '', 'Rua Itabaiana', '40', '', 'Jardim Petrópolis', 'São José dos Campos', 'SP', '12237-540', NULL, 1),
(6, 'DOVALE SJC', 'DOVALE INDUSTRIA E COMERCIO DE CHAVES LTDA', '04.630.252/0001-55', '645.467.933.119', '', '(12)3212-1000', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'RUA WILTON REIS COSTA', '70', '', 'Eldorado', 'São José dos Campos', 'SP', '12238-574', NULL, 1),
(7, 'DOVALE SAPUCAÍ', 'DOVALE INDUSTRIA E COMERCIO DE CHAVES LTDA', '04.630.252/0006-60', '002.246.974.0169', '', '(35)3655-1011', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'Rua Getulio Vargas,', '875', '', 'Centro', 'Sapucaí Mirim', 'MG', '37690-000', NULL, 1),
(8, 'TECPLAS', 'TECPLAS INDUSTRIA E COMERCIO LTDA', '56.840.077/0001-24', '645.106.684.114', '', '(12)3931-5233', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'Rua Serra do Roncador', '377', '', 'Jardim Anhembi', 'São José dos Campos', 'SP', '12235-240', NULL, 1),
(9, 'MWL', 'MWL Brasil Rodas e Eixos Ltda', '03.234.027/0001-37', '234041927111', '', '(12)3221-2400', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'Rodovia Vito Ardito', 'S/N K', '', 'Jardim Campo Grande', 'Caçapava', 'SP', '12282-535', NULL, 1),
(10, 'PAN-METAL', 'PAN-METAL INDUSTRIA METALURGICA LTDA', '48.584.510/0001-80', '148565816114', '', '(11)2264-1015', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'Rua Rizieri Negrini', '334', '', 'Sacomã', 'São Paulo', 'SP', '04257-143', NULL, 1),
(11, 'BRASILIT', 'Saint-Gobain do Brasil Produtos Insutriais p/Construção Ltda', '61.064.838/0012-96', '392211353118', '', '(12)2128-4400', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'Avenida do Cristal', '530', '', 'Jardim das Industrias', 'Jacareí', 'SP', '12311-210', NULL, 1),
(12, 'ITIBAN', 'ITIBAN INDÚSTRIA E COMÉRCIO DE FIEIRAS LTDA.EPP', '07.019.181/0001-47', '645.310.495.117', '', '(12)3931-0741', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'Rua Penha', '256', '', 'Chácaras Reunidas', 'São José dos Campos', 'SP', '12238-380', NULL, 1),
(13, 'GLOBO JAMBEIRO', 'GLOBO CENTRAL DE USINAGEM LTDA', '03.940.850/0001-68', '397055299116', '', '(12)3978-1801', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '45', 'RUA RODOLFO ALBERTO WISLING', '331A', '', 'DISTRITO INDUSTRIAL', 'Jambeiro', 'SP', '12270-000', NULL, 1),
(14, 'G&L INDUSTRIA', 'G&L INDUSTRIA E COMERCIO LTDA', '02.250.407/0001-00', '645.422.061.115', '', '(12)3933-4466', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'Rua Caravelas', '610', '', 'Jardim Vale do Sol', 'São José dos Campos', 'SP', '12238-170', NULL, 1),
(15, 'ITAKAR', 'HUMBERTO MANCILHA DIAS & CIA LTDA', '21.469.937/0001-80', '3314862970059', '', '(12)3361-2255', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'RUA DOS LAMINS', '519', '', 'CENTRO', 'Itanhandu', 'MG', '37464-000', NULL, 1),
(16, 'USIPED', 'SOUSA & SOUSA COMERCIO DE FERRAMENTAS LTDA', '09.165.407/0001-43', '645.870.231.119', '', '(12)9733-5363', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'Rua Professora Otavia Raimundo da Silva', '98', '', 'Jardim São Vicente', 'São José dos Campos', 'SP', '12224-470', NULL, 1),
(17, 'HYUNDAI', 'HYUNDAI HEAVY INDUSTRIES BRASIL', '13.837.846/0001-22', '79417866', '', '(24)3352-2338', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'RODOVIA PRES. DUTRA S/N KM 315', 'KM 315', '', 'CENTRO', 'Itatiaia', 'RJ', '27580-000', NULL, 1),
(18, 'GHM', 'G.H.M.COMERCIO E SERVICOS LTDA', '00.320.352/0001-24', '645220102118', '', '(12)3941-4292', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'Estrada Municipal Glaudiston Pereira de Oliveira', '3200', '', 'Putim', 'São José dos Campos', 'SP', '12228-010', NULL, 1),
(19, 'GLOBO BOTUCATU', 'GLOBO CENTRAL DE USINAGEM LTDA.', '03.940.850/0002-49', '224167398113', '', '(11)4992-5255', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '45', 'Rua José Lapenna', '231', '', 'Distrito Industrial', 'Botucatu', 'SP', '18608-843', NULL, 1),
(20, 'G-MAG', 'G-MAG USINAGEM LTDA', '03.940.850/0003-20', '645904970114', '', '(12)3550-9830', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '45', 'Rua Romualdo Davoli', '340', '', 'Eldorado', 'São José dos Campos', 'SP', '12238-577', NULL, 1),
(21, 'MAGNAGHI', 'MAGNAGHI AERONAUTICA DO BRASIL INDUSTRIA E COMERCIO LTDA.', '57.069.650/0001-00', '645110751114', '', '(12)3932-0888', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'Rua Januaria', '882', '', 'Chácaras Reunidas', 'São José dos Campos', 'SP', '12238-500', NULL, 1),
(22, 'IMPACT BRASIL', 'Impact Brasil Comunicação Visual', '03.359.506/0001-80', '596039459.00-31', '', '(35)3471-3808', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'Rua Parana', '76', '', 'Boa Vista', 'Santa Rita do Sapucaí', 'MG', '37540-000', NULL, 1),
(23, 'ALTO DA PONTE', 'ALTO DA PONTE USINAGEM LTDA', '00.846.768/0001-80', '645253892110', '', '(12)3941-6366', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'Rua Afrânio de Paiva Delgado', '103', '', 'Vila Veneziani', 'São José dos Campos', 'SP', '12212-350', NULL, 1),
(24, 'AISYS', 'Aisys Automacao Industrial Ltda', '02.789.201/0001-45', '645271737115', '', '(12)2139-8228', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'Avenida George Eastman', '1701', '', 'Palmeiras D Sao Jose', 'São José dos Campos', 'SP', '12237-640', NULL, 1),
(25, 'G.D FRANCO', 'G.D FRANCO-INSTALAÇÃO DE MAQUINAS E EQUIP INDUST', '18.944.122/0001-00', '645.631.690.115', '', '(12)3307-2380', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'Rua Avião Muniz', '105', 'Sala 04', 'Jardim Souto', 'São José dos Campos', 'SP', '12227-100', NULL, 1),
(26, 'PESOLA', 'Pesola Peças Usinadas Aeronauticas Ltda', '06.920.112/0001-47', '645465772110', '', '(12)3933-2923', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'Rua José de Campos', '270', '', 'Cidade Morumbi', 'São José dos Campos', 'SP', '12236-650', NULL, 1),
(27, 'LIEBHERR', 'Liebherr AeroSpace Brasil Ind Com Equip Aeronauticos Ltda', '07.419.960/0001-30', '332151052118', '', '(  )    -    ', 'Selecione', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'Rua Dr. Hans Liebher', '01', '', 'Vila Bela', 'Guaratinguetá', 'SP', '12522-640', NULL, 1),
(28, 'SAFRAN', 'Safran Cabin Brazil Ltda.', '03.361.189/0001-36', '392201924113', '', '(12)3954-0700', 'Consumidor Final', 'Roger Pereira Barreto', 'Marcos Antonio de Almeida Filho', '28', 'Avenida Getúlio Vargas', '3000', '', 'Jardim Califórnia', 'Jacareí', 'SP', '12305-000', NULL, 1),
(29, 'FEMARTE', 'METALURGICA FEMARTE INDUSTRIA E COMERCIO DE ILUMINACAO EIREL', '02.801.056/0001-70', '400007036116', '', '(11)4016-4936', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'Rua Silvio', '136', '', 'Maracana', 'Jarinu', 'SP', '13240-000', NULL, 1),
(30, 'VDS', 'VDS - USINAGEM INDUSTRIAL LTDA', '10.764.628/0001-17', '392.245.912.116', '', '(12)3961-3783', 'Selecione', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'Avenida Getúlio Vargas', '1340', '', 'Jardim Califórnia', 'Jacareí', 'SP', '12305-010', NULL, 1),
(31, 'LUFRA', 'Luiz Carlos Scornavacca Junior ME', '26.729.169/0001-70', '9078854291', '', '(  )    -    ', 'Revenda', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'Avenida República Argentina', '252', '', 'Água Verde', 'Curitiba', 'PR', '80240-210', NULL, 1),
(32, 'PIESA', 'PIESA - COMERCIO E REPRESENTACAO DE FERRAMENTAS LTDA', '22.371.605/0001-21', '645714921116', '', '(12)3943-3907', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'Rua Armando de Oliveira Cobra', '50', '', 'Parque Residencial Aquarius', 'São José dos Campos', 'SP', '12246-002', NULL, 1),
(33, 'STATUS', 'STATUS USINAGEM MECANICA LTDA - EPP', '01.049.314/0001-41', '645238819114', '', '(12)3933-4074', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'Estrada Imperador', '13', '', 'Chácaras Reunidas', 'São José dos Campos', 'SP', '12238-560', NULL, 1),
(34, 'AEROCRISTALDO', 'Aerocristaldo Ind. Com. Pecas Ltda', '08.775.626/0001-81', '185059121110', '', '(14)3816-1717', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'Av. Marginal', '200A', '', 'Distrito Industrial', 'Areiópolis', 'SP', '18670-000', NULL, 1),
(35, 'MAZATECH', 'MAZATECH INDUSTRIA LTDA ME', '12.319.394/0001-24', '224003553110', '', '(12)3942-2084', 'Consumidor Final', 'Roger Pereira Barreto', 'Marcos Antonio de Almeida Filho', '28 DDL', 'Avenida Deputado Dante Delmanto', '2413', '', 'Vila Paulista', 'Botucatu', 'SP', '18608-393', NULL, 1),
(36, 'A.S.TECHNOLOGY', 'A.S.TECHNOLOGY COMPONENTES ESPECIAIS LTDA', '01.786.547/0001-27', '645250395112', '', '(12)3929-5504', 'Consumidor Final', 'Alex Sandro de Oliveira', 'Marcos Antonio de Almeida Filho', '28', 'Rua Professora Ana Isabel Barbosa', '207', '', 'Jd. Diamante', 'São José dos Campos', 'SP', '12223-180', NULL, 1),
(37, 'LUSTRES FEMARTE', '@CTUAL ILUMINAÇÃO EIRELLI', '29.577.310/0001-55', '400.029.299.112', '', '(11)4016-5410', 'Consumidor Final', 'Alex Sandro de Oliveira', 'Marcos Antonio de Almeida Filho', '28', 'Rua Silvia', '193', '', 'Maracanã', 'Jarinu', 'SP', '13240-000', NULL, 1),
(38, 'REPATRI', 'REPATRI COMERCIO DE FERRAMENTAS LTDA. EPP', '81.029.043/0001-00', '252858930', '', '(48)3433-4415', 'Revenda', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '', 'RUA JULIA BENEDET SALVADOR', '340', '', 'Bairro Prospera', 'Criciúma', 'SC', '88811-760', NULL, 1),
(39, 'PRODMEC', 'PRODMEC ELETROMECANICA LTDA', '41.777.095/0001-31', '596.826.937-0053', '', '(35)3471-3840', 'Consumidor Final', 'Alex Sandro de Oliveira', 'Marcos Antonio de Almeida Filho', '', 'Rodovia BR 459', '50', '', 'Chácara Lagoa', 'Santa Rita do Sapucaí', 'MG', '37540-000', NULL, 1),
(40, 'MOTOPPAR', 'MOTOPPAR IND. COM. DE AUTOMATIZADORES LTDA', '52.605.821/0001-55', '315011558113', '', '(14)3407-1040', 'Consumidor Final', 'Alex Sandro de Oliveira', 'Marcos Antonio de Almeida Filho', '', 'AV. LABIENO C. MACHA', '3526', '', 'DISTRITO INDUSTRIAL', 'Garça', 'SP', '17400-000', NULL, 1),
(41, 'WATTS POLIMEROS', 'Watts Polimeros e Metais Eireli - EPP', '27.512.986/0001-35', '279042027111', '', '(16)3951-8161', 'Consumidor Final', 'Alex Sandro de Oliveira', 'Marcos Antonio de Almeida Filho', '28', 'XV de Novembro', '887', '', 'Centro', 'Cravinhos', 'SP', '14140-000', NULL, 1),
(42, 'PW INDUSTRIA', 'PW INDUSTRIA E COMERCIO', '59.819.938/0001-80', '188081572116', '', '(11)4654-8444', 'Consumidor Final', 'Jair', 'Marcos Antonio de Almeida Filho', '', 'Rua Licatem ', '420', '', 'ANEXO RUA ARUTEC 120  - JD FAZENDA RINCÃO', 'Arujá ', 'SP', '07428-280', NULL, 1),
(43, 'CARLU', 'CARLU MANUFATURADOS EIRELI', '02.443.321/0001-96', '90486399-81', '', '(45)3055-7001', 'Consumidor Final', 'Alex Sandro de Oliveira', 'Marcos Antonio de Almeida Filho', '', 'ROD BR 163 - KM 253 MAIS 100M', '253', '', 'LINHA MARRECO', 'Estado', 'PR', '85900-970', NULL, 1),
(44, 'ADV ANCED', 'ADV ANCED COMERCIO DE IMPLANTES E CONEXOES ODONTOLOGICAS LTDA', '09.362.746/0001-10', '635.066.314.110', '', '(11)4390-9222', 'Consumidor Final', 'Alex Sandro de Oliveira', 'Marcos Antonio de Almeida Filho', '', 'R PROFESSOR RUBIAO MEIRA', '363', '', 'VILA WASHINGTON', 'São Bernardo do Campo', 'SP', '09890-430', NULL, 1),
(45, 'GRAUNA', 'GRAUNA AEROSPACE LTDA', '03.011.370/0001-12', '125.040.248.114', '', '(12)3221-3500', 'Consumidor Final', 'Roger Pereira Barreto', 'Marcos Antonio de Almeida Filho', '', 'Rua Januária', '1100', '', 'Chácaras Reunidas', 'São José dos Campos  ', 'SP', '12238-500', NULL, 1),
(46, 'SOUZA E GIBIM', 'Souza E Gibim Construções LTDA', '11.944.701/0001-03', '90.519.677-44', '', '(  )    -    ', 'Consumidor Final', 'Alex Sandro de Oliveira', 'Marcos Antonio de Almeida Filho', '', 'Av Arvelino Durante', '430', '', 'Pq Industrial', 'Sabáudia', 'PR', '86720-000', NULL, 1),
(47, 'RETÍFICA ITATIBA', 'RETÍFICA ITATIBA LTDA', '56.057.086/0001-43', '382019176110', '', '(11)4894-8300', 'Consumidor Final', 'Alex Sandro de Oliveira', 'Marcos Antonio de Almeida Filho', '', 'Rua Jundiai', '730', '', 'Centro', 'Itatiba', 'SP', '13250-200', NULL, 1),
(48, 'CENTER TOOLS', 'CENTER TOOLS FERRAMENTAS DE CORTE LTDA', '60.379.575/0001-99', '112357860114', '', '(11)4894-8300', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '', 'RUA AMELIA DE CARVALHO GONCALVES', '41', '', 'SANTANA', 'SÃO PAULO', 'SP', '02013-009', NULL, 1),
(49, 'JACTO UNIPAC', 'MÁQUINAS AGRÍCOLAS JACTO S/A - UNIPAC', '55.064.562/0016-77', '548074602115', '', '(14)3405-2358', 'Consumidor Final', 'Jair', 'Marcos Antonio de Almeida Filho', '', 'RUA DR. LUIZ MIRANDA', '1700', '', 'PIRAJÁ', 'POMPÉIA', 'SP', '17580-000', NULL, 1),
(50, 'VEMAX', 'VEMAX USINAGEM - EIRELI', '03.569.026/0001-43', '224080070110', '', '(14)3813-3735', 'Consumidor Final', 'Alex Sandro de Oliveira', 'Marcos Antonio de Almeida Filho', '', 'Avenida Waldemar Vizotto', '168', '', 'Jardim Santa Eliza', 'Botucatu', 'SP', '18607-502', NULL, 1),
(51, 'COROZITA', 'FABRICA DE BOTÕES COROZITA', '72.278.880/0001-73', '688008977116', '', '(12)3634-3000', 'Consumidor Final', 'Roger Pereira Barreto', 'Marcos Antonio de Almeida Filho', '', 'Av. Marechal Deodoro', '689', '', 'CENTRO', 'TAUBATÉ', 'SP', '12080-000', NULL, 1),
(52, 'PRESSMECANICA', 'PRESSMECANICA COM E SERVICOS LTDA', '02.447.656/0001-82', '392236658119', '', '(12)3958-4053', 'Consumidor Final', 'Roger Pereira Barreto', 'Marcos Antonio de Almeida Filho', '', 'ROD. GERALDO SCAVONI', '2080', '', 'Condominio Indusvale', 'JACAREÍ', 'SP', '12305-490', NULL, 1),
(53, 'ALLTEC', 'ALLTEC IND. COMPONENTES MAT. COMPOSTOS LTDA', '00.745.309/0001-00', '645.229.712.112', '', '(12)3931-4178', 'Consumidor Final', 'Alex Sandro de Oliveira', 'Marcos Antonio de Almeida Filho', '', 'RUA MOXOTO', '456', '', 'CHACARAS REUNIDAS', 'SÃO JOSÉ DOS CAMPOS', 'SP', '12305-490', NULL, 1),
(54, 'AKAER', 'AKAER ENGENHARIA S.A.', '65.047.250/0001-22', '645841436112', '', '(12)2139-1100', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '', 'Avenida Cesare Mansueto Giulio Lattes', '501', '', 'Eugênio de Mello', 'São José dos Campos', 'SP', '12247-014', NULL, 1),
(55, 'KASMAP', 'KASMAP USINAGEM LTDA ME.', '06.993.115/0001-00', '645.468.512.110', '', '(12)2139-1100', 'Consumidor Final', 'Roger Pereira Barreto', 'Marcos Antonio de Almeida Filho', '', 'AV. ALTO DO RIO DOCE', '93', '', 'ALTOS DE SANTANA', 'São José dos Campos', 'SP', '12214-010', NULL, 1),
(56, 'E R DE ELIAS', 'E R DE ELIAS FERRAMENTAS ME', '16.838.570/0001-95', '645.390.833.112', '', '(12)3922-8950', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '', 'Rua Aparecida do Norte', '30', '', 'Vila Nova Conceição', 'São José dos Campos', 'SP', '12214-010', NULL, 1),
(57, 'WF', 'WF ESTRUTURAS & SISTEMAS LTDA', '12.775.368/0001-00', '224.082.987.112', '', '(14)3882-4071', 'Consumidor Final', 'Alex Sandro de Oliveira', 'Marcos Antonio de Almeida Filho', '', 'RUA ISAURA FERNANDES DOS SANTOS', '61', '', 'DISTRITO INDUSTRIAL', 'Botucatu', 'SP', '18608-750', NULL, 1),
(58, 'VEMAX USINAGEM', 'VEMAX USINAGEM - EIRELI', '03.569.026/0001-43', '224080070110', '', '(14)3813-3735', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'Avenida Waldemar Vizotto', '168', '', 'Jardim Santa Eliza', 'Botucatu', 'SP', '18607-502', NULL, 1),
(59, 'INVENT AUTOMACAO', 'INVENT AUTOMACAO INDUSTRIAL E USINAGEM', '11.378.919/0001-30', '0015118450098', '', '(35)3623-3702', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'Rua Engenheiro Albert Starke', '50', '', 'Distrito Industrial', 'Itajubá', 'MG', '37504-090', NULL, 1),
(60, 'JOFÉR', 'R.A. PEREIRA DOS SANTOS-ME', '08.982.778/0001-55', '548.072.210.118', '', '(14)3452-1518', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '', 'AVENIDA PERIMETRAL', '2083', '', 'DISTRITO INDUSTRIAL III', 'Pompéia', 'SP', '17580-000', NULL, 1),
(61, 'MAHLE', 'MAHLE METAL LEVE S/A', '60.476.884/0015-82', '3243373520086', '', '(35)3629-4000', 'Consumidor Final', 'Alex Sandro de Oliveira', 'Marcos Antonio de Almeida Filho', '', 'Av. Tiradentes', '251', '', 'Distrito Industrial', 'Itajubá', 'MG', '37504-088', NULL, 1),
(62, 'USIMAZA', 'USIMAZA INDUSTRIA LTDA', '09.111.405/0001-71', '125.035.430.116', '', '(12)3500-1999', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '', 'TRAVESSA QUIRINO CUSTODIO DA SILVA', '51', '', 'SANTANA', 'São José dos Campos', 'SP', '12211-181', NULL, 1),
(63, 'NIKKEYPAR', 'NIKKEYPAR COMERCIAL LTDA', '01.488.575/0001-68', '645.244.641.110', '', '(12)3932-5050', 'Consumidor Final', 'Alex Sandro de Oliveira', 'Marcos Antonio de Almeida Filho', '', 'AV. DR. JOÃO BATISTA DE SOUZA SOARES', '439', '', 'JARDIM AMERICA', 'São José dos Campos', 'SP', '12235-200', NULL, 1),
(64, 'TROYA', 'TROYA INDUSTRIA DE MAQUINAS E ENGENHARIA LTDA', '07.561.559/0001-30', '645.547.281.110', '', '(12)3935-4100', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'Avenida Cesare Mansueto Giulio Lattes', '501', '', 'Eugênio de Mello', 'São José dos Campos', 'SP', '12247-014', NULL, 1),
(65, 'USICAM', 'USICAM INDUSTRIA E COMERCIO LTDA', '03.436.236/0001-63', '286237530113', '', '(11)3296-0700', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'R. PAR. PEDRO PAULO CELESTINO', '118', '', 'PIRAPORINHA', 'DIADEMA', 'SP', '09950-360', NULL, 1),
(66, 'PACIFICO', 'PACIFICO FAUCETS USINAGEM 3D LTDA EPP', '01.697.625/0001-17', '114.981.301.114', '', '(11)2914-4886', 'Consumidor Final', 'Jair', 'Marcos Antonio de Almeida Filho', '28', 'Rua Xavier Curado', '741', '', 'Ipiranga', 'São Paulo', 'SP', '04210-100', NULL, 1),
(67, 'LADISLAU', 'LADISLAU RODRIGUES DE LIMA', '  .   .   /    -  ', '', '', '(  )    -    ', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'Rua Maria José da Silva', '261', '', 'Portal Mantiqueira', 'Caçapava', 'SP', '12290-180', NULL, 1),
(68, 'MECANICA CACAPAVA LTDA', 'MECANICA CACAPAVA LTDA', '54.304.118/0001-32', '234016773119', '', '(12)3653-1544', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'AVENIDA ADHEMAR PINTO DE SIQUEIRA', '131', '', 'VILA GALVAO', 'CACAPAVA', 'SP', '12286-325', NULL, 1),
(69, 'TECHIMPORT', 'TECHIMPORT TECNOLOGIA EM IMPLANTES ORTOPEDICOS LTDA - EPP', '15.524.734/0001-47', '587162261113', '', '(19)3522-9500', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'ROD WASHINGTON LUIZ KM 172 (RUA 06) LOTE B-8', '172', '', 'JD ANHANGUERA', 'RIO CLARO', 'SP', '13501-600', NULL, 1),
(70, 'LATECOERE', 'LATECOERE DO BRASIL INDUSTRIA AERONAUTICA LTDA', '06.201.952/0001-50', '392227213110', '', '(12)2128-9033', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'Avenida Getúlio Vargas', '3320', '', 'Jardim Califórnia', 'Jacareí', 'SP', '12305-010', NULL, 1),
(71, 'AVIBRAS', 'AVIBRAS INDUSTRIA AEROESPACIAL S/A', '60.181.468/0005-85', '392028949113', '', '(12)3955-6000', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'ROD TAMOIOS KM 14 ESTR.VARADOURO', '1200', '', 'ZONA RURAL', 'JACAREI', 'SP', '12315-020', NULL, 1),
(72, 'BRANE', 'BRANE USINAGEM', '26.268.892/0001-08', '645.802.802.113', '', '(12)3019-9718', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'Rua Loanda', '554', '', 'Chácaras Reunidas', 'São José dos Campos', 'SP', '12238-330', NULL, 1),
(73, 'FESC INDUSTRIA E COMERCIO', 'FESC INDUSTRIA E COMERCIO DE FERRAMENTAS EIRELI', '03.651.281/0001-30', '637.241.771.116', '', '(16)3375-6949', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'Rua Aldo Germano Klein', '371', '', 'CEAT', 'São Carlos', 'SP', '13573-470', NULL, 1),
(74, 'FERCORTE', 'Fercorte Ferramentas', '16.838.570/0001-95', '645.390.833.112', '', '(12)3922-8950', 'Revenda', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'Rua Aparecida do norte', '30', '', 'Vila Nova Conceição', 'São José dos Campos', 'SP', '12231-080', NULL, 1),
(75, 'PROLIND', 'PROLIND PRODUTOS AUTOMOTIVOS LTDA', '34.661.409/0001-70', '125.043.761.114', '', '(12)3908-5999', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'Rod. Presidente Dutra  KM133', '', '', 'Eugenio de Melo', 'São José dis Campos', 'SP', '12247-004', NULL, 1),
(76, 'HENRIQUE CASTIGLIONE', 'HENRIQUE CASTIGLIONE COMÉRCIO DE FERRAMENTAS LTDA', '12.029.091/0001-77', '336.944.864.117', '', '(11)2358-4644', 'Revenda', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'AV.DR TIMOTEO PENTEADO SALA 14', '3402', '', 'vila galvão', 'Guarulhos', 'SP', '07061-001', NULL, 1),
(77, 'ELETRISOL', 'ELETRISOL INDUSTRIA E COMERCIO LTDA', '56.990.468/0001-25', '388044410111', '', '(11)4961-1725', 'Consumidor Final', 'Alex Sandro de Oliveira', 'Marcos Antonio de Almeida Filho', '28', 'Estrada Municipal Benedito de Souza', '341', '', 'GALPÃO 03 - DA MINA', 'ITUPEVA', 'SP', '13295-000', NULL, 1),
(78, 'FERC METAL', 'FERC METAL Soluções em Usinagem LTDA', '67.598.474/0001-58', '535167147115', '', '(19)3421-7525', 'Revenda', 'Alex Sandro de Oliveira', 'Marcos Antonio de Almeida Filho', '28', 'AVENIDA JOÃO TEODORO,', '423', '', 'VILA RESENDE', 'PIRACICABA', 'SP', '13405-240', NULL, 1),
(79, 'SPEED CUT', 'Speed Cut Ind. Com. Ferr. LTDA', '05.576.671/0001-19', '', '', '(12)3653-6863', 'Consumidor Final', 'Alex Sandro de Oliveira', 'Marcos Antonio de Almeida Filho', '35', 'Rua Manuel Nunes da Costa', '271', '', 'Vila Galvão', 'Caçapava', 'SP', '12286-300', NULL, 1),
(80, 'APTIV', 'APTIV MANUFATURA E SERVICOS DE DISTRIBUICAO LTDA.', '00.857.758/0012-01', '4739623320350', '', '(35)3659-1140', 'Consumidor Final', 'Alex Sandro de Oliveira', 'Marcos Antonio de Almeida Filho', '28', 'Avenida Avelino Ribeiro', '900', '', 'Distrito Industrial', 'Paraisópolis ', 'SP', '37660-000', NULL, 1),
(81, 'RETÍFICA ITATIBA', 'RETÍFICA ITATIBA LTDA', '56.057.086/0001-43', '382019176110', '', '(14)4894-8300', 'Consumidor Final', 'Alex Sandro de Oliveira', 'Marcos Antonio de Almeida Filho', '28', 'Rua Jundiai', '730', '', 'Cenro', 'Itatiba', 'SP', '13250-200', NULL, 1),
(82, 'EMBRAER Botucatu', 'Embraer Empresa Brasileira de Aeronautica S/A', '07.689.002/0003-40', '224999988111', '', '(14)3811-2052', 'Consumidor Final', 'Alex Sandro de Oliveira', 'Marcos Antonio de Almeida Filho', '28', 'Av Alcides Cagliari', '2281', '', 'Aeroporto', 'Botucatu', 'SP', '18606-900', NULL, 1),
(83, 'ELECTRIC INK', 'Electric Ink Ind. Com.  Importação e Exportação Ltda.EPP', '08.244.232/0001-05', '001.015.986.0024', '', '(34)3312-8788', 'Consumidor Final', 'Alex Sandro de Oliveira', 'Marcos Antonio de Almeida Filho', '28', 'Av: Coronel Zacarias Borges de Araújo', '1200', '', 'Distrito Industrial 2', 'Uberaba', 'SP', '38064-700', NULL, 1),
(84, 'REITZ', 'REITZ INDÚSTRIA MECÂNICA LTDA', '92.795.368/0001-01', '096/0238034', '', '(51)3349-8000', 'Consumidor Final', 'Alex Sandro de Oliveira', 'Marcos Antonio de Almeida Filho', '28', 'RUA JOSÉ PARIS', '256', '', 'SARANDI', 'PORTO ALEGRE', 'RS', '91140-310', NULL, 1),
(85, 'A Z FER', 'A Z FER COMERCIAL LTDA', '02.821.488/0001-43', '412014487112', '', '(15)3283-4025', 'Revenda', 'Alex Sandro de Oliveira', 'Marcos Antonio de Almeida Filho', '28', 'RUA LUIZ ROVAI ', '287', '', 'VILA CAMPACCI', 'Laranjal Paulista', 'SP', '18500-000', NULL, 1),
(86, 'OK USINAGEM', 'OK INDUSTRIAL USINAGEM ECOMERCIO LTDA - ME', '07.789.524/0001-52', '0010004940040', '', '(  )    -    ', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'Rua Albert Starke', '95', '', 'Distrito Industrial', 'Itajuba', 'MG', '37504-090', NULL, 1),
(87, 'SOBRAER', 'SOBRAER - SONACA BRASILEIRA AERONÀUTICA LTDA', '04.059.223/0001-85', '645414218114', '', '(12)4009-8512', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'AV. DOUTOR JOAO BATISTA DE SOUZA SOARES', '4009', '', 'COLONIA PARAISO', 'São José dos Campos', 'SP', '12236-660', NULL, 1),
(88, 'MIRAGE', 'MIRAGE INDUSTRIA E COM. DE PEÇAS LTDA', '47.567.797/0001-77', '645.043.558.111', '', '(12)3931-2888', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'RUA LUCELIA', '917', '', 'CHACARAS REUNIDAS', 'São José dos Campos', 'SP', '12238-450', NULL, 1),
(89, 'SYMAQ BOTUCATU', 'SYMAQ INDÚSTRIA MECÂNICA LTDA (FILIAL BOTUCATU)', '60.071.826/0002-54', '224.122.180.118', '', '(14)3814-1555', 'Consumidor Final', 'Alex Sandro de Oliveira', 'Marcos Antonio de Almeida Filho', '28', 'AV. DEPUTADO DANTE DELMANTO', '2438', '', 'VILA PAULISTA', 'Botucatu', 'SP', '18608-393', NULL, 1),
(90, 'BOZZA', 'José Murília Bozza Comércio e Indústria Ltda', '61.103.669/0001-01', '635.018.913.115', '', '(11)2179-9966', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'Rua Tiradentes, 931 Com Anita Franchini', '50', '', 'Santa Terezinha', 'São Bernardo do Campo', 'SP', '09780-001', NULL, 1),
(91, 'SECO TOOLS', 'SECO TOOLS IND. COM. LTDA', '59.108.308/0001-06', '669464632110', '', '(15)2101-5600', 'Consumidor Final', 'Alex Sandro de Oliveira', 'Marcos Antonio de Almeida Filho', '28', 'AV. JOHN BOYD DUNLOP', '1500', '', 'IPORANGA', 'SOROCABA', 'SP', '18087-155', NULL, 1),
(92, 'HELIBOMBAS ', 'HELIBOMBAS IND. E COM. DE EQUIPAMENTOS HIDRÁULICOS LTDA', '01.679.707/0001-39', '181.123.474.111', '', '(16)3334-5252', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'R. PROF. LUIZ CARLOS DÓRIA TEIXEIRA DE CAMARGO', '536', '', 'JARDIM REGINA', 'ARARAQUARA', 'SP', '14808-116', NULL, 1),
(93, 'EMDEP', 'Emdep Brasil Industria e Comercio Ltda', '02.816.578/0001-46', '3249903100041', '', '(35)3629-9350', 'Consumidor Final', 'Alex Sandro de Oliveira', 'Marcos Antonio de Almeida Filho', '28', 'Av. Pocos de Caldas', '1133', '', 'Distrito Industrial', 'Itajubá', 'MG', '37504-126', NULL, 1),
(94, 'SHIMATOOLS', 'SHIMATOOLS SHIRLEY E MARTIM COMERCIO DE FERRAMENTAS LTDA EPP', '17.401.065/0001-41', '626470028111', '', '(11)2325-8970', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'Rua Giuseppe Lorenzini', '245', '', 'Jd. Las Vegas', 'Santo Andre', 'SP', '09182-430', NULL, 1),
(95, 'CR USINAGEM', 'CLAUDEMIR RIBEIRO USINAME ME', '05.583.823/0001-00', '645607841113', '', '(  )    -    ', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'R. Lagoa Santa ', '580', '', 'Chacarás Reunidas', 'São José dos Campos', 'SP', '12238-340', NULL, 1),
(96, 'LIMA & BONFA', 'LIMA & FONFA IND.COM. DE FERRAMENTAS LTDA', '00.446.330/0001-05', '621.206.130.117', '', '(19)3922-4252', 'Consumidor Final', 'Alex Sandro de Oliveira', 'Marcos Antonio de Almeida Filho', '28', 'RUA GUIDO SEGALHO', '375', '', '', 'SUMARÉ', 'SP', '13180-510', NULL, 1),
(97, 'PowerTools', 'POWER TOOLS COMÉRCIO DE FERRAMENTAS LTDA. EPP', '01.109.772/0001-29', '86.271.788', '', '(21)2481-8737', 'Revenda', 'Alex Sandro de Oliveira', 'Marcos Antonio de Almeida Filho', '28', 'AV. EMBAIXADOR ABELARDO BUENO, SALA 251 BL01', '1110', '', 'BARRA DA TIJUCA', 'Rio de Janeiro', 'RJ', '22775-039', NULL, 1),
(98, 'WKZ', 'WKZ SOLUCOES TECNOLOGICAS LTDA EPP', '10.924.780/0001-10', '669642210110', '', '(15)3018-9999', 'Revenda', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'RUA PAES DE LINHARES', '1007', '', 'Jd. Brasilandia', 'Sorocaba', 'SP', '18075-630', NULL, 1),
(99, 'Traumédica', 'TRAUMÉDICA INSTRUMENTAIS E IMPLANTES LTDA', '72.763.733/0001-99', '244491386113', '', '(19)3265-0874', 'Consumidor Final', 'Alex Sandro de Oliveira', 'Marcos Antonio de Almeida Filho', '28', 'Rua Horácio Coutinho dos Santos (Antiga Rua Dois) n° 233', '233', '', 'Pq. Industrial Lisboa', 'campinas', 'SP', '13052-774', NULL, 1),
(100, 'Pachioni', 'FERNANDO JOSE PACHIONI FERRAMENTAS - ME', '07.581.817/0001-40', '438225059112', '', '(14)3425-5238', 'Revenda', 'Alex Sandro de Oliveira', 'Marcos Antonio de Almeida Filho', '28', 'RUA FRANCISCO RODRIGUES SOUTO', '225', '', 'PALMITAL', 'Marília', 'SP', '17511-398', NULL, 1),
(101, 'ALD TOOLS', 'ALD Tools Comércio e Serviços Eireli – ME', '27.616.381/0001-94', '645.953.448.112', '', '(12)2477-3373', 'Revenda', 'Alex Sandro de Oliveira', 'Marcos Antonio de Almeida Filho', '28', 'Rua Pimenteiras ', '208', 'Sala 04', 'Parque Industrial', 'são josé dos campos', 'SP', '12235-750', NULL, 1),
(102, 'Tecnovale', 'Tecnovale Industrial Ltda EPP', '71.708.507/0001-42', '645199256110', '', '(12)3934-1188', 'Consumidor Final', 'Alex Sandro de Oliveira', 'Marcos Antonio de Almeida Filho', '28', 'Rua Guarapiranga', '161', '', 'Chacaras Reunidas', 'são josé dos Campos', 'SP', '12348-470', NULL, 1),
(103, 'FASTWORK', 'FASTWORK PROGRAM SYSTEMS LTDA', '02.465.954/0001-03', '535.223.989.118', '', '(  )    -    ', 'Consumidor Final', 'Roger Pereira Barreto', 'Marcos Antonio de Almeida Filho', '28', 'Rua Antônio Borja Medina1250', '1250', '', 'Unileste', 'PIRACICABA', 'SP', '13422-010', NULL, 1),
(104, 'PAUL-THI', 'PAUL-THI COMERCIAL IMPORTADORA DE FERRAMENTAS EIRELI', '59.372.755/0001-60', '112.177.782.113', '', '(  )    -    ', 'Selecione', 'Alex Sandro de Oliveira', 'Marcos Antonio de Almeida Filho', '28', 'Rua Pedro Vicente', '261', '', 'LUZ', 'SÃO PAULO', 'SP', '01109-010', NULL, 1),
(105, 'MG Ind', 'MG Ind. Com. de Equipamentos Para Embalagem Ltda', '50.022.367/0001-84', '110695350115', '', '(11)2292-2400', 'Selecione', 'Alex Sandro de Oliveira', 'Marcos Antonio de Almeida Filho', '28', 'Rua Joao Antonio de Oliveira', '1036', '', 'Mooca', 'são paulo', 'SP', '03111-001', NULL, 1),
(106, 'Almax', 'Almax Comercio de Ferramentas Ltda.', '11.269.288/0001-10', '148.858.450.115', '', '(11)3229-9422', 'Revenda', 'Alex Sandro de Oliveira', 'Marcos Antonio de Almeida Filho', '28', 'José Paulino', '226', '', 'Bom Retiro', 'São Paulo', 'SP', '01120-000', NULL, 1),
(107, 'FERBEL', 'FERBEL INDUSTRIA, COMERCIO E SERV. DE FERRAMENTAS LTDA EPP', '56.800.782/0001-06', '645.109.841.115', '', '(12)3921-6900', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'Rua Tenente benedito dias pereira', '8', '', 'JD Colorado', 'SÃO JOSÉ DOS CAMPOS', 'SP', '12227-760', NULL, 1),
(108, 'ELETRO AQUILA', 'Carlos Henrique Dell Aquila EPP', '52.330.222/0001-76', '645078139118', '', '(12)3921-3494', 'Revenda', 'Roger Pereira Barreto', 'Marcos Antonio de Almeida Filho', '35', 'Rua Rubiao Junior', '351', '', 'Centro', 'São José dos Campos', 'SP', '12210-180', NULL, 1),
(109, 'Sew Eurodrive', 'SEW-EURODRIVE BRASIL LTDA', '50.981.018/0020-52', '353262261114', '', '(  )    -    ', 'Selecione', 'Alex Sandro de Oliveira', 'Marcos Antonio de Almeida Filho', '28', 'ESTRADA MUNICIPAL JOSE RUBIM', '205', '', 'Complexo Industrial', 'Indaiatuba', 'SP', '13347-510', NULL, 1),
(110, 'JS', 'JAROSLAV SLATINSKY', '60.236.338/0001-79', '688081433110', '', '(12)3633-5311', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'Estrada Municipal Itapecirica', 'S/N', '', 'Itapecirica', 'Taubaté', 'SP', '12095-000', NULL, 1),
(111, 'PRK', 'INDUSTRIA DE ARTEFATOS PLASTICOS - PRK - LTDA', '07.418.781/0001-88', '688.281.390.117', '', '(12)3827-8710', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'Avenida Charles Schnneider', '0', '', 'Parque Senhor do Bonfim', 'Taubaté', 'SP', '12040-001', NULL, 1),
(112, 'Andrax Campinas', 'Andrax Comercio e Servicos Ltda', '04.716.758/0001-81', '244.999.877.111', '', '(19)3307-6660', 'Revenda', 'Alex Sandro de Oliveira', 'Marcos Antonio de Almeida Filho', '28', 'RUA SALTO GRANDE', '306', '', 'JARDIM DO TREVO', 'Campinas', 'SP', '13040-001', NULL, 1),
(113, 'Imake', 'IMAKE INDUSTRIA E COMERCIO DE PRODUTOS PLÁSTICOS LTDA', '49.293.194/0001-50', '109.862.292.110', '', '(11)2919-3833', 'Consumidor Final', 'Alex Sandro de Oliveira', 'Marcos Antonio de Almeida Filho', '28', 'AV. FORTE DO LEME', '107', '', 'SÃO MATEUS', 'são paulo', 'SP', '08340-010', NULL, 1),
(114, 'PLANIFER', 'PLANIFER-FERRAMENTARIA E ESTAMPARIA LTDA', '58.903.915/0001-97', '244319964114', '', '(19)3112-1150', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'Rodovia Dom Pedro I', '71', '', 'Terminal Intermodal de Cargas (TIC)', 'Campinas', 'SP', '13069-200', NULL, 1),
(115, 'Drava', 'DRAVA METAIS LTDA', '54.248.968/0001-60', '111260107110', '', '(11)2181-2000', 'Consumidor Final', 'Alex Sandro de Oliveira', 'Marcos Antonio de Almeida Filho', '28', 'Rua Arnaldo Magniccaro', '230', '', 'Vila Gea', 'São Paulo', 'SP', '04691-060', NULL, 1),
(116, 'USICAD', 'USICAD USINAGEM VDP LTDA - ME', '20.135.362/0001-05', '645.659.897.117', '', '(12)3341-1832', 'Consumidor Final', 'Gabriel Blachi', 'Marcos Antonio de Almeida Filho', '28', 'Rua Búzios', '211', '', 'Chácaras Reunidas', 'São José dos Campos', 'SP', '     -   ', NULL, 1),
(117, 'Lomavir ', 'USINAGEM LOMAVIR LTDA EPP', '45.389.582/0001-88', '645.118.887.116', '', '(12)3931-3538', 'Consumidor Final', 'Alex Sandro de Oliveira', 'Marcos Antonio de Almeida Filho', '28', 'RUA ADOLPHO GOLL', '53', '', 'JARDIM MORUMBI', 'São José dos Campos ', 'SP', '12236-842', NULL, 1),
(118, 'MZK FERRAMENTAS', 'M.CARDOSO - FERRAMENTAS', '08.904.622/0001-56', '582741788118', '', '(16)3636-7476', 'Consumidor Final', 'Gabriel Blachi', 'Marcos Antonio de Almeida Filho', '28', 'Rua Campinas', '2145', '', 'Vila Carvalho', 'Ribeirão Preto', 'SP', '14075-070', NULL, 1),
(119, 'Nova Solução', 'NOVA SOLUÇÃO COMÉRCIO DE FERRAMENTAS EIRELI - EPP', '24.447.520/0001-05', '198.012.323.113', '', '(21)3687-9579', 'Revenda', 'Alex Sandro de Oliveira', 'Marcos Antonio de Almeida Filho', '28', 'Av. Bom Jesus - 1º andar - Sala 7', '141', '', 'Centro', 'Bananal', 'SP', '12850-000', NULL, 1),
(120, 'Baumer', 'BAUMER S.A.', '61.374.161/0001-30', '456.056.910.118', '', '(19)3805-7696', 'Consumidor Final', 'Alex Sandro de Oliveira', 'Marcos Antonio de Almeida Filho', '28', 'AV. Pref. Antonio Tavares Leite', '181', '', 'Parque da Empresa', 'Moji Mirim', 'SP', '13803-330', NULL, 1),
(121, 'Inbra', 'INBRA-AEROSPACE IND. COM. DE COMPOSTOS AERONAUTICOS S.A.', '05.254.436/0001-20', '442189160118', '', '(11)2148-8600', 'Consumidor Final', 'Alex Sandro de Oliveira', 'Marcos Antonio de Almeida Filho', '28', 'AV. PAPA JOÃO XXIII, 5.153 – LOTE 03 – GALPÃO 03', '5.153', '', 'SERTÃOZINHO', 'Mauá ', 'SP', '09370-800', NULL, 1),
(122, 'BFTECH', 'BFTECH INDUSTRIA, COMERCIO E SERVICOS EIRELI', '13.363.534/0001-24', '001744813.00-64', '', '(34)3314-2528', 'Consumidor Final', 'Gabriel Blachi', 'Marcos Antonio de Almeida Filho', '28', 'Rua Elias Ferreira', '72', '', 'Cidade Jardim', 'Uberaba', 'MG', '38030-035', NULL, 1),
(123, 'Mec tec', 'MEC TEC EQUIPAMENTOS HIDRÁULICOS E MECÂNICOS LTDA', '03.875.122/0001-10', '492374528117', '', '(11)3683-5997', 'Consumidor Final', 'Alex Sandro de Oliveira', 'Marcos Antonio de Almeida Filho', '28', 'Av. Marechal Rondon ', '1388', 'Pd A1', 'Centro', 'Osasco', 'SP', '06093-010', NULL, 1),
(124, 'Pan-Metal Taubaté', 'PAN-METAL INDUSTRIA METALÚRGICA LTDA', '48.584.510/0003-41', '688.412.100.117', '', '(12)3627-0000', 'Consumidor Final', 'Gabriel Blachi', 'Marcos Antonio de Almeida Filho', '28', 'AV. EURICO AMBROGI SANTOS', '1300', '', 'LOTE INDÚSTRIAL PIRACANGAGUA', 'JD. SANTA TEREZA', 'SP', '12042-010', NULL, 1),
(125, 'STANDARD', 'USINAGEM STANDARD LTDA - EPP', '23.688.693/0001-52', '645.750.148.110', '', '(12)3204-8163', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'Rua: Viseu', '184', '', 'Chácaras Reunidas', 'São José dos Campos', 'SP', '12238-550', NULL, 1),
(126, 'Fame', 'Fame Fab Apar Mat Eletricos Ltda', '60.620.366/0001-95', '102456810111', '', '(11)3478-5600', 'Consumidor Final', 'Alex Sandro de Oliveira', 'Marcos Antonio de Almeida Filho', '28', 'Rua Cajuru', '746', '', 'Belenzinho', 'São Paulo', 'SP', '03057-000', NULL, 1),
(127, 'RUDLOFF ', 'RUDLOFF INDUSTRIAL LTDA', '61.425.963/0001-21', '104.203.440.110', '', '(11)2083-4500', 'Consumidor Final', 'Alex Sandro de Oliveira', 'Marcos Antonio de Almeida Filho', '28', 'Rua Bogaert', '64', '', 'Vila Vermelha', 'São Paulo', 'SP', '04298-020', NULL, 1),
(128, 'Geraldo Orlando da Silva Neto', 'Geraldo Orlando da Silva Neto', '  .   .   /    -  ', '', '', '(  )    -    ', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'Rua Airto Pelogia', '440', '', 'Conjunto Residencial Galo Branco', 'São José dos Campos', 'SP', '12247-551', NULL, 1),
(129, 'PLASTILANIA ', 'PLASTILANIA INDUSTRIA E COMERCIO DE PLASTICOS LTDA', '03.620.142/0001-40', '115.626.532.115', '', '(11)6967-1330', 'Consumidor Final', 'Alex Sandro de Oliveira', 'Marcos Antonio de Almeida Filho', '28', 'Rua Lopes da Costa', '580', '', 'Chácara São João', 'São Paulo', 'SP', '02279-060', NULL, 1),
(130, 'ROSENBERGER DOMEX', 'ROSENBERGER DOMEX TELECOMUNICACOES LTDA.', '54.821.137/0001-36', '234043780115', '', '(12)3222-8500', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'AV CABLETECH', '601', '', 'GUAMIRIM', 'CACAPAVA', 'SP', '12295-230', NULL, 1),
(131, 'STAMPWAY', 'STAMPWAY FERRAMENTARIA E CONFORMACAO DE METAIS EIRELI', '19.036.355/0001-69', '417363202117', '', '(19)3033-0403', 'Consumidor Final', 'Gabriel Blachi', 'Marcos Antonio de Almeida Filho', '28', 'AV AMBROSIO FUMAGALLI', '1168', '', 'PARQUE EGISTO RAGAZZO', 'Limeira', 'SP', '13485-333', NULL, 1),
(132, 'LIESPE', 'LIESPE FERRAMENTARIA EIRELI - EPP', '09.383.272/0001-92', '535433413110', '', '(00)3414-3839', 'Consumidor Final', 'Gabriel Blachi', 'Marcos Antonio de Almeida Filho', '28', 'RUA JOAO FRANCO DE OLIVEIRA', '1136', '', 'UNILESTE', 'PIRACICABA', 'SP', '13422-160', NULL, 1),
(133, 'Legado', 'LEGADO USINAGEM LTDA - ME', '05.947.370/0001-54', '645.453.997.118', '', '(12)3388-0004', 'Consumidor Final', 'Alex Sandro de Oliveira', 'Marcos Antonio de Almeida Filho', '28', 'RUA JAGUARÃO', '296', '', 'CHACARAS REUNIDAS', 'são josé dos Campos ', 'SP', '12238-410', NULL, 1),
(134, 'Plasteam', 'PLASTEAM IND. COM. DE PLASTICO LTDA', '00.789.758/0001-50', '645231104119', '', '(12)3934-1559', 'Consumidor Final', 'Alex Sandro de Oliveira', 'Marcos Antonio de Almeida Filho', '28', 'RUA MONTE AZUL', '280', '', 'CHACARAS REUNIDAS', 'são José dos Campos', 'SP', '12238-350', NULL, 1),
(135, 'A L M INDUSTRIA E COMERCIO', 'A L M INDUSTRIA E COMERCIO DE FERRAMENTAS EIRELLI', '27.977.931/0001-09', '05.392.795-8', '', '(92)3085-4557', 'Consumidor Final', 'Danilo Serafim de Jesus', 'Marcos Antonio de Almeida Filho', '28', 'RUA BOTSUANA', '20', 'QUADRA 89', 'NOVA CIDADE', 'MANAUS', 'AM', '69097-348', NULL, 1),
(136, 'EMBRAER', 'YABORA INDUSTRIA AERONAUTICA S.A.', '30.657.250/0001-60', '645975628111', '', '(12)3313-3720', 'Consumidor Final', 'Gabriel Blachi', 'Marcos Antonio de Almeida Filho', '30', 'Avenida Brigadeiro Faria Lima', '2170', '', 'Putim', 'São José dos Campos', 'SP', '12227-901', NULL, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `clientes`
--
ALTER TABLE `clientes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=137;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
