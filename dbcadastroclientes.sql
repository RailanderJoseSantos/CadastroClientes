create database dbcadastroclientes;
USE dbcadastroclientes;
CREATE TABLE IF NOT EXISTS `clientes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(40) CHARACTER SET utf8 NOT NULL,
  `telresidencial` varchar(13) NOT NULL,
  `telcomercial` varchar(13) NOT NULL,
  `telcelular` varchar(13) NOT NULL,
  `email` varchar(25) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nomeUnique` (`nome`),
  UNIQUE KEY `residUnique` (`telresidencial`),
  UNIQUE KEY `comercUnique` (`telcomercial`),
  UNIQUE KEY `celuUnique` (`telcelular`),
  UNIQUE KEY `emailUnique` (`email`)
) ENGINE=MyISAM AUTO_INCREMENT=0 DEFAULT CHARSET=latin1;