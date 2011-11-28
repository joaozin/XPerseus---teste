%regras usadas para recuperar o ID do elemento raiz da base ou da versão modificada

id_aeroportos_base(ID) :- aeroportos(base,ID).
id_aeroportos_mod(ID) :- aeroportos(modificado,ID).

id_rotas_base(ID) :- rotas(base,ID).
id_rotas_mod(ID) :- rotas(modificado,ID).

id_voos_base(ID) :- voos(base,ID).
id_voos_mod(ID) :- voos(modificado,ID).

%usadas para comparar modificações entre a versão base e a modificada
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

%se tem um novo aeroporto em modificado, nao existe na base.
rota_mudou(NovoPonto):-
	mesma_rota(A,B,Nome),
	aeroportos_visitados(A, C),
	aeroportos_visitados(B, D),
	visitado(D,NovoPonto),
	not visitado(C,NovoPonto).

%se uma rota mudou, um voo que a utilize tambem muda
voo_mudou(X):-
	mesmo_voo(A,B,Numero),
	rota_voo(A,RotaA),
	rota_mudou(RotaA),
	numero_voo(A, X).

%detecta um novo aeroporto no documento
novo_aeroporto(X) :- 
	id_aeroportos_mod(ID), 
	aeroporto(ID, Y), 
	not mesmo_aeroporto(W, Y, Nome),
	nome(Y, X).

%detecta uma nova rota
nova_rota(NomeRota) :-
	id_rotas_mod(ID),
	rota(ID,Y),
	not mesma_rota(W, Y, Nome),
	nome_rota(Y,NomeRota).

%detecta um novo voo
novo_voo(X) :-
	id_voo_mod(ID),
	voo(ID,Y),
	not mesmo_voo(W,Y,Numero),
	numero(Y,X).

rotas_percorridas(NomeRota,A):-
	voos(A,X),
	voo(X,IdVoo),
	rota_voo(IdVoo,NomeRota),
	rotas(A,IdRotas),
	rota(IdRotas,IdRota),
	nome_rota(IdRota,NomeRota).

%verifica se uma nova rota nao e utilizada
rota_nova_nao_percorrida(NomeRota) :- 
	nome_rota(X,NomeRota), 
	nova_rota(NomeRota),
	not rotas_percorridas(NomeRota, A).

aeroportos_alcancaveis_rota(NomeRota,Visitado) :-
	voos(modificado,X),
	voo(X,IdVoo),
	rota_voo(IdVoo,NomeRota),
	rotas(modificado,T),
	rota(T,IdRota),
	nome_rota(IdRota,NomeRota),
	aeroportos_visitados(IdRota, IdVisitados),
	visitado(IdVisitados,Visitado).

nao_alcancados(Nome):-
	id_aeroportos_mod(ID),
	aeroporto(ID,X),
	nome(X, Nome),
	Visitado \== Nome.
	
gera_lista_rota(NomeRota,Lista):-
	findall(Visitado,aeroportos_alcancaveis_rota(NomeRota,Visitado),Lista).

tamanho([],0).			
tamanho([_|R],N):-tamanho(R,N1),N is N1 + 1.

remover(X,[X|C],C).
remover(X,[Y|C],[Y|D]):-remover(X,C,D).		
inserir(L,[],[L]). 
inserir(X,L,L1):-remover(X,L1,L),!.