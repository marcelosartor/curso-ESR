--CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

ALTER TABLE public.pedido
    ADD COLUMN codigo uuid NOT NULL DEFAULT uuid_generate_v4();
    
ALTER TABLE IF EXISTS public.pedido                                                   
    add constraint UK_codigo unique (codigo);
    
