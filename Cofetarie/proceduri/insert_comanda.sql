Create or replace function insert_comanda(nr_bucati_nou integer, produs_nou varchar(100),
                              pret_nou integer, active_nou boolean)
    returns int
    language plpgsql
as  
$$
DECLARE new_id bigint;
Begin
 new_id = MAX(comanda_id) FROM comanda;
    new_id = new_id+1;
INSERT INTO public.comanda(comanda_id,
    nr_bucati, produs, pret, activ)
VALUES (new_id, new_id, produs_nou, pret_nou,active_nou) RETURNING comanda_id into new_id;
return new_id;
End;
$$;

