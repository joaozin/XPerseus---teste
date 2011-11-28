id_aeroportos_base(ID) :- aeroportos(base,ID).
id_aeroportos_mod(ID) :- aeroportos(modificado,ID).

id_rotas_base(ID) :- rotas(base,ID).
id_rotas_mod(ID) :- rotas(modificado,ID).

id_arestas_base(ID) :- arestas(base,ID).
id_arestas_mod(ID) :- arestas(modificado,ID).

mesma_rota(Rb,Rm,Nome) :-
	id_rotas_base(X),
	id_rotas_mod(Y),
	rota(X,Rb),
	rota(Y,Rm),
	aeroporto_rota(Rb,Nome),
	aeroporto_rota(Rm,Nome).

mesmo_aeroporto(Ab,Am,Nome) :- 
	id_aeroportos_base(X),
	id_aeroportos_mod(Y),
	aeroporto(X,Ab),
	aeroporto(Y,Am),
	nome(Ab,Nome),
	nome(Am,Nome).
	
aeroporto_vizinho(X,Y,Aresta) :- 
	aeroporto_aresta(A,Aresta),
	aeroporto_aresta(B,Aresta),
	X \== Y.

aeroporto_vizinho(Aresta) :- 
	aeroporto_aresta(X,Aresta), 
	aeroporto_aresta(Y,Aresta), 
	X \== Y.
	
comprimento_arestas_rota(X, Y) :- 
	arestas_rota(X,'[A|B]'), 
	comprimento_rota(Y,'[A|B]').	
	
comprimento_rota(0,[]).
comprimento_rota(1,[X]).
comprimento_rota(X,[A|B]) :- 
	comprimento_rota(Y,B),
	X is Y + 1.