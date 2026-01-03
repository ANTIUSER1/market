package pn.market.additional;

import lombok.ToString;

@ToString
public class Paging {
    private final int pageSize;
    private final int pageNumber;
    private final int totalPages;
    private final boolean hasNext;
    private final boolean hasPrevious;

    public Paging(int pageSize,
                  int pageNumber, int totalPages,
                  boolean hasNext, boolean hasPrevious) {
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
        this.totalPages = totalPages;
        this.hasNext = hasNext;
        this.hasPrevious = hasPrevious;
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

    public int getTotalPages() {
        return totalPages;
    }

}
