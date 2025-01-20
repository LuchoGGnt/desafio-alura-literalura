package desafio_final.literalura.services;

public interface IConversor {
    <T> T obtenerDatos(String json, Class<T> clase);
}
