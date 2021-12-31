package ir.shariaty.onlinetranslatorapp.model;

public class Model {

    private Data data;
    private Response response;
    public Model(Data data, Response response) {
        this.data = data;
        this.response = response;
    }

    public Data getData() {
        return data;
    }

    public Response getResponse() {
        return response;
    }
}
