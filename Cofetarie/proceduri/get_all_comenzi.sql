Create or replace function get_all_comenzi()
returns table (comanda_id integer, nr_bucati integer, produs  varchar(100), pret integer,active boolean)

language plpgsql  
as  
$$   
Begin  
   return query
   select comanda.comanda_id, comanda.nr_bucati, comanda.produs, comanda.pret, comanda.activ
   from comanda;
End;  
$$;  