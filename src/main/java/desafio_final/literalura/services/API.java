package desafio_final.literalura.services;

import desafio_final.literalura.entidades.libro.LibroDTO;

import java.util.List;

public class API {
    private int count;
    private Object next;
    private Object previous;
    private List<LibroDTO> results;

    // Getters y setters
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Object getNext() {
        return next;
    }

    public void setNext(Object next) {
        this.next = next;
    }

    public Object getPrevious() {
        return previous;
    }

    public void setPrevious(Object previous) {
        this.previous = previous;
    }

    public List<LibroDTO> getResults() {
        return results;
    }

    public void setResults(List<LibroDTO> results) {
        this.results = results;
    }
}
