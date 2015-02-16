package net.inpercima.runandfun.model;

public class RunkeeperActivities {

    private int size;

    private RunkeeperItems[] items;

    private String previous;

    private String next;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public RunkeeperItems[] getItems() {
        return items;
    }

    public void setItems(RunkeeperItems[] items) {
        this.items = items;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

}
