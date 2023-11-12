Create or replace function delete_praji(id_praji_de_sters integer)

returns void
language plpgsql  
as  
$$   
Begin  
  delete from prajitura where prajitura.prajitura_id = id_prajitura_de_sters;
End;  
$$; 