package logic.logicInterface;


public interface Changed extends Navigable, Appendable {
    void fillTable();
    void fillEditingFormular(Object o);
    void cleanAll(int tab);

    void searchButton();
}
