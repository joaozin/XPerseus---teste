rota_nova_nao_percorrida(NomeRota) :- 
	nome_rota(X,NomeRota), 
	nova_rota(NomeRota),
	not rotas_percorridas(NomeRota, A).