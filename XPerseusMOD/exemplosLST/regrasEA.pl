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

concatenar([],L,L):-!.
concatenar([X|M],L,[X|Y]):-concatenar(M,L,Y).

elim12([],L,L):-!.
elim12([X|M],L,S):-eliminaMx(L,X,T),elim12(M,T,S).

eliminaX([],_X,[]):-!.
eliminaX([X|M],X,Z):- eliminaX(M,X,Z),!.
eliminaX([R|M],X,[R|Z]):- eliminaX(M,X,Z),!.

eliminaRep([],[]):-!.
eliminaRep([X|M],S):-
	not(lista(X)),
	eliminaX(M,X,T),
	eliminaRep(T,Y),
	concatenar([X],Y,S).
eliminaRep([X|M],S):-
	elim12(X,M,T),
	eliminaRep(X,Y),
	eliminaRep(T,J),
	concatenar([Y],J,S).

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

rotas_percorridas(NomeRota):-
	voos(modificado,X),
	voo(X,IdVoo),
	rota_voo(IdVoo,NomeRota),
	rotas(modificado,IdRotas),
	rota(IdRotas,IdRota),
	nome_rota(IdRota,NomeRota).

rota_nova_nao_percorrida(NomeRota) :- 
	nome_rota(X,NomeRota), 
	nova_rota(NomeRota),
	not rotas_percorridas(NomeRota).

aeroportos_alcancaveis_rota(Visitado) :-
	voos(modificado,X),
	voo(X,IdVoo),
	rota_voo(IdVoo,NomeRota),
	rotas(modificado,T),
	rota(T,IdRota),
	nome_rota(IdRota,NomeRota),
	aeroportos_visitados(IdRota, IdVisitados),
	visitado(IdVisitados,Visitado).	

lista_aeroportos_alcancaveis(Lista):-
	findall(Visitado,aeroportos_alcancaveis_rota(Visitado),ListaVisitados),
	eliminaRep(ListaVisitados,Lista).

aeroportos_atendidos(ListaAeroportos) :- 
	findall(Visitado,visitado(_,Visitado),Lista), 
	eliminaRep(Lista,ListaAeroportos).