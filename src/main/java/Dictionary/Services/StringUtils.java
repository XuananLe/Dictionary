package Dictionary.Services;

public class StringUtils {
    public static String normalizeString(String searchTerm) {
        searchTerm = searchTerm.toLowerCase();
        searchTerm = searchTerm.substring(0, 1).toUpperCase() + searchTerm.substring(1);
        searchTerm = searchTerm.strip();
        searchTerm = searchTerm.trim();
        return searchTerm;
    }

    public static String normalizeStringForSearch(String searchTerm) {
        StringBuilder stringBuilder = new StringBuilder(searchTerm);
        int index = stringBuilder.indexOf(";");
        if (index != -1) {
            stringBuilder.delete(index, stringBuilder.length());
        }
        searchTerm = stringBuilder.toString();
        return searchTerm;
    }

}
