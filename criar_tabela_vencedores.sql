-- =====================================================
-- Script para criar a tabela vencedores_edital
-- Execute este script no banco db_bidding_system
-- =====================================================

USE `db_bidding_system`;

CREATE TABLE IF NOT EXISTS `vencedores_edital` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_edital` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `valor_lance` decimal(10,2) NOT NULL,
  `data_registro` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_edital` (`id_edital`),
  KEY `id_usuario` (`id_usuario`),
  CONSTRAINT `vencedores_edital_ibfk_1` FOREIGN KEY (`id_edital`) REFERENCES `editais` (`id`),
  CONSTRAINT `vencedores_edital_ibfk_2` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

SELECT 'Tabela vencedores_edital criada com sucesso!' AS resultado;
