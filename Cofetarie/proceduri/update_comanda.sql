Create or replace function update_comanda_info(comanda_id_nou integer, nr_bucati_nou integer, produs_nou varchar(100),
									pret_nou integer, active_nou boolean)
returns void
language plpgsql  
as  
$$   
Begin  
   UPDATE public.comanda
	SET nr_bucati=nr_bucati_nou, produs=produs_noua, pret=pret_nou, activ=active_nou
    where comanda_id_nou=comanda_id;
End;  
$$; 

