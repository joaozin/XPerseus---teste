rotas_novas_nao_percorridas(NomeRota) :- 
	nome_rota(X,NomeRota), 
	nova_rota(NomeRota),
	not rotas_percorridas(NomeRota, A).