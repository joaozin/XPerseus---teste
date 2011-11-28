mesmo_id(Fb,Fm,Nome) :- 
   funcionario(base,Fb),funcionario(modificado,Fm),
   id(Fb,ID),id(Fm,ID),
   nome(Fb,Nome).

mesmo_salario(Nome) :- 
   mesmo_id(Fb,Fm,Nome),
   salario(Fb,S),salario(Fm,S),
   nome(Fb,Nome).

mesmo_cargo(Nome) :- 
   mesmo_id(Fb,Fm,Nome),
   cargo(Fb,C),cargo(Fm,C),
   nome(Fb,Nome).

mesmo_depto(Nome) :- 
   mesmo_id(Fb,Fm,Nome),
   depto(Fb,D),depto(Fm,D),
   nome(Fb,Nome).

mesma_filial(Nome) :- 
   mesmo_id(Fb,Fm,Nome),
   filial(Fb,F),filial(Fm,F),
   nome(Fb,Nome).

recebeu_aumento(Nome) :- 
   mesmo_id(Fb,Fm,Nome),
   salario(Fb,Sb),salario(Fm,Sm),
   mesmo_cargo(Nome),
   Sb \== Sm.

foi_promovido(Nome) :-
   mesmo_id(Fb,Fm,Nome),
   salario(Fb,Sb),salario(Fm,Sm),
   cargo(Fb,Cb),cargo(Fm,Cm),
   Sb \== Sm,Cb \== Cm.
   
nova_funcao(Nome) :- 
   mesmo_id(Fb,Fm,Nome),
   mesmo_salario(Nome),
   cargo(Fb,Cb),cargo(Fm,Cm),
   Cb \== Cm.

foi_transferido(Nome) :- 
   mesmo_id(Fb,Fm,Nome),
   filial(Fb,Filb),filial(Fm,Film),
   Filb \== Film.

novo_depto(Nome) :- 
   mesmo_id(Fb,Fm,Nome),
   depto(Fb,Db),depto(Fm,Dm),
   Db \== Dm.

foi_promovido_e_transferido(Nome) :-
   foi_transferido(Nome),
   foi_promovido(Nome).





