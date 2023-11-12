Create or replace function delete_client(id_client_de_sters integer)

returns void
language plpgsql  
as  
$$   
Begin  
  delete from client where client.client_id = id_client_de_sters;
End;  
$$; 