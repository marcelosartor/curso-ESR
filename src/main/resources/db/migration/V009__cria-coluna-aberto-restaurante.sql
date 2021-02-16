ALTER TABLE restaurante ADD COLUMN aberto boolean NOT NULL DEFAULT false;
UPDATE restaurante set ativo = false;