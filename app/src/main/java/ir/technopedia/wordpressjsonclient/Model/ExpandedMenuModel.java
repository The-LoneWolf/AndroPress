package ir.technopedia.wordpressjsonclient.model;

public class ExpandedMenuModel {

    public String iconName = "";
    public int iconImg = -1;
    public boolean is_expanded = false;

    public void toggle() {
        is_expanded = !is_expanded;
    }
}