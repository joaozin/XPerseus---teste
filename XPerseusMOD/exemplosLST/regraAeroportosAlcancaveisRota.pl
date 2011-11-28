aeroportos_alcancaveis_rota(NomeRota,Visitado) :-
	voos(modificado,X),
	voo(X,IdVoo),
	rota_voo(IdVoo,NomeRota),
	rotas(modificado,T),
	rota(T,IdRota),
	nome_rota(IdRota,NomeRota),
	aeroportos_visitados(IdRota, IdVisitados),
	visitado(IdVisitados,Visitado).