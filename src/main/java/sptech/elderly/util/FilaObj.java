package sptech.elderly.util;

public class FilaObj<T> {

    private T[] fila;
    private int tamanho;

    public FilaObj(int capacidade) {
        this.fila = (T[]) new Object[capacidade];
        this.tamanho = 0;
    }

    public boolean isEmpty() {
        return tamanho == 0;
    }

    public boolean isFull() {
        return tamanho == fila.length;
    }

    public void insert(T info) {
        if (isFull()) {
            throw new IllegalStateException("Fila cheia");
        }

        fila[tamanho++] = info;
    }

    public T peek() {
        if (!isEmpty()) {
            return fila[0];
        }

        return null;
    }

    public T poll() {
        T primeiro = fila[0];

        if(!isEmpty()) {
            for (int i = 0; i < tamanho - 1; i++) {
                fila[i] = fila[i + 1];
            }

            fila[--tamanho] = null;
        }

        return primeiro;
    }

    public void exibe() {
        for (int i = 0; i < tamanho; i++) {
            System.out.println(fila[i] + " ");
        }
    }

    public int getTamanho(){
        return tamanho;
    }

    public T[] getFila() {
        return fila;
    }
}
