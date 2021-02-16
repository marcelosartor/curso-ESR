ALTER TABLE restaurante ADD COLUMN ativo boolean NOT NULL DEFAULT true;
UPDATE restaurante set ativo = true;