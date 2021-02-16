ALTER TABLE estado ADD COLUMN data_atualizacao timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP;
UPDATE estado set data_atualizacao = CURRENT_TIMESTAMP;

ALTER TABLE forma_pagamento ADD COLUMN data_atualizacao timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP;
UPDATE forma_pagamento set data_atualizacao = CURRENT_TIMESTAMP;
