id_aeroportos_base(ID) :- aeroportos(base,ID).
id_aeroportos_mod(ID) :- aeroportos(modificado,ID).

id_rotas_base(ID) :- rotas(base,ID).
id_rotas_mod(ID) :- rotas(modificado,ID).

id_voos_base(ID) :- voos(base,ID).
id_voos_mod(ID) :- voos(modificado,ID).

mesma_rota(Rb,Rm,Nome) :-
	id_rotas_base(X),
	id_rotas_mod(Y),
	rota(X,Rb),
	rota(Y,Rm),
	nome_rota(Rb,Nome),
	nome_rota(Rm,Nome).

mesmo_aeroporto(Ab,Am,Nome) :- 
	id_aeroportos_base(X),
	id_aeroportos_mod(Y),
	aeroporto(X,Ab),
	aeroporto(Y,Am),
	nome(Ab,Nome),
	nome(Am,Nome).

mesmo_voo(Vb,Vm,Numero) :-
	id_voos_base(X),
	id_voos_mod(Y),
	voo(X, Vb),
	voo(Y, Vm),
	numero_voo(Vb, Numero),
	numero_voo(Vm, Numero).