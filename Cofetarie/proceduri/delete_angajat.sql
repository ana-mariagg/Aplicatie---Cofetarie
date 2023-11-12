Create or replace function delete_angajat(id_angajat_de_sters integer)

returns void
language plpgsql  
as  
$$   
Begin  
  delete from angajat where angajat.angajat_id = id_angajat_de_sters;
End;
$$; 