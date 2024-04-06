//package sptech.elderly.classe;
//
//import sptech.elderly.repository.Enviavel;
//
//import java.time.LocalDateTime;
//
//public class Mensagem implements Enviavel {
//    private String conteudo;
//    private LocalDateTime dataMensagem;
//
//    public Mensagem(MensagemEntity mensagemEntity) {
//        this.conteudo = mensagemEntity.getConteudo();
//        this.dataMensagem = mensagemEntity.getHorario();
//    }
//
//    @Override
//    public void enviar() {
//        System.out.println(String.format("""
//                Conteúdo: %s
//
//                HoraMensagem: %s
//                """, this.conteudo, this.dataMensagem));
//    }
//}
