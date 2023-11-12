Create or replace function update_angajat_info(angajat_id_nou integer, nume_nou varchar(100), prenume_nou varchar(100),
									nr_telefon_nou varchar(100),
								  salariu_nou integer)
returns void
language plpgsql  
as  
$$   
Begin  
   UPDATE public.angajat
	SET nume=nume_nou, prenume=prenume_nou, nr_telefon=nr_telefon_nou, salariu=salariu_nou
    where angajat_id_nou=angajat_id;
End;  
$$; 