Create function get_all_praji()
returns table (praji_id integer, denumire varchar(100), stoc integer, pret integer, active boolean)
language plpgsql  
as  
$$   
Begin  
   return query
   select prajitura.praji_id, prajitura.denumire, prajitura.stoc,prajitura.pret, prajitura.activ
   from prajitura;
End;  
$$;  