Create or replace function update_praji_info(id_praji integer, denumire_nou varchar(100), stoc_nou integer,
								 pret_nou integer, active_nou boolean)
returns void
language plpgsql  
as  
$$   
Begin  
   UPDATE public.praji
	SET denumire=denumire_nou, stoc=stoc_nou, pret=pret_nou, activ=active_nou
    where id_praji=praji_id;
End;  
$$; 