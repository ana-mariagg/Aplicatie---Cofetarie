Create or replace function update_client_info(id_client integer, nume_nou varchar(100), prenume_nou varchar(100),
									nr_telefon_nou varchar(10), adresa_nou varchar(100),
								    id_an_nou integer, active_nou boolean)
returns void
language plpgsql  
as  
$$   
Begin  
   UPDATE public.client
	SET nume=nume_nou, prenume=prenume_nou, nr_telefon=nr_telefon_nou, adresa=adresa_nou, id_an=id_an_nou, activ=active_nou
    where id_client=client_id;
End;  
$$; 