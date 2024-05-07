//package sptech.elderly.classe;
//
//import sptech.elderly.repository.Enviavel;
//
//import java.time.LocalDateTime;
//
//public class SolicitacaoServico implements Enviavel {
//
//    private LocalDateTime horario;
//    private Double preco;
//
//    public SolicitacaoServico(Compromisso compromisso) {
//        this.horario = compromisso.getHorario();
//        this.preco = preco;
//    }
//
//    @Override
//    public void enviar() {
//        System.out.println(String.format("""
//                Dia do compromisso: %s
//
//                Preço: %.2f
//                """, this.horario, this.preco));
//    }
//}
