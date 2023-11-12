Create or replace function insert_angajat(nume_nou varchar(100), prenume_nou varchar(100),
                              nr_telefon_nou varchar(100), salariu_nou bigint )
    returns int
    language plpgsql
as  
$$
DECLARE new_id bigint;
Begin
    new_id = MAX( angajat_id ) FROM angajat;
    new_id = new_id+1;
INSERT INTO public.angajat(angajat_id,
    nume, prenume, nr_telefon,salariu)
VALUES (new_id,nume_nou, prenume_nou, nr_telefon_nou, salariu_nou) RETURNING angajat_id into new_id;
return new_id;
End;
$$;

