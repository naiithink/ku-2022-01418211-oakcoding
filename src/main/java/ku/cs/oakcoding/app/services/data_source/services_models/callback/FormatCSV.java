package ku.cs.oakcoding.app.services.data_source.services_models.callback;

public interface FormatCSV {
    String getQuoteFormat(Object o);

    String formatCSV(Object o);
}
