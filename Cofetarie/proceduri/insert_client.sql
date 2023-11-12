Create or replace function insert_client(nume_nou varchar(30), prenume_nou varchar(30),
                             nr_telefon_nou varchar(30), adresa_nou varchar(30),
							 id_an_inregistrare_nou integer, active_nou boolean)
    returns int
    language plpgsql
as  
$$
DECLARE new_id bigint;
Begin
    new_id = MAX(client_id) FROM client;
    new_id = new_id+1;
INSERT INTO public.client(client_id,
    nume, prenume, nr_telefon, adresa, id_comanda, activ)
VALUES (new_id, nume_nou, prenume_nou, nr_telefon_nou, adresa_nou,an_inregistrare_nou,active_nou) RETURNING client_id into new_id;
return new_id;
End;
$$;

