Create or replace function get_all_clienti()
returns table (client_id integer, nume varchar(100),prenume varchar(100),
			   nr_telefon varchar(100),adresa varchar(100), an_inregistrare integer,active boolean)
language plpgsql  
as  
$$   
Begin  
   return query
   select client.client_id, client.nume, client.prenume,
   client.nr_telefon, client.adresa, client.an_inregistrare, client.activ
   from client;
End;  
$$;  