Create or replace function delete_comanda(id_comanda_de_sters integer)

returns void
language plpgsql
as
$$
Begin
  delete from comanda where comanda.comanda_id = id_comanda_de_sters;
End;
$$;