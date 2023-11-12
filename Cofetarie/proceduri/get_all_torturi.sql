Create or replace function get_all_torturi()
returns table (tort_id integer, denumire varchar(100), stoc integer, pret integer, active boolean)
language plpgsql  
as  
$$   
Begin  
   return query
   select tort.decoratiune_id, tort.denumire, tort.stoc,decoratiune.pret, tort.activ
   from tort;
End;  
$$;  