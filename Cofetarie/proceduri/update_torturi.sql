Create or replace function update_tort_info(id_tort integer, denumire_nou varchar(100), stoc_nou integer,
								 pret_nou integer, active_nou boolean)
returns void
language plpgsql  
as  
$$   
Begin  
   UPDATE public.tort
	SET denumire=denumire_nou, stoc=stoc_nou, pret=pret_nou, activ=active_nou
    where id_tort=tort_id;
End;  
$$; 