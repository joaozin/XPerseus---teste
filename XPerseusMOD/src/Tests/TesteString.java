package Tests;


/**
 *
 * @autor Pedro Gazzola
 */
public class TesteString {

    public static void main(String args[]) {
        System.out.println("");
        /*String joao = "mesmo_id(Fb,Fm,Nome) :-    funcionario(base,Fb),funcionario(modificado,Fm),   id(Fb,ID),id(Fm,ID),   nome(Fb,Nome)."
                + "mesmo_salario(Nome) :-    mesmo_id(Fb,Fm,Nome),salario(Fb,S),salario(Fm,S),nome(Fb,Nome)."
                + "mesmo_cargo(Nome) :- mesmo_id(Fb,Fm,Nome),   cargo(Fb,C),cargo(Fm,C),   nome(Fb,Nome)."
                + "mesmo_depto(Nome) :-    mesmo_id(Fb,Fm,Nome),   depto(Fb,D),depto(Fm,D),   nome(Fb,Nome)."
                + "mesma_filial(Nome) :-    mesmo_id(Fb,Fm,Nome),   filial(Fb,F),filial(Fm,F),   nome(Fb,Nome)."
                + "foi_promovido_e_transferido(Nome) :-foi_transferido(Nome),   foi_promovido(Nome).";

        String[] divisao = joao.split(":-");

        int j = 0;
        for(String linha : divisao){
            System.out.println(j++);
            System.out.println(linha);
        }

        //System.out.println(divisao.length);*/
    }
}
