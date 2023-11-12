Create or replace function insert_praji(denumire_nou varchar(30), stoc_nou bigint,
                               pret_nou bigint, active_nou boolean)
    returns int
    language plpgsql
as  
$$
DECLARE new_id bigint;
Begin
    new_id = MAX( praji_id ) FROM prajitura;
    new_id = new_id+1;
INSERT INTO public.prajitura( praji_id,
    denumire, stoc, pret, activ)
VALUES (new_id,denumire_nou, stoc_nou, pret_nou,active_nou) RETURNING praji_id into new_id;
return new_id;
End;
$$;

