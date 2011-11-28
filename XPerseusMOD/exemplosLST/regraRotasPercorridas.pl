rotas_percorridas(NomeRota,A):-
	voos(A,X),
	voo(X,IdVoo),
	rota_voo(IdVoo,NomeRota),
	rotas(A,IdRotas),
	rota(IdRotas,IdRota),
	nome_rota(IdRota,NomeRota).