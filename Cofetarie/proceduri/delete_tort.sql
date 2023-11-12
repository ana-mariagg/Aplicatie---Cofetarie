Create or replace function delete_tort(id_tort_de_sters integer)

returns void
language plpgsql
as
$$
Begin
  delete from trot where tort.tort_id = id_trot_de_sters;
End;
$$;