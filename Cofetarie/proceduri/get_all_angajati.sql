Create or replace function get_all_angajati()
returns table (angajat_id integer, nume varchar(100), prenume  varchar(100), nr_telefon varchar(100),salariu integer)

language plpgsql  
as  
$$   
Begin  
   return query
   select angajat.angajat_id, angajat.nume, angajat.prenume, angajat.nr_telefon, angajat.salariu
   from angajat;
End;  
$$;  