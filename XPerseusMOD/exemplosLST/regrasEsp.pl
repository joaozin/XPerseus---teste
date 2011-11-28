rota_mudou(Nome):-
	mesma_rota(A,B,Nome),
	aeroportos_visitados(A, C),
	aeroportos_visitados(B, D),
	visitado(D,NovoPonto),
	not visitado(C,NovoPonto).

voo_mudou(X):-
	mesmo_voo(A,B,Numero),
	rota_voo(A,RotaA),
	rota_mudou(RotaA),
	numero_voo(A, X).

novo_aeroporto(X) :- 
	id_aeroportos_mod(ID), 
	aeroporto(ID, Y), 
	not mesmo_aeroporto(W, Y, Nome),
	nome(Y, X).

nova_rota(NomeRota) :-
	id_rotas_mod(ID),
	rota(ID,Y),
	not mesma_rota(W, Y, Nome),
	nome_rota(Y,NomeRota).

novo_voo(X) :-
	id_voo_mod(ID),
	voo(ID,Y),
	not mesmo_voo(W,Y,Numero),
	numero(Y,X).