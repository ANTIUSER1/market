package pn.market.additional;

public class Paging {
    private int pageSize;
    private int pageNumber;
private boolean hasNext;
private boolean hasPrevious;

    public Paging(int pageSize) {
        this.pageSize = pageSize;
        pageNumber=100;
        hasNext=true;
        hasPrevious=false;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public boolean isHasPrevious() {
        return hasPrevious;
    }
}
