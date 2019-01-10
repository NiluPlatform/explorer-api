package tech.nilu.platform.explorer.dto;

public class GenericResponse<T> {
    T result;

    public GenericResponse() {
    }

    public GenericResponse(T result) {
        this.result = result;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
