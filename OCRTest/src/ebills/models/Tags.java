package ebills.models;

/**
 * Created by pranavsharma on 09/05/15.
 */
public class Tags {

    public String getColumn__id() {
        return Column__id;
    }

    public void setColumn__id(String column__id) {
        Column__id = column__id;
    }

    public String getColumn_tags_name() {
        return Column_tags_name;
    }

    public void setColumn_tags_name(String column_tags_name) {
        Column_tags_name = column_tags_name;
    }

    public  String Column__id = "_id";
    public  String Column_tags_name = "tag_name";
}
